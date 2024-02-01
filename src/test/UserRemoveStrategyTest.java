package test;
import main.User;
import main.FullStandardUser;
import main.BuyStandardUser;
import main.UserRemoveStrategy;
import main.Game;
import main.StoreSystem;
import main.Strategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserRemoveStrategyTest {
    ArrayList<User> users;
    ArrayList<Game> games;

    @BeforeEach
    public void setUp() throws Exception {
        ArrayList<User> users = new ArrayList<User>();

        BuyStandardUser user = new BuyStandardUser("Sung", 15);
        users.add(user);
        this.users = users;

        this.games = new ArrayList<Game>();
        Game spiderman = new Game("Spiderman", 20);
        Game lol = new Game("League of Legends", 0);
        this.games.add(spiderman);
        this.games.add(lol);
        user.addToInventory(spiderman);
    }

    @Test
    public void testSplitTransaction(){
        String transaction = "08 Spiderman           Sung                           ";
        UserRemoveStrategy strat = new UserRemoveStrategy();
        strat.run(transaction, users, games);
        assertEquals("08", strat.getCode());
        assertEquals("Spiderman", strat.getTemp_gamename());
        assertEquals("Sung", strat.getTemp_username());
    }


}
