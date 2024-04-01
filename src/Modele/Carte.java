
package Modele;

import java.util.*;

import Vues.ApercuCarte;

import java.awt.Color;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * La classe Carte implémente un objet Carte correspondant à une tâche à
 * effectuer dans le projet.
 * La carte est contenue dans une liste {@link Modele.Liste} qui peut contenir
 * plusieurs instances de la classe carte de manière simultanée.
 * 
 * @author Soan
 * @author Olivier
 * @author Mathis
 * @author Yannick
 * @see Modele.Liste
 */

public class Carte implements Serializable {

	// ***********************************
	// ******* ATTRIBUTS *****************
	// ***********************************

	private static int compteur;
	private int identifiant;
	private Liste listeParent;
	private String background;
	private ArrayList<Etiquette> etiquettes;
	private String titre;
	private String description;
	private ArrayList<Commentaire> commentaires = new ArrayList<Commentaire>(); // La liste des commentaires associés à
																																							// cette carte.
	private ArrayList<Utilisateur> membres = new ArrayList<Utilisateur>(); // La liste des membres affectés à cette carte.
	private Carte carte_jointe; // Carte jointe à la carte courante (la carte courante dépend de la carte
															// jointe)
	private LocalDateTime date_debut;
	private LocalDateTime date_fin;
	private boolean termine = false;
	private ApercuCarte vueCarte = null;

	// ***********************************
	// ******* CONSTRUCTEUR *************
	// ***********************************

	/**
	 * Constructeur de la classe Carte : on spécifie ici seulement le titre de la
	 * carte pour la construction. Le reste des attributs est initialisé à null.
	 * L'identifiant de la carte est auto incrémenté à la construction.
	 * 
	 * @param titre Le titre de la carte.
	 */
	public Carte(String titre) {
		identifiant = compteur;
		this.titre = titre;
		etiquettes = new ArrayList<Etiquette>();
		commentaires = new ArrayList<Commentaire>();
		membres = new ArrayList<Utilisateur>();
		carte_jointe = null;
		date_debut = null;
		date_fin = null;
		compteur++;
	}

	/**
	 * Constructeur de la classe Carte : on spécifie ici le titre et la description
	 * de la carte pour la construction. Le reste des attributs est initialisé à
	 * null.
	 * L'identifiant de la carte est auto incrémenté à la construction.
	 * 
	 * @param titre       Le titre de la carte.
	 * @param description La description de la carte.
	 */
	public Carte(String titre, String description) {
		identifiant = compteur;
		this.titre = titre;
		this.etiquettes = new ArrayList<Etiquette>();
		this.description = description;
		commentaires = new ArrayList<Commentaire>();
		membres = new ArrayList<Utilisateur>();
		carte_jointe = null;
		date_debut = null;
		date_fin = null;
		compteur++;
	}

	// ***********************************
	// ******* METHODES ******************
	// ***********************************

	/**
	 * Ajoute une étiquette à la carte via son attribut
	 * {@link Modele.Carte#etiquettes}. L'étiquette est un objet
	 * {@link Modele.Etiquette}.
	 * On suppose ici que l'étiquette est déjà valide, sinon la construction de
	 * celle-ci aurait renvoyé une erreur.
	 * On effectue un parcours des étiquettes de la carte pour ne pas ajouter une
	 * étiquette de la même couleur qui existe déjà.
	 * La vérification de l'unicité de l'étiquette se fait sur sa couleur.
	 * 
	 * @param e l'étiquette à ajouter
	 */
	public void ajouterEtiquette(Etiquette e) {
		// On suppose ici que l'étiquette est déjà valide, sinon la construction de
		// celle-ci aurait renvoyé une erreur.

		// On effectue un parcours des étiquettes de la carte pour ne pas ajouter une
		// étiquette de la même couleur qui existe déjà.
		boolean trouve = false;
		for (int i = 0; i < this.etiquettes.size(); i++) {
			if (etiquettes.get(i) != null) {
				trouve = (e.getCouleur() == etiquettes.get(i).getCouleur());
			}
		}
		if (!trouve) { // Si l'étiquette n'a pas été trouvée
			// On peut l'ajouter
			etiquettes.add(e);
			System.out.println("Etiquette ajoutée avec succès");
		} else {
			System.out.println("Impossible d'ajouter cette étiquette: elle existe déjà.");
		}

	}

