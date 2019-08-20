package com.irayhan.sqlitedatabasetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    protected EditText etName;
    protected EditText etAge;
    protected EditText etInterest;
    protected TextView tvMsg;
    protected ListView lvData;


    protected DatabaseHelper database;
    protected List<String> nameList;
    protected ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etInterest = findViewById(R.id.etInterest);
        tvMsg = findViewById(R.id.tvMsg);
        lvData = findViewById(R.id.lvData);



        database = new DatabaseHelper(MainActivity.this);




    }


    @Override
    protected void onStart() {
        super.onStart();

        nameList = database.getAllData();
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, nameList);
        lvData.setAdapter(adapter);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String details = database.getAll(lvData.getItemAtPosition(position).toString());
                tvMsg.setText(details);

            }
        });

    }






    public void DeleteData(View view) {

        String name = etName.getText().toString();

        if(database.deleteData(name)){
            adapter.remove(name);
            tvMsg.setText("Delete Success");
        }
        else{
            tvMsg.setText("No Data Found");
        }



    }





    public void SaveData(View view) {

        String name = etName.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        String interest = etInterest.getText().toString();


        if(database.hasName(name)){
            tvMsg.setText("Duplicate Name");
        }
        else{
            if(database.insertData(name, age, interest)){
                tvMsg.setText("Save Success");
                adapter.add(name);
            }
        }




    }





    public void UpdateData(View view) {

        String name = etName.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        String interest = etInterest.getText().toString();

        if(database.updateData(name, age, interest)){
            tvMsg.setText("Update Success");
        }

    }

}
