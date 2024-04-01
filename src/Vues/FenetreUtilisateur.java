package Vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.*;

import Controleur.*;
import Modele.*;

/**
 * Cette classe représente une fenêtre par défaut pour l'application
 * TrelloLitePlus. Après la connexion de l'utilisateur, ce dernier arrive sur
 * cette page.
 * Tout comme la JFrame {@link Vues.FenetreDansTableau}, elle est composée d'une
 * barre des tâches {@link Vues.BarreMenu} et d'un panel central.
 * Ainsi, les éléments à ajouter seront dans le panelCentral : une méthode est
 * ainsi implémentée pour localiser l'ajout des éléments Swing directement dans
 * le panelCentral.
 * 
 * Cette vue est liée au modèle pour afficher autant de tableaux que
 * l'utilisateur en possède.
 * C'est le contrôleur {@link Controleur.ControleurTableau} qui gère l'affichage
 * de nouveaux boutons pour chaque tableau possédé par l'utilisateur.
 * 
 * @see Vues.FenetreDansTableau
 * @see Vues.BarreMenu
 * @see Controleur.ControleurTableau
 * @author Olivier
 */

public class FenetreUtilisateur extends JFrame {

	// ***********************************
	// ******* ATTRIBUTS *****************
	// ***********************************

	private BarreMenu taskBar;
	private JPanel panelCentral;
	private Utilisateur modele;
	private TrelloLitePlus application;

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe FenetreUtilisateur
	 * @param modele : le modèle de l'utilisateur
	 * @param application : l'application est le modèle de l'application dans son ensemble. Cet élément est notamment utile pour la sauvegarde.
	 */
	public FenetreUtilisateur(Utilisateur modele, TrelloLitePlus application) {
		this.application = application;
		this.modele = modele;
		application.setTableauActuel(null); // On ne consulte aucun tableau car on est sur la page d'accueil.

		this.setPreferredSize(new Dimension(1280, 720));
		setTitle("Trello Lite+");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Définition de la barre des tâches en haut
		taskBar = new BarreMenu(this, application);

		// Ajout de la barre des tâches dans la fenêtre. Elle sera en haut
		this.getContentPane().add(taskBar, BorderLayout.NORTH);

		// Ajout du panel central
		panelCentral = new JPanel(new GridLayout(2, 4));
		this.getContentPane().add(panelCentral);

		// Changement de la couleur de la fenêtre
		this.getContentPane().setBackground(Color.decode("#DAE8FC"));

		// Procédure à appeler lors de la fermeture de la fenêtre
		// Cette procédure est une procédure de sauvegarde de la session : on va
		// l'utiliser pour sérialiser tous les objets.
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// Sauvegarde des données
				try {
					sauvergardeSession();
				} catch (IOException e) {
					System.out.println("Erreur lors de la sauvegarde de la session");
				}
			}
		});
		redessiner();
	}


	/**
	 * Cette méthode permet de redessiner la fenêtre de l'application.
	 * Elle retire tous les éléments du panel central, puis les réaffiche.
	 * Elle est appelée directement dans le constructeur dans un but de factorisation du code.
	 */
	public void redessiner() {
		panelCentral.removeAll();

		new ControleurBoutonCreerTableau(modele, this, application);

		// Ajout des tableaux
		for (Tableau t : modele.getTableauxUtilisateur()) {

			ControleurTableau controleur = new ControleurTableau(t, this, application);
			panelCentral.add(controleur);

		}

		pack();
		repaint();
		revalidate();
	}

	// ***********************************
	// ************ METHODES *************
	// ***********************************

			/**
		 * Cette procédure permet d'ajouter un élément supplémentaire au panneau central
		 * de la fenêtre de l'application. Elle est surchargée pour pouvoir ajouter
		 * plusieurs éléments de type différents (boutons, panels...)
		 * @param panel : le panel à ajouter
		 */
	public void addElement(JPanel panel) {

		panelCentral.add(panel); // On ajoute le conteneurListe au panelCentral
	}

	/**
	 * Cette procédure permet d'ajouter un élément supplémentaire au panneau central
	 * de la fenêtre de l'application. Elle est surchargée pour pouvoir ajouter
	 * plusieurs éléments de type différents (boutons, panels...)
	 * @param bouton : le bouton à ajouter
	 */
	public void addElement(JButton bouton) {
		/** Procédure identique pour l'ajout d'un bouton */
		panelCentral.add(bouton);
	}


			/**
		 * sauvegardeSession est une méthode polymorphe, appelée lors de la fermeture
		 * des deux objets Fenêtre (JFrame) de l'application :
		 * {@link Vues.FenetreUtilisateur} et {@link Vues.FenetreDansTableau}.
		 *
		 * Cette méthode permet de sauvegarder la totalité des objets créés lors de la
		 * fermeture de l'application.
		 * L'utilisateur actuel de l'application sera sauvegardé, ainsi que ses
		 * tableaux,
		 * les listes et les cartes qu'il a créé.
		 * Le terme "base de données" est utilisé pour désigner les ArrayList de la
		 * classe TrelloLitePlus,
		 * qui sont des attributs static qui vont stocker tous les objets
		 * créés lors d'une session sur l'application.
		 * 
		 * @throws IOException : exception levée lors de la sauvegarde
		 */
	public void sauvergardeSession() throws IOException {
		application.enregistrement("Sauvegarde.dat");

	}

}
