import extensions.File;
import extensions .CSVFile;

class Main extends Program{

    User personne = newUser();

    void algorithm(){
        commencer();
    }

    

    void commencer(){
        afficherMisc("title");
        User joueur = newUser();
    }

//Charge les images textes//

    String loadImage(String chemin){
        String resultat="";
        File current=newFile(chemin);
        while(ready(current)){
            resultat=resultat+readLine(current)+"\n";
        }
        return resultat;
    }

//Fonctions shortcuts
//////////////////////////////////////////////////////////////////////////

    String loadSpritePNJ(String pnj){
        return loadImage("jeux/entité/Ascii art pnj/"+pnj+".txt");
    }

    String loadSpriteMonstre(String monstre){
        return loadImage("jeux/entité/Ascii art monstre/"+monstre+".txt");
    }

    String loadSpriteMisc(String misc){
        return loadImage("jeux/entité/Ascii misc/"+misc+".txt");
    }

    String loadDialoguePNJ(String pnj,boolean quete){
        CSVFile current = loadCSV("jeux/option pnj/dialogue.csv",';');
        int ligne = 0;
        while (!equals(getCell(current,ligne,0),pnj)){
            ligne++;
        }
        if (quete){
            return getCell(current,ligne,1);
        }
        return getCell(current,ligne,2);
    }

    void afficherMisc(String image){
        println(loadSpriteMisc(image));
    }

    void afficherSpritePNJ(String pnj){
        println(loadSpritePNJ(pnj));
    }

//////////////////////////////////////////////////////////////////////////

//Permet à l'utilisateur d'entrer une valeure entre 0 et le paramètre

    int saisie(int possibilite){
        String resultat;
        boolean trigger=true;
        do{
            resultat = readString();
            if((length(resultat) != 1) || (charAt(resultat,0)<'0' || charAt(resultat,0)>(possibilite+'0'))){
                println("Il semble que votre langue ait fourché");
            } else {
                trigger=false;
            }
        }while(trigger);
        return charAt(resultat,0)-'0';
    }

//Permet à l'utilisateur d'entrer une chaine de caractères (cas de réessaye si chaine vide)

    String saisieTexte(){
        String resultat;
        boolean trigger;
        do{
            trigger = false;
            resultat = readString();
            if (resultat == ""){
                trigger = true;
                println("Il semble que votre langue ait fourché");
            }
        }while(trigger);
        return resultat;
    }

//Permet de charger un String[] contenant [question + réponses possible*4 + réponse]  

    String[] loadQuestion(){
        String[] resultat=new String[6];
        CSVFile current=loadCSV("jeux/systeme de magie/question.csv",';');
        int nbligne=rowCount(current);
        int ligne=(int) random(0,nbligne);
        resultat[0]=getCell(current,ligne,0);
        resultat[5]=getCell(current,ligne,1);
        int pos;
        for (int i=0;i<4;i++){
            do{
                pos =(int) random(1,5);
            }while(resultat[pos]!=null);
            resultat[pos]=getCell(current,ligne,1+i);
        }
        return resultat;
    }

///////  STP FAIT CELUI LA J'AI RIEN COMPRIS A CE QUE TU VOULAIS FAIRE /////////

    void loadPnj(String pnj,String num){
        afficherSpritePNJ(pnj);
        CSVFile current=loadCSV("jeux/entité/option pnj/quete.csv",';');
        int ligne=0;
        String rep="";
        boolean encours=true;
        while(getCell(current,ligne,0)!= pnj){
            ligne++;
        }
        int colonne=5;
        while(encours){
            if(!equals(getCell(current,ligne,colonne),num)){
                ligne++;
            }
            else if(!equals(getCell(current,ligne,colonne),num) && ligne=rowCount(current)){
                loadDialoguePNJ(pnj,false);
                encours=false;
            } else{
                loadDialoguePNJ(pnj,true);
                println(getCell(current,ligne,colonne));
                //loadquete(permetra de coller la quete au joueur)
                encours=false;
            }
        }
    }
//Permet d'afficher la question

    void afficherQuestion(String[] question){
        println(question[0]+"\n");
        for (int i=0;i<4;i++){
            println("["+i+"] "+question[i+1]);
        }
        println("");
    }

//Permet de poser une question, d'attendre une réponse et de retourner un boolean en foncion de la réponse

