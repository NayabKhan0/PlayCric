package com.example.playcric;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.playcric.DataBase.DataBaseClient;
import com.example.playcric.DataBase.DataTable;
import com.example.playcric.Model.GroupTeamModel;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

public class RenameDialog extends Dialog {

    DataTable dataTable;
    Context context;
    Button saveButton, cancelButton;
    EditText editText;

    public RenameDialog(@NonNull Context context, DataTable dataTable) {
        super(context);
        this.context = context;
        this.dataTable = dataTable;
    }

    public RenameDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rename_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        init();
        onClick();
    }

    private void init() {
        editText = findViewById(R.id.editText);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
    }

    private void onClick() {
        editText.setText(dataTable.getTeamName());
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = editText.getText().toString();
                if (value.equals("")) {
                    Toast.makeText(context, "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else {
                    DataTable data = dataTable;
                    String TeamName = data.getTeamName();
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            DataBaseClient.getInstance(context)
                                    .getAppDatabase().dataDao().updateTeamName(TeamName,value);
                        }
                    });
                    Toast.makeText(context, "Save Successfully", Toast.LENGTH_SHORT).show();
                    cancel();
                }
            }
        });
    }
}
