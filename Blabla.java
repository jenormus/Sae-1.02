import extensions.CSVFile;
class Blabla extends Program{
    void algorithm(){
        deplacement("Hall");
    }

    int saisie(int possibilite) {
        String resultat;
        boolean trigger = true;

        do {
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

    void deplacement(String currentlieu){
        CSVFile lieu = loadCSV("jeux/deplacement.csv",';');
        int cpt=0;
        while(!equals(getCell(lieu,0,cpt),currentlieu)){
            cpt++;
        }
        int nbelements = (columnCount(lieu,cpt)/2);
        println("Déplacments :\n");
        for (int i=0;i<nbelements;i++){
            println("["+i+"] "+getCell(lieu,cpt,((i+1)*2)-1)+" - "+getCell(lieu,cpt,((i+1)*2)));
        }
        int action = saisie(nbelements-1);
        if(equals(getCell(lieu,cpt,((action+1)*2)-1),"lieu")){
            println(getCell(lieu,cpt,(action+1)*2));
        } else if(equals(getCell(lieu,cpt,((action+1)*2)-1),"pnj")){
            println(getCell(lieu,cpt,(action+1)*2));
        } else if(equals(getCell(lieu,cpt,((action+1)*2)-1),"monstre")){
            println(getCell(lieu,cpt,(action+1)*2));
        } else {
            println("erreur");
        }
    }
}