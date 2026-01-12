
import extensions.File;
import extensions.CSVFile;

class Main extends Program {

    //////////////////////////////////////////////////////////////////////////
// VARIABLES & LANCEMENT
//////////////////////////////////////////////////////////////////////////

    User personne;
    final String RESET = "\033c\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    void algorithm() {
        jeux();
    }

    void jeux() {
        print(RESET);
        CSVFile current = loadCSV("../ressources/utilisateur/sauvegarde.csv", ';');
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
            if(equals(getCell(current,0,0),"Joueur")){
                print(RESET);
                println("Vous n'avez aucune sauvegarde vous allez donc recommencez :)");
                waitingForPlayerActivity();
                debutjeux();
                NouvellePartie();
                menu();
            }else{
                personne = newUser();
                menu();
            }
        } else if (rep == 2) {
            Credit();
        }
    }

    void Credit() {
        println("\n════════════════ CRÉDITS ════════════════\n");

        File credit = newFile("../ressources/credit.txt");
        while (ready(credit)) {
            println("   " + readLine(credit));
            sleep(1000);
        }

        println("\n════════════════════════════════════════\n");
        jeux();
    }

    void debutjeux() {
        File credit = newFile("../ressources/presentation.txt");
        print(RESET);
        while (ready(credit)) {
            String ligne = readLine(credit);
            for (int i = 0; i < length(ligne); i++) {
                print(charAt(ligne, i));
                sleep(50);
            }
            println("");
        }
        waitingForPlayerActivity();

    }

    void NouvellePartie() {
        String[][] save = new String[][]{
            new String[]{ "Joueur", "" },
            new String[]{ "100", "100" },
            new String[]{ "1", "" },
            new String[]{ "default", "0" },
            new String[]{ "La route", "" },
            new String[]{ "0", "" }
            };
        String[][] save2 = new String[][]{
            new String[]{" "}};
        saveCSV(save, "../ressources/utilisateur/sauvegarde.csv", ';');
        saveCSV(save2, "../ressources/utilisateur/inventaire.csv", ';');
        CSVFile quete = loadCSV("../ressources/entité/option pnj/quete.csv", ';');
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
        saveCSV(rep, "../ressources/entité/option pnj/quete.csv", ';');
        print(RESET);
        println("<Tips> : Ne vous appelez pas \"Joueur\" au peur de consequence inatendue\n");
        personne = newUser();
        print(RESET);
        println("\n<Tips> : Allez parler à merlin dans le Hall de la guild de magie en premier");
        waitingForPlayerActivity();
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
        return loadImage("../ressources/entité/Ascii art pnj/" + pnj + ".txt");
    }

    String loadSpriteMonstre(String monstre) {
        return loadImage("../ressources/entité/Ascii art monstre/" + monstre + ".txt");
    }

    String loadSpriteMisc(String misc) {
        return loadImage("../ressources/entité/Ascii misc/" + misc + ".txt");
    }

    void afficherMisc(String image) {
        println(loadSpriteMisc(image));
    }

    void afficherSpritePNJ(String pnj) {
        print(RESET);
        println(loadSpritePNJ(pnj));
    }

    void afficherSpriteMonstre(String monstre) {
        println(loadSpriteMonstre(monstre));
    }

    void afficherLentement(String texte, int puissance){
        for (int i=0;i<length(texte);i++){
            print(charAt(texte,i));
            sleep(puissance);
        }
        println("");
    }

    //////////////////////////////////////////////////////////////////////////
