
import extensions.File;
import extensions.CSVFile;

class Main extends Program {

    //////////////////////////////////////////////////////////////////////////
// VARIABLES & LANCEMENT
//////////////////////////////////////////////////////////////////////////

    User personne = newUser();
    final String RESET = "\033c\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    void algorithm() {
        jeux();
    }

    void jeux() {
        print(RESET);
        afficherMisc("title");

        println("════════════════════════════════════");
        println("          MENU PRINCIPAL            ");
        println("════════════════════════════════════\n");
        println("[0] Nouvelle Partie");
        println("[1] Continuer");
        println("[2] Crédits\n");

        int rep = saisie(2);

        if (rep == 0) {
            debutjeux();
            NouvellePartie();
            menu();
        } else if (rep == 1) {
            menu();
        } else if (rep == 2) {
            Credit();
        }
    }

    void Credit() {
        println("\n════════════════ CRÉDITS ════════════════\n");

        File credit = newFile("credit.txt");
        while (ready(credit)) {
            println("   " + readLine(credit));
            sleep(1000);
        }

        println("\n════════════════════════════════════════\n");
        jeux();
    }

    void debutjeux() {
        File credit = newFile("presentation.txt");
        while (ready(credit)) {
            String ligne = readLine(credit);
            for (int i = 0; i < length(ligne); i++) {
                print(charAt(ligne, i));
                sleep(50);
            }
            println("");
        }

    }

    void NouvellePartie() {
        String[][] save = new String[][]{
            new String[]{ "Joueur", "" },
            new String[]{ "100", "100" },
            new String[]{ "1", "" },
            new String[]{ "default", "0" },
            new String[]{ "La route", "" }};
        String[][] save2 = new String[][]{
            new String[]{" "," "},
            new String[]{" "," "},
            new String[]{" "," "},
            new String[]{" "," "},
            new String[]{" "," "}};
        String[][] save3 = new String[][]{
            new String[]{" "}};
        saveCSV(save, "jeux/utilisateur/sauvegarde.csv", ';');
        saveCSV(save2, "jeux/utilisateur/user.csv", ';');
        saveCSV(save3, "jeux/utilisateur/inventaire.csv", ';');
        CSVFile quete = loadCSV("jeux/entité/option pnj/quete.csv", ';');
        String[][] rep = new String[rowCount(quete)][columnCount(quete)];
        int ligne = 0;
        for (int cptligne = 0; cptligne < rowCount(quete); cptligne++) {
            for (int cptcolonne = 0; cptcolonne < columnCount(quete); cptcolonne++) {
                if (cptcolonne == 5) {
                    rep[cptligne][cptcolonne] = "false";
                } else {
                    rep[cptligne][cptcolonne] = getCell(quete, cptligne, cptcolonne);
                }
            }
        }
        saveCSV(rep, "jeux/entité/option pnj/quete.csv", ';');
        personne = newUser();
    }

    //////////////////////////////////////////////////////////////////////////
// CHARGEMENT DES IMAGES ASCII
//////////////////////////////////////////////////////////////////////////

    String loadImage(String chemin) {
        String resultat = "";
        File current = newFile(chemin);

        while (ready(current)) {
            resultat = resultat + readLine(current) + "\n";
        }
        return resultat;
    }

    //////////////////////////////////////////////////////////////////////////
// FONCTIONS SHORTCUT
//////////////////////////////////////////////////////////////////////////

    String loadSpritePNJ(String pnj) {
        return loadImage("jeux/entité/Ascii art pnj/" + pnj + ".txt");
    }

    String loadSpriteMonstre(String monstre) {
        return loadImage("jeux/entité/Ascii art monstre/" + monstre + ".txt");
    }

    String loadSpriteMisc(String misc) {
        return loadImage("jeux/entité/Ascii misc/" + misc + ".txt");
    }

    String loadDialoguePNJ(String pnj, boolean quete) {
        CSVFile current = loadCSV("jeux/entité/option pnj/dialogue.csv", ';');
        int ligne = 0;

        while (!equals(getCell(current, ligne, 0), pnj)) {
            ligne++;
        }

        if (quete) {
            return getCell(current, ligne, 1);
        }
        return getCell(current, ligne, 2);
    }

    void afficherMisc(String image) {
        println(loadSpriteMisc(image));
    }

    void afficherSpritePNJ(String pnj) {
        println(loadSpritePNJ(pnj));
    }

    void afficherSpriteMonstre(String monstre) {
        println(loadSpriteMonstre(monstre));
    }

    //////////////////////////////////////////////////////////////////////////
