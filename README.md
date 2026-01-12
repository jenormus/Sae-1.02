```
_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _

 ███████████                                █████                        ███   █████               
░░███░░░░░███                              ░░███                        ░░░   ░░███                
 ░███    ░███ ████████   ██████    ██████  ███████    ██████  ████████  ████  ███████    ██████    
 ░██████████ ░░███░░███ ░░░░░███  ███░░███░░░███░    ███░░███░░███░░███░░███ ░░░███░    ░░░░░███   
 ░███░░░░░░   ░███ ░░░   ███████ ░███████   ░███    ░███████  ░███ ░░░  ░███   ░███      ███████   
 ░███         ░███      ███░░███ ░███░░░    ░███ ███░███░░░   ░███      ░███   ░███ ███ ███░░███   
 █████        █████    ░░████████░░██████   ░░█████ ░░██████  █████     █████  ░░█████ ░░████████  
░░░░░        ░░░░░      ░░░░░░░░  ░░░░░░     ░░░░░   ░░░░░░  ░░░░░     ░░░░░    ░░░░░   ░░░░░░░░   

_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
```

Bienvenue sur Praeterita, un  un jeu de rôle narratif où l’histoire devient une arme. Dans un monde ancien menacé par des monstres extrêmement puissants, le joueur incarne un héros chargé de sauver le monde. Chaque combat et chaque exploration plongent le joueur au cœur des grandes périodes de l’Histoire. La magie de Praeterita ne se maîtrise pas par la force brute, mais par la connaissance : pour attaquer, le joueur doit répondre à des questions historiques. Plus le joueur complète des quêtes, plus il débloque des attaques toujours plus puissantes. Tout au long de l’aventure, des monstres et des boss légendaires se dressent sur sa route. Le joueur progresse en apprenant, en combattant et en gagnant des niveaux à la fin de chaque quête. Praeterita transforme la révision de l’Histoire en une aventure immersive et captivante. Chaque victoire récompense le savoir et la réflexion. Dans Praeterita, sauver le monde passe par la maîtrise du passé. 

Pour lancer le jeu, compilez le fichiez Main.java puis executez le fichier Main.class à l'aide des commandes :

    ijava compile src/Main.java
    ijava execute src/Main

Détails fichier :

-les fichiers txt et csv sont tous dans le dossier ressources, à des endroits que nous pensons logiques (exemple : le dossier ascii art monstre dans le dossier entité pour les sprits des monstres)

-les fichiers java et class sont dans le dossier src

-des captures d'écran sont dans le dossier shots pour avoir une preview de ce à quoi le jeu ressemble

Arborescence:

Sae 1.02/
│
├── ressources/
│   │
│   ├── utilisateur/
│   │   ├── sauvegarde.csv
│   │   ├── inventaire.csv
│   │   └── objet.csv
│   │
│   ├── entité/
│   │   ├── monstre.csv
│   │   ├── desc_boss.csv
│   │   │
│   │   ├── Ascii art monstre/
│   │   │   ├── loup.txt
│   │   │   ├── fantome.txt
│   │   │   └── dragon.txt
│   │   │
│   │   ├── Ascii art pnj/
│   │   │   ├── merlin.txt
│   │   │   ├── forgeron.txt
│   │   │   └── marchand.txt
│   │   │
│   │   ├── Ascii misc/
│   │   │   ├── title.txt
│   │   │   ├── victoire.txt
│   │   │   ├── mort_joueur.txt
│   │   │   └── vendeur.txt
│   │   │
│   │   └── option pnj/
│   │       └── quete.csv
│   │
│   ├── systeme de magie/
│   │   ├── question.csv
│   │   └── possibilite attaque.csv
│   │
│   ├── deplacement.csv
│   ├── credit.txt
│   └── presentation.txt
├── src/
│   ├── Main.java
│   ├── Main.class
│   ├── User.java
│   ├── User.class
│   ├── Monstre.java
│   └── Monstre.class
│
├── shots/
│   │
│   ├── attaquer.png
│   ├── acheter potion.png
│   ├── utiliser objet.png
│   ├── vendre.png
│   │
│   ├── inventaire.png
│   ├── menu déplacement.png
│   ├── pnj interaction.png
│   │
│   ├── monstre.png
│   ├── Boss Roi_fantome.png
│   │
│   ├── boss room oui.png
│   ├── boss room non.png
│   │
│   ├── Combat Gagner.png
│   └── mort.png
│
└── README.md
