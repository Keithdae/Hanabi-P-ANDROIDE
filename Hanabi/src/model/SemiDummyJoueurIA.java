package model;

/**
 * 	SEMIDUMMY IA
 *  <p>
 * 	Mode de fonctionnement :
 * 	<ul>
 * 		<li>Si une carte est connu a 100% et qu'elle est jouable alors jouer la carte
 * 		<li>Sinon donner un indice à un joueur aleatoire s'il y a des jetons restants
 *  	<li>Sinon defausser :
 *  </ul>
 *  	<ol>
 *  		<li>s'il sait qu'il peut defausser une carte (carte deja posee et connu)
 *  		<li>sinon cartes sans indices
 *  		<li>sinon aleatoire
 *  	</ol>
 */
public class SemiDummyJoueurIA extends JoueurIA {

	/**
	 * Constructeur pour joueur SemiDummy IA
	 * @param nom		Le nom associe au joueur
	 * @param nbCartes	Le nombre de cartes que doit contenir sa main (definit par le nombre de joueurs dans la partie)
	 * @param p			La partie a laquelle participe cette IA
	 * @param id		Le numero du joueur dans la partie
	 */
    public SemiDummyJoueurIA(String nom, int nbCartes, Partie p, int id) {
        super(nom, nbCartes, p, id);
    }
    
