package com.wgfxer.projectpurpose.presentation.view.purposeslist;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.helper.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PurposesAdapter extends RecyclerView.Adapter<PurposesAdapter.PurposesHolder> {
    private List<Purpose> purposesList = new ArrayList<>();
    private OnPurposeClickListener onPurposeClickListener;

    @NonNull
    @Override
    public PurposesAdapter.PurposesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purpose_list_item, parent, false);
        return new PurposesAdapter.PurposesHolder(view);
    }

    @Override
    public void onBindViewHolder(PurposesAdapter.PurposesHolder holder, int position) {
        holder.bind(purposesList.get(position));
    }

    @Override
    public int getItemCount() {
        return purposesList.size();
    }

    void setPurposesList(List<Purpose> purposesList) {
        this.purposesList = purposesList;
        notifyDataSetChanged();
    }

    void setOnPurposeClickListener(OnPurposeClickListener onPurposeClickListener) {
        this.onPurposeClickListener = onPurposeClickListener;
    }

    class PurposesHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView dateTextView;
        private TextView daysToGoalTextView;
        private TextView daysHintTextView;
        private ImageView imageViewBackground;
        private ImageView imageViewGradient;

        PurposesHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.list_title_text_view);
            dateTextView = itemView.findViewById(R.id.purpose_date);
            daysToGoalTextView = itemView.findViewById(R.id.days);
            daysHintTextView = itemView.findViewById(R.id.text_days);
            imageViewBackground = itemView.findViewById(R.id.image_view_background);
            imageViewGradient = itemView.findViewById(R.id.image_view_gradient);
        }

        void bind(final Purpose purpose) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPurposeClickListener != null) {
                        onPurposeClickListener.onPurposeClick(purpose);
                    }
                }
            });
            imageViewGradient.setImageResource(purpose.getTheme().getGradientId());
            imageViewGradient.setAlpha(purpose.getTheme().getGradientAlpha());
            if (purpose.getTheme().getImagePath() != null) {
                imageViewBackground.setImageBitmap(BitmapFactory.decodeFile(purpose.getTheme().getImagePath()));
            } else {
                imageViewGradient.setAlpha(1f);
            }
            titleTextView.setText(purpose.getTitle());
            dateTextView.setText(Utils.getStringFromDate(purpose.getDate()));
            int daysToGoal = Utils.getDaysFromDate(purpose.getDate());
            if (daysToGoal > 0) {
                daysToGoalTextView.setVisibility(View.VISIBLE);
                daysHintTextView.setVisibility(View.VISIBLE);
                daysToGoalTextView.setText(String.valueOf(daysToGoal));
            } else {
                daysToGoalTextView.setVisibility(View.INVISIBLE);
                daysHintTextView.setVisibility(View.INVISIBLE);
            }

            if (purpose.getTheme().isWhiteFont()) {
                titleTextView.setTextColor(Color.WHITE);
                dateTextView.setTextColor(Color.WHITE);
                daysToGoalTextView.setTextColor(Color.WHITE);
                daysHintTextView.setTextColor(Color.WHITE);
            } else {
                titleTextView.setTextColor(Color.BLACK);
                dateTextView.setTextColor(Color.DKGRAY);
                daysToGoalTextView.setTextColor(Color.BLACK);
                daysHintTextView.setTextColor(Color.DKGRAY);
            }
        }
    }


    interface OnPurposeClickListener {
        void onPurposeClick(Purpose purpose);
    }
}