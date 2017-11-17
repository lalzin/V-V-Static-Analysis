package istic.vv;

import java.io.File;
import java.util.Map;

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

       
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        File inDir = new File("./src/main/java/istic/vv/SimpleTestNullPointer.java");
        launcher.addInputResource(inDir.getPath());
        launcher.buildModel();
        CtModel root = launcher.getModel();

        // DECOMMENTER LES LIGNES CI-DESSOUS POUR OBTENIR LE NOMBRE CYCLOMATIQUE
        
        CycloProcessor processor = new CycloProcessor();
        launcher.addProcessor(processor);
        launcher.process();
        
        System.out.println( "nbCyclo = "+processor.getNbCyclo());
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
