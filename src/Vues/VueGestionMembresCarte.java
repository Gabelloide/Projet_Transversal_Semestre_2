package Vues;

import javax.swing.*;

import Modele.*;
import Controleur.*;

import java.awt.*;

/**
 * Cette classe implémente la fenêtre de gestion des membres d'une carte.
 * Utilise : {@link Modele.Carte} pour des informations complémentaires sur la
 * classe Carte.
 * Cette vue est liée à deux contrôleurs différents :
 * {@link Controleur.ControleurMembreCarte} et
 * {@link Controleur.ControleurGestionMembresCarte}.
 * Tandis que la vue implémente l'apercu des utilisateurs actuellement présents
 * dans la carte, le premier contrôleur rajoute le bouton pour les supprimer.
 * Le deuxième implémente l'interface nécessaire pour ajouter un membre à la
 * carte, ou pour quitter cette fenêtre. (donc les boutons "Ajouter" et
 * "Quitter")
 * Utilise également le sous composant vue {@link Vues.VueMembre} pour afficher
 * de brèves informations sur le membre.
 * 
 * @author Soan
 * @author Olivier
 * @see Modele.Carte
 * @see Controleur.ControleurMembreCarte
 * @see Controleur.ControleurGestionMembresCarte
 * @see Vues.VueMembre
 */

public class VueGestionMembresCarte extends JFrame {
	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private TrelloLitePlus application;
	private Carte modele;
	private JPanel pnlMembres;
	private JLabel lblMailMembre;
	private JTextField txtfMailMembre;
	private ConteneurListe conteneurListe; // On veut actualiser ce conteneur liste à la fermeture de cette vue

	// ***********************************
	// ******** CONSTRUCTEURS ************
	// ***********************************

	/**
	 * Constructeur de la vue de gestion des membres d'une carte.
	 * @param carte la carte dont on gère les membres
	 * @param application l'application TrelloLitePlus (peut être utile pour la sauvegarde)
	 * @param conteneurListe le conteneur liste parent de cette vue (utile pour la redessiner)
	 */
	public VueGestionMembresCarte(Carte carte, TrelloLitePlus application, ConteneurListe conteneurListe) {
		this.application = application;
		this.modele = carte;
		this.conteneurListe = conteneurListe;

		this.setSize(500, 500);
		this.setTitle("Membres de la Carte");
		this.setResizable(false);
		this.setVisible(true);

		// Procédure en cas de fermeture de la fenetre avec la petite croix
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// On récupère la vue parente depuis le contrôleur pour la redessiner (car le
				// controleur avait déjà en attribut cette vue parente)
				conteneurListe.redessiner();
			}
		});

		redessiner();
	}

	// ***********************************
	// ******** METHODES *****************
	// ***********************************

	/**
	 * Méthode permettant de redessiner la fenêtre de gestion des membres d'une carte.
	 * Cette méthode est appelée à chaque fois que l'on veut rafraichir la fenêtre.
	 * Retire tous les éléments graphiques pour les réajouter ensuite.
	 * Elle est appelée dans le constructeur dans une optique de factorisation du code.
	 */
	public void redessiner() {

		getContentPane().removeAll();

		setLayout(new FlowLayout());

		// On crée un JLabel explicatif du contenu de la fenetre
		JLabel lblExplicatif = new JLabel("Liste des membres assignés à cette tâche :");

		// Ajout du JLabel explicatif
		this.add(lblExplicatif, BorderLayout.NORTH);

		// On centre le texte
		lblExplicatif.setHorizontalAlignment(JLabel.CENTER);

		lblMailMembre = new JLabel(
				"Entrez le mail de l'utilisateur à ajouter pour l'assigner à cette carte (l'utilisateur doit être présent dans ce tableau) :");

		txtfMailMembre = new JTextField(15);

		JPanel pnl = new JPanel();
		pnl.setLayout(new BorderLayout());

		pnl.add(lblMailMembre);
		pnl.add(txtfMailMembre, BorderLayout.SOUTH);

		pnlMembres = new JPanel();
		pnlMembres.setLayout(new BoxLayout(pnlMembres, BoxLayout.Y_AXIS));

		VueMembre v;
		// creation et ajout d'une vue et son controleur associé pour chq membres de
		// la carte actuelle
		for (int i = 0; i < modele.getMembres().size(); i++) {
			// on verifie si l'utilisateur est bien membre du tableau
			if (modele.getListeParent().getTableauParent().getMembres().contains(modele.getMembres().get(i))) {
				// si oui on cree la vue et son controleur pour ce membre
				v = new VueMembre(modele.getMembres().get(i), application);

				// on recupere l'indice de l'utilisateur actuel pour retrouver ses permissions
				int indUser = modele.getListeParent().getTableauParent()
						.getIndexUtilisateur(application.getUtilisateurActuel());
				// on verifie si l'utilisateur actuel est proprietaire ou admin du tableau (cad
				// permission 0 ou 1)
				if (modele.getListeParent().getTableauParent().getPermissions().get(indUser) <= 1) {
					new ControleurMembreCarte(v, modele, this, application);
				}
				// sinon, on ne fait rien, il ne peut pas supprimer de membres de la carte

				pnlMembres.add(v);
			}
			// sinon on supprime le membre de la carte
			else {
				modele.getMembres().remove(i);
			}

		}

		add(pnlMembres);
		add(pnl);

		// calcul de la taille de tous les elements de la classe + 125 pour avoir
		// un espace vide sous le bouton modifier
		int totalHeight = pnl.getPreferredSize().height + pnlMembres.getPreferredSize().height + 125;

		int totalWidth = pnl.getPreferredSize().width + pnlMembres.getPreferredSize().width + 75;

		this.setSize(new Dimension(totalWidth, totalHeight));

		// on recupere l'indice de l'utilisateur actuel pour retrouver ses permissions
		int indUser = modele.getListeParent().getTableauParent()
				.getIndexUtilisateur(application.getUtilisateurActuel());

		// on verifie si l'utilisateur actuel est en Lecture seule (cad permission 2)
		if (modele.getListeParent().getTableauParent().getPermissions().get(indUser) == 2) {
			// on supprime les elements permettant de rajouter un membre, il ne peut
			// pas ajouter de nouveaux membres a la carte
			pnl.remove(lblMailMembre);
			pnl.remove(txtfMailMembre);
		}

		// on cree le controleur associe
		new ControleurGestionMembresCarte(this, application);

		repaint();
		revalidate();
	}

	// ***********************************
	// ******** GETTERS & SETTERS ********
	// ***********************************

	/**
	 * Getter de la carte (modele) dont on veut gérer les membres.
	 * @return la carte (modele) 
	 */
	public Carte getModele() {
		return modele;
	}

	/**
	 * Getter du champ de texte pour entrer le mail d'un membre à ajouter.
	 * @return le champ de texte pour entrer le mail d'un membre à ajouter
	 */
	public JTextField getTxtfMailMembre() {
		return txtfMailMembre;
	}

	/**
	 * Retourne le conteneurListe dans laquelle la carte (modele) se trouve actuellement (utile pour la redessiner)
	 * @return le conteneurListe parent de la carte (modele)
	 */
	public ConteneurListe getConteneurListe() {
		return conteneurListe;
	}


}
