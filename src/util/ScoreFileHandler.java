package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ScoreFileHandler {
    private String title = "Historique des scores obtenus:";
    ArrayList<ScoreEntry> listOfScores = new ArrayList<>();

    public ScoreFileHandler() {
    }

    public void updateFile(int gameScore) {
        try {
            String fileName = "Scores.txt";
            File file = new File(fileName);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Cannot create " + fileName);
                }
            } else {
                // First, let's read the file.
                Scanner scanner = new Scanner(file);
                title = scanner.nextLine();

                // line format: "#4: 164 --> 27/07/2024 19:04:33".
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split(" ");
                    int score = Integer.parseInt(line[1]);
                    Date time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(line[3] + " " + line[4]);
                    listOfScores.add(new ScoreEntry(score, time));
                }
                scanner.close();
            }

            // Add the new score with current time at the right place in the list.
            Date now = new Date();
            if (listOfScores.isEmpty() || gameScore <= listOfScores.getLast().score) {
                listOfScores.add(new ScoreEntry(gameScore, now));
            } else {
                for (int i = 0; i < listOfScores.size(); i++) {
                    if (gameScore > listOfScores.get(i).score) {
                        listOfScores.add(i, new ScoreEntry(gameScore, now));
                        break;
                    } else if (i == listOfScores.size() - 1) {
                        listOfScores.add(new ScoreEntry(gameScore, now));
                    }
                }
            }

            // Write updated scores to file.
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(title);
            for (int i = 1; i <= listOfScores.size(); i++) {
                bw.newLine();
                bw.write("#" + i + ": " + listOfScores.get(i - 1).toString());
            }
            bw.close();

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}