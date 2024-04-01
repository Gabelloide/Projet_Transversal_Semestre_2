package Modele;

import java.io.Serializable;
// Classes pour la gestion des Dates
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Cette classe implémente un commentaire d'une carte.
 * Le commentaire est composé d'un identifiant, d'un contenu, d'un utilisateur
 * et d'une date de publication.
 * 
 * @see Modele.Carte
 * @see Modele.Utilisateur
 * @author Olivier
 * @author Soan
 * @author Yannick
 * @author Mathis
 */

public class Commentaire implements Serializable {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private int identifiant;
	private static int compteur;
	private String contenu;
	private Utilisateur utilisateur;
	private LocalDateTime date;

	// ***********************************
	// ******** CONSTRUCTEUR *************
	// ***********************************

	/**
	 * Constructeur de la classe Commentaire. L'identifiant du commentaire est auto
	 * incrémenté.
	 * 
	 * @param contenu     le contenu du commentaire.
	 * @param utilisateur l'utilisateur qui a écrit le commentaire.
	 */
	public Commentaire(String contenu, Utilisateur utilisateur) {
		identifiant = compteur;
		compteur++;
		this.utilisateur = utilisateur;
		this.contenu = contenu;
		this.date = LocalDateTime.now(); // Le commentaire est publié à la date de création de l'objet.
	}

	// ***********************************
	// ******* GETTERS & SETTERS *********
	// ***********************************

	/**
	 * Permet de modifier le contenu du commentaire.
	 * 
	 * @param nvContenu le nouveau contenu du commentaire.
	 */
	public void setContenu(String nvContenu) {
		contenu = nvContenu;
	}

	/**
	 * Permet de récupérer le contenu du commentaire.
	 * 
	 * @return le contenu du commentaire.
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * Permet de récupérer la date de publication du commentaire.
	 * 
	 * @return la date de publication du commentaire.
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Permet de récupérer l'auteur du commentaire.
	 * 
	 * @return l'utilisateur qui a écrit le commentaire.
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * La méthode toString() permet de récupérer une représentation textuelle d'un
	 * commentaire.
	 * Elle formate la date de publication du commentaire au format "dd/MM/yyyy".
	 */
	public String toString() {
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return "Commentaire [identifiant=" + identifiant + ", contenu=" + contenu + ", Utilisateur=" + utilisateur
				+ ", date=" + date.format(datePattern) + "]";
	}

}