
package Controleur;

import Modele.*;
import Vues.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe controleur pour l'apercu d'une carte : elle gère tous les boutons et
 * actions correspondant à la vue {@link ApercuCarte}.
 * Le modèle associé à cette vue et à ce contrôleur est une {@link Carte}.
 * Ce contrôleur modifie la carte (modèle) en fonction des différentes actions
 * effectuées sur la vue.
 * Il contient les éléments suivants qui seront rajoutés à la vue et qui sont à
 * l'écoute des actions de l'utilisateur :
 * - un bouton pour modifier la carte (géré par le contrôleur
 * {@link ControleurModificationCarte})<br>
 * - une case à cocher pour indiquer si la carte est terminée ou non<br>
 * 
 * @see ApercuCarte
 * @see Carte
 * @see ControleurModificationCarte
 * 
 * @author Olivier
 * @author Mathis
 */
public class ControleurApercuCarte extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	/** Constante pour l'action du bouton modifier */
	static public final String ACTION_MODIFIER = "MODIFIER";

	/** Constante pour l'action de la case à cocher */
	static public final String ACTION_COCHER = "COCHER";

	private Carte modele;
	private ApercuCarte vue;
	private JButton btnModifier;
	private TrelloLitePlus application;
	private JCheckBox cbTerminee;

	// ***********************************
	// ******** CONSTRUCTEUR *************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurApercuCarte
	 * 
	 * @param modele      : le modèle est une carte qui sera modifiée en fonction
	 *                    des différentes actions de l'utilisateur
	 * @param vue         : la vue est un aperçu de la carte qui sera actualisée en
	 *                    fonction du contenu du modèle
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde
	 */
	public ControleurApercuCarte(Carte modele, ApercuCarte vue, TrelloLitePlus application) {

		// On récupère les paramètres
		this.modele = modele;
		this.vue = vue;
		this.application = application;

		// Ajout d'un bouton avec une image
		Icon icon = new ImageIcon("modifier.png"); // Chemin relatif de l'image
		btnModifier = new JButton(icon);

		// redimensionner le bouton
		setPreferredSize(new Dimension(30, 20));
		// redimensionner l'image dans le button
		Image image = ((ImageIcon) icon).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		// creer une nouvelle image avec l'image redimensionnée
		Icon nouvelleIcone = new ImageIcon(image);
		// ajout de la nouvelle image dans le bouton
		btnModifier.setIcon(nouvelleIcone);

		// Le controleur écoute le bouton
		btnModifier.addActionListener(this);
		btnModifier.setActionCommand(ACTION_MODIFIER);

		// Ajout d'une case à cocher 'carte terminée' au panel des étiquettes
		cbTerminee = new JCheckBox("Carte terminée?");
		// On rend le fond du panel transparent pour ne pas interférer avec le fond du
		// panel général (ApercuCarte)
		cbTerminee.setOpaque(false);

		// La case est cochée si la carte est terminée, décochée sinon (on récupère
		// l'état de la carte (modele))
		cbTerminee.setSelected(modele.getEtat());

		// Le controleur écoute la case à cocher
		cbTerminee.addActionListener(this);
		cbTerminee.setActionCommand(ACTION_COCHER);

		// Ajout de notre bouton et checkBox à la vue
		vue.ajouterAPanelEtiquettes(cbTerminee);

		// rendre utilisable la checkbox uniquement si l'utilisateur est admin ou
		// proprio

		if (modele.getListeParent().getTableauParent() != null) {
			// La liste parente de cette carte n'aurait pas de tableau parent dans le cas d'une recherche annulée (on évite donc une erreur)

			int ind = modele.getListeParent().getTableauParent().getIndexUtilisateur(application.getUtilisateurActuel());
			if (modele.getListeParent().getTableauParent().getPermissions().get(ind) == 2) {
				cbTerminee.setEnabled(false);
			}
		}

		vue.ajouterAPanelEtiquettes(btnModifier);
	}

	// ***********************************
	// ********** METHODES ***************
	// ***********************************

	/**
	 * Méthode qui gère les différentes actions de l'utilisateur (en fonction des
	 * différents écouteurs placés sur les éléments ajoutés la vue)
	 * 
	 * @param e : l'événement qui a déclenché l'action
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_MODIFIER)) {

			// On ouvre la fenetre de modification de la carte
			VueModificationCarte vueModifCarte = new VueModificationCarte(modele);

			// Création du controleur associé
			new ControleurModificationCarte(modele, vueModifCarte, application);

			// On rend la fenetre visible
			vueModifCarte.setVisible(true);
		}

		if (e.getActionCommand().equals(ACTION_COCHER)) {

			// On remet le fond de l'apercu de la carte en blanc, car cocher la carte peut
			// retirer l'avertissement pour un retard
			vue.setBackground(null);

			// Avertir l'utilisateur s'il essaye de terminer une carte mais que la carte
			// liée n'est pas terminée
			// On vérifie dans un premier temps qu'il existe une carte liée, puis que la
			// carte liée n'est pas terminée
			if (modele.getCarteJointe() != null && modele.getCarteJointe().getEtat() == false && modele.getEtat() == false) {
				JOptionPane.showMessageDialog(null,
						"La carte liée n'est pas terminée, vous ne pouvez pas terminer cette carte");
				vue.redessiner();
			} else {
				// On change l'état de la carte
				modele.changerEtat();
				// On met à jour la vue
				vue.redessiner();
			}
		}

	}

}
