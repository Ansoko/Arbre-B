/**
 * 
 * @author Anne-Sophie Koch
 *
 */
package arbre;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		//ordre de l'arbre
		final int ordre = 3;

		Arbre<String, String> arbre = new Arbre<String, String>(ordre);	
		Arbre<String, String> arbre2 = new Arbre<String, String>(ordre);

		// Ouvrir le fichier txt
		FileInputStream fstream = new FileInputStream("all.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		String[] line;
		while ((strLine = br.readLine()) != null)   {
			line = strLine.split("\\|");
			arbre.inserer(line[1], line[0]);
			arbre2.inserer(line[0], line[1]);
		}
		fstream.close();

		Scanner sc = new Scanner(System.in);
		boolean close = false;
		while(!close) {
			System.out.println("Sur quel arbre voulez vous travailler ?"
					+ "\n (1) Arbre dont les clés sont des âges"
					+ "\n (2) Arbre dont les clés sont des prénoms"
					+ "\n (*) Quitter");			
			String str = sc.nextLine();			
			switch(str) {
			case "1":
				action(arbre);
				break;
			case "2":
				action(arbre2);
				break;
			default:
				close=true;
			}
		}

	}

	static public <K,V> void action(Arbre<K,V> arbre) {
		boolean close = false;
		Scanner sc = new Scanner(System.in);
		while(!close) {
		System.out.println("Que voulez vous faire ?"
				+ "\n (1) Afficher l'arbre"
				+ "\n (2) Ajouter une valeur"
				+ "\n (3) Supprimer une valeur"
				+ "\n (4) Rechercher une valeur"
				+ "\n (*) Retour à la sélection des arbres");			
		String str = sc.nextLine();			

		switch(str) {
		case "1":
			System.out.println(arbre.toString());
			break;
		case "2": //ajout
			System.out.print("Valeur à ajouter : ");
			str = sc.nextLine();
			System.out.print("Clé à ajouter : ");
			String cle = sc.nextLine();
			arbre.inserer((K)cle, (V)str);
			break;
		case "3": //suppression
			System.out.print("Valeur à supprimer : ");
			str = sc.nextLine();
			arbre.supprimer((V)str);
			break;
		case "4": //recherche
			System.out.print("Clé : ");
			str = sc.nextLine();
			System.out.println("La valeur associée est "+arbre.recherche((K)str));
			break;
		default:
			close=true;
		}
		}
	}

}
