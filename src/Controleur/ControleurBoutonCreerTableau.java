
package Controleur;

import Modele.*;
import Vues.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Ce contrôleur est un bouton présent dans la fenêtre initiale de
 * l'application, permettant de créer un tableau.
 * Ce bouton se manifeste sous la forme d'un gros bouton carré, avec le texte
 * "Créer un tableau".
 * Elle va par la suite appeler la fenêtre pop-up de création de tableau.
 * Consultez {@link VueCreationTableau} pour des informations sur la fenêtre
 * appelée.
 *
 * @see VueCreationTableau
 * @see FenetreUtilisateur
 * @author Olivier
 * @author Yannick
 */
public class ControleurBoutonCreerTableau extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************
	private JButton ajouterTableau;
	private FenetreUtilisateur vue;
	private Utilisateur modele;
	private Tableau futurTableauCree;
	private TrelloLitePlus application;

	static public final String ACTION_CREER = "CREER";

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurBoutonCreerTableau
	 * 
	 * @param modele      : un Utilisateur
	 * @param vue         : le menu principal, où est présent le bouton
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble.
	 *                    Cet élément est notamment utile pour la sauvegarde.
	 */
	public ControleurBoutonCreerTableau(Utilisateur modele, FenetreUtilisateur vue, TrelloLitePlus application) {

		// On initialise les attributs
		this.vue = vue;
		this.modele = modele;
		this.application = application;

		// On crée le bouton
		ajouterTableau = new JButton("Créer un tableau");
		ajouterTableau.setPreferredSize(new Dimension(250, 250));
		ajouterTableau.setForeground(Color.WHITE);
		ajouterTableau.setBackground(Color.BLACK);

		// On ajoute le bouton au contrôleur et on lui affecte un actionListener
		ajouterTableau.addActionListener(this);
		ajouterTableau.setActionCommand(ACTION_CREER);
		add(ajouterTableau);
		vue.addElement(this);
	}

	/**
	 * Lorsque l'on clique sur le bouton créer, on va ouvrir la fenêtre de création
	 * de tableau qui est l'objet suivant : {@link VueCreationTableau}
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_CREER)) {

			// Le contrôleur possède un attribut Tableau qui représente le futur tableau
			// créé. On va le transmettre à la fenêtre de création de tableau.

			// On va ouvrir la fenetre de création de tableau et créer le contrôleur associé
			// à cette fenêtre
			VueCreationTableau vueCreationTableau = new VueCreationTableau();
			new ControleurCreationTableau(futurTableauCree, vueCreationTableau, vue, modele, application);

			// On rend la fenêtre visible
			vueCreationTableau.setVisible(true);
		}
	}

}
