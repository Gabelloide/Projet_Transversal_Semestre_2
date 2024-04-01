package Vues;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * Cette classe implémente une vue de type JFrame composée de
 * plusieurs
 * JPanel, representant le menu pour créer une carte.
 * Utilise : {@link Vues.VueNom}, {@link Vues.VueDescription} et
 * {@link Vues.VueEtiquette}.
 * {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * {@link Controleur.ControleurCreationCarte} pour des informations
 * complémentaires sur
 * le Controleur associé.
 * 
 * @see Vues.VueNom
 * @see Vues.VueDescription
 * @see Vues.VueEtiquette
 * @see Modele.Carte
 * @see Controleur.ControleurCreationCarte
 * @author Soan
 */

public class VueCreationCarte extends JFrame {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private VueNom vNom;
	private VueDescription vDesc;
	private VueEtiquette vEtiquette;
	private JButton btnCreer;

	// ***********************************
	// ****** CONSTRUCTEUR ***************
	// ***********************************

	/**
	 * Constructeur de la classe VueCreationCarte.
	 * Intialise les différentes vues (sous composants de la vue) et les ajoute à la vue.
	 * @see Vues.VueNom
   * @see Vues.VueDescription
   * @see Vues.VueEtiquette
	 */
	public VueCreationCarte() {
		vNom = new VueNom();
		vEtiquette = new VueEtiquette();
		vDesc = new VueDescription();

		setLayout(new FlowLayout());

		add(vNom);
		add(vDesc);
		add(vEtiquette);

		this.setSize(new Dimension(350, 475));
		this.setTitle("Creation Carte");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	// ***********************************
	// ***** GETTERS & SETTERS ***********
	// ***********************************

	public VueNom getvNom() {
		return vNom;
	}

	public VueDescription getvDesc() {
		return vDesc;
	}

	public VueEtiquette getvEtiquette() {
		return vEtiquette;
	}

	public JButton getBtnCreer() {
		return btnCreer;
	}

}
