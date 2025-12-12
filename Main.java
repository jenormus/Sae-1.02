import extensions.File;
import extensions .CSVFile;

class Main extends Program{

    void algorithm(){
        commencer();
    }

    

    void commencer(){
        afficherMisc("title");
        print(question());
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

    void loadPnj(String pnj,String num){
        afficherMisc(loadSpritePNJ(pnj));
        CSVFile current=loadCSV("jeux/entité/option pnj/quete.csv");
        int ligne=0;
        int colonne=0;
        String rep="";
        boolean encours=true;
        while(getCell(current,ligne,colonne)=! pnj){
            ligne++;
        }
        colonne=5;
        while(encours){
            if(!equals(getCell(current,ligne,colonne),num)){
            ligne++;
            }
            else if(!equals(getCell(current,ligne,colonne),num) && ligne=rowCount(current)){
                //appelle le dialogue qui dit degage
                encours=false;
            } else{
                //appelle le dialogue qui dit quete 
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

    User newUser(String nom){
        User utilisateur = new User;
        CSVFile current = loadCSV("jeux/utilisateur/user.csv")
        m.nom=nom;
        m.pv_max=stringToInt(getCell(current,ligne,1));
        m.pv=m.pv_max;
        m.level=stringToInt(getCell(current,ligne,5));
    }

    Monstre newMonstre(String nom){
        int ligne=0;
        int colonne=0;
        while(getCell(current,ligne,colonne)=! nom){
            ligne++;
        }
        Monstre m=new Monstre();
        CSVFile current = loadCSV("jeux/entité/monstre.csv");
        m.nom=nom;
        m.pvmax=stringToInt(getCell(current,ligne,1));
        m.pv=m.pvmax;
        m.attaquenom=getCell(current,ligne,2);
        m.attaquedegat=stringToInt(getCell(current,ligne,3));
        m.esquive=stringToInt(getCell(current,ligne,4));
    }
}