	/**
	 * Modifie la couleur d'une étiquette existante sur la carte.
	 * Effectue diverses vérifications sur la nouvelle couleur.
	 *
	 * @param oldCouleur   la couleur de l'étiquette à modifier
	 * @param nvlleCouleur la nouvelle couleur de l'étiquette
	 */
	public void modifierEtiquette(Color oldCouleur, Color nvlleCouleur) {
		boolean oldCouleurTrouvee = false;
		int indiceAncienneCouleur = -1;
		for (int i = 0; i < this.etiquettes.size(); i++) {
			if (etiquettes.get(i) != null && etiquettes.get(i).getCouleur() == oldCouleur) { // Si l'ancienne couleur est
				// rencontrée à l'indice i
				oldCouleurTrouvee = true;
				indiceAncienneCouleur = i;
			}
		}
		// Ancienne couleur trouvée, on peut la changer.
		// On vérifie que la nouvelle couleur est valide
		boolean nouvelleCouleurExiste = false;
		for (int i = 0; i < Etiquette.couleurs.size(); i++) {
			if (Etiquette.couleurs.get(i) == nvlleCouleur) {
				nouvelleCouleurExiste = true;
			}
		}

		if (oldCouleurTrouvee && nouvelleCouleurExiste) { // Si elle existe ET que la nouvelle couleur est valide
			// On va changer l'ancienne en la nouvelle :
			etiquettes.get(indiceAncienneCouleur).setCouleur(nvlleCouleur);
		} else if (!oldCouleurTrouvee) {
			System.out.println("Impossible de modifier l'étiquette: l'ancienne couleur n'a pas été trouvée.");
		} else {
			System.out.println("Impossible de modifier l'étiquette: la nouvelle couleur n'est pas valide.");
		}
	}

	/**
	 * Supprime une étiquette de la carte.
	 * 
	 * @param couleur la couleur de l'étiquette à supprimer
	 */
	public void supprimerEtiquette(Color couleur) {
		// On suppose ici que l'étiquette est déjà valide, sinon la construction de
		// celle-ci aurait renvoyé une erreur.

		// On effectue un parcours des étiquettes de la carte pour ne pas supprimer une
		// étiquette de la même couleur qui existe déjà.
		boolean trouve = false;
		int indice = -1;
		int i = 0;
		while (!trouve && i < this.etiquettes.size()) {
			if (etiquettes.get(i) != null) {
				// On compare sur le RGB
				trouve = (couleur.getRGB() == etiquettes.get(i).getCouleur().getRGB());
				indice = i;
				i++;
			}
		}
		if (trouve) { // Si l'étiquette a été trouvée
			etiquettes.remove(indice);
			System.out.println("Etiquette supprimée avec succès");
		} else {
			System.out.println("Impossible de supprimer cette étiquette: elle n'existe pas.");
		}
	}

	/**
	 * Joint une autre carte à la carte courante.
	 * L'ajout est effectué sur l'attribut {@link Modele.Carte#carte_jointe}.
	 * Renvoie une erreur si la carte à joindre est déjà jointe.
	 *
	 * @param c1 la carte à joindre
	 */
	public void joindre(Carte c1) {
		if (c1 != this.getCarteJointe()) {
			this.carte_jointe = c1;
		} else {
			System.out.println("Cartes déjà jointes");
		}
	}

	/**
	 * Sépare la carte jointe à la carte courante.
	 *
	 * @param c1 la carte à séparer
	 */
	public void separer(Carte c1) {
		if (c1 == this.getCarteJointe()) {
			this.carte_jointe = null;
			c1.setCarteJointe(null);
		} else {
			System.out.println("Cartes non liées");
		}
	}

	/**
	 * Modifie les dates de début et de fin de la carte.
	 * 
	 * @param nvllDeb la nouvelle date de début
	 * @param nvllFin la nouvelle date de fin
	 */
	public void modifierDates(LocalDateTime nvllDeb, LocalDateTime nvllFin) {
		date_debut = nvllDeb;
		date_fin = nvllFin;
	}

	/**
	 * Ajoute un commentaire à la carte. Le commentaire est un objet de type
	 * {@link Modele.Commentaire}.
	 * Il est ajouté sur l'attribut {@link Modele.Carte#commentaires}.
	 * 
	 * @param c1 le commentaire à ajouter
	 */
	public void ajouterCommentaire(Commentaire c1) {
		int i = rechercheArrayList(c1); // On récupère l'indice (position) du commentaire souhaité
		if (i == -1) { // S'il existe, on l'ajoute à la liste des commentaires de la carte
			commentaires.add(c1);
		} else {
			System.out.println("Commentaire déjà présent");
		}
	}

	/**
	 * Supprime un commentaire de la carte.
	 * 
	 * @param c1 le commentaire à supprimer
	 */
	public void supprimerCommentaire(Commentaire c1) {
		int i = rechercheArrayList(c1); // On récupère l'indice (position) du commentaire souhaité
		if (i != -1) {
			commentaires.remove(i); // S'il existe, on effectue la suppression.
		} else { // Sinon, on renvoie que le commentaire n'existe pas
			System.out.println("Commentaire non présent");
		}
	}

