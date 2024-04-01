
package Controleur;

import Modele.*;
import Vues.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Cette classe implémente le contrôleur permettant d'ecrire
 * un nouveau commentaire d'une carte.
 * Cette classe est une composante de la classe
 * {@link Controleur.ControleurModificationCarte} car sa vue
 * ({@link VueEcrireCommentaire})
 * est une composante de la vue {@link VueModificationCarte}.
 * {@link Modele.Commentaire} pour des informations complémentaires sur
 * la
 * classe Commentaire.
 * {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * {@link VueEcrireCommentaire} pour des informations
 * complémentaires sur
 * la vue associée.
 *
 * @see Modele.Commentaire
 * @see Modele.Carte
 * @see Vues.VueEcrireCommentaire
 * @see Controleur.ControleurModificationCarte
 * @author Soan
 * @author Olivier
 */

public class ControleurEcrireCommentaire extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************
	static public final String ACTION_ENVOYER = "ENVOYER";

	private Carte modele;
	private VueEcrireCommentaire vue;
	private JButton btnEnvoyer;
	private ControleurModificationCarte controleurModificationCarte;
	private TrelloLitePlus application; // L'instance de l'application

	// ***********************************
	// ******** CONSTRUCTEUR *************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurEcrireCommentaire
	 * 
	 * @param modele                      : la carte à commenter
	 * @param vue                         : la vue associée à ce contrôleur :
	 *                                    {@link VueEcrireCommentaire}
	 * @param controleurModificationCarte : le contrôleur contenant ce contrôleur :
	 *                                    {@link ControleurModificationCarte}
	 * @param application                 : l'application est le modèle de
	 *                                    l'application dans son ensemble. Cet
	 *                                    élément est notamment utile pour la
	 *                                    sauvegarde.
	 */
	public ControleurEcrireCommentaire(Carte modele, VueEcrireCommentaire vue,
			ControleurModificationCarte controleurModificationCarte, TrelloLitePlus application) {

		// On initialise les attributs
		this.modele = modele;
		this.application = application;
		this.vue = vue;
		this.controleurModificationCarte = controleurModificationCarte;

		// On ajoute le bouton envoyer
		btnEnvoyer = new JButton("Commenter");

		add(btnEnvoyer);
		vue.add(this, BorderLayout.SOUTH);

		// On ajoute les actions
		btnEnvoyer.addActionListener(this);
		btnEnvoyer.setActionCommand(ACTION_ENVOYER);

	}

	// ***********************************
	// ********** METHODES ***************
	// ***********************************

	/**
	 * Méthode permettant de gérer les actions de l'utilisateur
	 * 
	 * @param e : l'action de l'utilisateur
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		// Si l'utilisateur clique sur le bouton envoyer
		if (e.getActionCommand().equals(ACTION_ENVOYER)) {

			// On vérifie que le commentaire n'est pas vide
			if (vue.getZoneCommentaire().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez renseigner un contenu pour le commentaire à creer.");
				return;
			} else {
				// On ajoute le commentaire à la carte, en précisant que l'auteur est
				// l'utilisateur actuel
				Utilisateur utilisateur = application.getUtilisateurActuel();
				modele.ajouterCommentaire(new Commentaire(vue.getZoneCommentaire().getText(), utilisateur));

				// On effectue une pseudo-actualisation de la fenêtre en la fermant et en en
				// réouvrant une autre.
				controleurModificationCarte.getModifCarte().dispose();

				// Sauvegarde de la vue
				VueModificationCarte vue = new VueModificationCarte(modele);

				// Création du controleur associé
				new ControleurModificationCarte(modele, vue, application);

				// On affiche la vue
				vue.setVisible(true);
			}
		}
	}

	/**
	 * Méthode permettant de récupérer le bouton envoyer
	 * 
	 * @return JButton : le bouton envoyer
	 */
	public JButton getBtnEnvoyer() {
		return btnEnvoyer;
	}

}
