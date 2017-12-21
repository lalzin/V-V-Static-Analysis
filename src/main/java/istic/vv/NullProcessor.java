package istic.vv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import istic.vv.DataVar.STATUS;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.code.CtAssignmentImpl;
import spoon.support.reflect.code.CtBlockImpl;
import spoon.support.reflect.code.CtConditionalImpl;
import spoon.support.reflect.code.CtIfImpl;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.code.CtVariableReadImpl;
import spoon.support.reflect.code.CtWhileImpl;
import spoon.support.reflect.declaration.CtClassImpl;
import spoon.support.reflect.declaration.CtFieldImpl;
import spoon.support.reflect.declaration.CtMethodImpl;

/**
 * Classe processor Spoon qui a pour préoccupation l'analyse statique de classe pour la détection des NullPointerException
* 
 * 
 * @author Simon LEDOUX-LEVIN / Alan MARZIN
 *
 */
public class NullProcessor extends AbstractProcessor<CtClassImpl> {
	
	private boolean addOkValue = true;

	/** Pile des tableaux gérant les variables pouvant declencher potentiellement un NullPointer */
	private ArrayList<HashMap<String, String>> stackVariable = new ArrayList<HashMap<String,String>>();
		
	/** Résultat de l'analuse NPE. C'est cet élement que l'user doit recupérer. */
	
	private ArrayList<DataVar> resultNpe = new ArrayList<DataVar>();

	private int blockDepth = 0;

	
	/**
	 * Méthode du processeur Spoon. 
	 * Analyse toute les méthodes et les statements de la classe envoyé dans le processeur.
	 * 
	 * @param myClass classe analysée par le processor.
	 */
	public void process(CtClassImpl myClass) {
		
		HashMap<String, String> mapVarGlobal = new HashMap<String, String>();
		stackVariable.add(mapVarGlobal);
		//Traite les attributs et leur initialisation
		List<CtFieldImpl> listFields = myClass.getFields();
		for(CtFieldImpl  field : listFields ) {
			fieldProcess(field);
		}

		Set<CtMethodImpl> listMethods = myClass.getAllMethods();
		
		//Traitement methode par méthode
		for(CtMethodImpl method : listMethods ) {
			CtBlockImpl block = (CtBlockImpl) method.getBody();
			statementProcess(block);
		}		
	}
	

	/**
	 * Méthode que l'on peut determiné de "proxy".
	 * En fonction de l'instruction analysée, il dirigé le statement vers la méthode approprié.
	 * 
	 * @param stat l'instruction devant être dirigé vers la méthode qui convient
	 */
	private void statementProcess(CtStatement stat) {
		if(stat instanceof CtAssignmentImpl) {
			assignProcess((CtAssignmentImpl) stat);
		} else if (stat instanceof CtLocalVariableImpl){
			localVariableProcess((CtLocalVariableImpl) stat);
		} else if (stat instanceof CtInvocationImpl) {
			invocationProcess((CtInvocationImpl) stat);
		} else if (stat instanceof CtIfImpl) {
			ifProcess((CtIfImpl) stat);
		} else if (stat instanceof CtWhileImpl) {
			whileProcess((CtLoop) stat);
		} else if (stat instanceof CtBlockImpl) {
			HashMap<String, String> newMapVar = (HashMap<String, String>) stackVariable.get(blockDepth).clone();
			stackVariable.add(newMapVar);
			blockDepth++;
			blockProcess((CtBlockImpl) stat);
			stackVariable.remove(blockDepth);
			blockDepth--;
			
		} else {
			/*System.out.println(stat.getClass());
			System.out.println(stat.toString());*/
		}
	}


	/**
	 * Méthode de traitement des Block. 
	 * Elle itère sur les instructions du block et l'envoie à la méthode statementProcess.
	 * 
	 * @param block le block a traité
	 */
	private void blockProcess(CtBlockImpl block) {
		Iterator<CtStatement> ite = block.iterator();
		while(ite.hasNext()) {
			CtStatement stat = ite.next();
			statementProcess(stat);
		}		
	}

	/**
	 * Méthode de traitement des boucles
	 * 
	 * @param stat la boucle a traité
	 */
	private void whileProcess(CtLoop stat) {

		statementProcess(stat.getBody());
	}


