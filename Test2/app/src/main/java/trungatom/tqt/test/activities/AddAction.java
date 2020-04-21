package trungatom.tqt.test.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trungatom.tqt.test.R;
import trungatom.tqt.test.databases.DatabaseUtils;
import trungatom.tqt.test.models.TodoModel;

public class AddAction extends AppCompatActivity {

    @BindView(R.id.et_date_edit)
    EditText etDate;
    @BindView(R.id.et_title_edit)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_save_edit)
    Button btSave;
    @BindView(R.id.sn_tag_edit)
    Spinner snTag;
    @BindView(R.id.et_time_edit)
    EditText etTime;


    TodoModel todoModel;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);
        ButterKnife.bind(this);
        setSpinner();

    }

    public void setDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddAction.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
                etDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void setTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddAction.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(0, 0, 0, hourOfDay, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                etTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    public void setSpinner() {
        ArrayList<String> arrTodo = new ArrayList<String>();
        arrTodo.add("Family");
        arrTodo.add("Work");
        arrTodo.add("Home");
        arrTodo.add("Birth");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrTodo);
        snTag.setAdapter(arrayAdapter);
        snTag.setSelection(0);
    }

    @OnClick({R.id.bt_save_edit, R.id.et_date_edit, R.id.et_time_edit, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_save_edit:
                int length = getIntent().getIntExtra("length", 0);
                Log.d("fix", "onViewClicked: " + length);
                DatabaseUtils.
                        getInstance(AddAction.this).
                        addData(etTitle.getText().toString(),
                                snTag.getSelectedItem().toString(),
                                etContent.getText().toString(),
                                etDate.getText().toString(),
                                length
                        );
                Intent intent = new Intent(AddAction.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.et_date_edit:
                setDate();
                break;
            case R.id.et_time_edit:
                setTime();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

}
