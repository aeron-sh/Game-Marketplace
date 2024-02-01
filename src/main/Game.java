package main;

/**
 * The class that represents each game object
 */

public class Game {

    String name;
    float cost;
    boolean discount;
    float discountPercent;

    // Boolean used to determine if a game was put on sale in the last 24 hours.
    boolean fresh;

    /**
     * Constructs a game to be put on sale
     * @param name the name of the game
     * @param cost the cost of the game
     */
    public Game (String name, float cost){
        if (name.length() > 25) {
            this.name = name.substring(0, 25);
            System.out.println("WARNING: Username is too long. System has shortened it too " + this.name);
        } else {
            this.name = name;
        }

        this.name = name;
        this.cost = cost;
        this.discount = false;
        this.discountPercent = 0;
        this.fresh = true;
    }

    /**
     * Constructs a game to be put on sale
     * @param name the name of the game
     * @param cost the cost of the game
     * @param discount
     * @param discountPerc
     */
    public Game (String name, float cost, boolean discount, float discountPerc){

        if (name.length() > 25) {
            this.name = name.substring(0, 25);
            System.out.println("WARNING: Username is too long. System has shortened it too " + this.name);
        } else {
            this.name = name;
        }

        this.cost = cost;
        this.discount = discount;
        this.discountPercent = discountPerc;
        this.fresh = false;
    }

    /**
     * Returns the cost of the game
     * @return float value for the cost of the game
     */
    public float getCost(){
        if (this.discount){
            return (float) (this.cost - (this.cost * this.discountPercent * 0.01));
        }
        return this.cost;
    }

    /**
     * Returns the name of the game.
     * @return the name of the game
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets the discount percentage for the game
     * @param percent the percent that the game is on discount for
     */
    public void setDiscountPercent(float percent){
        this.discountPercent = percent;
    }

    /**
     * Changes the cost of the game so that it is on discount
     */
    public void applyDiscount(){
        this.discount = true;
    }

    /**
     * Reverts the game's cost to what it was before it was on discount
     */
    public void stopDiscount(){
        this.discount = false;
    }

    /**
     * Returns true iff the game was not put on sale during the previous day.
     * @return true iff the game was not put on sale during the previous day, false otherwise.
     */
    public boolean isFresh(){
        return this.fresh;
    }

    /**
     * Sets the game so that it no longer presents that it was put on sale within the same day.
     */
    public void notFresh(){
        this.fresh = false;
    }

    /**
     * Returns true iff this game is put on discount.
     * @return true iff the game is on discount, false otherwise.
     */
    public boolean onDiscount(){
        return this.discount;
    }


    /**
     * Checks if this game equals another game.
     * @param o the other object being checked.
     * @return true iff this game is the same as the other object.
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        return ((Game) o).name.equals(this.name);

    }

    /**
     * @return a String representation of this game.
     */
    @Override
    public String toString(){
        String info = this.cost + "|";

        if (this.discount){
            info += "1|";
        } else {
            info += "0|";
        }

        info += this.discountPercent;
        return info;
    }
}
