package main;


import java.util.ArrayList;

/**
 * An abstract class to unify some methods between Users.
 */

public abstract class User {

    private String username;
    private float balance;
    boolean loggedIn;
    private ArrayList<Game> inventory;
    private ArrayList<Purchase> purchases;

    // The absolute max values that a user can have.
    private float MAX_BALANCE = 999999.99f;
    private float DAILY_MAX_ADD_FUNDS = 1000.00f;

    public User(String name, float balance) {
        if (name.length() > 15) {
            this.username = name.substring(0, 15);
            System.out.println("WARNING: Username is too long. System has shortened it too " + this.username);
        } else {
            this.username = name;
        }

        this.balance = balance;
        this.loggedIn = true;
        this.inventory = new ArrayList<Game>();
        this.purchases = new ArrayList<Purchase>();
    }

    public User(String name, float balance, ArrayList<Game> inventory) {

        if (name.length() > 15) {
            this.username = name.substring(0, 15);
            System.out.println("WARNING: Username is too long. System has shortened it too " + this.username);
        } else {
            this.username = name;
        }

        this.balance = balance;
        this.loggedIn = true;
        this.inventory = inventory;
        this.purchases = new ArrayList<Purchase>();
    }

    /**
     * Returns the username of this user.
     *
     * @return the username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the balance tat this user has.
     *
     * @return the float value of the balance.
     */
    public float getBalance() {
        return this.balance;
    }

    /**
     * Adds balance to this user.
     * If the user exceeds the maximum balance that can be added, the transaction does not take place.
     * If the user exceeds the maximum total balance for their account after adding, balance will be added until
     * the maximum is reached.
     *
     * @param credit the credits to be added to this account
     */
    public void addBalance(float credit) {
        if (credit > DAILY_MAX_ADD_FUNDS) {
            System.out.println("ERROR: Balanced added exceeds the daily maximum.");
        }

        if (this.balance + credit > MAX_BALANCE) {
            this.DAILY_MAX_ADD_FUNDS -= (MAX_BALANCE - this.balance);
            this.balance = MAX_BALANCE;
            System.out.println("WARNING: added balance was greater than max balance for user.");
        } else {
            this.DAILY_MAX_ADD_FUNDS -= credit;
            this.balance += credit;
        }
    }

    /**
     * Removes balance from the user's account.
     *
     * @param credit the credit to be removed.
     */
    public void payAmount(float credit) {
        this.balance -= credit;
    }

    /**
     * Returns true iff the user is logged in.
     *
     * @return
     */
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    /**
     * Logs off this user.
     */
    public void logOff() {
        this.loggedIn = false;
    }

    /**
     * Logs in this user.
     */
    public void logIn() {
        this.loggedIn = true;
    }

    /**
     * Adds a game to the user's inventory.
     *
     * @param game the game to be added.
     */
    public void addToInventory(Game game) {
        this.inventory.add(game);
    }

    /**
     * Removes a game from the user's inventory.
     *
     * @param game the game to be removed.
     */
    public void removeFromInventory(Game game) {
        this.inventory.remove(game);
    }

    /**
     * Returns the user's inventory of games.
     *
     * @return the arraylist of games this user owns.
     */
    public ArrayList<Game> getInventory() {
        return this.inventory;
    }

    public boolean owns(Game game) {
        return this.getInventory().contains(game);
    }

    /**
     * Adds a purchase to this user's purchases.
     *
     * @param purchase the purchase being added.
     */
    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    /**
     * Gets the arraylist of purchases that this user has made.
     *
     * @return the arraylist of purchases.
     */
    public ArrayList<Purchase> getPurchases() {
        return this.purchases;
    }

    /**
     * @param o a object to compare to this user
     * @return if the users are equal to each other by comparing usernames
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return ((User) o).getUsername().equals(this.getUsername());


    }
}
