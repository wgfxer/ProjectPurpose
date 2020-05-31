package com.wgfxer.projectpurpose.presentation.view.purposeinfo.reportsfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.Report;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * Диалог для создания и изменения отчета
 */
public class EditReportDialogFragment extends DialogFragment {

    private static final String KEY_REPORT_DATE = "KEY_REPORT_DATE";
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";

    private OnEditReportListener onEditReportListener;

    private EditText reportTitleEditText;
    private EditText reportDescriptionEditText;
    private EditText reportDidGoodEditText;
    private EditText reportCouldBetterEditText;
    private Report report;


    private void setOnEditReportListener(OnEditReportListener onEditReportListener) {
        this.onEditReportListener = onEditReportListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_report_dialog, null, false);

        findViews(view);

        fillViews();

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(getTitle())
                .setPositiveButton(R.string.dialog_ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(report == null){
                            report = new Report();
                            report.setPurposeId(getArguments().getInt(KEY_PURPOSE_ID));
                            report.setReportDate(getArguments().getLong(KEY_REPORT_DATE));
                        }
                        report.setTitleReport(reportTitleEditText.getText().toString());
                        report.setDescriptionReport(reportDescriptionEditText.getText().toString());
                        report.setWhatDidGood(reportDidGoodEditText.getText().toString());
                        report.setWhatCouldBetter(reportCouldBetterEditText.getText().toString());
                        onEditReportListener.onEditReport(report);
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
     * Находит все элементы во view
     *
     * @param view в которой нужно найти элементы
     */
    private void findViews(View view) {
        reportTitleEditText = view.findViewById(R.id.edit_text_title);
        reportDescriptionEditText = view.findViewById(R.id.edit_text_description);
        reportDidGoodEditText = view.findViewById(R.id.edit_text_did_good);
        reportCouldBetterEditText = view.findViewById(R.id.edit_text_could_better);
    }

    /**
     * Заполняет edittext'ы если редактирование отчета
     */
    private void fillViews() {
        if(report != null){
            reportTitleEditText.setText(report.getTitleReport());
            reportDescriptionEditText.setText(report.getDescriptionReport());
            reportDidGoodEditText.setText(report.getWhatDidGood());
            reportCouldBetterEditText.setText(report.getWhatCouldBetter());
        }
    }

    /**
     * Возвращает строку с заголовком диалога
     *
     * @return строку с title
     */
    private String getTitle() {
        String title;
        if (report == null) {
            title = getString(R.string.create_report_dialog_title);
        } else {
            title = getString(R.string.edit_report_dialog_title);
        }
        return title;
    }

    /**
     * Получить экземпляр диалога для добавления отчета
     */
    static EditReportDialogFragment getAddInstance(long reportDate, int purposeId, OnEditReportListener onEditReportListener) {

        Bundle args = new Bundle();

        args.putLong(KEY_REPORT_DATE, reportDate);
        args.putInt(KEY_PURPOSE_ID,purposeId);

        EditReportDialogFragment fragment = new EditReportDialogFragment();
        fragment.setArguments(args);
        fragment.setOnEditReportListener(onEditReportListener);
        return fragment;
    }

    /**
     * Получить экземпляр диалога для изменения отчета
     */
    static EditReportDialogFragment getEditInstance(Report report, OnEditReportListener onEditReportListener) {

        Bundle args = new Bundle();

        EditReportDialogFragment fragment = new EditReportDialogFragment();
        fragment.setArguments(args);
        fragment.setOnEditReportListener(onEditReportListener);
        fragment.report = report;

        return fragment;
    }

    /**
     * Интерфейс для слушания события изменения отчета или добавления нового отчета
     */
    interface OnEditReportListener {
        /**
         * вызывается после нажатия на кнопку готово в диалоге
         */
        void onEditReport(Report report);
    }
}
