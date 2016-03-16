package view;

import model.Carte;
import model.Couleur;
import model.EnleverCarteInexistanteException;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Table extends JPanel {
	private static final long serialVersionUID = 1L;

	public void paintTable(Graphics g, FenetrePartie fen)
	{
		int width = fen.getTableWidth(), height = fen.getTableHeight();
		int startX = (fen.getWidth() - width) /2;
		int startY = (fen.getHeight() - height) /2;
		g.drawImage(new ImageIcon("ressources/table.png").getImage(), startX, startY, width, height, this);
	}

	public void afficherCartesJouees (Graphics g, FenetrePartie fen) {

		HashMap<Couleur.CardColor, ArrayList<Carte>> cartesJouees = fen.getPartie().getCartesJouees();
		int karteH = fen.tableHeight/4;
		int karteW =(int)((float)karteH*0.645);
		int startX = fen.getWidth()/2 - (karteW/2); // Middle of the window, but moves half a card to the left
		int startY = (fen.getHeight() - fen.tableHeight) /2 + (karteH*6/11); // Top of the table, but moves down to create a margin

		// Test

		try {
			Image carte = new ImageIcon("ressources/" + fen.getPartie().getJoueurs()[1].getMain().getCarte(1).getCardName()).getImage();

			for (int i=0; i<5; i++) {
				g.drawImage(carte, startX, startY+(karteH)/6*i, karteW, karteH, fen);
				g.drawImage(carte, startX - karteW, startY+(karteH)/6*i, karteW, karteH, fen);
				g.drawImage(carte, startX - karteW * 2, startY+(karteH)/6*i, karteW, karteH, fen);
				g.drawImage(carte, startX + karteW, startY+(karteH)/6*i, karteW, karteH, fen);
				g.drawImage(carte, startX + karteW * 2, startY+(karteH)/6*i, karteW, karteH, fen);

			}
		} catch (EnleverCarteInexistanteException e) {
			e.printStackTrace();
		}

		// Avec 5 colonnes
		if (cartesJouees.get(Couleur.CardColor.MULTI)==null) {
			int i = 0;
			for (Carte c : cartesJouees.get(Couleur.CardColor.BLANC)) {
				Image carte = new ImageIcon("ressources/" + c.getCardName()).getImage();
				g.drawImage(carte, startX, startY+i, karteW, karteH, fen);
				i+=karteH/3;
			}
			i = 0;
			for (Carte c : cartesJouees.get(Couleur.CardColor.BLEU)) {
				Image carte = new ImageIcon("ressources/" + c.getCardName()).getImage();
				g.drawImage(carte, startX-karteW-2, startY+i, karteW, karteH, fen);
				i+=karteH/3;
			}
			i = 0;
			for (Carte c : cartesJouees.get(Couleur.CardColor.VERT)) {
				Image carte = new ImageIcon("ressources/" + c.getCardName()).getImage();
				g.drawImage(carte, startX-karteW*2-2, startY+i, karteW, karteH, fen);
				i+=karteH/3;
			}
			i = 0;
			for (Carte c : cartesJouees.get(Couleur.CardColor.ROUGE)) {
				Image carte = new ImageIcon("ressources/" + c.getCardName()).getImage();
				g.drawImage(carte, startX+karteW+2, startY+i, karteW, karteH, fen);
				i+=karteH/3;
			}
			i = 0;
			for (Carte c : cartesJouees.get(Couleur.CardColor.JAUNE)) {
				Image carte = new ImageIcon("ressources/" + c.getCardName()).getImage();
				g.drawImage(carte, startX+karteW*2+2, startY+i, karteW, karteH, fen);
				i+=karteH/3;
			}
		}

		// Avec 6 colonnes (cartes multicolores)
		else {

		}

	}
}
