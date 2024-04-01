package Vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.Duration;
import javax.swing.*;
import javax.swing.border.Border;
import Controleur.ControleurApercuCarte;
import Modele.*;

/**
 * Cette classe implémente la vue graphique permettant d'afficher un aperçu
 * d'une carte.
 * Cet aperçu contient le nom de la carte, les étiquettes associées, la date de
 * début, la date de fin, le nombre de commentaires, et un avertissement si la
 * date de fin est dépassée.
 * La classe {@link Modele.Carte} contient des informations complémentaires sur
 * la classe Carte donc cette vue gère la représentation.
 * Le contrôleur associé est {@link Controleur.ControleurApercuCarte}, qui gère
 * les interactions avec l'utilisateur.
 * 
 * @see Modele.Carte
 * @see Controleur.ControleurApercuCarte
 * 
 * @author Olivier
 * @author Mathis
 */

public class ApercuCarte extends JPanel {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private Carte modele;
	private TrelloLitePlus application;
	private JPanel panneauEtiquettes; // Panneau conteneur pour les étiquettes
	private JLabel lblNomCarte; // Label pour le nom de la carte
	private JPanel panelInfos; // infos de la carte (nombre de commentaires, date de fin, etc.)
	private JLabel lblDateDebut; // Label pour la date de début
	private JLabel lblDateFin; // Label pour la date de fin
	private JLabel lblLiaison; // Label pour indiquée si une carte est liée ou non
	private Duration joursRestants = Duration.ZERO; // Nombre de jours restants avant la date de fin (par défaut 0)
	private JLabel lblNbCommentaires; // Label pour le nombre de commentaires de la carte
	private JLabel lblAvertissement; // Label pour l'avertissement de la carte (si la date de fin est dépassée)
	private JLabel lblMembresAffectes; // Label pour les membres affectés à la carte

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur de la classe ApercuCarte : initialise les attributs et crée
	 * l'interface graphique via la méthode redessiner().
	 * 
	 * @param c           : la carte dont on veut afficher un aperçu (le modèle)
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde.
	 */
	public ApercuCarte(Carte c, TrelloLitePlus application) {
		// L'apercu carte prends en parametre une carte du modèle dont il sera la vue.
		modele = c;
		this.application = application;

		// Création de la bordure
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

		// Attribution de la bordure au panel
		this.setBorder(border);

		// Attribution d'un layout au panel
		this.setLayout(new BorderLayout());

		// La méthode redesinner() permet de créer l'interface graphique
		redessiner();

	}

	/**
	 * Permet de récupérer le modèle associé à la vue.
	 * 
	 * @return le modèle associé à la vue, objet de type Carte.
	 */
	public Carte getCarte() {
		return modele;
	}

