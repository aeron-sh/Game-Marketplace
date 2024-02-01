package main;

import java.util.ArrayList;

/**
 * The basic interface for strategies.
 */

public interface Strategy {

    /**
     * Runs the strategy
     * @param info the string containing the line corresponding to a strategy.
     * @param users all the users in the given system
     * @param games all the games in the current system.
     */
    public void run(String info, ArrayList<User> users, ArrayList<Game> games);

}
