
package com.ptut.dames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ptut.dames.Dames;

/**
 *
 * @author Maxime
 */
public class FirstPage implements Screen{
    private Dames app;
    Texture backg;
    SpriteBatch batch;
    private Stage stage;
    Skin skin;
    OrthographicCamera camera;
    
    public FirstPage(Dames app){
        this.app = app;
        backg=new Texture("backgjeudames.jpg");
        batch = new SpriteBatch();
        camera=new OrthographicCamera();
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        skin = new Skin(Gdx.files.internal("neonui/neonui/neon-ui.json" ));
        creationBouton();
    }

    private void creationBouton(){
        Gdx.input.setInputProcessor(stage);
        
        Table table = new Table();
        table.setFillParent(true);
        
        Texture playTexture = new Texture("playbuttfin.jpg");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        
        final ImageButton play = new ImageButton(drawable);
        table.add(play).padTop(150);
        table.row();
        stage.addActor(table);
        
          play.addListener(new ClickListener(){
       @Override
       public void clicked(InputEvent event, float x, float y) {
            
            app.setScreen(app.selectgame);
       }  
  });
       
          
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
            batch.draw(backg,0,0);
        batch.end();
        stage.act();
        stage.draw();
      
    }

    @Override
    public void resize(int width, int height) {
     camera.setToOrtho(false,width,height);
    }

    @Override
    public void pause() {
    
    }

    @Override
    public void resume() {
   
    }

    @Override
    public void hide() {
    
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
        batch.dispose();
    }
}
