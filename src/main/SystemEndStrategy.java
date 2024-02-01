package main;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;

public class SystemEndStrategy implements Strategy{

    @Override
    public void run(String info, ArrayList<User> users, ArrayList<Game> games) {
        writeGameFile(games);
        writeUserFile(users);
    }

    /**
     * Writes to the game file in the source folder.
     * @param games all the games in the current system.
     */
    public void writeGameFile(ArrayList<Game> games){
        try {
            FileWriter gameWriter = new FileWriter("GameInfo.txt");

            for(Game game: games){
                gameWriter.write(game.getName() + "\n");
                gameWriter.write(game.toString() + "\n");
            }

            gameWriter.close();

        } catch (IOException e) {
            System.out.println("Error: Failed to write to database.");
            e.printStackTrace();
        }
    }

    /**
     * Writes to the user file in the source folder.
     * @param users all the users in the current system.
     */
    public void writeUserFile(ArrayList<User> users){
        try {
            FileWriter userWriter = new FileWriter("UserInfo.txt");

            for (User user: users){
               userWriter.write(user.toString());
               userWriter.write("-------------------" + "\n");
            }

            userWriter.close();

        } catch (IOException e) {
            System.out.println("Error: Failed to write to database.");
            e.printStackTrace();
        }
    }
}


