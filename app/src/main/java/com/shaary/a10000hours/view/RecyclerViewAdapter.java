package com.shaary.a10000hours.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.model.Hobby;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    //Trying to make it with an object instead of simple string
    List<Hobby> hobbies;

    public RecyclerViewAdapter(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!hobbies.isEmpty()) {
            holder.hobby.setText(hobbies.get(position).getName());
        }

    }

    @Override
    public int getItemCount() {
        return hobbies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView hobby;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.hobby_image_view);
            hobby = itemView.findViewById(R.id.hobby_name);
            time = itemView.findViewById(R.id.hobby_time);
        }
    }
}
