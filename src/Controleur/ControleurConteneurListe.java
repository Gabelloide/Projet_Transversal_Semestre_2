
package Controleur;

import Modele.*;
import Vues.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

/**
 * Le contrôleur de la vue ConteneurListe gère
 * l'actualisation de l'affichage
 * des différentes cartes crées dans la liste de cartes. Il permet
 * également d'ajouter une nouvelle carte à la liste de cartes. Ces
 * cartes suivent ensuite
 * leur comportement habituel grâce à la classe {@link ApercuCarte}.
 * Par ailleurs, le contrôleur implémente également un bouton permettant
 * de supprimer la liste de carte de manière complète.
 * 
 * Le contrôleur est composé des éléments suivants : <br>
 * - un bouton pour ajouter une nouvelle carte <br>
 * - un bouton pour supprimer la liste de cartes
 * 
 * @see ConteneurListe
 * @see ApercuCarte
 * @see Carte
 * @see Liste
 * @author Olivier
 * @author Soan
 */

public class ControleurConteneurListe extends JPanel implements ActionListener {

	// ******************************************
	// *********** ATTRIBUTS ********************
	// ******************************************

	private Liste modele;
	public ConteneurListe vue;
	private JButton btnAjouter;
	private JButton btnSupprListe;
	private TrelloLitePlus application;

	static public final String ACTION_AJOUTER = "AJOUTER";
	static public final String ACTION_SUPPRIMER_LA_LISTE = "SUPPRIMER_LA_LISTE";

	// ******************************************
	// *********** CONSTRUCTEUR *****************
	// ******************************************

	/**
	 * Constructeur de la classe ControleurConteneurListe
	 * 
	 * @param modele      : Une liste de cartes
	 * @param vue         : La vue qui va accueillir les différents éléments
	 *                    graphiques du contrôleur (objet ConteneurListe)
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde.
	 */
	public ControleurConteneurListe(Liste modele, ConteneurListe vue, TrelloLitePlus application) {

		// On initialise les attributs
		this.modele = modele;
		this.vue = vue;
		this.application = application;

		// Fond transparent
		this.setOpaque(false);

		// afficher les boutons uniquement si l'utilisateur est admin ou proprio
		int ind = modele.getTableauParent().getIndexUtilisateur(application.getUtilisateurActuel());
		if (modele.getTableauParent().getPermissions().get(ind) != 2) {

			// On crée les boutons
			btnAjouter = new JButton("Ajouter une nouvelle carte...");
			btnSupprListe = new JButton("Supprimer la liste");
			btnSupprListe.setBackground(Color.RED);

			// Ajout des boutons au contrôleur et du contrôleur à la vue
			add(btnAjouter);
			add(btnSupprListe);

			vue.add(this);

			// Le controleur écoute les boutons
			btnAjouter.addActionListener(this);
			btnSupprListe.addActionListener(this);

			// On définit les actions des boutons
			btnAjouter.setActionCommand(ACTION_AJOUTER);
			btnSupprListe.setActionCommand(ACTION_SUPPRIMER_LA_LISTE);
		}
	}

	// ******************************************
	// *********** METHODES *********************
	// ******************************************

	/**
	 * Méthode permettant de gérer les actions des boutons du contrôleur
	 * 
	 * @param e : l'évènement qui a déclenché l'action
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		// Si l'utilisateur clique sur le bouton ajouter
		if (e.getActionCommand().equals(ACTION_AJOUTER)) {

			// La future carte qui sera potentiellement ajoutée par le contrôleur lors de
			// l'ajout dans une liste (bouton ajouter)
			// Pour l'instant, la future carte crée ne possède pas de nom ni de description.
			// Elles seront modifiées par la suite par le ControleurCreationCarte.
			// Carte futureCarteCree = new Carte("");

			// On va ouvrir la fenetre de création de carte
			VueCreationCarte vueCreationCarte = new VueCreationCarte();

			// Création du controleur associé à la VueCreationCarte. C'est lui qui va gérer la création des cartes pour la liste contrôlée actuellement.
			new ControleurCreationCarte(vueCreationCarte, vue, application);

			// On ajoute la vue à la fenêtre
			vueCreationCarte.setVisible(true);
			// La vueCreationCarte va redessiner le controleurConteneurListe à chaque fois
			// qu'une carte est créée.

		}

		// Si l'utilisateur clique sur le bouton supprimer la liste
		if (e.getActionCommand().equals(ACTION_SUPPRIMER_LA_LISTE)) {

			// On affiche une fenetre de confirmation
			int option = JOptionPane.showConfirmDialog(this,
					"Êtes-vous sûr de vouloir supprimer cette liste ?",
					"Confirmation de suppression",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			// Si l'utilisateur clique sur oui
			if (option == JOptionPane.YES_OPTION) {

				// On supprime toutes les cartes contenues dans la liste
				int i = 0;
				while (i < modele.getContenuListe().size()) {
					// On appelle la méthode supprimerCarte de la classe Liste sur chaque objet
					// Carte de la liste
					modele.supprimerCarte(modele.getContenuListe().get(i));
					i++;
				}

				// On supprime la liste de cartes du tableau parent
				modele.getTableauParent().supprimerListe(modele);

				// On redessine le tableau parent
				vue.getFenetreParent().redessiner();

				repaint();
				revalidate();
			}
		}

	}

}
