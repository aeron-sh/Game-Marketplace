package main;

import java.util.ArrayList;

/**
 * The strategy that handles giving gifts to other users.
 */

public class UserGiftStrategy implements Strategy {

    @Override
    public void run(String info, ArrayList<User> users, ArrayList<Game> games) {

        // Get all necessary information
        String gameName = getUsername(info.substring(3, 22).toCharArray());
        String giver = getUsername(info.substring(23, 37).toCharArray());
        String receiver= getUsername(info.substring(39).toCharArray());
        User giverUser = findUser(giver, users);
        User receiverUser = findUser(receiver, users);
        Game game = findGame(gameName, games);

        // check all constraints are fulfilled
        if (!giverUser.isLoggedIn()){
            System.out.println("Error: Giver is not logged in");
            return;
        }


        if (giverUser == null || receiverUser == null){
            System.out.println("Error: Giver or Receiver user does not exist (Gifting)");
            return;
        }


        if (receiverUser instanceof SellStandardUser){
            System.out.println("Error: A seller only User cannot receive a Gift (Gifting)");
            return;
        }

        if (game == null && !giverUser.owns(game)){
            System.out.println("Error: Game does not exist (Gifting)");
            return;
        }

        if (receiverUser.owns(game) ){
            System.out.println("Error: the receiver already owns" + game.getName() + " (Gifting)");
            return;
        }
        for (Purchase purchase :giverUser.getPurchases()){
            if (purchase.getGame() == game){
                if (purchase.isNewPurchase()){
                    System.out.println("This game was recently purchased and therefore cannot be gifted");
                    return;
                }
            }
        }

        if (game.fresh ){
            System.out.println("Error: Game has recently been put up to sale or has recently been purchased (Gifting)");
            return;
        }


        if (giverUser instanceof Admin){
            receiverUser.addToInventory(game);
            return;
        }

        if (!giverUser.getInventory().contains(game)){
            System.out.println("Error: Game not in users inventory");
            return;
        }

        if (!giverUser.owns(game)){
            System.out.println("Error: User does not own game (Gift)");
            return;
        }

        if (giverUser instanceof SellStandardUser){
            receiverUser.addToInventory(game);
        }else{
            giverUser.removeFromInventory(game);
            receiverUser.addToInventory(game);
        }
        System.out.println("Game " + game.name + "has been gifted from " + giverUser.getUsername() + "to " +
                receiverUser.getUsername());

    }

    /**
     * Gets the username from the char array of the transaction.
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
     * Finds the game from the ArrayList of games given the game name.
     * If no such user exists then it returns null.
     * @param gameName the string representing the game name
     * @param games the ArrayList of all the games
     * @return the game from the list of games
     */
    private Game findGame(String gameName, ArrayList<Game> games){

        for(Game game: games){
            if (game.name.equals(gameName)){
                return game;
            }
        }
        return null;
    }


}
