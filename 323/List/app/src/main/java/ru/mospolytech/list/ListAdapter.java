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

import static android.content.Context.MODE_PRIVATE;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private List<ListItem> list;

    public ListAdapter(Context context, List<ListItem> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
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
                editIntent.putExtra(EditActivity.POSITION, position);
                editIntent.putExtra(EditActivity.IMAGE, item.image);
                editIntent.putExtra(EditActivity.TITLE, item.title);
                editIntent.putExtra(EditActivity.TEXT, item.text);
                ((Activity) context).startActivityForResult(editIntent, MainActivity.EDIT);
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.remove(position);
                SharedPreferences.Editor editor = context.getSharedPreferences(
                        EditActivity.PREF, MODE_PRIVATE).edit();
                editor.putInt(EditActivity.COUNT, list.size());
                editor.remove(EditActivity.IMAGE + String.valueOf(position));
                editor.remove(EditActivity.TITLE + String.valueOf(position));
                editor.remove(EditActivity.TEXT + String.valueOf(position));
                editor.commit();
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
