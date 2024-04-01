package Modele;

import java.awt.Color;
import java.io.Serializable;
import java.util.*;

/**
 * Cette classe implémente un tableau. Un tableau est composé d'un identifiant,
 * d'un titre et d'un contenu.
 * Un tableau peut posséder au maximum 500 objets de type {@link Liste}.
 * Un tableau possède un propriétaire et des membres de type
 * {@link Utilisateur}. Le propriétaire est celui qui a créé le tableau.
 * Le tableau possède également une couleur.
 * Il existe trois permissions différentes pour les membres d'un tableau :
 * propriétaire, administrateur et lecture seule.
 * 
 * @see Modele.Liste
 * @see Modele.Utilisateur
 * @see Modele.Carte
 * @author Olivier
 * @author Soan
 * @author Yannick
 * @author Mathis
 */

public class Tableau implements Serializable {

	// ***********************************
	// ******* ATTRIBUTS *****************
	// ***********************************

	private static int compteur;
	private ArrayList<Liste> contenuTableau;
	private int identifiant;
	private String titre;
	private static final int MAX_LISTE = 500;
	private Utilisateur proprietaire;
	public final static String[] permissionsDispo = { "Propriétaire", "Administrateur", "Lecture seule" };
	private ArrayList<Utilisateur> membres;
	/**
	 * la permission du membre à l'indice i correspond à l'indice i dans
	 * 'permissionsDispo', et l'utilisateur à l'indice i a pour permission la perm à
	 * l'indice i de 'permissions'
	 */
	private ArrayList<Integer> permissions;
	private Color couleur = Color.WHITE; // La couleur du tableau, blanc par défaut

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur sans propriétaire de la classe Tableau. L'identifiant du tableau
	 * est auto incrémenté.
	 * 
	 * @param titreTab le titre du tableau.
	 */
	public Tableau(String titreTab) {
		contenuTableau = new ArrayList<Liste>(MAX_LISTE); // Le tableau possède une taille définie par la constante
		// MAX_LISTE
		identifiant = compteur;
		compteur++;
		titre = titreTab;
		membres = new ArrayList<Utilisateur>();
		permissions = new ArrayList<Integer>();
	}

	/**
	 * Constructeur avec propriétaire de la classe Tableau. L'identifiant du tableau
	 * est auto incrémenté.
	 * 
	 * @param titreTab     le titre du tableau.
	 * @param proprietaire le propriétaire du tableau.
	 */
	public Tableau(String titreTab, Utilisateur proprietaire) {
		contenuTableau = new ArrayList<Liste>(MAX_LISTE); // Le tableau possède une taille définie par la constante
		// MAX_LISTE
		identifiant = compteur;
		compteur++;
		titre = titreTab;
		membres = new ArrayList<Utilisateur>();

		permissions = new ArrayList<Integer>();
		this.ajouterUtilisateur(proprietaire, 0); // On ajoute l'utilisateur en tant que propriétaire (permission n°0)
		// Cela ajoutera également le tableau construit à la liste des tableaux possédés
		// par l'utilisateur passé en paramètre du constructeur.
	}

	// ***********************************
	// ******* METHODES ******************
	// ***********************************

	/**
	 * Permet d'ajouter une instance de la classe Liste au tableau, dans la limite
	 * de 500 listes.
	 * 
	 * @param liste la liste à ajouter.
	 */
	public void ajouterListe(Liste liste) {
		if (!contenuTableau.contains(liste)) {
			if (contenuTableau.size() < MAX_LISTE) { // Si le tableau n'est pas encore plein
				contenuTableau.add(liste);
				liste.setTableauParent(this);
			} else {
				System.out.println("Le tableau est plein ! Impossible d'ajouter une liste supplémentaire.");
			}
		} else {
			System.out.println("La liste est déjà présente ! Impossible d'ajouter cette liste.");
		}
	}

	/**
	 * Permet de supprimer une instance de la classe Liste du tableau.
	 * 
	 * @param liste
	 */
	public void supprimerListe(Liste liste) {
		// On effectue un parcours séquentiel dans la liste des listes pour supprimer la
		// liste voulue si elle est trouvée
		boolean trouve = false;
		int i = 0;
		while (i < contenuTableau.size() && !trouve) {
			if (contenuTableau.get(i) == liste) {
				liste.setTableauParent(null);
				contenuTableau.remove(i);
				trouve = true;
			} else {
				i++;
			}
		}
		// Si le booléen est faux à la fin de la liste, alors la liste n'a pas été
		// trouvée
		if (!trouve) {
			System.out.println("La liste n'existe pas dans le tableau !");
		}
	}

	/**
	 * Permet d'ajouter un utilisateur au tableau. L'utilisateur ajouté aura la
	 * permission passée en paramètre.
	 * 
	 * @param user       l'utilisateur à ajouter.
	 * @param permission la permission de l'utilisateur.
	 */
	public void ajouterUtilisateur(Utilisateur user, int permission) {
		if (permission >= 0 && permission < 3) // Il n'y a que 3 permissions possibles
		{
			if (!membres.contains(user)) {
				membres.add(user); // Si l'utilisateur n'est pas encore membre, l'ajouter
				permissions.add(permission); // On ajoute la permission de l'utilisateur (la vérification se fera dans
																			// setPerm)
			} else {
				System.out.println("L'utilisateur est déjà dans ce tableau."); // Sinon impossible
			}
			setPerm(user, permission); // Vérifier les permissions de l'utilisateur à ajouter grâce à la procédure
																	// setPerm.
			user.ajouterTab(this); // Le tableau est directement affecté aux tableaux de l'utilisateur cible
		} else {
			System.out.println("Permission non valide");
		}
	}

