/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptut.dames.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.ptut.dames.Assets;
import com.ptut.dames.screens.Parametres;

/**
 *
 * @author Jérémy Montrobert
 */
public class Tile extends Actor {

    public boolean estEnSurbrillance;

    private TextureRegion textureRegion;
    private TextureRegion TextureRegionEnSurbrillance;

    /**
     * Créer une tile pour le Plateau
     *
     * @param x position horizontale de la tile
     * @param y position verticale de la tile
     * @param estNoir Determine si la tile est noire ou non
     */
    public Tile(int x, int y, boolean estNoir) {
        this.setBounds(x, y, 1, 1);
        ArraySelection selection1 = Parametres.getDamier1();
        ArraySelection selection2 = Parametres.getDamier2();
        if (estNoir) {
            if (selection1.first() == null) {
                this.textureRegion = Assets.gameAtlas.findRegion("carrenoir");
            } else {
                if (selection1.first() == "Noir") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carrenoir");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carrerouge");
                }
                if (selection1.first() == "Bleu neon") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carrebleuneon");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carrerouge");
                }
                if (selection1.first() == "Gris") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carregris");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carregrissurbri");
                }
                if (selection1.first() == "Noir neon") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carrenoirneon");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("noirneonsurbri");
                }
                if (selection1.first() == "Blanc") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carreblanc");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carreblancsurbri");
                }
                if (selection1.first() == "Marron") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carremarron");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carremarronsurbri");
                }
                if (selection1.first() == "Rouge") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carrerouge");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carrerougesurbri");
                }

            }

        } else {

            if (selection2.first() == null) {
                this.textureRegion = Assets.gameAtlas.findRegion("carreblanc");
                this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carreblancsurbri");
            } else {
                if (selection2.first() == "Noir") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carrenoir");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carrerouge");
                }
                if (selection2.first() == "Bleu neon") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carrebleuneon");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carrerouge");
                }
                if (selection2.first() == "Gris") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carregris");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carregrissurbri");
                }
                if (selection2.first() == "Noir neon") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carrenoirneon");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("noirneonsurbri");
                }
                if (selection2.first() == "Blanc") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carreblanc");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carreblancsurbri");
                }
                if (selection2.first() == "Marron") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carremarron");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carremarronsurbri");
                }
                if (selection2.first() == "Rouge") {
                    this.textureRegion = Assets.gameAtlas.findRegion("carrerouge");
                    this.TextureRegionEnSurbrillance = Assets.gameAtlas.findRegion("carrerougesurbri");
                }

            }

            // this.textureRegion = Assets.gameAtlas.findRegion("carreblanc");
            //  this.TextureRegionEnSurbrillance = Assets.gameAtlas
            //         .findRegion("carreblanc");
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (this.estEnSurbrillance) {
            batch.draw(this.TextureRegionEnSurbrillance, this.getX(), this.getY(),
                    1, 1);
        } else {
            batch.draw(this.textureRegion, this.getX(), this.getY(), 1, 1);
        }
    }

}
