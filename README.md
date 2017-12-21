<h3>﻿V-V-Static-Analysis</h3>
<h1>Projet de Alan Marzin et Simon Ledoux Levin</h1>

<h1>Introduction : </h1>
Dans le cadre du module V&V du Master 2 Ingénierie logiciel en alternance, nous avons travaillé sur un outil d’analyse de code Java. Le sujet que nous avons choisi est la détection des NullPointerException. Il s’agissait donc d’analyser statiquement un code source afin de rendre un rapport a l’utilisateur, permettant d’avoir un avis sur les risques de NullPointerException sur ses fichiers Java. 
Pour ce faire, nous avons utilisé la librairie Spoon (créer par l’INRIA). 

<h1>Utilisation de l’analyseur :</h1> 

Importer le projet maven sous votre IDE préféré. 
Utiliser la class ScannerVandV pour faire l’analyse de nullPointer ou du nombre cyclomatique : 

<b>cyclomaticScanner :</b> Cette méthode prend un paramètre : le chemin vers le projet devant être analysé. Il renvoit un entier, le nombre cyclomatique du projet. Cette méthode utilise le processeur Spoon, CycloProcessor.

<b>cyclomaticScannerForFile :</b> Cette méthode prend un paramètre : le chemin vers le fichier JAVA devant être analysé. Il renvoit un entier, le nombre cyclomatique du fichier JAVA. Cette méthode utilise le processeur Spoon, CycloProcessor.

<b>nullPointerScanner :</b> Cette méthode prend un paramètre : le chemin vers le fichier à analyser pour trouver les éventuels NullPointerException. Cette méthode utilise le processeur Spoon, NullPointerProcessor.






