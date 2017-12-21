package istic.vv;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.log4j.Logger;

import istic.vv.DataVar.STATUS;
import spoon.Launcher;
import spoon.reflect.CtModel;

/**
 * Hello world!
 *
 */
public class App 
{
	
	final static Logger log = Logger.getLogger(App.class);

    public static void main( String[] args )
    {
    	     		
    	//int cycloResult = ScannerVandV.cyclomaticScanner("/home/simon/eclipse-workspace/Camenbert");
    	//log.info("CYCLOMATIC = "+cycloResult);
    	
    	ScannerVandV.nullPointerScanner("/home/simon/eclipse-workspace/V-V-Static-Analysis/input/NullPointer2Methods.java");
				

      
    }
    
  

    
}
