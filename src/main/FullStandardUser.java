package main;

import java.util.ArrayList;

public class FullStandardUser extends User{
    /**
     * a user class that has the functionalities of both buying and selling games
     *
     */

    private ArrayList<Game> inventory;
    private ArrayList<Game> sellable;

    /**
     * initialises a new FullStandardUser with both buy and sell capabilities
     * @param name the users name
     * @param balance the money the user has available
     */
    public FullStandardUser(String name, float balance) {
        super(name, balance);
    }

    /**
     * initialises a new FullStandardUser with both buy and sell capabilities
     * @param name the users name
     * @param balance the money the user has available
     * @param inventory the inventory of games this user has bought
     */
    public FullStandardUser(String name, float balance, ArrayList<Game> inventory) {
        super(name, balance,inventory);
        this.sellable = new ArrayList<>();
    }

    /**
     * initialises a new FullStandardUser with both buy and sell capabilities
     * @param name the users name
     * @param balance the money the user has available
     * @param inventory the inventory of games this user has bought
     * @param sellable the inventory of games that this user sells
     * @param loggedIn the boolean showing if this user is logged in or not
     */
    public FullStandardUser(String name, float balance, ArrayList<Game> inventory, ArrayList<Game> sellable, 
                            boolean loggedIn) {
        super(name, balance,inventory);
        this.sellable = sellable;
        this.loggedIn = loggedIn;
    }

    /**
     * Buys a game for this user.
     * @param game the game being bought.
     * @param cost the cost of the game.
     */
    public void buyGame(Game game, float cost){

        if (getBalance() < cost){
            System.out.println("ERROR: User has insufficient balance.");
            return;
        }

        if (this.owns(game)){
            System.out.println("ERROR: User already owns this game.");
        } else {
            this.addToInventory(game);
            this.payAmount(cost);
            Purchase purchase = new Purchase(game,true);
            this.addPurchase(purchase);
        }
    }

    /**
     * Adds funds to this user's account when their game is sold.
     * @param game the game being sold.
     * @param cost the cost of the game that was sold.
     */
    public void sell(Game game, float cost){
        if (!this.sellable.contains(game)){
            System.out.println("ERROR: Game being sold is not sold by this user.");
            return;
        }
        this.payAmount(-cost);
    }

    /**
     * Adds a game on sale for this user.
     * @param game the game being put on sale by this user.
     */
    public void putOnSale(Game game){
        if (this.sellable.contains(game)){
            System.out.println("ERROR: Game being sold is already sold by this user.");
            return;
        }
        this.sellable.add(game);
    }

    /**
     * Adds a game for this user to sell.
     * @param game the game being sold.
     */
    public void addGame(Game game){
        this.sellable.add(game);
    }

    /**
     * Returns an arraylist of the games being sold by this user.
     * @return
     */
    public ArrayList<Game> getSellable() {
        return sellable;
    }

    /**
     * Returns true iff the user is owns the given game.
     * @param game the game being checked
     * @return
     */
    public boolean owns(Game game){
        return this.sellable.contains(game) || this.inventory.contains(game);
    }

    /**
     * @return Returns string representation of this user.
     */
    @Override
    public String toString(){
        String info = this.getUsername() + "\n";
        info += "2|" +this.getBalance() + "|";

        if (this.isLoggedIn()){
            info += "0\n";
        } else {
            info += "1\n";
        }

        for (Game game : this.getInventory()){
            info += game.getName() + "\n";
        }

        info += "<SELL________________GAMES>\n";

        for (Game game : this.sellable){
            info += game.getName() + "\n";
        }

        return info;
    }


}
