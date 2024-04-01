
package Controleur;

import Vues.*;
import javax.swing.*;
import Modele.*;
import java.awt.event.*;

/**
 * Ce contrôleur est associé à la vue {@link Vues.VueGestionMembresCarte}.
 * Il implémente les actions nécessaires pour ajouter un membre à la carte, ou
 * pour quitter cette fenêtre.
 * Le contenu du champ de texte présent sur la vue est récupéré pour ajouter le
 * membre à la carte s'il existe, via le bouton d'ajout.
 * Le bouton pour quitter la fenêtre est également implémenté ici.
 * 
 * @see Vues.VueGestionMembresCarte
 * @see Modele.Carte
 * @author Soan
 */

public class ControleurGestionMembresCarte extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************
	static public final String ACTION_AJOUTER = "AJOUTER";
	static public final String ACTION_QUITTER = "QUITTER";
	TrelloLitePlus application;
	JButton btnAjouter;
	JButton btnQuitter;
	VueGestionMembresCarte vue;
	Carte modele;

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	public ControleurGestionMembresCarte(VueGestionMembresCarte vue, TrelloLitePlus application) {

		// On initialise les attributs
		this.application = application;
		this.vue = vue;
		this.modele = vue.getModele();

		// afficher le boutons Ajouter uniquement si l'utilisateur est admin ou proprio
		int ind = modele.getListeParent().getTableauParent().getIndexUtilisateur(application.getUtilisateurActuel());
		if (modele.getListeParent().getTableauParent().getPermissions().get(ind) != 2) {

			// On crée le bouton ajouter seulement si l'utilisateur est admin ou proprio
			btnAjouter = new JButton("Ajouter");

			// On ajoute le bouton au JPanel et on associe les actions
			btnAjouter.addActionListener(this);
			btnAjouter.setActionCommand(ACTION_AJOUTER);

			add(btnAjouter);
		}

		
		// le bouton quitter sera toujours présent donc on le crée ici
		btnQuitter = new JButton("Quitter");
		// On ajoute le bouton au JPanel et on associe les actions
		btnQuitter.addActionListener(this);
		btnQuitter.setActionCommand(ACTION_QUITTER);
		add(btnQuitter);


		vue.add(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_AJOUTER)) {

			// si l'utilisateur est deja membre de la carte
			boolean trouve = false;
			int i = 0;
			while (!trouve && i < modele.getMembres().size()) {
				// recherche si l'utilisateur fait deja parti des membres de la carte
				if (modele.getMembres().get(i).getEmail()
						.equals(vue.getTxtfMailMembre().getText())) {
					trouve = true;
				} else {
					i++;
				}
			}
			if (trouve) {
				JOptionPane.showMessageDialog(null, "L'utilisateur est déjà assigné à cette carte.");
				return;
			}
			// sinon si l'utilisateur essaye d'ajouter un membre vide, on lui affiche un
			// message
			else if (vue.getTxtfMailMembre().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez renseigner un mail pour ajouter un membre.");
				return;
			} else {
				// Sinon, on part à la recherche dans la liste des membres du tableau
				// l'utilisateur possédant l'adresse email
				// rentrée par l'utilisateur de l'application
				trouve = false;
				i = 0;
				while (!trouve && i < modele.getListeParent().getTableauParent().getMembres().size()) {
					// recherche si l'utilisateur fait parti des membres du tableau
					if (modele.getListeParent().getTableauParent().getMembres().get(i).getEmail()
							.equals(vue.getTxtfMailMembre().getText())) {
						trouve = true;
					} else {
						i++;
					}
				}
				// Si l'utilisateur existe
				if (trouve) {
					// on ajoute l'utilisateur à la carte
					modele.ajouterMembre(modele.getListeParent().getTableauParent().getMembres().get(i));

					// On redessine la vue
					vue.redessiner();

					// Sinon, on cherche si l'utilisateur existe dans l'application
				} else {
					trouve = false;
					i = 0;
					while (!trouve && i < application.getUtilisateurs().size()) {
						// recherche si l'utilisateur fait parti des membres de l'application
						if ( application.getUtilisateurs().get(i).getEmail()
								.equals(vue.getTxtfMailMembre().getText())) {
							trouve = true;
						} else {
							i++;
						}
					}
					if (trouve){
						JOptionPane.showMessageDialog(null, "Utilisateur trouvé dans l'application, essayez d'abord de l'ajouter au tableau");
						return;
					}else{
						// sinon l'utilisateur n'existe pas
						JOptionPane.showMessageDialog(null, "Utilisateur non trouvé, essayez avec une autre adresse email");
						return;
					}
					
				}
			}
		}
		// Si l'utilisateur clique sur le bouton quitter, on ferme la fenêtre
		if (e.getActionCommand().equals(ACTION_QUITTER)) {
			vue.dispose();
			vue.getConteneurListe().redessiner(); // On redessine la vue globale de la liste
		}
	}

}
