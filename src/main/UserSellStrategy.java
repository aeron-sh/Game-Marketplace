package main;

import java.util.ArrayList;

/**
 * This strategy handles sell transactions where input of type XX IIIIIIIIIIIIIIIIIII SSSSSSSSSSSSS DDDDD PPPPPP.
 *      XX represents two-digit transaction code: 03-sell.
 *      IIIIIIIIIII is the gamename.
 *      SSSSSSSSSSS is the username.
 *      DDDDD is the discount percentage.
 *      PPPPPP is the saleprice.
 */

public class UserSellStrategy implements Strategy{


    /**
     * This method excutes the transaction and make user to perform a sell.
     * @param info is a string line of transaction.
     * @param users Arraylist of users.
     * @param games Arraylist of games.
     */
    @Override
    public void run(String info, ArrayList<User> users, ArrayList<Game> games) {
        String code = info.substring(0, 3);
        String temp_gamename = info.substring(3, 22);
        String temp_username = info.substring(23, 36);
        String discount = info.substring(37, 42);
        String temp_saleprice = info.substring(43);


        String gamename = getName(temp_gamename.toCharArray());
        String username = getName(temp_username.toCharArray());
        float discount_percentage = Float.parseFloat(discount);
        float saleprice = Float.parseFloat(temp_saleprice);

        float price_discount_applied = applyDiscount(saleprice, discount_percentage);

        User user = findUser(username, users);
        Game game = new Game(gamename, saleprice);
        game.setDiscountPercent(discount_percentage);

        if (games.contains(game)) {
            System.out.println("ERROR: The game that this user is trying to create already exists.");
            return;
        }

        if (user == null) {
            System.out.println("Error: User does not exist");
            return;
        }
        if (!user.isLoggedIn()){
            System.out.println("Error: Seller is not logged in");
            return;
        }
        if (game == null) {
            System.out.println("Error: Game does not exist");
            return;
        }
        if (user instanceof BuyStandardUser) {
            System.out.println("Error: A Buy only User cannot sell a game");
            return;
        }

        for (Purchase purchase : user.getPurchases()) {
            if (purchase.getGame() == game) {
                if (purchase.isNewPurchase()) {
                    System.out.println("This game was recently purchase and therefore cannot be sold yet");
                    return;
                }
            }
        }

        if (user instanceof SellStandardUser) {
            ((SellStandardUser) user).putOnSale(game);
            games.add(game);
        }
        else if (user instanceof FullStandardUser){
            ((FullStandardUser) user).putOnSale(game);
            games.add(game);
        } else if (user instanceof Admin){
            ((Admin) user).putOnSale(game);
            games.add(game);
        }
        System.out.println("User " + user.getUsername() + " sold a game" + game.getName() + " for " +
                price_discount_applied + ".");
    }

    /**
     * This takes in whether username or gamename.
     * Since empty field in a transaction is filled with white spaces, this method deletes
     * all unneccesary white spaces. And returns the name.
     * @param name name that possibly contains unnecessary white space.
     * @return the username or gamename.
     */
    private String getName(char[] name){
        StringBuilder username = new StringBuilder();
        int position = -1;

        for (int i = name.length - 1; i >= 0; i--){
            if (name[i] != " ".charAt(0)){
                position = i;
                break;
            }
        }

        for (int i = 0; i < position + 1; i++){
            username.append(name[i]);
        }

        return username.toString();
    }

    /**
     * Find SellStandardUser or FullStandardUser from arraylist of users.
     * If no such user exist, return null.
     * @param username string represents the name of user
     * @param users arraylist of all users.
     * @return an User if exists, null otherwise.
     */
    private User findUser(String username, ArrayList<User> users){
        for (User user: users) {
            if (user.getUsername().equals(username)){
                if (user instanceof SellStandardUser || user instanceof FullStandardUser) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * Find Game from arraylist of games.
     * If no such game exists, return null.
     * @param gamename string represents the name of game.
     * @param games arraylist of all games.
     * @return a Game if exists, null otherwise.
     */
    private Game findGame(String gamename, ArrayList<Game> games){
        for (Game game: games) {
            if (game.getName().equals(gamename)){
                return game;
            }
        }
        return null;
    }

    /**
     * Apply the discount percentage to price then returns the price.
     * @param price float represents price.
     * @param discount discount percentage.
     * @return price with discount applied.
     */
    private float applyDiscount(float price, float discount){
        float discount_percentage = discount / 100;
        float discount_price = price * discount_percentage;
        return price - discount_price;
    }
}
