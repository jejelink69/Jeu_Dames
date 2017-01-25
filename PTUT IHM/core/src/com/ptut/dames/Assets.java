
package com.ptut.dames;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.Gdx;

/**
 *
 * @author Jérémy Montrobert
 */
public class Assets {

    public static TextureAtlas gameAtlas;

    /* Charge tous les élélents graphiques tel que les Textures, nécessaires au jeu*/
    public static void loadGame() {
        gameAtlas = new TextureAtlas(Gdx.files.internal("atlases/creatpackage/pions_damiers.atlas"));
    }
    
    public static void disposeGame() {
		gameAtlas.dispose();
	}
}
