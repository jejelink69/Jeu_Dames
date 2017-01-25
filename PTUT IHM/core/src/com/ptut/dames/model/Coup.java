package com.ptut.dames.model;

import java.util.List;

/**
 *
 * @author Simon
 */
public class Coup {
    private Pion pion;
    private int dir; // 0 vers la gauche et 1 vers la droite (On se place coté blanc)
    private int type;// 0 -> deplacement , 1->manger
    private int sens;// 0-> en avant, 1-> en arrière ! Uniquement important quand on mange et par rapport au Pion
    private List<Coup> next;
    private int distance; //Pour les dames 
    private boolean aDefaire=false;
    
    Coup (Pion p , int type, int dir, int sens){
        this.pion=p;
        this.dir=dir;
        this.type=type; 
        this.sens=sens;
        this.next=null;
      
    }
    Coup (Pion p , int type, int dir, int sens,int distance){
        this.pion=p;
        this.dir=dir;
        this.type=type; 
        this.sens=sens;
        this.next=null;
        this.distance=distance;
    }
    

    
    //Getter and Setter
    public Pion getPion() {
        return pion;
    }
    public int getDir() {
        return dir;
    }
    public int getType() {
        return type;
    }
    public List<Coup> getNext() {
        return next;
    }

    public int getSens() {
        return sens;
    }

    public int getDistance() {
        return distance;
    }

    public boolean getAdef() {
        return aDefaire;
    }

    public void setAdef(boolean aDefaire) {
        this.aDefaire = aDefaire;
    }
    

    public void setNext(List<Coup> next) {
        this.next = next;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }
    
    public int getTaille() {
        int i=1;
        Coup temp=this;
        
        while(temp.getNext()!=null){ // On prend le premier de la liste car tous les chemin on la même longueure
            i++;
            temp=temp.getNext().get(0);
        }
        return i;
    }
    
    public String toString(){
      
        return this.pion.getPosX()+" : "+this.pion.getPosY()+" -> Type : "+ type + " Dir : "+dir + " Sens : "+sens +" Dist : "+distance;
    }
   
    
}
