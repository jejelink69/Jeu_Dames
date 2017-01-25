package com.ptut.dames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ptut.dames.Dames;
import static com.ptut.dames.screens.JouerJoueur.choixcouleur;

/**
 *
 * @author Maxime
 */
public class Parametres implements Screen {

    Dames app;
    static SelectBox damier1,damier2;
    Label titre1, titre2;
    Skin skinneon;
    Stage stage;
    OrthographicCamera camera;
    Texture backg;
    SpriteBatch batch;
    Texture affichageDamier;
    Texture affichageDamier2;
    TextButton valider;

    
    

    public Parametres(Dames app) {
        this.app = app;
        skinneon = new Skin(Gdx.files.internal("neonui/neonui/neon-ui.json"));
        stage = new Stage();
        camera = new OrthographicCamera();
        stage.getViewport().setCamera(camera);
        damier1 = new SelectBox(skinneon);
        damier2 = new SelectBox(skinneon);
        backg = new Texture("backgmenu.jpg");
        batch = new SpriteBatch();
        titre1 = new Label("Choix du premier damier", skinneon);
        titre2 = new Label("Choix du second damier", skinneon);
        valider = new TextButton("Valider", skinneon);
        

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setSize(900, 600);
        table.setPosition(0, 0);
        
        String[] choixDamier1 = new String[]{"Noir", "Bleu neon", "Gris", "Marron", "Blanc", "Noir neon", "Rouge"};
        String[] choixDamier2 = new String[]{"Blanc", "Bleu neon", "Gris", "Marron", "Noir", "Noir neon", "Rouge"};

        damier1.setItems(choixDamier1);
        damier2.setItems(choixDamier2);
        table.add(titre1);
        table.add(damier1);
        table.row();
        table.add(titre2).padTop(40);
        table.add(damier2).padTop(40);
        table.row();
        table.add(valider).padTop(90).padLeft(20);
        
        

        stage.addActor(table);
        
        actionBouton();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        conditionAffichage();
        conditionAffichage2();
        batch.begin();
        batch.draw(backg, 0, 0);
        batch.draw(affichageDamier, 600, 390, 40, 40);
        batch.draw(affichageDamier2, 600, 300, 40, 40);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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
        stage.dispose();
    }
    
    public void actionBouton(){
        
         valider.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                app.setScreen(app.selectgame);

            }
        });

        
    }
    
    public static ArraySelection getDamier1() {
        return damier1.getSelection();
    }

    public static ArraySelection getDamier2() {
        return damier2.getSelection();
    }

    public void conditionAffichage(){
        if(damier1.getSelection().first() == "Noir"){
            affichageDamier = new Texture("atlases/atlas_test/carrenoir.png");
        }
        if(damier1.getSelection().first() == "Blanc"){
            affichageDamier = new Texture("atlases/atlas_test/carreblanc.png");
        }
        if(damier1.getSelection().first() == "Bleu neon"){
            affichageDamier = new Texture("atlases/atlas_test/carrebleuneon.png");
        }
        if(damier1.getSelection().first() == "Marron"){
            affichageDamier = new Texture("atlases/atlas_test/carremarron.png");
        }
        
        if(damier1.getSelection().first() == "Gris"){
            affichageDamier = new Texture("atlases/atlas_test/carregris.png");
        }
        if(damier1.getSelection().first() == "Noir neon"){
            affichageDamier = new Texture("atlases/atlas_test/carrenoirneon.png");
        }
        if(damier1.getSelection().first() == "Rouge"){
            affichageDamier = new Texture("atlases/atlas_test/carrerouge.png");
        }
         
    }
    
    public void conditionAffichage2(){
        if(damier2.getSelection().first() == "Noir"){
            affichageDamier2 = new Texture("atlases/atlas_test/carrenoir.png");
        }
        if(damier2.getSelection().first() == "Blanc"){
            affichageDamier2 = new Texture("atlases/atlas_test/carreblanc.png");
        }
        if(damier2.getSelection().first() == "Bleu neon"){
            affichageDamier2 = new Texture("atlases/atlas_test/carrebleuneon.png");
        }
        if(damier2.getSelection().first() == "Marron"){
            affichageDamier2 = new Texture("atlases/atlas_test/carremarron.png");
        }
        
        if(damier2.getSelection().first() == "Gris"){
            affichageDamier2 = new Texture("atlases/atlas_test/carregris.png");
        }
        if(damier2.getSelection().first() == "Noir neon"){
            affichageDamier2 = new Texture("atlases/atlas_test/carrenoirneon.png");
        }
        if(damier2.getSelection().first() == "Rouge"){
            affichageDamier2 = new Texture("atlases/atlas_test/carrerouge.png");
        }
         
    }

}
