package com.example.dell.kontento;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter {

    Activity context;
    int resource;
    ArrayList<TaskModel> list;
    SQLiteHelper myDb;

    public TaskAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<TaskModel> objects) {
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
        final View row = inflater.inflate(R.layout.row,null);

        final RelativeLayout box = (RelativeLayout)row.findViewById(R.id.box);
        final CheckBox checkId = (CheckBox)row.findViewById(R.id.checking);
        final TextView taskname = (TextView)row.findViewById(R.id.text1);
        final TextView state = (TextView)row.findViewById(R.id.status);
        final ImageView del = (ImageView)row.findViewById(R.id.del);

        final TaskModel tm = list.get(position);

        state.setText(tm.getStatus());
        checkId.setText(Integer.toString(tm.getId()));
        taskname.setText(tm.getTask());

        switch (tm.getType()){
            case "1":
                checkId.setBackgroundTintList(context.getResources().getColorStateList(R.color.penk,context.getTheme()));
                box.setBackgroundResource(R.drawable.fblue);
                break;
            case "2":
                box.setBackgroundResource(R.drawable.fpink);
                break;
            case "3":
                box.setBackgroundResource(R.drawable.fwhite);
                break;
            case "4":
                box.setBackgroundResource(R.drawable.fpenk);
                break;
            case "5":
                box.setBackgroundResource(R.drawable.fgreen);
                break;
        }

        if (tm.getStatus().equals("1")) {
            checkId.setChecked(!checkId.isChecked());
            taskname.setPaintFlags(taskname.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }

        checkId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean checked = checkId.isChecked();
                if(checked){
                    taskname.setPaintFlags(taskname.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    Toast.makeText(context, "Good Job! :D", Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "Status"+tm.getId(), Toast.LENGTH_LONG).show();
//                    String id = Integer.toString(checkId.getId());
////
//                    //Updatng the db
//                    String stat = "1";
//                    myDb.updateTask(id,stat);
//                    notifyDataSetChanged();
//                    Toast.makeText(context, "Statussss"+tm.getStatus(), Toast.LENGTH_LONG).show();
//                    if(valid == true){
//                        Toast.makeText(context, "Update is successfull", Toast.LENGTH_LONG).show();
//                    }else{
//                        Toast.makeText(context, "R.I.P", Toast.LENGTH_LONG).show();
//                    }
                }else {
                    taskname.setPaintFlags(0);
                    Toast.makeText(context, "Havent finish the task?", Toast.LENGTH_LONG).show();
                }
            }

        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Status"+tm.status, Toast.LENGTH_LONG).show();
                String id = Integer.toString(tm.getId());
                list.remove(getItem(position));
                notifyDataSetChanged();
                Toast.makeText(context, "Keep up the good work ^ - ^", Toast.LENGTH_LONG).show();
                myDb.deleteTask(id);
            }
        });


        return row;
    }
}
