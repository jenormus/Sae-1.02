import extensions.File;
import extensions .CSVFile;

class Main extends Program{

    void algorithm(){
        commencer();
    }

    

    void commencer(){
        afficherMisc("title");
        User joueur = newUser();
        print(joueur);
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

    void afficherMisc(String image){
        println(loadSpriteMisc(image));
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

    String askNom(){
        println("Il me semble que c'est la première fois que l'on se voit ici, comment t'appelles-tu ?\n");
        return saisieTexte();

    }

    void print(User utilisateur){
        println("nom = "+utilisateur.nom);
        println("vie = "+utilisateur.pv+":"+utilisateur.pv_max);
        println("level = "+utilisateur.level);
        println("quete = "+utilisateur.quete_kill+utilisateur.quete_cible+"tués");
    }

}