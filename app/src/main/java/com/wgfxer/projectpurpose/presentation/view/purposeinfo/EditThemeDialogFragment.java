package com.wgfxer.projectpurpose.presentation.view.purposeinfo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.PurposeTheme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Диалог для изменения темы цели
 */
public class EditThemeDialogFragment extends DialogFragment {
    private static final String EXTRA_IMAGE_PATH = "image_path";
    private static final String EXTRA_GRADIENT_NUMBER = "gradient_number";
    private static final String EXTRA_GRADIENT_ALPHA = "gradient_alpha";
    private static final String EXTRA_IS_WHITE_FONT = "is_white_font";

    private static final String KEY_GRADIENT_NUMBER = "KEY_GRADIENT_NUMBER";
    private static final String KEY_IS_WHITE_FONT = "KEY_IS_WHITE_FONT";


    private OnThemeChangeListener onThemeChangeListener;
    private PurposeTheme purposeTheme;

    /**
     * интерфейса для слушания события при нажатии на кнопку готово
     */
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

        purposeTheme = new PurposeTheme();
        purposeTheme.setImagePath(getArguments().getString(EXTRA_IMAGE_PATH));
        purposeTheme.setGradientPosition(getArguments().getInt(EXTRA_GRADIENT_NUMBER));
        purposeTheme.setGradientAlpha(getArguments().getFloat(EXTRA_GRADIENT_ALPHA));
        purposeTheme.setWhiteFont(getArguments().getBoolean(EXTRA_IS_WHITE_FONT));

        if (savedInstanceState != null) {
            purposeTheme.setGradientPosition(savedInstanceState.getInt(KEY_GRADIENT_NUMBER));
            purposeTheme.setWhiteFont(savedInstanceState.getBoolean(KEY_IS_WHITE_FONT));
        }

        setupGradientsRecycler(v);

        setupAlphaSeekbar(v);

        setupCheckbox(v);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.edit_theme_text)
                .setView(v)
                .setPositiveButton(R.string.dialog_ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onThemeChangeListener != null) {
                            onThemeChangeListener.onThemeChange(purposeTheme);
                        }
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
    }

    /**
     * Инициализация чекбокса для выбора светлой или темной темы цели
     */
    private void setupCheckbox(View v) {
        final CheckBox isWhiteFontCheckBox = v.findViewById(R.id.checkbox_is_white_font);
        isWhiteFontCheckBox.setChecked(purposeTheme.isWhiteFont());
        isWhiteFontCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purposeTheme.setWhiteFont(isWhiteFontCheckBox.isChecked());
            }
        });
    }

    /**
     * Инициализация ползунка для выбора прозрачности градиента
     */
    private void setupAlphaSeekbar(View v) {
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
    }

    /**
     * Инициализация RecyclerView для выбора градиента
     */
    private void setupGradientsRecycler(View v) {
        RecyclerView recyclerViewGradients = v.findViewById(R.id.recycler_view_gradients);
        recyclerViewGradients.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        GradientsAdapter adapter = new GradientsAdapter(PurposeTheme.GRADIENTS, new GradientsAdapter.OnGradientClickListener() {
            @Override
            public void onGradientClick(int gradientNumber) {
                purposeTheme.setGradientPosition(gradientNumber);
            }
        }, purposeTheme.getGradientPosition());
        recyclerViewGradients.setAdapter(adapter);
    }

    /**
     * Сохранение состояния при смене конфигурации
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_GRADIENT_NUMBER, purposeTheme.getGradientPosition());
        outState.putBoolean(KEY_IS_WHITE_FONT, purposeTheme.isWhiteFont());
    }

    /**
     * Создает и возвращает экземпляр диалога для заданной темы
     */
    public static EditThemeDialogFragment newInstance(PurposeTheme theme) {
        Bundle args = new Bundle();
        args.putString(EXTRA_IMAGE_PATH, theme.getImagePath());
        args.putInt(EXTRA_GRADIENT_NUMBER, theme.getGradientPosition());
        args.putFloat(EXTRA_GRADIENT_ALPHA, theme.getGradientAlpha());
        args.putBoolean(EXTRA_IS_WHITE_FONT, theme.isWhiteFont());

        EditThemeDialogFragment fragment = new EditThemeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * При отвязке от активити занулим ссылку на нее, чтобы не было утечки
     */
    @Override
    public void onDetach() {
        super.onDetach();
        onThemeChangeListener = null;
    }
}
