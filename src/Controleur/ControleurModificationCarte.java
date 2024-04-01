
package Controleur;

import Modele.*;
import Vues.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Cette classe implémente le contrôleur permettant de
 * modifier une carte.
 * Il est composé de différent sous-contrôleurs tout comme la vue associée
 * ({@link VueModificationCarte}) est composée
 * de plusieurs sous-vues.
 * {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * {@link Vues.VueModificationCarte} pour des informations
 * complémentaires sur la vue associée.
 * 
 * @author Soan
 * @author Olivier
 */

public class ControleurModificationCarte extends JPanel implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	static public final String ACTION_MODIFIER = "MODIFIER";
	static public final String ACTION_SUPPRIMER = "SUPPRIMER";
	static public final String ACTION_MEMBRES = "MEMBRES";

	private Carte modele;
	private VueModificationCarte vue;
	private ConteneurListe vueListe;
	private JButton btnModifier;
	private JButton btnSupprimer;
	private JButton btnMembre;

	private TrelloLitePlus application; // L'instance de l'application est passée en paramètre

	// ***********************************
	// ******** CONSTRUCTEUR *************
	// ***********************************

	/**
	 * Constructeur de la classe ControleurModificationCarte
	 * 
	 * @param modele      : la carte à modifier
	 * @param vue         : la vue associée à ce contrôleur :
	 *                    {@link VueModificationCarte}
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde.
	 */
	public ControleurModificationCarte(Carte modele, VueModificationCarte vue, TrelloLitePlus application) {

		// On initialise les attributs
		this.application = application;
		this.modele = modele;
		this.vue = vue;
		// On récupère la vue de la liste parente de la carte (pour une future
		// actualisation)
		this.vueListe = modele.getListeParent().getVueListe();

		// definition du layout
		setLayout(new GridLayout(2, 3));

		// ajout du controleur vueEcrireCommentaire
		ControleurEcrireCommentaire controleurEcrireComm = new ControleurEcrireCommentaire(modele,
				vue.getvEcrire(),
				this, application);
		add(controleurEcrireComm.getBtnEnvoyer());

		// creation du bouton pour assigner des membres et ajout au layout
		btnMembre = new JButton("Assigner des membres");
		this.add(btnMembre);

		btnMembre.addActionListener(this);
		btnMembre.setActionCommand(ACTION_MEMBRES);

		// afficher les boutons uniquement si l'utilisateur est admin ou proprio
		int ind = modele.getListeParent().getTableauParent().getIndexUtilisateur(application.getUtilisateurActuel());
		if (modele.getListeParent().getTableauParent().getPermissions().get(ind) != 2) {

			// Ajouts de différents boutons
			btnModifier = new JButton("Modifier carte");
			btnSupprimer = new JButton("Supprimer carte");

			// modifications des boutons
			btnModifier.setPreferredSize(new Dimension(150, 75));
			btnModifier.setBorder(BorderFactory.createLineBorder(Color.blue));
			btnSupprimer.setPreferredSize(new Dimension(100, 50));
			btnSupprimer.setBorder(BorderFactory.createLineBorder(Color.red));

			this.add(btnModifier);
			this.add(btnSupprimer);

			btnModifier.addActionListener(this);
			btnModifier.setActionCommand(ACTION_MODIFIER);
			btnSupprimer.addActionListener(this);
			btnSupprimer.setActionCommand(ACTION_SUPPRIMER);
		} else {
			// si l'utilisateur est en Lecture seule, il quand meme voir les membres de la
			// carte
			btnMembre.setText("Voir les membres");
			btnMembre.setPreferredSize(new Dimension(150, 75));
			btnMembre.setBorder(BorderFactory.createLineBorder(Color.blue));

			// desactivation de tous les controleurs
			vue.getvNom().getTxtfNom().setEnabled(false);
			vue.getvDates().getTxtfDateDeb().setEnabled(false);
			vue.getvDates().getTxtfDateFin().setEnabled(false);
			vue.getvDesc().getTxtfDesc().setEnabled(false);
			vue.getVlCarte().getCbCartes().setEnabled(false);
			vue.getvEtiquette().getCbRouge().setEnabled(false);
			vue.getvEtiquette().getCbBleu().setEnabled(false);
			vue.getvEtiquette().getCbJaune().setEnabled(false);
			vue.getvEtiquette().getCbVert().setEnabled(false);
			vue.getvEtiquette().getCbViolet().setEnabled(false);

		}
		// Ajout du controleur a la vue et définition de son attribut
		vue.add(this);
		vue.setControleur(this);
	}

	// ***********************************
	// ********** METHODES ***************
	// ***********************************

	/**
	 * Méthode permettant de gérer les actions sur les boutons
	 * 
	 * @param e : l'évènement qui a déclenché l'action
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		// Si on clique sur le bouton supprimer
		if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			// on supprime la carte grace à la methode de sa liste parent
			// afin de la supprimer correctement
			modele.getListeParent().supprimerCarte(modele);

			// on actualise la vue de la liste
			vueListe.redessiner();

		}
		// Si on clique sur le bouton modifier
		if (e.getActionCommand().equals(ACTION_MODIFIER)) {
			// Récuperation des informations de la vue pour modifier la carte

			// Tout d'abord, on vérifie la date
			// recuperation des dates dans le champ de texte.
			DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			String dateDeb = vue.getvDates().getTxtfDateDeb().getText();
			String dateFin = vue.getvDates().getTxtfDateFin().getText();

			// Vérification du format des dates saisies
			// Utilisation d'une expression régulière pour reserver le format dd/mm/yyyy
			// L'expression vérifie également que les jours sont inférieurs à 31, et les
			// mois à 12
			// Si les dates sont vides, il n'y a pas d'erreur

			// Explication :
			/*
			 * JOUR : Un chiffre entre 0 et 2 (0,1,2) puis un chiffre de 0 à 9 (de 00 à 29)
			 * OU BIEN un 3 avec un 0 ou 1 (30 ou 31)
			 * MOIS : Un 0 et un chiffre de 0 à 9 (00 à 09) OU BIEN un 1 et un chiffre de 0
			 * à 2 (10, 11, 12)
			 * ANNEE : Quatre chiffres de 0 à 9 (0000 à 9999)
			 * 
			 */

			if (!(dateDeb.isEmpty()) && !dateDeb.matches("([0-2][0-9]|(3)[0-1])/((0)[0-9]|(1)[0-2])/[0-9]{4}")) {
				JOptionPane.showMessageDialog(vue, "Format de date de début invalide !", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!(dateFin.isEmpty()) && !dateFin.matches("([0-2][0-9]|(3)[0-1])/((0)[0-9]|(1)[0-2])/[0-9]{4}")) {
				JOptionPane.showMessageDialog(vue, "Format de date de fin invalide !", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// L'utilisateur ne peut pas modifier la carte si la date de début est
			// supérieure à la date de fin (et inversement)
			if (!(dateDeb.isEmpty()) && !(dateFin.isEmpty())) {
				// On effectue une convertion pour pouvoir utiliser isAfter
				LocalDate localDateDeb = LocalDate.parse(dateDeb, datePattern);
				LocalDate localDateFin = LocalDate.parse(dateFin, datePattern);

				if (localDateDeb.isAfter(localDateFin)) {
					JOptionPane.showMessageDialog(vue, "La date de début doit être antérieure à la date de fin !",
							"Erreur",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			// On reconvertit les dates en LocalDateTime (LDT pour local date time)
			LocalDateTime dateDebLDT = null;
			LocalDateTime dateFinLDT = null;

			// On doit dans un premier temps convertir les dates string sous forme de
			// LocalDate
			// Cette localDate va être appliquée sur nos dates pour obtenir un LocalDateTime
			// dans le bon format, qui commence à 00:00:00

			// Si les dates ne sont pas vides, on les convertit
			if (!(dateDeb == null) && !dateDeb.equals("")) {
				LocalDate localDateDeb = LocalDate.parse(dateDeb, datePattern);
				dateDebLDT = localDateDeb.atStartOfDay();
			}
			if (!(dateFin == null) && !dateFin.equals("")) {
				LocalDate localDateFin = LocalDate.parse(dateFin, datePattern);
				dateFinLDT = localDateFin.atStartOfDay();
			}

			// Affection des dates à la carte
			modele.setDate_debut(dateDebLDT);
			modele.setDate_fin(dateFinLDT);

			// ----------------------------------------------

			// Récuperation du titre
			String titre = vue.getvNom().getTxtfNom().getText();

			//  on vérifie qu'une carte du même nom d'existe pas déjà dans la liste
			boolean trouve = false;
			int i = 0;
			while (!trouve && i < modele.getListeParent().getContenuListe().size()) {
				if (vue.getvNom().getTxtfNom().getText()
						.equals(modele.getListeParent().getContenuListe().get(i).getTitre()) && modele.getListeParent().getContenuListe().get(i) != modele) {

					trouve = true;
				}
				i++;
			}

			// Il faut vérifier que le nom de la carte n'est pas vide : on affiche un
			// message d'erreur si c'est le cas
			if (vue.getvNom().getTxtfNom().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez renseigner un nom pour la carte à créer.");
				return;
			} else if (trouve) {
				JOptionPane.showMessageDialog(null,
						"Veuillez renseigner un autre titre pour la carte à créer car ce titre est déjà présent.");
				return;
			} else {
				modele.setTitre(titre);
			}

			// Récuperation de la description
			String desc = vue.getvDesc().getTxtfDesc().getText();

			// modification de la description de la carte
			modele.setDescription(desc);

			// recuperation et ajout des etiquettes à la carte
			// parcours de la liste des etiquettes de la carte pour savoir quelles
			// etiquettes sont deja presentes
			boolean trouveRouge = false, trouveBleu = false, trouveJaune = false, trouveVert = false,
					trouveViolet = false;

			for ( i = 0; i < modele.getEtiquettes().size(); i++) {
				if (modele.getEtiquettes().get(i).getCouleurRGB() == Color.RED.getRGB()) {
					trouveRouge = true;
				}
				if (modele.getEtiquettes().get(i).getCouleurRGB() == Color.BLUE.getRGB()) {
					trouveBleu = true;
				}
				if (modele.getEtiquettes().get(i).getCouleurRGB() == Color.YELLOW.getRGB()) {
					trouveJaune = true;
				}
				if (modele.getEtiquettes().get(i).getCouleurRGB() == Color.GREEN.getRGB()) {
					trouveVert = true;
				}
				if (modele.getEtiquettes().get(i).getCouleurRGB() == Color.MAGENTA.getRGB()) {
					trouveViolet = true;
				}
			}

			// si la checkbox rouge est selectionnee et que l'etiquette rouge n'est pas deja
			// presente, on l'ajoute
			if (vue.getvEtiquette().getCbRouge().isSelected() && !trouveRouge) {
				modele.ajouterEtiquette(new Etiquette(Color.RED));
				// sinon si la carte possede l'etiquette et que la case n'est pas cochée alors
				// on supprime
			} else if (!vue.getvEtiquette().getCbRouge().isSelected() && trouveRouge) {
				modele.supprimerEtiquette(Color.RED);
			}
			if (vue.getvEtiquette().getCbBleu().isSelected() && !trouveBleu) {
				modele.ajouterEtiquette(new Etiquette(Color.BLUE));
			} else if (!vue.getvEtiquette().getCbBleu().isSelected() && trouveBleu) {
				modele.supprimerEtiquette(Color.BLUE);
			}
			if (vue.getvEtiquette().getCbJaune().isSelected() && !trouveJaune) {
				modele.ajouterEtiquette(new Etiquette(Color.YELLOW));
			} else if (!vue.getvEtiquette().getCbJaune().isSelected() && trouveJaune) {
				modele.supprimerEtiquette(Color.YELLOW);
			}
			if (vue.getvEtiquette().getCbVert().isSelected() && !trouveVert) {
				modele.ajouterEtiquette(new Etiquette(Color.GREEN));
			} else if (!vue.getvEtiquette().getCbVert().isSelected() && trouveVert) {
				modele.supprimerEtiquette(Color.GREEN);
			}
			if (vue.getvEtiquette().getCbViolet().isSelected() && !trouveViolet) {
				modele.ajouterEtiquette(new Etiquette(Color.MAGENTA));
			} else if (!vue.getvEtiquette().getCbViolet().isSelected() && trouveViolet) {
				modele.supprimerEtiquette(Color.MAGENTA);
			}

			// lier la carte ayant comme titre l'item selectionné de la combobox
			if (vue.getVlCarte().getCbCartes().getSelectedIndex() != 0) {
				Object carteAJoindre = vue.getVlCarte().getCbCartes().getSelectedItem();
				String titreCarteAJoindre = carteAJoindre.toString();
				trouve = false;
				i = 0;
				while (!trouve && i < modele.getListeParent().getContenuListe().size()) {
					if (modele.getListeParent().getContenuListe().get(i).getTitre() == titreCarteAJoindre) {
						modele.joindre(modele.getListeParent().getContenuListe().get(i));
						trouve = true;
					}
					i++;
				}
			}
			// sinon si on choisit l'item vide d'index 0, on retire la carte jointe
			else if (vue.getVlCarte().getCbCartes().getSelectedIndex() == 0) {
				modele.setCarteJointe(null);
			}

			// on actualise la vue de la liste
			vueListe.redessiner();
			modele.getApercuCarte().redessiner();

		}
		// après modification, on ferme la fenetre
		vue.dispose();

		// si on clique sur le btn membre
		if (e.getActionCommand().equals(ACTION_MEMBRES)) {

			VueGestionMembresCarte vueMembres = new VueGestionMembresCarte(modele, application, vueListe);
			vueMembres.setVisible(true);

		}
	}

	// ***********************************
	// ********** ACCESSEURS *************
	// ***********************************
	public TrelloLitePlus getApplication() {
		return application;
	}

	public VueModificationCarte getModifCarte() {
		return vue;
	}

	/**
	 * Renvoie la vue parente dans laquelle la vue associée à ce contrôleur se
	 * trouve.
	 * 
	 * @return ConteneurListe, la vue parente où la vueModificationCarte se trouve
	 *         entre autres
	 */
	public ConteneurListe getVueListe() {
		return vueListe;
	}

}
