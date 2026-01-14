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

Pour lancer le jeu, importez l'archive ijava, compilez le fichiez Main.java puis executez le fichier Main.class à l'aide des commandes :

    ijava compile src/Main.java
    ijava execute src/Main

Détails fichier :

-les fichiers txt et csv sont tous dans le dossier ressources, à des endroits que nous pensons logiques (exemple : le dossier ascii art monstre dans le dossier entité pour les sprits des monstres)

-les fichiers java et class sont dans le dossier src

-des captures d'écran sont dans le dossier shots pour avoir une preview de ce à quoi le jeu ressemble

Arborescence:
```
Sae 1.02/
│
├── ressources/
│   │
│   ├── utilisateur/
|   |   ├── inventaire.csv
│   │   ├── objet.csv
│   │   └── sauvegarde.csv
│   │
│   ├── entité/
│   │   ├── monstre.csv
│   │   ├── desc_boss.csv
│   │   │
│   │   ├── Ascii art monstre/
│   │   │   ├── Roi_fantome.txt
│   │   │   ├── bebe_dragon.txt
│   │   │   ├── dragon.txt
│   │   │   ├── fantome.txt
│   │   │   ├── loup.txt
│   │   │   ├── ogre.txt
│   │   │   └── sorciere.txt
│   │   │
│   │   ├── Ascii art pnj/
│   │   │   ├── directeur.txt
│   │   │   ├── elf.txt
│   │   │   ├── gardien.txt
│   │   │   ├── gnome.txt
│   │   │   └── merlin.txt
│   │   │
│   │   ├── Ascii misc/
│   │   │   ├── mort_joueur.txt
│   │   │   ├── title.txt
│   │   │   ├── vendeur.txt
│   │   │   └── victoire.txt
│   │   │
│   │   └── option pnj/
│   │       └── quete.csv
│   │
│   ├── systeme de magie/
│   │   ├── possibilite attaque.csv
│   │   └── question.csv
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
│   ├── Boss Roi_fantome.png
│   ├── Combat Gagner.png
│   ├── Vendre.png
│   ├── acheter potion.png
│   ├── attaquer.png
│   ├── boss room non.png
│   ├── boss room oui.png
│   ├── inventaire.png
│   ├── menu déplacement.png
│   ├── monstre.png
│   ├── mort.png
│   ├── pnj interaction.png
│   └── utiliser objet.png
│
└── README.md
```
