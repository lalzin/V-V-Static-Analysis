package istic.vv.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import istic.vv.CycloProcessor;
import istic.vv.ScannerVandV;
import spoon.Launcher;
import spoon.reflect.CtModel;

//Class de test pour le calcul du cyclomatique
public class CycloProcessorTest {

	//Test cyclomatique pour le dossier input à la racine du projet 
	@Test
	public void testCycloFolder () {
		int nbCycloResult = ScannerVandV.cyclomaticScanner("./input");
		assertNotNull(nbCycloResult);
		assertEquals(12, nbCycloResult);
	}
	
	//Test cyclomatique pour le fichier NullPointer2If contenant un seul if
	@Test
	public void testCycloFileIf() {
		int nbCycloResult = ScannerVandV.cyclomaticScannerFoFile("./input/NullPointer2If.java");
		assertNotNull(nbCycloResult);
		assertEquals(2, nbCycloResult);
	}
	//Test cyclomatique pour le fichier NullPointer2Methods contenant deux méthode sans condition ou boucle
	@Test
	public void testCycloFileMethod() {
		int nbCycloResult = ScannerVandV.cyclomaticScannerFoFile("./input/NullPointer2Methods.java");
		assertNotNull(nbCycloResult);
		assertEquals(1, nbCycloResult);
	}
	//Test cyclomatique pour le fichier NullPointerIf contenant un if et un else
	@Test
	public void testCycloFileIfElse() {
		int nbCycloResult = ScannerVandV.cyclomaticScannerFoFile("./input/NullPointerIf.java");
		assertNotNull(nbCycloResult);
		assertEquals(2, nbCycloResult);
	}
	//Test cyclomatique pour le fichier NullPointerSimple contenant une seuk méthode simple
		@Test
		public void testCycloFileSimple() {
			int nbCycloResult = ScannerVandV.cyclomaticScannerFoFile("./input/NullPointerSimple.java");
			assertNotNull(nbCycloResult);
			assertEquals(1, nbCycloResult);
		}
	
		//Test cyclomatique pour le fichier NullPointerWhile contenant un while 
		@Test
		public void testCycloFileWhile() {
			int nbCycloResult = ScannerVandV.cyclomaticScannerFoFile("./input/NullPointerWhile.java");
			assertNotNull(nbCycloResult);
			assertEquals(2, nbCycloResult);
		}
		//Test cyclomatique pour le fichier NullPointerWhileIfElse contenant un while et 2 if et un else
				@Test
				public void testCycloFileWhileIfElse() {
					int nbCycloResult = ScannerVandV.cyclomaticScannerFoFile("./input/NullPointerWhileIfElse.java");
					assertNotNull(nbCycloResult);
					assertEquals(4, nbCycloResult);
				}
}
