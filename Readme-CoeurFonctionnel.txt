Mode d�emploi �Test C�ur Fonctionnel : 

On peut lancer trois types de partie : 
-	Joueur contre IA -> avec en param�tre la � difficult� de l�IA
-	IA contre IA - > Avec en param�tre un plateau et les difficult�s des IAs 
	o	Pour faire jouer les IAs il faut entrer un INT au clavier, celui-ci n�a aucune importance il sert juste � faire des pauses pour voir les actions r�alis�es.
-	Joueur contre joueur

Les parties peuvent �tre lanc�es depuis la classe Partie.

Pour jouer en tant que joueur, la liste des coups s�affiche avec : 
-	En X : la ligne
-	En y : la colonne
-	Le type : 0-> D�placement 1 -> Manger
-	La direction : 0-> Gauche 1-> Droite (elle est ind�pendante de la couleur du pion, gauche va vers la colonne 0)
-	Le sens : 0-> un coup normale 1-> un coup en arri�re (Cet attribut est relatif � la couleur du pion, si le pion est noir 0 ira vers la ligne 0 et si le pion est blanc 0 ira vers la ligne 9)
Comme cette m�thode avec l�affichage des coups dans une liste est simplement la pour tester 
visuellement le c�ur fonctionnel, aucune gestion d�erreur dans le choix  du coup n�a �t� faite (Type, nombre pas dans le tableau �).
