package com.wgfxer.projectpurpose.presentation.view.purpose_info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wgfxer.projectpurpose.R;

import androidx.recyclerview.widget.RecyclerView;

public class GradientsAdapter extends RecyclerView.Adapter<GradientsAdapter.GradientsViewHolder> {
    private int[] gradients;
    private OnGradientClickListener onGradientClickListener;
    private int positionChecked;

    GradientsAdapter(int[] gradients, OnGradientClickListener onGradientClickListener, int positionChecked) {
        this.gradients = gradients;
        this.onGradientClickListener = onGradientClickListener;
        this.positionChecked = positionChecked;
    }

    @Override
    public GradientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GradientsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gradient_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GradientsViewHolder holder, int position) {
        holder.bind(gradients[position]);
    }

    @Override
    public int getItemCount() {
        return gradients.length;
    }

    class GradientsViewHolder extends RecyclerView.ViewHolder {
        private ImageView gradientImage;
        private ImageView isSelectedImage;

        GradientsViewHolder(View itemView) {
            super(itemView);
            gradientImage = itemView.findViewById(R.id.gradient_image);
            isSelectedImage = itemView.findViewById(R.id.is_selected_image);
        }

        void bind(final int gradientResourceId) {
            gradientImage.setImageResource(gradientResourceId);
            if (gradients[positionChecked] == gradientResourceId) {
                isSelectedImage.setVisibility(View.VISIBLE);
            } else {
                isSelectedImage.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    positionChecked = getAdapterPosition();
                    if (onGradientClickListener != null) {
                        onGradientClickListener.onGradientClick(gradientResourceId);
                    }
                    notifyDataSetChanged();
                }
            });

        }
    }

    interface OnGradientClickListener {
        void onGradientClick(int gradientResourceId);
    }
}