    boolean question(){
        String[] question=loadQuestion();
        afficherQuestion(question);
        int reponse=saisie(4);
        if(equals(question[reponse+1],question[5])){
            return true;
        }
        return false;
    }

//Permet de créer un élément de type User qui chargera soit les valeurs du fichier Sauvegarde soit les valeurs par défaut et den demandant le nom de la personne via la fonction "askNom()"

    User newUser(){
        User utilisateur = new User();
        CSVFile current = loadCSV("jeux/utilisateur/sauvegarde.csv",';');
        if (equals(getCell(current,0,0)," ")){
            utilisateur.nom = askNom();
        } else {
            utilisateur.nom = getCell(current,0,0);
        }
        if (equals(getCell(current,3,0)," ")){
            utilisateur.quete_cible=" ";
            utilisateur.quete_kill=0;
        } else {
            utilisateur.quete_cible=getCell(current,3,0);
            utilisateur.quete_kill=stringToInt(getCell(current,3,1));
        }
        utilisateur.level=stringToInt(getCell(current,2,0));
        utilisateur.pv = stringToInt(getCell(current,1,0));
        utilisateur.pv_max= stringToInt(getCell(current,1,1));
        return utilisateur;
    }

//Demande le nom de l'utilisateur et retourne un String

    String askNom(){
        println("Vous me semblez nouveau/nouvelle ici, je ne crois pas être familier avec votre nom, pourriez vous me le donner ?");
        return saisieTexte();
    }

//Overwrite le fichier actuel de sauvegarde pour mettre les nouvelles données

    void sauvegarder(){
        String[][] save = new String[][] {{personne.nom},{personne.pv+"",personne.pv_max+""},{personne.level+""},{personne.quete_cible,personne.quete_kill+""}};
        saveCSV(save,"jeux/utilisateur/sauvegarde.csv");
    }

//créer un monstre ayant ses statistiques venant du fichier "monstre.csv"

    Monstre newMonstre(String nom){
        int ligne=0;
        CSVFile current = loadCSV("jeux/entité/monstre.csv",';');
        while(equals(getCell(current,ligne,0),nom)){
            ligne++;
        }
        Monstre m=new Monstre();
        m.nom=nom;
        m.pvmax=stringToInt(getCell(current,ligne,1))*((int) (personne.level*0.20));
        m.pv=m.pvmax;
        m.attaquenom=getCell(current,ligne,2);
        m.attaquedegat=stringToInt(getCell(current,ligne,3))*((int) (personne.level*0.10));
        m.esquive=stringToInt(getCell(current,ligne,4));
    }

//fonction qui permet d'afficher les possibilités d'attaque, demande un choix à l'utilisateur et renvoie soit le nom de l'attaque soit "annuler" en fonction du choix (pour sortir du menu)

    String demanderAttaque(String[] attaquePossibles){
        int page=0;
        int nbPages = (length(attaquePossibles)+8-1)/8;
        while (true) {
            int debut = page * 8;
            int end = debut + 8;
            if (end > length(attaquePossibles)){
                end = length(attaquePossibles);
            }
            for (int i=debut;i<end;i++) {
                println("["+(i-debut)+"] " + attaquePossibles[i]);
            }
            if (nbPages!=1){
                println("["+(end-debut)+"] Suivant");
                println("["+(end-debut+1)+"] Annuler");
            } else {
                println("["+(end-debut)+"] Annuler");
            }
            int reponse = saisie(end-debut+1);
            if (nbPages!=1){
                if (reponse==end-debut+1) {
                    return "annuler";
                }
            } else {
                if (reponse==end-debut) {
                    return "annuler";
                }
            }
            if (reponse==end-debut) {
                page++;
                if (page*8>=length(attaquePossibles)) {
                    page = 0;
                }
            }
            if (reponse>=0 && reponse<end-debut) {
                return attaquePossibles[debut + reponse];
            }
        }
    }

//fonction à appeller facilement pour lancer l'action de l'attaque

    String attaque(){
        CSVFile current = loadCSV("jeux/sysyteme de magie/possibilite attaque.csv",';');
        String[] attaquePossibles = new String[rowCount(current)];
        int cpt=0;
        while (cpt<length(attaquePossibles) && stringToInt(getCell(current,cpt,2))<=personne.level){
            cpt++;
        }
        String[] attaquePossiblesClean = new String[cpt];
        for (int i=0;i<cpt;i++){
            attaquePossiblesClean[i]=getCell(current,i,0);
        }
        String choixAttaque = demanderAttaque(attaquePossiblesClean);
        return choixAttaque; 
    }

}