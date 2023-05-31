package com.seurs.mareu.ui;

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
    public interface OnClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnClickListener listener) { mListener = listener; }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Button mButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.button);
        }
    }

    public PlaceAdapter(List<Integer> places) { this.mPlaces = places; }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
        Integer mPlace = mPlaces.get(position);
        holder.mButton.setBackgroundColor(mPlace);
        //holder.itemView.setBackgroundColor(mPlace);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(pos);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }
}
