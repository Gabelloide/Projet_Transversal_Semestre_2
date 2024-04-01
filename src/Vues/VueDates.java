package Vues;

import javax.swing.*;

import Modele.Carte;

import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * 
 * Cette Vue est une sous-composante utilisée dans d'autres vues.
 * Les vues concernées sont : {@link Vues.VueModificationCarte}
 * Cette classe implémente une vue de type JPanel composée de
 * JLabel
 * et de JTextField pour chacune des dates de la Carte.
 * Elle représente le menu pour modifier les dates d'une Carte.
 * {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * 
 * @author Soan
 * @author Olivier
 */

public class VueDates extends JPanel {

	private JLabel lblDateDeb;
	private JLabel lblDateFin;
	private JTextField txtfDateDeb;
	private JTextField txtfDateFin;

	/**
	 * Constructeur de la classe VueDates.
	 * Intialise les différents JLabel et JTextField.
	 * Le constructeur n'effectue que très peu de vérifications sur le format des dates qui lui sont passées.
	 * D'autres controleurs qui utilisent cette vue sont chargés de vérifier le format des dates.
	 */
	public VueDates() {
		// initialisation des elements
		lblDateDeb = new JLabel("Date de début (format dd/MM/yyyy): ");
		txtfDateDeb = new JTextField(10);
		lblDateFin = new JLabel("Date de fin (format dd/MM/yyyy): ");
		txtfDateFin = new JTextField(10);
		// 10 = taille par default (2 caractères jours + 2 caractères mois + 4
		// caractères ans + 2 caractères slash)

		// utilisation d'un gridLayout pour ordonner le panel
		setLayout(new GridLayout(2, 2));

		// ajout des elements au panel
		add(lblDateDeb);
		add(txtfDateDeb);
		add(lblDateFin);
		add(txtfDateFin);

		// bordure du panel en bleu
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
	}

		/**
	 * Constructeur de la classe VueDates.
	 * Celui-ci récupère une carte en paramètre pour construire la vue en fonction de ses dates.
	 * Intialise les différents JLabel et JTextField.
	 * Le constructeur n'effectue que très peu de vérifications sur le format des dates qui lui sont passées.
	 * D'autres controleurs qui utilisent cette vue sont chargés de vérifier le format des dates.
	 */
	public VueDates(Carte carte) {
		// initialisation des elements
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// Si les dates de la carte ne sont pas nulles, on va les formatter pour les
		// afficher dans les champs de texte
		String sdateDeb = "";
		String sdateFin = "";
		if (carte.getDateDebut() != null) {
			sdateDeb = carte.getDateDebut().format(datePattern);
		}

		if (carte.getDateFin() != null) {
			sdateFin = carte.getDateFin().format(datePattern);
		}

		lblDateDeb = new JLabel("Date de début : ");
		txtfDateDeb = new JTextField(sdateDeb, 10);
		lblDateFin = new JLabel("Date de fin : ");
		txtfDateFin = new JTextField(sdateFin, 10);
		// 10 = taille par default

		// utilisation d'un gridLayout pour ordonner le panel
		setLayout(new GridLayout(2, 2));

		// ajout des elements au panel
		add(lblDateDeb);
		add(txtfDateDeb);
		add(lblDateFin);
		add(txtfDateFin);

		// bordure du panel en bleu
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
	}

	// ***********************************
	// ****** GETTERS & SETTERS **********
	// ***********************************

	/**
	 * Getter de l'attribut txtfDateDeb. Utile pour récupérer par la suite le contenu du champ de texte.
	 * @return JTextField
	 */
	public JTextField getTxtfDateDeb() {
		return txtfDateDeb;
	}

	/**
	 * Getter de l'attribut txtfDateFin. Utile pour récupérer par la suite le contenu du champ de texte.
	 * @return JTextField
	 */
	public JTextField getTxtfDateFin() {
		return txtfDateFin;
	}
}