    /**
     * Fonction qui decide et joue un coup dans la partie
     */
    public void jouerCoup() {
        Carte c = this.coupTrivial();
        Carte defaussable = this.defausseTriviale();

        if (c != null) {
            // ************************* JOUER LA CARTE *******************************
            int j = this.getMain().getIndex(c);

            try {
                p.joueCarte(this, j);
            } catch (EnleverCarteInexistanteException e) {
                e.printStackTrace();
            } catch (PartiePerdueException e) {
                e.printStackTrace();
            } catch (AdditionMainPleineException e) {
                e.printStackTrace();
            } catch (PiocheVideException e) {
                e.printStackTrace();
            }

            // ************************ DONNER INDICE **********************************
        } else if (p.jetonIndice != 0) {

            int joueur;
            do {
                joueur = r.nextInt(p.getJoueurs().length);
            } while (joueur==this.getId());

            int number = r.nextInt(5);

            Carte carteJouableIndiquable= this.chercheCarteJouableIndiquable(p.getJoueurs()[joueur]);
            if(carteJouableIndiquable!=null)
            {
            	if(!(carteJouableIndiquable.isValeurConnue())){
            		try {
                    	System.out.println("Voici tes cartes de valeur "+Integer.toString(carteJouableIndiquable.getValeur()));
                        p.indiceValeur(p.getJoueurs()[joueur], carteJouableIndiquable.getValeur());
                    } catch (IndiceSoitMemeException e) {
                        e.printStackTrace();
                    }
            	}
            	else{
            		try {
                    	System.out.println("Voici tes cartes "+carteJouableIndiquable.getCouleur().toString()+"s");
                        p.indiceCouleur(p.getJoueurs()[joueur], carteJouableIndiquable.getCouleur());
                    } catch (IndiceSoitMemeException e) {
                        e.printStackTrace();
                    }
            	}
            }
            else{            	
	            int cORi = r.nextInt(2);
	            if (cORi==0) {
	                if (number == 0) {
	                    try {
	                    	System.out.println("Voici tes cartes blanches");
	                        p.indiceCouleur(p.getJoueurs()[joueur], Couleur.CardColor.BLANC);
	                    } catch (IndiceSoitMemeException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (number == 1) {
	                    try {
	                    	System.out.println("Voici tes cartes rouges");
	                        p.indiceCouleur(p.getJoueurs()[joueur], Couleur.CardColor.ROUGE);
	                    } catch (IndiceSoitMemeException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (number == 2) {
	                    try {
	                    	System.out.println("Voici tes cartes vertes");
	                        p.indiceCouleur(p.getJoueurs()[joueur], Couleur.CardColor.VERT);
	                    } catch (IndiceSoitMemeException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (number == 3) {
	                    try {
	                    	System.out.println("Voici tes cartes bleues");
	                        p.indiceCouleur(p.getJoueurs()[joueur], Couleur.CardColor.BLEU);
	                    } catch (IndiceSoitMemeException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (number == 4) {
	                    try {
	                    	System.out.println("Voici tes cartes jaunes");
	                        p.indiceCouleur(p.getJoueurs()[joueur], Couleur.CardColor.JAUNE);
	                    } catch (IndiceSoitMemeException e) {
	                        e.printStackTrace();
	                    }
	                }
	
	            }
	            else {
	                try {
	                	System.out.println("Voici tes cartes de valeur "+Integer.toString(number+1));
	                    p.indiceValeur(p.getJoueurs()[joueur], number+1);
	                } catch (IndiceSoitMemeException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
            // ************************ DEFAUSSER **********************************
        } else {

            // ************************ DEFAUSSER CARTE CONNUE **********************************
            if (defaussable!=null) {

                int j=this.getMain().getIndex(defaussable);

                try {
                    p.defausse(this, j);
                } catch (EnleverCarteInexistanteException e) {
                    e.printStackTrace();
                } catch (AdditionMainPleineException e) {
                    e.printStackTrace();
                } catch (PiocheVideException e) {
                    e.printStackTrace();
                }
            }

            // ************************ DEFAUSSER CARTE SANS INDICE **********************************
            else {
            	boolean discarded = false;
            	int i = 0;
            	while(i < this.main.nbCartes && !discarded){
            		Carte card = null;
					try {
						card = this.main.getCarte(i);
					} catch (EnleverCarteInexistanteException e1) {
						e1.printStackTrace();
					}
            		if (!card.isCouleurConnue() && !card.isValeurConnue()) {
                        try {
                            p.defausse(this, i);
                            discarded = true;
                        } catch (EnleverCarteInexistanteException e) {
                            e.printStackTrace();
                        } catch (AdditionMainPleineException e) {
                            e.printStackTrace();
                        } catch (PiocheVideException e) {
                            e.printStackTrace();
                        }
                    }
            		i++;
            	}
            	
            	// ************************ DEFAUSSER CARTE ALEATOIREMENT **********************************
            	if(!discarded){
            		try {
                        try {
                            p.defausse(this, r.nextInt(this.getMain().nbCartes));
                        } catch (AdditionMainPleineException e) {
                            e.printStackTrace();
                        } catch (PiocheVideException e) {
                            e.printStackTrace();
                        }
                        } catch (EnleverCarteInexistanteException e) {
                        e.printStackTrace();
                    }
            	} // Defausse aleatoire
            } // Defausse carte sans indice ou aleatoire
        } // Defausse
    } // jouerCoup
    
    /**
     * 
     * @param j le joueur à qui l'on cherche à donner un indice
     * @return la carte sur laquelle donner l'indice
     */
    public Carte chercheCarteJouableIndiquable(Joueur j){
    	Carte CarteAIndiquer=null;
    	for (Carte c : j.getMain().main){
    		for (int i=0; i<this.p.cartesJouees.size(); i++) {
    			if (this.p.cartesJouees.get(c.getCouleur()).size()==(c.getValeur()-1)){
    				if(!((c.isValeurConnue())&&(j.getMain().valeurUnique(c.getValeur()))
    					||c.isCouleurConnue()))
    					CarteAIndiquer = c;
    				else if((!(c.isCouleurConnue())&&(j.getMain().couleurUnique(c.getCouleur()))
    					||c.isValeurConnue()))
    					CarteAIndiquer = c;
                }
            }
    		//Si toutes les cartes jouees ont la meme valeur(ou qu'il n'y a pas de cartes) et le joueur possede une carte de valeur +1
    		if ((this.p.cartesJouees.get(Couleur.CardColor.BLANC).size()==(c.getValeur()-1))
    			&&(this.p.cartesJouees.get(Couleur.CardColor.ROUGE).size()==(c.getValeur()-1))
    			&&(this.p.cartesJouees.get(Couleur.CardColor.VERT).size()==(c.getValeur()-1))
    			&&(this.p.cartesJouees.get(Couleur.CardColor.BLEU).size()==(c.getValeur()-1))
    			&&(this.p.cartesJouees.get(Couleur.CardColor.JAUNE).size()==(c.getValeur()-1)))
    		{
    			if(!(c.isValeurConnue()))
					CarteAIndiquer = c;
    		}
        }
        return CarteAIndiquer;
    }
}