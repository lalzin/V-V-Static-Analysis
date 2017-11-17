package istic.vv;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import istic.vv.DataVar.STATUS;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.code.CtAssignmentImpl;
import spoon.support.reflect.code.CtIfImpl;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.code.CtVariableReadImpl;
import spoon.support.reflect.declaration.CtClassImpl;
import spoon.support.reflect.declaration.CtFieldImpl;
import spoon.support.reflect.declaration.CtMethodImpl;

public class NullProcessor extends AbstractProcessor<CtClassImpl> {

	private HashMap<String, DataVar> mapVar = new HashMap<String, DataVar>();
		
	
	public void process(CtClassImpl myClass) {
		
		List<CtFieldImpl> listFields = myClass.getFields();
		for(CtFieldImpl  field : listFields ) {
			fieldProcess(field);
		}
		
		
		Set<CtMethodImpl> listMethods = myClass.getAllMethods();
		
		for(CtMethodImpl method : listMethods ) {
			
			CtBlock block = method.getBody();
			Iterator<CtStatement> ite = block.iterator();
			while(ite.hasNext()) {
				CtStatement stat = ite.next();

				if(stat instanceof CtAssignmentImpl) {
					assignProcess((CtAssignmentImpl) stat);
				} else if (stat instanceof CtLocalVariableImpl){
					localVariableProcess((CtLocalVariableImpl) stat);
				} else if(stat instanceof CtIfImpl) {
					ifProcess();
				} else if (stat instanceof CtInvocationImpl) {
					invocationProcess((CtInvocationImpl) stat);
				} else {
					//System.out.println(stat.toString() + " : " +stat.getClass());
				}
			}
			
		}		
	}
	
	private void ifProcess() {
		// TODO Auto-generated method stub
		
	}

	private void invocationProcess(CtInvocationImpl stat) {

		for ( CtElement elem : stat.getElements(null) ) {
			if(mapVar.containsKey(elem.toString())){
				if(mapVar.get(elem.toString()).getValue().equals("null")) {
					mapVar.get(elem.toString()).setStatus(STATUS.ALERT);
				}
			}
		}
	}

	private void localVariableProcess(CtLocalVariableImpl locVar) {
		String locVarName = locVar.getSimpleName();
		String assignement = locVar.getAssignment().toString();
		
		mapVar.put(locVarName, new DataVar(assignement, DataVar.STATUS.OK));		
	}

	private void fieldProcess(CtFieldImpl  field) {
		
		String fieldName = field.getSimpleName();
		String assignement = field.getAssignment().toString();		
		mapVar.put(fieldName, new DataVar(assignement, DataVar.STATUS.OK));
	}

	public void assignProcess(CtAssignmentImpl assign) {
		
		String assigned = assign.getAssigned().toString();
		String assignement = assign.getAssignment().toString();
		
		mapVar.put(assigned, new DataVar(assignement, DataVar.STATUS.OK));

	}

	public HashMap<String, DataVar> getMapVar() {
		return mapVar;
	}

	public void setMapVar(HashMap<String, DataVar> mapVar) {
		this.mapVar = mapVar;
	}

	
	

}
