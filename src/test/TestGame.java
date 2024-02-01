package test;

import main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class TestGame {
    ArrayList<Game> games;

    @BeforeEach
    public void setUp() {
        this.games = new ArrayList<Game>();
        Game game1 = new Game("Minecraft", 10);
        Game game2 = new Game("Valorant", 5);
        Game game3 = new Game("Fortnite", 20);
        this.games.add(game1);
        this.games.add(game2);
        this.games.add(game3);
    }

    @Test
    public void testDiscount(){

        for (Game game: this.games) {
            game.applyDiscount();
            assertTrue(game.onDiscount());
            game.stopDiscount();
            assertFalse(game.onDiscount());
        }

        Game newGame = new Game("CSGO", 30);
        float percent = 10f;
        float outcome = 27f;
        newGame.applyDiscount();
        newGame.setDiscountPercent(percent);
        assertEquals(outcome, newGame.getCost());
    }

    @Test
    public void testEquals(){
        Game game = new Game("Minecraft", 21);
        Game game1 = new Game("Minecraft", 11);

        boolean checker = game.equals(game1);
        assertTrue(checker);
    }

    @Test
    public void testFresh(){
        Game game1 = new Game("GTAV", 50);
        assertTrue(game1.isFresh());
        game1.notFresh();
        assertFalse(game1.isFresh());
    }

    @Test
    public void testRemoveGame() {
        Game game = new Game("Fortnite", 1);
        this.games.remove(game);
        for (Game game1: this.games) {
            assertNotEquals(game, game1);
        }
    }
}
