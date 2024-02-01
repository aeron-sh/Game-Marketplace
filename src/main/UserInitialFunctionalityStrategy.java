package main;


import java.util.ArrayList;

/**
 * The strategy that handles input of type XX UUUUUUUUUUUUUUU TT CCCCCCCCC
 * Where XX is a two-digit transaction code: 00-login, 01-create, 02-delete, 06-addcredit, 10-logout
 *       UUUUUUUUUUUUUUU is the username, which ends with whitespaces if it does not fill the whole space
 *       TT is the user type (AA=admin, FS=full-standard, BS=buy-standard, SS=sell-standard)
 *       CCCCCCCCC is the available credit
 */

public class UserInitialFunctionalityStrategy implements Strategy{

    @Override
    public void run(String info, ArrayList<User> users, ArrayList<Game> games){

        String transactionType = info.substring(0, 2);
        String username = getUsername(info.substring(3, 18).toCharArray());
        String accountType = info.substring(18, 20);
        float credits = getCredits(info.substring(21).toCharArray());

        switch (transactionType){
            case "00":
                // Login
                User user = findUser(username, users);
                if (user != null){
                    user.logIn();
                    if (!getUserCode(user).equals(accountType)){
                        System.out.println("WARNING: User tried to log in as a user different from " +
                                "their account type.");
                    }
                    System.out.println(user.getUsername() + " has logged in");
                }
                break;

            case "01":
                // Create User
                if (userExists(username, users)) {
                    System.out.println("ERROR: User already exists with that name. Error occurred in " +
                            "transaction " + info);
                    return;
                }
                switch (accountType){
                    case "AA":
                        // Create Admin User
                        if(!availableAdmin(users)){
                            System.out.println("ERROR: No available admin for this transaction. Error occurred in " +
                                    "transaction " + info);
                            return;
                        }
                        Admin admin = new Admin(username, credits);
                        users.add(admin);
                        System.out.println("User has been created (Create user)");
                        break;

                    case "FS":
                        // Create Full Standard User
                        if(!availableAdmin(users)) {
                            System.out.println("ERROR: No available admin for this transaction. Error occurred in " +
                                    "transaction " + info);
                            return;
                        }
                        FullStandardUser fullStandard = new FullStandardUser(username, credits, new ArrayList<Game>());
                        users.add(fullStandard);
                        System.out.println("User has been created (Create user)");
                        break;

                    case "BS":
                        // Create Buy Standard User
                        if(!availableAdmin(users)) {
                            System.out.println("ERROR: No available admin for this transaction. Error occurred in " +
                                    "transaction " + info);
                            return;
                        }
                        BuyStandardUser buyStandard = new BuyStandardUser(username, credits);
                        users.add(buyStandard);
                        System.out.println("User has been created (Create user)");
                        break;

                    case "SS":
                        // Create Sell Standard User
                        if(!availableAdmin(users)) {
                            System.out.println("ERROR: No available admin for this transaction. Error occurred in " +
                                    "transaction " + info);
                            return;
                        }
                        SellStandardUser sellStandard = new SellStandardUser(username, credits);
                        users.add(sellStandard);
                        System.out.println("User has been created (Create user)");
                        break;

                    default:
                        System.out.println("ERROR: User type is invalid. Error occurred in transaction " + info);
                        return;
                }
                break;
            case "02":
                // Delete User
                if(!availableAdmin(users)) {
                    System.out.println("ERROR: No available admin for this transaction. Error occurred in " +
                            "transaction " + info);
                    return;
                }
                User deleted = findUser(username, users);
                if (deleted != null)  {
                    // Checks if the user has any games up for sale
                    ArrayList<Game> userGames = new ArrayList<Game>();
                    if (deleted instanceof SellStandardUser) {
                        userGames = deleted.getInventory();
                    } else if (deleted instanceof  FullStandardUser){
                        userGames = ((FullStandardUser) deleted).getSellable();
                    } else if (deleted instanceof  Admin){
                        userGames = ((Admin) deleted).getSellable();
                    }

                    // Remove games up for sale and remove user.
                    removeGames(games, userGames);
                    users.remove(deleted);
                    System.out.println("User:" + deleted.getUsername() + " has been deleted");
                }
                break;

            case "06":
                // Add Credit
                User added = findUser(username, users);
                if (added != null) {
                    added.addBalance(credits);
                    System.out.println(credits + "has been added to " + added.getUsername());
                }
                break;

            case "07":
                // Start auction
                if (!availableAdmin(users)){
                    System.out.println("ERROR: No available admin for this transaction. Error occurred in " +
                            "transaction " + info);
                    return;
                }
                Admin admin = getAdmin(users);
                assert admin != null;
                admin.auctionSale(games);

            case "10":
                // Logout
                User loggedOut = findUser(username, users);
                if (loggedOut != null) {
                    loggedOut.logOff();
                    System.out.println("User " + loggedOut.getUsername() + " has logged out");
                }
                break;

            default:
                System.out.println("ERROR: Transaction type is invalid. Error occurred in transaction " + info);
                return;
        }
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
     * Returns the float value of the credits from the char array of the transaction.
     * @param values the char array representing the credits
     * @return the float value for credits
     */
    private float getCredits(char[] values){
        String creditsString = "";

        for (int i = 0; i < values.length; i++){
            creditsString += values[i];
        }
        return Float.parseFloat(creditsString);
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
     * Checks if an admin is available to perform the privileged transactions that this strategy handles.
     * @param users the array list of all users.
     * @return true iff an admin is logged in.
     */
    private boolean availableAdmin(ArrayList<User> users){
        for (User user: users){
            if (user instanceof Admin && user.isLoggedIn()){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true iff the user with a given username already exists.
     * @param username the username being checked
     * @param users the arraylist of all the users in the system
     * @return true iff username is already used.
     */
    private boolean userExists(String username, ArrayList<User> users){
        for (User user : users){
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an admin if they are online.
     * @param users the arraylist of all the users in the system
     * @return the online admin, null if no such admin exists.
     */
    private Admin getAdmin(ArrayList<User> users){
        for (User user : users){
            if (user instanceof  Admin && user.isLoggedIn()) {
                return (Admin) user;
            }
        }
        return null;
    }

    /**
     * Removes an array list of games removed from the arraylist of games in the system.
     * @param games the arraylist of games in the system
     * @param removed the arraylist of games to be removed
     */
    private void removeGames(ArrayList<Game> games, ArrayList<Game> removed){
        for (Game game : removed){
            games.remove(game);
        }

    }

    /**
     * Return the corresponding user code given a user.
     * @param user the user who's code is found.
     * @return returns the corresponding user code
     */
    private String getUserCode(User user){
        if (user instanceof BuyStandardUser){
            return "BS";
        } else if (user instanceof FullStandardUser){
            return "FS";
        } else if (user instanceof SellStandardUser){
            return "SS";
        } else if (user instanceof  Admin){
            return "AA";
        }
        return "";
    }
}
