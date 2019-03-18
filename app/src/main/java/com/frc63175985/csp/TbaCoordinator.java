package com.frc63175985.csp;

import android.os.AsyncTask;

import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.auth.ScoutAuthStateListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

interface TbaAsyncDelegate {
    void processFinished(String[] teamNames);
}

public class TbaCoordinator implements TbaAsyncDelegate, ScoutAuthStateListener {
    public static TbaCoordinator shared = new TbaCoordinator();

    private final String API_KEY = "DzhuOimNqUcdpPEhDhFMoIFltbTNnIAner1f64b3aSSNrDTpZ1ZozPdN263iIV8L";

    public String[] teams;

    private TbaCoordinator() {
        ScoutAuthState.shared.addStateListener(this);
        if (ScoutAuthState.shared.isLoggedIn()) {
            authStateChanged(true);
        }
    }

    public void pullEventData(TbaAsyncDelegate delegate) {
        // Make sure we have an event code
        if (ScoutAuthState.shared.tournament == null ||
            ScoutAuthState.shared.tournament.isEmpty()) {
            return;
        }

        new TbaAsyncRequest(this, delegate).execute(API_KEY);
    }

    @Override
    public void processFinished(String[] teamNames) {
        teams = teamNames;

        if (teams != null) {
            Debug.log("Pulled " + teamNames.length + " teams from TBA");
            FileManager.shared.saveEvent(teamNames);
        }
    }

    @Override
    public void authStateChanged(boolean loggedIn) {
        if (!loggedIn) {
            teams = null;
            return;
        }

        // Attempt to load an event file
        teams = FileManager.shared.loadEvent();
    }
}

class TbaAsyncRequest extends AsyncTask<String, Void, String> {
    private TbaAsyncDelegate[] delegates;

    public TbaAsyncRequest(TbaAsyncDelegate... delegates) {
        this.delegates = delegates;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings.length != 1) {
            throw new IllegalArgumentException("Must supply only one string");
        }

        String href = String.format(Locale.US, "https://thebluealliance.com/api/v3/event/%s/teams", ScoutAuthState.shared.tournament);
        HttpsURLConnection urlConnection = null;
        Debug.log("Requesting from " + href);

        String contents = null;

        try {
            URL url = new URL(href);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("X-TBA-Auth-Key", strings[0]);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            byte[] buffer = new byte[1024];

            int bytesRead;
            StringBuilder content = new StringBuilder();
            while((bytesRead = in.read(buffer)) != -1) {
                content.append(new String(buffer, 0, bytesRead));
            }

            contents = content.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return contents;
    }

    @Override
    protected void onPostExecute(String result) {
        String[] strings = null;

        try {
            JSONArray jsonResult = new JSONArray(result);
            int[] values = new int[jsonResult.length()];

            for (int i = 0; i < jsonResult.length(); i++) {
                JSONObject teamObject = jsonResult.getJSONObject(i);
                int teamNumber = teamObject.getInt("team_number");
                values[i] = teamNumber;
            }

            // Sort array
            Arrays.sort(values);

            strings = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                strings[i] = String.valueOf(values[i]);
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }

        for (TbaAsyncDelegate delegate : delegates) {
            delegate.processFinished(strings);
        }
    }
}
