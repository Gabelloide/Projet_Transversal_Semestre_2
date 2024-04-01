package Modele;

import java.io.*;
import java.util.ArrayList;
import Controleur.Login;

/**
 * L'instance principale du projet. Cette classe est le point d'entrée de
 * l'application.
 * Elle est composée d'une liste d'utilisateurs, d'un utilisateur actuel et d'un
 * tableau actuel.
 * La liste d'utilisateurs est une liste de {@link Modele.Utilisateur}, utilisée
 * pour le stockage de ces derniers pour pouvoir les sauvegarder.
 * 
 * @see Modele.Utilisateur
 * @author Olivier
 */

public class TrelloLitePlus implements Serializable {

	// ******************************************
	// **************** ATTRIBUTS ***************
	// ******************************************

	/** Utilisateurs de l'application */
	private ArrayList<Utilisateur> utilisateurs;

	/**
	 * Utilisateur actuel
	 * Cet attribut sera affecté à l'utilisateur qui se connecte par le biais du
	 * formulaire {@link Login}
	 */
	private Utilisateur utilisateurActuel;

	/** Pour pouvoir récupérer le tableau que l'utilisateur consulte actuellement */
	private Tableau tableauActuel;


	// ******************************************
	// ************** CONSTRUCTEUR **************
	// ******************************************

	/**
	 * Le constructeur de l'application se contente d'initialiser la liste des
	 * utilisateurs de l'application (elle sera amenée à être remplie si une
	 * sauvegarde est récupérée).
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public TrelloLitePlus() throws IOException, ClassNotFoundException, InterruptedException {
		this.utilisateurs = new ArrayList<Utilisateur>(); // On initialise la liste des utilisateurs de l'application
	}
	// ******************************************
	// **************** METHODES ****************
	// ******************************************

	/**
	 * Permet de débuter le fonctionnement de l'application. On commence par appeler
	 * le formulaire de connexion {@link Login}.
	 * Celui-ci va ensuite appeler le reste de l'application.
	 */
	public void lancerApplication() {
		// Formulaire de connexion
		new Login(this);
	}

