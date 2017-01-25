package com.ptut.dames.controller;

/**
 *
 * @author Jérémy Montrobert
 */
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.ptut.dames.model.Coup;
import com.ptut.dames.model.Plateau;
import com.ptut.dames.model.Piece;
import com.ptut.dames.model.Pion;
import static com.ptut.dames.model.Plateau.pieces;
import static com.ptut.dames.model.Plateau.tiles;
import com.ptut.dames.model.Tile;

import com.ptut.dames.views.GameRenderer;
import java.util.List;

public class ControlPlateau extends ActorGestureListener implements GestureListener {

    private float Cx = 0;
    private float Cy = 0;
    private final Plateau plateauC;
    private final Array<Tile> tileEnSurbrillance = new Array<Tile>();
    private GameRenderer view = null;

    public ControlPlateau(Plateau plateauC) {

        this.plateauC = plateauC;
    }

    private void deplacerPiece(Piece piece, int x, int y) {
        System.out.println("piece move1");
        int xOld = (int) piece.getX();
        int yOld = (int) piece.getY();
        System.out.println("piece move2");

        this.enleverSurbrillanceDeplacement();

        if (this.plateauC.getPiecePos(x, y) != null) {
            this.plateauC.enleverPiece(x, y);
        }

        this.plateauC.repositionnerPiece(xOld, yOld, x, y);
        System.out.println("piece move3");
        this.plateauC.pieceSelect.moved();
        System.out.println("piece move4");

        this.plateauC.pieceSelect = null;
        System.out.println("piece move5");
        this.plateauC.tour++;
        System.out.println("piece move6");

    }

    private void selectPiece(Piece piece) {
        this.enleverSurbrillanceDeplacement();
        this.plateauC.pieceSelect = piece;
        this.ajouterSurbrillanceDeplacement(4,4);
        System.out.println("piece select");
    }

    private void ajouterSurbrillanceDeplacement(int x, int y) {
        Tile tile = tiles[x][y];
        tile.estEnSurbrillance = true;
        System.out.println("tile en surbri: "+x+" "+y);
    }

    public void SurbrillanceCoupPossible(List<Coup> cps) {

        for (Coup c : cps) {
            int x;
            int y;
            x = c.getPion().getPosX();
            y = c.getPion().getPosY();
            ajouterSurbrillanceDeplacement(x, y);
            System.out.println(x + " " + y);
        }
    }

    public void SurbrillanceDestcoupPossible(Pion p, List<Coup> cps) {

        for (Coup c : cps) {
            if (c.getPion().equals(p)) {
                int x;
                int y;

                plateauC.jouerCoup(c);
                x = c.getPion().getPosX();
                y = c.getPion().getPosY();
                ajouterSurbrillanceDeplacement(y, x);
                System.out.println(x+" "+y);
                plateauC.dejouerCoup(c);
            }
        }

    }
    
    
    

    private void enleverSurbrillanceDeplacement() {

        while (this.tileEnSurbrillance.size > 0) {
            this.tileEnSurbrillance.pop().estEnSurbrillance = false;
        }
    }

