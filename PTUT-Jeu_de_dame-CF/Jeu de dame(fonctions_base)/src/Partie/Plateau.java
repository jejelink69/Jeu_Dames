package Partie;

import java.util.ArrayList;
import java.util.List;


public class Plateau {
    private  Pion plateau[][];
    
    public Plateau() {
        plateau=new Pion[10][10];
        this.initPlateau();
        
    }


    
    public void affichePlateau(){  //Methode pour afficher le plateau en console
        for (int i=9;i>=0;i--){
            for(int j=0;j<10;j++){
                if(plateau[i][j]!=null){
                    if(plateau[i][j].getCouleur().equals("Blanc")) System.out.print("| O ");
                    else System.out.print("| X ");
                }
                else System.out.print("|   ");
            }
             System.out.println("|");
            System.out.println("");
        }
        
        System.out.println("--------------------------------------------");
        
       
    }
    
    public void jouerCoup(Coup c){
        
        int dir=c.getDir();         //On recupère tous les paramètres du coup
        int type=c.getType();
        int sens=c.getSens();
        Pion p=c.getPion();
        String coul=p.getCouleur();
        int x;        
        int y;
        int xInte=99;  //Positions du pion qui va être mangé
        int yInte=99;
        //Deplacement d'un pion
        if(type==0){
            if(sens==0){
            if(coul.equals("Blanc"))x=p.getPosX()+1; //Future position X
            else x=p.getPosX()-1;

            if(dir==0) y=p.getPosY()-1; //Future position Y
            else y=p.getPosY()+1;
            }else{
                if(coul.equals("Blanc"))x=p.getPosX()-1; //Future position X
            else x=p.getPosX()+1;

            if(dir==0) y=p.getPosY()-1; //Future position Y
            else y=p.getPosY()+1;
            }      
        }
        //On mange un Pion
        else{
            
            if(sens==0){
                if(coul.equals("Blanc")){x=p.getPosX()+2; xInte=x-1;} //Future position X
                else { x=p.getPosX()-2; xInte=x+1;}
            }
            else{
                if(coul.equals("Blanc")){x=p.getPosX()-2; xInte=x+1;} //Future position X
                else { x=p.getPosX()+2; xInte=x-1;}
                
            }
            if(dir==0){ y=p.getPosY()-2; yInte=y+1;}//Future position Y
                else{ y=p.getPosY()+2; yInte=y-1;}
            
        }
        
        
        if(x<10 && x>=0 && y>=0 && y<10){                    // On vérifie que le déplacement reste dans le plateau
            if(plateau[x][y]==null){                        // On vérifie que l'on n'écrase pas un pion
                plateau[p.getPosX()][p.getPosY()]=null;   //On bouge le pion dans le tableau
                if((coul.equals("Blanc")&& x==9)||(coul.equals("Noir")&& x==0)){ //Si l'on crée un dame
                   p.setDame(true);
                }
                plateau[x][y]=p;
                p.setPosX(x);                 //On modifie ses attributs de positions            
                p.setPosY(y);
                
                
                if(xInte!=99 && yInte!=99)plateau[xInte][yInte]=null;  //On supprime le Pion une fois que le coup est effectué
            }
            
            c.setAdef(true);            //Pour que le coup ne soit déjouer qu'une seule fois
        }
        
        if(c.getType()==1 && c.getNext()!=null){        // Si le joueur peut/doit manger un autre Pion et qu'il n'a qu'une seule possibilité alors le système joue pour lui 
            if(c.getNext().size()==1){
                jouerCoup(c.getNext().get(0));
            }
        }
       
    }
    
    
    //Pour les dames qui peuvent se déplacer de plusieurs cases
    public void jouerCoup(Coup c, int dist){
        int distMax=c.getDistance();
        
        if(c.getType()==0){
        if(dist<=distMax){
            int i=0;
            while(i<=dist){
                jouerCoup(c);
                i++;
            }
        }
        }else{
            jouerCoup(c);
            for(int i=1;i<=dist;i++){
                jouerCoup(new Coup(c.getPion(),0,c.getDir(),c.getSens()));
            }
        }
    } 
    
