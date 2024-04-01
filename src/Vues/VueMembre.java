package Vues;

import javax.swing.*;

import Modele.*;

import java.awt.*;

/**
 * Sous composant graphique utilisé pour afficher brièvement les informations
 * d'un membre (utilisateur) d'un tableau.
 * Il est utilisé dans diverses vues dans un but de factorisation du code.
 * On va ici afficher l'adresse email de l'utilisateur. Rassembler cette vue
 * dans un sous-composant permet
 * une meilleure ouverture pour des modifications futures (par exemple, afficher
 * le nom et le prénom de l'utilisateur).
 *
 * Utilisé dans : {@link Vues.VueGestionMembresCarte},
 * {@link Vues.VueModificationPerms}.
 *
 * @see Vues.VueGestionMembresCarte
 * @see Vues.VueModificationPerms
 * @author Soan
 * @author Olivier
 */

public class VueMembre extends JPanel {
	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private TrelloLitePlus application;
	private Utilisateur membre;
	private JLabel lblMembre;

	// ***********************************
	// ******** CONSTRUCTEURS ************
	// ***********************************

	/**
	 * Constructeur de la classe VueMembre pour afficher les informations d'un
	 * membre en particulier.
	 * 
	 * @param membre      instance de la classe Utilisateur (membre à afficher)
	 * @param application instance de la classe TrelloLitePlus (application)
	 */
	public VueMembre(Utilisateur membre, TrelloLitePlus application) {
		this.application = application;
		this.membre = membre;
		lblMembre = new JLabel(membre.getEmail());

		setLayout(new FlowLayout());

		// ajout des elements au panel
		add(lblMembre);

		// bordure du panel en bleu
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

	}

	/**
	 * Constructeur de la classe VueMembre en ne passant pas en paramètre un membre
	 * en particulier,
	 * pour traiter le cas où on affiche les informations de l'utilisateur actuel de
	 * l'application.
	 * 
	 * @param application instance de la classe TrelloLitePlus (application)
	 */
	public VueMembre(TrelloLitePlus application) {
		// Comportement différent si l'utilisateur est l'utilisateur actuel

		this.application = application;
		this.membre = application.getUtilisateurActuel();
		int ind = application.getTableauActuel().getIndexUtilisateur(membre);
		int perm = application.getTableauActuel().getPermissions().get(ind);

		lblMembre = new JLabel(membre.getEmail() + " (vous)");

		String sPerm = Tableau.permissionsDispo[perm];
		JLabel lblPerm = new JLabel(sPerm);

		setLayout(new GridLayout(1, 2));

		// ajout des elements au panel
		add(lblMembre);
		add(lblPerm);

		// bordure du panel en bleu
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

	}

	// ************************************
	// **** GETTERS ET SETTERS ************
	// ************************************

	/**
	 * Getter de l'instance actuelle de l'application
	 * @return l'application
	 */
	public TrelloLitePlus getApplication() {
		return application;
	}

	/**
	 * Setter de l'instance actuelle de l'application
	 * @param application l'application
	 */
	public void setApplication(TrelloLitePlus application) {
		this.application = application;
	}

	/**
	 * Retourne l'utilisateur dont on affiche les informations
	 * @return l'utilisateur
	 */
	public Utilisateur getMembre() {
		return membre;
	}

	/**
	 * Setter de l'utilisateur dont on affiche les informations
	 * @param membre l'utilisateur
	 */
	public void setMembre(Utilisateur membre) {
		this.membre = membre;
	}

	/**
	 * Retourne le label contenant les informations de l'utilisateur
	 * @return le label
	 */
	public JLabel getLblMembre() {
		return lblMembre;
	}

	/**
	 * Setter du label contenant les informations de l'utilisateur
	 * @param lblMembre le label
	 */
	public void setLblMembre(JLabel lblMembre) {
		this.lblMembre = lblMembre;
	}

}
