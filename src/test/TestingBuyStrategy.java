package test;
import main.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class does some tests to ensure UserBuyStrategy.java works properly.
 */

public class TestingBuyStrategy {

    @Test
    public void testBuying(){
        String info = "04 TheMostFunGameEver  ThisMan1        FreeGameMan";
        Game game = new Game("TheMostFunGameEver", 10);

        ArrayList<Game> games = new ArrayList<Game>();
        games.add(game);

        ArrayList<User> users = new ArrayList<User>();
        SellStandardUser user1 = new SellStandardUser("ThisMan1", 3000, games, true);
        BuyStandardUser user2 = new BuyStandardUser("FreeGameMan", 50);

        users.add(user1);
        users.add(user2);

        UserBuyStrategy buyStrategy = new UserBuyStrategy();
        buyStrategy.run(info, users, games);

        Game userGame = user1.getInventory().get(0);

        assertEquals(userGame.getName(), "TheMostFunGameEver");
        assertEquals(user2.getInventory().size(), 1);
    }

}