    public void dejouerCoup(Coup c){  //On calcul le coup inverse pour le jouer et ainsi l'annuler
        int coul=0;
        
        if(c.getAdef()==true){
        if(c.getNext()!=null)dejouerCoup(c.getNext().get(0));
        if (c.getSens() == 0) {
            if (c.getType() == 0) {
                if (c.getDir() == 0) {
                    jouerCoup(new Coup(c.getPion(), 0, 1, 1));
                } else {
                    jouerCoup(new Coup(c.getPion(), 0, 0, 1));
                }
            } else if (c.getDir() == 0) {
                jouerCoup(new Coup(c.getPion(), 0, 1, 1));
                jouerCoup(new Coup(c.getPion(), 0, 1, 1));
                if (c.getPion().getCouleur().equals("Blanc")) {
                    coul = 1;
                    placerPion(coul, c.getPion().getPosX() + 1, c.getPion().getPosY() - 1);
                } else {
                    placerPion(coul, c.getPion().getPosX() - 1, c.getPion().getPosY() - 1);
                }
            } else {
                jouerCoup(new Coup(c.getPion(), 0, 0, 1));
                jouerCoup(new Coup(c.getPion(), 0, 0, 1));
                if (c.getPion().getCouleur().equals("Blanc")) {
                    coul = 1;
                    placerPion(coul, c.getPion().getPosX() + 1, c.getPion().getPosY() + 1);
                } else {
                    placerPion(coul, c.getPion().getPosX() - 1, c.getPion().getPosY() + 1);
                }
            }
        }else{                                                      //Pour un coup de type manger on deplace en pion comme il etait avant puis on recréer le pion mangé
            if (c.getType() == 0) {
                if (c.getDir() == 0) {
                    jouerCoup(new Coup(c.getPion(), 0, 1, 0));
                } else {
                    jouerCoup(new Coup(c.getPion(), 0, 0, 0));
                }
            } else if (c.getDir() == 0) {
                jouerCoup(new Coup(c.getPion(), 0, 1, 0));
                jouerCoup(new Coup(c.getPion(), 0, 1, 0));
                if (c.getPion().getCouleur().equals("Blanc")) {
                    coul = 1;
                    placerPion(coul, c.getPion().getPosX() - 1, c.getPion().getPosY() - 1);
                } else {
                    placerPion(coul, c.getPion().getPosX() + 1, c.getPion().getPosY() - 1);
                }
            } else {
                jouerCoup(new Coup(c.getPion(), 0, 0, 0));
                jouerCoup(new Coup(c.getPion(), 0, 0, 0));
                if (c.getPion().getCouleur().equals("Blanc")) {
                    coul = 1;
                    placerPion(coul, c.getPion().getPosX() - 1, c.getPion().getPosY() + 1);
                } else {
                    placerPion(coul, c.getPion().getPosX() +1, c.getPion().getPosY() + 1);
                }
            }
        }
        }
        
        c.setAdef(false);
    }
    
    public void dejouerCoup(Coup c, int dist){
         int distMax=c.getDistance();
        
         if(c.getType()==0){
             if(dist<=distMax){
            int i=0;
            while(i<=dist){             
                dejouerCoup(c);
                c.setAdef(true);            // Car une dame peut faire plusieurs déplacement de une case
                i++;
            }
             }
        }else{
           
             Coup cp=new Coup(c.getPion(),0,c.getDir(),c.getSens());                //
                for(int i=1;i<=dist;i++){
                    cp.setAdef(true);
                    dejouerCoup(cp);
                } 
                dejouerCoup(c);
             }
         
        
    } //Pour les dames qui peuvent se déplacer de plusieurs cases
    
    public List<Coup> coupPossible(int coul){ // 0-> Blanc et 1-> Noir  Renvoi la liste des coups possible pour la couleur passée en paramètre
               
        if (coul==0){
           List<Coup> attaque= attaquePossibleBlanc();
           
           if(attaque==null){
               List<Coup> deplacement= deplacementPossibleBlanc();              //Si une des deux listes est null
               
               
               return deplacement;                                              //On a enlever les coups des dames pour garder le jeu fonctionnel
           }else{
              return attaque;
           }
        }else{
            List<Coup> attaque= attaquePossibleNoir();
         
            
           if(attaque==null ){
               List<Coup> deplacement= deplacementPossibleNoir();
                
               return deplacement;
           }else{
             return attaque;
         
           }
        }
    }
     
