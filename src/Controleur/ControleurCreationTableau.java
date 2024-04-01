
package Controleur;

import Modele.*;
import Vues.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

/**
 * Cette classe permet de créer un tableau : elle représente un bouton
 * qui est présent dans la fenêtre pop-up {@link VueCreationTableau}.
 * Ce bouton permet de créer un tableau et de le rajouter à l'utilisateur
 * courant.
 * L'utilisateur va sélectionner ses différentes préférences pour le modèle
 * Tableau, puis va valider son choix en cliquant sur le
 * bouton généré par ce contrôleur.
 * 
 * @see VueCreationTableau
 * @see Tableau
 * @author Yannick
 * @author Olivier
 */

public class ControleurCreationTableau extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	static public final String ACTION_CREER = "CREER";
	private Tableau modele;
	private VueCreationTableau vue;
	private JButton btnCreer;
	private TrelloLitePlus application;

	private Utilisateur user; // utilisateur courant
	private FenetreUtilisateur fenetreUtilisateur; // fenetre parent

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurCreationTableau
	 * 
	 * @param modele             : le modèle du tableau
	 * @param vue                : la vue du tableau (qui va contenir le bouton)
	 * @param fenetreUtilisateur : la fenêtre parent contenant la vue
	 * @param user               : l'utilisateur courant
	 * @param application        : l'application est le modèle de l'application dans
	 *                           son ensemble. Cet élément est notamment utile pour
	 *                           la sauvegarde.
	 */
	public ControleurCreationTableau(Tableau modele, VueCreationTableau vue, FenetreUtilisateur fenetreUtilisateur,
			Utilisateur user, TrelloLitePlus application) {

		// On initialise les attributs
		this.application = application;
		this.modele = modele;
		this.user = user;
		this.vue = vue;
		this.fenetreUtilisateur = fenetreUtilisateur;

		// On crée le bouton
		btnCreer = new JButton("Créer");
		btnCreer.addActionListener(this);
		btnCreer.setActionCommand(ACTION_CREER);

		// On l'ajoute à la vue
		vue.add(btnCreer);
	}

	/**
	 * Méthode permettant de gérer les actions de l'utilisateur
	 * 
	 * @param e : l'évènement qui a été déclenché
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		// Si on appuie sur le bouton "Créer"
		if (e.getActionCommand().equals(ACTION_CREER)) {

			// Le tableau est null à ce stade.
			// Cependant l'utilisateur a appuyé sur créer, alors on le construit
			// Car il y a une volonté d'en créer un
			modele = new Tableau("");

			// impossible de creer un tableau sans nom (affichage d'un avertissement)
			if (vue.getvNom().getTxtfNom().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez renseigner un nom pour le tableau à creer.");
				return;
			} else {
				// Le titre de notre tableau (modele) devient celui rentré dans le champ de
				// texte de la vue.

				// modification du nom
				modele.setTitre(vue.getvNom().getTxtfNom().getText());

				// On ferme notre vueCreationTableau
				vue.dispose();

				// On définit la couleur du tableau :
				// On récupère la couleur sélectionnée dans la vueCreationTableau
				Color couleur = vue.getColorChooser();

				// On l'applique au tableau
				modele.setCouleur(couleur);

				// On ajoute l'utilisateur au tableau en tant que proprietaire
				modele.ajouterUtilisateur(user, 0);

				// On redessine la fenetre utilisateur car un tableau a été créé.
				fenetreUtilisateur.redessiner();
			}
		}
	}

	// *****************************
	// ***** GETTERS & SETTERS *****
	// *****************************

	public Tableau getTableauCree() {
		return modele;
	}

	public TrelloLitePlus getApplication() {
		return application;
	}

	public void setApplication(TrelloLitePlus application) {
		this.application = application;
	}

}
