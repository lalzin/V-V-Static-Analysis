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

public class NullProcessor extends AbstractProcessor<CtClassImpl> {
	
	private boolean addOkValue = true;

	private ArrayList<HashMap<String, String>> stackVariable = new ArrayList<HashMap<String,String>>();
		
	private ArrayList<DataVar> resultNpe = new ArrayList<DataVar>();
	
	private int blockDepth = 0;

	
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


	private void blockProcess(CtBlockImpl block) {
		Iterator<CtStatement> ite = block.iterator();
		while(ite.hasNext()) {
			CtStatement stat = ite.next();
			statementProcess(stat);
		}		
	}

	private void whileProcess(CtLoop stat) {

		statementProcess(stat.getBody());
	}


	private void conditionProcess(CtExpression ctExpression) {
		// TODO Auto-generated method stub
		
	}


	private void ifProcess(CtIfImpl ifStat) {
	
		conditionProcess(ifStat.getCondition());
		statementProcess(ifStat.getThenStatement());
		if(ifStat.getElseStatement() != null) {
			statementProcess(ifStat.getElseStatement());
		}
	}

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

	private void localVariableProcess(CtLocalVariableImpl locVar) {
		String locVarName = locVar.getSimpleName();
		String assignement = locVar.getAssignment().toString();
		
		stackVariable.get(blockDepth).put(locVarName,assignement);		
	}

	private void fieldProcess(CtFieldImpl  field) {
		
		String fieldName = field.getSimpleName();
		String assignement = field.getAssignment().toString();		
		stackVariable.get(blockDepth).put(fieldName, assignement);
	}

	public void assignProcess(CtAssignmentImpl assign) {
		
		String assigned = assign.getAssigned().toString();
		String assignement = assign.getAssignment().toString();
		stackVariable.get(blockDepth).put(assigned,assignement);

	}


	public ArrayList<DataVar> getResultNpe() {
		return resultNpe;
	}

	public void setResultNpe(ArrayList<DataVar> resultNpe) {
		this.resultNpe = resultNpe;
	}

	
	

}
