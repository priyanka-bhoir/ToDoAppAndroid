package com.priyanka.todoappandroid.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.priyanka.todoappandroid.AddNewTask;
import com.priyanka.todoappandroid.MainActivity;
import com.priyanka.todoappandroid.R;
import com.priyanka.todoappandroid.model.toDoModel;
import com.priyanka.todoappandroid.utils.databaseHelper;

import java.util.List;

public class ToDoAdaptor extends RecyclerView.Adapter<ToDoAdaptor.MyViewHolder> {
    
    private List<toDoModel> mList;
    private MainActivity activity;
    private databaseHelper myDb;

    public ToDoAdaptor(databaseHelper myDb, MainActivity activity){
        this.activity = activity;
        this.myDb = myDb;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox mcCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mcCheckBox = itemView.findViewById(R.id.checkbox);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tast_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final toDoModel item = mList.get(position);
        holder.mcCheckBox.setText(item.getTask());
        holder.mcCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mcCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    myDb.updateStatus(item.getId() , 1);
                }else{
                    myDb.updateStatus(item.getId(),0);
                }
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<toDoModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        toDoModel item = mList.get(position);
        myDb.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        toDoModel item  = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
}
