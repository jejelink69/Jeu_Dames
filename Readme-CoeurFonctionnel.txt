Mode d’emploi –Test Cœur Fonctionnel : 

On peut lancer trois types de partie : 
-	Joueur contre IA -> avec en paramètre la « difficulté de l’IA
-	IA contre IA - > Avec en paramètre un plateau et les difficultés des IAs 
	o	Pour faire jouer les IAs il faut entrer un INT au clavier, celui-ci n’a aucune importance il sert juste à faire des pauses pour voir les actions réalisées.
-	Joueur contre joueur

Les parties peuvent être lancées depuis la classe Partie.

Pour jouer en tant que joueur, la liste des coups s’affiche avec : 
-	En X : la ligne
-	En y : la colonne
-	Le type : 0-> Déplacement 1 -> Manger
-	La direction : 0-> Gauche 1-> Droite (elle est indépendante de la couleur du pion, gauche va vers la colonne 0)
-	Le sens : 0-> un coup normale 1-> un coup en arrière (Cet attribut est relatif à la couleur du pion, si le pion est noir 0 ira vers la ligne 0 et si le pion est blanc 0 ira vers la ligne 9)
Comme cette méthode avec l’affichage des coups dans une liste est simplement la pour tester 
visuellement le cœur fonctionnel, aucune gestion d’erreur dans le choix  du coup n’a été faite (Type, nombre pas dans le tableau …).
