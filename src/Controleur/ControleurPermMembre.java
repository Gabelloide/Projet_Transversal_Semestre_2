package Controleur;

import Vues.*;
import javax.swing.*;
import Modele.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;

/**
 * Cette classe est une composante de la classe {@link VueModificationPerms} :
 * elle est en relation avec la vue {@link VueMembre}.
 * Ce duo Vue-Contrôleur implémente les fonctionnalités de modification des
 * permissions d'un membre.
 * Il est possible d'attribuer une des trois permissions suivantes à un membre
 * donné : propriétaire, administrateur ou lecture seule.
 * Ce contrôleur permet de supprimer le membre du tableau (il n'y a plus accès),
 * mais également de modifier ses permissions à l'aide d'un bouton appliquer et
 * d'une combobox.
 * 
 * @see VueModificationPerms
 * @see VueMembre
 * @author Soan
 * @author Olivier
 * 
 */
public class ControleurPermMembre extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************
	static public final String ACTION_APPLIQUER = "APPLIQUER";
	static public final String ACTION_SUPPRIMER = "SUPPRIMER";
	TrelloLitePlus application;
	VueMembre vue;
	VueModificationPerms vueFn;
	Tableau modele;
	JComboBox<String> cbPerm;
	JButton btnAppliquer;
	JButton btnSupprimer;

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurPermMembre
	 * 
	 * @param vue         : la vue du membre (son nom, sa permission, son bouton de
	 *                    suppression, le bouton appliquer)
	 * @param vueFn       : la vue de la fenêtre parente (instance de la classe
	 *                    {@link VueMembre})
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde.
	 */
	public ControleurPermMembre(VueMembre vue, VueModificationPerms vueFn, TrelloLitePlus application) {

		// On initialise les attributs
		this.application = application;
		this.vue = vue;
		this.vueFn = vueFn;
		this.modele = application.getTableauActuel();

		// On crée les boutons
		btnAppliquer = new JButton("Appliquer");
		btnAppliquer.addActionListener(this);
		btnAppliquer.setActionCommand(ACTION_APPLIQUER);

		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(this);
		btnSupprimer.setActionCommand(ACTION_SUPPRIMER);

		btnSupprimer.setBorder(BorderFactory.createLineBorder(Color.red));

		// On crée la combobox
		cbPerm = new JComboBox<String>();

		// ajout des items a la combobox
		cbPerm.addItem("Propriétaire");
		cbPerm.addItem("Administrateur");
		cbPerm.addItem("Lecture seule");

		// parcours de l'arraylist des membres du tableau actuel
		boolean trouve = false;
		int i = 0;
		while (!trouve && i < modele.getMembres().size()) {
			if (modele.getMembres().get(i) == vue.getMembre()) {
				trouve = true;
			} else {
				i++;
			}
		}
		// si l'utilisateur est trouve, alors choisit par default la permission actuelle
		// du membre
		// Cela permet d'afficher la permission actuelle du membre dans la combobox
		if (trouve) {
			cbPerm.setSelectedIndex(modele.getPermissions().get(i));
		}

		// On ajoute les composants au panel avec un layout en flow
		setLayout(new FlowLayout());

		add(cbPerm);
		add(btnAppliquer);
		add(btnSupprimer);
		vue.add(this);

	}

	/**
	 * Méthode qui permet de gérer les actions de l'utilisateur sur les boutons
	 * 
	 * @param e : l'événement qui a été déclenché
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// recuperation de l'index de l'utilisateur actuel et donc de sa permission
		int indUserActuel = modele.getIndexUtilisateur(application.getUtilisateurActuel());

		// recuperation de l'index de l'utilisateur qui va être manipulé et donc de sa
		// permission
		int indMembre = modele.getIndexUtilisateur(vue.getMembre());

		if (e.getActionCommand().equals(ACTION_APPLIQUER)) {

			// si l'utilisateur actuel est le proprietaire
			if (modele.getPermissions().get(indUserActuel) == 0) {

				// si on choisit l'item Proprietaire, on demande confirmation pour transférer la
				// propriété
				if (cbPerm.getSelectedIndex() == 0) {
					int confirmDialogResult = JOptionPane.showConfirmDialog(
							this,
							"Êtes-vous sûr de vouloir transférer la propriété ?",
							"Confirmation",
							JOptionPane.YES_NO_OPTION);

					// si l'utilisateur confirme, on transfère la propriété
					if (confirmDialogResult == JOptionPane.YES_OPTION) {
						// la methode gère le transfert de propriété et utilisateur actuel devient admin
						modele.setPerm(vue.getMembre(), 0);

					}
				} else {
					// dans les autres cas, on modifie la permission du membre
					modele.setPerm(vue.getMembre(), cbPerm.getSelectedIndex());
				}

				// redessiner la vue globale
				vueFn.redessiner();
			}

			// sinon si l'utilisateur actuel est admin
			else if (modele.getPermissions().get(indUserActuel) == 1) {

				// si on modifie les permissions du proprietaire, on affiche un message d'erreur
				// car cela n'est pas possible
				if (modele.getPermissions().get(indMembre) == 0) {
					// Page d'avertissement
					JOptionPane.showMessageDialog(null,
							"En tant qu'administrateur, il vous est impossible de modifier les permissions du proprétaire.");

				}
				// si on veut modifier les permissions de quelqu'un en proprietaire
				else if (cbPerm.getSelectedIndex() == 0) {
					// Page d'avertissement
					JOptionPane.showMessageDialog(null,
							"En tant qu'administrateur, il vous est impossible de promouvoir cette personne propriétaire.");
				}
				// sinon si on modifie les permisisons d'un utilisateur de Lecture seule en
				// administateur
				else if (cbPerm.getSelectedIndex() == 1) {
					// Page d'avertissement
					JOptionPane.showMessageDialog(null,
							"En tant qu'administrateur, il vous est impossible de rétrograder cet utilisateur au rang de lecture seule.");

				}
				// sinon si on modifie les permisisons d'un admin en Lecture seule
				else if (cbPerm.getSelectedIndex() == 2) {
					// pop up
					JOptionPane.showMessageDialog(null,
							"En tant qu'administrateur, il vous est impossible de modifier les permissions d'un autre administrateur.");
				}

				// redessiner la vue globale
				vueFn.redessiner();
			}

		}

		// si on appuie sur le bouton supprimer, on va retirer le membre sélectionné du
		// tableau.
		if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {

			// Si l'utilisateur actuel est proprietaire, il peut supprimer tout le monde
			if (modele.getPermissions().get(indUserActuel) == 0) {

				// Si le propriétaire veut supprimer un membre, on envoie d'abord une fenêtre de
				// confirmation
				int confirmDialogResult = JOptionPane.showConfirmDialog(
						this,
						"Êtes-vous sûr de vouloir supprimer cet utilisateur du tableau ?",
						"Confirmation",
						JOptionPane.YES_NO_OPTION);

				// Si le propriétaire confirme, on supprime le membre
				if (confirmDialogResult == JOptionPane.YES_OPTION) {
					modele.supprimerUtilisateur(vue.getMembre());

					// redessiner la vue globale
					vueFn.redessiner();
				}
			}
			// si on est administrateur, on peut supprimer seulement les utilisateurs en
			// lecture seule
			else if (modele.getPermissions().get(indMembre) == 2) {

				// Si l'administrateur veut supprimer un membre, on envoie d'abord une fenêtre
				// de confirmation
				int confirmDialogResult = JOptionPane.showConfirmDialog(
						this,
						"Êtes-vous sûr de vouloir supprimer cet utilisateur du tableau ?",
						"Confirmation",
						JOptionPane.YES_NO_OPTION);

				// Si l'administrateur confirme, on supprime l'utilisateur
				if (confirmDialogResult == JOptionPane.YES_OPTION) {

					modele.supprimerUtilisateur(vue.getMembre());

					// redessiner la vue globale
					vueFn.redessiner();
				}

			}

			// si on est administrateur et que l'utilisateur ciblé est administrateur, on ne
			// peut pas le supprimer
			else if (modele.getPermissions().get(indMembre) == 1) {
				// Page d'avertissement
				JOptionPane.showMessageDialog(null,
						"En tant qu'administrateur, il vous est impossible de supprimer un autre administrateur.");
			}

			// si on est administrateur et que l'utilisateur ciblé est le propriétaire,
			// impossible de le supprimer
			else if (modele.getPermissions().get(indMembre) == 0) {
				// Page d'avertissement
				JOptionPane.showMessageDialog(null,
						"En tant qu'administrateur, il vous est impossible de supprimer le proprétaire.");
			}

		}
	}

}
