Projet Arbres B+ [Anne-Sophie Koch L3 MIAGE 2019]

	Structure : 
"Arbre" est composé de :
* noeud (type Noeud)
* ordre (static de type int)

"Noeud" est composé de :
* cle (type ArrayList<K>)
* pointeur (type ArrayList<V>) : il peut contenir des noeuds ou des valeurs selon le type de noeud
* type (type Type) : peut prendre comme valeur "racine", "intermediaire" ou "feuille"
* parent (type Noeud)
* voisin (type Noeud) : pointe sur le voisin de droite
* tauxRempl (type int) : taux de remplissage du noeud


	Stratégie pour l'insertion :
La séparation s'effectue en 2/3 pour les noeuds feuilles et les noeuds intermédiaires.

	Stratégie pour la suppression :
Lorsqu'il n'y a plus qu'il n'y a plus de valeur dans la feuille, le voisin de gauche est séparé pour remplir la feuille.
S'il n'y a pas de voisin de gauche ou qu'il ne peut pas être séparé, alors on prend le voisin de droite.
S'il n'a plus de voisin, alors on supprime le noeud intermédiaire et on réinsère dans l'arbre la valeur restante.


	Fonctionnalités :
Ce qui fonctionne bien :
- l'insertion
- la suppression
- la recherche
- le choix de l'arbre et le choix de l'action dans le main

Ce qui fonctionne mal :
- l'affichage de l'arbre : tout les noeuds intermédiaires, quelque soit leur niveau, s'affichent les uns en dessous des autres.

Ce qui n'est pas implémenté :
- l'équilibrage de l'arbre après la suppression.
