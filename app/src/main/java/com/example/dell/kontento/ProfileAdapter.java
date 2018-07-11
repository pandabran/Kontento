package com.example.dell.kontento;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileAdapter extends ArrayAdapter {

    Activity context;
    int resource;
    ArrayList<TaskModel> list;
    SQLiteHelper myDb;

    public ProfileAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<TaskModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        myDb = new SQLiteHelper(context);

        LayoutInflater inflater = context.getLayoutInflater();
        final View row = inflater.inflate(R.layout.row, null);

        final RelativeLayout box = (RelativeLayout) row.findViewById(R.id.box);
        final CheckBox checkId = (CheckBox) row.findViewById(R.id.checking);
        final TextView taskname = (TextView) row.findViewById(R.id.text1);
        final TextView state = (TextView) row.findViewById(R.id.status);
        final ImageView del = (ImageView) row.findViewById(R.id.del);


        final TaskModel tm = list.get(position);


        state.setText(tm.getStatus());
        checkId.setText(Integer.toString(tm.getId()));
        taskname.setText(tm.getTask());








        return row;
    }
}
