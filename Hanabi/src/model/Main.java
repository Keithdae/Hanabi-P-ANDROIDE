package model;

import java.util.ArrayList;

public class Main {
	private ArrayList<Carte> main;
	private final int nbCartes;
	
	public Main(int nbCartes, Carte[] cartes){
		this.nbCartes = nbCartes;
		main = new ArrayList<Carte>(nbCartes);
		for(int i=0; i<nbCartes; i++){
			main.add(cartes[i]);
		}
	}
	
	public Main(int nbCartes){
		this.nbCartes = nbCartes;
		main = new ArrayList<Carte>(nbCartes);
	}
	
	public void ajouterCarte(Carte c) throws AdditionMainPleineException{
		if(main.size() < this.nbCartes){
			main.add(c);
		}else{
			throw new AdditionMainPleineException();
		}
	}
	
	public void enleverCarte(int indice) throws EnleverCarteInexistanteException{
		if(main.size() > indice){
			main.remove(indice);
		}
		else{
			throw new EnleverCarteInexistanteException();
		}
	}
	
	// Type = 1 pour un indice couleur, sinon indice valeur
	public void indice(int[] indices,int type){
		for(int i:indices){
			if(type == 1){
				this.main.get(i).setCouleurConnue(true);
			}else{
				this.main.get(i).setValeurConnue(true);
			}
		}
	}
}
