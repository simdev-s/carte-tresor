package com.springdemo.cartetresor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.sum;

@SpringBootApplication
public class CarteTresorApplication {

	public static <Int> void main(String[] args) throws IOException {
		SpringApplication.run(CarteTresorApplication.class, args);

		// Fichier d'entrée
		Resource inputResource = new ClassPathResource("input.txt");
		File inputFile = inputResource.getFile();

		String inputContent = new String(Files.readAllBytes(inputFile.toPath()));
		String[] contentInputSplit = inputContent.split("\n");

		// Définition fichier de sortie
		Resource outputResource = new ClassPathResource("output.txt");
		File outputFile = outputResource.getFile();

		FileWriter outputFileWriter = new FileWriter(outputFile);
		BufferedWriter bufferedWriter = new BufferedWriter(outputFileWriter);

		// Initialisation des numéros de ligne 'Montagne', 'Trésor' et 'Aventurier'
		int numeroMontagne = 0;
		int numeroTresor = 0;
		int numeroAventurier = 0;

		// Itération sur fichier de sortie
		for (String line : contentInputSplit) {
			// Réupération du type de ligne
			String typeLigne=String.valueOf(line.charAt(0));
			// Séparation des lignes du fichier d'entrée
			String[] contentLineSplit = line.split(" ");
			// Cas de la ligne 'Carte'
			if(typeLigne.equals("C")){
				// Données du fichier sur la carte
				System.out.println("◉ CARTE          |" + " Nb cases en larg.  : " + contentLineSplit[2] + " |"
						+ " Nb cases en long.  : " + contentLineSplit[4]);
				int mapWidth = Integer.parseInt(contentLineSplit[2]);
				int mapLength = Integer.parseInt(contentLineSplit[4]);
				bufferedWriter.write(line + "\n");
				//int[][] initCarte = new int[parseInt(contentLineSplit[2])][parseInt(contentLineSplit[4])];
				//System.out.println(initCarte.length);
				//System.out.println(initCarte[0].length);
			}
			// Cas de la ligne 'Montagne'
			if(typeLigne.equals("M")){
				// Renseignement du numéro de la montagne
				numeroMontagne++;
				// Données du fichier pour chaque montagne
				System.out.println("◉ MONTA. " + numeroMontagne + "       | Pos. horiz. monta. : " + contentLineSplit[2] + " |"
						+ " Pos. verti. monta. : " + contentLineSplit[4]);

				bufferedWriter.write(line + "\n");
//				String[][] initCarte = new String[3][4];
//				initCarte[parseInt(contentLineSplit[2])][parseInt(contentLineSplit[4])]="M";
//				System.out.println(initCarte.length);
//				System.out.println(initCarte[0].length);
//				System.out.println(initCarte[1][1]);
//				System.out.println(initCarte[1][2]);
//				System.out.println(initCarte[2][2]);
			}
			// Cas de la ligne 'Trésor'
			if(typeLigne.equals("T")){
				// Renseignement du numéro du trésor
				numeroTresor++;
				// Affichage des données du fichier de départ pour chaque trésor
				System.out.println("◉ TRÉSOR " + numeroTresor + " début |" + " Pos. horiz. trésor : " + contentLineSplit[2] + " |"
						+ " Pos. verti. trésor : " + contentLineSplit[4] + " |" + " Nb trésors restants début : " + contentLineSplit[6]);
				/// Paramétrage des nouvelles données pour chaque trésor
				// Initialisation des données variables de sortie pour chaque trésor
				int nbTresorsRestants = parseInt(contentLineSplit[6]);
				String nbTresorsRestantsString = String.valueOf(nbTresorsRestants);
				// Définition des données variables de sortie pour chaque trésor
				// Affichage des données du fichier de sortie pour chaque trésor
				System.out.println("◉ TRÉSOR " + numeroTresor + " fin   |" + " Pos. horiz. trésor : " + contentLineSplit[2] + " |"
						+ " Pos. verti. trésor : " + contentLineSplit[4] + " |" + " Nb trésors restants fin   : " + nbTresorsRestants);
				// Écriture du fichier de sortie pour chaque trésor
				for (int i = 0; i < 6; i++) {
					bufferedWriter.write(contentLineSplit[i] + " ");
				}
				bufferedWriter.write(nbTresorsRestantsString);
				bufferedWriter.write("\n");
			}
			// Cas de la ligne 'Aventurier'
			if(typeLigne.equals("A")){
				// Renseignement du numéro de l'aventurier
				numeroAventurier++;
				// Affichage des données du fichier de départ pour chaque aventurier
				System.out.println("◉ AVENT. " + numeroAventurier + " début |" + " Nom aventurier : " +  contentLineSplit[2]
						+ " | Pos. horiz. avent. début : " + contentLineSplit[4] + " | Pos. verti. avent. "
						+ "début : " + contentLineSplit[6] + " | Orientation début : " + contentLineSplit[8]
						+ " | Séquence de mouvement full : " + contentLineSplit[10]);
				///  Paramétrage des nouvelles données pour chaque aventurier
				// Récupération de la séquence de mouvement pour chaque aventurier
				String sequenceAventurierFull = contentLineSplit[10];
				char[] sequenceAventurierFullArray = sequenceAventurierFull.toCharArray();
				int sequenceAventurierSize = sequenceAventurierFullArray.length;
				// Initialisation des données variables de sortie pour chaque aventurier
				int posHorizAvent = parseInt(contentLineSplit[4]);
				int posVerticAvent = parseInt(contentLineSplit[6]);
				String orientationAvent = contentLineSplit[8];

				int nbTresorsRamassesAvent = 0;
				// Définition des données variables de sortie pour chaque aventurier
				for(int i = 0; i < sequenceAventurierSize; i++) {
					posHorizAvent += Character.getNumericValue(sequenceAventurierFullArray [i]);
					posVerticAvent += Character.getNumericValue(sequenceAventurierFullArray [i]);
					nbTresorsRamassesAvent += ((posHorizAvent == 5 && posVerticAvent == 6)
							|| (posHorizAvent == 7 && posVerticAvent == 8)) ? 1:0;
					System.out.println("	➤ Avent. " + numeroAventurier + " inter |" + " Nom aventurier : " +  contentLineSplit[2]
							+ " | Pos. horiz. avent. " + sum(i,1) + " : " + posHorizAvent + " | Pos. verti. avent. "
							+ sum(i,1) + " : "  + posVerticAvent + " | Orientation " + sum(i,1) +  " : "
							+ orientationAvent + " | Séquence de mouvement now : " + sequenceAventurierFullArray [i]
							+ " | Nombre trésors rammassés : " + nbTresorsRamassesAvent);
				}
				// Affichage des données du fichier de sortie pour chaque aventurier
				System.out.println("◉ AVENT. " + numeroAventurier + " fin   |" + " Nom aventurier : " +  contentLineSplit[2]
						+ " | Pos. horiz. avent. fin   : " + posHorizAvent + " | Pos. verti. avent. "
						+ "fin   : " + posVerticAvent + " | Orientation fin   : " + orientationAvent
						+ " | Nombre trésors rammassés : " + nbTresorsRamassesAvent);
				// Écriture du fichier de sortie pour chaque aventurier
				for (int i = 0; i < 4; i++) {
					bufferedWriter.write(contentLineSplit[i] + " ");
				}
				bufferedWriter.write(posHorizAvent + " - " + posVerticAvent + " - " + orientationAvent + " - " + nbTresorsRamassesAvent);
				bufferedWriter.write("\n");
			}
		}
		bufferedWriter.close ();
	}
}