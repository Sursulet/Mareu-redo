package com.seurs.mareu.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seurs.mareu.R;
import com.seurs.mareu.model.Meeting;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;
    private OnClickListener mListener;

    public interface OnClickListener {
        //void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mMeetingColor;
        private final TextView mMeetingItemTitle;
        private final TextView mMeetingParticipants;

        public ViewHolder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);

            mMeetingColor = itemView.findViewById(R.id.meeting_color);
            mMeetingItemTitle = itemView.findViewById(R.id.meeting_item_title);
            mMeetingParticipants = itemView.findViewById(R.id.meeting_item_participants);
            ImageButton mOnDeleteMeeting = itemView.findViewById(R.id.on_delete_meeting);

            mOnDeleteMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public MeetingAdapter(List<Meeting> mMeetings) {
        this.mMeetings = mMeetings;
    }

    @NonNull
    @Override
    public MeetingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_row_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.ViewHolder holder, int position) {
        Meeting mMeeting = mMeetings.get(position);
        holder.mMeetingColor.setColorFilter(mMeeting.getPlace());
        holder.mMeetingItemTitle.setText(String.format("%s - %s - %s", mMeeting.getTopic(), mMeeting.getHour(), mMeeting.getManager()));
        holder.mMeetingParticipants.setText(TextUtils.join(", ", mMeeting.getParticipants()));
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}
