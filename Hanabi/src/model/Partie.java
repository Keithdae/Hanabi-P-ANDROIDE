package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.Couleur.CardColor;

/**
 * Represente une partie.
 */
public class Partie {
	/**
	 * Nombre de joueurs dans cette partie
	 * @see <a href="Joueur.html">Joueur</a>
	 */
	protected int nbJoueurs;
	/**
	 * Nombre de cartes par joueur
	 * @see <a href="Carte.hhtml">Carte</a>
	 */
	protected int nbCartes;
	/**
	 * Ensemble des joueurs
	 * @see <a href="Joueur.html">Joueur</a>
	 */
	protected Joueur[] joueurs;
	/**
	 * Nombre de fautes commises dans cette partie
	 */
	protected int jetonEclair;
	/**
	 * Le nombre maximal de jetons indices dans cette partie
	 */
	protected int maxIndices; 
	/**
	 * Le nombre de jetons indices disponible dans cette partie
	 */
	protected int jetonIndice;
	/**
	 * Definit si cette partie est jouee avec les cartes multicolores
	 */
	protected boolean multicolor;
	/**
	 * Les cartes dans lesquelles les joueurs piochent
	 * @see <a href="Carte.hhtml">Carte</a>
	 */
	protected ArrayList<Carte> pioche;
	/**
	 * Les cartes defaussees au cours de cette partie
	 * @see <a href="Carte.hhtml">Carte</a>
	 */
	protected ArrayList<Carte> defausse;
	/**
	 * Les cartes jouees au cours de cette partie, rangees par couleur
	 * @see <a href="Couleur.hhtml">Couleur</a>
	 * @see <a href="Carte.hhtml">Carte</a>
	 */
	protected HashMap<CardColor, ArrayList<Carte>> cartesJouees;
	/**
	 * Indique quel joueur joue
	 */
	protected int aQuiLeTour;
	
	/**
	 * Constructeur d'une partie avec un nombre de joueur, d'indice donnes, et si la couleur multicolore est autorisee
	 * @param nbJoueurs		Nombre de joueurs dans la partie
	 * @param maxIndices	Nombre d'indices maximum
	 * @param multicolor	Couleur multicolore autorisee ou non
	 */
	public Partie(int nbJoueurs, int maxIndices, boolean multicolor){
		this.nbJoueurs = nbJoueurs;
		if(nbJoueurs == 2 || nbJoueurs == 3){
			this.nbCartes = 5;
		}
		else if(nbJoueurs == 4 || nbJoueurs == 5){
			this.nbCartes = 4;
		}
		this.jetonEclair = 0;
		this.aQuiLeTour = 0;
		this.maxIndices = maxIndices;
		this.jetonIndice = maxIndices;
		this.multicolor = multicolor;
		this.defausse = new ArrayList<Carte>();
		this.pioche = new ArrayList<Carte>();
		this.cartesJouees = new HashMap<CardColor, ArrayList<Carte>>(5);
		this.cartesJouees.put(CardColor.BLANC, new ArrayList<Carte>());
		this.cartesJouees.put(CardColor.BLEU, new ArrayList<Carte>());
		this.cartesJouees.put(CardColor.VERT, new ArrayList<Carte>());
		this.cartesJouees.put(CardColor.ROUGE, new ArrayList<Carte>());
		this.cartesJouees.put(CardColor.JAUNE, new ArrayList<Carte>());
		if(this.multicolor){
			this.cartesJouees.put(CardColor.MULTI, new ArrayList<Carte>());
		}
	}
	
	/**
	 * Pioche une carte dans la main du joueur donne
	 * @param j	Joueur qui pioche la carte
	 * @throws AdditionMainPleineException	Si la main du joueur est deja pleine
	 * @throws PiocheVideException			Si la pioche ne contient plus de carte
	 */
	public void pioche(Joueur j) throws AdditionMainPleineException, PiocheVideException{
		if(!this.pioche.isEmpty()){
			j.getMain().ajouterCarte(this.pioche.remove(this.pioche.size()-1));
		}else{
			throw new PiocheVideException();
		}
	}
	
