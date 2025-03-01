# Othello Game

Un jeu d'Othello (Reversi) implémenté en Java avec JavaFX, utilisant une architecture MVC et des design patterns avancés.

![Othello](https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Othello-Standard-Board.jpg/240px-Othello-Standard-Board.jpg)

## Description du projet

Ce projet est une implémentation du jeu de société Othello, également connu sous le nom de Reversi. Le jeu se déroule sur une grille 8x8 où deux joueurs s'affrontent en plaçant des pions noirs et blancs, avec pour objectif de posséder le plus grand nombre de pions à la fin de la partie.

## Fonctionnalités

- Interface graphique JavaFX complète
- Jeu à deux joueurs sur le même ordinateur
- Mode IA avec algorithme Minimax et élagage alpha-beta
- Fonctionnalités Undo/Redo pour revenir en arrière ou refaire des coups
- Affichage des scores en temps réel
- Mise en évidence des coups valides
- Indication visuelle du dernier coup joué

## Règles du jeu

1. Le jeu commence avec 4 pions placés au centre du plateau : 2 noirs et 2 blancs disposés en diagonale.
2. Les joueurs placent leurs pions à tour de rôle.
3. Un coup valide doit encadrer au moins un pion adverse entre le nouveau pion placé et un pion de sa couleur déjà présent sur le plateau.
4. Tous les pions adverses encadrés sont retournés et deviennent de la couleur du joueur qui a joué.
5. Si un joueur ne peut pas jouer, le tour passe à l'adversaire.
6. Le jeu se termine lorsque aucun des joueurs ne peut plus jouer ou lorsque le plateau est plein.
7. Le joueur ayant le plus de pions de sa couleur sur le plateau remporte la partie.

## Architecture du projet

Le projet est structuré selon l'architecture Modèle-Vue-Contrôleur (MVC) :

### Modèle
- `Game` : Gère l'état du jeu, les joueurs et les règles
- `Grid` : Représente le plateau de jeu et implémente la logique de placement des pions
- `Player` : Représente un joueur avec sa couleur et son score
- `Pawn` : Représente un pion sur le plateau

### Vue
- `OthelloView` : Gère l'affichage du jeu, des pions et des messages
- `JavaFXBoardGameView` : Implémente l'interface utilisateur avec JavaFX

### Contrôleur
- `OthelloController` : Gère les interactions entre le modèle et la vue, traite les actions utilisateur

### Patrons de conception utilisés
- **Iterator** : Utilisation de l'interface standard `java.util.Iterator` avec `GridIterator` pour parcourir le plateau de jeu
- **Command** : `MoveCommand` pour implémenter les fonctionnalités undo/redo
- **Factory** : `ShapeFactory` pour la création des formes graphiques

### Intelligence Artificielle
- Implémentation d'un algorithme Minimax avec élagage alpha-beta
- Profondeur d'analyse ajustable (actuellement 5 niveaux)
- Matrice de poids pour l'évaluation des positions (les coins sont plus valorisés)
- Mécanisme de timeout pour garantir des temps de réponse raisonnables

## Comment exécuter le projet

### Prérequis
- Java 17 ou supérieur
- Gradle (inclus via le wrapper)

### Compilation et exécution
```bash
./gradlew run
```

### Tests
```bash
./gradlew test
```

### Créer une distribution
```bash
./gradlew jlinkZip
```
Le fichier zip sera créé dans le répertoire `build/distributions/`.

## Commandes de jeu

- **New Game** : Démarre une nouvelle partie
- **Undo** : Annule le dernier coup joué
- **Redo** : Rejoue un coup annulé
- **AI Mode** : Active/désactive le mode contre l'IA

## Structure des tests

Des tests unitaires sont disponibles pour les principales classes du modèle :
- `TestGame` : Tests pour la classe Game
- `TestGrid` : Tests pour la classe Grid
- `TestPlayer` : Tests pour la classe Player
- `TestMinimaxOthelloAI` : Tests pour l'algorithme d'IA

## Contributions récentes

- Refactorisation de l'implémentation des itérateurs pour utiliser l'interface standard `java.util.Iterator` au lieu d'une interface personnalisée
- Amélioration de l'algorithme IA avec une meilleure évaluation des positions
- Correction de bugs dans le contrôleur pour une meilleure gestion des coups

## Licence

Ce projet est destiné à des fins éducatives.