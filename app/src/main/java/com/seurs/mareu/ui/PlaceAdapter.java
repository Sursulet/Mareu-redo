package com.seurs.mareu.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seurs.mareu.R;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private final List<Integer> mPlaces;
    private OnClickListener mListener;
    int row_index = 0;

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button mButton;

        public ViewHolder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);
            /*mButton = itemView.findViewById(R.id.button);

            Log.d("PEACH", "ViewHolder: "+row_index);
            if (row_index == -1) {
                itemView.setBackgroundColor(Color.TRANSPARENT);
            } else {
                if (row_index == getAdapterPosition()) {
                    itemView.setBackgroundColor(Color.CYAN);
                } else {
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                }
            }
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                            if (row_index != position) {
                                notifyItemChanged(row_index);
                                row_index = getAdapterPosition();
                                notifyItemChanged(row_index);
                            }
                        }
                    }
                }
            });*/
        }
    }

    public PlaceAdapter(List<Integer> places) {
        this.mPlaces = places;
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_row_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
        Integer mPlace = mPlaces.get(position);
        //holder.mButton.setBackgroundColor(mPlace);
        if (row_index == position) {
            holder.itemView.setBackgroundColor(Color.CYAN);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        //holder.itemView.setBackgroundColor(mPlace);

    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }
}
