package Vues;

import java.awt.Color;
import javax.swing.*;

/**
 * Cette Vue est une sous-composante utilisée dans d'autres vues.
 * Les vues concernées sont : {@link Vues.VueCreationCarte} et {@link Vues.VueModificationCarte}
 * Cette classe implémente une vue de type JPanel composée
 * d'un JLabel
 * "Nom :" et d'un JTextField vide en face
 * Elle représente le menu pour modifier le nom d'une Carte.
 * {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * 
 * @author Soan
 */

public class VueNom extends JPanel {

	// *********************************
	// ******* ATTRIBUTS ***************
	// *********************************

	private JLabel lblNom;
	private JTextField txtfNom;

	// *********************************
	// ******* CONSTRUCTEURS ***********
	// *********************************

	/**
	 * Constructeur de la vue.
	 * Initialise le label explicatif et le champ de texte permettant de rentrer le nom voulu.
	 */
	public VueNom() {

		// Creer un label et un champ de texte
		lblNom = new JLabel("Nom : ");
		txtfNom = new JTextField(15); // 15 = caractere affichable par defaut

		// ajouter lbl et txtf dans le panel
		add(lblNom);
		add(txtfNom);

		// border du panel en bleu
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		this.setSize(300, 150);

	}

	// *********************************
	// ******* GETTERS & SETTERS ********
	// *********************************

	/**
	 * Permet d'obtenir le contenu du champ de texte.
	 * @return le champ de texte
	 */
	public JTextField getTxtfNom() {
		return txtfNom;
	}

	/**
	 * Permet de définit l'instance du champ de texte à utiliser dans la vue.
	 * @param txtfNom le champ de texte à utiliser
	 */
	public void setTxtfNom(JTextField txtfNom) {
		this.txtfNom = txtfNom;
	}

}
