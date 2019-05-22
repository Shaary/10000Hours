package com.shaary.a10000hours.view;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.model.Session;
import com.shaary.a10000hours.model.Skill;

public class SessionsAdapter extends ListAdapter<Session, SessionsAdapter.MyViewHolder> {

    public SessionsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Session> DIFF_CALLBACK = new DiffUtil.ItemCallback<Session>() {
        @Override
        public boolean areItemsTheSame(@NonNull Session oldSession, @NonNull Session newSession) {
            return oldSession.timeId == newSession.timeId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Session oldSession, @NonNull Session newSession) {
            return oldSession.getSessionDate().equals(newSession.getSessionDate()) &&
                    oldSession.getSessionTime().equals(newSession.getSessionTime());
        }
    };

    @NonNull
    @Override
    public SessionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_item_view, parent, false);
        return new SessionsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionsAdapter.MyViewHolder holder, int position) {
        Session session = getItem(position);
        holder.onBind(session);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sessionText;
        Button deleteButton;
        Button editButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            sessionText = itemView.findViewById(R.id.session_text_view);
            deleteButton = itemView.findViewById(R.id.del_session_button);
            editButton = itemView.findViewById(R.id.edit_session_button);
        }
        public void onBind(Session session) {
            String date = android.text.format.DateFormat.format("yyyy-MM-dd", session.getSessionDate()).toString();
            sessionText.setText(date + " " + session.getSessionTime());
        }
    }
}
