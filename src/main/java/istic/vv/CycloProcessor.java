package istic.vv;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

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

	int nbCyclo = 1; //  Si +1 pour un projet
	//int nbCyclo = 0; //  Si +1 pour chaque méthode



	public void process(CtMethodImpl method) {
		

		int nbCond = method.getElements(new TypeFilter(CtIf.class)).size();
		int nbLoop = method.getElements(new TypeFilter(CtLoop.class)).size();
		
		System.out.println("nbCond = "+nbCond);
		System.out.println("nbLoop = "+nbLoop);

		//nbCyclo = nbCyclo + nbCond + nbLoop + 1; // Si +1 pour chaque méthode
		nbCyclo = nbCyclo + nbCond + nbLoop; // Si +1 pour un projet
		
	}

	public int getNbCyclo() {
		return nbCyclo;
	}
	
	public int getFileCyclo(File file) {
		
		return 0;
	}

	
	

}
