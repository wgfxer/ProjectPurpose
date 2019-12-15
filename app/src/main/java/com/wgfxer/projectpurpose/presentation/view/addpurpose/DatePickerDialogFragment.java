package com.wgfxer.projectpurpose.presentation.view.addpurpose;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.wgfxer.projectpurpose.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


/**
 * диалог для выбора даты цели
 */
public class DatePickerDialogFragment extends DialogFragment {
    private static final String KEY_PURPOSE_DATE = "PURPOSE_DATE";

    private OnDateSetListener onDateSetListener;

    /**
     * интерфейс для прослушивания события установки даты
     */
    interface OnDateSetListener {
        void onDateSet(Date date);
    }


    /**
     * настройка отображения диалога
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() instanceof OnDateSetListener) {
            onDateSetListener = (OnDateSetListener) getActivity();
        }
        Date date = (Date) getArguments().getSerializable(KEY_PURPOSE_DATE);
        if (date == null) date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker, null, false);
        final DatePicker datePicker = v.findViewById(R.id.date_picker);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                null);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_pick)
                .setView(v)
                .setPositiveButton(R.string.dialog_ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();
                        onDateSetListener.onDateSet(new GregorianCalendar(year, month, day).getTime());
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
     * создает и возвращает новый экземпляр фрагмента
     *
     * @param date уже выбранная дата, может быть null, если не была установлена
     * @return экземпляр
     */
    public static DatePickerDialogFragment newInstance(@Nullable Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PURPOSE_DATE, date);
        DatePickerDialogFragment datePickerFragment = new DatePickerDialogFragment();
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }
}
