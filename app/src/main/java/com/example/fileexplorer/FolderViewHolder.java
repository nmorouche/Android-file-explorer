package com.example.fileexplorer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FolderViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.folder_view_holder_text)
    TextView folderViewText;
    @BindView(R.id.folder_view_holder_image)
    ImageView folderViewImage;

    public FolderViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
