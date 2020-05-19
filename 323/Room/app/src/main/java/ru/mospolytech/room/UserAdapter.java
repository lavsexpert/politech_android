package ru.mospolytech.room;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    List<User> list;
    private int selectedPosition;
    private View selected;

    UserAdapter(Context context, List<User> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ((MainActivity)context).position = position;
        User user = list.get(position);
        holder.textID.setText(String.valueOf(user.id));
        holder.textName.setText(user.name);
        holder.textEmail.setText(user.email);

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != null){
                    selected.setBackgroundColor(Color.WHITE);
                }
                v.setBackgroundColor(Color.CYAN);
                selectedPosition = position;
                selected = v;
                ((MainActivity)context).position = selectedPosition;
            }
        });

        holder.line.setSelected(selectedPosition == position);
        if(selectedPosition == position && selected != null)
            holder.line.setBackgroundColor(Color.CYAN);
        else
            holder.line.setBackgroundColor(Color.WHITE);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textID, textName, textEmail;
        LinearLayout line;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textID = itemView.findViewById(R.id.textID);
            textName = itemView.findViewById(R.id.textName);
            textEmail = itemView.findViewById(R.id.textEmail);
            line = itemView.findViewById(R.id.line);
        }
    }
}
