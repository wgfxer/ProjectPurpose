package com.wgfxer.projectpurpose.presentation.view.purposeinfo.reportsfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.domain.Report;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * Диалог для создания и изменения отчета
 */
public class EditReportDialog extends DialogFragment {

    private static final String KEY_REPORT_DATE = "KEY_REPORT_DATE";
    private static final String KEY_REPORT_TITLE = "KEY_REPORT_TITLE";
    private static final String KEY_REPORT_DESCRIPTION = "KEY_REPORT_DESCRIPTION";
    private static final String KEY_REPORT_DID_GOOD = "KEY_REPORT_DID_GOOD";
    private static final String KEY_REPORT_COULD_BETTER = "KEY_REPORT_COULD_BETTER";

    private static final String KEY_MODE = "KEY_MODE";
    private static final int KEY_MODE_EDIT = 1;
    private static final int KEY_MODE_CREATE = 2;

    private OnEditReportListener onEditReportListener;

    private EditText reportTitleEditText;
    private EditText reportDescriptionEditText;
    private EditText reportDidGoodEditText;
    private EditText reportCouldBetterEditText;
    private long reportDate;


    /**
     * Интерфейс для слушания события изменения отчета или добавления нового отчета
     */
    interface OnEditReportListener {
        /**
         * вызывается после нажатия на кнопку готово в диалоге
         */
        void onEditReport(Report report);
    }


    void setOnEditReportListener(OnEditReportListener onEditReportListener) {
        this.onEditReportListener = onEditReportListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_report_dialog, null, false);

        findViews(view);

        fillViews();

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(getTitle())
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Report report = new Report(
                                reportDate,
                                reportTitleEditText.getText().toString(),
                                reportDescriptionEditText.getText().toString(),
                                reportDidGoodEditText.getText().toString(),
                                reportCouldBetterEditText.getText().toString()
                        );
                        onEditReportListener.onEditReport(report);
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

    /**
     * заполняет edittext'ы если редактирование отчета
     */
    private void fillViews() {
        reportDate = getArguments().getLong(KEY_REPORT_DATE);
        reportTitleEditText.setText(getArguments().getString(KEY_REPORT_TITLE, null));
        reportDescriptionEditText.setText(getArguments().getString(KEY_REPORT_DESCRIPTION, null));
        reportDidGoodEditText.setText(getArguments().getString(KEY_REPORT_DID_GOOD, null));
        reportCouldBetterEditText.setText(getArguments().getString(KEY_REPORT_COULD_BETTER, null));
    }

    /**
     * находит все элементы во view
     * @param view в которой нужно найти элементы
     */
    private void findViews(View view) {
        reportTitleEditText = view.findViewById(R.id.edit_text_title);
        reportDescriptionEditText = view.findViewById(R.id.edit_text_description);
        reportDidGoodEditText = view.findViewById(R.id.edit_text_did_good);
        reportCouldBetterEditText = view.findViewById(R.id.edit_text_could_better);
    }

    /**
     * по текущему моду возвращает строку с заголовком диалога
     * @return строку с title
     */
    private String getTitle() {
        int mode = getArguments().getInt(KEY_MODE);
        String title = null;
        if (mode == KEY_MODE_CREATE) {
            title = getString(R.string.create_report_dialog_title);
        } else if (mode == KEY_MODE_EDIT) {
            title = getString(R.string.edit_report_dialog_title);
        }
        return title;
    }

    static EditReportDialog getAddInstance(long reportDate) {

        Bundle args = new Bundle();

        args.putLong(KEY_REPORT_DATE, reportDate);
        args.putInt(KEY_MODE, KEY_MODE_CREATE);

        EditReportDialog fragment = new EditReportDialog();
        fragment.setArguments(args);
        return fragment;
    }

    static EditReportDialog getEditInstance(Report report) {

        Bundle args = new Bundle();

        args.putLong(KEY_REPORT_DATE, report.getDateReport());
        args.putString(KEY_REPORT_TITLE, report.getTitleReport());
        args.putString(KEY_REPORT_DESCRIPTION, report.getDescriptionReport());
        args.putString(KEY_REPORT_DID_GOOD, report.getWhatDidGood());
        args.putString(KEY_REPORT_COULD_BETTER, report.getWhatCouldBetter());

        args.putInt(KEY_MODE, KEY_MODE_EDIT);

        EditReportDialog fragment = new EditReportDialog();
        fragment.setArguments(args);
        return fragment;
    }
}