	/**NON FONCTIONNEL. 
	 * La gestion des conditions/expressions n'est pas fonctionelle.
	 * 
	 * 
	 * @param ctExpression l'expression a traité
	 */
	private void conditionProcess(CtExpression ctExpression) {
		// TODO Auto-generated method stub
		
	}


	/**Méthode de traitement des IfElse
	 * 
	 * 
	 * @param ifStat le block If/Else a analyser.
	 */
	private void ifProcess(CtIfImpl ifStat) {
	
		conditionProcess(ifStat.getCondition());
		statementProcess(ifStat.getThenStatement());
		if(ifStat.getElseStatement() != null) {
			statementProcess(ifStat.getElseStatement());
		}
	}

	/**Méthode qui traite l'invocation d'une variable.
	 * Cest ici que l'analyse NPE determine si un appel est dangereux, potentiellement dangereux ou non.
	 * 
	 * 
	 * @param stat l'invocation/l'appel a traiter.
	 */
	private void invocationProcess(CtInvocationImpl stat) {

		for ( CtElement elem : stat.getElements(null) ) {
			if(stackVariable.get(blockDepth).containsKey(elem.toString())){
				if(stackVariable.get(blockDepth).get(elem.toString()).equals("null")) {
					DataVar maDataVar;
					if(this.blockDepth<2) {
						maDataVar = new DataVar(stackVariable.get(blockDepth).get(elem.toString()), 
								  STATUS.ALERT, 
								  stat.getPosition().getLine(),
								  elem.toString());
					} else {
						maDataVar = new DataVar(stackVariable.get(blockDepth).get(elem.toString()), 
								  STATUS.WARNING, 
								  stat.getPosition().getLine(),
								  elem.toString());
					}
			
					if(checkAddResult(maDataVar)) {
						resultNpe.add(maDataVar);
					}
				} else {
					if(addOkValue) {
						DataVar maDataVar = new DataVar(stackVariable.get(blockDepth).get(elem.toString()), 
								  STATUS.OK, 
								  stat.getPosition().getLine(),
								  elem.toString());
						if(checkAddResult(maDataVar)) {
							resultNpe.add(maDataVar);
						}
					}
				}
			} 
		}
	}

	/**
	 * Méthode privée filtrant les doublons dans la liste des resultats NPE.
	 * 
	 * @param maDataVar l'objet a checker en doublon
	 * @return true si l'objet n'est pas présent, false sinon.
	 */
	private boolean checkAddResult(DataVar maDataVar) {
		
		for(DataVar itData : getResultNpe())  {
			if(itData.getLine() == (maDataVar.getLine()) && 
			    itData.getStatus().equals(maDataVar.getStatus()) &&
				itData.getValue().equals(maDataVar.getValue()) &&
				itData.getVariableName().equals(maDataVar.getVariableName())) {
				
				return false;
				
				}
		}
		return true;
	}

	/**
	 * Méthode traitant la déclarations dzes variables locales.
	 * La valeur de la variable est stockée dans le hashmap courant de stackVariable.
	 * 
	 * @param locVar la variable local a traiter
	 */
	private void localVariableProcess(CtLocalVariableImpl locVar) {
		String locVarName = locVar.getSimpleName();
		String assignement = locVar.getAssignment().toString();
		
		stackVariable.get(blockDepth).put(locVarName,assignement);		
	}

	/**
	 * Méthode traitant la déclaration des attributs.
	 * L'attribut est stockée dans le hashmap courant de stackVariable.
	 * @param field l'attribut a traiter
	 */
	private void fieldProcess(CtFieldImpl  field) {
		
		String fieldName = field.getSimpleName();
		String assignement = field.getAssignment().toString();		
		stackVariable.get(blockDepth).put(fieldName, assignement);
	}

	/**
	 * Méthode traitant les affectations.
	 * La valeur de l'affectation est mis a jour dans le hashmap courant.
	 * @param assign l'affection a traiter
	 */
	public void assignProcess(CtAssignmentImpl assign) {
		
		String assigned = assign.getAssigned().toString();
		String assignement = assign.getAssignment().toString();
		stackVariable.get(blockDepth).put(assigned,assignement);

	}


	/**
	 * Permet a l'utilisateur de recuperer les résultats de l'analyse. 
	 * 
	 * @return une liste des résultats
	 */
	public ArrayList<DataVar> getResultNpe() {
		return resultNpe;
	}
	

}
