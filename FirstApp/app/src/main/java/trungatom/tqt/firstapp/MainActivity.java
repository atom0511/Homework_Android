package trungatom.tqt.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    int requestCodeWriteStorage = 1001;
    private GridView gridView;
    private ImageAdapter imageAdapter;
    RelativeLayout relativeLayout;
    ImageUtils imageUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fbAdd = findViewById(R.id.floatingButton);
        relativeLayout= findViewById(R.id.rl_my_layout);
        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                startActivity(intent);
            }
        });
        setupPermission();

        gridView = findViewById(R.id.gv_images);
        imageAdapter = new ImageAdapter();
        gridView.setAdapter(imageAdapter);

        relativeLayout = findViewById(R.id.rl_my_layout);
        if (imageAdapter.getCount() != 0) {
            relativeLayout.setVisibility(View.INVISIBLE);
        }
        imageUtils = new ImageUtils();
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("tag", "onItemLongClick: ed");
                deleteImage(MainActivity.this, position);
                return false;
            }
        });
    }



    private void deleteImage(Context context, final int position){
        new AlertDialog.Builder(context)
                .setTitle("Xóa ảnh")
                .setMessage("Bạn chắc chắn chưa?")
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imageUtils.getListImage().get(position).delete();
                        imageAdapter.notifyDataSetChanged();
                        if (imageAdapter.getCount() == 0) {
                            relativeLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (imageAdapter.getCount() != 0) {
            relativeLayout.setVisibility(View.INVISIBLE);
        }
        imageAdapter.notifyDataSetChanged();
    }

    private void setupPermission() {

        String[] permissions = new String[1];
        permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissions, requestCodeWriteStorage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == requestCodeWriteStorage) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Bạn chắc chưa?").setMessage("Đ cấp quyền thì ăn lồn nhé. Cấp quyền đi")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setupPermission();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                        .show();
            }
        }
    }
}
