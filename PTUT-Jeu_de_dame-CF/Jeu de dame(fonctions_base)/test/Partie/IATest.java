
package Partie;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class IATest {
    


    //on verifie le cas ou la profondeur est inférieur à zero 
    @Test
    public void testmax() {
    Plateau test = new Plateau();
    test.viderPlateau();
    test.placerPion(0,8,8); 
    Coup testc = new Coup (test.getPion(8,8),1,0,0);
    IA testia = new IA (test,0);
    int a = testia.max(testc,-1);
    assertTrue ("probleme fonction max avec aucune profondeur",a==10);

   
    
    }

    
 

  
    //on test quelques cas basique afin de voir si l'IA fait les bons déplacements 
    @Test
    public void testjouerIA() {
    Plateau test = new Plateau();
    test.viderPlateau();
    test.placerPion(0,3,1);
    test.placerPion(1,5,3);
    IA testia = new IA (test,0);
    testia.jouerIA(2);
    assertTrue ("probleme IA se met en position d'etre mangé",test.getPion(4, 0).getCouleur().equals("Blanc"));
    
    Plateau test2 = new Plateau();
    test2.viderPlateau();
    test2.placerPion(0,2,7);
    IA testia2 = new IA (test2,0);
    testia2.jouerIA(2);
    assertTrue ("probleme IA ne prefere pas aller sur les coté",test2.getPion(3,8).getCouleur().equals("Blanc"));
    }
    
}