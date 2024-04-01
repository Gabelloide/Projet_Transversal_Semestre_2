package Modele;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Cette classe représente une étiquette d'une carte. Une étiquette est
 * représentée par sa couleur, qui est un objet de type Color.
 * Cela permet majoritairement d'implémenter des méthodes supplémentaires sur
 * les couleurs pour les manipuler plus facilement.
 * 
 * @see java.awt.Color
 * @author Olivier
 * @author Soan
 * @author Yannick
 * @author Mathis
 */

public class Etiquette implements Serializable {

	// ***********************************
	// ******* ATTRIBUTS *****************
	// ***********************************

	/** Liste des couleurs autorisées pour la construction d'une étiquette. */
	public static final ArrayList<Color> couleurs = new ArrayList<Color>() {
		{
			add(Color.BLUE);
			add(Color.YELLOW);
			add(Color.GREEN);
			add(Color.RED);
			add(Color.ORANGE);
			add(Color.PINK);
			add(Color.CYAN);
			add(Color.MAGENTA);
			add(Color.GRAY);
			add(Color.BLACK);
		}
	};

	/** La couleur finalement attribuée à l'étiquette */
	private Color couleur;

	// ***********************************
	// ******* CONSTRUCTEUR **************
	// ***********************************

	/**
	 * Constructeur d'une étiquette.
	 * Si la couleur n'existe pas, le constructeur interne à la classe Color de
	 * {@link java.awt.Color} renverra une erreur.
	 * 
	 * @param couleur La couleur de l'étiquette.
	 */
	public Etiquette(Color couleur) {
		this.couleur = couleur;
	}

	// ***********************************
	// ******* GETTERS & SETTERS *********
	// ***********************************

	/**
	 * Permet de récupérer la couleur de l'étiquette.
	 * 
	 * @return La couleur de l'étiquette.
	 */
	public Color getCouleur() {
		return this.couleur;
	}

	/**
	 * Permet de récupérer la couleur de l'étiquette sous forme d'entier RGB.
	 * 
	 * @return La couleur de l'étiquette sous forme d'entier RGB.
	 */
	public int getCouleurRGB() {
		return this.couleur.getRGB();
	}

	/**
	 * Permet de modifier la couleur de l'étiquette.
	 * 
	 * @param couleur La nouvelle couleur de l'étiquette.
	 * @throws IllegalArgumentException Si la couleur n'existe pas.
	 */
	public void setCouleur(Color couleur) {
		// Si la couleur n'existe pas, le constructeur interne à la classe Color de
		// Java.awt renverra une erreur.
		this.couleur = couleur;
	}

	// ***********************************
	// ******* METHODES ******************
	// ***********************************
	@Override
	public String toString() {
		return "Etiquette [couleur=" + couleur + "]";
	}
}
