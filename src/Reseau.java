import java.util.*;
/**
 * Classe représentant le réseau, qui contient des commutateurs et des machines.
 */
public class Reseau {
    private List<Commutateur> commutateurs;
    private List<Machine> machines;

    public Reseau() {
        this.commutateurs = new ArrayList<>();
        this.machines = new ArrayList<>();
    }

    public void ajouterCommutateur(Commutateur commutateur) {
        this.commutateurs.add(commutateur);
    }

    public void ajouterMachine(Machine machine) {
        this.machines.add(machine);
    }

    public List<Commutateur> getCommutateurs() {
        return commutateurs;
    }

    /**
     * Algorithme de Dijkstra pour calculer le chemin le plus court entre deux commutateurs.
     */
    public List<Commutateur> calculerCheminPlusCourt(Commutateur source, Commutateur destination) {
        Map<Commutateur, Integer> distance = new HashMap<>();
        Map<Commutateur, Commutateur> precedents = new HashMap<>();
        Set<Commutateur> visite = new HashSet<>();

        // Initialiser les distances et précédents
        for (Commutateur commutateur : commutateurs) {
            distance.put(commutateur, Integer.MAX_VALUE);
            precedents.put(commutateur, null);
        }
        distance.put(source, 0);

        while (!visite.contains(destination)) {
            Commutateur actuel = null;
            int distanceMin = Integer.MAX_VALUE;

            for (Commutateur commutateur : commutateurs) {
                if (!visite.contains(commutateur) && distance.get(commutateur) < distanceMin) {
                    actuel = commutateur;
                    distanceMin = distance.get(commutateur);
                }
            }

            if (actuel == null) break;

            visite.add(actuel);

            for (Map.Entry<Commutateur, Integer> voisinEntry : actuel.voisins.entrySet()) {
                Commutateur voisin = voisinEntry.getKey();
                int poids = voisinEntry.getValue();

                if (!visite.contains(voisin)) {
                    int alt = distance.get(actuel) + poids;
                    if (alt < distance.get(voisin)) {
                        distance.put(voisin, alt);
                        precedents.put(voisin, actuel);
                    }
                }
            }
        }

        // Reconstruire le chemin le plus court
        List<Commutateur> chemin = new ArrayList<>();
        for (Commutateur commutateur = destination; commutateur != null; commutateur = precedents.get(commutateur)) {
            chemin.add(commutateur);
        }
        Collections.reverse(chemin);
        return chemin;
    }
}