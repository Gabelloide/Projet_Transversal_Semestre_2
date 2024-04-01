
package Controleur;

import Vues.*;
import javax.swing.*;
import Modele.*;
import java.awt.event.*;

/**
 * Cette classe est le contrôleur associé à la fenêtre
 * {@link VueModificationPerms}.
 * Il permet d'ajouter un utilisateur au tableau, et par extension avec le
 * contrôleur {@link ControleurPermMembre}, d'agir sur ses permissions.
 * Il permet également de quitter la fenêtre {@link VueModificationPerms}.
 * 
 * @see VueModificationPerms
 * @see ControleurPermMembre
 * @author Soan
 * @author Olivier
 */

public class ControleurModificationPerms extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************
	static public final String ACTION_AJOUTER = "AJOUTER";
	static public final String ACTION_QUITTER = "QUITTER";
	TrelloLitePlus application;
	JButton btnAjouter;
	JButton btnQuitter;
	VueModificationPerms vue;
	Tableau modele;

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurModificationPerms
	 * 
	 * @param vue         : la fenêtre {@link VueModificationPerms} associée à ce
	 *                    contrôleur
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde.
	 */
	public ControleurModificationPerms(VueModificationPerms vue, TrelloLitePlus application) {

		// On initialise les attributs
		this.application = application;
		this.modele = application.getTableauActuel(); // On récupère le tableau actuel
		this.vue = vue;

		// On crée les boutons
		btnAjouter = new JButton("Ajouter");
		btnQuitter = new JButton("Quitter");

		// On ajoute les boutons au JPanel et on associe les actions
		btnAjouter.addActionListener(this);
		btnQuitter.addActionListener(this);
		btnAjouter.setActionCommand(ACTION_AJOUTER);
		btnQuitter.setActionCommand(ACTION_QUITTER);

		add(btnAjouter);
		add(btnQuitter);

		vue.add(this);
	}

	/**
	 * Méthode permettant de gérer les actions sur les boutons
	 * 
	 * @param e : l'évènement qui a été déclenché
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_AJOUTER)) {

			// si l'utilisateur est deja membre du tableau
			boolean trouve = false;
			int i = 0;
			while (!trouve && i < modele.getMembres().size()) {
				// recherche si l'utilisateur fait deja parti des membres du tableau
				if (modele.getMembres().get(i).getEmail()
						.equals(vue.getTxtfMailMembre().getText())) {
					trouve = true;
				} else {
					i++;
				}
			}
			if (trouve) {
				JOptionPane.showMessageDialog(null, "L'utilisateur est déjà membre du Tableau.");
				return;
			}

			// sinon si l'utilisateur essaye d'ajouter un membre vide, on lui affiche un
			// message
			else if (vue.getTxtfMailMembre().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez renseigner un mail pour ajouter un membre.");
				return;
			}
			// Si l'utilisateur essaye d'ajouter sa propre adresse mail, on lui affiche un
			// message
			else if (vue.getTxtfMailMembre().getText().equals(application.getUtilisateurActuel().getEmail())) {
				JOptionPane.showMessageDialog(null, "Vous ne pouvez pas vous ajouter vous même.");
				return;
			} else {
				// Sinon, on part à la recherche de l'utilisateur possédant l'adresse email
				// rentrée par l'utilisateur de l'application
				trouve = false;
				i = 0;
				while (!trouve && i < application.getUtilisateurs().size()) {
					// recherche si l'utilisateur existe sur l'application
					if (application.getUtilisateurs().get(i).getEmail().equals(vue.getTxtfMailMembre().getText())) {
						trouve = true;
					} else {
						i++;
					}
				}
				// Si l'utilisateur existe
				if (trouve) {
					// on ajoute l'utilisateur au tableau et on lui donne par défaut la permission
					// Lecture seule
					modele.ajouterUtilisateur(application.getUtilisateurs().get(i), 2);

					// On redessine la vue
					vue.redessiner();

					// Sinon, l'utilisateur n'existe pas et on affiche un message d'erreur
				} else {
					JOptionPane.showMessageDialog(null, "Utilisateur non trouvé, essayez avec une autre adresse email");
					return;
				}
			}
		}
		// Si l'utilisateur clique sur le bouton quitter, on ferme la fenêtre
		if (e.getActionCommand().equals(ACTION_QUITTER)) {
			vue.dispose();
		}
	}

}
