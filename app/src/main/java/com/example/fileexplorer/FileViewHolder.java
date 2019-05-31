package com.example.fileexplorer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.file_view_holder_text)
    TextView fileViewText;
    @BindView(R.id.file_view_holder_image)
    ImageView fileViewImage;

    public FileViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