	/**
	 * Permet d'enregistrer la sauvegarde de l'application sur le disque dur.
	 * On sauvegarde la liste des utilisateurs de l'application, à savoir l'attribut
	 * {@link TrelloLitePlus#utilisateurs}.
	 * 
	 * @param nomFichier Le nom du fichier dans lequel on souhaite enregistrer la
	 *                   sauvegarde
	 * @throws IOException
	 */
	public void enregistrement(String nomFichier) throws IOException {

		System.out.println("Tentative d'enregistrement de la sauvegarde sur le disque dur...\n");

		// lors de la sauvegarde, on va supprimer la liste de recherche présente dans
		// les tableaux.
		// Cette liste possède l'identifiant 999 : elle n'est pas sauvegardée à la
		// fermeture de l'application.
		// On la supprime pour éviter de la récupérer au prochain lancement de
		// l'application.

		int i = 0;
		int j = 0;
		int k = 0;
		while (i < utilisateurs.size()) {
			while (j < utilisateurs.get(i).getTableauxUtilisateur().size()) {
				while (k < utilisateurs.get(i).getTableauxUtilisateur().get(j).getContenuTableau().size()) {
					if (utilisateurs.get(i).getTableauxUtilisateur().get(j).getContenuTableau().get(k).getIdentifiant() == 999) {
						utilisateurs.get(i).getTableauxUtilisateur().get(j)
								.supprimerListe(utilisateurs.get(i).getTableauxUtilisateur().get(j).getContenuTableau().get(k));
					}
					k++;
				}
				j++;
			}
			i++;
		}

		try {
			// Tentative de lecture dans le fichier "nomFichier"
			FileOutputStream fichier = new FileOutputStream(nomFichier);
			ObjectOutputStream flotObjet = new ObjectOutputStream(fichier);
			// Si fichier trouvé, alors on écrit notre sauvegarde entière puis on ferme le
			// flux
			flotObjet.writeObject(this);
			flotObjet.close();
			// On affiche un message
			System.out.println("Enregistrement de la sauvegarde réussi !\n");
		} catch (IOException e) {
			// En cas d'erreur, on affiche un message
			System.out.println("Erreur lors de l'enregistrement de la sauvegarde !\n");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Cette méthode renvoie un objet de la classe TrelloLitePlus
	 * contenant une précédente sauvegarde sérialisée d'un objet de cette même
	 * classe.
	 * 
	 * @param nomFichier Le nom du fichier à récupérer
	 * @return Une instance de la classe TrelloLitePlus
	 */
	public TrelloLitePlus recuperation(String nomFichier)
			throws IOException, ClassNotFoundException, InterruptedException {

		System.out.println("Tentative de lecture dans le fichier " + nomFichier + "...");
		TrelloLitePlus sauvegarde = new TrelloLitePlus();

		try {

			// Tentative de lecture dans le fichier "nomFichier"
			FileInputStream fichier = new FileInputStream(nomFichier);
			ObjectInputStream flotObjet = new ObjectInputStream(fichier);

			// Si fichier trouvé, alors on écrit son contenu dans l'objet sauvegarde
			// temporaire puis on ferme le flux
			sauvegarde = (TrelloLitePlus) (flotObjet.readObject());
			flotObjet.close();

			// On affiche un message
			System.out.println("Récupération de la sauvegarde contenue dans le fichier " + nomFichier + " réussie !\n");

			// En cas d'erreur, les différentes exceptions sont gérées
		} catch (IOException e) {
			System.out.println("Le fichier " + nomFichier + " n'existe pas !\n");
			System.out.println(e.getMessage());

		} catch (ClassNotFoundException e) {
			System.out.println("Erreur lors de la lecture du fichier " + nomFichier + ".\n");

		}

		return sauvegarde;
	}

	/**
	 * Permet d'ajouter un utilisateur à la liste des utilisateurs de l'application.
	 * 
	 * @param u L'utilisateur à ajouter
	 */
	public void ajouterUtilisateur(Utilisateur u) {
		utilisateurs.add(u);
	}

	/**
	 * Surcharge de la méthode
	 * {@link TrelloLitePlus#ajouterUtilisateur(Utilisateur)}.
	 * Permet de retirer un utilisateur de la liste des utilisateurs de
	 * l'application.
	 * 
	 * @param indice L'indice de l'utilisateur à retirer
	 * @return L'utilisateur retiré
	 */
	public Utilisateur retirerUtilisateur(int indice) {
		return utilisateurs.remove(indice);
	}

	/**
	 * Surcharge de la méthode {@link TrelloLitePlus#retirerUtilisateur(int)}.
	 * Permet de retirer un utilisateur de la liste des utilisateurs de
	 * l'application.
	 * 
	 * @param user L'utilisateur à retirer
	 * @return Vrai si l'utilisateur a bien été retiré, faux sinon
	 */
	public boolean retirerUtilisateur(Utilisateur user) {
		return utilisateurs.remove(user);
	}

	// ******************************************
	// **************** GETTERS *****************
	// ******************************************

	/**
	 * Permet de récupérer la liste des utilisateurs de l'application.
	 * 
	 * @return La liste des utilisateurs de l'application
	 */
	public ArrayList<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	/**
	 * Permet de récupérer l'utilisateur actuellement connecté à l'application.
	 * 
	 * @return L'utilisateur actuellement connecté à l'application
	 */
	public Utilisateur getUtilisateurActuel() {
		return utilisateurActuel;
	}

	/**
	 * Permet de récupérer le tableau actuellement sélectionné par l'utilisateur.
	 * 
	 * @return Le tableau actuellement sélectionné par l'utilisateur
	 */
	public Tableau getTableauActuel() {
		return tableauActuel;
	}

	// ******************************************
	// **************** SETTERS *****************
	// ******************************************

	public void setUtilisateurs(ArrayList<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public void setUtilisateurActuel(Utilisateur utilisateurActuel) {
		this.utilisateurActuel = utilisateurActuel;
	}

	public void setTableauActuel(Tableau tableauActuel) {
		this.tableauActuel = tableauActuel;
	}

	// ***************************
	// ************ MAIN *********
	// ***************************

	public static void main(String[] args) {
		try {

			TrelloLitePlus application = new TrelloLitePlus().recuperation("Sauvegarde.dat");

			application.lancerApplication();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
