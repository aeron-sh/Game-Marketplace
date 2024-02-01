package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * The master class that will choose what strategy to run, parse through all the data, and keep track of all variables.
 */

public class StoreSystem {

    // An arraylist of all the users in the system.
    public ArrayList<User> users;

    // An arraylist of all the games in the system.
    public ArrayList<Game> games;

    private Admin godUser;

    public StoreSystem(){
        this.users = new ArrayList<User>();
        this.games = new ArrayList<Game>();
        this.godUser = new Admin("godUser", 0);
    }

    /**
     * The main system. This runs the entire store, and must be called once for each day of the daily transaction
     * file.
     */
    public void runSystem(){
        startSystem();

        if (this.users.isEmpty()){
            this.users.add(godUser);
        }

        try {
            readTransactions();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fatal Error in storeSystem Daily.txt file not found");
        }
        this.users.remove(godUser);
        endSystem();
    }


    /**
     * Reads through the database at the start of the system or the next day.
     * Empties the users and games and repopulates them from reading.
     */
    private void startSystem(){
        this.users = new ArrayList<User>();
        this.games = new ArrayList<Game>();

        SystemReadDatabaseStrategy strat = new SystemReadDatabaseStrategy();
        runStrategy(strat, "");
    }

    /**
     * Stores all the data in the database for reading.
     */
    private void endSystem(){
        SystemEndStrategy end = new SystemEndStrategy();
        runStrategy(end, "");
    }

    /**
     * Reads all the transactions in the daily.txt file and performs them.
     * @throws Exception
     */
    private void readTransactions() throws Exception {
        ArrayList<String> strategies = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("daily.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line != "") {
                String[] content = line.split(" ");
                String type = content[0];

                if (type.equals("00") || type.equals("01") || type.equals("02") || type.equals("06")
                        || type.equals("10") || type.equals("07")) {
                    UserInitialFunctionalityStrategy strat = new UserInitialFunctionalityStrategy();
                    runStrategy(strat, line);
                } else if (type.equals("05")) {
                    UserRefundStrategy strat = new UserRefundStrategy();
                    runStrategy(strat, line);
                } else if (type.equals("03")) {
                    UserSellStrategy strat = new UserSellStrategy();
                    runStrategy(strat, line);
                } else if (type.equals("04")) {
                    UserBuyStrategy strat = new UserBuyStrategy();
                    runStrategy(strat, line);
                } else if (type.equals("08")) {
                    UserRemoveStrategy strat = new UserRemoveStrategy();
                    runStrategy(strat, line);
                } else if (type.equals("09")) {
                    UserGiftStrategy strat = new UserGiftStrategy();
                    runStrategy(strat, line);
                }
            }else{
                System.out.println("Error: cannot accept a empty line");
            }
        }
        reader.close();
    }

    /**
     * Runs the necessary strategies for when they are needed.
     * @param strat the strategy being run
     * @param line the line from the transaction file corresponding to this strategy.
     */
    private void runStrategy(Strategy strat, String line){
        strat.run(line, this.users, this.games);
    }
}
