package istic.vv;

import java.io.File;
import java.util.Map;

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
       
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        File inDir = new File("./src/main/java/istic/vv/SimpleTestNullPointer.java");
        launcher.addInputResource(inDir.getPath());
        launcher.buildModel();
        CtModel root = launcher.getModel();

        // DECOMMENTER LES LIGNES CI-DESSOUS POUR OBTENIR LE NOMBRE CYCLOMATIQUE
        
        /*CycloProcessor processor = new CycloProcessor();
        launcher.addProcessor(processor);
        launcher.process();*/
        
                
        NullProcessor nullProcessor = new NullProcessor();
        launcher.addProcessor(nullProcessor);
        launcher.process();
        
        
        // Affiche le contenu du hashmap gérant les variables/attributs de la classe inspectée
        for (String mapKey : nullProcessor.getMapVar().keySet()) {
        	DataVar data = nullProcessor.getMapVar().get(mapKey);
        	System.out.println("< "+mapKey+" , "+data.getValue()+" >");
        	
        }        
        
        /*
        File outDir = new File("generation");
        launcher.setSourceOutputDirectory(outDir.getPath());
        launcher.prettyprint();
        */
    }
    
    
    
}
