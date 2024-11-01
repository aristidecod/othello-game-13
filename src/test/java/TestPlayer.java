import fr.univ_amu.m1info.board_game_library.Color;
import fr.univ_amu.m1info.board_game_library.Grid;
import fr.univ_amu.m1info.board_game_library.Pion;
import fr.univ_amu.m1info.board_game_library.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    private Player player;
    private Grid grid;

    @BeforeEach
    public void setUp() {
        player = new Player("Player 1", Color.BLACK);
        grid = new Grid();
    }

    @Test
    public void testGetName() {
        assertEquals("Player 1", player.getName(), "Le nom du joueur doit être 'Player 1'");
    }

    @Test
    public void testGetColor() {
        assertEquals(Color.BLACK, player.getColor(), "La couleur du joueur doit être noire (BLACK)");
    }

    @Test
    public void testInitialScore() {
        assertEquals(0, player.getScore(), "Le score initial du joueur doit être 0");
    }

    @Test
    public void testSetScore() {
        player.setScore(5);
        assertEquals(5, player.getScore(), "Le score du joueur doit être mis à jour à 5");
    }

    @Test
    public void testPlayValidMove() {
        boolean result = player.play(5, 7, grid);
        // Vérifie que le coup a été joué et que le pion a été placé
        assertTrue(result, "Le coup doit être valide");
        assertEquals(Color.BLACK, grid.getPion(5, 7).getColor(), "Le pion doit être placé en (5, 7) avec la couleur noire");
    }

    @Test
    public void testPlayInvalidMove() {
        boolean result = player.play(9, 9, grid);
        // Vérifie que le coup n'a pas été joué et qu'aucun pion n'a été placé
        assertFalse(result, "Le coup doit être invalide");
    }

    @Test
    public void testCalculateScoreAndPercentage() {
        // Le joueur joue plusieurs coups
        player.play(0, 0, grid);
        player.play(1, 0, grid);
        player.play(2, 0, grid);

        // Un autre joueur ou des pions de couleur différente sont ajoutés manuellement
        grid.placePion(0, 1, new Pion(Color.WHITE));
        grid.placePion(1, 1, new Pion(Color.WHITE));
        grid.placePion(2, 1, new Pion(Color.WHITE));

        player.calculateScore(grid);
        assertEquals(5, player.getScore(), "Le score doit être calculé en fonction du nombre de pions de la couleur du joueur sachant qu'il y'a deja deux pions placer");
    }

}
