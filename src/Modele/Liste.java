package Modele;

import Vues.*;

import java.io.Serializable;
import java.util.*;

/**
 * Cette classe implémente une liste d'un tableau. Le {@link Modele.Tableau}
 * possède une liste de listes (maximum 500 listes par tableau).
 * Une liste est composée d'un identifiant, d'un titre et d'un contenu et peut
 * posséder au maximum 500 cartes.
 * 
 * @see Modele.Tableau
 * @see Modele.Carte
 * @author Olivier
 * @author Soan
 * @author Yannick
 * @author Mathis
 */

public class Liste implements Serializable {

	// ***********************************
	// ******* ATTRIBUTS *****************
	// ***********************************

	private static int compteur;
	private int identifiant;
	private Tableau tableauParent;
	private ArrayList<Carte> contenuListe;
	private String titre;
	private static final int MAX_CARTE = 500;
	private ConteneurListe vueListe = null;

	// ***********************************
	// ******* CONSTRUCTEUR *************
	// ***********************************

	/**
	 * Constructeur de la classe Liste. L'identifiant de la liste est auto
	 * incrémenté.
	 * 
	 * @param titre le titre de la liste.
	 */
	public Liste(String titre) {
		contenuListe = new ArrayList<Carte>(MAX_CARTE); // La liste possède une taille définie par la constante MAX_CARTE
		this.titre = titre;
		identifiant = compteur;
		compteur++;
	}

	/**
	 * Constructeur de la classe Liste. L'identifiant de la liste est auto
	 * incrémenté.
	 * Cette version comprend le tableau parent dans lequel la liste est créée.
	 * 
	 * @param titre   le titre de la liste.
	 * @param tableau le tableau parent de la liste.
	 */
	public Liste(String titre, Tableau tableau) {
		contenuListe = new ArrayList<Carte>(MAX_CARTE); // La liste possède une taille définie par la constante MAX_CARTE
		this.titre = titre;
		this.tableauParent = tableau;
		identifiant = compteur;
		compteur++;
	}

	/**
	 * Constructeur de la classe Liste. Ce constructeur est exclusivement utilisé
	 * pour créer une liste temporaire pour une Recherche.
	 * Voir
	 * {@link Controleur.ControleurBarreMenu#actionPerformed(java.awt.event.ActionEvent)}
	 * pour le cas traité.
	 * 
	 * @param titre       le titre de la liste.
	 * @param identifiant l'identifiant de la liste.
	 */
	public Liste(int identifiant, String titre) {
		contenuListe = new ArrayList<Carte>(MAX_CARTE); // La liste possède une taille définie par la constante MAX_CARTE
		this.titre = titre;
		this.identifiant = identifiant;
	}

	// ***********************************
	// ******* METHODES ******************
	// ***********************************

	/**
	 * Permet d'ajouter un objet de type {@link Carte} à la liste, dans la limite de
	 * 500 cartes par liste.
	 * 
	 * @param carte la carte à ajouter à la liste.
	 */
	public void ajouterCarte(Carte carte) {
		if (!contenuListe.contains(carte)) {
			if (contenuListe.size() < MAX_CARTE) { // Si la liste n'est pas encore pleine
				contenuListe.add(carte);
				carte.setListeParent(this);
			} else {
				System.out.println("La liste est pleine ! Impossible d'ajouter une carte supplémentaire.");
			}
		} else {
			System.out.println("La carte est déjà présente ! Impossible d'ajouter cette carte.");
		}
	}

	/**
	 * Permet de supprimer un objet de type {@link Carte} de la liste.
	 * 
	 * @param carte la carte à supprimer de la liste.
	 */
	public void supprimerCarte(Carte carte) {
		// On effectue un parcours séquentiel dans la liste des cartes pour supprimer la
		// carte voulue si elle est trouvée
		boolean trouve = false;
		int i = 0;
		while (i < contenuListe.size() && !trouve) {
			if (contenuListe.get(i) == carte) {
				carte.setListeParent(null);
				contenuListe.remove(i);
				trouve = true;
			} else {
				i++;
			}
		}
		// Si le booléen est faux à la fin de la liste, alors la carte n'a pas été
		// trouvée
		if (!trouve) {
			System.out.println("La carte n'existe pas dans la liste !");
		}
	}

	@Override
	public String toString() {
		return "Liste [identifiant=" + identifiant + ", titre=" + titre + ", contenuListe=" + contenuListe + "]";
	}

	// ***********************************
	// ******* GETTERS ET SETTERS ********
	// ***********************************

	/**
	 * Permet de récupérer l'identifiant de la liste.
	 * 
	 * @return l'identifiant de la liste.
	 */
	public int getIdentifiant() {
		return this.identifiant;
	}

	/**
	 * Permet de modifier l'identifiant de la liste.
	 * 
	 * @param identifiant le nouvel identifiant de la liste.
	 */
	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * Permet de récupérer le titre de la liste.
	 * 
	 * @return le titre de la liste.
	 */
	public String getTitre() {
		return this.titre;
	}

	/**
	 * Permet de modifier le titre de la liste.
	 * 
	 * @param titre le nouveau titre de la liste.
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * Permet de récupérer le contenu de la liste.
	 * 
	 * @return le contenu de la liste.
	 */
	public ArrayList<Carte> getContenuListe() {
		return contenuListe;
	}

	/**
	 * Permet de modifier le contenu de la liste.
	 * 
	 * @param contenuListe le nouveau contenu de la liste.
	 */
	public void setContenuListe(ArrayList<Carte> contenuListe) {
		this.contenuListe = contenuListe;
	}

	/**
	 * Permet de modifier le tableau parent de la liste.
	 * 
	 * @param tableau le nouveau tableau parent de la liste.
	 */
	public void setTableauParent(Tableau tableau) {
		this.tableauParent = tableau;
	}

	/**
	 * Permet de récupérer le tableau parent de la liste.
	 * 
	 * @return le tableau parent de la liste.
	 */
	public Tableau getTableauParent() {
		return this.tableauParent;
	}

	/**
	 * Permet de récupérer la vue de la liste.
	 * 
	 * @return la vue de la liste.
	 */
	public ConteneurListe getVueListe() {
		return vueListe;
	}

	/**
	 * Permet de modifier la vue de la liste.
	 * 
	 * @param vueListe la nouvelle vue de la liste.
	 */
	public void setVueListe(ConteneurListe vueListe) {
		this.vueListe = vueListe;
	}

}