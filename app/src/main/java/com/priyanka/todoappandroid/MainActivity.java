package com.priyanka.todoappandroid;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.priyanka.todoappandroid.Adapter.ToDoAdaptor;
import com.priyanka.todoappandroid.model.toDoModel;
import com.priyanka.todoappandroid.utils.databaseHelper;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner{

    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private databaseHelper helper;
    private List<toDoModel> mList;
    private ToDoAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        mRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        helper = new databaseHelper(MainActivity.this);
        mList = new ArrayList<>();
        adaptor = new ToDoAdaptor(helper,MainActivity.this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adaptor);

        mList = helper.getAllTask();
        Collections.reverse(mList);
        adaptor.setTasks(mList);

        fab.setOnClickListener(view -> {
            AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adaptor));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = helper.getAllTask();
        Collections.reverse(mList);
        adaptor.setTasks(mList);
        adaptor.notifyDataSetChanged();
    }
}