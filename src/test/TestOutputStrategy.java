package test;
import main.BuyStandardUser;
import main.User;
import main.FullStandardUser;
import main.SystemEndStrategy;
import main.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * The class for testing the SystemEndStrategy.
 */
public class TestOutputStrategy{
    ArrayList<User> users;
    ArrayList<Game> games;

    @BeforeEach
    public void setUp() throws Exception {
        ArrayList<User> users = new ArrayList<User>();

        BuyStandardUser user = new BuyStandardUser("Nitro", 5);
        users.add(user);
        this.users = users;

        this.games = new ArrayList<Game>();
        Game game1 = new Game("Minecraft", 10);
        Game game2 = new Game("Valorant", 5);
        this.games.add(game1);
        this.games.add(game2);
    }

    @Test
    public void BaseTest(){
        SystemEndStrategy strat = new SystemEndStrategy();
        strat.writeUserFile(this.users);
    }

    @Test
    public void UsersWithGamesTest(){
        SystemEndStrategy strat = new SystemEndStrategy();
        Game game = new Game("Fortnite", 20, false, 0);
        ArrayList<Game> games = new ArrayList<Game>();
        games.add(game);
        games.add(game);
        games.add(game);
        this.users.add(new BuyStandardUser("Nitro2", 5, games, true));
        strat.writeUserFile(this.users);
    }

    @Test
    public void GamesOutputTest(){
        SystemEndStrategy strat = new SystemEndStrategy();
        strat.writeGameFile(this.games);
    }

    @Test
    public void GamesDiscountOutputTest(){
        SystemEndStrategy strat = new SystemEndStrategy();
        Game game = new Game("Valheim", 10.22f, true, 0.5f);
        this.games.add(game);
        strat.writeGameFile(this.games);
    }
}
