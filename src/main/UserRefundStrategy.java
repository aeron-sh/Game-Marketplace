package main;


import java.util.ArrayList;

/**
 * The strategy that handles input of type XX UUUUUUUUUUUUUUU SSSSSSSSSSSSSSS CCCCCCCCC
 *      Where XX is a two-digit transaction code: 05-refund
 *      UUUUUUUUUUUUUUU is the buyer's username
 *      SSSSSSSSSSSSSSS is the seller’s username
 *      CCCCCCCCC is the available credit
 *
 *      The usernames end with whitespaces if they don't fill the whole space
 */

public class UserRefundStrategy implements Strategy{

    @Override
    public void run(String info, ArrayList<User> users, ArrayList<Game> games){

        if (!availableAdmin(users)){
            System.out.println("Error: no available admin (refund)");
            return;
        }
        Admin admin = getAdmin(users);
        String buyer_user = getUsername(info.substring(3, 18).toCharArray());
        String seller_user = getUsername(info.substring(19, 34).toCharArray());
        float credits = getCredits(info.substring(35).toCharArray());

        User buyer = findUser(buyer_user, users);
        User seller = findUser(seller_user, users);

        if (buyer == null || seller == null){
            System.out.println("Error: not a valid buyer or seller (refund)");
            return;
        }

        if (buyer instanceof SellStandardUser){
            System.out.println("Error: A seller only class can not get a refund (refund)");
            return;
        }
        if (seller instanceof BuyStandardUser){
            System.out.println("Error: A buyer only class can not be the one to issue a refund (refund)");
            return;
        }

        if (seller.getBalance() < credits){
            System.out.println("Error: Seller is missing credits for this refund");
            return;
        }

        admin.refund(buyer, seller, credits);
        System.out.println("Refund of " + credits + " to " + buyer.getUsername() + " from " + seller.getUsername());


    }

    /**
     * Gets the username from the char array of the transaction.
     * @param values the char array representing the username.
     * @return the username
     */
    private String getUsername(char[] values){
        String username = "";

        for (int i = 0; i < values.length; i++){
            if (values[i] != " ".charAt(0)) {
                username += values[i];
            } else{
                return username;
            }
        }
        return username;
    }

    /**
     * gets the available admin user if there is any
     * @param users the total list of users
     * @return admin user if one is logged in null otherwise
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
     * Checks if an admin is available to perform the privelaged transactions that this strategy handles.
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
}
