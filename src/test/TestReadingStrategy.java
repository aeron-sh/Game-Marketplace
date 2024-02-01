package test;
import main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * The class for testing the SystemReadDatabaseStrategy.
 */
public class TestReadingStrategy{
    ArrayList<User> users;
    ArrayList<Game> games;

    @BeforeEach
    public void setUp() throws Exception {
        this.users = new ArrayList<User>();
        this.games = new ArrayList<Game>();

        SystemEndStrategy strat = new SystemEndStrategy();
        Game game = new Game("That Game 2", 20, false, 0);
        ArrayList<Game> games = new ArrayList<Game>();
        this.games.add(game);
        this.users.add(new BuyStandardUser("Nitro2", 5, this.games, true));
        strat.run("", this.users, this.games);
    }

    @Test
    public void testBuyUser(){
        ArrayList<User> oldUsers = this.users;
        ArrayList<Game> oldGames = this.games;

        this.users = new ArrayList<User>();
        this.games = new  ArrayList<Game>();

        SystemReadDatabaseStrategy strat = new SystemReadDatabaseStrategy();
        strat.run("", this.users, this.games);

        for (Game game: oldGames){
            for (Game game2: this.games){
                assertEquals(game2.getName(), game.getName());
            }
        }

        for (User user: oldUsers){
            for (User user2: this.users){
                assertEquals(user2.getUsername(), user.getUsername());
            }
        }
    }

    @Test
    public void testFullUser(){
        this.users = new ArrayList<User>();
        this.games = new ArrayList<Game>();

        ArrayList<Game> owned = new ArrayList<Game>();
        ArrayList<Game> sold = new ArrayList<Game>();
        Game g1 = new Game("Test1", 1);
        Game g2 = new Game("Test2", 2);
        owned.add(g1);
        sold.add(g2);

        FullStandardUser full = new FullStandardUser("Full", 10, owned, sold, false);
        this.users.add(full);
        this.games.add(g1);
        this.games.add(g2);

        SystemEndStrategy strat = new SystemEndStrategy();
        strat.run("", this.users, this.games);

        ArrayList<User> oldUsers = this.users;
        ArrayList<Game> oldGames = this.games;

        this.users = new ArrayList<User>();
        this.games = new ArrayList<Game>();

        SystemReadDatabaseStrategy strat2 = new SystemReadDatabaseStrategy();
        strat2.run("", this.users, this.games);

        FullStandardUser user = (FullStandardUser) this.users.get(0);
        ArrayList<Game> sellable = user.getSellable();

        assertEquals(sellable.get(0).getName(), oldGames.get(1).getName());


    }

    @Test
    public void testTwoUsers(){
        this.users = new ArrayList<User>();
        this.games = new ArrayList<Game>();

        ArrayList<Game> owned = new ArrayList<Game>();
        ArrayList<Game> sold = new ArrayList<Game>();
        Game g1 = new Game("Test1", 1);
        Game g2 = new Game("Test2", 2);
        owned.add(g1);
        sold.add(g2);

        FullStandardUser full = new FullStandardUser("Full", 10, owned, sold, false);
        this.users.add(full);
        this.games.add(g1);
        this.games.add(g2);

        BuyStandardUser buy = new BuyStandardUser("Buy", 5, owned, false);
        this.users.add(buy);

        SystemEndStrategy strat = new SystemEndStrategy();
        strat.run("", this.users, this.games);

        ArrayList<User> oldUsers = this.users;
        ArrayList<Game> oldGames = this.games;

        this.users = new ArrayList<User>();
        this.games = new ArrayList<Game>();

        SystemReadDatabaseStrategy strat2 = new SystemReadDatabaseStrategy();
        strat2.run("", this.users, this.games);

        assertEquals(true, this.users.size() == 2);

    }
}