// zone de jeux
//////////////////////////////////////////////////////////////////////////
    
    void menu() {
        CSVFile current = loadCSV("jeux/entité/option pnj/quete.csv", ';');
        boolean ingame = true;
        while (ingame) {
            print(RESET);
            if (personne.level >= rowCount(current)) {
                ingame = false;
                println("Merci d'avoir jouer a notre magnifique jeux qui vaut surement un 20/20");
                Credit();
            } else {
                CSVFile lieu = loadCSV("jeux/deplacement.csv", ';');
                int cpt = 0;
                while (!equals(getCell(lieu, cpt, 0), personne.currentlieu)) {
                    cpt++;
                }
                int nbelements = (columnCount(lieu, cpt) / 2);
                println("Action(s) :\n");
                for (int i = 0; i < nbelements; i++) {
                    print("[" + i + "] | ");
                    if (equals(getCell(lieu, cpt, ((i + 1) * 2) - 1),"trap")){
                        print("lieu");
                    }else{ 
                        print(getCell(lieu, cpt, ((i + 1) * 2) - 1));
                    }
                    print(" - " + getCell(lieu, cpt, ((i + 1) * 2)));
                    if(i==0 && !equals(personne.currentlieu,"La route")){
                        print(" (zone précédente)\n");
                    }
                    println("");
                }
                println("\n[" + nbelements + "] | inventaire");
                println("\n[" + (nbelements + 1) + "] | Sauvegarder et Quitter");

                println("");
                int action = saisie(nbelements + 1);
                if (action == nbelements) {
                    afficherinventaire();
                    waitingForPlayerActivity();
                } else if (action == nbelements + 1) {
                    sauvegarder();
                    ingame = false;
                    //"else if" pour l'inventaire a ajouter
                } else if (equals(getCell(lieu, cpt, ((action + 1) * 2) - 1),"trap")) {
                    print(RESET);
                    println("Mince, il semble que vous ayez marché sur un piège en entrant, je n'essayerais de revenir ici si j'étais vous !");
                    waitingForPlayerActivity();
                    personne.currentlieu = getCell(lieu, cpt, (action + 1) * 2);
                } else if (equals(getCell(lieu, cpt, ((action + 1) * 2) - 1), "lieu")) {
                    personne.currentlieu = getCell(lieu, cpt, (action + 1) * 2);
                } else if (equals(getCell(lieu, cpt, ((action + 1) * 2) - 1), "pnj")) {
                    loadPnj(getCell(lieu, cpt, (action + 1) * 2));
                } else if (equals(getCell(lieu, cpt, ((action + 1) * 2) - 1), "monstre")) {
                    if (combat(getCell(lieu, cpt, (action + 1) * 2))){
                        ingame = false;
                    }
                    waitingForPlayerActivity();
                } else {
                    println("erreur");
                }
                println("");
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////
// SAISIES UTILISATEUR
//////////////////////////////////////////////////////////////////////////

    int saisie(int possibilite) {
        String resultat;
        boolean trigger = true;

        do {
            if(equals(personne.nom,"Joueur")){
                print("<"+personne.nom+"> : ");
            }
            resultat = readString();

            if ((length(resultat) != 1)
                    || (charAt(resultat, 0) < '0'
                    || charAt(resultat, 0) > (possibilite + '0'))) {

                println("Il semble que votre langue ait fourché");
            } else {
                trigger = false;
            }
        } while (trigger);

        return charAt(resultat, 0) - '0';
    }

    String saisieTexte() {
        String resultat;
        boolean trigger;

        do {
            trigger = false;
            resultat = readString();

            if (resultat == "") {
                trigger = true;
                println("Il semble que votre langue ait fourché");
            }
        } while (trigger);

        return resultat;
    }

    void waitingForPlayerActivity(){ 
       println("\nAppuyez sur la touche Entrée pour continuer");
       readString();
    }

    //////////////////////////////////////////////////////////////////////////
// SYSTEME DE QUESTIONS
//////////////////////////////////////////////////////////////////////////

    String[] loadQuestion() {
        String[] resultat = new String[6];
        CSVFile current = loadCSV("jeux/systeme de magie/question.csv", ';');

        int nbligne = rowCount(current);
        int ligne = (int) random(0, nbligne-1);

        resultat[0] = getCell(current, ligne, 0);
        resultat[5] = getCell(current, ligne, 1);

        int pos;
        for (int i = 0; i < 4; i++) {
            do {
                pos = (int) random(1, 5);
            } while (resultat[pos] != null);

            resultat[pos] = getCell(current, ligne, 1 + i);
        }
        return resultat;
    }

    //////////////////////////////////////////////////////////////////////////
// PNJ
//////////////////////////////////////////////////////////////////////////

    void loadPnj(String pnj) {
        afficherSpritePNJ(pnj);

        CSVFile current = loadCSV("jeux/entité/option pnj/quete.csv", ';');
        int ligne = 0;
        String rep = "";
        boolean encours = true;
        while (!(equals(getCell(current, ligne, 0), pnj))) {
            ligne++;
        }

        int colonne = 4;

        while (encours) {
            if (ligne == (rowCount(current) - 1)) {

                loadDialoguePNJ(pnj, false);
                encours = false;
            } else if (!(stringToInt(getCell(current, ligne, colonne)) == personne.level)) {
                ligne++;
            } else if (equals(getCell(current, ligne, 5), "false")) {
                println("\n════════════════════════════════════");
                println(" " + pnj + " vous parle ");
                println("════════════════════════════════════");
                loadDialoguePNJ(pnj, true);
                loadquete();
                println(getCell(current, ligne, 1) + " " + getCell(current, ligne, 3) + " " + getCell(current, ligne, 2));
                println("════════════════════════════════════\n");
                encours = false;
            } else if (equals(getCell(current, ligne, 5), "true")) {
                if (verifierquete(ligne)) {
                    println("\n════════════════════════════════════");
                    println(" " + pnj + " vous parle ");
                    println("════════════════════════════════════");
                    println("merci d'avoir accompli la mission vous trouverez une nouvelle mission chez" + getCell(current, ligne, 6));
                    println("Je vous offre cette potion de soin pour votre bravour.");
                    ajouterobjet("potion de soins");
                    println("════════════════════════════════════\n");

                    encours = false;
                } else {
                    println("\n════════════════════════════════════");
                    println(" " + pnj + " vous parle ");
                    println("════════════════════════════════════");
                    println("Petit/Petite chenapan retourne travailler");
                    println("════════════════════════════════════\n");
                    println(personne.quete_kill);
                    encours = false;
                }
            }
        }
        waitingForPlayerActivity();
    }

    //////////////////////////////////////////////////////////////////////////
// AFFICHAGE QUESTIONS
//////////////////////////////////////////////////////////////////////////

    void afficherQuestion(String[] question) {
        println("══════════════════════════════════════");
        println(" QUESTION ");
        println("══════════════════════════════════════");
        println(question[0] + "\n");

        for (int i = 0; i < 4; i++) {
            println(" [" + i + "]  | " + question[i + 1]);
        }

        println("══════════════════════════════════════\n");
    }

    boolean question() {
        String[] question = loadQuestion();
        afficherQuestion(question);

        int reponse = saisie(4);

        if (equals(question[reponse + 1], question[5])) {
            return true;
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////
// UTILISATEUR & SAUVEGARDE
//////////////////////////////////////////////////////////////////////////

    User newUser() {
        User utilisateur = new User();
        CSVFile current = loadCSV("jeux/utilisateur/sauvegarde.csv", ';');

        if (equals(getCell(current, 0, 0), " ")) {
            utilisateur.nom = askNom();
        } else {
            utilisateur.nom = getCell(current, 0, 0);
        }

        if (equals(getCell(current, 3, 0), " ")) {
            utilisateur.quete_cible = " ";
            utilisateur.quete_kill = 0;
        } else {
            utilisateur.quete_cible = getCell(current, 3, 0);
            utilisateur.quete_kill = stringToInt(getCell(current, 3, 1));
        }

        utilisateur.level = stringToInt(getCell(current, 2, 0));
        utilisateur.pv = stringToInt(getCell(current, 1, 0));
        utilisateur.pv_max = stringToInt(getCell(current, 1, 1));
        utilisateur.currentlieu = getCell(current, 4, 0);
        return utilisateur;
    }

    String askNom() {
        println("Vous me semblez nouveau/nouvelle ici, je ne crois pas être familier avec votre nom, pourriez vous me le donner ?");
        return saisieTexte();
    }

    void sauvegarder() {
        String[][] save = new String[][]{
            new String[]{personne.nom, ""},
            new String[]{personne.pv + "", personne.pv_max + ""},
            new String[]{personne.level + "", ""},
            new String[]{personne.quete_cible, personne.quete_kill + ""},
            new String[]{personne.currentlieu + "", ""}
        };
        saveCSV(save, "jeux/utilisateur/sauvegarde.csv", ';');
    }

    //////////////////////////////////////////////////////////////////////////
// quete
//////////////////////////////////////////////////////////////////////////
void loadquete() {
        CSVFile quete = loadCSV("jeux/entité/option pnj/quete.csv", ';');
        personne.quete_kill = 0;
        String[][] rep = new String[rowCount(quete)][columnCount(quete)];
        int ligne = 0;
        for (int cptligne = 0; cptligne < rowCount(quete); cptligne++) {
            for (int cptcolonne = 0; cptcolonne < columnCount(quete); cptcolonne++) {
                if (cptcolonne == 5 && stringToInt(getCell(quete, cptligne, 4)) == personne.level) {
                    rep[cptligne][cptcolonne] = "true";
                    ligne = cptligne;
                    personne.quete_cible = getCell(quete, ligne, 2);
                    println(personne.quete_cible);
                } else {
                    rep[cptligne][cptcolonne] = getCell(quete, cptligne, cptcolonne);
                }
            }
        }
        saveCSV(rep, "jeux/entité/option pnj/quete.csv", ';');
    }

    boolean verifierquete(int ligne) {
        CSVFile current = loadCSV("jeux/entité/option pnj/quete.csv", ';');
        if (personne.quete_kill >= stringToInt(getCell(current,ligne , 3))) {
            return true;
        }
        return false;
    }

    void verifiermonstretuer(String nom) {
        if (equals(nom, personne.quete_cible)) {
            personne.quete_kill++;
        }
    }

    //////////////////////////////////////////////////////////////////////////
// MONSTRES
//////////////////////////////////////////////////////////////////////////

    Monstre newMonstre(String nom) {
        int ligne = 0;
        CSVFile current = loadCSV("jeux/entité/monstre.csv", ';');

        while (!equals(getCell(current, ligne, 0), nom)) {
            ligne++;
        }

        Monstre m = new Monstre();
        m.nom = nom;
        m.pvmax = (int) (stringToInt(getCell(current, ligne, 1)) * (1 + ((personne.level-1) * 0.2)));
        m.pv = m.pvmax;
        m.attaquenom = getCell(current, ligne, 2);
        m.attaquedegat = (int) (stringToInt(getCell(current, ligne, 3)) * (1 + ((personne.level-1) * 0.10)));
        m.esquive = stringToInt(getCell(current, ligne, 4));

        return m;
    }

    //////////////////////////////////////////////////////////////////////////
// inventaire
//////////////////////////////////////////////////////////////////////////
void afficherinventaire(){
    CSVFile current = loadCSV("jeux/utilisateur/inventaire.csv", ';');
    print(RESET);
    println("\n════════════ INVENTAIRE ════════════");

    if (rowCount(current) == 0){
        println("  (Inventaire vide)");
    } else {
        for (int ligne = 0; ligne < rowCount(current); ligne++){
            println("  [" + ligne + "] " + getCell(current, ligne, 0));
        }
    }

    println("════════════════════════════════════\n");
}


    void utiliserObjet(User nom, Monstre monstre) {
        CSVFile current = loadCSV("jeux/utilisateur/inventaire.csv", ';');
        afficherinventaire();
        if (rowCount(current) > 0) {
            int ligne = saisie(rowCount(current));
            if (equals(getCell(current, ligne, 1), "soins")) {
                nom.pv = nom.pv + stringToInt(getCell(current, ligne, 2));
                supprimerobjet(current, ligne);
                if (nom.pv > nom.pv_max) {
                    nom.pv = nom.pv_max;
                }
            } else {
                monstre.pv = monstre.pv - stringToInt(getCell(current, ligne, 2));
                supprimerobjet(current, ligne);
                if (monstre.pv < 0) {
                    monstre.pv = 0;
                }
            }
        } else {
            println("\n\n<Tips> : Faite plus de quete afin d'obtenir plus d'objet \n\n Le monstre malin, comme il est, en profite pour vous attaquer.");
        }

    }

    void supprimerobjet(CSVFile current, int ligne) {

        String[][] rep = new String[rowCount(current) - 1][columnCount(current)];

        int ind = 0;

        for (int cptligne = 0; cptligne < rowCount(current); cptligne++) {

            if (cptligne == ligne) {
                continue; // on saute la ligne à supprimer
            }

            for (int colonne = 0; colonne < columnCount(current); colonne++) {
                rep[ind][colonne] = getCell(current, cptligne, colonne);
            }

            ind++;
        }

        saveCSV(rep, "jeux/utilisateur/inventaire.csv", ';');
    }

    void ajouterobjet(String nom) {
        CSVFile current = loadCSV("jeux/utilisateur/objet.csv", ';');
        CSVFile inventaire = loadCSV("jeux/utilisateur/inventaire.csv", ';');
        int ligne = 0;
        String[][] rep = new String[rowCount(inventaire) + 1][columnCount(inventaire)];
        while (!equals(getCell(current, ligne, 0), nom)) {
            ligne++;
        }
        for (int cptligne = 0; cptligne < rowCount(inventaire); cptligne++) {
            for (int cptcolonne = 0; cptcolonne < columnCount(inventaire); cptcolonne++) {
                rep[cptligne][cptcolonne] = getCell(inventaire, cptligne, cptcolonne);
            }
        }
        for (int cptcolonne = 0; cptcolonne < columnCount(inventaire); cptcolonne++) {
            rep[rowCount(inventaire)][cptcolonne] = getCell(current, ligne, cptcolonne);
        }
        saveCSV(rep, "jeux/utilisateur/inventaire.csv");
    }

    //////////////////////////////////////////////////////////////////////////
// COMBAT
//////////////////////////////////////////////////////////////////////////

    String demanderAttaque(String[] attaquePossibles) {
        int page = 0;
        int nbPages = (length(attaquePossibles) + 8 - 1) / 8;
        while (true) {
            boolean trigger = false;
            int debut = page * 8;
            int end = debut + 8;
            if (end > length(attaquePossibles)) {
                end = length(attaquePossibles);
            }
            for (int i=debut;i<end;i++) {
                println("["+(i-debut)+"] | " + attaquePossibles[i]);
            }
            if (nbPages!=1){
                println("["+(end-debut)+"] | Suivant");
                println("["+(end-debut+1)+"] | Annuler\n");
                trigger = true;
            } else {
                println("\n["+(end-debut)+"] | Annuler\n");
            }
            int reponse;
            if (trigger) {
                reponse = saisie(end - debut + 1);
            } else {
                reponse = saisie(end - debut);
            }
            if (nbPages != 1) {
                if (reponse == end - debut + 1) {
                    return "annuler";
                } else if (reponse == end - debut) {
                    page++;
                    if (page * 8 >= length(attaquePossibles)) {
                        page = 0;
                    }
                }
            } else {
                if (reponse == end - debut) {
                    return "annuler";
                }
            }
            if (reponse >= 0 && reponse < end - debut) {
                println("7");
                return attaquePossibles[debut + reponse];
            }
        }
    }

//fonction à appeller facilement pour lancer l'action de l'attaque
    String attaque() {
        CSVFile current = loadCSV("jeux/systeme de magie/possibilite attaque.csv", ';');
        String[] attaquePossibles = new String[rowCount(current)];
        int cpt = 0;
        while (cpt < length(attaquePossibles) && stringToInt(getCell(current, cpt, 2)) <= personne.level) {
            cpt++;
        }
        String[] attaquePossiblesClean = new String[cpt];
        for (int i = 0; i < cpt; i++) {
            attaquePossiblesClean[i] = getCell(current, i, 0);
        }
        String choixAttaque = demanderAttaque(attaquePossiblesClean);
        return choixAttaque;
    }

    int degats(String attaque) {
        CSVFile current = loadCSV("jeux/systeme de magie/possibilite attaque.csv", ';');
        int cpt = -1;
        int resultat;
        do {
            cpt++;
            resultat = stringToInt(getCell(current, cpt, 1));
        } while (!equals(attaque, getCell(current, cpt, 0)));
        return resultat;
    }

    void afficherCurrentMonstre(Monstre currentMonstre){
        print(RESET);
        println("═══════════════ COMBAT ═══════════════");
        println(" Ennemi : " + currentMonstre.nom);
        println(" PV Ennemi : " + currentMonstre.pv + "/" + currentMonstre.pvmax);
        println("══════════════════════════════════════\n");
        afficherSpriteMonstre(currentMonstre.nom);
        println("══════════════════════════════════════\n");
        println("Vos PV : " + personne.pv + "/" + personne.pv_max + "\n");
    }

    boolean combat(String monstre) {
        Monstre currentMonstre = newMonstre(monstre);
        while (true) {
            boolean annuler = false;
            afficherCurrentMonstre(currentMonstre);
            println("[0] Attaquer");
            println("[1] Utiliser un objet\n");
            int reponse = saisie(1);
            if (reponse == 0) {
                afficherCurrentMonstre(currentMonstre);
                String attaqueChoisie = attaque();
                if (equals(attaqueChoisie, "annuler")) {
                    annuler = true;
                }
                if (!annuler){
                    afficherCurrentMonstre(currentMonstre);
                    boolean question = question();
                    int degats = degats(attaqueChoisie);
                    print(RESET);
                    if (!question) {
                        println("Mince, vous vous êtes trompé et blessé par la même occasion en vous infligeant "
                                + (int) (degats / 2) + " dégats...");
                        personne.pv = personne.pv - ((int) (degats / 2));
                    } else {
                        println("Bien joué, vous avez réussi à attaquer !\n");
                        int random = (int) ((random() * 100) + 1);

                        if (random <= currentMonstre.esquive) {
                            println("Cependant le/a " + currentMonstre.nom + " a réussi à esquiver !\n");
                        } else {
                            println("Vous avez infligé " + degats + " dégats !\n");
                            currentMonstre.pv = currentMonstre.pv - degats;
                        }
                    }
                }
            } else {
                utiliserObjet(personne, currentMonstre);
            }
            if (!annuler) {
                if (currentMonstre.pv > 0) {
                    println("le/a " + currentMonstre.nom + " a utilisé "
                            + currentMonstre.attaquenom + " et vous a infligé "
                            + currentMonstre.attaquedegat + " dégats !\n");

                    personne.pv = personne.pv - currentMonstre.attaquedegat;
                    waitingForPlayerActivity();
                } else {
                    print(RESET);
                    println("Bravo vous vous en sortez sain et sauf\n");
                    afficherMisc("victoire");
                    verifiermonstretuer(currentMonstre.nom);
                    println("Une lumiére magique descend et vous soigne de toute vos blessure");
                    personne.pv = personne.pv_max;
                    return false;
                }

                if (personne.pv <= 0) {
                    print(RESET);
                    println("Vous avez succombé\n");
                    afficherMisc("mort_joueur");
                    return true;
                }
            }
        }
    }

}
