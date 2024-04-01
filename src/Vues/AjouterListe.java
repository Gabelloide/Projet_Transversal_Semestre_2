package Vues;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;

import Modele.TrelloLitePlus;

/**
 * 
 * Cette classe implémente la Vue graphique permettant
 * d'ajouter une liste à un tableau.
 * Cette vue permet à l'utilisateur de renseigner le nom de la liste à
 * ajouter, et de valider l'ajout, ou de l'annuler.
 * {@link Modele.Liste} pour des informations complémentaires sur la
 * classe Liste.
 * {@link Controleur.ControleurAjouterListe} pour des informations
 * complémentaires sur le contrôleur associé.
 * 
 * @author Olivier
 */

public class AjouterListe extends JPanel {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private TrelloLitePlus application;

	// ***********************************
	// ****** CONSTRUCTEUR ***************
	// ***********************************

	/**
	 * Constructeur de la classe AjouterListe
	 * 
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde.
	 */
	public AjouterListe(TrelloLitePlus application) {
		this.application = application;
		// Contour du panel
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		// Définition du layout
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Fond transparent (pour voir le fond de la fenêtre)
		this.setOpaque(false);
	}

	// ***********************************
	// ***** GETTERS & SETTERS ***********
	// ***********************************

	/**
	 * permet de récupérer l'instance actuelle de l'application
	 * 
	 * @return l'instance de l'application actuelle
	 */
	public TrelloLitePlus getApplication() {
		return application;
	}

}