	/**
	 * Modifie le contenu d'un commentaire. Cette méthode fait donc appel à la
	 * méthode {@link Modele.Commentaire#setContenu(String)}.
	 * 
	 * @param c1      le commentaire à modifier
	 * @param contenu le nouveau contenu du commentaire
	 */
	public void modifierCommentaire(Commentaire c1, String contenu) {
		int i = rechercheArrayList(c1); // On récupère l'indice (position) du commentaire souhaité
		if (i != -1) { // S'il existe, on change son contenu
			c1.setContenu(contenu);
		} else {
			System.out.println("Commentaire non présent");
		}
	}

	/**
	 * Modifie l'état de la carte. Si la carte est terminée, elle ne l'est plus et
	 * inversement.
	 * La modification est effectuée sur l'attribut {@link Modele.Carte#termine}.
	 * 
	 * @see Modele.Carte#termine
	 */
	public void changerEtat() {
		// Change l'état actuel de la carte. Si la carte est terminée, retire la valeur
		// de terminason. Si elle ne l'est pas, alors on la définit comme terminée
		termine = !termine;
	}

	/**
	 * Ajoute un membre à la carte.
	 * Le membre est un objet de type {@link Modele.Utilisateur}.
	 * @param membre le membre à ajouter
	 */
	public void ajouterMembre(Utilisateur membre) {
		if (!membres.contains(membre)) {
			if (this.getListeParent().getTableauParent().getMembres().contains(membre)) {
				membres.add(membre);
			} else {
				System.out.println("L'utilisateur n'est pas membre du tableau parent");
			}
		} else {
			System.out.println("Membre déjà présent");
		}
	}

	/**
	 * Supprime un membre de la carte.
	 * Le membre est un objet de type {@link Modele.Utilisateur}.
	 * @param membre le membre à supprimer
	 */
	public void supprimerMembre(Utilisateur membre) {
		if (membres.contains(membre)) {
			membres.remove(membre);
		} else {
			System.out.println("Membre non présent");
		}
	}

	// ***********************************
	// ****** FONCTIONS & PROCEDURES *****
	// ***********************************

	/**
	 * Renvoie l'indice de l'identifiant du commentaire c1 dans la liste des
	 * commentaires.
	 * Permet de rechercher l'identifiant du commentaire, -1 si non présent.
	 * Cette méthode est appelée dans les méthodes
	 * {@link Modele.Carte#ajouterCommentaire(Commentaire)} et
	 * {@link Modele.Carte#supprimerCommentaire(Commentaire)} et
	 * {@link Modele.Carte#modifierCommentaire(Commentaire, String)}.
	 * 
	 * @param c1 le commentaire dont on veut l'indice.
	 * @return l'indice de l'identifiant du commentaire c1 dans la liste des
	 *         commentaires, -1 si non présent.
	 */
	public int rechercheArrayList(Commentaire c1) {
		int i = -1;
		if (commentaires.contains(c1)) {
			boolean trouve = false;
			i = 0;
			while (!trouve && i < commentaires.size()) {
				if (commentaires.get(i) == c1) {
					trouve = true;
				} else {
					i++;
				}
			}
		}
		return i;
	}

	// ***********************************
	// ********* Setter & Getter *********
	// ***********************************

	/**
	 * Renvoie l'identifiant de la carte.
	 * 
	 * @return l'identifiant de la carte.
	 */
	public int getIdentifiant() {
		return identifiant;
	}

	/**
	 * Modifie l'identifiant de la carte.
	 * 
	 * @param identifiant le nouvel identifiant de la carte.
	 */
	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * Renvoie la couleur de fond de la carte.
	 * 
	 * @return la couleur de fond de la carte.
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * Modifie la couleur de fond de la carte.
	 * 
	 * @param bg la nouvelle couleur de fond de la carte.
	 */
	public void setBackground(String bg) {
		background = bg;
	}

	/**
	 * Retourne le titre de la carte
	 * @return le titre de la carte
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * Modifie le titre de la carte
	 * @param titre le nouveau titre de la carte
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * Retourne la description de la carte
	 * @return la description de la carte
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Retourne la date de début de la carte sous format LocalDateTime
	 * @return la date de début de la carte sous format LocalDateTime
	 */
	public LocalDateTime getDateDebut() {
		return date_debut;
	}

	/**
	 * Retourne la date de fin de la carte sous format LocalDateTime
	 * @return la date de fin de la carte sous format LocalDateTime
	 */
	public LocalDateTime getDateFin() {
		return date_fin;
	}

