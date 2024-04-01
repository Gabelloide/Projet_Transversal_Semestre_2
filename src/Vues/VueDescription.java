package Vues;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

/**
 * Cette Vue est une sous-composante utilisée dans d'autres vues.
 * Les vues concernées sont : {@link Vues.VueModificationCarte},
 * {@link Vues.VueCreationCarte}
 * Cette classe implémente une vue de type JPanel composée
 * d'un JLabel
 * "Description : " et d'une JTextArea qui peut etre initialisée avec du
 * texte.
 * Elle représente le menu pour modifier la description d'une Carte.
 * {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * 
 * @author Soan
 */

public class VueDescription extends JPanel {

	// *********************************
	// ******* ATTRIBUTS ***************
	// *********************************

	private JLabel lblDesc;
	private JTextArea txtfDesc;

	// *********************************
	// ******* CONSTRUCTEURS ***********
	// *********************************

	/**
	 * Constructeur de la vueDescription
	 * Initialise le label et la zone de texte pour renseigner une description.
	 */
	public VueDescription() {

		// Initialisation du label et d'un champ de texte
		lblDesc = new JLabel("Description : ");
		txtfDesc = new JTextArea(4, 20);
		// 20 = caractere affichable par defaut

		// activation du retour a la ligne
		txtfDesc.setLineWrap(true);

		// ajout des elements au panel en utilisant un borderLayout
		add(lblDesc, BorderLayout.NORTH);

		// creation d'un jScrollPane a partir du textArea pour pouvoir utiliser le
		// scroll
		// s'il s'agrandit (si on a remplit l'espace de base)
		JScrollPane scrollPane = new JScrollPane(txtfDesc);
		add(scrollPane, BorderLayout.CENTER);

		// border du panel en bleu
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		this.setSize(300, 150);

	}

	/**
	 * Constructeur de la vueDescription
	 * Le string passé en paramètre est notamment utilisé pour récupérer une description déjà existente et construire la vue en fonction de celle-ci.
	 * @param s : String qui sera affiché dans la zone de texte.
	 */
	public VueDescription(String s) {

		// Initialisation du label et d'un champ de texte
		lblDesc = new JLabel("Description : ");
		txtfDesc = new JTextArea(s, 4, 20);
		// 20 = caractere affichable par defaut

		// activation du retour a la ligne
		txtfDesc.setLineWrap(true);

		// ajout des elements au panel en utilisant un borderLayout
		add(lblDesc, BorderLayout.NORTH);

		// creation d'un jScrollPane a partir du textArea pour pouvoir utiliser le
		// scroll
		// s'il s'agrandit (si on a remplit l'espace de base)
		JScrollPane scrollPane = new JScrollPane(txtfDesc);
		add(scrollPane, BorderLayout.CENTER);

	}

	// ***********************************
	// ****** GETTERS & SETTERS **********
	// ***********************************

	/**
	 * Getter de la zone de texte
	 * @return la zone de texte
	 */
	public JTextArea getTxtfDesc() {
		return txtfDesc;
	}

}
