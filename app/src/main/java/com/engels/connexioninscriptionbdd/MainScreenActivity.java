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
import android.widget.Toast;
import com.engels.connexioninscriptionbdd.data.MyDatabase;
import com.engels.connexioninscriptionbdd.data.User;



public class MainScreenActivity extends AppCompatActivity {

    private Button btnConnecter,btnShowUsers;
    private Button btnInscription;
    private EditText edtLogin;
    private EditText edtPwd;
    private String loginSaved,passwordSaved;
    private User user;
    private MyDatabase maBDD;
    private Context ctx;
    private SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        user = new User();
        btnConnecter = (Button) findViewById(R.id.btnValider);
        btnInscription = (Button) findViewById(R.id.btnInscription);
        btnShowUsers = (Button) findViewById(R.id.btnShowUsers);
        edtLogin = (EditText) findViewById(R.id.editLogin); //login est en fait le nom
        edtPwd = (EditText) findViewById(R.id.editPwd);
        ctx= getApplicationContext();
        settings = getSharedPreferences("userData", Context.MODE_PRIVATE);
        onClickButtons();
        }

    public void onClickButtons(){
        btnConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields() == true) {
                    maBDD = new MyDatabase(getApplicationContext());
                    maBDD.open();
                    User userReq = maBDD.getUsers(edtLogin.getText().toString());
                    maBDD.close();

                    if (userReq != null){
                        if (checkCo(userReq.getNom().toString(), userReq.getPassword().toString()) == true) {
                            Toast.makeText(getApplicationContext(), "OK validé", Toast.LENGTH_SHORT).show();//loginSaved = user.getNom();
                            Intent it = new Intent(MainScreenActivity.this, ProfilActivity.class);
                            startActivity(it);
                            SharedPreferences settings = getSharedPreferences("userData", Context.MODE_PRIVATE);
                            // ajout /enregistrement des donnees dans preferences
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("login",userReq.getNom());
                            editor.putString("prenom",userReq.getPrenom());
                            editor.apply();
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Pb connexion ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Compte n'existe pas ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Pb la ", Toast.LENGTH_SHORT);
                    Log.d("Requete Select", "On affiche quedalle");
                }
            }
        });
        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainScreenActivity.this, InscriptionActivity.class);
                startActivity(it);
                finish();
            }
        });
        btnShowUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainScreenActivity.this,ContenuBaseActivity.class);
                startActivity(it);
            }
        });

    }



    public boolean checkFields(){
        if (edtLogin.getText().toString().length() == 0){
            edtLogin.setError("Entrer votre login");
            return false;
        }

        if (edtPwd.getText().toString().length() == 0){
            edtPwd.setError("Entrer votre password");
            return false;
        }
        return true;
    }

    public boolean checkCo(String loginSaved, String passwordSaved){

        if (!edtLogin.getText().toString().equals(loginSaved)) {
            edtLogin.setError("Login incorrect ou inexistant");
            return false;
        }

        if (!edtPwd.getText().toString().equals(passwordSaved)) {
            edtPwd.setError("Password incorrect");
            return false;
        }

        if (edtLogin.getText().toString().equals(loginSaved) && edtPwd.getText().toString().equals(passwordSaved)){
            Toast.makeText(getApplicationContext(),"Bienvenue dans votre profil",Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
