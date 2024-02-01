package test;
import main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class does some tests to ensure UserGiftStrategy.java works properly.
 */

public class TestGiftStrategy {

    @Test
    public void testGifting(){
        String info = "09 TheMostFunGameEver  ThisMan1        FreeGameMan";
        Game game = new Game("TheMostFunGameEver", 10);

        ArrayList<Game> games = new ArrayList<Game>();
        games.add(game);

        ArrayList<User> users = new ArrayList<User>();
        BuyStandardUser user1 = new BuyStandardUser("ThisMan1", 3000, games, true);
        BuyStandardUser user2 = new BuyStandardUser("FreeGameMan", 0,
                new ArrayList<Game>(), false);

        users.add(user1);
        users.add(user2);

        UserGiftStrategy gift = new UserGiftStrategy();
        gift.run(info, users, games);

        Game userGame = user2.getInventory().get(0);

        assertEquals(userGame.getName(), "TheMostFunGameEver");
        assertEquals(user1.getInventory().size(), 0);
    }

    @Test
    public void testAdminGifting(){
        String info = "09 TheMostFunGameEver  ThisMan1        FreeGameMan";
        Game game = new Game("TheMostFunGameEver", 10);

        ArrayList<Game> games = new ArrayList<Game>();
        game.notFresh();
        games.add(game);

        ArrayList<User> users = new ArrayList<User>();
        Admin user1 = new Admin("ThisMan1", 3000, new ArrayList<Game>(),
                new ArrayList<Game>(), true);
        BuyStandardUser user2 = new BuyStandardUser("FreeGameMan", 0,
                new ArrayList<Game>(), false);

        users.add(user1);
        users.add(user2);

        UserGiftStrategy gift = new UserGiftStrategy();
        gift.run(info, users, games);

        Game userGame = user2.getInventory().get(0);

        assertEquals(userGame.getName(), "TheMostFunGameEver");
        assertEquals(user1.getInventory().size(), 0);
    }

    @Test
    public void testAdminGameRemoval(){
        String info = "09 TheMostFunGameEver  ThisMan1        FreeGameMan";
        Game game = new Game("TheMostFunGameEver", 10);

        ArrayList<Game> games = new ArrayList<Game>();
        game.notFresh();
        games.add(game);

        ArrayList<User> users = new ArrayList<User>();
        Admin user1 = new Admin("ThisMan1", 3000, new ArrayList<Game>(),
                new ArrayList<Game>(), true);
        BuyStandardUser user2 = new BuyStandardUser("FreeGameMan", 0,
                new ArrayList<Game>(), false);

        users.add(user1);
        users.add(user2);

        UserGiftStrategy gift = new UserGiftStrategy();
        gift.run(info, users, games);

        assertEquals(games.size(), 1);

    }



}
