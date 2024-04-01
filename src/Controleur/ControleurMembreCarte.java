package Controleur;

import Vues.*;
import javax.swing.*;
import Modele.*;
import java.awt.Color;
import java.awt.event.*;

/**
 * Cette classe est un contrôleur associé à la vue
 * {@link Vues.VueGestionMembresCarte}.
 * Tandis que la vue implémente l'apercu des utilisateurs actuellement présents
 * dans la carte, ce contrôleur rajoute le bouton pour les supprimer.
 * Utilise également le sous composant vue {@link Vues.VueMembre} pour afficher
 * de brèves informations sur le membre.
 * 
 * @author Soan
 * @see Vues.VueGestionMembresCarte
 * @see Modele.Carte
 * @see Vues.VueMembre
 */

public class ControleurMembreCarte extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************
	static public final String ACTION_SUPPRIMER = "SUPPRIMER";
	TrelloLitePlus application;
	VueMembre vue;
	VueGestionMembresCarte vueFn;
	Carte modele;
	JButton btnSupprimer;

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurMembreCarte
	 * 
	 * @param vue         instance de la classe VueMembre (vue d'un membre de la
	 *                    carte)
	 * @param carte       instance de la classe Carte (modèle de la carte)
	 * @param vueFn       instance de la classe VueGestionMembresCarte (vue globale
	 *                    de la gestion des membres de la carte)
	 * @param application instance de la classe TrelloLitePlus (application)
	 */
	public ControleurMembreCarte(VueMembre vue, Carte carte, VueGestionMembresCarte vueFn, TrelloLitePlus application) {

		// On initialise les attributs
		this.application = application;
		this.vue = vue;
		this.vueFn = vueFn;
		this.modele = carte;

		// On crée le bouton
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(this);
		btnSupprimer.setActionCommand(ACTION_SUPPRIMER);

		btnSupprimer.setBorder(BorderFactory.createLineBorder(Color.red));

		add(btnSupprimer);
		vue.add(this);

	}

	/**
	 * Méthode appelée lorsque l'utilisateur appuie sur le bouton "Supprimer"
	 */
	public void actionPerformed(ActionEvent e) {
		// si on appuie sur le bouton supprimer, on va retirer le membre sélectionné de
		// la carte.
		if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			modele.supprimerMembre(vue.getMembre());

			// redessiner la vue globale
			vueFn.redessiner();
		}

	}

}
