package com.frc63175985.csp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.auth.PitScoutRecord;
import com.frc63175985.csp.auth.ScoutAuthState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileManager {
    public static final FileManager shared = new FileManager();
    private File rootFolder;
    private File pitFolder;
    private File matchFolder;
    private File aggregateFolder;
    private File eventsFolder;
    private SimpleDateFormat FILENAME_FORMAT = new SimpleDateFormat("y-M-d-k-h-m-s-S", Locale.US);

    private FileManager() {
        rootFolder = new File(Environment.getExternalStorageDirectory(), "CSP");
        pitFolder = new File(rootFolder, "PIT");
        matchFolder = new File(rootFolder, "MATCH");
        aggregateFolder = new File(rootFolder, "AGGREGATION");
        eventsFolder = new File(rootFolder, "EVENTS");

        if (!pitFolder.exists()) {
            pitFolder.mkdirs();
        }

        if (!matchFolder.exists()) {
            matchFolder.mkdirs();
        }

        if (!aggregateFolder.exists()) {
            aggregateFolder.mkdirs();
        }

        if (!eventsFolder.exists()) {
            eventsFolder.mkdirs();
        }
    }

    public Bitmap readImage(@NonNull String filename) {
        String imagePath = new File(pitFolder, filename).getAbsolutePath();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Debug.log("Reading image at path " + imagePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        // scale image down
        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        return bitmap;
    }

    public File getNewImageFile(@NonNull String filename) {
        File imageFile = new File(pitFolder, filename + ".jpg");
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    /**
     * Save the aggregation data to the disk.
     * @return the file was saved to
     */
    public File saveAggregation(String data) {
        File newAggregateFile = new File(aggregateFolder, FILENAME_FORMAT.format(new Date()) + ".csv");
        try {
            newAggregateFile.createNewFile();
            FileWriter writer = new FileWriter(newAggregateFile);
            writer.write(data);
            writer.close();

            return newAggregateFile;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Save the current Match entry to the disk.
     */
    public void saveMatch() {
        String filename = String.format(Locale.US, "MATCH_%s_TEAM_%s.csv",
                ScoutAuthState.shared.currentMatch.str(Match.MATCH_NUMBER),
                ScoutAuthState.shared.currentMatch.str(Match.TEAM_NUMBER));
        File newMatchFile = new File(matchFolder, filename);
        try {
            newMatchFile.createNewFile();
            FileWriter writer = new FileWriter(newMatchFile);
            writer.write(ScoutAuthState.shared.currentMatch.export());
            writer.close();

            Debug.log("Wrote match file to path " + newMatchFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the current Pit Scouting entry to the disk
     * @returns the {@link File} object this entry saved to
     */
    public File savePit() {
        File newPitFile = new File(pitFolder, "PIT_TEAM_" + ScoutAuthState.shared.pitScoutRecord.str(PitScoutRecord.TEAM_NUMBER) + ".csv");
        try {
            newPitFile.createNewFile();
            FileWriter writer = new FileWriter(newPitFile);
            writer.write(ScoutAuthState.shared.pitScoutRecord.export());
            writer.close();
            Debug.log("Wrote pit file to path " + newPitFile.getAbsolutePath());

            return newPitFile;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Save an event to the disk
     * @param teams the array of teams to be saved
     * @return the file that was saved
     */
    public File saveEvent(String[] teams) {
        // convert teams to csv file
        StringBuilder builder = new StringBuilder();
        for (String teamNumber : teams) {
            builder.append(teamNumber).append(",");
        }
        builder.deleteCharAt(builder.length()-1); // delete last comma

        File newEventFile = new File(eventsFolder, ScoutAuthState.shared.tournament + ".csv");
        try {
            newEventFile.createNewFile();
            FileWriter writer = new FileWriter(newEventFile);
            writer.write(builder.toString());
            writer.close();
            Debug.log("Wrote event file to path " + newEventFile.getAbsolutePath());

            return newEventFile;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable public String[] loadEvent() {
        File eventFile = new File(eventsFolder, ScoutAuthState.shared.tournament + ".csv");
        if (eventFile.exists()) {
            Debug.log("Event " + ScoutAuthState.shared.tournament + " is already saved! Loading...");

            try {
                BufferedReader reader = new BufferedReader(new FileReader(eventFile));
                String rawData = reader.readLine();
                String[] data = rawData.split(",");
                reader.close();

                return data;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public File getRobotPicture(String filename) {
        return new File(pitFolder, filename);
    }
}
