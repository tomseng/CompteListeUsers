package com.engels.connexioninscriptionbdd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.engels.connexioninscriptionbdd.data.MyDatabase;
import com.engels.connexioninscriptionbdd.data.User;

import java.util.ArrayList;
import java.util.List;

public class ContenuBaseActivity extends AppCompatActivity {

    private ListView listView;
    public static ArrayList<String> usersArray = new ArrayList<String>();
    private MyDatabase maBDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenu_base);

        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, usersArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    protected void onStart(){
        super.onStart();
        maBDD = new MyDatabase(getApplicationContext());
        maBDD.open();
        usersArray.clear();
        List<User> users = maBDD.getAllUsers();
        maBDD.close();
    }
}
