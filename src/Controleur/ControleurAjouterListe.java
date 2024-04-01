package Controleur;

import Modele.*;
import Vues.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Cette classe implémente le contrôleur permettant d'ajouter
 * une liste à un tableau.
 * Par conséquent, ce contrôleur crée une liste mais contrôle un objet
 * de type Tableau dans le modèle.
 * {@link Modele.Tableau} pour des informations complémentaires sur le
 * modèle associé.
 * {@link Vues.AjouterListe} pour des informations complémentaires sur
 * la vue associée.
 * 
 * Il contient les éléments suivants :
 * - un bouton pour ajouter la liste
 * - un bouton pour annuler l'ajout de la liste
 * - un champ de texte pour saisir le nom de la liste
 * 
 * @see Modele.Tableau
 * @see Vues.AjouterListe
 * @author Olivier
 * @author Soan
 */

public class ControleurAjouterListe extends JPanel implements ActionListener {

	// ******************************************
	// *********** ATTRIBUTS ********************
	// ******************************************

	/** Constante pour l'action du bouton ajouter */
	static public final String ACTION_AJOUTER = "AJOUTER";

	/** Constante pour l'action du bouton annuler */
	static public final String ACTION_ANNULER = "ANNULER";

	private Tableau modele;
	private AjouterListe vue;
	private JButton btnAjouter;
	private JButton btnSupprimer;
	private JTextField champTexte;
	private Liste listeAAjouter; // La future liste qui sera potentiellement ajoutée par le contrôleur
	private FenetreDansTableau fenetre; // La fenêtre qui contient la vue. On va l'utiliser pour ajouter une nouvelle
	// instance de la classe ConteneurListe pour la liste fraîchement ajoutée.
	private TrelloLitePlus application;
	private JLabel lblAjouter ;

	// ******************************************
	// *********** CONSTRUCTEUR *****************
	// ******************************************

	/**
	 * Constructeur de la classe ControleurAjouterListe
	 * 
	 * @param modele      : le modèle est un tableau qui sera modifié si
	 *                    l'utilisateur ajoute une liste
	 * @param vue         : la vue va accueillir les différents éléments graphiques
	 *                    du contrôleur
	 * @param fenetre     : la fenêtre parent contenant la vue. On va l'utiliser
	 *                    pour y ajouter une nouvelle instance de la classe
	 *                    ConteneurListe pour la liste fraîchement ajoutée.
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde
	 */
	public ControleurAjouterListe(Tableau modele, AjouterListe vue, FenetreDansTableau fenetre,
			TrelloLitePlus application) {

		// On récupère les paramètres
		this.application = application;
		this.modele = modele;
		this.vue = vue;
		this.fenetre = fenetre;

		// Changer le fond du controleur en un gris plus foncé
		this.setBackground(Color.LIGHT_GRAY); 



		// afficher les boutons et la zone de texte uniquement si l'utilisateur est admin ou proprio
		int ind = modele.getIndexUtilisateur(application.getUtilisateurActuel());
		if (modele.getPermissions().get(ind) != 2) {

			// Création des boutons et du champ de texte
			btnAjouter = new JButton("Ajouter");
			btnSupprimer = new JButton("x");

			champTexte = new JTextField(15); // Taille équivalente à 15 caractères par défaut
			champTexte.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

			// Label pour indiquer "ajouter une liste"
			lblAjouter = new JLabel("Ajouter une liste :");

			// Ajout des boutons au contrôleur et du contrôleur à la vue, et ajout du champ
			// de texte
			add(lblAjouter);
			add(btnAjouter);
			add(btnSupprimer);
			add(champTexte);
			vue.add(this);

			// Le controleur écoute les boutons
			btnAjouter.addActionListener(this);
			btnSupprimer.addActionListener(this);

			// On définit les actions des boutons
			btnAjouter.setActionCommand(ACTION_AJOUTER);
			btnSupprimer.setActionCommand(ACTION_ANNULER);

		}
	}

	// ******************************************
	// *********** METHODES *********************
	// ******************************************

