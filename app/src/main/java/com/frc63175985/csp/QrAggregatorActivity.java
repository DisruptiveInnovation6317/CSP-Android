package com.frc63175985.csp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrAggregatorActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private FrameLayout frameLayout;
    private ZXingScannerView mScannerView;
    private ListView scannedListView;
    private ArrayAdapter<String> listViewValues;
    private boolean showingScanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_aggregator);

        frameLayout = findViewById(R.id.qr_aggregator_content_frame);
        scannedListView = new ListView(this);
        scannedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(QrAggregatorActivity.this)
                        .setTitle("Delete this entry")
                        .setMessage("Are you sure you want to delete this?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete match at position
                                listViewValues.remove(listViewValues.getItem(position));
                                listViewValues.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });

        mScannerView = new ZXingScannerView(this);
        mScannerView.setFormats(Collections.singletonList(BarcodeFormat.QR_CODE));

        listViewValues = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        scannedListView.setAdapter(listViewValues);

        showScanned();

        findViewById(R.id.qr_scan_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showingScanner) {
                    QrAggregatorActivity.this.showScanned();
                } else {
                    QrAggregatorActivity.this.showCamera();
                }
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private void showCamera() {
        frameLayout.removeAllViews();
        frameLayout.addView(mScannerView);
        mScannerView.startCamera();
        showingScanner = true;

        if (Debug.EMULATE_QR_SCAN) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    QrAggregatorActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    QrAggregatorActivity.this,
                                    "Emulating scan in 3 seconds.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    QrAggregatorActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleResult(null);
                        }
                    });
                }
            }).start();
        }
    }

    private void showScanned() {
        mScannerView.stopCamera();
        frameLayout.removeAllViews();
        frameLayout.addView(scannedListView);
        showingScanner = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
    }

    @Override
    protected void onStop() {
        mScannerView.stopCamera();
        super.onStop();
    }

    @Override
    public void handleResult(Result result) {
        if (result == null) {
            if (Debug.EMULATE_QR_SCAN) {
                listViewValues.add("DEFAULT,Cedar+Falls,,,-1,-1,Richards,+Brandon,FALSE,FALSE," +
                        "FALSE,FALSE,-1,-1,FALSE,0,-1,FALSE,-1,FALSE,-1,FALSE,-1,FALSE,-1,FALSE" +
                        ",-1,FALSE,-1,FALSE,-1,FALSE,-1,FALSE,-1,FALSE,FALSE,FALSE,FALSE,FALSE," +
                        "-1,FALSE,-1,FALSE,-1,FALSE,-1,FALSE,-1,FALSE,-1,FALSE,-1,FALSE,-1,FALSE," +
                        "-1,FALSE,-1,FALSE,,FALSE,FALSE,0,FALSE,FALSE,FALSE,FALSE,FALSE");
                listViewValues.add("DEFAULT,Cedar+Falls,,,1,1,Richards,+Brandon,TRUE,TRUE," +
                        "TRUE,TRUE,1,1,TRUE,0,1,TRUE,1,TRUE,1,TRUE,1,TRUE,1,TRUE" +
                        ",1,TRUE,1,TRUE,1,TRUE,1,TRUE,1,TRUE,TRUE,TRUE,TRUE,TRUE," +
                        "1,TRUE,1,TRUE,1,TRUE,1,TRUE,1,TRUE,1,TRUE,1,TRUE,1,TRUE," +
                        "1,TRUE,1,TRUE,,TRUE,TRUE,0,TRUE,TRUE,TRUE,TRUE,TRUE");
                listViewValues.notifyDataSetChanged();
                showScanned();
                Toast.makeText(this, "Added new match data!", Toast.LENGTH_SHORT).show();
            }

            return;
        }

        listViewValues.add(result.getText());
        listViewValues.notifyDataSetChanged();
        showScanned();
        Toast.makeText(this, "Added new match data!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mScannerView.stopCamera();
            mScannerView.startCamera();
        }
    }

    public void onClick(View view) {
        if (showingScanner) {
            return;
        }

        if (view.getId() == R.id.qr_action_clear) {
            new AlertDialog.Builder(QrAggregatorActivity.this)
                    .setTitle("Clear all entries.")
                    .setMessage("Are you sure you want to delete all entries?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listViewValues.clear();
                            listViewValues.notifyDataSetChanged();
                            Toast.makeText(QrAggregatorActivity.this, "Matches cleared", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        } else if (view.getId() == R.id.qr_action_export) {
            StringBuilder allEntries = new StringBuilder();
            for (int i = 0; i < listViewValues.getCount(); i++) {
                allEntries.append(listViewValues.getItem(i)).append("\n");
            }

            String allEntriesContent = allEntries.toString();
            if (allEntriesContent.isEmpty()) {
                return;
            }

            final File aggregationFile = FileManager.shared.saveAggregation(allEntriesContent);

            if (aggregationFile == null) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("There was an error saving the file")
                        .setNegativeButton(android.R.string.ok, null)
                        .show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("File saved")
                        .setMessage("Successfully exported to path:\n" + aggregationFile.getAbsolutePath() + "\nWould you like to share this file?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                Uri uri = FileProvider.getUriForFile(
                                        QrAggregatorActivity.this,
                                        QrAggregatorActivity.this.getApplicationContext()
                                                .getPackageName() + ".fileprovider", aggregationFile);
                                shareIntent.setDataAndType(uri, "text/plain");
                                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                startActivity(Intent.createChooser(shareIntent, "Share file using"));
                            }
                        })
                        .show();
            }
        }
    }
}