	/**
	 * Defausse la carte donnee de la main du joueur
	 * @param j		Le joueur qui defausse la carte
	 * @param index	L'indice de la carte a defausse
	 * @throws EnleverCarteInexistanteException	Si l'indice est superieur a {@link #nbCartes}
	 * @throws AdditionMainPleineException		Si la main du joueur est deja pleine
	 * @throws PiocheVideException				Si la pioche ne contient plus de carte
	 */
	public void defausse(Joueur j, int index) throws EnleverCarteInexistanteException, AdditionMainPleineException, PiocheVideException{
		Carte carte = j.getMain().enleverCarte(index);
		this.defausse.add(carte);

        // Rajoute un jeton indice s'il n'y a pas déjà tous les indices disponibles
		if(this.jetonIndice != this.maxIndices){
			this.jetonIndice ++;
		}
		pioche(j);
		this.aQuiLeTour = (this.aQuiLeTour+1)%this.nbJoueurs;
	}
	
	
	/**
	 * Joue la carte donnee de la main du joueur
	 * @param j			Le joueur qui joue la carte
	 * @param indice	L'indice de la carte a joue
	 * @throws EnleverCarteInexistanteException	Si l'indice est superieur a {@link #nbCartes}
	 * @throws PartiePerdueException			Si {@link #jetonEclair} atteint 3
	 * @throws AdditionMainPleineException		Si la main du joueur est deja pleine
	 * @throws PiocheVideException				Si la pioche ne contient plus de carte
	 */
	public void joueCarte(Joueur j, int indice) throws EnleverCarteInexistanteException, PartiePerdueException, AdditionMainPleineException, PiocheVideException{
		// Enleve la carte de la main du joueur
        Carte carte = j.getMain().enleverCarte(indice);

        // La carte est valide
		if(this.cartesJouees.get(carte.getCouleur()).size()+1 == carte.getValeur()){
			this.cartesJouees.get(carte.getCouleur()).add(carte);
		}

        // La carte n'est pas valide
		else{
			this.defausse.add(carte);
			this.jetonEclair++;
			if(this.jetonEclair == 3){
				throw new PartiePerdueException();
			}
		}
		pioche(j);
		this.aQuiLeTour = (this.aQuiLeTour+1)%this.nbJoueurs;
	}
	
	
	/**
	 * Donne un indice sur une couleur donnee pour la main d'un joueur
	 * @param j	Le joueur qui recoit l'indice
	 * @param c	La couleur indiquee
	 * @throws IndiceSoitMemeException	Si le joueur tente de se donner un indice
	 */
	public void indiceCouleur(Joueur j, CardColor c) throws IndiceSoitMemeException{
		if(j != this.joueurs[this.aQuiLeTour]){
			j.getMain().indiceCouleur(c);
			this.jetonIndice--;
			this.aQuiLeTour = (this.aQuiLeTour+1)%this.nbJoueurs;
		}
		else{
			throw new IndiceSoitMemeException();
		}
	}
	
	
	/**
	 * Donne un indice sur une valeur donnee pour la main d'un joueur
	 * @param j		Le joueur qui recoit l'indice
	 * @param val	La valeur indiquee
	 * @throws IndiceSoitMemeException	Si le joueur tente de se donner un indice
	 */
	public void indiceValeur(Joueur j, int val) throws IndiceSoitMemeException{
		if(j != this.joueurs[this.aQuiLeTour]){
			j.getMain().indiceValeur(val);
			this.jetonIndice--;
			this.aQuiLeTour = (this.aQuiLeTour+1)%this.nbJoueurs;
		}
		else{
			throw new IndiceSoitMemeException();
		}
	}
	
	
	/**
	 * @return Le score de la partie
	 */
	public int calculerPoints(){
		int total = 0;
		total += this.cartesJouees.get(CardColor.BLANC).size();
		total += this.cartesJouees.get(CardColor.JAUNE).size();
		total += this.cartesJouees.get(CardColor.VERT).size();
		total += this.cartesJouees.get(CardColor.BLEU).size();
		total += this.cartesJouees.get(CardColor.ROUGE).size();
		if(this.multicolor){
			total += this.cartesJouees.get(CardColor.MULTI).size();
		}
		return total;
	}
	
