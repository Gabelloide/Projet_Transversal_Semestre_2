package Vues;

import java.awt.Color;

import javax.swing.*;

import Modele.*;

/**
 * Cette Vue est une sous-composante utilisée dans d'autres vues.
 * Les vues concernées sont : {@link Vues.VueModificationCarte}
 * Cette classe implémente une vue de type JPanel composée
 * d'un
 * JLabel "Liée à :" et d'une JCombobox avec le nom des autres cartes
 * de la liste. Elle represente le menu de selection de la carte à lier.
 * {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * 
 * @author Soan
 */

public class VueLiaisonCarte extends JPanel {

	// *********************************
	// ******* ATTRIBUTS ***************
	// *********************************

	private JLabel lblLiaison;
	private JComboBox<String> cbCartes;


	// *********************************
	// ******** CONSTRUCTEUR ***********
	// *********************************

	/**
	 * Constructeur de la vue. Initialise une comboBox permettant de choisir la carte à lier au modèle.
	 * @param carte la carte que l'on veut lier à une autre carte
	 */
	public VueLiaisonCarte(Carte carte) {
		// initialisation des elements
		lblLiaison = new JLabel("Liée à :");
		cbCartes = new JComboBox<String>();

		// ajout des items à la combobox
		cbCartes.addItem("");
		for (int i = 0; i < carte.getListeParent().getContenuListe().size(); i++) {
			// exclure la carte actuelle car ne peut pas etre liee a elle meme
			// et les cartes deja liees a la carte actuelle
			if (carte.getListeParent().getContenuListe().get(i) != carte
					&& carte.getListeParent().getContenuListe().get(i).getCarteJointe() != carte) {
				cbCartes.addItem(carte.getListeParent().getContenuListe().get(i).getTitre());
			}

		}
		// choisir par default la carte jointe si non nulle
		if (carte.getCarteJointe() != null) {
			String cartejointe = carte.getCarteJointe().getTitre();
			cbCartes.setSelectedItem(cartejointe);
		}

		// ajout des elements au panel
		add(lblLiaison);
		add(cbCartes);

		// bordure du panel en bleu et definition de la taille
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		setSize(300, 150);
	}

	// *********************************
	// ******** GETTERS ****************
	// *********************************

	/**
	 * Getter de la combobox de la vue
	 * @return la combobox
	*/
	public JComboBox<String> getCbCartes() {
		return cbCartes;
	}

}
