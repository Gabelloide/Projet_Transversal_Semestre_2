
package Controleur;

import Vues.*;
import javax.swing.*;
import Modele.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 
 * Cette classe implémente le contrôleur correspondant à la vue
 * {@link Vues.BarreMenu}.
 * Ce contrôleur permet de gérer les actions de l'utilisateur sur la barre de
 * menu.
 * Il est également possible de rechercher une carte dans le tableau, ce qui va
 * créer une liste fictive contenant les résultats de la recherche.
 * Il contient les éléments suivants : <br>
 * - un bouton pour accéder à la liste des tableaux de l'utilisateur <br>
 * - un bouton pour accéder à la liste des membres du tableau <br>
 * - un bouton pour supprimer le tableau
 * - un champ de texte et bouton pour rechercher une carte
 * 
 * 
 * @author Yannick
 * @author Olivier
 * @author Soan
 */

public class ControleurBarreMenu extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	// Attribut de la barre de menu
	private BarreMenu vue;
	private JFrame fenetreParent;
	private TrelloLitePlus application;
	private JButton mesTableaux;

	// Bouton pour FenetreUtilisateur
	private JButton btnDeconnexion;

	/** Constante pour l'action du bouton deconnexion */
	static public final String ACTION_DECONNEXION = "DECONNEXION";

	// Bouton pour FenetreDansTableau
	private JButton btnMembres;
	private JButton btnSupprimer;
	private JButton btnQuitter;
	private Tableau monTableau;

	/** Constante pour l'action du bouton accéder */
	static public final String ACTION_ACCEDER = "ACCEDER";

	/** Constante pour l'action du bouton permissions */
	static public final String ACTION_PERMISSION = "PERMISSION";

	/** Constante pour l'action du bouton supprimer */
	static public final String ACTION_SUPPRIMER = "SUPPRIMER";

	/** Constante pour l'action du bouton quitter */
	static public final String ACTION_QUITTER = "QUITTER";

	// Pour rechercher une carte
	private int IDRecherche;
	private JTextField rechTextField;
	private JButton rechercher;
	private JButton cancelRecherche;

	/** Constante pour l'action du bouton rechercher */
	static public final String ACTION_RECHERCHER = "RECHERCHER";

	/** Constante pour l'action du bouton annuler recherche */
	static public final String ACTION_ANNULER_RECHERCHE = "ANNULER_RECHERCHE";

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurBarreMenu
	 * Ce constructeur ne prend pas de tableau en paramètre. Cela signifie que cette
	 * BarreMenu est celle présente sur la première fenêtre de l'application :
	 * FenetreUtilisateur.
	 * 
	 * @param vue           : la vue va accueillir les différents éléments
	 *                      graphiques du contrôleur
	 * @param fenetreParent : la fenêtre parent contenant la vue.
	 * @param application   : l'application est le modèle de l'application dans son
	 *                      ensemble. Cet élément est notamment utile pour la
	 *                      sauvegarde.
	 */
	public ControleurBarreMenu(BarreMenu vue, JFrame fenetreParent, TrelloLitePlus application) {

		// On initialise les attributs
		this.vue = vue;
		this.fenetreParent = fenetreParent;
		this.application = application;

		// On crée le bouton
		mesTableaux = new JButton("Mes tableaux");
		mesTableaux.addActionListener(this);
		mesTableaux.setActionCommand(ACTION_ACCEDER);

		btnDeconnexion = new JButton("Déconnexion");
		btnDeconnexion.addActionListener(this);
		btnDeconnexion.setActionCommand(ACTION_DECONNEXION);
		btnDeconnexion.setBackground(Color.red);

		// On ajoute le bouton
		add(mesTableaux);
		add(btnDeconnexion);
	}

	/**
	 * Constructeur de la classe ControleurBarreMenu.
	 * Ce constructeur prend en paramètre un tableau. Cela signifie que cette
	 * BarreMenu est celle présente sur la fenêtre d'un tableau : FenetreTableau.
	 * 
	 * @param vue           : la vue va accueillir les différents éléments
	 *                      graphiques du contrôleur
	 * @param fenetreParent : la fenêtre parent contenant la vue.
	 * @param application   : l'application est le modèle de l'application dans son
	 *                      ensemble. Cet élément est notamment utile pour la
	 *                      sauvegarde.
	 * @param monTableau    : le tableau sur lequel on se trouve actuellement.
	 */
	public ControleurBarreMenu(BarreMenu vue, JFrame fenetreParent, TrelloLitePlus application, Tableau monTableau) {

		// On initialise les attributs
		this.vue = vue;
		this.fenetreParent = fenetreParent;
		this.application = application;
		this.monTableau = monTableau;

		// On crée les boutons

		mesTableaux = new JButton("Mes tableaux");
		mesTableaux.addActionListener(this);
		mesTableaux.setActionCommand(ACTION_ACCEDER);

		btnMembres = new JButton("Membres du tableau");
		btnMembres.addActionListener(this);
		btnMembres.setActionCommand(ACTION_PERMISSION);

		btnSupprimer = new JButton("Supprimer le tableau");
		btnSupprimer.addActionListener(this);
		btnSupprimer.setActionCommand(ACTION_SUPPRIMER);
		btnSupprimer.setBackground(Color.red);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this);
		btnQuitter.setActionCommand(ACTION_QUITTER);
		btnQuitter.setBackground(Color.red);

		// On ajoute le bouton des tableaux
		add(mesTableaux);

		// Ajout du bouton membres seulement si on est sur la fenetre d'un tableau
		// Et seulement si l'utilisateur actuel est administrateur ou propriétaire
		// On parcours les utilisateurs du tableau actuel afin de récuperer la
		// permission de l'utilisateur actuel
		if (application.getTableauActuel() != null) { // Si on se trouve bien dans un tableau
			int ind = application.getTableauActuel().getIndexUtilisateur(application.getUtilisateurActuel());
			if (application.getTableauActuel().getPermissions().get(ind) != 2) { // Si la permission n'est PAS Lecture Seule
				// Alors on ajoute le bouton d'accès aux permissions
				add(btnMembres);
			}
			if (application.getTableauActuel().getPermissions().get(ind) == 0) { // Si la permission EST propriétaire
				// On ajoute le bouton de suppression de tableau
				add(btnSupprimer);
			} else if (application.getTableauActuel().getPermissions().get(ind) != 0) { // Si la permission n'est PAS
																																									// propriétaire
				// On ajoute le bouton pour quitter le tableau
				add(btnQuitter);
			}

			// Elements pour la recherche
			rechTextField = new JTextField(20);
			rechercher = new JButton("Rechercher");
			rechercher.addActionListener(this);
			rechercher.setActionCommand(ACTION_RECHERCHER);
			cancelRecherche = new JButton("Annuler");
			cancelRecherche.addActionListener(this);
			cancelRecherche.setActionCommand(ACTION_ANNULER_RECHERCHE);
			add(rechTextField);
			add(rechercher);
			add(cancelRecherche);
		}

	}

	// ***********************************
	// ********** METHODES ***************
	// ***********************************

	/**
	 * Méthode permettant de gérer les actions de l'utilisateur sur la barre de
	 * menu.
	 * 
	 * @param e : l'évènement qui a déclenché l'action
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_ACCEDER)) {

			// On revient au menu sous le POV de l'utilisateur actuel
			fenetreParent.dispose();
			// On crée une nouvelle fenêtre
			FenetreUtilisateur fnTab = new FenetreUtilisateur(application.getUtilisateurActuel(), application);
			application.setTableauActuel(null); // l'utilisateur ne consulte plus le tableau
			fnTab.setVisible(true);
		}
		if (e.getActionCommand().equals(ACTION_PERMISSION)) {

			// Si l'utilisateur clique sur le bouton "Membres"
			// On crée une nouvelle fenetre de modification des permissions
			VueModificationPerms vuemodif = new VueModificationPerms(application);
			vuemodif.setVisible(true);
		}
		if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {

			// Si l'utilisateur clique sur le bouton "Supprimer" pour supprimer le tableau
			// On affiche une fenetre de confirmation
			int option = JOptionPane.showConfirmDialog(this,
					"Etes-vous sûr de vouloir supprimer ce tableau ?",
					"Confirmation de suppression",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			// Si l'utilisateur clique sur oui
			if (option == JOptionPane.YES_OPTION) {

				// On doit supprimer le tableau pour tous les utilisateurs de l'application :
				// On parcours tous les utilisateurs de l'application
				for (Utilisateur user : application.getUtilisateurs()) {
					user.getTableauxUtilisateur().remove(monTableau) ; // On supprime le tableau de l'utilisateur
				}

				// On ferme la fenêtre et on revient au menu sous le point de vue de
				// l'utilisateur actuel
				fenetreParent.dispose();
				FenetreUtilisateur fnTab = new FenetreUtilisateur(application.getUtilisateurActuel(), application);
				application.setTableauActuel(null); // l'utilisateur ne consulte plus le tableau (plus de tableau actuel)
				fnTab.setVisible(true);
			}
		}

		// Si l'utilisateur veut rechercher des éléments
		if (e.getActionCommand().equals(ACTION_RECHERCHER)) {
			// L'idée générale est de créer une liste 'fictive' appelée "Résultat de la
			// recherche" qui va accueillir les cartes qui correspondent à la recherche.
			// Cette liste est supprimée dès lors que l'utilisateur arrête la recherche, ou
			// s'il en fait une autre.

			// si le tableau est vide
			if (rechTextField.getText().equals("")) {
				// popup : impossible de rechercher si le champ est vide
				JOptionPane.showMessageDialog(this, "Veuillez renseigner une carte à rechercher", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}

			// si le tableau est vide
			else if (monTableau.getContenuTableau().size() == 0) {

				// popup : impossible de faire une recherche sur un tableau vide
				JOptionPane.showMessageDialog(this, "Impossible de faire une recherche sur un tableau vide", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}

			// sinon s'il y a deja une recherche en cours
			else if (IDRecherche != 0) {

				// on annule alors la recherche
				// on supprime la liste de recherche du tableau
				int i = 0;
				boolean trouve = false;
				while (!trouve && i < monTableau.getContenuTableau().size()) {
					if (monTableau.getContenuTableau().get(i).getIdentifiant() == IDRecherche) {
						trouve = true;
						monTableau.supprimerListe(monTableau.getContenuTableau().get(i));
						IDRecherche = 0; // valeur par défaut pour dire qu'il n'y a pas de recherche en cours car on
															// vient de l'annuler
					}
					i++;
				}
				if (fenetreParent instanceof FenetreDansTableau) {
					((FenetreDansTableau) fenetreParent).redessiner(); // On doit downcaster la fenetre car c'est une JFrame
				}
				// puis on lance la nouvelle recherche
				ArrayList<Liste> mesListes = monTableau.getContenuTableau();
				Liste cartesTrouvees = new Liste(999, "Résultat de la recherche");
				IDRecherche = cartesTrouvees.getIdentifiant();
				i = 0;
				while (i < mesListes.size()) {
					for (Carte carte : mesListes.get(i).getContenuListe()) {
						if (carte.getTitre().contains(rechTextField.getText())) {
							cartesTrouvees.ajouterCarte(carte);
						}
					}
					i++;
				}

				// ajout de la liste de cartes trouvées au tableau
				monTableau.ajouterListe(cartesTrouvees);

				if (fenetreParent instanceof FenetreDansTableau) {
					((FenetreDansTableau) fenetreParent).redessiner(); // On doit downcaster la fenetre car c'est une JFrame
				}

				// sinon (l'utilisateur recherche de manière normale)
			}

			// S'il n'y a pas de recherche en cours
			else {

				ArrayList<Liste> mesListes = monTableau.getContenuTableau();
				Liste cartesTrouvees = new Liste(999, "Résultat de la recherche"); // Liste à part, ID 999
				IDRecherche = cartesTrouvees.getIdentifiant(); // 999

				int i = 0;
				while (i < mesListes.size()) {
					for (Carte carte : mesListes.get(i).getContenuListe()) {
						if (carte.getTitre().contains(rechTextField.getText())) {
							cartesTrouvees.ajouterCarte(carte);
						}
					}
					i++;
				}
				// ajout de la liste de cartes trouvées au tableau
				monTableau.ajouterListe(cartesTrouvees);

				if (fenetreParent instanceof FenetreDansTableau) {
					((FenetreDansTableau) fenetreParent).redessiner(); // On doit downcaster la fenetre car c'est une JFrame
				}
			}
		}

		// Si l'utilisateur veut annuler la recherche en cours
		if (e.getActionCommand().equals(ACTION_ANNULER_RECHERCHE)) {

			// si aucune recherche en cours
			if (IDRecherche == 0) {
				// popup impossible d'annuler car aucune recherche en cours
				JOptionPane.showMessageDialog(this, "Impossible d'annuler car aucune recherche en cours", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
			// Si une recherche est en cours
			else {
				// on supprime la liste de recherche du tableau
				int i = 0;
				boolean trouve = false;
				while (!trouve && i < monTableau.getContenuTableau().size()) {
					if (monTableau.getContenuTableau().get(i).getIdentifiant() == IDRecherche) {
						trouve = true;
						monTableau.supprimerListe(monTableau.getContenuTableau().get(i));
						IDRecherche = 0; // valeur par défaut pour dire qu'il n'y a pas de recherche en cours car on
															// vient de l'annuler
					}
					i++;
				}
				if (fenetreParent instanceof FenetreDansTableau) {
					((FenetreDansTableau) fenetreParent).redessiner();// On doit downcaster la fenetre car c'est une JFrame
				}
			}
		}
		if (e.getActionCommand().equals(ACTION_QUITTER)) {

			// Si l'utilisateur clique sur le bouton "Quitter" pour quitter le tableau
			// On affiche une fenetre de confirmation
			int option = JOptionPane.showConfirmDialog(this,
					"Etes-vous sûr de vouloir quitter ce tableau ?",
					"Confirmation quitter tableau",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			// Si l'utilisateur clique sur oui
			if (option == JOptionPane.YES_OPTION) {

				// On supprime l'utilisateur actuel des membres du tableau
				monTableau.supprimerUtilisateur(application.getUtilisateurActuel());

				// On ferme la fenêtre et on revient au menu sous le point de vue de
				// l'utilisateur actuel
				fenetreParent.dispose();
				FenetreUtilisateur fnTab = new FenetreUtilisateur(application.getUtilisateurActuel(), application);
				application.setTableauActuel(null); // l'utilisateur ne consulte plus le tableau (plus de tableau actuel)
				fnTab.setVisible(true);
			}
		}
		if (e.getActionCommand().equals(ACTION_DECONNEXION)) {
			// Si l'utilisateur clique sur le bouton "Déconnexion"
			// On affiche une fenetre de confirmation
			int option = JOptionPane.showConfirmDialog(this,
					"Etes-vous sûr de vouloir vous déconnecter ?",
					"Confirmation déconnexion",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			// Si l'utilisateur clique sur oui
			if (option == JOptionPane.YES_OPTION) {

				// On ferme la fenêtre et on revient à la fenêtre de connexion
				fenetreParent.dispose();
				new Login(application);
			}
		}
	}

	// ***********************************
	// ********** ACCESSEURS *************
	// ***********************************

	/**
	 * Renvoie la vue associée à ce controleur
	 * @return une vue BarreMenu
	 */
	public BarreMenu getVue() {
		return vue;
	}

	/**
	 * Modifie la vue associée à ce controleur
	 * @param vue la nouvelle vue
	 */
	public void setVue(BarreMenu vue) {
		this.vue = vue;
	}





}
