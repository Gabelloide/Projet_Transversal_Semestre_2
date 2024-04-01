package Vues;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * 
 * Cette classe implémente une vue permettant de rentrer les
 * différentes informations nécessaires à la création d'un tableau.
 * Elle est composée de plusieurs JPanel, et utilise
 * {@link Vues.VueNom} notamment.
 * Pour créer le tableau de manière effective, il faut utiliser le
 * Controleur associé : {@link Controleur.ControleurCreationTableau}.
 * 
 * @author Yannick
 * @author Olivier
 */

public class VueCreationTableau extends JFrame {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private VueNom vNom;
	private Color colorChooser; // La couleur choisie pour le fond du tableau
	private JPanel apercuCouleur;
	private JButton selectCouleur;

	// ***********************************
	// ****** CONSTRUCTEUR ***************
	// ***********************************

	/**
	 * Constructeur de la classe VueCreationTableau.
	 * Initialise différents composants graphiques pour effectuer efficacement la création d'un tableau.
	 * Le plus notable est le sélecteur de couleur, qui va permettre de choisir la couleur du fond du tableau (attribut tableau.couleur).
	 */
	public VueCreationTableau() {
		vNom = new VueNom();

		apercuCouleur = new JPanel(); // apercu de la couleur
		apercuCouleur.setPreferredSize(new Dimension(150, 30)); // définir la taille de l'aperçu

		apercuCouleur.setBorder(BorderFactory.createLineBorder(Color.black)); // définir une bordure noire

		// Création du bouton qui nous permet de choisir la couleur
		JButton selectCouleur = new JButton("Sélectionne une couleur");

		// Si on appuie sur le bouton, alors :
		selectCouleur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// On affiche la fenêtre de choix de couleur

				colorChooser = JColorChooser.showDialog(apercuCouleur, "Choisissez une couleur", Color.WHITE, false);
				// Si une couleur est choisie, alors on l'affiche dans le panel
				if (colorChooser != null) {
					apercuCouleur.setBackground(colorChooser);
				}
			}
		});

		add(vNom);
		add(selectCouleur);
		add(apercuCouleur);

		setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setSize(new Dimension(350, 475));
		this.setTitle("Créer un Tableau");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// ***********************************
	// ****** GETTERS & SETTERS **********
	// ***********************************

	/**
	 * Getter de l'attribut vNom.
	 * @return vNom
	 */
	public VueNom getvNom() {
		return vNom;
	}

	/**
	 * Setter de l'attribut vNom.
	 * @param vNom
	 */
	public void setvNom(VueNom vNom) {
		this.vNom = vNom;
	}

	/**
	 * Getter de l'attribut colorChooser.
	 * @return colorChooser
	 */
	public Color getColorChooser() {
		return colorChooser;
	}

	/**
	 * Setter de l'attribut colorChooser.
	 * @param colorChooser
	 */
	public void setColorChooser(Color colorChooser) {
		this.colorChooser = colorChooser;
	}

	/**
	 * Getter de l'attribut apercuCouleur.
	 * @return
	 */
	public JPanel getApercuCouleur() {
		return apercuCouleur;
	}

	/**
	 * Setter de l'attribut apercuCouleur.
	 * @param apercuCouleur
	 */
	public void setApercuCouleur(JPanel apercuCouleur) {
		this.apercuCouleur = apercuCouleur;
	}

	/**
	 * Getter de l'attribut selectCouleur.
	 * @return selectCouleur
	 */
	public JButton getSelectCouleur() {
		return selectCouleur;
	}

	/**
	 * Setter de l'attribut selectCouleur.
	 * @param selectCouleur
	 */
	public void setSelectCouleur(JButton selectCouleur) {
		this.selectCouleur = selectCouleur;
	}

	// ***********************************
	// ***** GETTERS & SETTERS ***********
	// ***********************************

}
