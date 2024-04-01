
package Controleur;

import Modele.*;
import Vues.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Cette classe implémente le contrôleur permettant de creer
 * une carte depuis une liste.
 * {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * {@link Vues.VueCreationCarte} pour des informations complémentaires
 * sur
 * la vue associée.
 * 
 * @see Modele.Carte
 * @see Vues.VueCreationCarte
 * @author Soan
 * @author Olivier
 */

public class ControleurCreationCarte extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	static public final String ACTION_CREER = "CREER";

	private Carte modele;
	private VueCreationCarte vue;
	private JButton btnCreer;
	private TrelloLitePlus application;

	// Cette vue sera actualisée lors de la création d'une carte
	private ConteneurListe conteneurListe;

	// La liste dans laquelle on va ajouter la carte
	private Liste listeContenue;



	// ***********************************
	// ******** CONSTRUCTEUR *************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurCreationCarte
	 * 
	 * @param vue            : la vue associée à ce contrôleur :
	 *                       {@link VueCreationCarte}
	 * @param conteneurListe : la liste dans laquelle on va ajouter la carte :
	 *                       {@link ConteneurListe}
	 * @param application    : l'application est le modèle de l'application dans son
	 *                       ensemble. Cet élément est notamment utile pour la
	 *                       sauvegarde.
	 */
	public ControleurCreationCarte(VueCreationCarte vue, ConteneurListe conteneurListe,
			TrelloLitePlus application) {

		// On initialise les attributs
		this.modele = new Carte(""); // On initialise le modèle. Le titre sera changé par la suite.
		this.vue = vue;
		this.conteneurListe = conteneurListe;
		this.application = application;

		// On récupère la liste dans laquelle on va ajouter la carte
		listeContenue = conteneurListe.getModele();

		// On crée le bouton
		btnCreer = new JButton("Créer carte");
		btnCreer.setPreferredSize(new Dimension(150, 75));
		btnCreer.setBorder(BorderFactory.createLineBorder(Color.blue));
		add(btnCreer);
		vue.add(this);
		btnCreer.addActionListener(this);
		btnCreer.setActionCommand(ACTION_CREER);

	}

	// ***********************************
	// ********** METHODES ***************
	// ***********************************

	/**
	 * Cette méthode permet de gérer les actions effectuées par l'utilisateur
	 * 
	 * @param e : l'action effectuée par l'utilisateur
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_CREER)) {

			// Sinon, on vérifie qu'une carte du même nom d'existe pas déjà dans la liste
			boolean trouve = false;
			int i = 0;
			while (!trouve && i < listeContenue.getContenuListe().size()) {
				if (vue.getvNom().getTxtfNom().getText()
						.equals(listeContenue.getContenuListe().get(i).getTitre())) {

					trouve = true;
				}
				i++;
			}

			// Il faut vérifier que le nom de la carte n'est pas vide : on affiche un
			// message d'erreur si c'est le cas
			if (vue.getvNom().getTxtfNom().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez renseigner un nom pour la carte à créer.");
				return;
			} else if (trouve) {
				JOptionPane.showMessageDialog(null,
						"Veuillez renseigner un autre titre pour la carte à créer car ce titre est déjà présent.");
				return;
			} else {
				// Récuperation des informations de la vue pour creer carte ensuite

				// recuperation du nom
				String nom = vue.getvNom().getTxtfNom().getText();

				// modification du nom
				modele.setTitre(nom);

				// recuperation de la description
				String desc = vue.getvDesc().getTxtfDesc().getText();

				// modification de la description
				modele.setDescription(desc);

				// recuperation et ajout des etiquettes à la carte
				if (vue.getvEtiquette().getCbRouge().isSelected()) {
					modele.ajouterEtiquette(new Etiquette(Color.RED));
				}
				if (vue.getvEtiquette().getCbBleu().isSelected()) {
					modele.ajouterEtiquette(new Etiquette(Color.BLUE));
				}
				if (vue.getvEtiquette().getCbJaune().isSelected()) {
					modele.ajouterEtiquette(new Etiquette(Color.YELLOW));
				}
				if (vue.getvEtiquette().getCbVert().isSelected()) {
					modele.ajouterEtiquette(new Etiquette(Color.GREEN));
				}
				if (vue.getvEtiquette().getCbViolet().isSelected()) {
					modele.ajouterEtiquette(new Etiquette(Color.MAGENTA));
				}

				// La carte est créée, on peut donc fermer la fenêtre
				vue.dispose();

				// On ajoute la carte à la liste
				listeContenue.ajouterCarte(modele);
				
				// On redessine la liste pour afficher la carte créée
				conteneurListe.redessiner();
			}

		}
	}

	// ***********************************
	// ******** GETTERS & SETTERS ********
	// ***********************************

	public Carte getCarteCree() {
		return modele;
	}

	public TrelloLitePlus getApplication() {
		return application;
	}

}
