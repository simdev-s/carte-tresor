package com.springdemo.cartetresor;

import com.springdemo.cartetresor.domain.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.sum;

@SpringBootApplication
public class CarteTresorApplication {

	public static <Int> void main(String[] args) throws IOException {
		SpringApplication.run(CarteTresorApplication.class, args);

		// 👍 Fichier d'entrée
		Resource inputResource = new ClassPathResource("input.txt");
		File inputFile = inputResource.getFile();
		String inputContent = new String(Files.readAllBytes(inputFile.toPath()));
		String[] contentInputSplit = inputContent.split("\n");

		// 👍 Définition fichier de sortie
		Resource outputResource = new ClassPathResource("output.txt");
		File outputFile = outputResource.getFile();
		FileWriter outputFileWriter = new FileWriter(outputFile);
		BufferedWriter bufferedWriter = new BufferedWriter(outputFileWriter);

		// 👍 Instanciation des classes
		Carte carte = new Carte();
		Montagne montagne = new Montagne();
		Tresor tresor = new Tresor();
		Aventurier aventurier = new Aventurier();

		// 👍 Création des ArrayList associés aux paramètres
		ArrayList<Integer> montagneHorizArray = new ArrayList<>();
		ArrayList<Integer> montagneVerticArray = new ArrayList<>();

		ArrayList<Integer> tresorHorizArray = new ArrayList<>();
		ArrayList<Integer> tresorVerticArray = new ArrayList<>();
		ArrayList<Integer> nbTresorsArray = new ArrayList<>();

		ArrayList<String> nomAventArray = new ArrayList<>();
		ArrayList<Integer> aventHorizArray = new ArrayList<>();
		ArrayList<Integer> aventVerticArray = new ArrayList<>();
		ArrayList<String> orientationAventArray = new ArrayList<>();
		ArrayList<Integer> nbTresorsRamassesAventArray = new ArrayList<>();

		// 👍 Initialisation des numéros de ligne 'Montagne', 'Trésor' et 'Aventurier'
		int numeroMontagne = 0;
		int numeroTresor = 0;
		int numeroAventurier = 0;

		// 👍 Récupération des données du fichier d'entrée pour réécriture tronquée dans le fichier de sortie
		System.out.println(" ");
		System.out.println("------ Affichage ligne par ligne pour les trésors et les aventuriers au DÉBUT------");
		for (String line : contentInputSplit) {
			// 👍 Réupération du type de ligne
			String typeLigne=String.valueOf(line.charAt(0));
			// 👍 Séparation des lignes du fichier d'entrée
			String[] contentLineSplit = line.split(" ");
			// 👍 Cas de la ligne 'Carte'
			if(typeLigne.equals("C")){
				// Données du fichier sur la carte
				carte.mapWidth = Integer.parseInt(contentLineSplit[2]);
				carte.mapHeight = Integer.parseInt(contentLineSplit[4]);
			}

			// 👍 Cas de la ligne 'Montagne'
			if(typeLigne.equals("M")){
				// Renseignement du numéro de la montagne
				numeroMontagne++;
				// Données du fichier pour chaque montagne
				montagne.montagneHoriz = Integer.parseInt(contentLineSplit[2]);
				montagne.montagneVertic = Integer.parseInt(contentLineSplit[4]);

				montagneHorizArray.add(montagne.montagneHoriz);
				montagneVerticArray.add(montagne.montagneVertic);
			}

			// 👍 Cas de la ligne 'Trésor'
			if(typeLigne.equals("T")){
				// Renseignement du numéro du trésor
				numeroTresor++;
				// Affichage des données du fichier de départ pour chaque trésor
				System.out.println("◉ TRÉSOR " + numeroTresor + " début |" + " Nb trésors restants début : " + contentLineSplit[6]);

				// Définition des données variables de sortie pour chaque trésor
				tresor.tresorHoriz = Integer.parseInt(contentLineSplit[2]);
				tresorHorizArray.add(tresor.tresorHoriz);

				tresor.tresorVertic = Integer.parseInt(contentLineSplit[4]);
				tresorVerticArray.add(tresor.tresorVertic);

				tresor.nbTresors = Integer.parseInt(contentLineSplit[6]);
				nbTresorsArray.add(tresor.nbTresors);
			}

			// 👍 Cas de la ligne 'Aventurier'
			if(typeLigne.equals("A")){
				// Définition des données variables de sortie pour chaque trésor
				aventurier.prenom = contentLineSplit[2];
				nomAventArray.add(aventurier.prenom);

				aventurier.aventHoriz = Integer.parseInt(contentLineSplit[4]);
				aventHorizArray.add(aventurier.aventHoriz);

				aventurier.aventVertic = Integer.parseInt(contentLineSplit[6]);
				aventVerticArray.add(aventurier.aventVertic);

				aventurier.orientation = contentLineSplit[8];
				orientationAventArray.add(aventurier.orientation);

				aventurier.seqMvt = contentLineSplit[10];

				nbTresorsRamassesAventArray.add(0);

				// Renseignement du numéro de l'aventurier
				numeroAventurier++;
				// Affichage des données du fichier de départ pour chaque aventurier
				System.out.println("◉ AVENT. " + numeroAventurier + " début |" + " Pos. horiz. avent. début  : " + contentLineSplit[4] + " | Pos. verti. avent. "
						+ "début : " + contentLineSplit[6] + " | Orientation début : " + contentLineSplit[8]
						+ " | Séquence de mouvement full : " + contentLineSplit[10]);
			}
		}

		// 👍 Initialisation et remplissage des diverses cartes
			// 👍 Carte
			String[][] initCarte = new String[carte.mapHeight][carte.mapWidth];

			// 👍 Montagne
			for(int i = 0; i < montagneVerticArray.size(); i++) {
				initCarte[montagneVerticArray.get(i)][montagneHorizArray.get(i)]="M";
			}

			// 👍 Trésor
			int[][] initCarteNbTres = new int[carte.mapHeight][carte.mapWidth];

			for(int i = 0; i < tresorVerticArray.size(); i++) {
				initCarte[tresorVerticArray.get(i)][tresorHorizArray.get(i)]="T";

				initCarteNbTres[tresorVerticArray.get(i)][tresorHorizArray.get(i)]=nbTresorsArray.get(i);
			}

			// 👍 Aventurier
			String[][] initCarteNomAvent = new String[carte.mapHeight][carte.mapWidth];
			String[][] initOrientationNomAvent = new String[carte.mapHeight][carte.mapWidth];
			int[][] initCarteNbresorsRamassesAvent = new int[carte.mapHeight][carte.mapWidth];

			for(int i = 0; i < aventVerticArray.size(); i++) {
				initCarte[aventVerticArray.get(i)][aventHorizArray.get(i)]="A";

				initCarteNomAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=nomAventArray.get(i);
				initOrientationNomAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=orientationAventArray.get(i);
				initCarteNbresorsRamassesAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=nbTresorsRamassesAventArray.get(i);
			}

		/// 👍 ------ Affichage de diverses cartes au DÉBUT ------
			System.out.println(" ");
			System.out.println("------ Cartes diverses au DÉBUT ------");

			// 👍 Carte globale
			System.out.println("**** CARTE GLOBALE 'DÉBUT' ****");
			System.out.println(Arrays.deepToString(initCarte).replace("],", "],\n")
					.replace("null", "•"));

			// 👍 Nombre de trésors
			System.out.println(" ");
			System.out.println("**** TRÉSORS 'DÉBUT' ****");
			System.out.println("** Nb de trésors DÉBUT **");
			System.out.println(Arrays.deepToString(initCarteNbTres).replace("],", "],\n")
					.replace("0", "•"));

			// 👍 Carte avec le nom des aventuriers
			System.out.println(" ");
			System.out.println("**** AVENTURIER 'DÉBUT' ****");
			System.out.println("** Carte avec noms aventuriers DÉBUT **");
			System.out.println(Arrays.deepToString(initCarteNomAvent).replace("],", "],\n")
					.replace("null", "•"));
			// 👍 Carte avec orientation des aventuriers
			System.out.println("** Carte avec orientation des aventuriers DÉBUT **");
			System.out.println(Arrays.deepToString(initOrientationNomAvent).replace("],", "],\n")
					.replace("null", "•"));

		// 👍 Récupération des données du fichier d'entrée pour réécriture tronquée dans le fichier de sortie
		System.out.println(" ");
		System.out.println("------ Affichage ligne par ligne pour les trésors et les aventuriers à la FIN ------");
		numeroTresor = 0;
		numeroAventurier = 0;
		for (String line : contentInputSplit) {
			// 👍 Réupération du type de ligne
			String typeLigne=String.valueOf(line.charAt(0));
			// 👍 Séparation des lignes du fichier d'entrée
			String[] contentLineSplit = line.split(" ");
			// 👍 Cas de la ligne 'Carte'
			if(typeLigne.equals("C")){
				// Données du fichier sur la carte
				carte.mapWidth = Integer.parseInt(contentLineSplit[2]);
				carte.mapHeight = Integer.parseInt(contentLineSplit[4]);
				bufferedWriter.write(line + "\n");
			}

			// 👍 Cas de la ligne 'Montagne'
			if(typeLigne.equals("M")){
				// Renseignement du numéro de la montagne
				numeroMontagne++;
				// Données du fichier pour chaque montagne
				montagne.montagneHoriz = Integer.parseInt(contentLineSplit[2]);
				montagne.montagneVertic = Integer.parseInt(contentLineSplit[4]);

				montagneHorizArray.add(montagne.montagneHoriz);
				montagneVerticArray.add(montagne.montagneVertic);
				bufferedWriter.write(line + "\n");
			}

			// 🔥 Cas de la ligne 'Trésor'
			if(typeLigne.equals("T")){
				// Renseignement du numéro du trésor
				numeroTresor++;
				// Affichage des données du fichier de départ pour chaque trésor
				System.out.println("◉ TRÉSOR " + numeroTresor + " fin |" + " Nb trésors restants fin : " + contentLineSplit[6]);

				// Définition des données variables de sortie pour chaque trésor
				tresor.tresorHoriz = Integer.parseInt(contentLineSplit[2]);
				tresorHorizArray.add(tresor.tresorHoriz);

				tresor.tresorVertic = Integer.parseInt(contentLineSplit[4]);
				tresorVerticArray.add(tresor.tresorVertic);

				tresor.nbTresors = Integer.parseInt(contentLineSplit[6]);
				nbTresorsArray.add(tresor.nbTresors);

				// Écriture du fichier de sortie pour chaque trésor
				for (int i = 0; i < 6; i++) {
					bufferedWriter.write(contentLineSplit[i] + " ");
				}
				bufferedWriter.write("\n");
			}

			// 🔥 Cas de la ligne 'Aventurier'
			if(typeLigne.equals("A")){
				// Définition des données variables de sortie pour chaque trésor
				aventurier.prenom = contentLineSplit[2];
				nomAventArray.add(aventurier.prenom);

				aventurier.aventHoriz = Integer.parseInt(contentLineSplit[4]);
				aventHorizArray.add(aventurier.aventHoriz);

				aventurier.aventVertic = Integer.parseInt(contentLineSplit[6]);
				aventVerticArray.add(aventurier.aventVertic);

				aventurier.orientation = contentLineSplit[8];
				orientationAventArray.add(aventurier.orientation);

				aventurier.seqMvt = contentLineSplit[10];

				nbTresorsRamassesAventArray.add(0);

				// Renseignement du numéro de l'aventurier
				numeroAventurier++;
				// Affichage des données du fichier de départ pour chaque aventurier
				System.out.println("◉ AVENT. " + numeroAventurier + " fin |" + " Pos. horiz. avent. fin  : " + contentLineSplit[4] + " | Pos. verti. avent. "
						+ "fin : " + contentLineSplit[6] + " | Orientation fin : " + contentLineSplit[8]
						+ " | Séquence de mouvement full : " + contentLineSplit[10]);

				// Écriture du fichier de sortie pour chaque aventurier
				for (int i = 0; i < 4; i++) {
					bufferedWriter.write(contentLineSplit[i] + " ");
				}
				bufferedWriter.write("\n");
			}
		}

		// ℹ Fermeture possible du 'bufferedWriter'

		// 👍 Initialisation et remplissage des diverses cartes
		// 👍 Carte

		// 👍 Montagne
		for(int i = 0; i < montagneVerticArray.size(); i++) {
			initCarte[montagneVerticArray.get(i)][montagneHorizArray.get(i)]="M";
		}

		// 🔥 Trésor
		for(int i = 0; i < tresorVerticArray.size(); i++) {
			initCarte[tresorVerticArray.get(i)][tresorHorizArray.get(i)]="T";

			initCarteNbTres[tresorVerticArray.get(i)][tresorHorizArray.get(i)]=nbTresorsArray.get(i);
		}

		// 🔥 Aventurier
		for(int i = 0; i < aventVerticArray.size(); i++) {
			initCarte[aventVerticArray.get(i)][aventHorizArray.get(i)]="A";

			initCarteNomAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=nomAventArray.get(i);
			initOrientationNomAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=orientationAventArray.get(i);
			initCarteNbresorsRamassesAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=nbTresorsRamassesAventArray.get(i);
		}

		/// 👍 ------ Affichage de diverses cartes à la FIN ------
			System.out.println(" ");
			System.out.println("------ Cartes diverses à la FIN ------");

			// 👍 Carte globale
			System.out.println("**** CARTE GLOBALE 'FIN' ****");
			System.out.println(Arrays.deepToString(initCarte).replace("],", "],\n")
					.replace("null", "•"));

			// 👍 Nombre de trésors
			System.out.println(" ");
			System.out.println("**** TRÉSORS 'FIN' ****");
			System.out.println("** Nb de trésors FIN **");
			System.out.println(Arrays.deepToString(initCarteNbTres).replace("],", "],\n")
					.replace("0", "•"));

			// 👍 Carte avec le nom des aventuriers
			System.out.println(" ");
			System.out.println("**** AVENTURIER 'FIN' ****");
			System.out.println("** Carte avec noms aventuriers FIN **");
			System.out.println(Arrays.deepToString(initCarteNomAvent).replace("],", "],\n")
					.replace("null", "•"));
			// Carte avec orientation des aventuriers
			System.out.println("** Carte avec orientation des aventuriers FIN **");
			System.out.println(Arrays.deepToString(initOrientationNomAvent).replace("],", "],\n")
					.replace("null", "•"));
			// Carte avec le nombre de trésors des aventuriers
			System.out.println("** Carte avec nb de trésors des aventuriers FIN **");
			System.out.println(Arrays.deepToString(initCarteNbresorsRamassesAvent).replace("],", "],\n")
					.replace("0", "•"));

		// 👍 Fermeture du 'bufferedWriter'
		bufferedWriter.close ();
	}
}