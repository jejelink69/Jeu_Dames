package Partie;


public class Pion {
        
    private String couleur;         //Blanc ou Noir
    private int posX;
    private int posY;
    private boolean dame;
  
    

    public Pion( String couleur, int posX, int posY){
        this.couleur = couleur;
        this.posX=posX;
        this.posY=posY;
        this.dame=false;
    }
    
   
    
    
    //Getter and Setter
    
    public String getCouleur(){
        return this.couleur;
    }
    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public boolean getDame(){
        return this.dame;
    }
    public void setDame(boolean i){
        this.dame=i;
    }
    public void setPosX(int positionX) {
        this.posX = positionX;
    }
    public void setPosY(int positionY) {
        this.posY = positionY;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    
}
