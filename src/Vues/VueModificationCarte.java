package Vues;

import Modele.*;
import java.awt.*;
import javax.swing.*;

import Controleur.ControleurModificationCarte;

/**
 * 
 * Cette classe implémente une vue de type JFrame composée de
 * plusieurs
 * JPanel, representant le menu pour modifier une carte.
 * Utilise : {@link Vues.VueDescription}, {@link Vues.VueEtiquette},
 * {@link Vues.VueDates},
 * {@link Vues.VueCommentaire}, {@link Vues.VueEcrireCommentaire} et
 * {@link Vues.VueLiaisonCarte}.
 * {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * {@link Controleur.ControleurModificationCarte} pour des
 * informations complémentaires sur
 * le Controleur associé.
 * 
 * @author Soan
 * @author Olivier
 */

public class VueModificationCarte extends JFrame {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private VueNom vNom;
	private VueDescription vDesc;
	private VueEtiquette vEtiquette;
	private VueDates vDates;
	private JPanel vComms;
	private VueEcrireCommentaire vEcrire;
	private VueLiaisonCarte vlCarte;
	private ControleurModificationCarte controleur ;

	// ***********************************
	// ****** CONSTRUCTEUR ***************
	// ***********************************

	/**
	 * Constructeur de la classe VueModificationCarte qui initialise les différents éléments de la vue.
	 * Ils permettent d'effectuer des modifications sur la carte (modele) passée en paramètre du constructeur.
	 * @param carte la carte à modifier (modele)
	 */
	public VueModificationCarte(Carte carte) {
		vNom = new VueNom();
		vNom.getTxtfNom().setText(carte.getTitre());

		vDesc = new VueDescription(carte.getDescription());
		vEtiquette = new VueEtiquette(carte);
		vDates = new VueDates(carte);

		vComms = new JPanel();
		// BoxLayout comme cela pour les ordonner de facon verticale
		vComms.setLayout(new BoxLayout(vComms, BoxLayout.Y_AXIS));
		// declaration des commentaires
		for (int i = 0; i < carte.getCommentaires().size(); i++) {
			vComms.add(new VueCommentaire(carte.getCommentaires().get(i)));
		}
		vEcrire = new VueEcrireCommentaire();
		vlCarte = new VueLiaisonCarte(carte);

		// FlowLayout pour la Frame
		setLayout(new FlowLayout());

		add(vNom);
		add(vDesc);
		add(vEtiquette);
		add(vDates);
		add(vlCarte);
		add(vComms);
		add(vEcrire);

		// calcul de la taille de tous les elements de la classe + 300
		int totalHeight = vNom.getPreferredSize().height + vDesc.getPreferredSize().height
				+ vEtiquette.getPreferredSize().height + vDates.getPreferredSize().height
				+ vComms.getPreferredSize().height + vEcrire.getPreferredSize().height
				+ vlCarte.getPreferredSize().height + 300;

		// setSize grace au calcul precedent
		this.setSize(new Dimension(400, totalHeight));
		this.setTitle("Modification Carte");
		this.setResizable(false);


		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// On récupère la vue parente depuis le contrôleur pour la redessiner (car le controleur avait déjà en attribut cette vue parente)
				controleur.getVueListe().redessiner();
			}
		});

	}

	// ***********************************
	// ***** GETTERS & SETTERS ***********
	// ***********************************

	/**
	 * Obtenir la description de la carte
	 * @return la description de la carte
	 */
	public VueDescription getvDesc() {
		return vDesc;
	}

	/**
	 * Obtenir le nom de la carte
	 * @return le nom de la carte
	 */
	public VueNom getvNom() {
		return vNom;
	}

	/**
	 * Obtenir les étiquettes de la carte
	 * @return les étiquettes de la carte
	 */
	public VueEtiquette getvEtiquette() {
		return vEtiquette;
	}

	/**
	 * Obtenir les dates de la carte
	 * @return les dates de la carte
	 */
	public VueDates getvDates() {
		return vDates;
	}

	/**
	 * Obtenir les commentaires de la carte
	 * @return les commentaires de la carte
	 */
	public JPanel getvComms() {
		return vComms;
	}

	/**
	 * Obtenir le champ pour écrire un commentaire
	 * @return le champ pour écrire un commentaire
	 */
	public VueEcrireCommentaire getvEcrire() {
		return vEcrire;
	}

	/**
	 * Obtenir la combobox des liaisons de la carte
	 * @return la combobox des liaisons de la carte
	 */
	public VueLiaisonCarte getVlCarte() {
		return vlCarte;
	}

	/**
	 * Permet de mettre en place le ControleurModificationCarte qui est associé à cette vue.
	 * @param controleur le controleur à mettre en place
	 */
	public void setControleur(ControleurModificationCarte controleur) {
		this.controleur = controleur ;
	}


}
