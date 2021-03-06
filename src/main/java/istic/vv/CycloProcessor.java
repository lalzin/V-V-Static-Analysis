package istic.vv;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtLoop;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.declaration.CtMethodImpl;

/**
 * Classe permettant d'analyser une méthode et de renvoyer son nombre cyclomatique.
 * Une nombre cyclomatique se calcul en additonnant le nombre de IF avec le nombre de boucles.
 * 
 * @author Simon LEDOUX-LEVIN / Alan MARZIN
 *
 */
public class CycloProcessor extends AbstractProcessor<CtMethodImpl> {

	int nbCyclo = 1; //  Si +1 pour un projet
	//int nbCyclo = 0; //  Si +1 pour chaque méthode


	public void process(CtMethodImpl method) {
		

		int nbCond = method.getElements(new TypeFilter(CtIf.class)).size();
		int nbLoop = method.getElements(new TypeFilter(CtLoop.class)).size();

		//nbCyclo = nbCyclo + nbCond + nbLoop + 1; // Si +1 pour chaque méthode
		nbCyclo = nbCyclo + nbCond + nbLoop; // Si +1 pour un projet
		
	}

	public int getNbCyclo() {
		return nbCyclo;
	}
		
	

}
