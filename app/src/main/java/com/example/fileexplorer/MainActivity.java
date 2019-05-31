package com.example.fileexplorer;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.fileexplorer.Model.CellDTO;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_rv)
    RecyclerView recyclerView;
    FileAdapter fileAdapter;
    private String path;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    displayData(path);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        File currentDirectory = new File(path);
        if (currentDirectory.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
            exitAlert();
        } else {
            path = currentDirectory.getParent();
            displayData(path);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new FileAdapter();
        recyclerView.setAdapter(fileAdapter);
        fileAdapter.setClickListener(new ClickListener() {
            @Override
            public void onClick(CellDTO cellDTO) {
                if (cellDTO.getType().equals("file")) {
                    openFile(path, cellDTO.getTitle());
                } else {
                    path = path + "/" + cellDTO.getTitle();
                    displayData(path);
                }
            }
        });
    }

    private void openFile(String path, String title) {
        File file = new File(path + "/" + title);
        Uri path1 = FileProvider.getUriForFile(MainActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                file);
        Intent openIntent = new Intent(Intent.ACTION_VIEW);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        openIntent.setDataAndType(path1, "application/*");
        try {
            startActivity(openIntent);
        } catch (ActivityNotFoundException e) {
            Log.d("openFile", e.toString());
        }
    }

    private void displayData(String path) {
        File directory = new File(path);
        ArrayList<CellDTO> arrayList = new ArrayList<>();
        String[] directories = directory.list();
        for (String d : directories) {
            CellDTO cell;
            File test = new File(path + "/" + d);
            if (test.isDirectory()) {
                cell = new CellDTO(d, "folder");
            } else {
                cell = new CellDTO(d, "file");
            }
            arrayList.add(cell);
        }
        fileAdapter.setFile(arrayList);
    }

    private void exitAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Quittez")
                .setMessage("Voulez-vous quitter l'application ?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            displayData(path);
        }
    }
}