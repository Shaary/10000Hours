package com.shaary.a10000hours.view;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.model.Skill;

public class SkillAdapter extends ListAdapter<Skill, SkillAdapter.MyViewHolder> {
    public static final String TAG = SkillAdapter.class.getSimpleName();

    private onHobbyClickListener listener;

    public interface onHobbyClickListener {
        void skillClicked(Skill skill);
    }

    public SkillAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Skill> DIFF_CALLBACK = new DiffUtil.ItemCallback<Skill>() {
        @Override
        public boolean areItemsTheSame(@NonNull Skill oldSkill, @NonNull Skill newSkill) {
            return oldSkill.getName().equals(newSkill.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Skill oldSkill, @NonNull Skill newSkill) {
            return oldSkill.getName().equals(newSkill.getName()) &&
                    oldSkill.getTime().equals(newSkill.getTime());
        }
    };

    public void setOnItemClickListener(onHobbyClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hobbie_item_view, parent, false);
        return new SkillAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Skill currentSkill = getItem(position);
        Log.d(TAG, "onBindViewHolder: curname " + currentSkill.getName());
        holder.onBind(currentSkill);
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

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.skillClicked(getItem(position));
                }
            });
        }

        public void onBind(Skill skill) {
            hobby.setText(skill.getName());
            time.setText(skill.getTime());
        }
    }
}
