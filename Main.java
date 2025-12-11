import extensions.File;
class Main extends Program{

    void algorithm(){
        print(loadSprite("entité/Ascii art pnj/elf.txt"));
    }

    String loadSprite(String chemin){
        String resultat="";
        File current=newFile(chemin);
        while(ready(current)){
            resultat=resultat+readLine(current)+"\n";
        }
        return resultat;
    }
}