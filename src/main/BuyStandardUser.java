package main;


import java.util.ArrayList;

/**
 * A type of user that can only buy
 */

public class BuyStandardUser extends User{

    /**
     * Creates a BuyStandardUser
     * @param name the username
     * @param balance the user's balance
     */
    public BuyStandardUser(String name, float balance){
        super(name, balance);
    }

    /**
     * Creates a BuyStandardUser
     * @param name the username
     * @param balance the user's balance
     * @param inventory the inventory that this user has.
     * @param loggedIn the boolean used to check if the user is online or not.
     */
    public BuyStandardUser(String name, float balance, ArrayList<Game> inventory, boolean loggedIn){
        super(name, balance, inventory);

        if (loggedIn) {
            this.logIn();
        } else{
            this.logOff();
        }
    }


    /**
     * Buys a game for this user if all conditions are met.
     * @param game the game being bought.
     */
    public void buyGame(Game game){
        float cost = game.getCost();

        if (getBalance() < cost){
            System.out.println("ERROR: User has insufficient balance.");
            return;
        }

        if (this.getInventory().contains(game)){
            System.out.println("ERROR: User already owns this game.");
        } else {
            this.addToInventory(game);
            this.payAmount(cost);
            Purchase purchase = new Purchase(game,true);
            this.addPurchase(purchase);
        }

    }

    /**
     * @return Returns string representation of this user.
     */
    @Override
    public String toString(){
        String info = this.getUsername() + "\n";
        info += "0|" +this.getBalance() + "|";

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
