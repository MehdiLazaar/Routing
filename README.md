# Network Project - Switch Simulation

This project represents a network simulation using switches and machines. It allows visualizing the connections between switches, calculating the shortest paths between them, and generating routing tables.

## Technologies Used

- Java
- AWT and Swing for the graphical interface

## Main Classes

### 1. `Commutateur` (Switch)

This class represents a switch in the network. It contains the following attributes:

- `nom` : the name of the switch.
- `voisins` : a map that stores neighboring switches and link weights.
- `x`, `y` : coordinates for graphical display.
- `distances` : a map to store distances to neighbors.
- `precedents` : a map to trace the path when calculating distances.
- `marque` : an indicator to know if the switch has been visited in Dijkstra's algorithm.
- `couleur` : the color used for the graphical display of the switch.

#### Key Methods

- `ajouterVoisin(Commutateur voisin, int ponderation)`: Adds a neighbor to the switch with a given weight.
- `calculerTableRoutage()`: Calculates the routing table for the switch using Dijkstra's algorithm.

### 2. `Machine` (Machine)

This class represents a machine in the network.

- `nom` : the name of the machine.

### 3. `Reseau` (Network)

This class represents the entire network, which contains a list of switches and machines.

#### Key Methods

- `ajouterCommutateur(Commutateur commutateur)`: Adds a switch to the network.
- `calculerCheminPlusCourt(Commutateur source, Commutateur destination)`: Calculates the shortest path between two switches using Dijkstra's algorithm.

### 4. `Main`

The main class that initializes the application and manages the user interface.

#### Features

- Creation of switches via the graphical interface.
- Linking switches with weights.
- Calculation of the shortest path between two switches.
- Display of the routing table for a selected switch.

## Usage Instructions

1. **Run the application**: Execute the `Main` class.
2. **Create switches**: Click on the drawing area to create new switches.
3. **Link switches**: Enable "Link Switches" mode and click on two switches to connect them with a weight.
4. **Calculate a path**: Enable "Calculate Shortest Path" mode and select two switches to display the shortest path.
5. **Display the routing table**: Enable "Calculate Routing Table" mode and select a switch to see its routing table.

## Authors

- Mehdi Lazaar (LMehdi)

## License

This project is under the MIT license.


---

# Projet Réseau - Simulation de Commutateurs

Ce projet représente une simulation de réseau en utilisant des commutateurs et des machines. Il permet de visualiser les connexions entre les commutateurs, de calculer des chemins les plus courts entre eux, et de générer des tables de routage.

## Technologies Utilisées

- Java
- AWT et Swing pour l'interface graphique

## Classes Principales

### 1. `Commutateur`

Cette classe représente un commutateur dans le réseau. Elle contient les attributs suivants :

- `nom` : le nom du commutateur.
- `voisins` : une carte qui stocke les commutateurs voisins et les pondérations des liens.
- `x`, `y` : les coordonnées pour l'affichage graphique.
- `distances` : une carte pour stocker les distances aux voisins.
- `precedents` : une carte pour retracer le chemin lors du calcul des distances.
- `marque` : un indicateur pour savoir si le commutateur a été visité dans l'algorithme de Dijkstra.
- `couleur` : la couleur utilisée pour l'affichage graphique du commutateur.

#### Méthodes Clés

- `ajouterVoisin(Commutateur voisin, int ponderation)`: Ajoute un voisin au commutateur avec une pondération donnée.
- `calculerTableRoutage()`: Calcule la table de routage pour le commutateur en utilisant l'algorithme de Dijkstra.

### 2. `Machine`

Cette classe représente une machine dans le réseau.

- `nom` : le nom de la machine.

### 3. `Reseau`

Cette classe représente le réseau entier, qui contient une liste de commutateurs et de machines.

#### Méthodes Clés

- `ajouterCommutateur(Commutateur commutateur)`: Ajoute un commutateur au réseau.
- `calculerCheminPlusCourt(Commutateur source, Commutateur destination)`: Calcule le chemin le plus court entre deux commutateurs à l'aide de l'algorithme de Dijkstra.

### 4. `Main`

La classe principale qui initialise l'application et gère l'interface utilisateur.

#### Fonctionnalités

- Création de commutateurs via l'interface graphique.
- Liaison de commutateurs avec des pondérations.
- Calcul du chemin le plus court entre deux commutateurs.
- Affichage de la table de routage pour un commutateur sélectionné.

## Instructions d'Utilisation

1. **Lancer l'application** : Exécutez la classe `Main`.
2. **Créer des commutateurs** : Cliquez sur la zone de dessin pour créer de nouveaux commutateurs.
3. **Relier des commutateurs** : Activez le mode "Relier Commutateurs" et cliquez sur deux commutateurs pour les relier avec une pondération.
4. **Calculer un chemin** : Activez le mode "Calculer Chemin Plus Court" et sélectionnez deux commutateurs pour afficher le chemin le plus court.
5. **Afficher la table de routage** : Activez le mode "Calculer Table de Routage" et sélectionnez un commutateur pour voir sa table de routage.

## Auteurs

- - Mehdi Lazaar (LMehdi)

## License

Ce projet est sous la licence MIT.
