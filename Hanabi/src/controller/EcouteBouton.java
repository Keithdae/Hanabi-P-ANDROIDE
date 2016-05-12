package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.AdditionMainPleineException;
import model.DummyJoueurIA;
import model.EpistemicJoueurIA;
import model.PiocheVideException;
import model.SemiDummyJoueurIA;
import model.HeuristicJoueurIA;
import model.Joueur;
import model.JoueurHumain;
import model.ParamHeuristic;
import model.Partie;
import view.FenetrePartie;
import view.Parametres;

public class EcouteBouton implements ActionListener {
	Parametres p;
	
	public EcouteBouton(Parametres p){
		this.p = p;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==p.ok){
			if(p.pseudo.getText().isEmpty() || p.pseudo.getText().equals("Entrez un ID!")){
				p.pseudo.setForeground(Color.RED);
				p.pseudo.setText("Entrez un ID!");
			}
			else{
				Partie game = new Partie((Integer)p.nbJoueurs.getSelectedItem(),8,p.multiColor.isSelected());
				Joueur[] joue = new Joueur[(Integer)p.nbJoueurs.getSelectedItem()];
			    joue[0] = new JoueurHumain(p.pseudo.getText(), game, 0);
			    if(p.joueurIA1.getSelectedItem().equals(DummyJoueurIA.class.getName())){
			    	joue[1] = new DummyJoueurIA("Sherlock", game, 1);
			    }
			    else if(p.joueurIA1.getSelectedItem().equals(SemiDummyJoueurIA.class.getName())){
			    	joue[1] = new SemiDummyJoueurIA("Sherlock", game, 1);
			    }
			    else if(p.joueurIA1.getSelectedItem().equals(HeuristicJoueurIA.class.getName())){
			    	ParamHeuristic param=new ParamHeuristic();
			    	joue[1] = new HeuristicJoueurIA("Sherlock", game, 1,param);
			    }
			    else{
			    	joue[1] = new EpistemicJoueurIA("Sherlock", game, 1,0.62);
			    }
			    if((Integer)p.nbJoueurs.getSelectedItem()>2){
				    if(p.joueurIA2.getSelectedItem().equals(DummyJoueurIA.class.getName())){
				    	joue[2] = new DummyJoueurIA("Watson", game, 2);
				    }
				    else if(p.joueurIA2.getSelectedItem().equals(SemiDummyJoueurIA.class.getName())){
				    	joue[2] = new SemiDummyJoueurIA("Watson", game, 2);
				    }
				    else if(p.joueurIA2.getSelectedItem().equals(HeuristicJoueurIA.class.getName())){
				    	ParamHeuristic param=new ParamHeuristic();
				    	joue[2] = new HeuristicJoueurIA("Watson", game, 2,param);
				    }
				    else{
				    	joue[2] = new EpistemicJoueurIA("Watson", game, 2,0.62);
				    }
			    }
			    if((Integer)p.nbJoueurs.getSelectedItem()>3){
				    if(p.joueurIA3.getSelectedItem().equals(DummyJoueurIA.class.getName())){
				    	joue[3] = new DummyJoueurIA("Moriarty", game, 3);
				    }
				    else if(p.joueurIA3.getSelectedItem().equals(SemiDummyJoueurIA.class.getName())){
				    	joue[3] = new SemiDummyJoueurIA("Moriarty", game, 3);
				    }
				    else if(p.joueurIA3.getSelectedItem().equals(HeuristicJoueurIA.class.getName())){
				    	ParamHeuristic param=new ParamHeuristic();
				    	joue[3] = new HeuristicJoueurIA("Moriarty", game, 3,param);
				    }
				    else{
				    	joue[3] = new EpistemicJoueurIA("Moriarty", game, 3,0.62);
				    }
			    }
			    if((Integer)p.nbJoueurs.getSelectedItem()>4){
				    if(p.joueurIA4.getSelectedItem().equals(DummyJoueurIA.class.getName())){
				    	joue[4] = new DummyJoueurIA("Lestrade", game, 4);
				    }
				    else if(p.joueurIA4.getSelectedItem().equals(SemiDummyJoueurIA.class.getName())){
				    	joue[4] = new SemiDummyJoueurIA("Lestrade", game, 4);
				    }
				    else if(p.joueurIA4.getSelectedItem().equals(HeuristicJoueurIA.class.getName())){
				    	ParamHeuristic param=new ParamHeuristic();
				    	joue[4] = new HeuristicJoueurIA("Lestrade", game, 4,param);
				    }
				    else{
				    	joue[4] = new EpistemicJoueurIA("Lestrade", game, 4,0.62);
				    }
			    }
			    try {
					game.initPartie(joue);
				} catch (AdditionMainPleineException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (PiocheVideException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			    if(this.p.getFen() == null){
			    	new FenetrePartie(game);
			    }
			    else{
			    	this.p.getFen().setPartie(game);
			    	this.p.getFen().setVisible(true);
			    }			    
			    p.dispose();
			}
		}
	}

}
