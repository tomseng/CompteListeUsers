package com.engels.connexioninscriptionbdd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.engels.connexioninscriptionbdd.data.MyDatabase;

import org.w3c.dom.Text;

public class ProfilActivity extends AppCompatActivity {

    private TextView phrase;
    private SharedPreferences settings;
    private String loginSaved,prenomSaved;
    private EditText edtNomAlter;
    private EditText edtPrenomAlter;
    private EditText edtPwdAlter;
    private Button btnUpdateData;
    private MyDatabase maBDD;
    private Button btnDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        phrase = (TextView) findViewById(R.id.phrase);
        settings = getSharedPreferences("userData", Context.MODE_PRIVATE);
        loginSaved = settings.getString("login", null);
        prenomSaved = settings.getString("prenom", null);
        edtNomAlter = (EditText) findViewById(R.id.edtNomAlter);
        edtPwdAlter = (EditText) findViewById(R.id.edtPwdAlter);
        edtPrenomAlter = (EditText) findViewById(R.id.edtPrenom);
        btnUpdateData = (Button) findViewById(R.id.btnUpdateData);
        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);
        phrase.setText("Bonjour " +  loginSaved + " vous êtes bien connecté ") ;
        edtNomAlter.setText(loginSaved);
        edtPrenomAlter.setText(prenomSaved);

        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maBDD = new MyDatabase(getApplicationContext());
                maBDD.open();
                Long result = maBDD.updateUser(edtNomAlter.getText().toString(), edtPrenomAlter.getText().toString(), edtPwdAlter.getText().toString());
                Log.d("Update de l'user ", result.toString());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("login", edtNomAlter.getText().toString());
                editor.putString("prenom", edtPrenomAlter.getText().toString());
                editor.putString("password", edtPrenomAlter.getText().toString());

                editor.apply();
                finish();

                maBDD.close();
                Toast.makeText(getApplicationContext(), "OK validé", Toast.LENGTH_SHORT).show();
                if (!result.toString().equals(-1)) {
                    Intent it = new Intent(ProfilActivity.this, MainScreenActivity.class);
                    startActivity(it);

                    Toast.makeText(ProfilActivity.this, "Ok ça a l'air ok ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfilActivity.this, "pb requete", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                finish();
                Intent it = new Intent(ProfilActivity.this,MainScreenActivity.class);
                startActivity(it);
                finish();
            }
        });




    }
}
