package Partie;

import java.util.List;


public class Coup {
    private Pion pion;
    private int dir; // 0 vers la gauche et 1 vers la droite (On se place coté blanc)
    private int type;// 0 -> deplacement , 1->manger
    private int sens;// 0-> en avant, 1-> en arrière ! Uniquement important quand on mange et par rapport au Pion
    private List<Coup> next;
    private int distance; //Distance maximum possible pour les Dames
    private boolean aDefaire=false;     //True si le coup a été joué et que l'on peut le déjoueur
    
    Coup (Pion p , int type, int dir, int sens){        //Coup pour les Pions
        this.pion=p;
        this.dir=dir;
        this.type=type; 
        this.sens=sens;
        this.next=null;
      
    } 
    Coup (Pion p , int type, int dir, int sens,int distance){ //Coup pour les Dames
        this.pion=p;
        this.dir=dir;
        this.type=type; 
        this.sens=sens;
        this.next=null;
        this.distance=distance;
    }
    
    // ON récupère la taille d'un coup (Le nombre de coup pour effectuer une séquence de type manger plusieurs pion car obligatoire)
    public int getTaille() {
        int i=1;
        Coup temp=this;
        
        while(temp.getNext()!=null){ // On prend le premier de la liste car tous les chemin on la même longueure
            i++;
            temp=temp.getNext().get(0);
        }
        return i;
    }
    
    //Getters and Setters
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
    

    
    public String toString(){
      
        return this.pion.getPosX()+" : "+this.pion.getPosY()+" -> Type : "+ type + " Dir : "+dir + " Sens : "+sens +" Dist : "+distance;
    }
   
    
}
