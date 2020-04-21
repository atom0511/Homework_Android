package trungatom.tqt.test.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trungatom.tqt.test.R;
import trungatom.tqt.test.databases.DatabaseUtils;

public class EditAction extends AppCompatActivity {

    @BindView(R.id.iv_back_edit)
    ImageView ivBackEdit;
    @BindView(R.id.sn_tag_edit)
    Spinner snTagEdit;
    @BindView(R.id.et_date_edit)
    EditText etDateEdit;
    @BindView(R.id.et_title_edit)
    EditText etTitleEdit;
    @BindView(R.id.et_content_edit)
    EditText etContentEdit;
    @BindView(R.id.bt_save_edit)
    Button btSaveEdit;
    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_action);
        ButterKnife.bind(this);
        setSpinner();
        String[] data = getIntent().getStringArrayExtra("dataOfItem");
        etDateEdit.setText(data[0]);
        etTitleEdit.setText(data[1]);
        etContentEdit.setText(data[3] + ".");

    }

    @OnClick({R.id.iv_back_edit, R.id.bt_save_edit, R.id.et_date_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_edit:
                onBackPressed();
                break;
            case R.id.bt_save_edit:
                editData();
                Intent intent = new Intent(EditAction.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.et_date_edit:
                setDate();
                break;
        }
    }

    public void setSpinner() {
        tag = getIntent().getStringArrayExtra("dataOfItem")[2];
        ArrayList<String> arrTodo = new ArrayList<String>();
        arrTodo.add("Family");
        arrTodo.add("Work");
        arrTodo.add("Home");
        arrTodo.add("Birth");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrTodo);
        snTagEdit.setAdapter(arrayAdapter);
        switch (tag) {
            case "Family":
                snTagEdit.setSelection(0);
                break;
            case "Work":
                snTagEdit.setSelection(1);
                break;
            case "Home":
                snTagEdit.setSelection(2);
                break;
            case "Birth":
                snTagEdit.setSelection(3);
                break;
        }
    }

    public void setDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditAction.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
                etDateEdit.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void editData() {
        int position = getIntent().getIntExtra("position", 0);
        DatabaseUtils.getInstance(EditAction.this)
                .updateData(etTitleEdit.getText().toString(),
                        snTagEdit.getSelectedItem().toString(),
                        etContentEdit.getText().toString(),
                        etDateEdit.getText().toString(),
                        position
                );
    }
}
