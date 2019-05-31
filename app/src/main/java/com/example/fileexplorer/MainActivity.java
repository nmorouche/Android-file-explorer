package com.example.fileexplorer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_rv)
    RecyclerView recyclerView;
    FileAdapter fileAdapter;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
        path = Environment.getRootDirectory().getAbsolutePath();
        displayData(path);
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new FileAdapter();
        recyclerView.setAdapter(fileAdapter);
        fileAdapter.setClickListener(new ClickListener() {
            @Override
            public void onClick(CellDTO cellDTO) {
                Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
                if(cellDTO.getType().equals("file")){
                    Toast.makeText(MainActivity.this, "C'EST UN FICHIER", Toast.LENGTH_SHORT).show();
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
        Uri path1 = Uri.fromFile(file);
        Intent openIntent = new Intent(Intent.ACTION_VIEW);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        openIntent.setDataAndType(path1, "*/*");
        try {
            startActivity(openIntent);
        }
        catch (ActivityNotFoundException e) {
            Log.d("openFile", e.toString());
        }
    }

    private void displayData(String path) {
        File directory = new File(path);
        ArrayList<CellDTO> arrayList = new ArrayList<>();
        String[] directories = directory.list();
        for(String d:directories) {
            CellDTO cell;
            File test = new File(path + "/" + d);
            if(test.isDirectory()) {
                cell = new CellDTO(d, "folder");
            } else {
                cell = new CellDTO(d, "file");
            }
            arrayList.add(cell);
        }
        fileAdapter.setFile(arrayList);
    }

    @Override
    public void onBackPressed() {
        File currentDirectory = new File(path);
        if(currentDirectory.getAbsolutePath().equals(Environment.getRootDirectory().getAbsolutePath())){
            Toast.makeText(MainActivity.this, "Quittez", Toast.LENGTH_SHORT).show();
        } else {
            path = currentDirectory.getParent();
            displayData(path);
        }
    }
}