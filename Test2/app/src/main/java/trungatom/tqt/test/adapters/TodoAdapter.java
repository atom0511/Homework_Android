package trungatom.tqt.test.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

import trungatom.tqt.test.R;
import trungatom.tqt.test.models.TodoModel;

public class TodoAdapter extends ArrayAdapter<TodoModel> {
    List<TodoModel> arrTodo;
    private int resource;

    public TodoAdapter(@NonNull Context context, int resource, @NonNull List<TodoModel> objects) {
        super(context, resource, objects);
        this.arrTodo = objects;
        this.resource = resource;
    }

    @SuppressLint("CheckResult")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_todo, parent, false);
            viewHolder.tvDate = convertView.findViewById(R.id.tv_date);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.tvTag = convertView.findViewById(R.id.tv_tag);
            viewHolder.tvContent = convertView.findViewById(R.id.tv_content);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            viewHolder.tvDate.setText(arrTodo.get(position).getmShowTime());
            viewHolder.tvTitle.setText(arrTodo.get(position).getmTitle());
            viewHolder.tvTag.setText(arrTodo.get(position).getmTag());
            viewHolder.tvContent.setText(arrTodo.get(position).getmContent() + ".");
            setColorTag(viewHolder.tvTag, viewHolder.tvDate, parent.getContext());
        } catch (Throwable t) {
            Log.d("fix", "getView: " + t);
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tvDate;
        TextView tvTitle;
        TextView tvTag;
        TextView tvContent;
    }

    GradientDrawable drawableDate;
    GradientDrawable drawableTag;
    public void setColorTag(TextView tvTag, TextView tvDate, Context context) {
        switch (tvTag.getText().toString()) {
            case "Family":
                drawableTag = (GradientDrawable) tvTag.getBackground().mutate();
                drawableTag.setStroke(5,ContextCompat.getColor(context, R.color.familyTagColor));

                drawableDate = (GradientDrawable) tvDate.getBackground().mutate();
                drawableDate.setColor(ContextCompat.getColor(context, R.color.familyTagColor));
                break;
            case "Work":
                drawableTag = (GradientDrawable) tvTag.getBackground().mutate();
                drawableTag.setStroke(5,ContextCompat.getColor(context, R.color.workTagColor));

                drawableDate = (GradientDrawable) tvDate.getBackground().mutate();
                drawableDate.setColor(ContextCompat.getColor(context, R.color.workTagColor));
                break;
            case "Home":
                drawableTag = (GradientDrawable) tvTag.getBackground().mutate();
                drawableTag.setStroke(5,ContextCompat.getColor(context, R.color.homeTagColor));

                drawableDate = (GradientDrawable) tvDate.getBackground().mutate();
                drawableDate.setColor(ContextCompat.getColor(context, R.color.homeTagColor));
                break;
            case "Birth":
                drawableTag = (GradientDrawable) tvTag.getBackground().mutate();
                drawableTag.setStroke(5,ContextCompat.getColor(context, R.color.birthTagColor));

                drawableDate = (GradientDrawable) tvDate.getBackground().mutate();
                drawableDate.setColor(ContextCompat.getColor(context, R.color.birthTagColor));
                break;
        }
    }
}
