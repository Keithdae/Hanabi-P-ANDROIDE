package model;

import java.util.ArrayList;

import model.Couleur.CardColor;

/**
 * Represente la main d'un joueur.
 */
public class Main {
	/**
	 * La liste de cartes qui composent cette main
	 * @see <a href="Carte.html">Carte</a>
	 */
	private ArrayList<Carte> main;
	/**
	 * Le nombre de cartes que peut contenir la main au maximum
	 */
	private final int nbCartes; // Nombre de cartes par joueur
	
	/**
	 * Constructeur d'une main avec les cartes donnees en parametre 
	 * @param nbCartes	Nombre de cartes que peut contenir cette main
	 * @param cartes	Cartes a ajouter dans cette main
	 */
	public Main(int nbCartes, Carte[] cartes){
		this.nbCartes = nbCartes;
		main = new ArrayList<Carte>(nbCartes);
		for(int i=0; i<nbCartes; i++){
			main.add(cartes[i]);
		}
	}
	
	
	/**
	 * Constructeur d'une main vide avec une limite de nombre de cartes donnee en parametre 
	 * @param nbCartes	Nombre de cartes que peut contenir cette main
	 */
	public Main(int nbCartes){
		this.nbCartes = nbCartes;
		main = new ArrayList<Carte>(nbCartes);
	}
	
	
	/**
	 * Ajoute une carte a cette main
	 * @param c		Carte a ajouter a cette main
	 * @throws AdditionMainPleineException	Si cette main est deja pleine
	 */
	public void ajouterCarte(Carte c) throws AdditionMainPleineException{
		if(main.size() < this.nbCartes){
			main.add(c);
		}else{
			throw new AdditionMainPleineException();
		}
	}
	
	
	/**
	 * Enleve et renvoie la carte de cette main a l'indice donne
	 * @param indice	Position de la carte dans cette main
	 * @return	La carte a l'indice donne
	 * @throws EnleverCarteInexistanteException	Si l'indice est superieur a {@link #nbCartes}
	 */
	public Carte enleverCarte(int indice) throws EnleverCarteInexistanteException{
		if(main.size() > indice){
			return this.main.remove(indice);
		}
		else{
			throw new EnleverCarteInexistanteException();
		}
	}
	
	
	/**
	 * Applique un indice sur la couleur des cartes de cette main
	 * @param c	Couleur indiquee
	 */
	public void indiceCouleur(CardColor c){
		for(int i=0;i<this.nbCartes;i++){
			if(this.main.get(i).getCouleur()==c){
				this.main.get(i).setCouleurConnue(true);
			}
		}
	}
	
	
	/**
	 * Applique un indice sur la valeur des cartes de cette main
	 * @param val	La valeur indiquee
	 */
	public void indiceValeur(int val){
		for(int i=0;i<this.nbCartes;i++){
			if(this.main.get(i).getValeur()==val){
				this.main.get(i).setValeurConnue(true);
			}
		}
	}
	
	
	/**
	 * Renvoie la carte de cette main a l'indice donne
	 * @param indice	Position de la carte dans cette main
	 * @return	La carte a l'indice donne
	 * @throws EnleverCarteInexistanteException	Si l'indice est superieur a {@link #nbCartes}
	 */
	public Carte getCarte(int indice) throws EnleverCarteInexistanteException{
		if(main.size() > indice){
			return this.main.get(indice);
		}
		else{
			throw new EnleverCarteInexistanteException();
		}
	}
}
