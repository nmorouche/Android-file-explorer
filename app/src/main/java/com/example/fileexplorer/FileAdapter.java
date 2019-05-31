package com.example.fileexplorer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter {
    private ArrayList<CellDTO> file;
    private ClickListener clickListener;

    public void setFile(ArrayList<CellDTO> file) {
        this.file = file;
        notifyDataSetChanged();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        if(i == 0) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.folder_view_holder, viewGroup, false);
            return new FolderViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_view_holder, viewGroup, false);
            return new FileViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(file.get(position).getType().equals("file")) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final CellDTO cell = file.get(i);
        if(viewHolder instanceof FileViewHolder){
            FileViewHolder newFile = (FileViewHolder) viewHolder;
            newFile.fileViewText.setText(cell.getTitle());
            if(clickListener != null) {
                newFile.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(cell);
                    }
                });
            }
        } else {
            FolderViewHolder newFile = (FolderViewHolder) viewHolder;
            newFile.folderViewText.setText(cell.getTitle());
            if(clickListener != null) {
                newFile.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(cell);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return file != null?file.size():0;
    }
}