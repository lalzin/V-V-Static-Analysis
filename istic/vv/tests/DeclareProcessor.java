package istic.vv.tests;

import java.util.HashMap;
import java.util.Iterator;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.code.CtAssignmentImpl;
import spoon.support.reflect.code.CtBlockImpl;
import spoon.support.reflect.code.CtInvocationImpl;

public class DeclareProcessor extends  AbstractProcessor<CtBlockImpl>  {

	private HashMap<String, DataVar> mapVar = new HashMap<String, DataVar>();
		
	
	public void process(CtBlockImpl block) {
		
		
		Iterator<CtStatement> ite = block.iterator();
		while(ite.hasNext()) {
			CtStatement stat = ite.next();
			System.out.println(stat.getClass());

			if(stat instanceof CtAssignmentImpl) {
				assignProcess((CtAssignmentImpl) stat);
			} else if (stat instanceof CtInvocationImpl){
				
				
				
			}else {	
			}
			
		}	
	}
	
	public void assignProcess(CtAssignmentImpl assign) {
		
		String assigned = assign.getAssigned().toString();
		String assignement = assign.getAssignment().toString();
		
		mapVar.put(assigned, new DataVar(assignement, DataVar.STATUS.OK));
		System.out.println("     Assigned = "+assigned+" / Assignement = "+assignement);
		if(assignement.equals("null")) {
			System.out.println("     --> WARNING = assignement is null");
		}		
		
		

	}

	public HashMap<String, DataVar> getMapVar() {
		return mapVar;
	}

	public void setMapVar(HashMap<String, DataVar> mapVar) {
		this.mapVar = mapVar;
	}

	


}