	/**
	 * Initialise une partie avec les noms des joueurs donnes, et remplit leurs mains
	 * @param nomsJoueurs	Noms des joueurs de la partie
	 * @throws AdditionMainPleineException		Si la main du joueur est deja pleine
	 * @throws PiocheVideException				Si la pioche ne contient plus de carte
	 */
	public void initPartie(String[] nomsJoueurs) throws AdditionMainPleineException, PiocheVideException{
		this.joueurs = new Joueur[this.nbJoueurs];
		for(int i=0; i<this.nbJoueurs; i++){
			this.joueurs[i] = new JoueurHumain(nomsJoueurs[i],this.nbCartes,this);
		}
		creerLesCartes();
		for(int i=0; i<this.nbCartes; i++){
			for(Joueur j : this.joueurs){
				pioche(j);
			}
		}
	}

	
	/**
	 * Construit la pioche avec les cartes necessaires puis melange les cartes
	 */
	private void creerLesCartes() {
		//creates the cards
		ArrayList<Carte> deck = new ArrayList<Carte>();
		for(int i=1; i<6; i++){
			if(i==1){
				for(int j=0;j<3;j++){
					deck.add(new Carte(CardColor.BLANC,i));
					deck.add(new Carte(CardColor.JAUNE,i));
					deck.add(new Carte(CardColor.VERT,i));
					deck.add(new Carte(CardColor.BLEU,i));
					deck.add(new Carte(CardColor.ROUGE,i));
					if(this.multicolor){
						deck.add(new Carte(CardColor.MULTI,i));
					}
				}
			}
			else if(i==5){
				deck.add(new Carte(CardColor.BLANC,i));
				deck.add(new Carte(CardColor.JAUNE,i));
				deck.add(new Carte(CardColor.VERT,i));
				deck.add(new Carte(CardColor.BLEU,i));
				deck.add(new Carte(CardColor.ROUGE,i));	
				if(this.multicolor){
					deck.add(new Carte(CardColor.MULTI,i));
				}
			}
			else{
				for(int j=0;j<2;j++){
					deck.add(new Carte(CardColor.BLANC,i));
					deck.add(new Carte(CardColor.JAUNE,i));
					deck.add(new Carte(CardColor.VERT,i));
					deck.add(new Carte(CardColor.BLEU,i));
					deck.add(new Carte(CardColor.ROUGE,i));
					if(this.multicolor){
						deck.add(new Carte(CardColor.MULTI,i));
					}
				}
			}
		}
		//shuffles the cards into the deck
		int deckSize = deck.size();
		Random rng = new Random();
		for(int i=0; i<deckSize; i++){
			int n = rng.nextInt(deck.size());
			this.pioche.add(deck.remove(n));
		}
	}
	
	/**
	 * Definit les {@link #joueurs} de cette partie a partir d'un ensemble de joueurs donne
	 * @param joueurs	Les joueurs
	 */
	public void setJoueurs(Joueur[] joueurs){
		this.joueurs = joueurs;
	}
	
	
	/**
	 * @return	Les joueurs de cette partie {@link #joueurs}
	 */
	public Joueur[] getJoueurs() {
		return joueurs;
	}

	
	/**
	 * @return	L'indice du joueur courant {@link #aQuiLeTour}
	 */
	public int getaQuiLeTour() {
		return aQuiLeTour;
	}

	
	/**
	 * @return Le nombre de fautes {@link #jetonEclair}
	 */
	public int getJetonEclair() {
		return jetonEclair;
	}

	
	/**
	 * @return Le nombre d'indices disponibles {@link #jetonIndice}
	 */
	public int getJetonIndice() {
		return jetonIndice;
	}
}