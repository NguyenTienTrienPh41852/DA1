package com.example.da1_t6.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Fragment.fragment_QuanLyKhoanChi;
import com.example.da1_t6.Model.Icon;
import com.example.da1_t6.R;

import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {
    private Context context;
    private List<Icon> iconList;

    fragment_QuanLyKhoanChi fragmentQuanLyKhoanChi;
    Dialog dialog;

    public IconAdapter(Context context, List<Icon> iconList, fragment_QuanLyKhoanChi fragmentQuanLyKhoanChi, Dialog dialog) {
        this.context = context;
        this.iconList = iconList;
        this.fragmentQuanLyKhoanChi = fragmentQuanLyKhoanChi;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon, parent, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        Icon iconResId = iconList.get(position);
        Log.e("TAG","icon: "+iconResId);
        holder.iconImageView.setImageResource(iconResId.getIcon());
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    public class IconViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImageView;

        public IconViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Handle item click, e.g., show dialog or perform an action
                        fragmentQuanLyKhoanChi.setIcon(iconList.get(position));
                        dialog.dismiss();
                    }
                }
            });
        }
    }


}
