import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class ReseauSwing {
    Reseau reseau;
    JPanel panel;
    Commutateur commutateurSource;
    Commutateur commutateurDestination;
    boolean relierMode;
    private boolean calculerCheminMode;

    public ReseauSwing() {
        this.reseau = new Reseau();
        this.relierMode = false;
        this.calculerCheminMode = false;
        afficherInterface();
    }

    private void afficherInterface() {
        JFrame frame = new JFrame("Réseau");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Commutateur commutateur : reseau.getCommutateurs()) {
                    g.setColor(commutateur.getCouleur());
                    g.fillOval(commutateur.getX(), commutateur.getY(), 30, 30);
                    g.drawString(commutateur.getNom(), commutateur.getX(), commutateur.getY() - 10);
                    // Dessiner les connexions entre commutateurs
                    for (Map.Entry<Commutateur, Integer> voisinEntry : commutateur.getVoisins().entrySet()) {
                        Commutateur voisin = (Commutateur) voisinEntry.getKey();
                        g.drawLine(commutateur.getX() + 15, commutateur.getY() + 15,
                                voisin.getX() + 15, voisin.getY() + 15);
                    }
                }
            }
        };

        // Ajout des boutons de contrôle
        JToolBar toolBar = new JToolBar();
        JButton boutonRelier = new JButton("Relier");
        boutonRelier.addActionListener(e -> toggleRelierMode());
        JButton boutonChemin = new JButton("Chemin Court");
        boutonChemin.addActionListener(e -> toggleCheminCourtMode());
        toolBar.add(boutonRelier);
        toolBar.add(boutonChemin);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private void toggleRelierMode() {
        relierMode = !relierMode;
    }

    private void toggleCheminCourtMode() {
        calculerCheminMode = !calculerCheminMode;
    }
}
