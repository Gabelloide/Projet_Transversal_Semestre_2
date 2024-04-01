
package Controleur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Modele.*;
import Vues.FenetreUtilisateur;

/**
 * Classe permettant de créer un utilisateur en renseignant
 * son pseudo, son email, son nom et son prénom.
 * Cette classe est utilisée lors de la première utilisation de
 * l'application, pour créer un utilisateur.
 * Cet utilisateur sera l'utilisateur actuel de l'application, et sera
 * utilisé pour toute la durée de l'utilisation de l'application.
 * {@link Utilisateur} pour des informations complémentaires sur la
 * classe Utilisateur.
 * 
 * @author Olivier
 */

public class Login extends JFrame implements ActionListener {

	// ***********************************
	// ******** ATTRIBUTS ****************
	// ***********************************
	static public final String ACTION_ENVOYER = "ENVOYER";
	private JTextField pseudoField;
	private JTextField emailField;
	private JTextField nomField;
	private JTextField prenomField;
	private TrelloLitePlus application; // L'instance de l'application est passée en paramètre au constructeur de la
																			// classe Login.

	// ***********************************
	// ******** CONSTRUCTEUR *************
	// ***********************************

	/**
	 * Constructeur de la classe Login
	 * 
	 * @param application : l'application est le modèle de l'application dans son
	 *                    ensemble. Cet élément est notamment utile pour la
	 *                    sauvegarde.
	 */
	public Login(TrelloLitePlus application) {
		// On initialise les attributs
		this.application = application;

		// Quelques paramètres de mise en page
		this.setTitle("Connexion");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350, 250);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// Ajout d'un JLabel pour indiquer à l'utilisateur de renseigner ses
		JLabel connect = new JLabel("Veuillez renseigner vos identifiants");

		// On centre le texte dans la fenêtre
		connect.setHorizontalAlignment(JLabel.CENTER);

		// Ajout du JLabel au JFrame
		this.add(connect, BorderLayout.NORTH);

		// Creation d'un formulaire de connection avec Pseudo, email, nom et prénom à
		// remplir dans des JTextField
		JPanel formulaire = new JPanel();
		formulaire.setLayout(new GridLayout(5, 2));

		JLabel pseudo = new JLabel("Pseudo :");
		pseudoField = new JTextField();
		JLabel email = new JLabel("Email :");
		emailField = new JTextField();
		JLabel nom = new JLabel("Nom :");
		nomField = new JTextField();
		JLabel prenom = new JLabel("Prénom :");
		prenomField = new JTextField();

		formulaire.add(email);
		formulaire.add(emailField);
		formulaire.add(pseudo);
		formulaire.add(pseudoField);
		formulaire.add(nom);
		formulaire.add(nomField);
		formulaire.add(prenom);
		formulaire.add(prenomField);

		// Ajout d'un bouton d'envoi pour effectuer la création de l'utilisateur
		JPanel bouton = new JPanel();
		JButton envoyer = new JButton("Envoyer");

		// Création d'un écouteur pour le bouton envoyer
		envoyer.addActionListener(this);
		envoyer.setActionCommand(ACTION_ENVOYER);
		bouton.add(envoyer);

		// Ajout des éléments au JFrame, avec leur position
		this.add(formulaire, BorderLayout.CENTER);
		this.add(bouton, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	/**
	 * Méthode permettant de gérer les actions effectuées par l'utilisateur
	 * 
	 * @param e : l'action déclenchée par l'utilisateur
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_ENVOYER)) {

			// Seul l'email est réellement déterminant pour l'identification d'un
			// utilisateur : par conséquent il ne peut pas être vide.
			if (emailField.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez au moins renseigner une adresse email.");
				return;
			}

			else {

				Utilisateur utilisateurConnecte = null; // L'utilisateur connecté est initialisé à null pour le moment.

				// Si l'utilisateur essaye de se connecter avec une adresse qui existe déjà dans
				// la base de données, alors il sera connecté en son nom.
				int i = 0;
				boolean utilisateurTrouve = false;
				// On effectue une recherche dans la liste des utilisateurs de l'application
				while (i < application.getUtilisateurs().size() && !utilisateurTrouve) {

					// Si on a trouvé un email identique à celui entré
					if (application.getUtilisateurs().get(i).getEmail().equals(emailField.getText())) {

						// On effectue le comportement habituel, mais l'utilisateur ici est un
						// utilisateur qui existait déjà précédemment.
						utilisateurTrouve = true;

						// On le supprime de la liste des utilisateurs. Il sera réajouté lors de la
						// fermeture de l'application.
						utilisateurConnecte = application.retirerUtilisateur(i);
						application.setUtilisateurActuel(utilisateurConnecte);

						// On ajoute également cet utilisateur à la liste des
						// utilisateurs de l'application pour une potentielle
						// sauvegarde.
						application.ajouterUtilisateur(utilisateurConnecte);

						// On lance l'application sous l'identité de l'utilisateur connecté :
						this.dispose();
						FenetreUtilisateur window = new FenetreUtilisateur(utilisateurConnecte, application);
						window.setVisible(true);

					}
					i++;
				}

				// A ce stade, si utilisateurConnecte est toujours null, alors l'utilisateur
				// n'existait pas dans la base de données.
				if (utilisateurConnecte == null) {

					// On crée donc un nouvel utilisateur avec les informations renseignées dans le
					// formulaire :

					// Si le bouton envoyer est cliqué, on récupère les informations du formulaire
					String pseudo = pseudoField.getText();
					String email = emailField.getText();
					String nom = nomField.getText();
					String prenom = prenomField.getText();

					// On va également qualifier d'anonyme tout utilisateur n'ayant pas renseigné de
					// pseudo, nom ou prénom.
					if (pseudo.equals("")) {
						pseudo = "Anonyme";
					}
					if (nom.equals("")) {
						nom = "Anonyme";
					}
					if (prenom.equals("")) {
						prenom = "Anonyme";
					}

					// On crée un utilisateur avec ces informations
					// Cet utilisateur est celui présent dans la classe principale de l'application.
					// Il sera l'utilisateur actuel pour tout le reste de l'utilisation de
					// l'application.

					utilisateurConnecte = new Utilisateur(email, nom, prenom, pseudo);
					application.setUtilisateurActuel(utilisateurConnecte);

					// On ajoute également cet utilisateur à la liste des utilisateurs de
					// l'application pour une potentielle sauvegarde.
					application.ajouterUtilisateur(utilisateurConnecte);
					this.dispose();

					// On lance l'application sous l'identité de l'utilisateur connecté :
					FenetreUtilisateur window = new FenetreUtilisateur(utilisateurConnecte, application);
					window.setVisible(true);
				}
			}
		}
	}
}
