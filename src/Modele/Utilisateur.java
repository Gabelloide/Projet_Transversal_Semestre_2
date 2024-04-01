package Modele;

import java.io.Serializable;
import java.util.*;

/**
 * Implémente un utilisateur de l'application.
 * L'utilisateur est identifié par un identifiant (auto incrémenté), un mail, un
 * nom, un prénom et un pseudo.
 * C'est le mail qui est réellement déterminant dans la base de données, pour
 * différencier un utilisateur d'un autre.
 * Le pseudo, nom et prénom sont des informations facultatives.
 * 
 * Voir {@link Controleur.Login} pour plus d'informations sur la connexion d'un
 * utilisateur.
 * 
 * @see Controleur.Login
 * 
 * @author Olivier
 * @author Soan
 * @author Yannick
 * @author Mathis
 */

public class Utilisateur implements Serializable {

	// ***********************************
	// ******* ATTRIBUTS *****************
	// ***********************************

	private static int compteur;
	/**
	 * Les tableaux où l'utilisateur est présent. Cette liste est initialement null
	 */
	private ArrayList<Tableau> tableauxUtilisateur;
	private int identifiant;
	private String mail;
	private String nom;
	private String prenom;
	private String pseudo;

	// ***********************************
	// ******* CONSTRUCTEUR *************
	// ***********************************

	/**
	 * Constructeur de la classe Utilisateur. L'identifiant de l'utilisateur est
	 * auto incrémenté.
	 * 
	 * @param mail   l'adresse mail de l'utilisateur.
	 * @param nom    le nom de l'utilisateur.
	 * @param prenom le prénom de l'utilisateur.
	 * @param pseudo le pseudo de l'utilisateur.
	 */
	public Utilisateur(String mail, String nom, String prenom, String pseudo) {
		identifiant = compteur;
		compteur++;
		this.mail = mail;
		this.nom = nom;
		this.prenom = prenom;
		this.pseudo = pseudo;
		tableauxUtilisateur = new ArrayList<Tableau>();
	}

	// ***********************************
	// ******* METHODES ******************
	// ***********************************

	/**
	 * Ajoute un tableau à la liste des tableaux de l'utilisateur.
	 * 
	 * @param tab le tableau à ajouter.
	 */
	public void ajouterTab(Tableau tab) {
		if (!tableauxUtilisateur.contains(tab)) { // Si ce tableau n'a pas été trouvé dans ceux de l'utilisateur
			tableauxUtilisateur.add(tab);
		} else { // Sinon impossible, trouvé donc tableau déjà présent
			System.out.println("Tableau déjà présent");
		}
	}

	/**
	 * Supprime un tableau de la liste des tableaux de l'utilisateur.
	 * 
	 * @param tab le tableau à supprimer.
	 */
	public void supprimerTab(Tableau tab) {
		if (tableauxUtilisateur.contains(tab)) {
			tableauxUtilisateur.remove(tab);
		} else {
			System.out.println("L'utilisateur ne possède pas ce tableau !");
		}
	}

	/**
	 * Effectue la "création" d'un tableau : l'utilisateur se voit affecté un
	 * tableau dont il est directement le propriétaire
	 * 
	 * @param tab le tableau à créer.
	 */
	public void creerTableau(Tableau tab) {
		tab.ajouterUtilisateur(this, 0); // On affecte le tableau "créé" à l'utilisateur cible en tant que propriétaire
		this.ajouterTab(tab); // On appelle la méthode d'ajout de tableau
	}

	// ***********************************
	// ******* GETTERS ET SETTERS ********
	// ***********************************

	/**
	 * Renvoie l'identifiant de l'utilisateur.
	 * 
	 * @return l'identifiant de l'utilisateur.
	 */
	public int getIdentifiant() {
		return this.identifiant;
	}

	/**
	 * Modifie l'identifiant de l'utilisateur.
	 * 
	 * @param identifiant le nouvel identifiant de l'utilisateur.
	 */
	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * Renvoie le mail de l'utilisateur.
	 * 
	 * @return le mail de l'utilisateur.
	 */
	public String getMail() {
		return this.mail;
	}

	/**
	 * Modifie le mail de l'utilisateur.
	 * 
	 * @param mail le nouveau mail de l'utilisateur.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Renvoie le nom de l'utilisateur.
	 * 
	 * @return le nom de l'utilisateur.
	 */
	public String getNom() {
		return this.nom;
	}

	// modifie le nom
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Renvoie le prénom de l'utilisateur.
	 * 
	 * @return le prénom de l'utilisateur.
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * Modifie le prénom de l'utilisateur.
	 * 
	 * @param prenom le nouveau prénom de l'utilisateur.
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Renvoie le pseudo de l'utilisateur.
	 * 
	 * @return le pseudo de l'utilisateur.
	 */
	public String getPseudo() {
		return this.pseudo;
	}

	/**
	 * Modifie le pseudo de l'utilisateur.
	 * 
	 * @param pseudo le nouveau pseudo de l'utilisateur.
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Renvoie la liste des tableaux de l'utilisateur.
	 * 
	 * @return la liste des tableaux de l'utilisateur.
	 */
	public ArrayList<Tableau> getTableauxUtilisateur() {
		return this.tableauxUtilisateur;
	}

	/**
	 * Modifie la liste des tableaux de l'utilisateur.
	 * 
	 * @param tableauxUtilisateur la nouvelle liste des tableaux de l'utilisateur.
	 */
	public void setTableauxUtilisateur(ArrayList<Tableau> tableauxUtilisateur) {
		this.tableauxUtilisateur = tableauxUtilisateur;
	}

	/**
	 * Renvoie le mail de l'utilisateur.
	 * 
	 * @return le mail de l'utilisateur.
	 */
	public String getEmail() {
		return this.mail;
	}

	// ***********************************
	// ********* AFFICHAGES **************
	// ***********************************
	@Override
	public String toString() {
		return "Utilisateur [" + "identifiant=" + identifiant
				+ ", nom=" + nom
				+ ", prenom=" + prenom
				+ ", mail=" + mail
				+ "]";
	}

	/**
	 * Affiche dans la console l'ensemble des tableaux d'un utilisateur ciblé, avec
	 * son rôle dans chaque tableau.
	 */
	public void afficherTableaux() {
		// Affiche dans la console l'ensemble des tableaux d'un utilisateur ciblé.
		for (Tableau t : tableauxUtilisateur) {
			boolean trouve = false;
			int i = 0;
			while (!trouve && i < t.getMembres().size()) {
				if (t.getMembres().get(i) == this) {
					trouve = true;
				} else {
					i++;
				}
			}
			// A ce stade, i est l'indice du membre dans la liste des membres du tableau (et
			// aussi dans la liste des permissions)

			String permissionsUtilisateur = Tableau.permissionsDispo[t.getPermissions().get(i)];

			System.out.println("Identifiant du tableau : " + t.getIdentifiant() + ", nom du tableau : " + t.getTitre()
					+ ", rôle : " + permissionsUtilisateur);
		}
	}

}