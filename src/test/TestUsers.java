package test;

import main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing all the user functionality.
 */
public class TestUsers{
    ArrayList<User> users;

    @BeforeEach
    public void setUp() {

        this.users = new ArrayList<User>();
        BuyStandardUser user1 = new BuyStandardUser("Nitro", 5);
        SellStandardUser user2 = new SellStandardUser("Aaron", 8);
        FullStandardUser user3 = new FullStandardUser("Toast", 20);
        Admin user4 = new Admin("Mystro", 1500);
        this.users.add(user1);
        this.users.add(user2);
        this.users.add(user3);
        this.users.add(user4);
    }

    @Test
    public void testAddBalanceAdmin(){

        Admin admin = null;
        for (User user: this.users) {
            if (user instanceof Admin) {
                admin = (Admin) user;
            }
        }

        for (User user: this.users) {
            assert admin != null;
            admin.addBalance(50, user);
            assertTrue(user.getBalance() >= 50);
        }
    }

    @Test
    public void testEquals(){
        BuyStandardUser user = new BuyStandardUser("Nitro", 10);
        BuyStandardUser user1 = new BuyStandardUser("Nitro", 15);

        boolean checker = user.equals(user1);
        assertTrue(checker);

    }

    @Test
    public void testRemoveUser(){
        FullStandardUser user = new FullStandardUser("Toast", 11);
        this.users.remove(user);
        for (User user1: this.users) {
            assertNotEquals(user1, user);
        }
    }
}
