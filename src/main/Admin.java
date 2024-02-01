package main;

import java.util.ArrayList;

public class Admin extends User{


    ArrayList<Game> inventory;
    ArrayList<Game> sellable;

    /**
     *
     * @param name the username for this Admin user
     * @param balance the balance they begin with
     */
    public Admin(String name, float balance) {
        super(name, balance);
        this.sellable = new ArrayList<>();
    }

    /**
     *
     * @param name the username for this Admin user
     * @param balance the balance they begin with
     * @param inventory the games the user owns and cannot sell
     */
    public Admin(String name, float balance, ArrayList<Game> inventory) {
        super(name, balance, inventory);
        this.sellable = new ArrayList<>();
    }

    /**
     *
     * @param name the username for this Admin user
     * @param balance the balance they begin with
     * @param inventory the games the user owns and cannot sell
     * @param sellable the games this user has on sale
     */
    public Admin(String name, float balance, ArrayList<Game> inventory, ArrayList<Game> sellable){
        super(name, balance, inventory);
        this.sellable = sellable;
    }

    /**
     *
     * @param name the username for this Admin user
     * @param balance the balance they begin with
     * @param inventory the games the user owns and cannot sell
     * @param sellable the games this user has on sale
     * @param loggedin if the yser is currently logged in or not
     */
    public Admin(String name, float balance, ArrayList<Game> inventory, ArrayList<Game> sellable,
                            boolean loggedin) {
        super(name, balance,inventory);
        this.sellable = sellable;
        this.loggedIn = loggedin;
    }


    /**
     * buy a new game
     * @param game the game to be bought
     * @param cost the cost of the game
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
     * buy a new game
     * @param game the game to be bought
     * @param cost the cost of the game
     */
    public void sell(Game game, float cost){
        if (this.sellable.contains(game)){
            return;
        }
        this.payAmount(-cost);
    }

    /**
     * add credits credit to the users balance
     * @param credit the credits to add
     * @param user the user to add them to
     */
    public void addBalance(int credit, User user){
        user.addBalance(credit);
    }


    /**
     *
     * @return the games this user is selling
     */
    public ArrayList<Game> getSellable() {
        return sellable;
    }

    /**
     * remove the game from either the sellers inventory or the games this user is selling
     * @param game the game to be removed
     */
    public void removefromInventory(Game game){
        this.inventory.remove(game);
        this.sellable.remove(game);
    }

    /**
     * removes Game object game from the users inventory
     * @param game the game being removed
     * @param user the user to remove it from
     */
    public void removeGame(Game game, User user){
        user.removeFromInventory(game);
    }


    /**
     * adds Game game to the specified users inventory
     * @param game the game to be added
     * @param user the user that you add it to
     */
    public void gift(Game game, User user){
        user.addToInventory(game);
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
     *
     * @param game the game to check
     * @return true if the game is within the users inventory or its a game that the user is selling
     */
    public boolean owns(Game game){
        return this.sellable.contains(game) || this.inventory.contains(game);
    }

    /**
     * Activates/Deactivates a auction sale
     * @param games the list of games that the sale is happening to
     */
    public void auctionSale(ArrayList<Game> games) {
        if (games.size() < 1) {
            System.out.println("Error: there are no games (Auction sale)");
        }
        boolean auction = games.get(0).onDiscount();

        if (auction) {
            for (Game game : games) {
                game.stopDiscount();
                System.out.println("Auction sale has ended");
            }
        } else {
            for (Game game : games) {
                game.applyDiscount();
                System.out.println("Auction sale has begun");
            }

        }
    }


    public void refund(User buyer, User seller, float credits){
        buyer.payAmount(-credits);
        seller.payAmount(credits);
    }

    /**
     * @return Returns string representation of this user.
     */
    @Override
    public String toString(){
        String info = this.getUsername() + "\n";
        info += "3|" +this.getBalance() + "|";

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
