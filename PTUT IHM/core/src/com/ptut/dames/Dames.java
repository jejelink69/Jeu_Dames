package com.ptut.dames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.ptut.dames.screens.FirstPage;
import com.ptut.dames.screens.GameScreen;
import com.ptut.dames.screens.JouerIA;
import com.ptut.dames.screens.JouerJoueur;
import com.ptut.dames.screens.Parametres;
import com.ptut.dames.screens.SelectGame;

public class Dames extends Game {

    public static Game game;

    /*
     UWIDTH = variable correspondant au nombre d'unité de jeu pour la taille
    de l'écran, a noter que chaque pièce vaudra 1 unité de longueur et 1 de
    largeur
     */
    public static final int UWIDTH = 10;
    public GameScreen gamescreen;
    public FirstPage firstpage;
    public SelectGame selectgame;
    public JouerIA jouerIA;
    public JouerJoueur jouerjoueur;
    public Parametres parametres;
    static boolean jouercontrejoueur = false;
    static boolean jouercontreIA = false;
    
    
    

    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(true);
        game = this;
        gamescreen = new GameScreen(this);
        firstpage = new FirstPage(this);
        selectgame = new SelectGame(this);
        parametres = new Parametres(this);

        jouerIA = new JouerIA(this);
        jouerjoueur = new JouerJoueur(this);

        this.setScreen(firstpage);
    }
    
    public static boolean getJouercontrejoueur() {
        return jouercontrejoueur;
    }

    public static boolean getJouercontreIA() {
        return jouercontreIA;
    }

    public static void setJouercontrejoueur(boolean jouercontrejoueur) {
        Dames.jouercontrejoueur = jouercontrejoueur;
    }

    public static void setJouercontreIA(boolean jouercontreIA) {
        Dames.jouercontreIA = jouercontreIA;
    }
}
