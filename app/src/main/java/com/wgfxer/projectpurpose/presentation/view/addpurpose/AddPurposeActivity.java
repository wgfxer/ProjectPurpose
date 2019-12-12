package com.wgfxer.projectpurpose.presentation.view.addpurpose;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.helper.Utils;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class AddPurposeActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialogFragment.OnDateSetListener {
    private static final String KEY_PURPOSE_ID = "PURPOSE_ID";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_READ_EXTERNAL_REQUEST = 2;

    private EditText purposeTitleEditText;
    private Button selectDateButton;
    private Button pickImageButton;
    private LinearLayout purposeLL;
    private Purpose purpose;
    private int id;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purpose);

        viewModel = ViewModelProviders.of(this, new MainViewModelFactory(this))
                .get(MainViewModel.class);

        purposeTitleEditText = findViewById(R.id.list_title_text_view);
        selectDateButton = findViewById(R.id.choose_date);
        Button doneButton = findViewById(R.id.button_done);
        purposeLL = findViewById(R.id.purpose_ll);
        pickImageButton = findViewById(R.id.take_photo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectDateButton.setOnClickListener(this);
        pickImageButton.setOnClickListener(this);
        doneButton.setOnClickListener(this);

        id = getIntent().getIntExtra(KEY_PURPOSE_ID, -1);
        if (id != -1) {
            LiveData<Purpose> purposeLiveData = viewModel.getPurposeById(id);
            purposeLiveData.observe(this, new Observer<Purpose>() {
                @Override
                public void onChanged(@Nullable Purpose purpose) {
                    AddPurposeActivity.this.purpose = purpose;
                    if (purpose != null) {
                        updateUI();
                    }
                }
            });
            getSupportActionBar().setTitle(R.string.edit_purpose);
        } else {
            purpose = new Purpose();
        }


    }

    private void updateUI() {
        purposeTitleEditText.setText(purpose.getTitle());
        purposeTitleEditText.setSelection(purposeTitleEditText.getText().length());
        selectDateButton.setText(Utils.getStringFromDate(purpose.getDate()));
        if (purpose.getTheme().getImagePath() != null) {
            pickImageButton.setText(getString(R.string.change_image_text));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_done:
                if (!isFieldsEmpty()) {
                    purpose.setTitle(purposeTitleEditText.getText().toString());
                    if (id != -1) {
                        viewModel.updatePurpose(purpose);
                        onBackPressed();
                    } else {
                        viewModel.insertPurpose(purpose);
                        onBackPressed();
                    }
                }
                break;
            case R.id.choose_date:
                DatePickerDialogFragment datePickerFragment = DatePickerDialogFragment.newInstance(purpose.getDate());
                datePickerFragment.show(getSupportFragmentManager(), null);
                break;
            case R.id.take_photo:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        startIntentPickImage();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_EXTERNAL_REQUEST);
                    }
                } else {
                    startIntentPickImage();
                }
                break;
            default:
                break;
        }
    }

    void startIntentPickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                purpose.getTheme().setImagePath(cursor.getString(columnIndex));
                pickImageButton.setText(getString(R.string.change_image_text));
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_READ_EXTERNAL_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startIntentPickImage();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFieldsEmpty() {
        if (purposeTitleEditText.getText().toString().trim().isEmpty()) {
            purposeLL.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shaking));
            Toast.makeText(this, getString(R.string.toast_didnt_enter_goal_name), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (purpose.getDate() == null) {
            selectDateButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shaking));
            Toast.makeText(this, getString(R.string.toast_didnt_enter_date), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public static Intent editPurpose(Context context, int id) {
        Intent intent = new Intent(context, AddPurposeActivity.class);
        intent.putExtra(KEY_PURPOSE_ID, id);
        return intent;
    }

    public static Intent newPurpose(Context context) {
        return new Intent(context, AddPurposeActivity.class);
    }

    @Override
    public void onDateSet(Date date) {
        purpose.setDate(date);
        selectDateButton.setText(Utils.getStringFromDate(date));
    }
}
