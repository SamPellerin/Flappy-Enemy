package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreEntry {
    int score;
    Date time;

    public ScoreEntry(int score, Date timestamp) {
        this.score = score;
        this.time = timestamp;
    }

    // Writing the coin amount to the "Scores.txt" file.
    @Override
    public String toString() {
        return this.score + " --> " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.time);
    }
}
