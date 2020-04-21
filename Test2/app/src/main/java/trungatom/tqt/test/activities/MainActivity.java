package trungatom.tqt.test.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trungatom.tqt.test.R;
import trungatom.tqt.test.adapters.TodoAdapter;
import trungatom.tqt.test.databases.DatabaseUtils;
import trungatom.tqt.test.models.TodoModel;

public class MainActivity extends AppCompatActivity {
    TodoAdapter adapter;
    ListView listView;
    FloatingActionButton btn;
    List<TodoModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv_todo);
        btn = findViewById(R.id.floatingButton);
        arrayList = DatabaseUtils.getInstance(this).getListTopic();
        adapter = new TodoAdapter(MainActivity.this, R.layout.item_list_todo,
                arrayList);
        listView.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAction.class);
                intent.putExtra("length", listView.getCount());
                startActivity(intent);
            }
        });
        deleteListItem();
        editItemList();
    }

    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
    public void deleteListItem(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("fix", "onClick: " + position);
                        DatabaseUtils.getInstance(MainActivity.this).deleteData(position);
                        arrayList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
                return false;
            }
        });
    }

    public void editItemList(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditAction.class);
                String[] data = {arrayList.get(position).getmShowTime(),
                                 arrayList.get(position).getmTitle(),
                                 arrayList.get(position).getmTag(),
                                 arrayList.get(position).getmContent()
                                 };
                intent.putExtra("dataOfItem", data);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}
