package Vues;

import javax.swing.*;

import Modele.*;
import Controleur.*;

import java.awt.*;

/**
 * Cette classe implémente une fenêtre pour ajouter des permissions à un membre
 * dans un tableau :
 * Elle comprend un champ de texte pour entrer le mail du membre à ajouter, et
 * un bouton pour valider (dans le controleur).
 * Elle est ainsi liée au contrôleur
 * {@link Controleur.ControleurModificationPerms}.
 * La vue va récupérer le tableau actuellement visiter pour en afficher les
 * membres s'il y en a.
 * 
 * @author Olivier
 * @author Soan
 */

public class VueModificationPerms extends JFrame {
	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************

	private TrelloLitePlus application;
	private JPanel vMembres;
	private JLabel lblMailMembre;
	private JTextField txtfMailMembre;

	// ***********************************
	// ******** CONSTRUCTEURS ************
	// ***********************************

	/**
	 * Constructeur de la vue de modification des permissions d'un membre dans un tableau.
	 * @param application l'application TrelloLitePlus (peut être utile pour la sauvegarde)
	 */
	public VueModificationPerms(TrelloLitePlus application) {
		this.application = application;
		lblMailMembre = new JLabel("Entrez le mail de l'utilisateur à ajouter :");
		txtfMailMembre = new JTextField(15);
		vMembres = new JPanel();

		this.setSize(400, 500);
		this.setTitle("Modification Permissions");
		this.setResizable(false);
		this.setVisible(true);

		redessiner();

	}

	// ***********************************
	// ******** GETTERS & SETTERS ********
	// ***********************************

	/**
	 * Permet d'accéder au champ de texte pour entrer le mail du membre à ajouter.
	 * @return le champ de texte
	 */
	public JTextField getTxtfMailMembre() {
		return txtfMailMembre;
	}

	// ***********************************
	// ******** METHODES *****************
	// ***********************************

	/**
	 * Méthode permettant de redessiner la vue de modification des permissions d'un membre dans un tableau.
	 * Elle est appelée à chaque fois que l'on veut mettre à jour la vue.
	 * Elle va donc supprimer tous les éléments de la fenêtre, puis les recréer.
	 * Elle s'occupe d'ajouter le controleur associé à la vue.
	 * Elle est appelée dans le contructeur dans une optique de factorisation du code.
	 */
	public void redessiner() {

		getContentPane().removeAll();

		setLayout(new FlowLayout());

		lblMailMembre = new JLabel("Entrez le mail de l'utilisateur à ajouter :");
		txtfMailMembre = new JTextField(15);

		JPanel pnl = new JPanel();
		pnl.setLayout(new BorderLayout());

		pnl.add(lblMailMembre);
		pnl.add(txtfMailMembre, BorderLayout.SOUTH);

		vMembres = new JPanel();
		vMembres.setLayout(new BoxLayout(vMembres, BoxLayout.Y_AXIS));
		// creation et ajout d'une vue et son controleur associé pour chq membres du
		// tableau actuel
		VueMembre v = new VueMembre(application);
		vMembres.add(v);

		for (int i = 0; i < application.getTableauActuel().getMembres().size(); i++) {
			if (application.getTableauActuel().getMembres().get(i) != application.getUtilisateurActuel()) {
				v = new VueMembre(application.getTableauActuel().getMembres().get(i), application);
				new ControleurPermMembre(v, this, application);
				vMembres.add(v);
			}
		}

		add(vMembres);
		add(pnl);

		// calcul de la taille de tous les elements de la classe + 75 pour avoir
		// un espace vide sous le bouton modifier
		int totalHeight = pnl.getPreferredSize().height + vMembres.getPreferredSize().height + 125;

		int totalWidth = pnl.getPreferredSize().width + vMembres.getPreferredSize().width;

		this.setSize(new Dimension(totalWidth, totalHeight));

		// On réajoute le controleur
		new ControleurModificationPerms(this, application);

		repaint();
		revalidate();
	}

}
