package test;
import main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class does some basic tests to ensure StoreSystem.java works properly, and checks if
 * UserInitialFunctionality.java works properly.
 * Also checks that each strategy runs through the StoreSystem.
 */

public class TestStoreSystemRun {

    @Test
    public void testForNoError(){
        StoreSystem system = new StoreSystem();
        system.runSystem();
    }

    @Test
    public void testUserCreation(){
        // Add the following lines to daily.txt:
        //01 Pizzaman       BS 000005.00
        StoreSystem system = new StoreSystem();
        system.runSystem();
        assertEquals(system.users.get(0).getUsername(), "Pizzaman");
    }

    @Test
    public void testUserLogOut(){
        // Add the following lines to daily.txt:
        //01 Pizzaman       BS 000005.00
        //10 Pizzaman       BS 000005.00
        StoreSystem system = new StoreSystem();
        system.runSystem();
        assertEquals(system.users.get(0).isLoggedIn(), false);
    }

    @Test
    public void testAddCredit(){
        // Add the following lines to daily.txt:
        //01 Pizzaman       BS 000005.00
        //06 Pizzaman       BS 000010.50
        StoreSystem system = new StoreSystem();
        system.runSystem();
        assertEquals(system.users.get(0).getBalance(), 15.50f);
    }

    @Test
    public void testDelete(){
        // Add the following lines to daily.txt:
        //01 Pizzaman       BS 000005.00
        //02 Pizzaman       BS 000010.50
        StoreSystem system = new StoreSystem();
        system.runSystem();
        assertEquals(true, system.users.size() == 0);
    }

    @Test
    public void testSelling(){
         /* Add the following lines to daily.txt:
        01 Pizzaman       FS 005005.00
        01 FreeGameMan    BS 000010.50
        03 SuperGameManGame    Pizzaman      00.05 000.50
        */

        StoreSystem system = new StoreSystem();
        system.runSystem();
        FullStandardUser user = null;
        for (User u : system.users){
            if (u.getUsername().equals("Pizzaman")){
                user = (FullStandardUser) u;
            }
        }

        assertEquals(user.getSellable().get(0).getName(), "SuperGameManGame");
    }

    @Test
    public void testBuying(){
        /* Add the following lines to daily.txt:
        01 Pizzaman       FS 005005.00
        01 FreeGameMan    BS 000010.50
        03 SuperGameManGame    Pizzaman      00.05 000.50
        01 BuyerMan       BS 000005.00
        04 SuperGameManGame    Pizzaman        BuyerMan
        */

        StoreSystem system = new StoreSystem();
        system.runSystem();

        User user = null;

        for (User u : system.users){
            if (u.getUsername().equals("BuyerMan")){
                user = u;
            }
        }

        assertEquals(user.getInventory().size(), 1);
    }

    @Test
    public void testGifting(){
        /* Add the following lines to daily.txt:
        01 Pizzaman       FS 005005.00
        01 FreeGameMan    BS 000010.50
        03 SuperGameManGame    Pizzaman      00.05 000.50
        01 BuyerMan       BS 000005.00
        04 SuperGameManGame    Pizzaman        BuyerMan
        Then have another daily.txt file running this:
        01 godModeMan     AA 005005.00
        00 BuyerMan       BS 000000.00
        09 SuperGameManGame    BuyerMan        FreeGameMan
        */

        StoreSystem system = new StoreSystem();
        system.runSystem();
        User user = null;

        for (User u : system.users){
            if (u.getUsername().equals("FreeGameMan")){
                user = u;
            }
        }
        assertEquals(user.getInventory().get(0).getName(), "SuperGameManGame");
    }


}