// zone de jeux
//////////////////////////////////////////////////////////////////////////
    
    void menu() {
        CSVFile current = loadCSV("../ressources/entité/option pnj/quete.csv", ';');
        boolean ingame = true;
        while (ingame) {
            print(RESET);
            if (personne.level >= rowCount(current)) {
                ingame = false;
                println("Merci d'avoir jouer a notre magnifique jeu qui vaut surement un 20/20");
                Credit();
            } else {
                CSVFile lieu = loadCSV("../ressources/deplacement.csv", ';');
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
                        print(" (zone en arrière)\n");
                    }
                    println("");
                }
                println("\n[" + nbelements + "] | inventaire");
                println("\n[" + (nbelements + 1) + "] | Market");
                println("\n[" + (nbelements + 2) + "] | Sauvegarder et Quitter");

                println("");
                int action = saisie(nbelements + 2);
                if (action == nbelements) {
                    afficherinventaire();
                    waitingForPlayerActivity();
                }  else if (action == nbelements + 1) {
                    Market();
                } else if (action == nbelements + 2) {
                    sauvegarder();
                    ingame = false;
                } else if (equals(getCell(lieu, cpt, ((action + 1) * 2) - 1),"trap")) {
                    print(RESET);
                    println("Mince, il semble que vous ayez marché sur un piège en entrant, je n'essayerais de revenir ici si j'étais vous !");
                    waitingForPlayerActivity();
                    personne.currentlieu = getCell(lieu, cpt, (action + 1) * 2);
                } else if (equals(getCell(lieu, cpt, ((action + 1) * 2) - 1), "bossroom")){
                    if (bossStuff(getCell(lieu, cpt, (action + 1) * 2))){
                        personne.currentlieu = getCell(lieu, cpt, (action + 1) * 2);
                    }
                    waitingForPlayerActivity();
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

    boolean bossStuff(String boss_entrer){
        CSVFile current = loadCSV("../ressources/entité/desc_boss.csv", ';');
        int ligne = 0;
        print(RESET);
        while (!equals(boss_entrer,getCell(current,ligne,0))){
            ligne++;
        }
        if (personne.level >= stringToInt(getCell(current,ligne,1))){
            if (personne.level == stringToInt(getCell(current,ligne,1))){
                afficherLentement(getCell(current,ligne,2),50);
            } else {
                afficherLentement(getCell(current,ligne,3),100);
            }
            return true;
        }
        println("Vous n'avez pas encore ce qui faut à un magicien pour pénetrer dans ces lieux. Gagnez en force et en connaissance puis revener");
        return false;
    }

    //////////////////////////////////////////////////////////////////////////
// SAISIES UTILISATEUR
//////////////////////////////////////////////////////////////////////////

    int saisie(int possibilite) {
        String resultat;
        boolean trigger = true;

        do {
            if(!(personne == null)){
                print("<"+personne.nom+"> : ");
            }
            resultat = readString();

            if (!estNombre(resultat) || stringToInt(resultat) < 0 || stringToInt(resultat) > possibilite) {
                println("Il semble que votre langue ait fourché");
            } else {
                trigger = false;
            }
        } while (trigger);
        return stringToInt(resultat);
    }

    boolean estNombre(String valeur){
        boolean resultat = true;
        if (length(valeur) == 0){
            resultat = false;
        }else{
            for(int i=0;i<length(valeur);i++){
                if(charAt(valeur,i) < '0' || charAt(valeur,i) > '9'){
                    resultat = false;
                }
            }
        }
        return resultat;
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
        CSVFile current = loadCSV("../ressources/systeme de magie/question.csv", ';');

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

        CSVFile current = loadCSV("../ressources/entité/option pnj/quete.csv", ';');
        int ligne = 0;
        String rep = "";
        boolean encours = true;
        while (!(equals(getCell(current, ligne, 0), pnj))) {
            ligne++;
        }

        int colonne = 4;

        while (encours) {
            if (ligne == (rowCount(current) - 1)) {
                println("════════════════════════════════════");
                blaBlaPnj("Il vous salue et retourne à leur occupation, il ne semble pas avoir besoin de vous dans l'immédiat");
                println("════════════════════════════════════");
                encours = false;
            } else if (!(stringToInt(getCell(current, ligne, colonne)) == personne.level)) {
                ligne++;
            } else if (equals(getCell(current, ligne, 5), "false")) {
                println("\n════════════════════════════════════");
                println(pnj + " vous parle");
                println("════════════════════════════════════");
                blaBlaPnj(getCell(current, ligne, 1));
                loadquete();
                println("════════════════════════════════════");
                println("*Venez à bout de " + getCell(current, ligne, 3) + " " + getCell(current, ligne, 2)+"(s)*");
                println("════════════════════════════════════\n");
                encours = false;
            } else if (equals(getCell(current, ligne, 5), "true")) {
                if (verifierquete(ligne)) {
                    println("\n════════════════════════════════════");
                    println(pnj + " vous parle");
                    println("════════════════════════════════════");
                    blaBlaPnj("Il vous offre une potion de soin et "+250*personne.level+" or pour votre bravour .");
                    ajouterobjet("potion de soins");
                    personne.argent=personne.argent+250*personne.level; 
                    personne.level++;
                    personne.pv_max=personne.pv_max+10;
                    personne.pv=personne.pv_max;
                    println("════════════════════════════════════");
                    blaBlaPnj(getCell(current, ligne, 6));
                    println("════════════════════════════════════\n");

                    encours = false;
                } else {
                    println("\n════════════════════════════════════");
                    println(" " + pnj + " vous parle ");
                    println("════════════════════════════════════");
                    println("*"+pnj+" ne semble pas vous remarquer. Essayez de lui reparler quand vous aurez fini sa quête*");
                    println("════════════════════════════════════\n");
                    encours = false;
                }
            }
        }
        waitingForPlayerActivity();
    }

    void blaBlaPnj(String texte){
        String result = "";
        boolean ast = false;
        for (int i=0;i<length(texte);i++){
            char lettre = charAt(texte,i);
            if (lettre == '?'){
                result = result + "?\n";
            } else if (lettre == '*'){
                if (!ast){
                    result = result + "\n*";
                } else {
                    result = result + "*\n";
                }
            } else {
                result = result + lettre;
            }
        }
        afficherLentement(result,20);
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
        CSVFile current = loadCSV("../ressources/utilisateur/sauvegarde.csv", ';');

        if (equals(getCell(current, 0, 0), " ") || equals(getCell(current, 0, 0), "Joueur")) {
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
        utilisateur.argent=stringToInt(getCell(current, 5, 0));
        return utilisateur;
    }

    String askNom() {
        println("Vous me semblez nouveau/nouvelle ici, je ne crois pas être familier avec votre nom, pourriez vous me le donner ?\n");
        return saisieTexte();
    }

    void sauvegarder() {
        String[][] save = new String[][]{
            new String[]{personne.nom, ""},
            new String[]{personne.pv + "", personne.pv_max + ""},
            new String[]{personne.level + "", ""},
            new String[]{personne.quete_cible, personne.quete_kill + ""},
            new String[]{personne.currentlieu + "", ""},
            new String[]{personne.argent + "", ""}
        };
        saveCSV(save, "../ressources/utilisateur/sauvegarde.csv", ';');
    }

    //////////////////////////////////////////////////////////////////////////
// quete
//////////////////////////////////////////////////////////////////////////
void loadquete() {
        CSVFile quete = loadCSV("../ressources/entité/option pnj/quete.csv", ';');
        personne.quete_kill = 0;
        String[][] rep = new String[rowCount(quete)][columnCount(quete)];
        int ligne = 0;
        for (int cptligne = 0; cptligne < rowCount(quete); cptligne++) {
            for (int cptcolonne = 0; cptcolonne < columnCount(quete); cptcolonne++) {
                if (cptcolonne == 5 && stringToInt(getCell(quete, cptligne, 4)) == personne.level) {
                    rep[cptligne][cptcolonne] = "true";
                    ligne = cptligne;
                    personne.quete_cible = getCell(quete, ligne, 2);
                } else {
                    rep[cptligne][cptcolonne] = getCell(quete, cptligne, cptcolonne);
                }
            }
        }
        saveCSV(rep, "../ressources/entité/option pnj/quete.csv", ';');
    }

    boolean verifierquete(int ligne) {
        CSVFile current = loadCSV("../ressources/entité/option pnj/quete.csv", ';');
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
        CSVFile current = loadCSV("../ressources/entité/monstre.csv", ';');

        while (!equals(getCell(current, ligne, 0), nom)) {
            ligne++;
        }

        Monstre m = new Monstre();
        m.nom = nom;
        m.pvmax = stringToInt(getCell(current, ligne, 1));
        if (equals("normal",getCell(current, ligne, 5))){
            m.pvmax = (int) (m.pvmax * (1 + ((personne.level-1) * 0.2)));
        }    
        m.pv = m.pvmax;
        m.attaquenom = getCell(current, ligne, 2);
        m.attaquedegat = stringToInt(getCell(current, ligne, 3));
        if (equals("normal",getCell(current, ligne, 5))){
            m.attaquedegat = (int)( m.attaquedegat * (1 + ((personne.level-1) * 0.05)));
        }
        m.esquive = stringToInt(getCell(current, ligne, 4));

        return m;
    }

    //////////////////////////////////////////////////////////////////////////
// inventaire+Market
//////////////////////////////////////////////////////////////////////////
void afficherinventaire(){
    CSVFile current = loadCSV("../ressources/utilisateur/inventaire.csv", ';');
    print(RESET);
    println("\n════════════ INVENTAIRE ════════════");

    if (rowCount(current) == 0){
        println("  (Inventaire vide)");
        println("════════════════════════════════════\n");
    } else {
        for (int ligne = 0; ligne < rowCount(current); ligne++){
            println("  [" + ligne + "] " + getCell(current, ligne, 0) + " " + getCell(current, ligne, 2));
        }
        println("════════════════════════════════════\n");
    }
}

void Market(){
    CSVFile current = loadCSV("../ressources/utilisateur/objet.csv", ';');
    CSVFile inv = loadCSV("../ressources/utilisateur/inventaire.csv", ';');
    print(RESET);
    println("\n═══════════════════ Market ═══════════════════\n");
    afficherMisc("vendeur");
    println("══════════════════════════════════════════════\n[0]Acheter\n[1]Vendre\n[2]Quitter\n════════════════════════════════════\n");
    int choix=saisie(2);
    if(choix==0){
        println("\nVotre argent :"+personne.argent+"\n");
        println("Que souhaité vous achetez?");
        for (int ligne = 0; ligne < rowCount(current); ligne++){
            println("  [" + ligne + "] objet: " + getCell(current, ligne, 0) +" prix: "+getCell(current, ligne, 3)+" or");
        }
        int nb=saisie(rowCount(current)-1);
        if(personne.argent>=stringToInt(getCell(current,nb,3))){
            ajouterobjet(getCell(current, nb, 0));
            personne.argent=personne.argent-stringToInt(getCell(current, nb, 3));
            println("Merci de votre acchat a trés bientot jeune Héros");
            waitingForPlayerActivity();
        } else{
            println("Vous n'avez pas assez d'argent pour achetez cet objet. Je vous prie de quitter mon magasin.\nAu revoir !");
            waitingForPlayerActivity();
        }
    } else if(choix==1){
        if (rowCount(inv) > 0){
        afficherinventaire();
        println("Que souhaité vous vendre?");
        println("Tips:Chaque vente vous rapporte 250 or qu'importe l'objet");
        int choixinv=saisie(rowCount(inv));
        supprimerobjet(inv,choixinv);
        personne.argent+=250;
        waitingForPlayerActivity();
        } else{
            afficherinventaire();
            println("Vous n'avez aucun objet pensez a acheter une potion");
            waitingForPlayerActivity();
        }
    } else{
        println("\nAu revoir et revenez avec plus d'argent");
        waitingForPlayerActivity();
    }
    println("════════════════════════════════════\n");
}

    void utiliserObjet(Monstre monstre) {
        CSVFile current = loadCSV("../ressources/utilisateur/inventaire.csv", ';');
        afficherinventaire();
        if (rowCount(current) > 0) {
            int ligne = saisie(rowCount(current));
            if (equals(getCell(current, ligne, 1), "soins")) {
                personne.pv = personne.pv + stringToInt(getCell(current, ligne, 2));
                supprimerobjet(current, ligne);
                if (personne.pv > personne.pv_max) {
                    personne.pv = personne.pv_max;
                }
            } else if (equals(getCell(current, ligne, 1), "overheal")){
                personne.pv = personne.pv + stringToInt(getCell(current, ligne, 2))* monstre.attaquedegat;
                supprimerobjet(current, ligne);
            } else {
                monstre.pv = monstre.pv - stringToInt(getCell(current, ligne, 2));
                supprimerobjet(current, ligne);
                if (monstre.pv < 0) {
                    monstre.pv = 0;
                }
            }
        } else {
            println("\n\n<Tips> : Faite plus de quete afin d'obtenir plus d'objet \n\nLe monstre malin, comme il est, en profite pour vous attaquer.");
            waitingForPlayerActivity();
        }

    }

    void supprimerobjet(CSVFile current, int ligne) {
        if(rowCount(current)>0){
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

        saveCSV(rep, "../ressources/utilisateur/inventaire.csv", ';');
        } else{
            String[][] rep = new String[][]{ new String[]{" "}};
            saveCSV(rep, "../ressources/utilisateur/inventaire.csv", ';');
        }
    }

    void ajouterobjet(String nom) {
        CSVFile current = loadCSV("../ressources/utilisateur/objet.csv", ';');
        CSVFile inventaire = loadCSV("../ressources/utilisateur/inventaire.csv", ';');
        int ligne = 0;
        if(rowCount(inventaire) > 0){
            String[][] rep = new String[rowCount(inventaire) + 1][columnCount(inventaire)];
        while (!equals(getCell(current, ligne, 0), nom)) {
            ligne++;
        }
        for (int cptligne = 0; cptligne < rowCount(inventaire); cptligne++) {
            for (int cptcolonne = 0; cptcolonne < columnCount(inventaire); cptcolonne++) {
                rep[cptligne][cptcolonne] = getCell(inventaire, cptligne, cptcolonne);
            }
        }
        for (int cptcolonne = 0; cptcolonne < columnCount(current)-1; cptcolonne++) {
            rep[rowCount(inventaire)][cptcolonne] = getCell(current, ligne, cptcolonne);
        }
        saveCSV(rep, "../ressources/utilisateur/inventaire.csv", ';');
        } else{
            String[][] rep = new String[1][columnCount(current)];
            for (int cptcolonne = 0; cptcolonne < columnCount(current); cptcolonne++) {
                rep[0][cptcolonne] = getCell(current, ligne, cptcolonne);
            }
            saveCSV(rep, "../ressources/utilisateur/inventaire.csv", ';');
        }
        
    }

    //////////////////////////////////////////////////////////////////////////
// COMBAT
//////////////////////////////////////////////////////////////////////////

    String demanderAttaque(String[] attaquePossibles) {
        int page = 0;
        int nbPages = (length(attaquePossibles) + 8 - 1) / 8;
        while (true) {
            println("══════════════════════════════════════");
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
                println("["+(end-debut+1)+"] | Annuler");
                trigger = true;
            } else {
                println("\n["+(end-debut)+"] | Annuler");
            }
            println("══════════════════════════════════════\n");
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
        CSVFile current = loadCSV("../ressources/systeme de magie/possibilite attaque.csv", ';');
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
        CSVFile current = loadCSV("../ressources/systeme de magie/possibilite attaque.csv", ';');
        int cpt = -1;
        int resultat;
        do {
            cpt++;
            resultat = stringToInt(getCell(current, cpt, 1));
        } while (!equals(attaque, getCell(current, cpt, 0)));
        return resultat;
    }

    void afficherCurrentMonstre(Monstre currentMonstre, int berserk){
        print(RESET);
        println("═══════════════ COMBAT ═══════════════");
        print(" Ennemi : " + currentMonstre.nom);
        if (berserk>0){
            print(" (Berserk");
            if (berserk>1){
                print(" niveau "+berserk);
            }
            print(")");
        }
        println("");
        println(" PV Ennemi : " + currentMonstre.pv + "/" + currentMonstre.pvmax);
        println("══════════════════════════════════════\n");
        afficherSpriteMonstre(currentMonstre.nom);
        println("══════════════════════════════════════");
        println("Vos PV : " + personne.pv + "/" + personne.pv_max);
    }

    void resultatAttaque(String attaqueChoisie, Monstre currentMonstre){
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
        waitingForPlayerActivity();
    }

    void mortJoueur(){
        print(RESET);
        println("Vous avez succombé\n");
        afficherMisc("mort_joueur");
    }

    void mortMonstre(Monstre currentMonstre){
        print(RESET);
        println("Bravo vous vous en sortez sain et sauf\n");
        afficherMisc("victoire");
        personne.argent=personne.argent+150;
        verifiermonstretuer(currentMonstre.nom);
        println("Une lumiére magique descend et vous soigne de toute vos blessure");
        personne.pv = personne.pv_max;
    }

    void attaqueMonstre(Monstre currentMonstre){
        print(RESET);
        println("le/a " + currentMonstre.nom + " a utilisé "
                + currentMonstre.attaquenom + " et vous a infligé "
                + currentMonstre.attaquedegat + " dégats !\n");
        personne.pv = personne.pv - currentMonstre.attaquedegat;
        waitingForPlayerActivity();
    }

    boolean combat(String monstre) {
        Monstre currentMonstre = newMonstre(monstre);
        int berserk = 0;
        while (true) {
            boolean annuler = false;
            if(currentMonstre.pv<=currentMonstre.pvmax/2){
                print(RESET);
                if (berserk == 0){
                    println("Le monstre est enragé et entre en mode Berserk.\nPlus il reste dans ce mode plus fort il devient!");
                    waitingForPlayerActivity();
                }
                currentMonstre.attaquedegat=(int) (currentMonstre.attaquedegat*1.25);
                berserk++;
            }
            afficherCurrentMonstre(currentMonstre,berserk);
            println("══════════════════════════════════════");
            println("[0] Attaquer");
            println("[1] Utiliser un objet");
            println("══════════════════════════════════════\n");
            int reponse = saisie(1);
            if (reponse == 0) {
                afficherCurrentMonstre(currentMonstre,berserk);
                String attaqueChoisie = attaque();
                if (equals(attaqueChoisie, "annuler")) {
                    annuler = true;
                }
                if (!annuler){
                    afficherCurrentMonstre(currentMonstre,berserk);
                    resultatAttaque(attaqueChoisie,currentMonstre);
                }
            } else {
                utiliserObjet(currentMonstre);
            }
            if (!annuler) {
                if (currentMonstre.pv > 0) {
                    attaqueMonstre(currentMonstre);
                } else {
                    mortMonstre(currentMonstre);
                    personne.pv_max=personne.pv_max+1;
                    personne.pv=personne.pv_max;
                    return false;
                }
                if (personne.pv <= 0) {
                    mortJoueur();
                    return true;
                }
            }
        }
    }



//////////////////////////////////////////////////////
//TEST
//////////////////////////////////////////////////////
User joueur ;

//////////////////////////////////////////////////////
// ALGORITHME PRINCIPAL (MODE TEST)
//////////////////////////////////////////////////////
/*void algorithm() {

    joueur = new User();
    personne = joueur; // alias pour compatibilité avec le jeu

    testEstNombre();
    testDegats();
    testNewMonstre();
    testScalingMonstre();
    testBoss();
    testQuete();
    testVerifierMonstreTuer();
    testVerifierQuete();
    testInventaire();
    testSauvegarde();
    testAttaquesParNiveau();

    println("\n✅ TOUS LES TESTS SONT PASSÉS AVEC SUCCÈS");

    // jeux(); // ← à remettre après les tests
}*/

//////////////////////////////////////////////////////
// TEST estNombre
//////////////////////////////////////////////////////
void testEstNombre() {
    assertEquals(true, estNombre("0"));
    assertEquals(true, estNombre("123"));
    assertEquals(true, estNombre("999"));

    assertEquals(false, estNombre(""));
    assertEquals(false, estNombre(" "));
    assertEquals(false, estNombre("12a"));
    assertEquals(false, estNombre("a12"));
    assertEquals(false, estNombre("12.5"));
}

//////////////////////////////////////////////////////
// TEST degats
//////////////////////////////////////////////////////
void testDegats() {
    assertEquals(20, degats("Coup du Temps"));
    assertEquals(19, degats("Pointe du Scribe"));
    assertEquals(22, degats("Marche des Géants"));
    assertEquals(30, degats("Guillotine"));
    assertEquals(42, degats("Trône d'Or"));
}

//////////////////////////////////////////////////////
// TEST création monstre
//////////////////////////////////////////////////////
void testNewMonstre() {
    joueur.level = 1;

    Monstre loup = newMonstre("loup");

    assertEquals("loup", loup.nom);
    assertEquals(110, loup.pvmax);
    assertEquals(110, loup.pv);
    assertEquals(7, loup.attaquedegat);
    assertEquals(20, loup.esquive);
}

//////////////////////////////////////////////////////
// TEST scaling monstre
//////////////////////////////////////////////////////
void testScalingMonstre() {
    joueur.level = 2;
    assertEquals(132, newMonstre("loup").pvmax);

    joueur.level = 3;
    assertEquals(154, newMonstre("loup").pvmax);

    joueur.level = 4;
    assertEquals(176, newMonstre("loup").pvmax);
}

//////////////////////////////////////////////////////
// TEST boss
//////////////////////////////////////////////////////
void testBoss() {
    joueur.level = 3;
    assertEquals(false, bossStuff("La forge"));

    joueur.level = 6;
    assertEquals(true, bossStuff("La forge"));

    joueur.level = 10;
    assertEquals(true, bossStuff("Antre sombre"));
}

//////////////////////////////////////////////////////
// TEST quête
//////////////////////////////////////////////////////
void testQuete() {
    joueur.level = 1;
    loadquete();

    assertEquals("loup", joueur.quete_cible);
    assertEquals(0, joueur.quete_kill);
}

//////////////////////////////////////////////////////
// TEST monstre tué
//////////////////////////////////////////////////////
void testVerifierMonstreTuer() {
    verifiermonstretuer("fantome");
    assertEquals(0, joueur.quete_kill);

    verifiermonstretuer("loup");
    assertEquals(1, joueur.quete_kill);
}

//////////////////////////////////////////////////////
// TEST validation quête
//////////////////////////////////////////////////////
void testVerifierQuete() {
    assertEquals(true, verifierquete(0));
}

//////////////////////////////////////////////////////
// TEST inventaire
//////////////////////////////////////////////////////
void testInventaire() {
    ajouterobjet("potion de soins");
    CSVFile inv = loadCSV("../ressources/utilisateur/inventaire.csv", ';');

    assertEquals("potion de soins", getCell(inv, 0, 0));

    supprimerobjet(inv, 0);
    inv = loadCSV("../ressources/utilisateur/inventaire.csv", ';');

    assertEquals(0, rowCount(inv));
}

//////////////////////////////////////////////////////
// TEST sauvegarde
//////////////////////////////////////////////////////
void testSauvegarde() {
    joueur.nom = "Testeur";
    joueur.level = 5;
    joueur.pv = 80;
    joueur.pv_max = 100;
    joueur.currentlieu = "La route";
    joueur.argent = 999;

    sauvegarder();

    CSVFile save = loadCSV("../ressources/utilisateur/sauvegarde.csv", ';');

    assertEquals("Testeur", getCell(save, 0, 0));
    assertEquals("80", getCell(save, 1, 0));
    assertEquals("100", getCell(save, 1, 1));
    assertEquals("5", getCell(save, 2, 0));
    assertEquals("La route", getCell(save, 4, 0));
    assertEquals("999", getCell(save, 5, 0));
}

//////////////////////////////////////////////////////
// TEST attaques par niveau
//////////////////////////////////////////////////////
void testAttaquesParNiveau() {
    CSVFile atk = loadCSV("../ressources/systeme de magie/possibilite attaque.csv", ';');

    joueur.level = 1;
    assertEquals(4, countAttaques(atk, 1));

    joueur.level = 3;
    assertEquals(7, countAttaques(atk, 3));

    joueur.level = 6;
    assertEquals(13, countAttaques(atk, 6));

    joueur.level = 10;
    assertEquals(20, countAttaques(atk, 10));
}

//////////////////////////////////////////////////////
// FONCTION UTILITAIRE
//////////////////////////////////////////////////////
int countAttaques(CSVFile atk, int niveau) {
    int cpt = 0;
    while (cpt < rowCount(atk) && stringToInt(getCell(atk, cpt, 2)) <= niveau) {
        cpt++;
    }
    return cpt;
}
}