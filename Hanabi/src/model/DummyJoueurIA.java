package model;




/**
 * 	DUMMY IA
 *  <p>
 * 	Mode de fonctionnement :
 * 	<ul>
 * 		<li>Si une carte est connu a 100% et qu'elle est jouable alors jouer la carte
 * 		<li>Sinon donner un indice aleatoire à un joueur aleatoire s'il y a des jetons restants
 *  	<li>Sinon defausser :
 *  </ul>
 *  	<ol>
 *  		<li>s'il sait qu'il peut defausser une carte (carte deja posee et connu)
 *  		<li>sinon cartes sans indices
 *  		<li>sinon aleatoire
 *  	</ol>
 */
public class DummyJoueurIA extends JoueurIA {
	private static final long serialVersionUID = -6758471606475814137L;
	public static final String nom = "Homer";

	/**
	 * Constructeur pour joueur Dummy IA
	 * @param nom		Le nom associe au joueur
	 * @param nbCartes	Le nombre de cartes que doit contenir sa main (definit par le nombre de joueurs dans la partie)
	 * @param p			La partie a laquelle participe cette IA
	 * @param id		Le numero du joueur dans la partie
	 */
    public DummyJoueurIA(Partie p, int id) {
        super(nom, p, id);
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
    
    
}