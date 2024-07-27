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

@SpringBootApplication
public class CarteTresorApplication {

	public static void main(String[] args) throws IOException {
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
				System.out.println("◉ CARTE          |" + " Nb cases en larg.  : " + contentLineSplit[2] + " |"
						+ " Nb cases en long.  : " + contentLineSplit[4]);
				bufferedWriter.write(line + "\n");
			}
			// Cas de la ligne 'Montagne'
			if(typeLigne.equals("M")){
				// Renseignement du numéro de la montagne
				numeroMontagne++;
				System.out.println("◉ MONTAGNE " + numeroMontagne + "     | Pos. horiz. monta. : " + contentLineSplit[2] + " |"
						+ " Pos. verti. monta. : " + contentLineSplit[4]);
				bufferedWriter.write(line + "\n");
			}
			// Cas de la ligne 'Trésor'
			if(typeLigne.equals("T")){
				// Renseignement du numéro du trésor
				numeroTresor++;
				// Données du fichier de départ sur les trésors
				System.out.println("◉ TRÉSOR " + numeroTresor + " début |" + " Pos. horiz. trésor : " + contentLineSplit[2] + " |"
						+ " Pos. verti. trésor : " + contentLineSplit[4] + " |" + " Nb trésors restants début : " + contentLineSplit[6]);

				// Paramétrage des nouvelles données pour les trésors
				// Données du fichier de sortie sur les trésors
				System.out.println("◉ TRÉSOR " + numeroTresor + " fin   |" + " Pos. horiz. trésor : " + contentLineSplit[2] + " |"
						+ " Pos. verti. trésor : " + contentLineSplit[4] + " |" + " Nb trésors restants fin   : " + "<< ? >>");
				// Écriture du fichier de sortie sur les trésors
				for (int i = 0; i < 6; i++) {
					bufferedWriter.write(contentLineSplit[i] + " ");
				}
				bufferedWriter.write("?");
				bufferedWriter.write("\n");
			}
			// Cas de la ligne 'Aventurier'
			if(typeLigne.equals("A")){
				// Renseignement du numéro de l'aventurier
				numeroAventurier++;
				// Données du fichier de départ sur les aventuriers
				System.out.println("◉ AVENT. " + numeroAventurier + " début |" + " Nom aventurier : " +  contentLineSplit[2]
						+ " | Pos. horiz. avent. début : " + contentLineSplit[4] + " | Pos. verti. avent. "
						+ "début : " + contentLineSplit[6] + " | Orientation début : " + contentLineSplit[8]
						+ " | Séquence de mouvement    : " + contentLineSplit[10]);

				//  Paramétrage des nouvelles données pour les aventuriers
				int num1 = Integer.parseInt(contentLineSplit[4]) + 1;
				int num2 = Integer.parseInt(contentLineSplit[6]) + 2;
				// Données du fichier de sortie sur les aventuriers
				System.out.println("◉ AVENT. " + numeroAventurier + " fin   |" + " Nom aventurier : " +  contentLineSplit[2]
						+ " | Pos. horiz. avent. fin   : " + "<< ? >>" + " | Pos. verti. avent. "
						+ "fin   : " + "<< ? >>" + " | Orientation fin   : " + "<< ? >>"
						+ " | Nombre trésors rammassés : " + "<< ? >>");
				// Écriture du fichier de sortie sur les aventuriers
				for (int i = 0; i < 4; i++) {
					bufferedWriter.write(contentLineSplit[i] + " ");
				}
				bufferedWriter.write("? - ? - ? - ?");
				bufferedWriter.write("\n");
			}
		}
		bufferedWriter.close ();
	}
}