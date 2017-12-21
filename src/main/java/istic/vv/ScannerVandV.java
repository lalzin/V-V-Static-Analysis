package istic.vv;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.log4j.Logger;

import istic.vv.DataVar.STATUS;
import spoon.Launcher;
import spoon.reflect.CtModel;

public class ScannerVandV {

	final static Logger log = Logger.getLogger(ScannerVandV.class);

	
	  public static int cyclomaticScanner(String directoryPath) {
	    	
	    	log.debug("### Cyclomatic Scanner ###");
	    	
	    	//Chemin vers le répertoire du projet a scanner
	    	File directoryProject = new File(directoryPath);
	    	
	    	//Retourne tous les fichiers .java contenu dans le projet et tous les sous-répertoires recursivement.
			Collection<File> files = FileUtils.listFiles(directoryProject,new RegexFileFilter("^(\\w*\\.java$)"),DirectoryFileFilter.DIRECTORY);
			
			if(files.size()==0) {
				log.debug("Aucun fichier .java dans ce projet.");
			}
			
			int totalCyclo = 0;
			for(File file : files) {
		        Launcher launcher = new Launcher();
		        launcher.getEnvironment().setAutoImports(true);
		        launcher.getEnvironment().setNoClasspath(true);
		        launcher.addInputResource(file.getPath());
		        launcher.buildModel();
		        CtModel root = launcher.getModel();
				CycloProcessor processor = new CycloProcessor();
		        log.debug("\nFile : "+file.getName());
		        launcher.addProcessor(processor);
		        launcher.process();
		        log.debug("Cyclomatic = "+processor.getNbCyclo());
		        totalCyclo = totalCyclo+ processor.getNbCyclo();
		        
			}
	    	

	        return totalCyclo;
	    }
	    
	    public static ArrayList<DataVar> nullPointerScanner(String fileToAnalyse) {
	    	
	    	log.debug("### NullPointerException Scanner ###");
	    	
	    	File directoryProject = new File(fileToAnalyse);
	    	Launcher launcher = new Launcher();
	    	launcher.getEnvironment().setAutoImports(true);
	    	launcher.getEnvironment().setNoClasspath(true);
	        launcher.addInputResource(directoryProject.getPath());
	        
	    	NullProcessor nullProcessor = new NullProcessor();
	        launcher.addProcessor(nullProcessor);
	        
	        launcher.buildModel();

	        launcher.process();
	       
	        // Affiche les résultats de l'analyse NPE
	       /* for (DataVar data : nullProcessor.getResultNpe()) {
	        	if(data.getStatus().equals(STATUS.ALERT)) {
	        		log.info("ALERT for ["+data.getVariableName()+"] with value ["+data.getValue()+"] at line "+data.getLine());
	        	} else if(data.getStatus().equals(STATUS.WARNING)) {
	        		log.info("WARNING for ["+data.getVariableName()+"] with value ["+data.getValue()+"] at line "+data.getLine());
	        	} else if(data.getStatus().equals(STATUS.OK)) {
	        		log.info("OK for ["+data.getVariableName()+"] with value ["+data.getValue()+"] at line "+data.getLine());
	        	}
	        }     */

	        return nullProcessor.getResultNpe();
	    }
	    
	
	
}
