import java.awt.*;
import java.util.*;
public class Commutateur {
    String nom;
    /* Pour associer chaque voisin avec sa ponderation */
    Map<Commutateur,Integer> voisins;
    /* Postion du commutateur sur la fenetre */
    int x,y;
    /* Distances par rapport aux commutateurs */
    Map<Commutateur,Integer> distances;
    /* Chemin le plus court vers chaque commutateur */
    Map<Commutateur, Commutateur> precedents;
    /* Pour savoir si le commutateur etait deja visitée
    ou
    pas pendant le calcul */
    boolean marque;
    boolean estSelectionnee;
    Color couleur;

    /*
    * Constructeurs avec 3 params
    * */
    Commutateur (String nom, int x, int y){
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.voisins = new HashMap<>();
        this.distances = new HashMap<>();
        this.precedents = new HashMap<Commutateur, Commutateur>();
        this.marque = false;
        this.estSelectionnee = false;
        this.couleur = Color.BLUE;
    }
    /*
    * Setteurs pour le couleur
    * */
    void setCouleur(Color c){
        this.couleur = c;
    }
    void ajouterVoisins(Commutateur voisin, int ponderation){
        voisins.put(voisin,ponderation);
        voisin.voisins.put(this,ponderation);
    }
    String calculerTableRoutage() {
        distances.clear();
        precedents.clear();

        // Initialisation des distances
        for (Commutateur c : voisins.keySet()) {
            distances.put(c, voisins.get(c));
            precedents.put(c, this);
        }

        marque = true;

        while (true) {
            Commutateur prochain = null;
            int minDistance = Integer.MAX_VALUE;

            // Recherche du commutateur non marqué avec la plus petite distance
            for (Commutateur c : voisins.keySet()) {
                if (!c.marque && distances.get(c) < minDistance) {
                    prochain = c;
                    minDistance = distances.get(c);
                }
            }

            if (prochain == null)
                break;

            prochain.marque = true;

            // Mise à jour des distances
            for (Commutateur voisin : prochain.voisins.keySet()) {
                if (!voisin.marque) {
                    int distanceAlternative = distances.get(prochain) + prochain.voisins.get(voisin);
                    if (distanceAlternative < distances.getOrDefault(voisin, Integer.MAX_VALUE)) {
                        distances.put(voisin, distanceAlternative);
                        precedents.put(voisin, prochain);
                    }
                }
            }
        }

        // Construction de la table de routage
        StringBuilder tableRoutage = new StringBuilder();
        tableRoutage.append("Table de routage pour le commutateur ").append(nom).append(":\n");
        for (Commutateur c : distances.keySet()) {
            tableRoutage.append("Vers ").append(c.nom).append(": Distance = ").append(distances.get(c)).append(", Passant par: ").append(precedents.get(c).nom).append("\n");
        }
        return tableRoutage.toString();
    }
}
