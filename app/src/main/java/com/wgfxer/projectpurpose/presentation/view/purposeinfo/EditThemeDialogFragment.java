package com.wgfxer.projectpurpose.presentation.view.purposeinfo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.domain.PurposeTheme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EditThemeDialogFragment extends DialogFragment {
    private static final String EXTRA_IMAGE_PATH = "image_path";
    private static final String EXTRA_GRADIENT_ID = "gradient_id";
    private static final String EXTRA_GRADIENT_ALPHA = "gradient_alpha";
    private static final String EXTRA_IS_WHITE_FONT = "is_white_font";

    private OnThemeChangeListener onThemeChangeListener;

    interface OnThemeChangeListener {
        void onThemeChange(PurposeTheme theme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() instanceof EditThemeDialogFragment.OnThemeChangeListener) {
            onThemeChangeListener = (OnThemeChangeListener) getActivity();
        }
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_theme, null, false);

        final PurposeTheme purposeTheme = new PurposeTheme();
        purposeTheme.setImagePath(getArguments().getString(EXTRA_IMAGE_PATH));
        purposeTheme.setGradientId(getArguments().getInt(EXTRA_GRADIENT_ID));
        purposeTheme.setGradientAlpha(getArguments().getFloat(EXTRA_GRADIENT_ALPHA));
        purposeTheme.setWhiteFont(getArguments().getBoolean(EXTRA_IS_WHITE_FONT));

        TypedArray typedArrayGradients = getResources().obtainTypedArray(R.array.gradients_array);
        int[] gradients = new int[typedArrayGradients.length()];
        for (int i = 0; i < gradients.length; i++) {
            gradients[i] = typedArrayGradients.getResourceId(i, 0);
        }
        typedArrayGradients.recycle();
        int selectedGradientPosition = 0;
        for (int i = 0; i < gradients.length; i++) {
            if (gradients[i] == purposeTheme.getGradientId()) {
                selectedGradientPosition = i;
            }
        }
        RecyclerView recyclerViewGradients = v.findViewById(R.id.recycler_view_gradients);
        recyclerViewGradients.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        GradientsAdapter adapter = new GradientsAdapter(gradients, new GradientsAdapter.OnGradientClickListener() {
            @Override
            public void onGradientClick(int gradientResourceId) {
                purposeTheme.setGradientId(gradientResourceId);
            }
        }, selectedGradientPosition);
        recyclerViewGradients.setAdapter(adapter);

        SeekBar seekbarGradientAlpha = v.findViewById(R.id.seekbar_gradient_alpha);
        seekbarGradientAlpha.setProgress((int) (purposeTheme.getGradientAlpha() * 100));
        seekbarGradientAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                purposeTheme.setGradientAlpha(i / 100f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final CheckBox isWhiteFontCheckBox = v.findViewById(R.id.checkbox_is_white_font);
        isWhiteFontCheckBox.setChecked(purposeTheme.isWhiteFont());
        isWhiteFontCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purposeTheme.setWhiteFont(isWhiteFontCheckBox.isChecked());         //возможна ошибка
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.edit_theme_text)
                .setView(v)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onThemeChangeListener != null) {
                            onThemeChangeListener.onThemeChange(purposeTheme);
                        }
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
    }

    public static EditThemeDialogFragment newInstance(PurposeTheme theme) {
        Bundle args = new Bundle();
        args.putString(EXTRA_IMAGE_PATH, theme.getImagePath());
        args.putInt(EXTRA_GRADIENT_ID, theme.getGradientId());
        args.putFloat(EXTRA_GRADIENT_ALPHA, theme.getGradientAlpha());
        args.putBoolean(EXTRA_IS_WHITE_FONT, theme.isWhiteFont());

        EditThemeDialogFragment fragment = new EditThemeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