	/**
	 * Retourne la date de début de la carte sous format String dd/MM/yyyy
	 * @return la date de début de la carte sous format String dd/MM/yyyy
	 */
	public String getDateDebutFormatee() {
		if (date_debut == null)
			return "";
		else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return date_debut.format(formatter);
		}
	}

	/**
	 * Retourne la date de fin de la carte sous format String dd/MM/yyyy
	 * @return la date de fin de la carte sous format String dd/MM/yyyy
	 */
	public String getDateFinFormatee() {
		if (date_fin == null)
			return "";
		else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return date_fin.format(formatter);
		}
	}

	/**
	 * Modifie la date de début de la carte
	 * @param date_debut la nouvelle date de début de la carte
	 */
	public void setDate_debut(LocalDateTime date_debut) {
		this.date_debut = date_debut;
	}

	/**
	 * Modifie la date de fin de la carte
	 * @param date_fin la nouvelle date de fin de la carte
	 */
	public void setDate_fin(LocalDateTime date_fin) {
		this.date_fin = date_fin;
	}

	/**
	 * Renvoie l'état de la carte (true si terminée, false sinon)
	 * @return l'état de la carte (boolean)
	 */
	public boolean getEtat() {
		return termine;
	}

	/**
	 * Modifie la description de la carte
	 * @param nvllDescription la nouvelle description de la carte
	 */
	public void setDescription(String nvllDescription) {
		description = nvllDescription;
	}

	/**
	 * Récupère la liste des étiquettes de la carte
	 * @return la liste des étiquettes de la carte
	 */
	public ArrayList<Etiquette> getEtiquettes() {
		return etiquettes;
	}

	/**
	 * Modifie la liste des étiquettes de la carte
	 * @param etiquettes la nouvelle liste des étiquettes de la carte
	 */
	public void setEtiquettes(ArrayList<Etiquette> etiquettes) {
		this.etiquettes = etiquettes;
	}

	/**
	 * Récupère la liste des commentaires de la carte
	 * @return la liste des commentaires de la carte
	 */
	public ArrayList<Commentaire> getCommentaires() {
		return commentaires;
	}

	/**
	 * Modifie la liste des commentaires de la carte
	 * @param commentaires la nouvelle liste des commentaires de la carte
	 */
	public void setCommentaires(ArrayList<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	/**
	 * Récupère la carte actuellement jointe à la carte actuelle
	 * @return une carte
	 */
	public Carte getCarteJointe() {
		return carte_jointe;
	}

	/**
	 * Modifie la carte actuellement jointe à la carte actuelle
	 * @param carteJointe la nouvelle carte jointe à la carte actuelle
	 */
	public void setCarteJointe(Carte carteJointe) {
		this.carte_jointe = carteJointe;
	}

	/**
	 * Permet de définir la liste parente de la carte
	 * @param liste la liste parente de la carte
	 */
	public void setListeParent(Liste liste) {
		this.listeParent = liste;
	}

	/**
	 * Récupère la liste parente de la carte
	 * @return la liste parente de la carte
	 */
	public Liste getListeParent() {
		return this.listeParent;
	}

	/**
	 * Récupère la vue associée à la carte
	 * @return la vue associée à la carte
	 */
	public ApercuCarte getApercuCarte() {
		return vueCarte;
	}

	/**
	 * Modifie la vue associée à la carte
	 * @param vueCarte la nouvelle vue associée à la carte
	 */
	public void setApercuCarte(ApercuCarte vueCarte) {
		this.vueCarte = vueCarte;
	}

	/**
	 * Récupère la liste des membres de la carte
	 * @return la liste des membres de la carte
	 */
	public ArrayList<Utilisateur> getMembres() {
		return membres;
	}

	// ***********************************
	// ********* AFFICHAGES **************
	// ***********************************

	@Override
	public String toString() {
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		if (date_debut != null && date_fin != null) {
			return "\nCarte [identifiant=" + identifiant + ", etiquettes=" + etiquettes + ", titre=" + titre
					+ ", description=" + description + ", date_debut="
					+ date_debut.format(datePattern)
					+ ", date_fin=" + date_fin.format(datePattern) + ", termine=" + termine + ", commentaires=" + commentaires
					+ "]";
		} else {
			return "\nCarte [identifiant=" + identifiant
					+ ", etiquettes=" + etiquettes
					+ ", titre=" + titre
					+ ", description=" + description
					+ ", date_debut=" + date_debut
					+ ", date_fin=" + date_fin
					+ ", termine=" + termine
					+ ", commentaires=" + commentaires
					+ "]";
		}
	}

	/**
	 * Affiche les différences étiquettes de la carte. Fait appel à la méthode
	 * {@link Modele.Etiquette#toString()}.
	 */
	public void afficherEtiquettes() {
		for (int i = 0; i < etiquettes.size(); i++) {
			if (etiquettes.get(i) != null) {
				System.out.println(etiquettes.get(i));
			}
		}
	}

}
