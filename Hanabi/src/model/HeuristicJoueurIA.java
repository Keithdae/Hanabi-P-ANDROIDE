package model;

public class HeuristicJoueurIA extends JoueurIA{
	private static final long serialVersionUID = 5998778971436218754L;
	public static final String nom = "Marge";
	
	private ParamHeuristic param;
	
	public HeuristicJoueurIA(Partie p, int id,ParamHeuristic param) 
	{
        super(nom, p, id);
        this.param=param;
    }
	public PartieView createView(Partie p)
	{
		PartieView pv=new PartieView(p,this.param);
		return pv;
	}
	public void jouerCoup()
	{
		int i=0;
		double h_max=0;
		double h_jouer[]=new double[this.getMain().main.size()];
		double h_defausser[]=new double[this.getMain().main.size()];
		double h_indice_couleur[]=null;
		if(this.p.isMulticolor())
		{
			h_indice_couleur=new double[(this.p.nbJoueurs)*6];
		}
		else
		{
			h_indice_couleur=new double[(this.p.nbJoueurs)*5];
		}
		double h_indice_valeur[]=new double[(this.p.nbJoueurs)*5];
		/*********Tests jouer************/
		i=0;
		for(Carte c : this.getMain().main)
		{
			h_jouer[i]=testJouerCarte(c);
			i++;
		}
		
		/*********Tests defausser************/
		i=0;
		for(Carte c : this.getMain().main)
		{
			h_defausser[i]=testDefausserCarte(c);
			i++;
		}
		
		/*********Tests indices************/
		if(p.getJetonIndice()>0)
		{

			for(Joueur j : p.joueurs)
	    	{
	    		if (j.getId()!=this.id)
	    		{
	    			h_indice_valeur[j.getId()*5]=testIndice(j, null, 1);
	    			h_indice_valeur[j.getId()*5+1]=testIndice(j, null, 2);
	    			h_indice_valeur[j.getId()*5+2]=testIndice(j, null, 3);
	    			h_indice_valeur[j.getId()*5+3]=testIndice(j, null, 4);
	    			h_indice_valeur[j.getId()*5+4]=testIndice(j, null, 5);
	    			
	    			if(this.p.isMulticolor())
	    			{
	    				h_indice_couleur[j.getId()*6]=testIndice(j, Couleur.CardColor.BLANC, 0);
		    			h_indice_couleur[j.getId()*6+1]=testIndice(j, Couleur.CardColor.BLEU, 0);
		    			h_indice_couleur[j.getId()*6+2]=testIndice(j, Couleur.CardColor.VERT, 0);
		    			h_indice_couleur[j.getId()*6+3]=testIndice(j, Couleur.CardColor.JAUNE, 0);
		    			h_indice_couleur[j.getId()*6+4]=testIndice(j, Couleur.CardColor.ROUGE, 0);
	    				h_indice_couleur[j.getId()*6+5]=testIndice(j, Couleur.CardColor.MULTI, 0);
	    			}
	    			else
	    			{
	    				h_indice_couleur[j.getId()*5]=testIndice(j, Couleur.CardColor.BLANC, 0);
		    			h_indice_couleur[j.getId()*5+1]=testIndice(j, Couleur.CardColor.BLEU, 0);
		    			h_indice_couleur[j.getId()*5+2]=testIndice(j, Couleur.CardColor.VERT, 0);
		    			h_indice_couleur[j.getId()*5+3]=testIndice(j, Couleur.CardColor.JAUNE, 0);
		    			h_indice_couleur[j.getId()*5+4]=testIndice(j, Couleur.CardColor.ROUGE, 0);
	    			}
	    		}
	    	}	
    	}
		/*
		System.out.println("Fin tests h du joueur "+this.id);
		for(i=0;i<5;i++)
			System.out.println("h_jouer "+i+" = "+h_defausser[i]);
		for(i=0;i<5;i++)
			System.out.println("h_jouer "+i+" = "+h_jouer[i]);
		for(i=0;i<15;i++)
			System.out.println("h_indive valeur "+i+" = "+h_indice_valeur[i]);
		for(i=0;i<18;i++)
			System.out.println("h_indive couleur "+i+" = "+h_indice_couleur[i]);
		System.out.println("Fin tests h du joueur "+this.id);
		 */
		
		/*********Recherche h max**********/
		
		for(i=0; i<this.getMain().main.size();i++)
		{
			if(h_jouer[i]>h_max)
				h_max=h_jouer[i];
			if(h_defausser[i]>h_max)
				h_max=h_defausser[i];
		}
		for(Joueur j : p.joueurs)
    	{
			for(i=0; i<5;i++)
			{
				if(h_indice_valeur[j.getId()*5+i]>h_max)
					h_max=h_indice_valeur[j.getId()*5+i];
			}
			if(this.p.isMulticolor())
			{
				for(i=0; i<6;i++)
				{
					if(h_indice_couleur[j.getId()*6+i]>h_max)
						h_max=h_indice_couleur[j.getId()*6+i];
				}
			}
			else
			{
				for(i=0; i<5;i++)
				{
					if(h_indice_couleur[j.getId()*5+i]>h_max)
						h_max=h_indice_couleur[j.getId()*5+i];
				}
			}
    	}
		//selection coup
		boolean a_joue=false;
		if(p.getJetonIndice()>0)
		{
			for(Joueur j : p.joueurs)
	    	{
				if (j.getId()!=this.id)
	    		{
					if(!(this.p.isMulticolor()))
					{
						for(i=0; i<5;i++)
						{
							if(a_joue==false)
							{
								if(h_indice_valeur[j.getId()*5+i]==h_max)
								{
									try {
										p.indiceValeur(j, i+1);
									} catch (IndiceSoitMemeException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									a_joue=true;
								}
							}
							if(a_joue==false)
							{
								if(h_indice_couleur[j.getId()*5+i]==h_max)
								{
									Couleur.CardColor coul=null;
									if(i==0)
										coul=Couleur.CardColor.BLANC;
									else if(i==1)
										coul=Couleur.CardColor.BLEU;
									else if(i==2)
										coul=Couleur.CardColor.VERT;
									else if(i==3)
										coul=Couleur.CardColor.JAUNE;
									else if(i==4)
										coul=Couleur.CardColor.ROUGE;
									try {
										p.indiceCouleur(j, coul);
									} catch (IndiceSoitMemeException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									a_joue=true;
								}
							}
						}
					}
					else
					{
						for(i=0; i<5;i++)
						{
							if(a_joue==false)
							{
								if(h_indice_valeur[j.getId()*5+i]==h_max)
								{
									try {
										p.indiceValeur(j, i+1);
									} catch (IndiceSoitMemeException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									a_joue=true;
								}
							}
						}
						for(i=0; i<6;i++)
						{
							if(a_joue==false)
							{
								if(h_indice_couleur[j.getId()*6+i]==h_max)
								{
									Couleur.CardColor coul=null;
									if(i==0)
										coul=Couleur.CardColor.BLANC;
									else if(i==1)
										coul=Couleur.CardColor.BLEU;
									else if(i==2)
										coul=Couleur.CardColor.VERT;
									else if(i==3)
										coul=Couleur.CardColor.JAUNE;
									else if(i==4)
										coul=Couleur.CardColor.ROUGE;
									else if(i==5)
										coul=Couleur.CardColor.MULTI;
									try {
										p.indiceCouleur(j, coul);
									} catch (IndiceSoitMemeException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									a_joue=true;
								}
							}
						}
					}
				}
    		}
    	}
		for(i=0; i<this.getMain().main.size();i++)
		{
			if(a_joue==false)
			{
				if(h_jouer[i]==h_max)
				{
					try {
						p.joueCarte(this, i);
					} catch (EnleverCarteInexistanteException | PartiePerdueException | AdditionMainPleineException
							| PiocheVideException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					a_joue=true;
				}
			}
			if(a_joue==false)
			{
				if(h_defausser[i]==h_max)
				{
					try {
						p.defausse(this, i);
					} catch (EnleverCarteInexistanteException | AdditionMainPleineException
							| PiocheVideException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					a_joue=true;
				}
			}
		}
	}
	public double testJouerCarte(Carte c1)
	{
		double h=0;
		PartieView pw=createView(this.p);
		pw.majJoueur(this);
		pw.decr_nb_jouables();//la carte que l'on va jouer
		if(this.isJouableTrivial(c1))
		{
			pw.incr_nb_points();
			for(Carte c2 : this.getMain().main)
			{
				if((c2.isValeurConnue())&&(c2.isCouleurConnue()))
				{
					if(c2!=c1)
					{
						if(c2.getCouleur()==c1.getCouleur())
						{
							if(c2.getValeur()==c1.getValeur())
							{
								pw.incr_nb_defaussables();
								pw.incr_info_defaussables();
								pw.incr_info_defaussables();
								pw.decr_nb_jouables();
								pw.decr_info_jouables();
								pw.decr_info_jouables();
							}
							else if(c2.getValeur()==(c1.getValeur()+1))
							{
								pw.incr_nb_jouables();
								pw.incr_info_jouables();
								pw.incr_info_jouables();
								pw.decr_info_neutres();
								pw.decr_info_neutres();
							}
						}
					}
				}
			}
			for(Joueur j : p.joueurs)
	    	{
	    		if (j.getId()!=this.id)
	    		{
	    			for(Carte c3 : j.getMain().main)
	    			{
	    				if(c3.getCouleur()==c1.getCouleur())
						{
							if(c3.getValeur()==c1.getValeur())
							{
								pw.incr_nb_defaussables();
								pw.decr_nb_jouables();
								if(c3.isCouleurConnue())
								{
									pw.incr_info_defaussables();
									pw.decr_info_jouables();
								}
								if(c3.isValeurConnue())
								{
									pw.incr_info_defaussables();
									pw.decr_info_jouables();
								}	
							}
							else if(c3.getValeur()==(c1.getValeur()+1))
							{
								pw.incr_nb_jouables();
								if(c3.isCouleurConnue())
								{
									pw.incr_info_jouables();
									pw.decr_info_neutres();
								}
								if(c3.isValeurConnue())
								{
									pw.incr_info_jouables();
									pw.decr_info_neutres();
								}
							}
						}
	    			}
	    		}
	    		h=pw.valeur_heuristique();
	    	}
		}
		else{
			h=-10000;
		}
		return h;
	}
	public double testDefausserCarte(Carte c1)
	{
		PartieView pw=this.createView(this.p);
		pw.majJoueur(this);
		pw.incr_nb_indices();//puisque l'on defausse une carte
		boolean bloquage=false;
		int nb_meme_carte_defausse=0;
		for(Carte c3 : this.p.getDefausse())
		{
			if((c3.getCouleur()==c1.getCouleur())&&(c3.getValeur()==c1.getValeur()))
				nb_meme_carte_defausse++;
		}
		if(c1.getValeur()==1)
		{
			if(nb_meme_carte_defausse==2)
				bloquage=true;
		}
		else if(c1.getValeur()!=5)
		{
			if(nb_meme_carte_defausse==1)
				bloquage=true;
		}
		
		//Donnees directes
		if(this.isJouableTrivial(c1))
		{
			pw.decr_nb_jouables();
			if(c1.isCouleurConnue())
			{
				pw.decr_info_jouables();
			}
			if(c1.isValeurConnue())
			{
				pw.decr_info_jouables();
			}
		}
		else if(this.isDefaussableTrivial(c1))
		{
			if(c1.isCouleurConnue())
			{
				pw.decr_info_defaussables();
			}
			if(c1.isValeurConnue())
			{
				pw.decr_info_defaussables();
			}
		}
		else
		{
			if(c1.isCouleurConnue())
			{
				pw.decr_info_neutres();
			}
			if(c1.isValeurConnue())
			{
				pw.decr_info_neutres();
			}
		}
		//Parcours des mains et donnees liees
		for(Carte c2 : this.getMain().main)
		{
			if((c2.isValeurConnue())&&(c2.isCouleurConnue()))
			{
				if(c2!=c1)
				{
					if(c2.getCouleur()==c1.getCouleur())
					{
						if(c2.getValeur()>(c1.getValeur()))
						{
							if(bloquage)
							{
								pw.incr_nb_defaussables();
								pw.incr_info_defaussables();
								pw.incr_info_defaussables();
								pw.decr_info_neutres();
								pw.decr_info_neutres();
							}
						}
					}
				}
			}
		}
		for(Joueur j : p.joueurs)
    	{
    		if (j.getId()!=this.id)
    		{
    			for(Carte c3 : j.getMain().main)
    			{
    				if(c3.getCouleur()==c1.getCouleur())
					{
    					if(c3.getValeur()>(c1.getValeur()))
						{
							if(bloquage)
							{
								pw.incr_nb_defaussables();
								if(c3.isCouleurConnue())
								{
									pw.incr_info_defaussables();
									pw.decr_info_neutres();
								}
								if(c3.isValeurConnue())
								{
									pw.incr_info_defaussables();
									pw.decr_info_neutres();
								}
							}
						}
					}
    			}
    		}
    	}
		return pw.valeur_heuristique();
	}
	public double testIndice(Joueur j, Couleur.CardColor couleur, int valeur)//laisser un des 2 à null/0
	{
		PartieView pw=this.createView(this.p);
		pw.majJoueur(this);
		pw.decr_nb_indices();
		if(couleur != null)
		{
			for(Carte c : j.getMain().main)
			{
				if((!c.isCouleurConnue())&&(c.getCouleur()==couleur))
				{
					if(c.isJouableOmniscient(p))
					{
						pw.incr_info_jouables();
					}
					else if(c.isDefaussableOmniscient(p))
					{
						pw.incr_info_defaussables();
					}
					else
					{
						pw.incr_info_neutres();
					}	
				}
			}
		}
		
		else if(valeur != 0)
		{
			for(Carte c : j.getMain().main)
			{
				if((!c.isValeurConnue())&&(c.getValeur()==valeur))
				{
					if(c.isJouableOmniscient(p))
					{
						pw.incr_info_jouables();
					}
					else if(c.isDefaussableOmniscient(p))
					{
						pw.incr_info_defaussables();
					}
					else
					{
						pw.incr_info_neutres();
					}	
				}
			}
		}
		return pw.valeur_heuristique();
	}
}
