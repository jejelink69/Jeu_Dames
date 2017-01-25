
package com.ptut.dames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ptut.dames.Dames;
import com.ptut.dames.model.Plateau;
import static com.ptut.dames.screens.JouerIA.plateau;
import static com.ptut.dames.screens.JouerJoueur.nomJ1;
import static com.ptut.dames.screens.Parametres.damier1;

/**
 *
 * @author Maxime
 */
public class JouerIA implements Screen {
     private Dames app;
    Texture backg;
    SpriteBatch batch;
    private Stage stage;
    Skin skinneon;
    OrthographicCamera camera;
    static TextField nomJ1;
    static TextField nomIA;
    Label difficulte;
    SelectBox diff;
    static SelectBox  choixcouleur;
    static SelectBox choixcouleur2;
    TextButton menu;
    TextButton play;
    static Plateau plateau;
    Texture apercu1;
    Texture apercu2;
    
            
    public JouerIA(Dames app){
        this.app = app;
        backg=new Texture("backgmenu.jpg");
        batch = new SpriteBatch();
        camera=new OrthographicCamera();
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        skinneon = new Skin(Gdx.files.internal("neonui/neonui/neon-ui.json" ));
        creationBouton();
    }

    private void creationBouton(){
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        nomJ1 = new TextField("Joueur 1", skinneon);
        nomIA = new TextField("IA", skinneon);
        difficulte = new Label("Difficulte :", skinneon);
        menu = new TextButton("<<", skinneon);
        play = new TextButton("Jouer", skinneon);
        String[] diffi = new String[]{"Facile","Moyen", "Difficile"};
        String[] choixTexture = new String[]{"Bleu", "Rouge", "Vert", "Coeur gris", "Coeur rouge", "Etoile bleu","Etoile jaune", "Etoile rouge"};
        String[] choixTexture2 = new String[]{"Rouge", "Bleu", "Vert", "Coeur gris", "Coeur rouge", "Etoile bleu","Etoile jaune", "Etoile rouge"};
        
        
        diff = new SelectBox(skinneon);
        diff.setItems(diffi);
        choixcouleur = new SelectBox(skinneon);
        choixcouleur.setItems(choixTexture);
        choixcouleur2 = new SelectBox(skinneon);
        choixcouleur2.setItems(choixTexture2);
        
        table.add(nomJ1).padBottom(50);
        table.add(choixcouleur).padBottom(50);
        table.row();
        table.add(nomIA).padBottom(50);
        table.add(choixcouleur2).padBottom(50);
        table.row();
        table.add(difficulte);
        table.add(diff);
        table.row();
        table.add(play).width(150).height(50).padLeft(50);
        table.row();
        
        menu.setPosition(800,500);
        stage.addActor(table);
        stage.addActor(menu);
        
         actionBouton();
    }
    
    private void actionBouton(){
       menu.addListener(new ClickListener(){
       @Override
       public void clicked(InputEvent event, float x, float y) {
            
            app.setScreen(app.firstpage);
            
       }    
  });
       
       play.addListener(new ClickListener(){
       @Override
       public void clicked(InputEvent event, float x, float y) {
           Dames.setJouercontreIA(true);
           app.setScreen(app.gamescreen);
           
           
          //  ScreenGame nouv = new ScreenGame(app,nomJ1.getText(),nomIA.getText(),choixcouleur.getSelection(), choixcouleur2.getSelection());
          //  app.setScreen(nouv);
            
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
        apercu();
        apercu2();
        batch.begin();
            batch.draw(backg,0,0);
            batch.draw(apercu1, 620, 405, 40, 40);
            batch.draw(apercu2, 620, 300, 40, 40);
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
        skinneon.dispose();
        stage.dispose();
    }
    
    
    public static ArraySelection getColor1(){
       return choixcouleur.getSelection();
    }
    
    public static ArraySelection getColor2(){
       return choixcouleur2.getSelection();
    }
    
      public static String getnomJ1(){
       return nomJ1.getText();
    }
    public static String getnomJ2(){
       return nomIA.getText();
    }
    
     public static Plateau getPlateau() {
        return plateau;
    }
     
     public void apercu(){ 
        if(choixcouleur.getSelection().first().equals("Bleu")) apercu1=new Texture("atlases/atlas_test/pionbleu.png");
        if(choixcouleur.getSelection().first().equals("Vert")) apercu1=new Texture("atlases/atlas_test/pionvert.png");
        if(choixcouleur.getSelection().first().equals("Rouge")) apercu1=new Texture("atlases/atlas_test/pionrouge.png");
        if(choixcouleur.getSelection().first().equals("Coeur gris")) apercu1=new Texture("atlases/atlas_test/pioncoeurgris.png");
        if(choixcouleur.getSelection().first().equals("Coeur rouge")) apercu1=new Texture("atlases/atlas_test/pioncoeurrouge.png");
        if(choixcouleur.getSelection().first().equals("Etoile bleu")) apercu1=new Texture("atlases/atlas_test/pionetoilebleu.png");
        if(choixcouleur.getSelection().first().equals("Etoile jaune")) apercu1=new Texture("atlases/atlas_test/pionetoilejaune.png");
        if(choixcouleur.getSelection().first().equals("Etoile rouge")) apercu1=new Texture("atlases/atlas_test/pionetoilerouge.png");
     
     }
     
     public void apercu2(){
        if(choixcouleur2.getSelection().first().equals("Bleu")) apercu2=new Texture("atlases/atlas_test/pionbleu.png");
        if(choixcouleur2.getSelection().first().equals("Vert")) apercu2=new Texture("atlases/atlas_test/pionvert.png");
        if(choixcouleur2.getSelection().first().equals("Rouge")) apercu2=new Texture("atlases/atlas_test/pionrouge.png");
        if(choixcouleur2.getSelection().first().equals("Coeur gris")) apercu2=new Texture("atlases/atlas_test/pioncoeurgris.png");
        if(choixcouleur2.getSelection().first().equals("Coeur rouge")) apercu2=new Texture("atlases/atlas_test/pioncoeurrouge.png");
        if(choixcouleur2.getSelection().first().equals("Etoile bleu")) apercu2=new Texture("atlases/atlas_test/pionetoilebleu.png");
        if(choixcouleur2.getSelection().first().equals("Etoile jaune")) apercu2=new Texture("atlases/atlas_test/pionetoilejaune.png");
        if(choixcouleur2.getSelection().first().equals("Etoile rouge")) apercu2=new Texture("atlases/atlas_test/pionetoilerouge.png");
     
     }

    public static TextField getNomJ1() {
        return nomJ1;
    }

    public static TextField getNomIA() {
        return nomIA;
    }
     
     
 }


