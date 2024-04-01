package Vues;

import java.awt.*;
import javax.swing.*;
import Modele.*;

/**
 * Cette Vue est une sous-composante utilisée dans d'autres vues.
 * Les vues concernées sont : {@link Vues.VueModificationCarte},
 * {@link Vues.VueCreationCarte}
 * Cette classe implémente une vue de type JPanel composée de
 * JCheckBox et
 * de JTextField pour chaque Etiquettes. Elle represente le menu
 * de selection des differentes Etiquettes.
 * {@link Modele.Etiquette} pour des informations complémentaires sur la
 * classe Etiquette.
 * 
 * @author Soan
 */

public class VueEtiquette extends JPanel {

	// *********************************
	// ******* ATTRIBUTS ***************
	// *********************************

	// une checkbox par couleur
	// un txt field par couleur et on aura la couleur grace au background de celui ci
	private JLabel lblEtiquette;
	private JCheckBox cbRouge;
	private JTextField txtfRouge;
	private JCheckBox cbBleu;
	private JTextField txtfBleu;
	private JCheckBox cbJaune;
	private JTextField txtfJaune;
	private JCheckBox cbVert;
	private JTextField txtfVert;
	private JCheckBox cbViolet;
	private JTextField txtfViolet;

	// *********************************
	// ******* CONSTRUCTEURS ***********
	// *********************************

	/**
	 * Constructeur de la vue.
	 * Initialise tous les attributs et mets en place une grille pour disposer les différentes étiquettes que l'on peut cocher.
	 */
	public VueEtiquette() {

		// utilisation d'un grid layout
		setLayout(new GridLayout(7, 2));

		// initialisation de tous les attributs
		// utilisation de label vide pour sauter une case de la grid ( faire un semblant
		// d'alinea)
		lblEtiquette = new JLabel("Etiquettes : ");
		JLabel vide = new JLabel("");

		cbRouge = new JCheckBox();
		txtfRouge = new JTextField(8);
		txtfRouge.setBackground(Color.RED);
		txtfRouge.setEnabled(false);

		cbBleu = new JCheckBox();
		txtfBleu = new JTextField(8);
		txtfBleu.setBackground(Color.BLUE);
		txtfBleu.setEnabled(false);

		cbJaune = new JCheckBox();
		txtfJaune = new JTextField(8);
		txtfJaune.setBackground(Color.YELLOW);
		txtfJaune.setEnabled(false);

		cbVert = new JCheckBox();
		txtfVert = new JTextField(8);
		txtfVert.setBackground(Color.GREEN);
		txtfVert.setEnabled(false);

		cbViolet = new JCheckBox();
		txtfViolet = new JTextField(8);
		txtfViolet.setBackground(Color.MAGENTA);
		txtfViolet.setEnabled(false);

		// ajout des elements au panel
		add(lblEtiquette);
		add(vide);
		add(cbRouge);
		add(txtfRouge);
		add(cbBleu);
		add(txtfBleu);
		add(cbJaune);
		add(txtfJaune);
		add(cbVert);
		add(txtfVert);
		add(cbViolet);
		add(txtfViolet);

		// border du panel en bleu
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

	}

	/**
	 * Constructeur de la vue, qui peut prendre une carte en paramètre pour cette fois ci récupérer l'état des cases à cocher en fonction des étiquettes de la carte.
	 * @param carte La carte dont on veut récupérer les étiquettes.
	 */
	public VueEtiquette(Carte carte) {

		// utilisation d'un grid layout
		setLayout(new GridLayout(7, 2));

		// initialisation de tous les attributs
		// utilisation de label vide pour sauter une case de la grid ( faire un semblant
		// d'alinea)
		lblEtiquette = new JLabel("Etiquettes : ");
		JLabel vide = new JLabel("");

		cbRouge = new JCheckBox();
		txtfRouge = new JTextField(8);
		txtfRouge.setBackground(Color.RED);
		txtfRouge.setEnabled(false);

		cbBleu = new JCheckBox();
		txtfBleu = new JTextField(8);
		txtfBleu.setBackground(Color.BLUE);
		txtfBleu.setEnabled(false);

		cbJaune = new JCheckBox();
		txtfJaune = new JTextField(8);
		txtfJaune.setBackground(Color.YELLOW);
		txtfJaune.setEnabled(false);

		cbVert = new JCheckBox();
		txtfVert = new JTextField(8);
		txtfVert.setBackground(Color.GREEN);
		txtfVert.setEnabled(false);

		cbViolet = new JCheckBox();
		txtfViolet = new JTextField(8);
		txtfViolet.setBackground(Color.MAGENTA);
		txtfViolet.setEnabled(false);

		// coche la checkbox si la carte possede l'etiquette de cette couleur
		// On compare sur les valeurs de Rouge, Bleu, Vert (integers) et non pas sur
		// l'objet entier (pour ne pas comparer les références)
		for (int i = 0; i < carte.getEtiquettes().size(); i++) {

			if (carte.getEtiquettes().get(i).getCouleurRGB() == Color.RED.getRGB()) {
				cbRouge.setSelected(true);
			}
			if (carte.getEtiquettes().get(i).getCouleurRGB() == Color.BLUE.getRGB()) {
				cbBleu.setSelected(true);
			}
			if (carte.getEtiquettes().get(i).getCouleurRGB() == Color.YELLOW.getRGB()) {
				cbJaune.setSelected(true);
			}
			if (carte.getEtiquettes().get(i).getCouleurRGB() == Color.GREEN.getRGB()) {
				cbVert.setSelected(true);
			}
			if (carte.getEtiquettes().get(i).getCouleurRGB() == Color.MAGENTA.getRGB()) {
				cbViolet.setSelected(true);
			}
		}
		// ajout des elements au panel
		add(lblEtiquette);
		add(vide);
		add(cbRouge);
		add(txtfRouge);
		add(cbBleu);
		add(txtfBleu);
		add(cbJaune);
		add(txtfJaune);
		add(cbVert);
		add(txtfVert);
		add(cbViolet);
		add(txtfViolet);

		// border du panel en bleu
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

	}

	// ***********************************
	// ****** GETTERS & SETTERS **********
	// ***********************************

	/**
	 * Retourne la checkbox rouge
	 * @return cbRouge
	 */
	public JCheckBox getCbRouge() {
		return cbRouge;
	}

	/**
	 * Retourne la checkbox bleue
	 * @return cbBleu
	 */
	public JCheckBox getCbBleu() {
		return cbBleu;
	}

	/**
	 * Retourne la checkbox jaune
	 * @return cbJaune
	 */
	public JCheckBox getCbJaune() {
		return cbJaune;
	}

	/**
	 * Retourne la checkbox verte
	 * @return cbVert
	 */
	public JCheckBox getCbVert() {
		return cbVert;
	}

	/**
	 * Retourne la checkbox violette
	 * @return cbViolet
	 */
	public JCheckBox getCbViolet() {
		return cbViolet;
	}

}
