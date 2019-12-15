package com.wgfxer.projectpurpose.presentation.view.settings;

import android.app.AlertDialog;
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
import androidx.fragment.app.DialogFragment;

/**
 * ДиалогФрагмент для выбора времени напоминания
 */
public class TimePickerFragment extends DialogFragment {

    private static final String KEY_HOURS = "KEY_HOURS";
    private static final String KEY_MINUTES = "KEY_MINUTES";

    private OnTimeSetListener onTimeSetListener;

    void setOnTimeSetListener(OnTimeSetListener onTimeSetListener) {
        this.onTimeSetListener = onTimeSetListener;
    }

    /**
     * интерфейс для прослушивания события установки времени
     */
    interface OnTimeSetListener {
        void onTimeSet(int hours, int minutes);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.time_picker, null, false);
        final TimePicker timePicker = view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(getArguments().getInt(KEY_HOURS));
            timePicker.setMinute(getArguments().getInt(KEY_MINUTES));
        } else {
            timePicker.setCurrentHour(getArguments().getInt(KEY_HOURS));
            timePicker.setCurrentMinute(getArguments().getInt(KEY_MINUTES));
        }
        return new AlertDialog.Builder(getContext())
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

    /**
     * Создает и возвращает экземпляр диалога в зависимости от уже выбранного времени
     */
    public static TimePickerFragment newInstance(int hours, int minutes) {
        Bundle args = new Bundle();
        args.putInt(KEY_HOURS, hours);
        args.putInt(KEY_MINUTES, minutes);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
