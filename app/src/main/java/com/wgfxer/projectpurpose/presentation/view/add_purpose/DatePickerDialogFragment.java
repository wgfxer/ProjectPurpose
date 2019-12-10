package com.wgfxer.projectpurpose.presentation.view.add_purpose;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.wgfxer.projectpurpose.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class DatePickerDialogFragment extends DialogFragment {
    private static final String KEY_PURPOSE_DATE = "PURPOSE_DATE";

    private OnDateSetListener onDateSetListener;

    interface OnDateSetListener {
        void onDateSet(Date date);
    }

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
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();
                        onDateSetListener.onDateSet(new GregorianCalendar(year, month, day).getTime());
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

    public static DatePickerDialogFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PURPOSE_DATE, date);
        DatePickerDialogFragment datePickerFragment = new DatePickerDialogFragment();
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }
}
