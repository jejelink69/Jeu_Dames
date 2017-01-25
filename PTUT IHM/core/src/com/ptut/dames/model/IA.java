package com.ptut.dames.model;


import com.ptut.dames.model.Plateau;
import com.ptut.dames.model.Coup;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *
 * @author p1505974
 */
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
    
        public int max(Coup c, int prof){
        int max=-9999999;
        int tmp;
        int x=c.getPion().getPosX();
        int y=c.getPion().getPosY();
        
           List<Coup> suiv=new ArrayList<Coup>();
        if(prof<=0){
            return plat.evaluation(c);
        }else if(plat.partieFinie()!=0)return 10000;  //L'IA a gagnée la partie
        
        plat.jouerCoup(c);
        suiv=plat.coupPossible(couleur);
        for (Coup cp : suiv){
            tmp=min(cp,prof-1);
            
            if(tmp>max)max=tmp;
        }
        plat.dejouerCoup(c);
        int ev=plat.evaluation(c);
        return ev+max;
    }
        public int min(Coup c, int prof){
        int min=9999999;
        int tmp;
           List<Coup> suiv=new ArrayList<Coup>();
        if(prof<=0){
            return -plat.evaluation(c);
        }
        plat.jouerCoup(c);
        if(plat.partieFinie()==0)return -10000;
        suiv=plat.coupPossible(adverse);
        for (Coup cp : suiv){
            tmp=max(cp,prof-1);
            
            if(tmp<min)min=tmp;
        }
        plat.dejouerCoup(c);
        int ev=-plat.evaluation(c);
        return ev+min;
    }
        public void jouerIA(int prof){
            List<Coup> cps=new ArrayList<Coup>();
           int tmp,max=-99,pil;
           Random rand=new Random();
          //On vérifie que la partie n'est pas finie
           if(plat.partieFinie()==0){
           cps=plat.coupPossible(couleur);
          
               cps.get(2);
           Coup coupFin=cps.get(0);
            
            
          
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
}