    public void addView(GameRenderer view) {
        this.view = view;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {

        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {

        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public boolean touchDown(float Cx, float Cy, int pointer, int button) {
        this.Cx = Cx;
        this.Cy = Cy;
        int i = 0;
        int x = 0;
        int y = 0;
        int Ox = 150;
        int Oy = 599;

        while (i <= 9) {
            if (Cx > Ox + i * 59) {
                x = i;
            }
            if (Cy < Oy - i * 59) {
                y = i;
            }
            i++;
        }
        if (Cx < Ox) {              //si on dépasse le damier a gauche
            x = -1;
            y = -1;
        }
        if (Cx > Ox + 10 * 59) {    //si on dépasse le damier à droite
            x = -1;
            y = -1;
        }
        Cx = x;
        Cy = y;
        Cx = (int) Cx;
        Cy = (int) Cy;
        //System.out.println(Cx + "   test 2   " + Cy);

        // Actor ciblepiece = pieces[(int)Cx][(int)Cy];
        try {
            Actor cibletile = tiles[(int) Cx][(int) Cy];

            //int px = (int) ciblepiece.getX(); //pos x piece cliquée 
            //int py = (int) ciblepiece.getY(); // pos y piece cliquée   
            int tx = (int) cibletile.getX(); // pos x tile cliquée
            int ty = (int) cibletile.getY(); // pos y tile cliquée

            System.out.println(tx + "    " + ty);

            if (pieces[tx][ty] != null) {
                System.out.println("il y a une piece ici");
                Piece piece = pieces[tx][ty]; // on transforme l'acteur en Pièce
                this.selectPiece(piece);
                this.ajouterSurbrillanceDeplacement(4,4);
                System.out.println(this.plateauC.pieceSelect);
            } else if (this.plateauC.pieceSelect != null) {
              
                System.out.println(this.plateauC.pieceSelect);
                this.deplacerPiece(this.plateauC.pieceSelect, (int) Cx, (int) Cy);
               
            }
        } catch (Exception e) {
            System.out.println("Hors Damier !!!");
        }
        //System.out.println(px + "   test 4   " + py);
        //si la cible est une Piece
        /* if (cible.getClass().getSuperclass().equals(Piece.class)) { 
            System.out.println("piece");
            Piece piece = (Piece) cible; // on transforme l'acteur en Pièce

            if ((((this.plateauC.tour % 2) == 0) && piece.estBlanc)
                    || (((this.plateauC.tour % 2) == 1) && !piece.estBlanc)) {
                this.selectPiece(piece);
            } else {
                this.deplacerPiece(this.plateauC.pieceSelect, tx, ty);
            }
        } else {
            this.deplacerPiece(this.plateauC.pieceSelect, tx, ty);
        }*/
        return true;
    }

    /* public float ConvertCoord() {
        int i = 0;
        int x = 0;                  //la coordonée entière X de la case
        int y = 0;                  //la coordonée entière Y de la case
        int Ox = 150;               //origine X en pixel
        int Oy = 599;               //origine Y en pixel
        while (i <= 9) {
            if (Cx > Ox + i * 59) {
                x = i;
            }
            if (Cy < Oy - i * 59) {
                y = i;
            }
            i++;
        }
        if (Cx < Ox) {              //si on dépasse le damier a gauche
            x = -1;
            y = -1;
        }
        if (Cx > Ox + 10 * 59) {    //si on dépasse le damier à droite
            x = -1;
            y = -1;
        }

        System.out.println(x + "    " + y);
        
        return (int)Cx + (int)Cy;

    }*/

 /*public void pieceSelectionnee(){
        Actor cible = pieces[(int)Cx][(int)Cy];
        int tx =(int) cible.getX();
        int ty =(int) cible.getY();
        System.out.println("tx= "+tx+"  ty= "+ty);
    }*/
 /*Actor cible = event.getTarget(); // recupere la cible qui à recu l'evenement: celle cliquée
        int tx = (int) cible.getX(); // pos x tile cliquée
        int ty = (int) cible.getY(); // pos y tile cliquée
        System.out.println("touch");
        System.out.println(tx + " " + ty);
        
        //si la cible est une Piece
        if (cible.getClass().getSuperclass().equals(Piece.class)) { 
            System.out.println("piece");
            Piece piece = (Piece) cible; // on transforme l'acteur en Pièce

            if ((((this.plateauC.tour % 2) == 0) && piece.estBlanc)
                    || (((this.plateauC.tour % 2) == 1) && !piece.estBlanc)) {
                this.selectPiece(piece);
            } else {
                this.deplacerPiece(this.plateauC.pieceSelect, tx, ty);
            }
        } else {
            this.deplacerPiece(this.plateauC.pieceSelect, tx, ty);
        }
        return true;

    }*/
    @Override
    public boolean longPress(float x, float y
    ) {
        return false;

    }

    /* -detecter case cliquée, récupérer coordonées
       -verifier si il y a une pièce sur la case
       -select
       -nouveau clic, recupérer coord
       -déplacer sur case cliquée
     */
    public void ConvertCoord(float x, float y) {

    }

}
