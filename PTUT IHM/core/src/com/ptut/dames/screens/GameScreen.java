package com.ptut.dames.screens;

/**
 *
 * @author Jérémy Montrobert
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ptut.dames.Assets;
import com.ptut.dames.Dames;
import com.ptut.dames.controller.ControlPlateau;
import com.ptut.dames.model.Plateau;
import com.ptut.dames.views.GameRenderer;

public class GameScreen implements Screen {

    private GameRenderer renderer;
    public static String couleur1, couleur2, nom1, nom2;
    Stage stageBout;
    Skin skinneon;
   
    Label J1, J2;
    public InputMultiplexer multiplexer;
    Dames app;
    
    TextButton menu;
    public GameScreen(Dames app) {

        this.app = app;
        stageBout = new Stage(new ScreenViewport());
        skinneon = new Skin(Gdx.files.internal("neonui/neonui/neon-ui.json" ));
        
        menu = new TextButton("Fin de la partie", skinneon);
        menu.setPosition(765,550);
        stageBout.addActor(menu);
        
        
        multiplexer = new InputMultiplexer();
       
        actionBouton();
   
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.0f, .3f, .4f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        this.renderer.render(delta);
        stageBout.getViewport().apply();
        stageBout.act();
        stageBout.draw();
        

    }

    @Override
    public void resize(int width, int height) {
        
        this.renderer.setSize(width, height);
        stageBout.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
//        multiplexer.addProcessor(stage);
//        Gdx.input.setInputProcessor(multiplexer);
        Plateau plateau;
        ControlPlateau controller;
        multiplexer.addProcessor(stageBout);
        Assets.loadGame();
        Gdx.input.setInputProcessor(multiplexer);
        plateau = new Plateau(app);
        plateau.ajoutPions();
        controller = new ControlPlateau(plateau);

        this.renderer = new GameRenderer(plateau, controller, app, this);
        this.renderer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        
        if(app.getJouercontreIA()){
            J1 = new Label(JouerIA.getnomJ1(), skinneon);
            J1.setPosition(20,20);
            stageBout.addActor(J1);
            J2 = new Label(JouerIA.getnomJ2(), skinneon);
            J2.setPosition(20,550);
            stageBout.addActor(J2);}
        if(app.getJouercontrejoueur()){
            J1 = new Label(JouerJoueur.getnomJ1(), skinneon);
            J1.setPosition(20,20);
            stageBout.addActor(J1);
            J2 = new Label(JouerJoueur.getnomJ2(), skinneon);
            J2.setPosition(20,550);
            stageBout.addActor(J2);
        }
        
         
//        menu.setPosition(850, 550);
//        stage.addActor(menu);

    }

    @Override
    public void hide() {
        this.renderer.dispose();
        Assets.disposeGame();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
       
       stageBout.dispose(); 

    }
    
    private void actionBouton(){
       menu.addListener(new ClickListener(){
       @Override
       public void clicked(InputEvent event, float x, float y) {
            Dames.setJouercontreIA(false);
            Dames.setJouercontrejoueur(false);
            J2.remove();
            J1.remove();
            app.setScreen(app.selectgame);
            
       }    
  });
    }
    
  private void creationBouton(){
        
    }
    
}
