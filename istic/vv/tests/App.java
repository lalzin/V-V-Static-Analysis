package istic.vv.tests;

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
        System.out.println( "Hello World!" );
        
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        File inDir = new File("src/main/java/istic/vv/tests/TestNullPointer.java");
        launcher.addInputResource(inDir.getPath());
        launcher.buildModel();
        CtModel root = launcher.getModel();

        DeclareProcessor processor = new DeclareProcessor();
        launcher.addProcessor(processor);
        
        launcher.process();
        
       
        
        Map<String, DataVar> hashMap =  processor.getMapVar();
        for (String mapKey : hashMap.keySet()) {
        	System.out.println("-> KEY ["+mapKey+"] VALUE ["+hashMap.get(mapKey).getValue()+
        						"] STATUS ["+hashMap.get(mapKey).getStatus()+"]");
        }
        
        File outDir = new File("generation");
        launcher.setSourceOutputDirectory(outDir.getPath());
        launcher.prettyprint();
        
    }
}
