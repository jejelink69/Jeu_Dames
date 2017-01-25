package Partie;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Partie {
    
    public static void main(String []args){
        Plateau plat=new Plateau();
        Partie test=new Partie();
                
                
      test.iaVsIA(plat,3,3);
        
    
    }
    
    // Fontion pour faire jouer 2 IA une contre l'autre , premier int= profondeur pour les blancs, deuxième int= profondeurs pour les noirs
    public void iaVsIA(Plateau test3, int profBlanc, int profNoir){
         
        Scanner entree=new Scanner(System.in);
          int choix;
      IA blanc=new IA(test3,0);
      IA noir=new IA(test3,1);
      
      while(test3.partieFinie()==0){
          blanc.jouerIA(profBlanc);
          test3.affichePlateau();
          noir.jouerIA(profNoir);
          test3.affichePlateau();
         choix=entree.nextInt();
      }
    }
    
    
    // Jouer contre l'IA , prof de calcul des coups de l'IA
    public void jouerContreIA(int prof){
         Plateau  test3=new Plateau();
      List<Coup> suiv=new ArrayList<Coup>();
      Scanner entree=new Scanner(System.in);
      int choix;
      IA ia=new IA(test3,0);
      while(test3.partieFinie()==0){   
      ia.jouerIA(prof);
       test3.affichePlateau();
      suiv=test3.coupPossible(1);
      for(int j=0;j<suiv.size();j++){
          Coup c=suiv.get(j);
          System.out.println("Coup "+j+" : x="+c.getPion().getPosX()+"; y= "+c.getPion().getPosY()+" Type :"+c.getType()+" Dir :"+c.getDir());
         
      }
      
      choix=entree.nextInt();
      test3.jouerCoup(suiv.get(choix));
    }
    }
    
    
    //2joueurs jouent à tour de rôle
    public void joueurVsJoueur(){
             Plateau  test3=new Plateau();
      List<Coup> suiv=new ArrayList<Coup>();
      Scanner entree=new Scanner(System.in);
      int choix;
      while(test3.partieFinie()==0){
          test3.affichePlateau();
          suiv=test3.coupPossible(0);
      
       for(int j=0;j<suiv.size();j++){
          Coup c=suiv.get(j);
          System.out.println("Coup "+j+" : x="+c.getPion().getPosX()+"; y= "+c.getPion().getPosY()+" Type :"+c.getType()+" Dir :"+c.getDir());
         
      }
       choix=entree.nextInt();
      test3.jouerCoup(suiv.get(choix));
      test3.affichePlateau();
         suiv=test3.coupPossible(1);
      
       for(int j=0;j<suiv.size();j++){
          Coup c=suiv.get(j);
          System.out.println("Coup "+j+" : x="+c.getPion().getPosX()+"; y= "+c.getPion().getPosY()+" Type :"+c.getType()+" Dir :"+c.getDir());
         
      }
      
      choix=entree.nextInt();
      test3.jouerCoup(suiv.get(choix));
    }
    }
    
  
}
