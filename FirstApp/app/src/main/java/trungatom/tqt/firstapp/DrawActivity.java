package trungatom.tqt.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivColorPicker, ivSave;
    private RadioGroup rgPenSize;
    private int currentColror;
    private int currentPensize;
    private DrawingView drawingView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        Create();
        progressDialog = new ProgressDialog(DrawActivity.this);
        ivColorPicker.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        ivColorPicker.setColorFilter(currentColror);
        currentPensize = 10;

        drawingView.setCurrentColor(currentColror);
        drawingView.setCurrentSize(currentPensize);
        rgPenSize.check(R.id.rb_pen_size_normal);
        rgPenSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_pen_size_thin:
                        currentPensize = 5;
                        break;
                    case  R.id.rb_pen_size_normal:
                        currentPensize = 10;
                        break;
                    case  R.id.rb_pen_size_strong:
                        currentPensize = 15;
                        break;
                }
                drawingView.setCurrentSize(currentPensize);
                Toast.makeText(DrawActivity.this, "Selected: " + currentPensize, Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void Create(){
        ivColorPicker = findViewById(R.id.iv_color_picker);
        rgPenSize = findViewById(R.id.rg_pen_size);
        ivSave = findViewById(R.id.iv_save);
        currentColror = ContextCompat.getColor(DrawActivity.this, R.color.colorAccent);
        drawingView = findViewById(R.id.drawing_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_color_picker:
                showColorPickerDialog();
                break;
            case R.id.iv_save:
                saveImage();
                break;
        }
    }

    private void saveImage(){
        //show progress dialog

        progressDialog.setMessage("Saving...");
        progressDialog.show();
        //get bitmap
        drawingView.setDrawingCacheEnabled(true);
        drawingView.buildDrawingCache();
        Bitmap bitmap = drawingView.getDrawingCache();

        //save image
        ImageUtils.saveImage(bitmap, DrawActivity.this);

        //close activity
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    private void showColorPickerDialog() {
        ColorPickerDialogBuilder
                .with(DrawActivity.this)
                .setTitle("Choose color")
                .initialColor(currentColror)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(DrawActivity.this, "Selected", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        currentColror = selectedColor;
                        drawingView.setCurrentColor(currentColror);
                        ivColorPicker.setColorFilter(currentColror);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}
