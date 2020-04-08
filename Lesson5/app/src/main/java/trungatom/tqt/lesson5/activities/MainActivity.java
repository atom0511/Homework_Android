package trungatom.tqt.lesson5.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import trungatom.tqt.lesson5.R;
import trungatom.tqt.lesson5.adapters.ItemAdapter;
import trungatom.tqt.lesson5.databases.DatabaseUtils;
import trungatom.tqt.lesson5.models.ItemModel;

public class MainActivity extends AppCompatActivity {
    ItemAdapter adapter;
    ListView listView;
    public static BookListActivity bookListActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookListActivity = new BookListActivity();
        listView = findViewById(R.id.lv_book);
        adapter = new ItemAdapter(MainActivity.this, R.layout.item_list_book, DatabaseUtils.getInstance(this).getListTopic());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });
    }
}
