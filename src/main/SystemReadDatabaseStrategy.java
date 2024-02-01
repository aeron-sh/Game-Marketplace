package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SystemReadDatabaseStrategy implements Strategy{


    @Override
    public void run(String info, ArrayList<User> users, ArrayList<Game> games) {
        readGamesFile(games);
        readUserFile(users, games);
        updatePurchases(users);
    }

    /**
     * Reads all the games from the game database.
     * @param games the current games in the system.
     */
    private void readGamesFile(ArrayList<Game> games){
        try {
            BufferedReader gameReader = new BufferedReader(new FileReader("GameInfo.txt"));
            String line = gameReader.readLine();
            int index = 0;

            String gameName = null;
            while (line != null){

                // Even lines are game names
                if(index % 2 == 0){
                    gameName = line;
                } else {
                    // Odd lines are information about the game
                    String[] info = line.split("\\|");

                    // Get the info from each part
                    float cost = Float.parseFloat(info[0]);

                    boolean discount = false;
                    if (info[1].equals("1")){
                        discount = true;
                    }

                    float discountPerc = Float.parseFloat(info[2]);

                    // Recreate the game object
                    Game game = new Game(gameName, cost, discount, discountPerc);
                    game.notFresh();
                    games.add(game);
                    gameName = null;
                }
                line = gameReader.readLine();
                index++;
            }
            gameReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR: Could not read game information from database files. Error " +
                    "occurred in SystemReadDatabaseStrategy.java");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Could not read lines from the game information database. Error " +
                    "occurred in SystemReadDatabaseStrategy.java");
        }
    }

    /**
     * Reads all the users from the user database.
     * PRECONDITION: This must be initialized AFTER all the games are populated.
     *
     * @param users the users currently in the system.
     * @param games the games currently in the system.
     */
    public void readUserFile(ArrayList<User> users, ArrayList<Game> games){

        try {
            BufferedReader userReader = new BufferedReader(new FileReader("UserInfo.txt"));
            ArrayList<ArrayList<String>> userInfo = splitUserInfo(userReader);

            for (ArrayList<String> user : userInfo){
                // Get username
                String username = user.get(0);

                // Split up the condensed information
                String[] remInfo = user.get(1).split("\\|");

                // Get basic user info
                String type = remInfo[0];
                float balance = Float.parseFloat(remInfo[1]);
                boolean loggedIn = false;

                if (remInfo[2].equals("1")){
                    loggedIn = true;
                }

                // Get all games
                ArrayList<Game> inventory = new ArrayList<Game>();
                ArrayList<Game> sellable = new ArrayList<Game>();

                switch(type){
                    case "0":
                        // BuyStandardUser
                        inventory = getBasicUserGames(user, games);
                        BuyStandardUser buyUser = new BuyStandardUser(username, balance, inventory, loggedIn);
                        users.add(buyUser);
                        break;

                    case "1":
                        // SellStandardUser
                        inventory = getBasicUserGames(user, games);
                        SellStandardUser sellUser = new SellStandardUser(username, balance, inventory, loggedIn);
                        users.add(sellUser);
                        break;
                    case "2":
                        // FullStandardUser
                        inventory = getAdvUserGames(user, games);
                        sellable = getAdvUserSold(user, games);
                        FullStandardUser fullUser = new FullStandardUser(username, balance, inventory,
                                sellable, loggedIn);
                        users.add(fullUser);
                        break;
                    case "3":
                        // Admin user.
                        inventory = getAdvUserGames(user, games);
                        sellable = getAdvUserSold(user, games);
                        Admin adminUser = new Admin(username, balance, inventory, sellable, loggedIn);
                        users.add(adminUser);
                        break;

                    default:
                        return;

                }
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR: Could not read user information from database files. Error " +
                    "occurred in SystemReadDatabaseStrategy.java");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Could not read lines from the user information database. Error " +
                    "occurred in SystemReadDatabaseStrategy.java");
        }

    }

    /**
     * Splits the user info into segments of array list strings.
     * @param userReader the BufferedReader that will be reading the user input
     * @return the user info in segments
     * @throws IOException
     */
    private ArrayList<ArrayList<String>> splitUserInfo(BufferedReader userReader) throws IOException {
        String line = userReader.readLine();

        // Holds all user information
        ArrayList<ArrayList<String>> userInfo = new ArrayList<ArrayList<String>>();

        // Holder for each user's information
        ArrayList<String> newUser = new ArrayList<String>();

        while(line != null){

            if (line.equals("-------------------")){
                userInfo.add(newUser);
                newUser = new ArrayList<String>();
            } else {
                newUser.add(line);
            }
            line = userReader.readLine();
        }
        userReader.close();
        return userInfo;
    }

    /**
     * Finds a game from the array list of games in the system when given a game name.
     * Returns null if no game is found
     * @param name the string name of the game
     * @param games the array list of games in the system.
     * @return
     */
    private Game findGame(String name, ArrayList<Game> games){

        for (Game game : games){
            if (game.getName().equals(name)){
                return game;
            }
        }
        return null;
    }

    /**
     * Gets all the games for the BuyStandardUser or the SellStandardUser.
     * @param user the array list of strings with all the user information
     * @param games the array list of all games currently in the system.
     * @return the array list of games for this user's inventory
     */
    private ArrayList<Game> getBasicUserGames(ArrayList<String> user, ArrayList<Game> games){
        ArrayList<Game> inventory = new ArrayList<Game>();
        for (int i = 2; i < user.size(); i++){
            Game game = findGame(user.get(i), games);

            if (game != null){
                inventory.add(game);
            }
        }
        return inventory;
    }


    /**
     * Gets all the bought games for the FullStandardUser and the Admin user.
     * @param user the array list of strings with all the user information
     * @param games the array list of all games currently in the system.
     * @return the array list of games for this user's bought inventory
     */
    private ArrayList<Game> getAdvUserGames(ArrayList<String> user, ArrayList<Game> games){
        ArrayList<Game> inventory = new ArrayList<Game>();
        int endIndex = user.indexOf("<SELL________________GAMES>");

        for (int i = 2; i < endIndex; i++){
            Game game = findGame(user.get(i), games);

            if (game != null){
                inventory.add(game);
            }
        }
        return inventory;
    }


    /**
     * Gets all the games that is sold bny the FullStandardUser or the Admin user.
     * @param user the array list of strings with all the user information
     * @param games the array list of all games currently in the system.
     * @return the array list of games on sale for this user
     */
    private ArrayList<Game> getAdvUserSold(ArrayList<String> user, ArrayList<Game> games){
        ArrayList<Game> inventory = new ArrayList<Game>();
        int startIndex = user.indexOf("<SELL________________GAMES>") + 1;

        for (int i = startIndex; i < user.size(); i++){
            Game game = findGame(user.get(i), games);

            if (game != null){
                inventory.add(game);
            }
        }
        return inventory;
    }

    /**
     * Updates the purchases for each user with the proper information.
     * @param users the arraylist of users.
     */
    private void updatePurchases(ArrayList<User> users){
        for (User user : users){
            if (! (user instanceof  SellStandardUser)){
                ArrayList<Game> userGames = user.getInventory();
                for (Game game : userGames){
                    Purchase purchase = new Purchase(game, false);
                    user.addPurchase(purchase);
                }
            }
        }
    }
}