	/**
	 * Permet de supprimer l'accès d'un utilisateur au tableau.
	 * 
	 * @param user l'utilisateur à supprimer.
	 */
	public void supprimerUtilisateur(Utilisateur user) {
		if (user == proprietaire) {
			System.out.println("Impossible de supprimer le proprietaire du tableau");
		} else {
			boolean trouve = false;
			int i = 0;
			while (!trouve && i < membres.size()) {
				if (membres.get(i) == user) {
					trouve = true;
				} else {
					i++;
				}
			}
			if (trouve) {
				user.supprimerTab(this); // On retire le tableau dans la liste des tableaux de l'utilisateur cible
				membres.remove(i); // On retire le membre de la liste des membres du tableau
				permissions.remove(i); // On lui retire sa permission associée
			} else {
				System.out.println("Utilisateur non present dans le tableau");
			}
		}
	}

	/**
	 * Algorithme de recherche pour récupérer la position d'un utilisateur dans la
	 * liste des utilisateurs du tableau.
	 * 
	 * @param user l'utilisateur dont on veut récupérer la position.
	 * @return la position de l'utilisateur dans la liste des utilisateurs du
	 *         tableau. (-1 si l'utilisateur n'est pas dans la liste)
	 */
	public int getIndexUtilisateur(Utilisateur user) {
		boolean trouve = false;
		int i = 0;
		while (!trouve && i < membres.size()) {
			if (membres.get(i) == user) {
				trouve = true;
			} else {
				i++;
			}
		}
		if (!trouve) {
			i = -1;
		}
		return i;
	}

	/**
	 * Cette méthode permet de modifier la permission d'un utilisateur dans le
	 * tableau.
	 * Si l'utilisateur est propriétaire, il peut transmettre sa propriété à un
	 * autre utilisateur et devenir administrateur
	 * Sinon, le fonctionnement habituel est de modifier la permission de
	 * l'utilisateur en changeant la valeur en un entier présent dans le tableau des
	 * permissions
	 * 
	 * @param user       : utilisateur dont on veut modifier la permission
	 * @param permission : nouvelle permission de l'utilisateur
	 */
	public void setPerm(Utilisateur user, int permission) {

		if (permission == 0 && proprietaire != null) // Si la permission est 'Propriétaire' mais que le tableau en a déjà un
		// propriétaire
		{
			// Recherche de l'indice i de l'ancien propriétaire
			boolean trouve = false;
			int i = 0;
			while (!trouve && i < permissions.size()) {
				if (permissions.get(i) == 0)
					trouve = true;
				else
					i++;
			}
			if (trouve) // Si le proriétaire est trouvé
			{
				permissions.set(i, 1); // La permission de l'ancien propriétaire est située à l'indice i. Il devient
				// Administrateur.
				i = 0;
				trouve = false;
				while (!trouve && i < membres.size()) {
					if (membres.get(i) == user) { // Recherche du nouveau propriétaire dans la liste membres
						trouve = true; // i = indice du nouveau propriétaire
					} else
						i++;
				}
				permissions.set(i, 0); // modifie enfin la permission pour le nouveau propriétaire
				proprietaire = user; // L'utilisateur courant devient le propriétaire
			}
		} else if (permission == 0 && proprietaire == null) { // S'il n'y a pas encore de propriétaire et qu'on souhaite en
			// ajouter un
			proprietaire = user; // L'utilisateur courant devient le propriétaire
		} else if (permission > 0 && permission < 3) { // Si la personne souhaitée ne sera pas propriétaire
			boolean trouve = false; // Recherche de l'indice i de l'utilisateur
			int i = 0;
			while (!trouve && i < membres.size()) {
				if (membres.get(i) == user) {
					trouve = true;
				} else {
					i++;
				}

			}
			if (trouve) {
				permissions.set(i, permission); // sensible a la casse
			}

		}
	}

	// ***********************************
	// ******* GETTERS ET SETTERS ********
	// ***********************************

	public ArrayList<Liste> getContenuTableau() {
		return contenuTableau;
	}

	public void setContenuTableau(ArrayList<Liste> contenuTableau) {
		this.contenuTableau = contenuTableau;
	}

	public int getIdentifiant() {
		return this.identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	public Color getCouleur() {
		return couleur;
	}

	public ArrayList<Utilisateur> getMembres() {
		return membres;
	}

	public ArrayList<Integer> getPermissions() {
		return permissions;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	// ***********************************
	// ********* AFFICHAGES **************
	// ***********************************

	@Override
	public String toString() {
		return "Tableau [" + "identifiant=" + identifiant
				+ ", titre=" + titre
				+ ", propriétaire=" + proprietaire
				+ ", contenuTableau=" + contenuTableau
				+ ", membres=" + membres
				+ ",permissions=" + permissions
				+ "]";
	}

}
