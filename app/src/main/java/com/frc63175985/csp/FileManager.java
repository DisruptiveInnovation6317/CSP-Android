package com.frc63175985.csp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.frc63175985.csp.auth.ScoutAuthState;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class FileManager {
    public static final FileManager shared = new FileManager();
    private File rootFolder;
    private File pitFolder;
    private File matchFolder;
    private File aggregateFolder;
    private SimpleDateFormat FILENAME_FORMAT = new SimpleDateFormat("y-M-d-k-h-m-s-S", Locale.US);

    public static final int CHOOSE_AGGREGATOR_FILE_REQUEST_CODE = 1;

    private FileManager() {
        rootFolder = new File(Environment.getExternalStorageDirectory(), "CSP");
        pitFolder = new File(rootFolder, "PIT");
        matchFolder = new File(rootFolder, "MATCH");
        aggregateFolder = new File(rootFolder, "AGGREGATION");

        if (!pitFolder.exists()) {
            pitFolder.mkdirs();
        }

        if (!matchFolder.exists()) {
            matchFolder.mkdirs();
        }

        if (!aggregateFolder.exists()) {
            aggregateFolder.mkdirs();
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
        File newMatchFile = new File(matchFolder, FILENAME_FORMAT.format(new Date()) + ".csv");
        try {
            newMatchFile.createNewFile();
            FileWriter writer = new FileWriter(newMatchFile);
            writer.write(ScoutAuthState.shared.currentMatch.export());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the current Pit Scouting entry to the disk
     */
    public void savePit() {
        File newPitFile = new File(pitFolder, FILENAME_FORMAT.format(new Date()) + ".csv");
        try {
            newPitFile.createNewFile();
            FileWriter writer = new FileWriter(newPitFile);
            writer.write(ScoutAuthState.shared.pitScoutRecord.export());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
