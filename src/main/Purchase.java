package main;

public class Purchase {

    Game game;
    boolean newPurchase;

    public Purchase(Game game, boolean newPurchase){
        this.game = game;
        this.newPurchase = newPurchase;
    }

    /**
     * Returns the game from this purchase
     * @return the game
     */
    public Game getGame(){
        return this.game;
    }

    /**
     * Returns true iff this purchase was within the last 24 hours.
     * @return
     */
    public boolean isNewPurchase(){
        return this.newPurchase;
    }
}
