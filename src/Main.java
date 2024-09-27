import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;


public class Main {
    // Instance du réseau
    private static Reseau reseau;
    // Commutateur sélectionné
    private static Commutateur commutateurSelectionne;
    // Mode pour relier deux commutateurs
    private static boolean relierMode = false;
    // Mode de calcul du chemin le plus court
    private static boolean calculerCheminMode = false;
    // Mode de calcul de la table de routage
    private static boolean calculerTableRoutageMode = false;
    // Commutateur source pour la connexion
    private static Commutateur commutateurSource;
    // Commutateur destination pour la connexion
    private static Commutateur commutateurDestination;
    // Panneau pour afficher les commutateurs et les connexions
    private static JPanel panel;

    public static void main(String[] args) {
        // Création d'un réseau
        reseau = new Reseau();

        // Affichage de l'interface utilisateur
        afficherInterface();
    }

    private static void afficherInterface() {
        JFrame frame = new JFrame("Création de réseau");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panneau pour afficher les commutateurs et les connexions
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Parcours de tous les commutateurs du réseau
                for (Commutateur commutateur : reseau.commutateurs) {
                    // Utiliser la couleur du commutateur
                    g.setColor(commutateur.couleur);
                    // Dessiner le commutateur
                    g.fillOval(commutateur.x, commutateur.y, 30, 30);
                    // Afficher le nom du commutateur
                    g.drawString(commutateur.nom, commutateur.x - 10, commutateur.y - 10);
                    // Parcours de tous les voisins du commutateur
                    for (Map.Entry<Commutateur, Integer> entry : commutateur.voisins.entrySet()) {
                        Commutateur voisin = entry.getKey();
                        int ponderation = entry.getValue();
                        g.setColor(Color.BLACK);
                        // Dessiner la liaison
                        g.drawLine(commutateur.x + 15, commutateur.y + 15, voisin.x + 15, voisin.y + 15);
                        // Afficher la pondération
                        g.drawString(Integer.toString(ponderation), (commutateur.x + voisin.x + 30) / 2, (commutateur.y + voisin.y + 30) / 2);
                    }
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (relierMode) {
                    if (commutateurSource == null) {
                        commutateurSource = trouverCommutateur(e.getX(), e.getY());
                        if (commutateurSource != null) {
                            commutateurSource.setCouleur(Color.RED);
                        }
                    } else {
                        commutateurDestination = trouverCommutateur(e.getX(), e.getY());
                        if (commutateurDestination != null && !commutateurDestination.equals(commutateurSource)) {
                            commutateurDestination.setCouleur(Color.RED);
                            lieeCommutateurs();
                        }
                    }
                    panel.repaint();
                } else if (calculerCheminMode) {
                    if (commutateurSource == null) {
                        commutateurSource = trouverCommutateur(e.getX(), e.getY());
                        if (commutateurSource != null) {
                            commutateurSource.setCouleur(Color.RED);
                        }
                    } else if (commutateurDestination == null) {
                        commutateurDestination = trouverCommutateur(e.getX(), e.getY());
                        if (commutateurDestination != null && !commutateurDestination.equals(commutateurSource)) {
                            // Changer la couleur du commutateur destination
                            commutateurDestination.setCouleur(Color.RED);
                            calculerCheminPlusCourt(commutateurSource, commutateurDestination);
                            // Réinitialiser les couleurs des commutateurs
                            couleurOrigine();
                        }
                    }
                    panel.repaint();
                } else if (calculerTableRoutageMode) {
                    commutateurSelectionne = trouverCommutateur(e.getX(), e.getY());
                    if (commutateurSelectionne != null) {
                        // Changer la couleur du commutateur sélectionné
                        commutateurSelectionne.setCouleur(Color.RED);
                        // Calculer la table de routage
                        String tableRoutage = commutateurSelectionne.calculerTableRoutage();
                        // Afficher la table de routage
                        afficherTableRoutage(tableRoutage);
                        // Réinitialiser les couleurs sauf le commutateur sélectionné
                        couleurOrigineSelect();
                    }
                    panel.repaint();
                } else {
                    creerCommutateur(e.getX(), e.getY());
                }
            }
        });

        frame.add(panel, BorderLayout.CENTER);

        JToolBar toolBar = new JToolBar();

        JButton relierButton = new JButton("Relier Commutateurs");
        relierButton.addActionListener(e -> {
            relierMode = !relierMode;
            calculerCheminMode = false; // Désactiver le mode de calcul du chemin le plus court
            calculerTableRoutageMode = false; // Désactiver le mode de calcul de la table de routage
            commutateurSource = null;
            commutateurDestination = null;
            couleurOrigine(); // Réinitialiser les couleurs des commutateurs
        });
        toolBar.add(relierButton);

        JButton calculerCheminButton = new JButton("Calculer Chemin Plus Court");
        calculerCheminButton.addActionListener(e -> {
            calculerCheminMode = !calculerCheminMode;
            relierMode = false; // Désactiver le mode de liaison
            calculerTableRoutageMode = false; // Désactiver le mode de calcul de la table de routage
            commutateurSource = null;
            commutateurDestination = null;
            couleurOrigine(); // Réinitialiser les couleurs des commutateurs
        });
        toolBar.add(calculerCheminButton);

        JButton calculerTableRoutageButton = new JButton("Calculer Table de Routage");
        calculerTableRoutageButton.addActionListener(e -> {
            calculerTableRoutageMode = !calculerTableRoutageMode;
            relierMode = false; // Désactiver le mode de liaison
            calculerCheminMode = false; // Désactiver le mode de calcul du chemin le plus court
            commutateurSelectionne = null;
            couleurOrigine(); // Réinitialiser les couleurs des commutateurs
        });
        toolBar.add(calculerTableRoutageButton);

        frame.add(toolBar, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private static void couleurOrigine() {
        for (Commutateur commutateur : reseau.commutateurs) {
            commutateur.setCouleur(Color.BLUE); // Réinitialiser toutes les couleurs des commutateurs
        }
    }

    private static void couleurOrigineSelect() {
        for (Commutateur commutateur : reseau.commutateurs) {
            if (!commutateur.equals(commutateurSelectionne)) {
                commutateur.setCouleur(Color.BLUE); // Réinitialiser les couleurs sauf le commutateur sélectionné
            }
        }
    }
    private static Commutateur trouverCommutateur(int x, int y) {
        for (Commutateur commutateur : reseau.commutateurs) {
            if (x >= commutateur.x && x <= commutateur.x + 30 && y >= commutateur.y && y <= commutateur.y + 30) {
                return commutateur;
            }
        }
        return null;
    }

    private static void lieeCommutateurs() {
        if (commutateurSource != null && commutateurDestination != null && !commutateurSource.equals(commutateurDestination)) {
            int ponderation = choisirPonderation();
            if (ponderation >= 0) {
                commutateurSource.ajouterVoisin(commutateurDestination, ponderation);
                commutateurDestination.ajouterVoisin(commutateurSource, ponderation);
                commutateurSource = null;
                commutateurDestination = null;
                panel.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner deux commutateurs différents pour les relier.");
        }
    }

    private static int choisirPonderation() {
        String ponderationStr = JOptionPane.showInputDialog("Entrez la pondération du lien (0 pour annuler) :");
        if (ponderationStr != null && !ponderationStr.isEmpty()) {
            try {
                int ponderation = Integer.parseInt(ponderationStr);
                if (ponderation >= 0) {
                    return ponderation;
                }
            } catch (NumberFormatException e) {
                // Ignorer et continuer
            }
        }
        // Annuler
        return -1;
    }

    private static void creerCommutateur(int x, int y) {
        String nom = JOptionPane.showInputDialog("Entrez le nom du commutateur :");
        if (nom != null && !nom.isEmpty()) {
            Commutateur commutateur = new Commutateur(nom, x, y);
            reseau.ajouterCommutateur(commutateur);
            commutateur.setCouleur(Color.BLUE); // Définir la couleur du nouveau commutateur
            panel.repaint();
        }
    }

    private static void calculerCheminPlusCourt(Commutateur source, Commutateur destination) {
        List<Commutateur> chemin = reseau.calculerCheminPlusCourt(source, destination);
        if (chemin != null && !chemin.isEmpty()) {
            StringBuilder message = new StringBuilder("Chemin le plus court trouvé : ");
            for (Commutateur commutateur : chemin) {
                message.append(commutateur.nom).append(" -> ");
            }
            message.delete(message.length() - 4, message.length()); // Supprimer le dernier "->"
            JOptionPane.showMessageDialog(null, message.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Aucun chemin trouvé entre ces deux commutateurs.");
        }
    }

    private static void afficherTableRoutage(String tableRoutage) {
        JOptionPane.showMessageDialog(null, tableRoutage, "Table de routage", JOptionPane.INFORMATION_MESSAGE);
    }
}
            
