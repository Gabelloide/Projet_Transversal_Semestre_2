
package Controleur;

import Modele.*;
import Vues.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Cette classe est le contrôleur associé à la fenêtre
 * {@link FenetreUtilisateur}. En effet ce contrôleur est un JPanel qui est
 * ajouté à cette fenêtre.
 * Elle implémente un gros bouton carré possédant le nom du tableau, et
 * permettant d'accéder à la fenêtre {@link FenetreDansTableau} correspondant
 * à ce même tableau.
 * Le bouton est de la même couleur que l'attribut couleur du tableau.
 * 
 * @see FenetreUtilisateur
 * @see FenetreDansTableau
 * @see Tableau
 * @author Yannick
 * @author Olivier
 */

public class ControleurTableau extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************
	private JButton btnTableau;
	private Tableau modele;
	private FenetreUtilisateur vue; // On est actuellement dans le menu principal (FenetreUtilisateur)
	private TrelloLitePlus application;
	static public final String ACTION_ACCEDER = "ACCEDER";

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************
	public ControleurTableau(Tableau modele, FenetreUtilisateur vue, TrelloLitePlus application) {

		// On initialise les attributs
		this.modele = modele;
		this.vue = vue;
		this.application = application;

		// On crée le bouton
		btnTableau = new JButton(modele.getTitre());
		btnTableau.setBackground(modele.getCouleur());
		btnTableau.setPreferredSize(new Dimension(250, 250));
		btnTableau.addActionListener(this);
		btnTableau.setActionCommand(ACTION_ACCEDER);

		// On ajoute le bouton au contrôleur
		add(btnTableau);

	}

	/**
	 * Cette méthode est appelée lorsque l'utilisateur clique sur le bouton pour
	 * accéder au tableau.
	 * Elle va fermer la fenêtre actuelle, et ouvrir la fenêtre
	 * {@link FenetreDansTableau} correspondant au tableau.
	 * 
	 * @param e : l'évènement qui a été déclenché
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_ACCEDER)) {

			// On ferme la fenêtre actuelle
			vue.dispose();

			// On ouvre la fenêtre du tableau
			FenetreDansTableau fnTab = new FenetreDansTableau(modele, application);
			application.setTableauActuel(modele);// on affecte a tableauActuel le tableau que l'utilisateur consulte
																						// actuellement
			fnTab.setVisible(true);
		}
	}
}
