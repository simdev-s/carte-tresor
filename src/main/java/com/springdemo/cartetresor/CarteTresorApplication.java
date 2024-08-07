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
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.sum;

@SpringBootApplication
public class CarteTresorApplication {

	public static <Int> void main(String[] args) throws IOException {
		SpringApplication.run(CarteTresorApplication.class, args);

		// Import fichier d'entrée
		Resource inputResource = new ClassPathResource("input.txt");
		File inputFile = inputResource.getFile();
		String inputContent = new String(Files.readAllBytes(inputFile.toPath()));
		String[] contentInputSplit = inputContent.split("\n");

		// Définition fichier de sortie
		Resource outputResource = new ClassPathResource("output.txt");
		File outputFile = outputResource.getFile();
		FileWriter outputFileWriter = new FileWriter(outputFile);
		BufferedWriter bufferedWriter = new BufferedWriter(outputFileWriter);

		// Instanciation des classes
		Carte carte = new Carte();
		Montagne montagne = new Montagne();
		Tresor tresor = new Tresor();
		Aventurier aventurier = new Aventurier();

		// Création des ArrayList associés aux paramètres des classes
		ArrayList<Integer> montagneHorizArray = new ArrayList<>();
		ArrayList<Integer> montagneVerticArray = new ArrayList<>();

		ArrayList<Integer> tresorHorizArray = new ArrayList<>();
		ArrayList<Integer> tresorVerticArray = new ArrayList<>();
		ArrayList<Integer> nbTresorsArray = new ArrayList<>();

		ArrayList<String> prenomAventArray = new ArrayList<>();
		ArrayList<Integer> aventHorizArray = new ArrayList<>();
		ArrayList<Integer> aventVerticArray = new ArrayList<>();
		ArrayList<String> orientationAventArray = new ArrayList<>();

		// Récupération des données du fichier d'entrée pour réécriture tronquée dans le fichier de sortie
		System.out.println(" ");
		System.out.println("|||||||| DÉBUT ||||||||");
		int int_aventurier=0;
		for (String line : contentInputSplit) {
			// Réupération du type de ligne
			String typeLigne=String.valueOf(line.charAt(0));
			// Séparation des lignes du fichier d'entrée
			String[] contentLineSplit = line.split(" ");
			// Cas de la ligne 'Carte'
			if(typeLigne.equals("C")){
				// Données du fichier sur la carte
				carte.mapWidth = Integer.parseInt(contentLineSplit[2]);
				carte.mapHeight = Integer.parseInt(contentLineSplit[4]);
			}

			// Cas de la ligne 'Montagne'
			if(typeLigne.equals("M")){
				// Données du fichier pour chaque montagne
				montagne.montagneHoriz = Integer.parseInt(contentLineSplit[2]);
				montagneHorizArray.add(montagne.montagneHoriz);

				montagne.montagneVertic = Integer.parseInt(contentLineSplit[4]);
				montagneVerticArray.add(montagne.montagneVertic);
			}

			// Cas de la ligne 'Trésor'
			if(typeLigne.equals("T")){
				// Définition des données variables de sortie pour chaque trésor
				tresor.tresorHoriz = Integer.parseInt(contentLineSplit[2]);
				tresorHorizArray.add(tresor.tresorHoriz);

				tresor.tresorVertic = Integer.parseInt(contentLineSplit[4]);
				tresorVerticArray.add(tresor.tresorVertic);

				tresor.nbTresors = Integer.parseInt(contentLineSplit[6]);
				nbTresorsArray.add(tresor.nbTresors);
			}

			// Cas de la ligne 'Aventurier'
			if(typeLigne.equals("A")){
				// Définition des données variables de sortie pour chaque trésor
				aventurier.prenom = contentLineSplit[2];
				prenomAventArray.add(aventurier.prenom);

				aventurier.aventHoriz = Integer.parseInt(contentLineSplit[4]);
				aventHorizArray.add(aventurier.aventHoriz);

				aventurier.aventVertic = Integer.parseInt(contentLineSplit[6]);
				aventVerticArray.add(aventurier.aventVertic);

				aventurier.orientation = contentLineSplit[8];
				orientationAventArray.add(aventurier.orientation);

				aventurier.seqMvt = contentLineSplit[10];
			}
		}

		// Initialisation et remplissage des diverses cartes
			// Carte
			String[][] initCarte = new String[carte.mapHeight][carte.mapWidth];

			// Montagne
			for(int i = 0; i < montagneVerticArray.size(); i++) {
				initCarte[montagneVerticArray.get(i)][montagneHorizArray.get(i)]="M";
			}

			// Trésor
			int[][] initCarteNbTres = new int[carte.mapHeight][carte.mapWidth];

			for(int i = 0; i < tresorVerticArray.size(); i++) {
				initCarte[tresorVerticArray.get(i)][tresorHorizArray.get(i)]="T";

				initCarteNbTres[tresorVerticArray.get(i)][tresorHorizArray.get(i)]=nbTresorsArray.get(i);
			}

			// Aventurier
			String[][] initCarteNomAvent = new String[carte.mapHeight][carte.mapWidth];
			String[][] initOrientationNomAvent = new String[carte.mapHeight][carte.mapWidth];

			for(int i = 0; i < aventVerticArray.size(); i++) {
				initCarte[aventVerticArray.get(i)][aventHorizArray.get(i)]="A";

				initCarteNomAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=prenomAventArray.get(i);
				initOrientationNomAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=orientationAventArray.get(i);
			}

		/// ------ Affichage de diverses cartes au DÉBUT ------
			System.out.println("------ Cartes diverses au DÉBUT ------");

			// Carte globale
			System.out.println("**** CARTE GLOBALE 'DÉBUT' ****");
			System.out.println(Arrays.deepToString(initCarte).replace("],", "],\n")
					.replace("null", "•"));

			// Nombre de trésors
			System.out.println(" ");
			System.out.println("**** TRÉSORS 'DÉBUT' ****");
			System.out.println("** Nb de trésors DÉBUT **");
			System.out.println(Arrays.deepToString(initCarteNbTres).replace("],", "],\n")
					.replace("0", "•"));

			// Carte avec le nom des aventuriers
			System.out.println(" ");
			System.out.println("**** AVENTURIER 'DÉBUT' ****");
			System.out.println("** Carte avec noms aventuriers DÉBUT **");
			System.out.println(Arrays.deepToString(initCarteNomAvent).replace("],", "],\n")
					.replace("null", "•"));
			// Carte avec orientation des aventuriers
			System.out.println("** Carte avec orientation des aventuriers DÉBUT **");
			System.out.println(Arrays.deepToString(initOrientationNomAvent).replace("],", "],\n")
					.replace("null", "•"));

		// Récupération des données du fichier d'entrée pour réécriture tronquée dans le fichier de sortie
		System.out.println(" ");
		System.out.println("|||||||| FIN ||||||||");

		prenomAventArray.clear();
		aventHorizArray.clear();
		aventVerticArray.clear();
		orientationAventArray.clear();
		ArrayList<Integer> nbTresorsRamassesAventArray = new ArrayList<>();

		initCarteNomAvent = new String[carte.mapHeight][carte.mapWidth];
		initOrientationNomAvent = new String[carte.mapHeight][carte.mapWidth];
		int[][] finCarteNbTresorsRamassesAvent = new int[carte.mapHeight][carte.mapWidth];

		for (String line : contentInputSplit) {
			// Réupération du type de ligne
			String typeLigne=String.valueOf(line.charAt(0));
			// Séparation des lignes du fichier d'entrée
			String[] contentLineSplit = line.split(" ");
			// Cas de la ligne 'Carte'
			if(typeLigne.equals("C")){
				bufferedWriter.write(line + "\n");
			}

			// Cas de la ligne 'Montagne'
			if(typeLigne.equals("M")){
				bufferedWriter.write(line + "\n");
			}

			// Cas de la ligne 'Aventurier'
			if(typeLigne.equals("A")){
				// Définition des données variables de sortie pour chaque trésor
				aventurier.prenom = contentLineSplit[2];

				aventurier.aventHoriz = Integer.parseInt(contentLineSplit[4]);

				aventurier.aventVertic = Integer.parseInt(contentLineSplit[6]);

				aventurier.orientation = contentLineSplit[8];

				aventurier.nbTresorsRamassesAvent = 0;

				aventurier.seqMvt = contentLineSplit[10];
				char[] sequenceAventurierFullArray = aventurier.seqMvt.toCharArray();
                for (char c : sequenceAventurierFullArray) {
					String s = String.valueOf(c);
					if (s.equals("A")) {
                        switch (aventurier.orientation) {
                            case "S" -> {
								if ((aventurier.aventVertic + 1 <= carte.mapHeight - 1)
										&& !Objects.equals(initCarte[aventurier.aventVertic + 1][aventurier.aventHoriz], "M")
										&& !Objects.equals(initCarte[aventurier.aventVertic + 1][aventurier.aventHoriz], "A")) {
									if (initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]>0) {
										initCarte[aventurier.aventVertic][aventurier.aventHoriz]="T";
									} else {
										initCarte[aventurier.aventVertic][aventurier.aventHoriz]=null;
									}
									aventurier.aventVertic = aventurier.aventVertic + 1;
									if (initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz] > 0){
										aventurier.nbTresorsRamassesAvent=aventurier.nbTresorsRamassesAvent+1;
										initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]
												=initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]-1;
									}
									initCarte[aventurier.aventVertic][aventurier.aventHoriz]="A";
								}
							}
							case "N" -> {
								if ((aventurier.aventVertic -1 >= 0)
										&& !Objects.equals(initCarte[aventurier.aventVertic - 1][aventurier.aventHoriz], "M")
										&& !Objects.equals(initCarte[aventurier.aventVertic - 1][aventurier.aventHoriz], "A")) {
									if (initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]>0) {
										initCarte[aventurier.aventVertic][aventurier.aventHoriz]="T";
									} else {
										initCarte[aventurier.aventVertic][aventurier.aventHoriz]=null;
									}
									aventurier.aventVertic = aventurier.aventVertic - 1;
									if (initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz] > 0){
										aventurier.nbTresorsRamassesAvent=aventurier.nbTresorsRamassesAvent+1;
										initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]
												=initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]-1;
									}
									initCarte[aventurier.aventVertic][aventurier.aventHoriz]="A";
								}
							}
							case "E" -> {
								if ((aventurier.aventHoriz + 1 <= carte.mapWidth - 1)
										&& !Objects.equals(initCarte[aventurier.aventVertic][aventurier.aventHoriz + 1], "M")
										&& !Objects.equals(initCarte[aventurier.aventVertic][aventurier.aventHoriz + 1], "A")) {
									if (initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]>0) {
										initCarte[aventurier.aventVertic][aventurier.aventHoriz]="T";
									} else {
										initCarte[aventurier.aventVertic][aventurier.aventHoriz]=null;
									}
									aventurier.aventHoriz = aventurier.aventHoriz + 1;
									if (initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz] > 0){
										aventurier.nbTresorsRamassesAvent=aventurier.nbTresorsRamassesAvent+1;
										initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]
												=initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]-1;
									}
									initCarte[aventurier.aventVertic][aventurier.aventHoriz]="A";
								}
							}
							case "O" -> {
								if ((aventurier.aventHoriz - 1 >= 0)
										&& !Objects.equals(initCarte[aventurier.aventVertic][aventurier.aventHoriz - 1], "M")
										&& !Objects.equals(initCarte[aventurier.aventVertic][aventurier.aventHoriz - 1], "A")) {
									if (initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]>0) {
										initCarte[aventurier.aventVertic][aventurier.aventHoriz]="T";
									} else {
										initCarte[aventurier.aventVertic][aventurier.aventHoriz]=null;
									}
									aventurier.aventHoriz = aventurier.aventHoriz - 1;
									if (initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz] > 0){
										aventurier.nbTresorsRamassesAvent=aventurier.nbTresorsRamassesAvent+1;
										initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]
												=initCarteNbTres[aventurier.aventVertic][aventurier.aventHoriz]-1;
									}
									initCarte[aventurier.aventVertic][aventurier.aventHoriz]="A";
								}
							}
                         }
					}
					if (s.equals("D")) {
						switch (aventurier.orientation) {
							case "S" -> aventurier.orientation = "O";
							case "N" -> aventurier.orientation = "E";
							case "E" -> aventurier.orientation = "S";
							case "O" -> aventurier.orientation = "N";
						}
					}
					if (s.equals("G")) {
						switch (aventurier.orientation) {
							case "S" -> aventurier.orientation = "E";
							case "N" -> aventurier.orientation = "O";
							case "E" -> aventurier.orientation = "N";
							case "O" -> aventurier.orientation = "S";
						}
					}
                }
				prenomAventArray.add(aventurier.prenom);
				aventHorizArray.add(aventurier.aventHoriz);
				aventVerticArray.add(aventurier.aventVertic);
				orientationAventArray.add(aventurier.orientation);
				nbTresorsRamassesAventArray.add(aventurier.nbTresorsRamassesAvent);
			}
		}

		// Écriture du fichier de sortie pour chaque trésor
		for (int i = 0; i < tresorHorizArray.size(); i++) {
			if (initCarteNbTres[tresorVerticArray.get(i)][tresorHorizArray.get(i)] > 0) {
			bufferedWriter.write("T - " + tresorHorizArray.get(i) + " - " + tresorVerticArray.get(i)
					+ " - " + initCarteNbTres[tresorVerticArray.get(i)][tresorHorizArray.get(i)] + "\n");
			}
		}

		// Écriture du fichier de sortie pour chaque aventurier
		for (int i = 0; i < aventHorizArray.size(); i++) {
			bufferedWriter.write("A - " + prenomAventArray.get(i) + " - " + aventHorizArray.get(i)
					+ " - " + aventVerticArray.get(i) + " - " + orientationAventArray.get(i) + " - "
					+ nbTresorsRamassesAventArray.get(i));
			if (i < aventHorizArray.size() -1) {
				bufferedWriter.write("\n");
			}
		}

		// Fermeture du 'bufferedWriter'
		bufferedWriter.close ();

		// Remplissage des diverses cartes
			// Aventurier
			for(int i = 0; i < aventVerticArray.size(); i++) {
				initCarte[aventVerticArray.get(i)][aventHorizArray.get(i)]="A";

				initCarteNomAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=prenomAventArray.get(i);
				initOrientationNomAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=orientationAventArray.get(i);
				finCarteNbTresorsRamassesAvent[aventVerticArray.get(i)][aventHorizArray.get(i)]=nbTresorsRamassesAventArray.get(i);
			}

		/// ------ Affichage de diverses cartes à la FIN ------
			System.out.println("------ Cartes diverses à la FIN ------");

			// Carte globale
			System.out.println("**** CARTE GLOBALE 'FIN' ****");
			System.out.println(Arrays.deepToString(initCarte).replace("],", "],\n")
					.replace("null", "•"));

			// Nombre de trésors
			System.out.println(" ");
			System.out.println("**** TRÉSORS 'FIN' ****");
			System.out.println("** Nb de trésors FIN **");
			System.out.println(Arrays.deepToString(initCarteNbTres).replace("],", "],\n")
					.replace("0", "•"));

			// Carte avec le nom des aventuriers
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
			System.out.println(Arrays.deepToString(finCarteNbTresorsRamassesAvent).replace("],", "],\n")
					.replace("0", "•"));
	}
}