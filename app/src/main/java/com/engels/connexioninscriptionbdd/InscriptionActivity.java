package com.engels.connexioninscriptionbdd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.engels.connexioninscriptionbdd.data.MyDatabase;

public class InscriptionActivity extends AppCompatActivity {


    private Button btnEnregistrer;
    private MyDatabase maBDD;
    private EditText editNom, editPrenom,editPwd;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        editNom =  (EditText) findViewById(R.id.editNom);
        editPrenom = (EditText) findViewById(R.id.editPrenom);
        editPwd = (EditText) findViewById(R.id.editPwd);
        btnEnregistrer = (Button) findViewById(R.id.btnEnregistrer);
        setButtonsListeners();


    }

    private void setButtonsListeners(){
        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()==true) {
                    maBDD = new MyDatabase(getApplicationContext());
                    maBDD.open();
                    Long result = maBDD.addUser(editNom.getText().toString(), editPrenom.getText().toString(), editPwd.getText().toString());
                    Log.d("Insert de l'user ", result.toString());

                    maBDD.close();
                    Toast.makeText(getApplicationContext(),"OK validé",Toast.LENGTH_SHORT).show();
                    if (!result.toString().equals(-1)){
                    Intent it = new Intent(InscriptionActivity.this,ConfirmSignUpActivity.class);
                        startActivity(it);
                        finish();
                    }

                }
                else(Toast.makeText(getApplicationContext(),"Champs",Toast.LENGTH_SHORT)).show();
            }
        });
    }
    public boolean checkForm(){
        if (editNom.getText().toString().length() == 0){
            editNom.setError("Entrer votre nom");
            return false;
        }

        if (editPrenom.getText().toString().length() == 0){
            editPrenom.setError("Entrer votre prenom");
            return false;
        }

        if (editPwd.getText().toString().length() == 0){
            editPwd.setError("Entrer votre password");
            return false;
        }

        return true;
    }

}
