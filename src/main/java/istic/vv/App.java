package istic.vv;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import istic.vv.DataVar.STATUS;
import spoon.Launcher;
import spoon.reflect.CtModel;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	int nbAlert = 0;
    	int nbWarning = 0;
    	int nbOk = 0;

       


        // DECOMMENTER LES LIGNES CI-DESSOUS POUR OBTENIR LE NOMBRE CYCLOMATIQUE
        
		File dir = new File("/home/simon/eclipse-workspace/Camenbert");
		Collection<File> files = FileUtils.listFiles(dir,new RegexFileFilter("^(\\w*\\.java$)"),DirectoryFileFilter.DIRECTORY);
		if(files.size()==0)System.out.println("NULL");
		int totalCyclo = 0;
		for(File file : files) {
	        Launcher launcher = new Launcher();
	        launcher.getEnvironment().setAutoImports(true);
	        launcher.getEnvironment().setNoClasspath(true);
	        //File inDir = new File("./src/main/java/istic/vv/TestNullPointer.java");
	        launcher.addInputResource(file.getPath());
	        launcher.buildModel();
	        CtModel root = launcher.getModel();
			CycloProcessor processor = new CycloProcessor();
	        System.out.println("File : "+file.getName());
	        launcher.addProcessor(processor);
	        launcher.process();
	        System.out.println("cycloFile = "+processor.getNbCyclo());
	        totalCyclo = totalCyclo+ processor.getNbCyclo();
	        
		}

				

        System.out.println( "nbCycloFolder = "+totalCyclo);
       /* NullProcessor nullProcessor = new NullProcessor();
        launcher.addProcessor(nullProcessor);
        launcher.process();
        
        
        // Affiche le contenu du hashmap gérant les variables/attributs de la classe inspectée
        for (String mapKey : nullProcessor.getMapVar().keySet()) {
        	DataVar data = nullProcessor.getMapVar().get(mapKey);
        	//System.out.println("<"+mapKey+" , "+data.getValue()+">");
        	if(data.getStatus().equals(STATUS.ALERT)) {
        		nbAlert++;
        	} else if(data.getStatus().equals(STATUS.WARNING)) {
        		nbWarning++;
        	} else if(data.getStatus().equals(STATUS.OK)) {
        		nbOk++;
        	}
        }     
        
        System.out.println("###### RESULT ######");
        System.out.println("nbAlert = " + nbAlert);
        System.out.println("nbWarning = " + nbWarning);
        System.out.println("nbOk = " + nbOk);
*/
        /*
        File outDir = new File("generation");
        launcher.setSourceOutputDirectory(outDir.getPath());
        launcher.prettyprint();
        */
    }
    
    
    
}
