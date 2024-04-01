package Vues;

import javax.swing.*;
import java.awt.*;

/**
 * Cette Vue est une sous-composante utilisée dans d'autres vues.
 * Les vues concernées sont : {@link Vues.VueModificationCarte},
 * {@link Vues.VueEcrireCommentaire}
 * Certains contrôleurs la rappellent pour d'autres implémentations :
 * {@link Controleur.ControleurEcrireCommentaire},
 * {@link Controleur.ControleurModificationCarte}
 * Cette classe implémente une vue de type JPanel composée
 * d'un JLabel
 * "Ecrire un nouveau commentaire : " et d'une grande JTextArea. Elle
 * represente
 * le menu pour ecrire un nouveau commentaire.
 * {@link Modele.Commentaire} pour des informations complémentaires sur
 * la
 * classe Commentaire.
 * {@link Controleur.ControleurEcrireCommentaire} pour des
 * informations complémentaires sur
 * le Controleur associé.
 * 
 * @author Soan
 */

public class VueEcrireCommentaire extends JPanel {

	// *********************************
	// ******* ATTRIBUTS ***************
	// *********************************

	private JLabel labelTitre;
	private JTextArea zoneCommentaire;
	private JScrollPane scrollPane;

	// *********************************
	// ******* CONSTRUCTEUR ************
	// *********************************

	/**
	 * Constructeur de la classe VueEcrireCommentaire.
	 * Initialise la zone d'écriture du commentaire et le label indicateur.
	 */
	public VueEcrireCommentaire() {

		labelTitre = new JLabel("Écrire un nouveau commentaire :");
		zoneCommentaire = new JTextArea(10, 30);
		// activation du retour a la ligne auto pour la TextArea
		zoneCommentaire.setLineWrap(true);
		zoneCommentaire.setWrapStyleWord(true);
		// creation d'un jScrollPane a partir du textArea pour pouvoir utiliser le
		// scroll
		// s'il s'agrandit (si on a remplit l'espace de base)
		scrollPane = new JScrollPane(zoneCommentaire);

		// utilisation du borderLayout pour les ordonner
		setLayout(new BorderLayout());

		// ajout des elements au panel
		add(labelTitre, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		// bordure du panel en bleu
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
	}

	// ***********************************
	// ****** GETTERS & SETTERS **********
	// ***********************************

	/**
	 * Getter de la zone de texte. Permet de récupérer la zone de texte pour par la suite accéder au texte qu'elle contient.
	 * @return la zone de texte.
	 */
	public JTextArea getZoneCommentaire() {
		return zoneCommentaire;
	}

}
