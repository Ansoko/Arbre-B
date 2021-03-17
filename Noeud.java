package arbre;
import java.util.ArrayList;

public class Noeud<K, V> {

	private ArrayList<K> cle; 
	/**
	 * pointeur regroupe  : les enfants (pour les noeuds intermédiaires)
	 * ou les valeurs liés aux clés (pour les noeuds feuilles)
	 */
	private ArrayList<V> pointeur;
	private Type type;
	public enum Type{
		racine, feuille, intermediaire;
	}
	private Noeud<K,V> parent;
	private Noeud <K,V> voisin; //le voisin de droite d'une feuille
	private int tauxRempl;

	public Noeud() {
		tauxRempl = 0;
		cle = new ArrayList<K>();
		pointeur = new ArrayList<V>();
		type = Type.feuille;
		voisin = null;
		parent = null;
	}

	/**
	 * Création d'un voisin
	 */
	public void newVoisin() {
		voisin = new Noeud<K,V>();
		voisin.type = type;
		if(parent==null) { //création du parent si inexistant
			parent = new Noeud<K, V>();
			parent.type = Type.intermediaire;
			parent.pointeur.add((V) this);
		}
		parent.pointeur.add((V) voisin);
		voisin.parent = parent;
	}

	
	public String toString() {

		String bloc = "";

		if(type != Type.feuille) {
			for (int i=0; i<cle.size(); i++) {
				bloc = bloc + getCle(i) + ", ";
			}
			bloc = bloc +"\n";

			for(int i=0; i<pointeur.size(); i++) {
				bloc = bloc + "> " + getPointeur(i).toString();

			}
		}
		else { //(type == Type.feuille)
			bloc = bloc + "-> ";
			for (int i=0; i<cle.size(); i++) {
				bloc = bloc + getCle(i) + "=" + getPointeur(i) + ", ";
			}
			bloc = bloc + "\n";
		}

		return bloc;

	}


	protected K getCle(int i) {
		return cle.get(i);
	}

	protected V getPointeur(int i) {
		return pointeur.get(i);
	}

	/**
	 * Insertion dans l'arbre
	 * @param key
	 * @param val
	 */
	protected void insertArbre(K key, V val) {
		rechercheCle(key).insert(key, val);
	}


	/**
	 * Insertion dans un noeud
	 * @param key
	 * @param val
	 */
	private void insert(K key, V val) {

		cle.add(key);
		pointeur.add(val);
		tauxRempl++;
		tri();

		if(tauxRempl>Arbre.ordre) { //le taux de remplissage maximal est dépassé
			if(type==Type.feuille) {
				separerFeuille();
			}else {
				separerNoeud();
			}
		}

	}


	/**
	 * Sépare en deux les éléments une feuille
	 */	
	private void separerFeuille() {

		//création du voisin
		if(voisin==null) {
			newVoisin();
		}else {	//on intercalle un nouveau voisin
			Noeud<K,V> voisin2 = new Noeud<K,V>();
			voisin2.voisin = voisin;
			voisin = voisin2;

			parent.pointeur.add((V) voisin);
			voisin.parent = parent;
		}

		int nbkey = tauxRempl/2;

		parent.cle.add(getCle(nbkey));
		parent.tauxRempl++;
		parent.tri();

		for(int i=nbkey; i<tauxRempl; tauxRempl--) {
			voisin.insert(getCle(nbkey), getPointeur(nbkey));
			cle.remove(nbkey);
			pointeur.remove(nbkey);
		}

		if(parent.pointeur.size()>(Arbre.ordre+1)) {
			parent.separerNoeud();
		}

	}


	/**
	 * Sépare en deux les éléments d'un noeud
	 */
	private void separerNoeud() {

		//création du voisin
		if(voisin==null) {
			newVoisin();
		}else {	//on intercalle un nouveau voisin
			Noeud<K,V> voisin2 = new Noeud<K,V>();
			voisin2.type = Type.intermediaire;
			voisin2.voisin = voisin;
			voisin = voisin2;

			parent.pointeur.add((V) voisin);
			voisin.parent = parent;

		}

		int nbkey = tauxRempl/2;

		parent.cle.add(getCle(nbkey));
		cle.remove(getCle(nbkey));
		parent.tauxRempl++;
		tauxRempl--;
		parent.tri();

		for(int i=nbkey; i<tauxRempl; tauxRempl--) {	
			voisin.insert(getCle(nbkey), getPointeur(nbkey+1));
			((Noeud<K, V>)getPointeur(nbkey+1)).parent = voisin;
			cle.remove(nbkey);
			pointeur.remove(nbkey+1);
		}
		voisin.pointeur.add(getPointeur(nbkey+1));
		((Noeud<K, V>)getPointeur(nbkey+1)).parent = voisin;
		pointeur.remove(nbkey+1);

		if(parent.pointeur.size()>(Arbre.ordre+1)) {
			parent.separerNoeud();
		}
	}


