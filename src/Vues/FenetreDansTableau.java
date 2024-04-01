package Vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.*;
import Controleur.*;
import Modele.*;

/**
 * Cette classe représente une fenêtre par défaut à l'intérieur d'un tableau
 * pour l'application Trello Lite+.
 * Elle étend la classe JFrame et contient les fonctionnalités et les composants
 * de la fenêtre principale de Trello Lite+.
 * Elle comprend notamment la barre des tâches {@link BarreMenu} et un panel
 * central.
 * Ainsi, les éléments à ajouter seront dans le panelCentral : une méthode est
 * ainsi implémentée pour localiser l'ajout des éléments Swing directement dans
 * le panelCentral.
 * 
 * @see JFrame
 * @see BarreMenu
 * @author Olivier
 */

public class FenetreDansTableau extends JFrame {

	// ***********************************
	// ******* ATTRIBUTS *****************
	// ***********************************
	private BarreMenu taskBar;
	private JPanel panelCentral;
	private Tableau modele;
	private TrelloLitePlus application;

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe FenetreDansTableau : effecute la création de la
	 * fenêtre.
	 * Paramètre également le comportement de la fenêtre à sa fermerture, pour
	 * appeler l'outil de sauvegarde : {@link #sauvergardeSession()}.
	 * 
	 * @param modele      : le modèle du tableau
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde.
	 */
	public FenetreDansTableau(Tableau modele, TrelloLitePlus application) {

		// Affectation de l'application à la fenêtre
		this.application = application;

		// Affectation du modele à la fenêtre
		this.modele = modele;

		// On consulte le modèle donc il devient le tableau actuel.
		application.setTableauActuel(modele);

		// Background de la même taille que celle du tableau :
		this.setBackground(modele.getCouleur());

		// Quelques éléments de mise en page et de comportement
		setSize(1280, 720);
		setTitle("Trello Lite+");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Définition de la barre des tâches en haut
		taskBar = new BarreMenu(this, application, modele);
		taskBar.setCouleurFond(modele.getCouleur());

		// Ajout de la barre des tâches dans la fenêtre. Elle sera en haut
		this.getContentPane().add(taskBar, BorderLayout.NORTH);

		// Ajout du panel central
		panelCentral = new JPanel(new GridLayout(1, 1));
		this.getContentPane().add(panelCentral);
		// Rendre le panelCentral transparent pour voir le fond de la fenêtre
		panelCentral.setOpaque(false);

		// Changement de la couleur de la fenêtre
		this.getContentPane().setBackground(Color.decode("#DAE8FC"));

		/**
		 * Procédure à appeler lors de la fermeture de la fenêtre.
		 * Cette procédure est une procédure de sauvegarde de la session : on va
		 * l'utiliser pour sérialiser tous les objets.
		 */
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// Sauvegarde des données
				try 
				{
					sauvergardeSession();
				} 
				catch (IOException e) 
				{
					System.out.println("Erreur lors de la sauvegarde de la session");
				}
			}
		});

		redessiner(); // On appelle la méthode redessiner pour ajouter les éléménts manquants qui
									// dépendent du modèle.
	}

	// ***********************************
	// *********** METHODES **************
	// ***********************************

	/**
	 * Cette procédure permet d'ajouter un élément supplémentaire au panneau central
	 * de la fenêtre de l'application. Elle est surchargée pour pouvoir ajouter
	 * plusieurs éléments de type différents (boutons, panels...)
	 * 
	 * @param panel : le panel à ajouter
	 */
	public void addElement(JPanel panel) {

		panelCentral.add(panel); // On ajoute le conteneurListe au panelCentral
	}

	/**
	 * Cette procédure permet d'ajouter un élément supplémentaire au panneau central
	 * de la fenêtre de l'application. Elle est surchargée pour pouvoir ajouter
	 * plusieurs éléments de type différents (boutons, panels...)
	 * 
	 * @param bouton : le bouton à ajouter
	 */
	public void addElement(JButton bouton) {
		panelCentral.add(bouton);
	}

	/**
	 * Cette procédure permet de redessiner la fenêtre. Elle est utilisée pour
	 * mettre à jour l'affichage de la fenêtre après une modification du modèle.
	 */
	public void redessiner() {

		// Pour chaque liste dans le tableau, on va créer un objet ContenuListe et on va
		// l'afficher dans le panel central.
		panelCentral.removeAll();

		for (Liste liste : modele.getContenuTableau()) {
			// Création de la vue associée à la liste courante
			// la création du controleur associé à la vue courante est gérée par le
			// constructeur de ConteneurListe.
			ConteneurListe conteneurListe = new ConteneurListe(liste, this, application);
			panelCentral.add(conteneurListe);
		}

		// On recrée le bouton pour ajouter une nouvelle liste.
		panelCentral.add(new ControleurAjouterListe(modele, new AjouterListe(application), this, application));

		repaint();
		revalidate();
	}

	/**
	 * sauvegardeSession est une méthode polymorphe, appelée lors de la fermeture
	 * des deux objets Fenêtre (JFrame) de l'application :
	 * FenetrePrincipale et FenetreDansTableau.
	 *
	 * Cette méthode permet de sauvegarder les utilisateurs créés lors de la
	 * fermeture de l'application.
	 * L'utilisateur actuel de l'application sera sauvegardé : par extension ses
	 * tableaux,
	 * les listes et les cartes également.
	 * Le terme "base de données" est utilisé pour désigner l'ArrayList de la
	 * classe TrelloLitePlus,
	 * qui est l'attribut qui va stocker tous les utilisateurs créés lors d'une
	 * session sur l'application.
	 */
	public void sauvergardeSession() throws IOException {
		application.enregistrement("Sauvegarde.dat");
	}

}
