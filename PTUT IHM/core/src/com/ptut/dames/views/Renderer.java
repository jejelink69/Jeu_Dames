
package com.ptut.dames.views;

/**
 *
 * @author Jérémy Montrobert
 */
public interface Renderer {

    /**
     * Doit etre appelé quand un rendu doit etre fait
     *
     * @param delta temps en seconde depuis le dernier rendu
     */
    public void render(float delta);

    /**
     * 
     *
     * @param width nouvelle largeur en px
     * @param height nouvelle hauteur en px
     */
    public void setSize(int width, int height);

    /**
     * doit etre appelé quand le render doit relaché les ressources
     */
    public void dispose();
}

