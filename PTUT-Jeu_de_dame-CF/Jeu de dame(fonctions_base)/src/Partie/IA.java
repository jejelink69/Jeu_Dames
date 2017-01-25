package Partie;


import Partie.Coup;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class IA {
    private Plateau plat;
    private int couleur; // 0-> Blanc ; 1-> Noir
    private int adverse;// Couleur de l'adversaire
    
    IA(Plateau plat,int couleur){
        this.plat=plat;
        this.couleur=couleur;
        if(couleur==0)adverse=1;
        else adverse=0;
    }
    
    
    
        //Fonction Max de l'algo Min-Max; Calcul des coup joué par l'IA
        public int max(Coup c, int prof){
        int max=-9999999;
        int tmp;
        int x=c.getPion().getPosX();
        int y=c.getPion().getPosY();
        
           List<Coup> suiv=new ArrayList<Coup>();
        if(prof<=0){
            return plat.evaluation(c);  //Si profondeur<0 alors fin de l'arbre
        }
        
        plat.jouerCoup(c);          //On joue le coup et on voit ce que peut faire l'adrversaire avec MIN
        suiv=plat.coupPossible(couleur);
        for (Coup cp : suiv){
            tmp=min(cp,prof-1);
            
            if(tmp>max)max=tmp;
        }
        plat.dejouerCoup(c);
        int ev=plat.evaluation(c);
        return ev+max;
    }
        
        //Fonction Min de l'algo Min-Max   Calcul des coups de l'adversaire de l'IA
        public int min(Coup c, int prof){
        int min=9999999;
        int tmp;
           List<Coup> suiv=new ArrayList<Coup>();
        if(prof<=0){
            return -plat.evaluation(c);
        }
        plat.jouerCoup(c);
 
        suiv=plat.coupPossible(adverse);
        for (Coup cp : suiv){
            tmp=max(cp,prof-1);
            
            if(tmp<min)min=tmp;
        }
        plat.dejouerCoup(c);
        int ev=-plat.evaluation(c);
        return ev+min;
    }
        
        //On lance l'algo Min-Max pour chaque coup possile, On joue uniquement celui ayant la meilleure valuation
        public void jouerIA(int prof){
            List<Coup> cps=new ArrayList<Coup>();
           int tmp,max=-99,pil;
           Random rand=new Random();
          //On vérifie que la partie n'est pas finie
         
           cps=plat.coupPossible(couleur);
          
        
           Coup coupFin=new Coup(null,0,0,0);
           
            
          
            for(Coup c:cps){
                tmp=max(c,prof);
                
                if(tmp>max){
                    max=tmp;
                    coupFin=c;
                }else if(tmp==max){         //Si un arbre à la même valeur que le max alors aléatoirement il remplace celui-ci
                    pil=rand.nextInt(2);
                    if(pil==0){
                        max=tmp;
                        coupFin=c;
                    }
                    
                }
            }
            
            plat.jouerCoup(coupFin);
        
        }
}
