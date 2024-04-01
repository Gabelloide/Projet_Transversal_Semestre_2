package Vues;

import java.awt.*;
import javax.swing.*;

import Controleur.*;
import Modele.*;

/**
 * Cette vue est la représentation graphique d'une liste. Elle va accueillir des
 * vues de type {@link ApercuCarte}.
 * Elle affiche dans un premier temps le titre de la liste.
 * Le contrôleur associé est {@link ControleurConteneurListe}, il implémente le
 * bouton de suppression de liste et le bouton d'ajout de carte.
 * 
 * @see Modele.Liste
 * @see Controleur.ControleurConteneurListe
 * @see Vues.ApercuCarte
 * @author Olivier
 */

public class ConteneurListe extends JPanel {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private TrelloLitePlus application;
	private Liste modele;
	private String nomConteneur;
	private FenetreDansTableau fenetreParent; // La fenêtre dans laquelle se trouve la vue

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe ConteneurListe
	 * 
	 * @param modele        : le modèle de la liste
	 * @param fenetreParent : la fenêtre dans laquelle se trouve la vue
	 * @param application   : l'application est le modèle de l'application dans son
	 *                      ensemble. Cet élément est notamment utile pour la
	 *                      sauvegarde.
	 */
	public ConteneurListe(Liste modele, FenetreDansTableau fenetreParent, TrelloLitePlus application) {
		this.application = application;
		this.modele = modele;
		this.nomConteneur = modele.getTitre();
		this.fenetreParent = fenetreParent;

		// Fond transparent (pour voir le fond de la fenêtre)
		this.setOpaque(false);

		// On associe la vue au modèle
		modele.setVueListe(this);

		// Définition de la taille du conteneur
		setSize(new Dimension(1000, 1000));

		// Définition du schéma (layout)
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// Contour
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

		// A la construction du conteneurListe, on va ajouter toutes les cartes qui sont
		// présentes dans la liste associée à l'aide de redessiner qui actualise en
		// fonction du modèle.
		redessiner();
	}

	// ***********************************
	// ********** METHODES ***************
	// ***********************************

	/**
	 * Effectue l'actualisation de l'interface graphique en fonction des
	 * caractéristiques du modèle.
	 * Cette méthode est appelée à chaque fois que le modèle est modifié.
	 */
	public void redessiner() {
		// On supprime tous les composants de la vue
		removeAll();

		// Ajout du titre de la liste
		JLabel lblNom = new JLabel(nomConteneur);
		lblNom.setHorizontalAlignment(SwingConstants.CENTER);
		// Augmentation de la hauteur du label nom
		lblNom.setPreferredSize(new Dimension(1000, 50));
		this.add(lblNom);

		new ControleurConteneurListe(modele, this, application);

		// Si le contrôleur contient des ApercuCarte qui ne sont pas dans le modèle
		// (cartes supprimées), on les supprime de la vue.
		for (int i = 0; i < this.getComponentCount(); i++) {
			// Pour chaque composant de la vue
			if (this.getComponent(i) instanceof ApercuCarte) {
				// Si le composant est un ApercuCarte (donc qu'une carte est associée)
				// Alors on le supprime de la vue si elle n'est pas dans le modèle
				ApercuCarte apercuCarte = (ApercuCarte) this.getComponent(i);
				if (!modele.getContenuListe().contains(apercuCarte.getCarte())) {
					this.remove(apercuCarte);
				}
			}
		}

		// Pour chaque carte présente dans la liste (modele) dont ce contrôleur est le
		// contrôleur
		for (int i = 0; i < modele.getContenuListe().size(); i++) {
			Carte carte = modele.getContenuListe().get(i);

			ApercuCarte apercuCarte = new ApercuCarte(carte, application);
			carte.setApercuCarte(apercuCarte); // On associe la vue au modèle
			this.add(apercuCarte); // On ajoute la carte à la vue
		}

		// On redessine la vue correctement
		repaint();
		revalidate();
	}

	// ***********************************
	// ***** GETTERS & SETTERS ***********
	// ***********************************

	/**
	 * Retourne le modèle associé à la vue
	 * 
	 * @return le modèle associé à la vue
	 */
	public Liste getModele() {
		return modele;
	}


	/**
	 * Retourne la fenêtre dans laquelle se trouve la vue
	 * 
	 * @return la fenêtre dans laquelle se trouve la vue
	 */
	public FenetreDansTableau getFenetreParent() {
		return fenetreParent;
	}

}
