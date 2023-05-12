package com.seurs.mareu.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seurs.mareu.R;
import com.seurs.mareu.model.Meeting;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private List<Meeting> mMeetings;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mMeetingColor;
        private final TextView mMeetingItemTitle;
        private final TextView mMeetingParticipants;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMeetingColor = itemView.findViewById(R.id.meeting_color);
            mMeetingItemTitle = itemView.findViewById(R.id.meeting_item_title);
            mMeetingParticipants = itemView.findViewById(R.id.meeting_item_participants);
        }
    }

    @NonNull
    @Override
    public MeetingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.ViewHolder holder, int position) {
        Meeting mMeeting = mMeetings.get(position);
        holder.mMeetingItemTitle.setText(mMeeting.getPlace());
        holder.mMeetingParticipants.setText(mMeeting.getParticipants());
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}