	/**
	 * Méthode qui gère les différentes actions de l'utilisateur (en fonction des
	 * différents écouteurs placés sur les éléments ajoutés la vue)
	 * 
	 * @param e : l'événement qui a déclenché l'action
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_AJOUTER)) {

			// Si le champ de texte est vide, on affiche un message d'erreur
			if (champTexte.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez renseigner un nom pour la liste à ajouter.");
				return;
			} else {
				// On crée une liste avec le nom renseigné
				// Cette liste sera affectée au tableau (qui est le modèle) dont le contrôleur
				// s'occupe.
				listeAAjouter = new Liste(champTexte.getText());

				// Ajout de la liste au tableau (modele)
				modele.ajouterListe(listeAAjouter);

				// On vide le champ de texte
				champTexte.setText("");

				// On crée une nouvelle instance de ConteneurListe pour la Fenetre
				fenetre.addElement(new ConteneurListe(listeAAjouter, fenetre, application));

				// On redessine la fenêtre
				fenetre.redessiner();

			}
		} else if (e.getActionCommand().equals(ACTION_ANNULER)) // Si l'utilisateur clique sur le bouton "x"
		{
			// On vide le champ de texte
			champTexte.setText("");
		}
	}

	// ******************************************
	// *********** GETTERS & SETTERS ************
	// ******************************************

	/**
	 * Renvoie le modele associé à ce contrôleur
	 * @return un tableau
	 */
	public Tableau getModele() {
		return modele;
	}

	/**
	 * Modifie le modele associé à ce contrôleur
	 * @param modele : un tableau
	 */
	public void setModele(Tableau modele) {
		this.modele = modele;
	}

	/**
	 * Renvoie la vue associée à ce contrôleur
	 * @return une vue 'AjouterListe'
	 */
	public AjouterListe getVue() {
		return vue;
	}

	/**
	 * Modifie la vue associée à ce contrôleur
	 * @param vue : une vue 'AjouterListe'
	 */
	public void setVue(AjouterListe vue) {
		this.vue = vue;
	}

	/**
	 * Renvoie le bouton 'Ajouter'
	 * @return un bouton
	 */
	public JButton getBtnAjouter() {
		return btnAjouter;
	}

	/**
	 * Modifie le bouton 'Ajouter'
	 * @param btnAjouter : un bouton
	 */
	public void setBtnAjouter(JButton btnAjouter) {
		this.btnAjouter = btnAjouter;
	}

	/**
	 * Renvoie le bouton 'Supprimer'
	 * @return un bouton
	 */
	public JButton getBtnSupprimer() {
		return btnSupprimer;
	}

	/**
	 * Modifie le bouton 'Supprimer'
	 * @param btnSupprimer : un bouton
	 */
	public void setBtnSupprimer(JButton btnSupprimer) {
		this.btnSupprimer = btnSupprimer;
	}

	/**
	 * Renvoie le champ de texte
	 * @return un champ de texte
	 */
	public JTextField getChampTexte() {
		return champTexte;
	}

	/**
	 * Modifie le champ de texte
	 * @param champTexte : un champ de texte
	 */
	public void setChampTexte(JTextField champTexte) {
		this.champTexte = champTexte;
	}

	/**
	 * Renvoie la liste à ajouter
	 * @return une liste
	 */
	public Liste getListeAAjouter() {
		return listeAAjouter;
	}

	/**
	 * Modifie la liste à ajouter
	 * @param listeAAjouter : une liste
	 */
	public void setListeAAjouter(Liste listeAAjouter) {
		this.listeAAjouter = listeAAjouter;
	}

	/**
	 * Renvoie la fenêtre dans laquelle on ajoute la liste
	 * @return une fenêtre
	 */
	public FenetreDansTableau getFenetre() {
		return fenetre;
	}

	/**
	 * Modifie la fenêtre dans laquelle on ajoute la liste
	 * @param fenetre : une fenêtre
	 */
	public void setFenetre(FenetreDansTableau fenetre) {
		this.fenetre = fenetre;
	}

	/**
	 * Renvoie l'instance de l'application actuellement lancée
	 * @return une instance de l'application
	 */
	public TrelloLitePlus getApplication() {
		return application;
	}

	/**
	 * Modifie l'instance de l'application actuellement lancée
	 * @param application : une instance de l'application
	 */
	public void setApplication(TrelloLitePlus application) {
		this.application = application;
	}

}
