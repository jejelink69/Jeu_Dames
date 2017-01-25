
package com.ptut.dames.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ptut.dames.Dames;
import com.ptut.dames.controller.ControlPlateau;
import com.ptut.dames.model.Plateau;
import com.ptut.dames.model.Tile;
import com.ptut.dames.screens.GameScreen;

public class GameRenderer implements Renderer {
    Dames app;
    GameScreen game;
    private final Stage stage;
    private Table hud;
    
    protected ControlPlateau controller;
    
    
    Stage stageBout;
    Skin skinneon;
    OrthographicCamera camera;
    
    

    public GameRenderer(Plateau plateau, ControlPlateau controller, Dames app, GameScreen game) {
        
        this.game=game;
        this.app=app;
        stage = new Stage(new FitViewport(10, 10));
        this.stage.addActor(plateau);
        this.controller = controller;
        
        
        game.multiplexer.addProcessor(stage);
        game.multiplexer.addProcessor(new GestureDetector(new ControlPlateau(plateau)));
        
        
      
    }

    @Override
    public void render(float delta) {
        Plateau plateau = new Plateau(app);
      
        Gdx.gl.glClearColor(.0f, .3f, .4f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.getViewport().apply();
        stage.act();
        stage.draw();
        
        
 
    }

    @Override
    public void setSize(int width, int height) {
        
        this.stage.getViewport().update(width, height, false);
        
   
       Gdx.graphics.requestRendering();
    }

    @Override
    public void dispose() {
        }
}