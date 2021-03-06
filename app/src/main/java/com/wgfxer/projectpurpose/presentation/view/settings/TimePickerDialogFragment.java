package com.wgfxer.projectpurpose.presentation.view.settings;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.wgfxer.projectpurpose.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * ДиалогФрагмент для выбора времени напоминания
 */
public class TimePickerDialogFragment extends DialogFragment {

    private static final String KEY_HOURS = "KEY_HOURS";
    private static final String KEY_MINUTES = "KEY_MINUTES";

    private OnTimeSetListener onTimeSetListener;
    private TimePicker timePicker;

    private int hours;
    private int minutes;

    /**
     * интерфейс для прослушивания события установки времени
     */
    public interface OnTimeSetListener {
        void onTimeSet(int hours, int minutes);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.time_picker, null, false);
        timePicker = view.findViewById(R.id.time_picker);
        if (getActivity() instanceof OnTimeSetListener) {
            onTimeSetListener = (OnTimeSetListener) getActivity();
        }
        initViews(savedInstanceState);
        return new AlertDialog.Builder(getActivity() )
                .setTitle("Выберите время уведомления")
                .setView(view)
                .setPositiveButton(R.string.dialog_ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        if (onTimeSetListener != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                onTimeSetListener.onTimeSet(timePicker.getHour(), timePicker.getMinute());
                            } else {
                                onTimeSetListener.onTimeSet(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                            }
                        }
                        dialog1.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        dialog1.dismiss();
                    }
                }).create();
    }

    private void initViews(@Nullable Bundle savedInstanceState) {
        timePicker.setIs24HourView(true);
        hours = getArguments().getInt(KEY_HOURS);
        minutes = getArguments().getInt(KEY_MINUTES);
        if (savedInstanceState != null) {
            hours = savedInstanceState.getInt(KEY_HOURS);
            minutes = savedInstanceState.getInt(KEY_MINUTES);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hours);
            timePicker.setMinute(minutes);
        } else {
            timePicker.setCurrentHour(hours);
            timePicker.setCurrentMinute(minutes);
        }
    }


    /**
     * Создает и возвращает экземпляр диалога в зависимости от уже выбранного времени
     */
    public static TimePickerDialogFragment newInstance(int hours, int minutes) {
        Bundle args = new Bundle();
        args.putInt(KEY_HOURS, hours);
        args.putInt(KEY_MINUTES, minutes);
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * При отвязке
     */
    @Override
    public void onDetach() {
        super.onDetach();
        onTimeSetListener = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outState.putInt(KEY_HOURS, timePicker.getHour());
            outState.putInt(KEY_MINUTES, timePicker.getMinute());
        } else {
            outState.putInt(KEY_HOURS, timePicker.getCurrentHour());
            outState.putInt(KEY_MINUTES, timePicker.getCurrentMinute());
        }
    }
}
