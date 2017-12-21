package istic.vv.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import istic.vv.CycloProcessor;
import istic.vv.ScannerVandV;
import spoon.Launcher;
import spoon.reflect.CtModel;

public class CycloProcessorTest {

	
	@Test
	public void testCyclo1 () {
		ScannerVandV.cyclomaticScanner("imput");
		
		
		
	}
	
}
