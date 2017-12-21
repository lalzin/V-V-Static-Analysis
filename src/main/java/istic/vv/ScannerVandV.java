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

/**
 * Point d'entrée du projet. 
 * Voici ces fonctionnalités : 
 * 	- Scanneur de projet pour obtenir le nombre cyclomatique
 *  - Scanneur de fichier pour obtenir le nombre cyclomatique
 *  - Scanneur de fichier pour obtenir une analyse des risques de NullPointerException
 *  
 * @author Simon LEDOUX-LEVIN / Alan MARZIN
 *
 */
public class ScannerVandV {
	
	

	final static Logger log = Logger.getLogger(ScannerVandV.class);

	  /**
	   * Méthode permettant d'otbenir le nombre cylomatique de tout un projet.
	   * Il parcourt tout le repertoire et sous-répertoire afin d'analyser les fichiers .JAVA.
	   * Il fait appel au processor CycloProcessor.
	   * 
	   * @param directoryPath le chemin du projet a analyser.
	   * @return le nombre cyclomatique du projet
	   */
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
	  
	  /**
	   * Méthode permettant d'otbenir le nombre cylomatique d'un fichier .JAVA.
	   * Il fait appel au processor CycloProcessor.
	   * 
	   * @param filePath le chemin du fichier a analyser.
	   * @return le nombre cyclomatique du fichier
	   */
	  public static int cyclomaticScannerForFile(String filePath) {
	    	
	    	log.debug("### Cyclomatic Scanner One File ###");
	    	
	    	//Chemin vers le fichier a analyser
	    	File oneFile= new File(filePath);
	    	log.debug("## Fichier analysée :" + oneFile.getName() +" ##");
	    	
			if(!oneFile.exists()) {
				log.debug("Le fichier n'existe pas");
			}
			
		        Launcher launcher = new Launcher();
		        launcher.getEnvironment().setAutoImports(true);
		        launcher.getEnvironment().setNoClasspath(true);
		        launcher.addInputResource(oneFile.getPath());
		        launcher.buildModel();
		        CtModel root = launcher.getModel();
				CycloProcessor processor = new CycloProcessor();
		        log.debug("\nFile : "+oneFile.getName());
		        launcher.addProcessor(processor);
		        launcher.process();
		        log.debug("Cyclomatic = "+processor.getNbCyclo());  
	        return processor.getNbCyclo();
	    }
	    
	  /**
	   * 
	   * Méthode permettant d'executer une analyse des risques des NullPointerExpception d'un fichier JAVA.
	   * Elle utilise le processor NullPointerProcessor.
	   * Il retourne un rapport/une liste contenant des objets résumant les appels effectués.
	   * Le type DataVar est structuré comme suit :
	   * 	VariableName : la variable appelée, concerné.
	   * 	Value : La valeur de la variable concerné
	   * 	Status : 3  valeurs : 
	   * 					Alert (le NPE est certains d'être déclenché)
	   * 					Warning (Le NPE est potentiellement dangereux. Impossible de le determiner.
	   * 					OK (L'appel est safe, pas de NPE potentiel.
	   * 	Line : La ligne concerné dans le programme analysé.

	   * 
	   * @param fileToAnalyse le fichier JAVA a analyser.
	   * @return Une liste résumant les appels effectués et leurs caractéristiques.
	   */
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

	        return nullProcessor.getResultNpe();
	    }

		public static boolean equalsResultNPE(ArrayList<DataVar> listResults, ArrayList<DataVar> listExpectedResult) {
			
			if(listResults.size() != listExpectedResult.size()) {
				return false;
			}
			
			boolean listIdentique = true;

			for(DataVar dataExpected : listExpectedResult) {
				
				boolean elementPresent = false;
				for(DataVar realData : listResults) {
					
					if(realData.equals(dataExpected)) {
						elementPresent = true;
					}				
				}
				if(elementPresent==false) {
					listIdentique = false;
				}
			}
			return listIdentique;	
		}
}
