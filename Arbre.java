package arbre;

public class Arbre<K, V> {

	private Noeud<K, V> noeud;
	public static int ordre;


	public Arbre(int ordre) {
		this.noeud = new Noeud<>();
		Arbre.ordre = ordre;
	}

	//affiche l'arbre
	public String toString() {
		return noeud.rechercheRacine().toString();
	}

	//retourne la valeur attribuée à une clé
	public V recherche(K cleRecherchee) {
		V recherchee=(V)"0";
		try {
			recherchee = noeud.rechercheRacine().recherche(cleRecherchee);
		}
		catch(Exception e) {
			recherchee = (V)"Cette valeur n'est pas dans l'arbre.";
		}
		return recherchee;
	}

	//insère dans l'arbre
	public void inserer(K elem, V val) {
		noeud.rechercheRacine().insertArbre(elem, val);
	}

	//supprime la valeur
	public void supprimer(V val) {
		noeud.rechercheRacine().supprimer(val);
	}
}
