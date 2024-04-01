package Vues;

import Modele.*;
import java.awt.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * Cette classe implémente une vue de type JPanel composée d'un JLabel contenant
 * le pseudo et date d'un commentaire et d'une JTextArea contenant le contenu du
 * Commentaire en lui meme.
 * Elle représente un Commentaire. Utilisée dans
 * {@link Vues.VueModificationCarte}.
 * {@link Modele.Commentaire} pour des informations complémentaires sur la
 * classe Commentaire.
 * 
 * @see Vues.VueModificationCarte
 * @see Modele.Commentaire
 * @author Soan
 */

public class VueCommentaire extends JPanel {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private JLabel lblNomEtDate;
	private JTextArea contenu;

	// ***********************************
	// ********* CONSTRUCTEUR ************
	// ***********************************

	/**
	 * Constructeur de la classe VueCommentaire.
	 * @param nom : pseudo de l'auteur du commentaire
	 * @param contenu : contenu du commentaire
	 * @param date : date de creation du commentaire
	 */
	public VueCommentaire(String nom, String contenu, LocalDateTime date) {

		// creation du formatteur de date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm");
		// utilisation du format sur la date pour l'avoir sous la bonne forme
		this.lblNomEtDate = new JLabel(nom + " (" + date.format(formatter) + ") :");
		this.contenu = new JTextArea(contenu);
		// desactivation de la modification du textArea pour l'utiliser comme un label
		this.contenu.setEditable(false);

		// utilisation du borderLayout pour ordonner le panel
		setLayout(new BorderLayout());
		// ajout des elements au panel
		add(this.lblNomEtDate, BorderLayout.WEST);
		// creation d'un espace vide
		add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.CENTER);
		add(this.contenu, BorderLayout.SOUTH);

		// bordure du panel en bleu
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
	}

	/**
	 * Constructeur de la classe VueCommentaire.
	 * Ce contructeur ne prend pas de date en paramètre, il crée un commentaire avec la date actuelle.
	 * @param nom : pseudo de l'auteur du commentaire
	 * @param contenu : contenu du commentaire
	 */
	public VueCommentaire(String nom, String contenu) {

		// creation du formatteur de date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm");

		// utilisation du format sur la date actuelle pour l'avoir sous la bonne forme
		this.lblNomEtDate = new JLabel(nom + " (" + LocalDateTime.now().format(formatter) + ") :");

		this.contenu = new JTextArea(contenu);
		// desactivation de la modification du textArea pour l'utiliser comme un label
		this.contenu.setEditable(false);
		// utilisation du borderLayout pour ordonner le panel
		setLayout(new BorderLayout());
		// ajout des elements au panel
		add(this.lblNomEtDate, BorderLayout.WEST);
		// creation d'un espace vide
		add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.CENTER);
		add(this.contenu, BorderLayout.SOUTH);

		// bordure du panel en bleu
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
	}

	/**
	 * Constructeur de la classe VueCommentaire.
	 * Ce constructeur prend un objet Commentaire en paramètre, pour effectuer la construction avec ses attributs.
	 * @param commentaire : objet Commentaire
	 */
	public VueCommentaire(Commentaire commentaire) {

		// creation du formatteur de date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm");

		// utilisation du format sur la date du commentaire pour l'avoir sous la bonne
		// forme
		this.lblNomEtDate = new JLabel(
				commentaire.getUtilisateur().getEmail() + " (" + commentaire.getDate().format(formatter) + ") :");

		this.contenu = new JTextArea(commentaire.getContenu());
		// desactivation de la modification du textArea pour l'utiliser comme un label
		this.contenu.setEditable(false);
		// utilisation du borderLayout pour ordonner le panel
		setLayout(new BorderLayout());
		// ajout des elements au panel
		add(this.lblNomEtDate, BorderLayout.WEST);
		// creation d'un espace vide
		add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.CENTER);
		add(this.contenu, BorderLayout.SOUTH);

		// bordure du panel en bleu
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
	}

}
