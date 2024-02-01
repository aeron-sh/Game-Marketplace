package main;

import java.util.ArrayList;

/**
 * This strategy handles remove transaction where input of type XX IIIIIIIIIIIIIIIIIII UUUUUUUUUUUUUUU SSSSSSSSSSSSSSS
 *      XX represents two-digit transaction code: 08-remove game.
 *      IIIIIIII represents the gamename.
 *      UUUUUUUU represents the username.
 *      SSSSSSSS represents the receiver's username which is not necessary in this transaction type.
 */

public class UserRemoveStrategy implements Strategy{

    /**
     * These attributes are used for testing purposes only
     */
    String code;
    String temp_gamename;
    String temp_username;

    /**
     * This method excutes the transaction and remove a game from user.
     * @param info a string line of transaction.
     * @param users arraylist of users.
     * @param games arraylist of games.
     */
    @Override
    public void run(String info, ArrayList<User> users, ArrayList<Game> games) {

        // Set the code, GameName and Username for this transaction
        String code = info.substring(0, 2);
        String gamename = getName(info.substring(3, 22).toCharArray());
        String username = getName(info.substring(23, 38).toCharArray());

        // Set the attributes of this class for testing to the values calculated above
        this.code = code;
        this.temp_gamename = gamename;
        this.temp_username = username;

        // Get the Game and User for this transaction by using the GameName and UserName calculated earlier
        Game game = findGame(gamename, games);
        User user = findUser(username, users);

        // Make sure the transaction type is 08 for remove
        if (code.equals("08")) {

            // If statements checking to make sure that user and game are actual objects
            if (user == null) {
                System.out.println("Error: User does not exist. Error occurred in transaction " + info);
                return;
            }
            if (game == null) {
                System.out.println("Error: Game does not exist. Error occurred in transaction " + info);
                return;
            }

            // Checking if the game is new and can be removed or not
            for (Purchase purchase : user.getPurchases()) {
                if (purchase.getGame() == game) {
                    if (purchase.isNewPurchase()) {
                        System.out.println("This game was recently purchased and therefore cannot be removed");
                    }
                }
            }
            if (game.isFresh()) {
                System.out.println("Error: Game has recently been put up, therefore can't be removed. " +
                        "Error occurred in transaction " + info);
                return;
            } else {
                user.removeFromInventory(game);
            }
            System.out.println("Game: " + game.getName() + "has been removed from " + user.getUsername() + "'s inventory.");
        }
    }

    /**
     * This takes in whether a username or gamename.
     * Since empty field in a transaction is filled with white spaces, this method deletes
     * all unnecessary white spaces. And returns the name.
     * @param name name that possibly contains unnecessary white space.
     * @return the username or gamename.
     */
    private String getName(char[] name) {
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
     * Find any type of User from arraylist of users that is logged in OR has same name.
     * If no such user exist, return null.
     * @param username string represents the name of user
     * @param users arraylist of all users.
     * @return an User if exists, null otherwise.
     */
    private User findUser(String username, ArrayList<User> users) {
        for (User user: users) {
            if (user.getUsername().equals(username) || user.isLoggedIn()){
                return user;
            }
        }
        return null;
    }

    /**
     * Find game from arralist of games.
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
     * The following getters are exclusively used for testing purposes
     */
    public String getCode() {
        return this.code;
    }
    public String getTemp_gamename() {
        return this.temp_gamename;
    }
    public String getTemp_username() {
        return this.temp_username;
    }
}
