package Vues;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.*;

import Controleur.ControleurBarreMenu;
import Modele.*;

/**
 * Cette classe implémente la Vue graphique permettant d'afficher la barre des
 * tâches (située en haut de la fenêtre).
 * Il existe deux implémentations différentes de cette barre des tâches : une
 * pour le menu principal, et une pour la fenêtre lorsque l'utilisateur est
 * dans un tableau. Les deux classes concernées sont respectivement
 * {@link Vues.FenetreUtilisateur} et {@link Vues.FenetreDansTableau}.
 * Le contrôleur associé à cette vue est {@link Controleur.ControleurBarreMenu}.
 * 
 * @see Controleur.ControleurBarreMenu
 * @see Vues.FenetreUtilisateur
 * @see Vues.FenetreDansTableau
 * @author Olivier
 * @author Soan
 * @author Yannick
 */

public class BarreMenu extends JPanel {
	// L'objet BarreMenu est une barre des tâches pour les fenêtres qui seront crées
	// dans le projet.

	/**
	 * Correspond à la fenetre dans laquelle se trouve la barre des tâches
	 * (FenetreUtilisateur ou FenetreDansTableau), d'où le type englobant JFrame
	 * (classem mère) pour éviter les erreurs de compilation
	 */
	private JFrame fenetreParent;

	/** Correspond au tableau dans lequel se trouve la barre des tâches */
	private Tableau monTableau;

	private TrelloLitePlus application;

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	// Ce constructeur de la Barre des tâches est utilisé pour la barre des tâches
	// présente dans la frame
	// FenetreUtilisateur (affichage différent)

	/**
	 * Ce constructeur de la Barre des tâches est utilisé pour la barre des tâches
	 * présente dans la frame FenetreUtilisateur (affichage différent)
	 * 
	 * @param fenetreParent : la fenêtre parente (de la classe FenetreUtilisateur)
	 * @param application   : l'application est le modèle de l'application dans son
	 *                      ensemble. Cet élément est notamment utile pour la
	 *                      sauvegarde.
	 */
	public BarreMenu(JFrame fenetreParent, TrelloLitePlus application) {
		// On affecte la fenêtre parente
		this.fenetreParent = fenetreParent;
		this.application = application;

		// Définition de la barre des tâches en haut
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setBackground(Color.decode("#9999FF"));

		// Le nom de l'application en haut à gauche
		JLabel titreFenetre = new JLabel("Trello Lite+");

		// Augmentation de la taille du titre : (police 20)
		titreFenetre.setFont(new Font("base", Font.PLAIN, 20));

		// Bouton pour consulter les tableaux
		ControleurBarreMenu mesTableaux = new ControleurBarreMenu(this, fenetreParent, application);

		// Ajout des différents composants à la barre des tâches en haut
		this.add(titreFenetre);
		this.add(mesTableaux);
	}

	/**
	 * Ce constructeur de la Barre des tâches est utilisé pour la barre des tâches
	 * présente dans la frame FenetreDansTableau (affichage différent)
	 * 
	 * @param fenetreParent : la fenêtre parente (de la classe FenetreDansTableau)
	 * @param application   : l'application est le modèle de l'application dans son
	 *                      ensemble. Cet élément est notamment utile pour la
	 *                      sauvegarde.
	 * @param monTableau    : le tableau qui est actuellement consulté par
	 *                      l'utilisateur
	 */
	public BarreMenu(JFrame fenetreParent, TrelloLitePlus application, Tableau monTableau) {

		// On affecte la fenêtre parente
		this.fenetreParent = fenetreParent;
		this.application = application;
		this.monTableau = monTableau;

		// Définition de la barre des tâches en haut
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setBackground(Color.decode("#9999FF"));

		// Le nom de l'application en haut à gauche
		JLabel titreFenetre = new JLabel("Trello Lite+");

		// Augmentation de la taille du titre : (police 20)
		titreFenetre.setFont(new Font("base", Font.PLAIN, 20));

		// Bouton pour consulter les tableaux
		ControleurBarreMenu mesTableaux = new ControleurBarreMenu(this, fenetreParent, application, monTableau);

		// Ajout des différents composants à la barre des tâches en haut
		this.add(titreFenetre);
		this.add(mesTableaux);
	}

	// ***********************************
	// ******* GETTERS & SETTERS *********
	// ***********************************

	/**
	 * Cette méthode permet de modifier la couleur de fond de la barre des tâches
	 * @param couleur : la couleur de fond à appliquer
	 */
	public void setCouleurFond(Color couleur) {
		this.setBackground(couleur);
	}

	/**
	 * Cette méthode permet de récupérer la fenêtre parente de la barre des tâches
	 * @return la fenêtre parente
	 */
	public JFrame getFenetreParent() {
		return fenetreParent;
	}

	/**
	 * Cette méthode permet de modifier la fenêtre parente de la barre des tâches
	 * @param fenetreParent : la nouvelle fenêtre parente
	 */
	public void setFenetreParent(JFrame fenetreParent) {
		this.fenetreParent = fenetreParent;
	}

	/**
	 * Cette méthode permet de récupérer le tableau actuellement consulté
	 * @return le tableau
	 */
	public Tableau getMonTableau() {
		return monTableau;
	}

	/**
	 * Cette méthode permet de modifier le tableau actuellement consulté
	 * @param monTableau : le nouveau tableau
	 */
	public void setMonTableau(Tableau monTableau) {
		this.monTableau = monTableau;
	}

	/**
	 * Cette méthode permet de récupérer l'instance de l'application actuellement lancée
	 * @return l'application
	 */
	public TrelloLitePlus getApplication() {
		return application;
	}

	/**
	 * Cette méthode permet de modifier l'instance de l'application actuellement lancée
	 * @param application : la nouvelle application
	 */
	public void setApplication(TrelloLitePlus application) {
		this.application = application;
	}

}
