package istic.vv.istic.vv;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import istic.vv.CycloProcessor;
import spoon.Launcher;
import spoon.reflect.CtModel;

public class CycloProcessorTest {

	
	@Test
	public void testCyclo1 () {
		Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        File inDir = new File("./src/main/java/istic/vv/TestNullPointer.java");
        assertNotNull(launcher);
        assertNotNull(inDir);
        launcher.addInputResource(inDir.getPath());
        launcher.buildModel();
        CtModel root = launcher.getModel();
		CycloProcessor processor = new CycloProcessor();
        launcher.addProcessor(processor);
        launcher.process();
        assertEquals(processor.getNbCyclo(), 5);
	}
}
