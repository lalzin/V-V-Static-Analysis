package istic.vv;

import java.util.Iterator;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;
import spoon.support.reflect.code.CtForEachImpl;
import spoon.support.reflect.code.CtForImpl;
import spoon.support.reflect.code.CtIfImpl;
import spoon.support.reflect.code.CtSwitchImpl;
import spoon.support.reflect.code.CtWhileImpl;
import spoon.support.reflect.declaration.CtMethodImpl;

public class CycloProcessor extends AbstractProcessor<CtMethodImpl> {

	int nbCyclo = 1;
	
	public void process(CtMethodImpl method) {

		System.out.println("-> Cyclo Processor");

		
		CtBlock block = method.getBody();
		Iterator<CtStatement> ite = block.iterator();
		
		while(ite.hasNext()) {
			CtStatement statement = ite.next();
			if(statement instanceof CtIfImpl) {
				nbCyclo ++;
			} else if (statement instanceof CtForImpl)  {
				nbCyclo ++;
			}else if(statement instanceof CtForEachImpl)  {
				nbCyclo ++;
			}else if(statement instanceof CtWhileImpl)  {
				nbCyclo ++;
			}else if(statement instanceof CtSwitchImpl)  {
				nbCyclo += ((CtSwitchImpl) statement).getCases().size();
			}
		}
	}

	public int getNbCyclo() {
		return nbCyclo;
	}
	
	

}