    private void initPlateau(){
        //Initialisation des pions blancs
        
       for(int i=0;i<4;i++){
           for(int j=0;j<10;j+=2){
               if(i%2==0)plateau[i][j]=new Pion("Blanc",i,j);
               else plateau[i][j+1]=new Pion("Blanc",i,j+1);
            }
        }
       //Initialisation des pions noirs
       for(int i=6;i<10;i++){
           for(int j=0;j<10;j+=2){
               if(i%2==0)plateau[i][j]=new Pion("Noir",i,j);
               else plateau[i][j+1]=new Pion("Noir",i,j+1);
            }
        }
    }
    
    public int partieFinie(){       //0-> Pas finie 1-> blanc a gagné 2->Noir a gagné
        int cptNoir=0;
        int cptBlanc=0;
        for (int i=0;i<=9;i++){
            for (int j=0;j<9;j++){
                if(plateau[i][j]!=null){
                if(plateau[i][j].getCouleur().equals("Blanc"))cptBlanc+=1;
                else cptNoir+=1;
        }
        }
        }
        if(cptNoir==0)return 1;
        else if(cptBlanc==0)return 2;
        else return 0;
    }
    
   
    //Méthodes nécessaires pour CoupPossible
    public List<Coup> deplacementPossibleBlanc(){                           //Pour chaque Pion du tableau on regarde si les cases devant sont disponibles
        List<Coup> moves = new ArrayList<Coup>();

        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (plateau[i][j] != null && !plateau[i][j].getDame()) {
                    Pion temp = plateau[i][j];
                    if (temp.getCouleur().equals("Blanc")) {
                        if (i < 9) {
                            if (j == 0) {
                                if (plateau[i + 1][j + 1] == null) {
                                    moves.add(new Coup(temp, 0, 1, 0));
                                }
                            } else if (j == 9) {
                                if (plateau[i + 1][j - 1] == null) {
                                    moves.add(new Coup(temp, 0, 0, 0));
                                }
                            } else if (j > 0 && j < 9) {
                                if (plateau[i + 1][j - 1] == null) {
                                    moves.add(new Coup(temp, 0, 0, 0));
                                }
                                if (plateau[i + 1][j + 1] == null) {
                                    moves.add(new Coup(temp, 0, 1, 0));
                                }
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }
    
    private List<Coup> deplacementPossibleNoir(){
        List<Coup> moves = new ArrayList<Coup>();

        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (plateau[i][j] != null) {
                    Pion temp = plateau[i][j];
                    if (temp.getCouleur().equals("Noir")) {
                        if (i > 0) {
                            if (j == 0) {
                                if (plateau[i - 1][j + 1] == null) {
                                    moves.add(new Coup(temp, 0, 1, 0));  //Deplacement à droite quand on est sur le bord gauche
                                }
                            } else if (j == 9) {
                                if (plateau[i - 1][j - 1] == null) {
                                    moves.add(new Coup(temp, 0, 0, 0));   //Deplacement à gauche quand on est sur le bord droit
                                }
                            } else if (j > 0 && j < 9) {
                                if (plateau[i - 1][j - 1] == null) {
                                    moves.add(new Coup(temp, 0, 0, 0)); //Deplacement à gauche
                                }
                                if (plateau[i - 1][j + 1] == null) {
                                    moves.add(new Coup(temp, 0, 1, 0)); //Deplacement à droite
                                }
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }
    
    private List<Coup> attaquePossibleBlanc(){          //Pour chaque Pion on regarde si un pion adverse se trouve devant lui puis on récupère tous les coups pour chaque pion
        List<Coup> moves=new ArrayList<Coup>();           //On ne garde ensuite que les plus grands coups
        
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (plateau[i][j] != null) {
                    Pion temp = plateau[i][j];
                    if (temp.getCouleur().equals("Blanc")) {
                        if (j == 0 || j == 1) {
                            if (i < 8 && i > 1) {     // Si le pion est sur le bord de gauche alors uniquement déplacement sur la droite
                                if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==8 || i==9) {  // Uniquement manger vers le bas
                                if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==1 || i==0) {  // Uniquement vers le haut
                                if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        } else if (j == 8 || j == 9) {   // Si le pion est sur le bord de droite alors uniquement déplacement sur la gauche
                            if (i < 8 && i > 1) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==8 || i==9) {  // Uniquement manger vers le bas
                                if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==0||i==1) {  // Uniquement vers le haut
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        } else if (j >1 && j < 8) { //Si le pion n'est pas sur les bords
                            if (i > 1 && i < 8) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i == 0 || i == 1) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i == 8 || i == 9) {
                                if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaquePionBlanc(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        List<Coup> resultat = gardeLesMax(moves);
        if (resultat != null) {
            return resultat;
        } else {
            return null;
        }
    }
    private List<Coup> attaquePossibleNoir(){           // Pareil que pour attaque blanc
        List<Coup> moves=new ArrayList<Coup>();
    
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (plateau[i][j] != null) {
                    Pion temp = plateau[i][j];
                    if (temp.getCouleur().equals("Noir")) {
                        if (j == 0 || j == 1) {
                            if (i < 8 && i>1) {     // Si le pion est sur le bord de gauche alors uniquement déplacement sur la droite
                                if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i > 7) {  // Uniquement manger vers le bas
                                if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i < 2) {  // Uniquement vers le haut
                                if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        } else if (j == 8 || j == 9) {   // Si le pion est sur le bord de droite alors uniquement déplacement sur la gauche
                            if (i < 8 && i > 1) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i > 7) {  // Uniquement manger vers le bas
                                if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i < 2) {  // Uniquement vers le haut
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        } else if (j > 1&& j < 8) { //Si le pion n'est pas sur les bords
                            if (i > 1 && i < 8) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i == 0 || i == 1) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i == 8 || i == 9) {
                                if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaquePionNoir(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        List<Coup> resultat = gardeLesMax(moves);
        if (resultat != null) {
            return resultat;
        } else {
            return null;
        }

    }
    
    
    private List<Coup> attaquePionBlanc(Pion temp){         //Pour chaque Pion on regarde s'il peut manger un pion , on joue le coup et on regarde s'il peut en manger d'autre pour les rafles
        int x=temp.getPosX();                               //On ne garde que les plus long coups pour ce pion
        int y=temp.getPosY();
       
        List<Coup> res= new ArrayList<Coup>();
        
       
          if(y==0|| y==1){
            if(x<8 && x>1){     // Si le pion est sur le bord de gauche alors uniquement déplacement sur la droite
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Noir")){
                if(plateau[x+2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Noir")){
                if(plateau[x-2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x>7){  // Uniquement manger vers le bas
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Noir")){
                if(plateau[x-2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x<2){  // Uniquement vers le haut
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Noir")){
                if(plateau[x+2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }
          }else if(y==8 || y==9){   // Si le pion est sur le bord de droite alors uniquement déplacement sur la gauche
            if(x<8 && x>1){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Noir")){
                if(plateau[x+2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Noir")){
                if(plateau[x-2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x>7){  // Uniquement manger vers le bas
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Noir")){
                if(plateau[x-2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x<2){  // Uniquement vers le haut
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Noir")){
                if(plateau[x+2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }
          }else if(y>1 && y<8){ //Si le pion n'est pas sur les bords
            if(x>1 && x<8){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Noir")){
                if(plateau[x+2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Noir")){
                if(plateau[x+2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Noir")){
                if(plateau[x-2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Noir")){
                if(plateau[x-2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x==0|| x==1){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Noir")){
                if(plateau[x+2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Noir")){
                if(plateau[x+2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x==8||x==9){
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Noir")){
                if(plateau[x-2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Noir")){
                if(plateau[x-2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionBlanc(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
                }
            }
        }
    List<Coup> resultat= gardeLesMax(res);
    if(resultat!=null)return resultat;
    else return null;
    }
    
    private List<Coup> attaquePionNoir(Pion temp){      //Pareil que pour les blanc
        int x=temp.getPosX();
        int y=temp.getPosY();
        List<Coup> res= new ArrayList<Coup>();
               
       
          if(y==0 || y==1){
            if(x<8 && x>1){     // Si le pion est sur le bord de gauche alors uniquement déplacement sur la droite
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Blanc")){
                if(plateau[x+2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Blanc")){
                if(plateau[x-2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x>7){  // Uniquement manger vers le bas
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Blanc")){
                if(plateau[x-2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x<2){  // Uniquement vers le haut
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Blanc")){
                if(plateau[x+2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }
          }else if(y==8 || y==9){   // Si le pion est sur le bord de droite alors uniquement déplacement sur la gauche
            if(x<8 && x>1){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Blanc")){
                if(plateau[x+2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Blanc")){
                if(plateau[x-2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x>7){  // Uniquement manger vers le bas
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Blanc")){
                if(plateau[x-2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x<2){  // Uniquement vers le haut
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Blanc")){
                if(plateau[x+2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }
          }else if(y>1 && y<8){ //Si le pion n'est pas sur les bords
            if(x>1 && x<8){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Blanc")){
                if(plateau[x+2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Blanc")){
                if(plateau[x+2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Blanc")){
                if(plateau[x-2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Blanc")){
                if(plateau[x-2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x==0|| x==1){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Blanc")){
                if(plateau[x+2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Blanc")){
                if(plateau[x+2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,1);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
            }else if(x==8||x==9){
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Blanc")){
                if(plateau[x-2][y+2]==null){
                       Coup cad=new Coup(temp,1,1,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Blanc")){
                if(plateau[x-2][y-2]==null){
                       Coup cad=new Coup(temp,1,0,0);
                       jouerCoup(cad);
                       cad.setNext(attaquePionNoir(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                    }
                }
            }
        }
    List<Coup> resultat= gardeLesMax(res);
    if(resultat!=null)return resultat;
    else return null; 
    }
    
    private List<Coup> gardeLesMax(List<Coup> cp){              //Garde uniquement les coups les plus long 
          List<Coup> resultat= new ArrayList<Coup>();  
        if(cp.size()>0){
           int max=cp.get(0).getTaille();
          for (Coup c: cp){
          if(c.getTaille()>max)max=c.getTaille();
          } 
          for(Coup c:cp){
              if(c.getTaille()>=max)resultat.add(c);
          }
          return resultat;
       }
       return null;
        
        
    }
     
    
    //Fonction évaluation qui ne prend que des coups de Type deplacement
    
    public int evaluation(Coup coup){  //Deplacement sur les bords=2; Protéger un pion=4; attaquer un pion=5; deplacement normal=1; Faire une dame=10 ;
       int coul;
       if(coup.getPion().getCouleur().equals("Blanc"))coul=0;
       else coul=1;
       
       jouerCoup(coup);
       int y=coup.getPion().getPosY();
       int x=coup.getPion().getPosX();
        
        if(coul==0) {
            if(x==9){dejouerCoup(coup);return 10;}//Si le déplacement engendre une dame
            if(attaquePionBlanc(coup.getPion())!=null){dejouerCoup(coup);return 5;} //Au prochain coup on peut manger un pion
            if(x<8 ){
                if(y<8){
                    if(plateau[x+1][y+1]!=null){                                    //Le pion en protège un autre
                        if(plateau[x+1][y+1].getCouleur().equals("Blanc")){
                            if(plateau[x+2][y+2]!=null){
                                if(plateau[x+2][y+2].getCouleur().equals("Noir")){
                                    dejouerCoup(coup);
                                    return 4;
                                }
                            }       
                        }
                    }
                }
                if(y>2){
                    if(plateau[x+1][y-1]!=null){                                    
                        if(plateau[x+1][y-1].getCouleur().equals("Blanc")){
                            if(plateau[x+2][y-2]!=null){
                                if(plateau[x+2][y-2].getCouleur().equals("Noir")){
                                    dejouerCoup(coup);
                                    return 4;
                                }
                            }       
                        }
                    }
                }
            }
        } 
        else{
            if(x==0){dejouerCoup(coup);return 10; }
            if(attaquePionNoir(coup.getPion())!=null){dejouerCoup(coup);return 5;}
            if(x>2){
                if(y<8){
                    if(plateau[x-1][y+1]!=null){                                    
                        if(plateau[x-1][y+1].getCouleur().equals("Noir")){
                            if(plateau[x-2][y+2]!=null){
                                if(plateau[x-2][y+2].getCouleur().equals("Blanc")){
                                    dejouerCoup(coup);
                                    return 4;
                                }
                            }       
                        }
                    }
                }if(y>2){
                    if(plateau[x-1][y-1]!=null){                                    
                        if(plateau[x-1][y-1].getCouleur().equals("Noir")){
                            if(plateau[x-2][y-2]!=null){
                                if(plateau[x-2][y-2].getCouleur().equals("Blanc")){
                                    dejouerCoup(coup);
                                    return 4;
                                }
                            }       
                        }
                    }
                }
            }
        }
            
        if(y==0 || y==9){dejouerCoup(coup); return 2; } 
            
        dejouerCoup(coup);
        return 1;
    }
     
     //Getter
     public Pion getPion(int x, int y ){
         return plateau[x][y];
     }
     
     // Méthodes pour les tests
     
     public void placerPion(int couleur,int x, int y){  //0-> Blanc et 1-> Noir
         plateau[x][y]=new Pion("Blanc",x,y);
         if(couleur==1){
             plateau[x][y].setCouleur("Noir");
         }
     }
     
     public void supprimerPion(int x, int y ){
         plateau[x][y]=null;
     }
     
     public void viderPlateau(){
         for (int i=0;i<10;i++){
             for(int j=0;j<10;j++){
                 plateau[i][j]=null;
             }
         }
     }

     
     
     //Methodes pour les dames
     
     public List<Coup> deplacementDameBlanche(){        //Pour chaque dame on regarde dans toutes les directions où elles peuvent aller et la distance max de ce déplacement
         int dist;
         List<Coup> dep=new ArrayList<Coup>();
         for(int i=0;i<9;i++){
             for(int j=0;j<9;j++){
                 if(plateau[i][j]!=null){
                 Pion p =plateau[i][j];
                 if(p.getDame()==true && p.getCouleur().equals("Blanc")){
                     dist=deplacementDroitAvantDame(p);
                     if(dist!=0)dep.add(new Coup(p,0,1,0,dist));
                     dist=deplacementGaucheAvantDame(p);
                     if(dist!=0)dep.add(new Coup(p,0,0,0,dist));
                     dist=deplacementDroitArriereDame(p);
                     if(dist!=0)dep.add(new Coup(p,0,1,1,dist));
                     dist=deplacementGaucheArriereDame(p);
                     if(dist!=0)dep.add(new Coup(p,0,0,1,dist));
                    }
                }
             }
         }
         return dep;
     }
     
     public List<Coup> deplacementDameNoir(){       //Pareil que les blancs
         int dist;
         List<Coup> dep=new ArrayList<Coup>();
         for(int i=0;i<9;i++){
             for(int j=0;j<9;j++){
                 if(plateau[i][j]!=null){
                 Pion p =plateau[i][j];
                 if(p.getDame()==true && p.getCouleur().equals("Noir")){
                     dist=deplacementDroitArriereDame(p);    //Arriere avec un pion noir devient un coup en sens=1
                     if(dist!=0)dep.add(new Coup(p,0,1,0,dist));
                     dist=deplacementGaucheArriereDame(p);
                     if(dist!=0)dep.add(new Coup(p,0,0,0,dist));
                     dist=deplacementDroitAvantDame(p);
                     if(dist!=0)dep.add(new Coup(p,0,1,0,dist));
                     dist=deplacementGaucheAvantDame(p);
                     if(dist!=0)dep.add(new Coup(p,0,0,0,dist));
                     
                    }
                 }
             }
         }
         return dep;
     }
          
     public int deplacementDroitAvantDame(Pion p){
         
            int x=p.getPosX()+1;
            int y=p.getPosY()+1;
            int dist=0;
            while(y<=9&&x<=9&&plateau[x][y]==null){
                dist+=1; 
                x+=1;
                y+=1;
            }
            return dist;
        }
     
     public int deplacementDroitArriereDame(Pion p){
            int x=p.getPosX()-1;
            int y=p.getPosY()+1;
            int dist=0;
            while(y<=9&&x>=0&&plateau[x][y]==null){
                dist+=1; 
                x-=1;
                y+=1;
            }
            return dist;
        }
     
     public int deplacementGaucheAvantDame(Pion p){
        
             int x=p.getPosX()+1;
             int y=p.getPosY()-1;
             int dist=0;
             while(y>=0&&x<=9&&plateau[x][y]==null){
                 dist+=1; 
                 x+=1;
                 y-=1;
            }
             return dist;  
         }
     
     public int deplacementGaucheArriereDame(Pion p){
            int x=p.getPosX()-1;
            int y=p.getPosY()-1;
            int dist=0;
            while(y>=0&&x>=0&&plateau[x][y]==null){
                dist+=1; 
                x-=1;
                y-=1;
           }
            return dist;  
        }
        
    
      //Pour chaque dame, on regarde si un pion adverse se trouve sur une de ses diagonales. Puis comme pour les pion, on ne garde ques les coups mangeant le plus de pions
        private List<Coup> attaquePossibleDameBlanche() {
         List<Coup> moves=new ArrayList<Coup>();

           for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (plateau[i][j] != null) {
                    Pion temp = plateau[i][j];
                    if ( temp.getDame()==true && temp.getCouleur().equals("Blanc")) {
                        if (j == 0 || j == 1) {
                            if (i < 8 && i > 1) {     // Si le pion est sur le bord de gauche alors uniquement déplacement sur la droite
                            int tempi =i;
                            int tempj =j;
                             while(i<=8&&j<=8&&plateau[i][j]==null){
                 
                                 i+=1;
                                 j+=1;
                                                                   }
            if (plateau[i+1][j+1]!=null && plateau[i+1][j+1].getCouleur().equals("Noir"))
            { List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                     }
                            i=tempi;
                            j=tempj;
           while(i>=1&&j<=8&&plateau[i][j]==null){
                 
                                 i-=1;
                                 j+=1;
                                                                   }
            if (plateau[i-1][j+1]!=null && plateau[i-1][j+1].getCouleur().equals("Noir"))
                                {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } }
                            else if (i==8 || i==9) {  // Uniquement manger vers le bas
                                if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==1 || i==0) {  // Uniquement vers le haut
                                if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        } else if (j == 8 || j == 9) {   // Si le pion est sur le bord de droite alors uniquement déplacement sur la gauche
                            if (i < 8 && i > 1) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==8 || i==9) {  // Uniquement manger vers le bas
                                if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==0||i==1) {  // Uniquement vers le haut
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        } else if (j >1 && j < 8) { //Si le pion n'est pas sur les bords
                            if (i > 1 && i < 8) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i == 0 || i == 1) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i == 8 || i == 9) {
                                if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Noir")) {
                                    List<Coup> tmp = attaqueDameBlanche(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        List<Coup> resultat = gardeLesMax(moves);
        if (resultat != null) {
            return resultat;
        } else {
            return null;
        }
    }    
        
        private List<Coup> attaquePossibleDameNoir(){
            
         List<Coup> moves=new ArrayList<Coup>();

           for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (plateau[i][j] != null) {
                    Pion temp = plateau[i][j];
                    if ( temp.getDame()==true && temp.getCouleur().equals("Noir")) {
                        if (j == 0 || j == 1) {
                            if (i < 8 && i > 1) {     // Si le pion est sur le bord de gauche alors uniquement déplacement sur la droite
                            int tempi =i;
                            int tempj =j;
                             while(i<=8&&j<=8&&plateau[i][j]==null){
                 
                                 i+=1;
                                 j+=1;
                                                                   }
            if (plateau[i+1][j+1]!=null && plateau[i+1][j+1].getCouleur().equals("Blanc"))
            {List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                     }
                            i=tempi;
                            j=tempj;
           while(i>=1&&j<=8&&plateau[i][j]==null){
                 
                                 i-=1;
                                 j+=1;
                                                                   }
            if (plateau[i-1][j+1]!=null && plateau[i-1][j+1].getCouleur().equals("Blanc"))
                                {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } }
                            else if (i==8 || i==9) {  // Uniquement manger vers le bas
                                if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==1 || i==0) {  // Uniquement vers le haut
                                if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        } else if (j == 8 || j == 9) {   // Si le pion est sur le bord de droite alors uniquement déplacement sur la gauche
                            if (i < 8 && i > 1) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==8 || i==9) {  // Uniquement manger vers le bas
                                if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i==0||i==1) {  // Uniquement vers le haut
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        } else if (j >1 && j < 8) { //Si le pion n'est pas sur les bords
                            if (i > 1 && i < 8) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i == 0 || i == 1) {
                                if (plateau[i + 1][j - 1] != null && plateau[i + 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i + 1][j + 1] != null && plateau[i + 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            } else if (i == 8 || i == 9) {
                                if (plateau[i - 1][j + 1] != null && plateau[i - 1][j + 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                } else if (plateau[i - 1][j - 1] != null && plateau[i - 1][j - 1].getCouleur().equals("Blanc")) {
                                    List<Coup> tmp = attaqueDameNoire(temp);
                                    if (tmp != null) {
                                        for (Coup c : tmp) {
                                            moves.add(c);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        List<Coup> resultat = gardeLesMax(moves);
        if (resultat != null) {
            return resultat;
        } else {
            return null;
        }
    }            
       
//Pour chaque dame, On la deplace jusqu'au pion s'il est mangeable. Puis on test pour chaque case possible derrière ce pion si on peut en manger d'autre.
        
        //Ces fonctions ne sont pas finies
        private List<Coup> attaqueDameBlanche(Pion temp){
            
             int x=temp.getPosX();
        int y=temp.getPosY();
       
        List<Coup> res= new ArrayList<Coup>();
        
          int dist=2;
          if(y==0|| y==1){
            if(x<8 && x>1){     // Si le pion est sur le bord de gauche alors uniquement déplacement sur la droite
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Noir")){
                  
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Noir")){
                while(x-dist>0 && y+dist<10 && plateau[x-dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist+=1;
                   }
              }
            }else if(x>7){  // Uniquement manger vers le bas
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Noir")){
                while(x-dist>0 && y+dist<10 && plateau[x-dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist+=1;
                   }
              }
            }else if(x<2){  // Uniquement vers le haut à droite
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Noir")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
            }
          }else if(y==8 || y==9){   // Si le pion est sur le bord de droite alors uniquement déplacement sur la gauche
            if(x<8 && x>1){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Noir")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Noir")){
               while(x-dist>0 && y-dist>0 && plateau[x-dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
            }else if(x>7){  // Uniquement manger vers le bas à gauche
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Noir")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
            }else if(x<2){  // Uniquement vers le haut à gauche
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Noir")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
            }
          }else if(y>1 && y<8){ //Si le pion n'est pas sur les bords
            if(x>1 && x<8){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Noir")){
              while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Noir")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Noir")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Noir")){
               while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
            }else if(x==0|| x==1){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Noir")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Noir")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
            }else if(x==8||x==9){
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Noir")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Noir")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist);
                       jouerCoup(cad,dist);
                       cad.setNext(attaqueDameBlanche(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad,dist);
                       dist +=1;
                   }
                }
            }
        }
    List<Coup> resultat= gardeLesMax(res);
    if(resultat!=null)return resultat;
    else return null;
    }
        private List<Coup> attaqueDameNoire(Pion temp){
            
             int x=temp.getPosX();
        int y=temp.getPosY();
       
        List<Coup> res= new ArrayList<Coup>();
        
          int dist=2;
          if(y==0|| y==1){
            if(x<8 && x>1){     // Si le pion est sur le bord de gauche alors uniquement déplacement sur la droite
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Blanc")){
                  
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Blanc")){
                while(x-dist>0 && y+dist<10 && plateau[x-dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist+=1;
                   }
              }
            }else if(x>7){  // Uniquement manger vers le bas
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Blanc")){
                while(x-dist>0 && y+dist<10 && plateau[x-dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist+=1;
                   }
              }
            }else if(x<2){  // Uniquement vers le haut à droite
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
            }
          }else if(y==8 || y==9){   // Si le pion est sur le bord de droite alors uniquement déplacement sur la gauche
            if(x<8 && x>1){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Blanc")){
               while(x-dist>0 && y-dist>0 && plateau[x-dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
            }else if(x>7){  // Uniquement manger vers le bas à gauche
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
            }else if(x<2){  // Uniquement vers le haut à gauche
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
            }
          }else if(y>1 && y<8){ //Si le pion n'est pas sur les bords
            if(x>1 && x<8){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Blanc")){
              while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Blanc")){
               while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
            }else if(x==0|| x==1){
              if(plateau[x+1][y-1]!=null && plateau[x+1][y-1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
              if(plateau[x+1][y+1]!=null && plateau[x+1][y+1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,1,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
            }else if(x==8||x==9){
              if(plateau[x-1][y+1]!=null && plateau[x-1][y+1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y+dist<10 && plateau[x+dist][y+dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
              }
              if(plateau[x-1][y-1]!=null && plateau[x-1][y-1].getCouleur().equals("Blanc")){
                while(x+dist<10 && y-dist>0 && plateau[x+dist][y-dist]==null){
                       Coup cad=new Coup(temp,1,1,0,dist-1);
                       jouerCoup(cad);
                       cad.setNext(attaqueDameNoire(cad.getPion()));
                       res.add(cad);
                       dejouerCoup(cad);
                       dist +=1;
                   }
                }
            }
        }
    List<Coup> resultat= gardeLesMax(res);
    if(resultat!=null)return resultat;
    else return null;
    }
    
}