	/**
	 * Effectue un nettoyage de l'interface graphique et la recrée en fonction du
	 * modèle.
	 * Permet donc d'actualiser l'interface graphique en fonction des changements
	 * effectués sur le modèle.
	 */
	public void redessiner() {
		removeAll(); // Nettoyage de l'interface

		// Initialisation de certains labels
		lblDateFin = new JLabel();
		lblDateDebut = new JLabel();
		lblNbCommentaires = new JLabel();
		lblAvertissement = new JLabel();
		lblMembresAffectes = new JLabel();
		lblLiaison = new JLabel();

		String s = modele.getTitre(); // Par défaut le nom de la carte
		// si la carte est liée à une autre
		if (modele.getCarteJointe() != null) {
			// creer un label avec le titre de la carte et le titre de la carte liée
			s += " (pointe vers " + modele.getCarteJointe().getTitre() + ")";
		}

		lblNomCarte = new JLabel(s);
		lblNomCarte.setHorizontalAlignment(SwingConstants.CENTER); // Centrer le texte

		// Création d'un panel conteneur pour les différentes étiquettes de la carte si
		// elle en possède :
		// Utiliser un FlowLayout avec alignement horizontal pour les etiquette
		panneauEtiquettes = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		// Ce panel possède un fond transparent pour ne pas interférer avec le fond du
		// panel général (ApercuCarte)
		panneauEtiquettes.setOpaque(false);

		if (modele.getEtiquettes().size() != 0) {
			// Si la carte du modèle possède des étiquettes:
			// Ajouter les étiquettes au panel
			for (Etiquette e : modele.getEtiquettes()) {
				if (e != null) {
					// Créer un panel pour chaque étiquette
					JPanel etiquette = new JPanel();
					// Modification du fond du panel
					etiquette.setBackground(e.getCouleur()); // couleur de l'étiquette
					// On rend le panel rectangulaire et un peu plus grand
					etiquette.setPreferredSize(new java.awt.Dimension(50, 20));

					// Ajouter le panel au panel conteneur
					panneauEtiquettes.add(etiquette);
				}
			}
		}

		// Création d'un panel conteneur pour les infos de la carte (lien, nombre de
		// commentaires, etc.)
		panelInfos = new JPanel(new GridLayout(5,1));
		// Ce panel possède un fond transparent pour ne pas interférer avec le fond du
		// panel général (ApercuCarte)
		panelInfos.setOpaque(false);

		// On affiche le nombre de commentaires s'il y en a dans les infos de la carte
		if (modele.getCommentaires().size() != 0) {
			// Si le modèle possède des commentaires
			// Afficher le nombre de commentaires dans les infos
			// On utilise un emoji pour le commentaire (unicode)
			lblNbCommentaires.setText("\uD83D\uDCAC" + modele.getCommentaires().size());
			panelInfos.add(lblNbCommentaires);
		}

		// Si la carte est liée à une autre, on ajoute cette information dans les infos
		if (modele.getCarteJointe() != null) {
			// Afficher un emoji lien dans les infos
			lblLiaison.setText("\u26D3\uFE0F (liée)");
			panelInfos.add(lblLiaison);
		}

		// On va également ajouter la date de début et la date de fin de la carte dans
		// les infos
		// On utilise un emoji pour la date de début et un autre pour la date de fin
		// (unicode)
		if (modele.getDateDebut() != null) {
			// Si la carte possède une date de début
			// Afficher la date un emoji dans les infos (emoji unicode)
			lblDateDebut.setText("\u23F0\uFE0F  Débute le : " + modele.getDateDebutFormatee());
			panelInfos.add(lblDateDebut);
		}

		if (modele.getDateFin() != null) {
			// Si la carte possède une date de fin
			// Afficher la date et un emoji dans les infos (emoji unicode)

			// On utilise la classe duration pour calculer le nombre de jours restants entre
			// aujourd'hui et la date de fin de la carte
			joursRestants = Duration.between(java.time.LocalDateTime.now(), modele.getDateFin());
			// On va par la suite convertir cette Duration en jours avec la méthode
			// toDays().

			// Si le nombre de jours est positif : on affiche "dans X jours"
			if (joursRestants.toDays() > 0) {
				lblDateFin.setText("\u23F0\uFE0F  Se termine le : " + modele.getDateFinFormatee() + " (dans "
						+ joursRestants.toDays() + " jour(s))");
			}
			// Sinon on affiche : "il y a X jours" et on retire le signe moins
			else {
				lblDateFin.setText("\u23F0\uFE0F  Se termine le : " + modele.getDateFinFormatee() + " (il y a "
						+ -(joursRestants.toDays()) + " jour(s))");
			}

			panelInfos.add(lblDateFin);
		}

		// Si la date de fin de la carte dépasse la date du jour, le fond de la carte
		// sera rouge et un avertissement va apparaitre dans les infos
		// La carte ne doit également pas être cochée comme étant terminée pour un tel
		// avertissement
		if (modele.getDateFin() != null && modele.getEtat() == false
				&& modele.getDateFin().isBefore(java.time.LocalDateTime.now())) {

			this.setBackground(Color.decode("#e06060"));

			// Avertissement
			lblAvertissement.setText("\u26A0\uFE0F EN RETARD!");

			panelInfos.add(lblAvertissement);
		}

		// Si la carte est cochée, on modifie le labelDateFin
		if (modele.getEtat() == true) {
			// On modifie le texte du labelDateFin en "s'est terminée le" au lieu de "se
			// termine le"
			if (joursRestants.toDays() > 0) {
				lblDateFin.setText("\u23F0\uFE0F  S'est terminée le : " + modele.getDateFinFormatee() + " (dans "
						+ joursRestants.toDays() + " jour(s))");
			}
			// Sinon on affiche : "il y a X jours" et on retire le signe moins
			else {
				lblDateFin.setText("\u23F0\uFE0F  S'est terminée le : " + modele.getDateFinFormatee() + " (il y a "
						+ -(joursRestants.toDays()) + " jour(s))");
			}
		}

		// Si des membres sont affectés à la carte, alors on affiche cette information
		// dans le panel des informations :
		if (modele.getMembres().size() != 0) {
			// S'il existe des membres
			// On affiche leur nombre aux côtés d'un emoji unicode dans les infos
			lblMembresAffectes.setText("\uD83D\uDC65 " + modele.getMembres().size());

			panelInfos.add(lblMembresAffectes);
		}

		if (modele.getEtat() == true) {
			// Si la carte est cochée, le fond devient vert légèrement clair pour indiquer à
			// l'utilisateur que la carte est terminée
			this.setBackground(Color.decode("#5f8a5f"));
		}

		// Ajouter les composants au panel
		this.add(panneauEtiquettes, BorderLayout.NORTH);
		this.add(lblNomCarte, BorderLayout.CENTER);
		this.add(panelInfos, BorderLayout.SOUTH);

		// Ajout d'un controleur à l'apercu carte
		new ControleurApercuCarte(modele, this, application);

		repaint();
		revalidate();

	}

	/**
	 * Surcharge de la méthode {@link #ajouterAPanelEtiquettes(JButton)} pour
	 * ajouter une JCheckBox cette fois ci.
	 * Méthode utilisée pour ajouter une checkbox spécifiquement au panneau des
	 * étiquettes
	 * En effet la checkbox sera dans le contrôleur ControleurApercuCarte pour gérer
	 * différents évènements, il est alors nécessaire de l'ajouter ici depuis
	 * la classe ControleurApercuCarte.
	 * 
	 * @param cbx la checkbox à ajouter
	 * @see ControleurApercuCarte pour les appels de cette méthode
	 */
	public void ajouterAPanelEtiquettes(JCheckBox cbx) {

		panneauEtiquettes.add(cbx);
	}

	/**
	 * Surcharge de la méthode {@link #ajouterAPanelEtiquettes(JCheckBox)} pour
	 * ajouter un bouton cette fois ci.
	 * 
	 * @param btn le bouton à ajouter
	 * @see ControleurApercuCarte pour les appels de cette méthode
	 */
	public void ajouterAPanelEtiquettes(JButton btn) {
		panneauEtiquettes.add(btn);
	}

}
