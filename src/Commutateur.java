import java.awt.*;
import java.util.*;
/**
 * Classe représentant un commutateur dans un réseau.
 */
public class Commutateur {
    String nom;
    Map<Commutateur, Integer> voisins;
    int x, y;
    Map<Commutateur, Integer> distances;
    public Map<Commutateur, Commutateur> precedents;
    boolean marque;
    Color couleur;

    public Commutateur(String nom, int x, int y) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.voisins = new HashMap<>();
        this.distances = new HashMap<>();
        this.precedents = new HashMap<>();
        this.marque = false;
        this.couleur = Color.BLUE;
    }

    public String getNom() {
        return nom;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public void ajouterVoisin(Commutateur voisin, int ponderation) {
        this.voisins.put(voisin, ponderation);
        voisin.voisins.put(this, ponderation);
    }

    /**
     * Calcul et renvoie la table de routage pour ce commutateur.
     */
    public String calculerTableRoutage() {
        distances.clear();
        precedents.clear();

        // Initialiser les distances avec les voisins directs
        for (Commutateur voisin : voisins.keySet()) {
            distances.put(voisin, voisins.get(voisin));
            precedents.put(voisin, this);
        }

        marque = true;
        while (true) {
            Commutateur prochain = null;
            int minDistance = Integer.MAX_VALUE;

            // Rechercher le commutateur non marqué avec la plus petite distance
            for (Commutateur voisin : voisins.keySet()) {
                if (!voisin.marque && distances.get(voisin) < minDistance) {
                    prochain = voisin;
                    minDistance = distances.get(voisin);
                }
            }

            if (prochain == null) break;

            prochain.marque = true;

            // Mettre à jour les distances des voisins du commutateur courant
            for (Map.Entry<Commutateur, Integer> voisinEntry : prochain.voisins.entrySet()) {
                Commutateur voisin = voisinEntry.getKey();
                int ponderation = voisinEntry.getValue();

                if (!voisin.marque) {
                    int distanceAlternative = distances.get(prochain) + ponderation;
                    if (distanceAlternative < distances.getOrDefault(voisin, Integer.MAX_VALUE)) {
                        distances.put(voisin, distanceAlternative);
                        precedents.put(voisin, prochain);
                    }
                }
            }
        }

        // Construire et renvoyer la table de routage
        StringBuilder tableRoutage = new StringBuilder();
        tableRoutage.append("Table de routage pour le commutateur ").append(nom).append(":\n");
        for (Map.Entry<Commutateur, Integer> entry : distances.entrySet()) {
            Commutateur destination = entry.getKey();
            tableRoutage.append("Vers ").append(destination.getNom())
                    .append(": Distance = ").append(entry.getValue())
                    .append(", Passant par: ").append(precedents.get(destination).getNom()).append("\n");
        }

        return tableRoutage.toString();
    }
}