package com.shaary.a10000hours.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.contracts.MyViewHolderView;
import com.shaary.a10000hours.model.Hobby;
import com.shaary.a10000hours.presenter.MainActivityViewPresenter;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    MainActivityViewPresenter presenter;
    private Context context;

    interface OnHobbyClickListener {

    }

    public RecyclerViewAdapter(MainActivityViewPresenter presenter) {
        this.presenter = presenter;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hobbie_item_view, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        presenter.setUpRecyclerView(position, holder);

    }

    @Override
    public int getItemCount() {
        return presenter.getHobbiesListSize();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements MyViewHolderView{
        ImageView image;
        TextView hobby;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.hobby_image_view);
            hobby = itemView.findViewById(R.id.hobby_name);
            time = itemView.findViewById(R.id.hobby_time);
        }


        @Override
        public void setName(String name) {
            hobby.setText(name);
        }

        @Override
        public void setTime(String time) {
            this.time.setText(time);
        }

        @Override
        public void setImage(Drawable image) {
            this.image.setImageDrawable(image);
        }

        //TODO: find a way to update adapter
        @Override
        public void refreshHobbiesList() {
            notifyDataSetChanged();
        }
    }

}
