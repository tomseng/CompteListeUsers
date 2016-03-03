package com.engels.connexioninscriptionbdd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmSignUpActivity extends AppCompatActivity {
    private Button btnAccueil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmsignup);
        btnAccueil = (Button) findViewById(R.id.btnAccueil);
        btnAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ConfirmSignUpActivity.this,MainScreenActivity.class);
                startActivity(it);
            }
        });
    }
}
