package istic.vv;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.support.reflect.code.CtAssignmentImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
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
				}			
				else {
					System.out.println(stat.toString() + " : " +stat.getClass());
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
