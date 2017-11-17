package istic.vv;

import java.util.Iterator;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtForEachImpl;
import spoon.support.reflect.code.CtForImpl;
import spoon.support.reflect.code.CtIfImpl;
import spoon.support.reflect.code.CtSwitchImpl;
import spoon.support.reflect.code.CtWhileImpl;
import spoon.support.reflect.declaration.CtMethodImpl;

public class CycloProcessor extends AbstractProcessor<CtMethodImpl> {

	static int nbCyclo = 1;
	
	public void process(CtMethodImpl method) {

	
		int nbCond = method.getElements(new TypeFilter(CtIf.class)).size();
		int nbLoop = method.getElements(new TypeFilter(CtLoop.class)).size();

		nbCyclo = nbCyclo + nbCond + nbLoop;
	}

	public int getNbCyclo() {
		return nbCyclo;
	}

	

}
