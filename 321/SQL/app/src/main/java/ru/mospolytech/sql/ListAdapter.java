package ru.mospolytech.sql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Context context;
    private List<ListItem> list;

    ListAdapter(Context context, List<ListItem> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    @SuppressLint("InflateParams")
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListItem item = list.get(position);
        holder.textID.setText(String.valueOf(item.id));
        holder.textName.setText(item.name);
        holder.textEmail.setText(item.email);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textID, textName, textEmail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textID = itemView.findViewById(R.id.textID);
            textName = itemView.findViewById(R.id.textName);
            textEmail = itemView.findViewById(R.id.textEmail);
        }
    }
}