	private void tri() {

		if(type == Type.feuille) { //type feuille
			for(int i=tauxRempl-1; i>0; i--) {
				for(int j=0; j<i; j++) {
					if(((String) getCle(j)).matches("[0-9]+")) {
						if(Integer.parseInt(getCle(j+1).toString()) < Integer.parseInt(getCle(j).toString())) { //j+1 < j
							K tempK = getCle(j);
							cle.set(j, getCle(j+1));
							cle.set(j+1, tempK);
							V tempV = getPointeur(j);
							pointeur.set(j, getPointeur(j+1));
							pointeur.set(j+1, tempV);
						}
					}else {
						if(((String) getCle(j+1)).compareTo((String)getCle(j))<0) {    //j+1 < j 
							K tempK = getCle(j);
							cle.set(j, getCle(j+1));
							cle.set(j+1, tempK);
							V tempV = getPointeur(j);
							pointeur.set(j, getPointeur(j+1));
							pointeur.set(j+1, tempV);
						}
					}

				}
			}
		}else { //type racine ou intermédiaire
			for(int i=tauxRempl-1; i>0; i--) {
				for(int j=0; j<i; j++) {
					if(((String) getCle(j+1)).compareTo((String)getCle(j))<0) {    //j+1 < j 
						K tempK = getCle(j);
						cle.set(j, getCle(j+1));
						cle.set(j+1, tempK);
						V tempV = getPointeur(j+1);
						pointeur.set(j+1, getPointeur(j+2));
						pointeur.set(j+2, tempV);
					}
				}
			}
		}

	}


	/**
	 * retourne la valeur associée à une clé
	 * @param cleRecherchee
	 * @return valeur
	 */
	protected V recherche (K cleRecherchee) {
		return rechercheCle(cleRecherchee).getPointeur(rechercheCle(cleRecherchee).cle.indexOf(cleRecherchee));
	}


	/**
	 * Retourne la racine de l'arbre
	 * @return racine de l'arbre
	 */
	protected Noeud<K, V> rechercheRacine(){

		if(!(parent == null)) {
			if(type != Type.feuille) {
				type=Type.intermediaire;
			}
			return parent.rechercheRacine();
		}

		//attribution du type racine au plus haut noeud (sans parent)
		if(type != Type.feuille) {
			type=Type.racine;
		}
		return this;

	}

	
	/**
	 * Retourne le noeud où se trouve la clé cherchée ou où va s'insérer la nouvelle valeur
	 * @param cleRecherchee
	 * @return Noeud où va s'insérer la valeur ou où se trouve la valeur
	 */
	private Noeud<K,V> rechercheCle(K cleRecherchee){

		if(!(type.equals(Type.feuille))) {
			for(int i=0; i<tauxRempl; i++) {

				if(((String) getCle(i)).matches("[0-9]+")) {
					if(Integer.parseInt(getCle(i).toString()) > Integer.parseInt(cleRecherchee.toString())) { //cleNoeud > cleRecherchee
						return ((Noeud<K, V>) getPointeur(i)).rechercheCle(cleRecherchee);
					}
				}else {
					if((getCle(i).toString()).compareTo(cleRecherchee.toString())>0) { //cleNoeud > cleRecherchee
						return ((Noeud<K, V>) getPointeur(i)).rechercheCle(cleRecherchee);
					}
				}

			}

			//il s'agit du dernier enfant :
			return ((Noeud<K, V>) getPointeur(pointeur.size()-1)).rechercheCle(cleRecherchee);
		}

		return this;
	}

	
	/**
	 * Suppression d'une valeur dans l'arbre
	 * @param val
	 */
	protected void supprimer(V val) {
		Noeud<K, V> n = rechercheVal(val);
		if(n==null) {
			System.out.println("Cette valeur n'est pas dans l'arbre");
		}else {
			int position = n.parent.pointeur.indexOf(n);

			//on supprime la valeur et la clé dans la feuille
			n.cle.remove(n.pointeur.indexOf(val));
			n.tauxRempl--;
			n.pointeur.remove(n.pointeur.indexOf(val));
			if(n.cle.isEmpty()) { //si la feuille est vide
				n.parent.pointeur.remove(n);

				if(position != 0) {
					position--;
				}
				n.parent.cle.remove(position);
				n.parent.tauxRempl--;

				boolean pasAssez = true;
				int pos2 = position;
				for (int i=position; i<n.parent.pointeur.size(); i++) {
					if(((Noeud<K, V>)n.parent.getPointeur(i)).cle.size()>1) {
						pasAssez=false;
						pos2 = i;
						i=n.parent.pointeur.size();
					}
				}

				if(!pasAssez) { //on peut séparer les voisins
					((Noeud<K, V>) n.parent.getPointeur(pos2)).separerFeuille();
				}
				else if(n.parent.tauxRempl<1 && !(n.parent.parent==null)) { //sinon on supprime le noeud et on réinsère la dernière valeur dans l'arbre s'il existe toujours
					K elem = ((Noeud<K, V>) n.parent.getPointeur(0)).getCle(0);
					V valeur = ((Noeud<K, V>) n.parent.getPointeur(0)).getPointeur(0);
					n.parent.supprimerNoeud();
					rechercheRacine().insertArbre(elem, valeur);
				}
			}
		}
	}

	
	/**
	 * Suppression du noeud
	 */
	private void supprimerNoeud() {
		int position = parent.pointeur.indexOf(this);
		parent.pointeur.remove(position);
		if(position!=0) {
			position--;
		}
		parent.cle.remove(position);
		parent.tauxRempl--;

		if(parent.tauxRempl<1 && !(parent.parent==null)) {
			parent.supprimerNoeud();

		}else if(parent.tauxRempl<1 && parent.parent==null) {
			((Noeud<K, V>) parent.getPointeur(0)).type=Type.racine;
			((Noeud<K, V>) parent.getPointeur(0)).parent=null;
		}
	}


	/**
	 * Retourne le noeud où se trouve la valeur
	 * @param valRecherchee
	 */
	private Noeud<K, V> rechercheVal(V valRecherchee) {
		if(!(type.equals(Type.feuille))) {
			return ((Noeud<K, V>) getPointeur(0)).rechercheVal(valRecherchee);
		}

		if(pointeur.contains(valRecherchee)) {
			return this;
		}else if(!(voisin==null)) {
			return voisin.rechercheVal(valRecherchee);
		}

		return null;
	}

}
