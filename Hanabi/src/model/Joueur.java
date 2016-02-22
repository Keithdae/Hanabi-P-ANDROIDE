package model;

/**
 * Represente un joueur.
 */
public abstract class Joueur {
	/**
	 * La main du joueur
	 * @see <a href="Main.html">Main</a>
	 */
	protected Main main;
	/**
	 * Le nom du joueur
	 */
	protected String nom; 
	
	
	/**
	 * Constructeur d'un joueur avec un nom et une taille de main definis
	 * @param nom		Le nom du joueur
	 * @param nbCartes	Nombre maximum de cartes de sa main 
	 */
	public Joueur(String nom,int nbCartes){
		this.nom = nom;
		this.main = new Main(nbCartes);
	}
	
	
	/**
	 * @return	La main de ce joueur
	 */
	public Main getMain(){
		return this.main;
	}
	
	/**
	 * @return	Le nom de ce joueur
	 */
	public String getNom(){
		return this.nom;
	}
}
