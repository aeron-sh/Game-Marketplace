package main;
import java.util.ArrayList;

/**
 * A SellStandardUser can only sell.
 */

public class SellStandardUser extends User{

    /**
     * A user that can only sell.
     * @param name the username of this user
     * @param balance the balance of this user
     */
    public SellStandardUser(String name, float balance) {
        super(name, balance);
    }

    /**
     * A user that can only sell.
     * @param name the username of this user
     * @param balance the balance of this user
     * @param inventory the inventory of games that this user sells
     * @param loggedIn the boolean determining whether this user is logged in or not.
     */
    public SellStandardUser(String name, float balance, ArrayList<Game> inventory, boolean loggedIn) {
        super(name, balance, inventory);
        this.loggedIn = loggedIn;
    }

    /**
     * Called when this user sells a game so that credit can be added to their account.
     * @param game the game being sold
     * @param price the price of the game.
     */
    public void sell(Game game, float price) {
        if (!this.exist(game)){
            System.out.println("ERROR: This user does not sell the game " + game.getName());
            return;
        }
        this.addBalance(price);
    }

    /**
     * Adds a game on sale for this user.
     * @param game the game being put on sale by this user.
     */
    public void putOnSale(Game game){
        if (this.getInventory().contains(game)){
            System.out.println("ERROR: Game being sold is already sold by this user.");
            return;
        }
        this.getInventory().add(game);
    }

    /**
     * Returns true iff this user is selling the specified game.
     * @param game the game being checked.
     * @return true iff the user is selling this game, false otherwise.
     */
    public boolean exist(Game game) {
        return this.getInventory().contains(game);
    }


    /**
     * @return Returns string representation of this user.
     */
    @Override
    public String toString(){
        String info = this.getUsername() + "\n";
        info += "1|" +this.getBalance() + "|";

        if (this.isLoggedIn()){
            info += "0\n";
        } else {
            info += "1\n";
        }

        for (Game game : this.getInventory()){
            info += game.getName() + "\n";
        }
        return info;
    }
}
