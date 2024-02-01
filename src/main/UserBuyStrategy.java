package main;


import java.util.ArrayList;

/**
 * The strategy that handles input of type XX IIIIIIIIIIIIIIIIIII SSSSSSSSSSSSSSS UUUUUUUUUUUUUU
 *      Where XX is a two-digit transaction code: 04-buy.
 *      IIIIIIIIIIIIIIIIIII is the game name
 *      SSSSSSSSSSSSSSS is the seller’s username
 *      UUUUUUUUUUUUUUU is the buyer's username
 *
 *      The usernames end with whitespaces if they don't fill the whole space
 */

public class UserBuyStrategy implements Strategy{

    @Override
    public void run(String info, ArrayList<User> users, ArrayList<Game> games){

        String transactionType = info.substring(0, 2);
        String gameName = getUsername(info.substring(3, 22).toCharArray());
        Game game = getGame(gameName, games);
        String sellerUsername = getUsername(info.substring(23, 38).toCharArray());
        String buyerUsername = getUsername(info.substring(39).toCharArray());

        if ("04".equals(transactionType)) {
            // Get users
            User buyer = findUser(buyerUsername, users);
            User seller = findUser(sellerUsername, users);

            if (buyer == null || seller == null){
                System.out.println("Error: the buyer or seller does not exist (buy)");
                return;
            }
            // Check if the buyer is a valid buyer
            if (buyer instanceof SellStandardUser){
                System.out.println("ERROR: Buyer user is not a user capable of buying. Error occurred in " +
                        "transaction " + info);
                return;
            }

            // Check if seller is a valid seller
            if(seller instanceof  BuyStandardUser){
                System.out.println("ERROR: Seller user is not a user capable of selling. Error occurred in " +
                        "transaction " + info);
                return;
            }

            if (!buyer.isLoggedIn()){
                System.out.println("Error: Buyer user is not logged in");
                return;
            }

            // Buy and sell the games for both users.
            buyGameForBuyer(buyer, game);
            sellGameForSeller(seller, game);
            System.out.println(buyer.getUsername() + " has bought " + gameName + " from" + seller.getUsername());


        }

    }

    /**
     * Gets the username from the char array of the transaction. Also works for game names.
     * @param values the char array representing the username.
     * @return the username
     */
    private String getUsername(char[] values){
        StringBuilder username = new StringBuilder();
        int position = -1;

        for (int i = values.length - 1; i >= 0; i--){
            if (values[i] != " ".charAt(0)){
                position = i;
                break;
            }
        }

        for (int i = 0; i < position + 1; i++){
            username.append(values[i]);
        }

        return username.toString();
    }

    /**
     * Returns the game with the name gameName
     * @param gameName the name of the game
     * @return the game with name gameName
     */
    private Game getGame(String gameName, ArrayList<Game> games){

        for (Game game: games) {
            if (game.name.equals(gameName)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Finds the user from the ArrayList of users given the username.
     * If no such user exists then it returns null.
     * @param username the string representing the username
     * @param users the ArrayList of all the users
     * @return the user from the list of users
     */
    private User findUser(String username, ArrayList<User> users){

        for(User user: users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    /**
     * Buys a game for a given user.
     * PRECONDITION: The user must be valid.
     * @param buyer the buyer buying the game
     * @param game the game being bought
     */
    private void buyGameForBuyer(User buyer, Game game){
        if (buyer instanceof BuyStandardUser){
            ((BuyStandardUser) buyer).buyGame(game);
        } else if (buyer instanceof  FullStandardUser){
            ((FullStandardUser) buyer).buyGame(game, game.getCost());
        } else if (buyer instanceof  Admin){
            ((Admin) buyer).buyGame(game, game.getCost());
        }
    }

    /**
     * Sells a game (add credits) for a given seller.
     * @param seller the seller selling the game
     * @param game the game being sold.
     */
    private void sellGameForSeller(User seller, Game game){
        if (seller instanceof SellStandardUser){
            ((SellStandardUser) seller).sell(game, game.getCost());
        } else if (seller instanceof  FullStandardUser){
            ((FullStandardUser) seller).sell(game, game.getCost());
        } else if (seller instanceof  Admin){
            ((Admin) seller).sell(game, game.getCost());
        }
    }
}
