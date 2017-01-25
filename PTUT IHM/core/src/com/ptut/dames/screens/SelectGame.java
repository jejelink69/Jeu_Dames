package com.ptut.dames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ptut.dames.Dames;
import java.awt.Font;

/**
 *
 * @author Maxime
 */
public class SelectGame implements Screen {

    private Dames app;
    Texture backg;
    SpriteBatch batch;
    private Stage stage;
    Skin skinneon;
    OrthographicCamera camera;
    TextButton jouerIA;
    TextButton jouerJoueur;
    TextButton exit;
    TextButton regles;
    TextButton credit;
    Dialog reglesduJeu;
    Drawable drawRegle;
    Texture textureRegle;
    ImageButton regle;
    TextButton menu;
    ImageButton param;

    public SelectGame(Dames app) {
        this.app = app;
        backg = new Texture("backgmenu.jpg");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        skinneon = new Skin(Gdx.files.internal("neonui/neonui/neon-ui.json"));
        creationBouton();
    }

    private void creationBouton() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setSize(900, 600);
        table.setPosition(0, 0);

        Texture textureParam = new Texture("engrenage.png");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(textureParam));
        param = new ImageButton(drawable);

        textureRegle = new Texture("ensavoirplus.png");
        drawRegle = new TextureRegionDrawable(new TextureRegion(textureRegle));
        regle = new ImageButton(drawRegle);

        jouerIA = new TextButton("Jouer contre IA", skinneon);
        jouerJoueur = new TextButton("Jouer contre un Joueur", skinneon);
        exit = new TextButton("Quitter le jeu", skinneon);
        menu = new TextButton("<<", skinneon);

        credit = new TextButton("Credit", skinneon);
        table.row();
        table.add(jouerIA).width(200).height(75).padTop(20);
        table.row();

        table.add(jouerJoueur).width(200).height(75).padTop(20);
        table.row();

        table.add(exit).width(200).height(75).padTop(20);
        table.row();
        table.add(credit).width(200).height(75).padTop(20);
        table.row();

        param.setPosition(790, 10);
        regle.setPosition(720, 10);
        menu.setPosition(800, 500);

        stage.addActor(menu);
        stage.addActor(param);
        stage.addActor(regle);
        stage.addActor(table);

        actionBouton();
    }

    private void actionBouton() {

        //JOUERIA 
        jouerIA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                app.setScreen(app.jouerIA);

            }
        });
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                app.setScreen(app.firstpage);

            }
        });

        // REGLES
        regle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Dialog dialog;
                dialog = new Dialog(" ", new Skin(Gdx.files.internal("visui/uiskin.json"))) {

                    {
//                        Texture textureParam = new Texture("fondDialog.jpg");
//                         Drawable drawww = new TextureRegionDrawable(new TextureRegion(textureParam));
//                         background(drawww);
                        text("REGLES DU JEU\n\n");
                        text(rgls());

                        // button("Quitter cette page", "Bonne partie !");
                        button("Page 2");
                    }

                    @Override
                    protected void result(final Object object) {
                        new Dialog("", new Skin(Gdx.files.internal("visui/uiskin.json"))) {

                            {
//                           text(object.toString());
                                text(regles2());
                                button("Page 3").addListener(new ClickListener() {
                                    public void clicked(InputEvent event, float x, float y) {
                                        Dialog dialog;
                                        dialog = new Dialog(" ", new Skin(Gdx.files.internal("visui/uiskin.json"))) {
                                            {

                                                text(regles3());

                                                button("Retour au jeu");
                                            }
                                        }.show(stage);
                                    }

                                });
                            }

                        }.show(stage);
                    }

                }.show(stage);

            }
        });
        
        
        credit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Dialog dialog;
                dialog = new Dialog(" ", new Skin(Gdx.files.internal("visui/uiskin.json"))) {

                    {

                        text("Projet tuteuré réalisé à L'IUT Informatique Lyon1. \nAuteur : Jeremy Montrobert \nMaxime Borel\nSimon Raschetti\nVincent Rossero\nTuteur : Vincent Vidal");
                       
                        button("Retour au jeu");
                    }


                }.show(stage);

                }});
        


        jouerJoueur.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                app.setScreen(app.jouerjoueur);

            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Gdx.app.exit();
            }});
        
        
        param.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                app.setScreen(app.parametres);

            }
        });

        credit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

              

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
        batch.draw(backg, 0, 0);
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
        skinneon.dispose();
        stage.dispose();
    }

    private Label rgls() {

        String regles = "Nombres de joueurs : 2\n"
                + "\n"
                + "\n"
                + "Le damier: 10x10 cases\n"
                + "\n"
                + "\n"
                + "Les pièces : pions et dames. Un joueur avec les pions/dames blancs et l’autre avec \n"
                + "les pions/dames noirs.\n"
                + "\n"
                + "\n"
                + "Déplacement : Les joueurs jouent chacun leur tour.\n"
                + "\n"
                + "\n"
                + "Les pions se déplacent vers l’avant en diagonale  d’une case si elle est libre si un pion adverse est sur la case, le pion le « mange ». \nLorsqu’un pion se trouve en présence, diagonalement, d’une pièce adverse derrière laquelle se trouve une case libre, \nil doit obligatoirement sauter par-dessus cette pièce et occuper la case libre.\n"
                + "S’il existe un pion à prendre après la prise d’un pion, "
                + "\n il faut obligatoirement prendre ce pion puis du troisième s’il en existe un autre et etc.. Jusqu’à occuper la case vide. \nDonc il y ‘a un changement de position et direction possible à la suite de prises successives.  C’est une rafle.\n";

        Label text = new Label(regles, new Skin(Gdx.files.internal("visui/uiskin.json")));
        text.setFontScale((float) 0.9);

        return text;

    }

    public Label regles2() {
        String str;
        str = "La dame peut aller vers l’avant et l’arrière sur les cases en diagonales, elle n’a pas de limitation de cases. \nLorsqu’une dame se trouve en présence sur la même diagonale, directement ou à distance, d’une pièce adverse\n derrière laquelle se trouvent une ou plusieurs cases libres, elle doit obligatoirement passer \npar-dessus cette pièce et occuper, au choix, une des cases libres. \n"
                + "S’il existe un pion à prendre après la prise d’un pion, il faut obligatoirement prendre ce pion puis du troisième s’il en existe un autre et etc.. \nJusqu’à occuper la case vide. \n Donc il y ‘a un changement de position et direction possible à la suite de prises successives. C’est une rafle.\n"
                + "!!!! Pendant une rafle, il faut manger le plus de pions possibles. \nSi par exemple, on a la possibilité de manger trois dames ou quatres pions simples, il faut manger les quatres simples.\n"
                + "\n"
                + "But : «Manger» tous les pions adverses en passant au-dessus d’un pion adverse et indirectement\n atteindre la dernière rangée avec un pion pour obtenir une  dame et bénéficier d’avantages\n"
                + "Enlever les pions du damier : Après avoir mangé un pion, il est possible de l’enlever du jeu. Néanmoins, s’il existe une rafle, on doit attendre la fin \nde cette rafle avant d’enlever tous les pions. \nIl est possible par exemple pour une dame dans une rafle de passer plusieurs fois sur la même case mais \nil est interdit de passer deux fois sur un pion déjà raflé\n";
        Label text = new Label(str, new Skin(Gdx.files.internal("visui/uiskin.json")));
        text.setFontScale((float) 0.9);

        return text;

    }

    public Label regles3() {

        String str = "Fin de partie : Le gain est obtenu par un joueur lorsque son adversaire :\n"
                + "Abandonne la partie ou se trouve dans l’impossibilité de jouer alors qu’il a le trait ou n’a plus de pièces.\n"
                + "Possibilité d’égalité si : aucun n’arrive à éliminer les pions. :\n"
                + "“La fin de partie est considérée comme égale lorsque la même position se représente pour la troisième fois, le même joueur ayant le trait.\n"
                + "Si, durant 25 coups, il n'y a ni déplacement de pion ni prise, la fin de partie est considérée comme égale.\n"
                + "S’il n 'y a plus que trois dames, deux dames et un pion, ou une dame et deux pions contre une dame, \nla fin de partie sera considérée comme égale lorsque les deux joueurs auront encore joué chacun 16 coups au maximum.\n"
                + "Pour autant qu’il n’y ait pas de phase de jeu en cours, la fin de partie de deux dames contre une dame, \net a fortiori, de une dame contre une dame, sera considérée égale.”\n"
                + "Déplacement des pions : \n"
                + "Un joueur sélectionne un pion en cliquant dessus. Les pions disponibles sont en surbrillance. \nCe pion passe en surbrillance ainsi que les cases en diagonale où il peut aller (la diagonale de droite et de gauche).\n Néanmoins, si un pion se situe à proximité, le joueur est dans l’obligation de prendre le pion .\n"
                + "L’image illustre ce cas. Le joueur sélectionne le pion noir, il n’a qu’un seul choix, car il faut impérativement prendre le pion rouge. \n"
                + "Cette règle s’applique aussi dans le cas d’une rafle, où le pion qui effectue cet enchaînement est sensé prendre tout les pions disponibles.\n"
                + "C’est pareil pour une dame, mais celle-ci pour aller sur toutes les cases en diagonale (cf les règles du jeu).";
        Label text = new Label(str, new Skin(Gdx.files.internal("visui/uiskin.json")));
        text.setFontScale((float) 0.9);

        return text;
    }

}
