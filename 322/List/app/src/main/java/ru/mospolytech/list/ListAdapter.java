package ru.mospolytech.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ListItem item = list.get(position);
        holder.imageView.setImageURI(item.image);
        holder.titleView.setText(item.title);
        holder.textView.setText(item.text);
        holder.buttonEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(context, EditActivity.class);
                editIntent.putExtra(EditActivity.IMAGE, item.image.toString());
                editIntent.putExtra(EditActivity.TITLE, item.title);
                editIntent.putExtra(EditActivity.TEXT, item.text);
                editIntent.putExtra(EditActivity.POSITION, position);
                ((Activity)context).startActivityForResult(editIntent, EditActivity.EDIT_REQUEST);
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.remove(position);
                SharedPreferences preferences = context.getSharedPreferences(
                        EditActivity.PREF, context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt(EditActivity.POSITION, list.size()-1);
                edit.remove(EditActivity.IMAGE + (list.size()));
                edit.remove(EditActivity.TITLE + (list.size()));
                edit.remove(EditActivity.TEXT + (list.size()));
                for (int i = position; i < list.size(); i++){
                    edit.putString(EditActivity.IMAGE + i, list.get(i).image.toString());
                    edit.putString(EditActivity.TITLE + i, list.get(i).title);
                    edit.putString(EditActivity.TEXT + i, list.get(i).text);
                }
                edit.apply();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleView;
        TextView textView;
        Button buttonEdit;
        Button buttonDelete;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView = itemView.findViewById(R.id.titleView);
            textView = itemView.findViewById(R.id.textView);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
