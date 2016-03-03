package com.engels.connexioninscriptionbdd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.engels.connexioninscriptionbdd.ContenuBaseActivity;
import com.engels.connexioninscriptionbdd.MainScreenActivity;
import com.engels.connexioninscriptionbdd.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egldu on 02/03/2016.
 */
public class MyDatabase {
   // private int BDD_VERSION = 2;
    private String BDD_NAME = "Bdd_Users";
    private SQLiteDatabase bdd;
    private ContenuBaseActivity CBA = new ContenuBaseActivity();

    private DataBase mybdd;
    public MyDatabase(Context context){
        mybdd = new DataBase(context,BDD_NAME,null, R.integer.BDD_VERSION);
    }
    public void open(){
        // on recupere 1 acces RW sur la db
        this.bdd = mybdd.getWritableDatabase();
    }
    public void close(){
        this.bdd.close();
    }
    public SQLiteDatabase getBDD(){
        return this.bdd;
    }
    // ci dessous le code de manip de la base données

    public long addUser(String nom,String prenom,String password){

        ContentValues cv_users = new ContentValues();
        cv_users.put("nom",nom);
        cv_users.put("prenom",prenom);
        cv_users.put("password", password);
        return  bdd.insert("USERS",null,cv_users);
    }

    public User getUsers(String nom){
        User resultat = null;
        Cursor cur = bdd.rawQuery("SELECT nom, prenom, password FROM USERS WHERE nom = ?",new String[]{nom});
        while (cur.moveToNext()) {
            resultat = new User();
            resultat.setNom(cur.getString(cur.getColumnIndex("nom")));
            resultat.setPrenom(cur.getString(cur.getColumnIndex("prenom")));
            resultat.setPassword(cur.getString(cur.getColumnIndex("password")));


        }

        cur.close();
        return resultat;
    }
    public long updateUser(String nom,String prenom,String password){
        ContentValues cv_users = new ContentValues();
//        cv_users.put("nom",nom);
        cv_users.put("prenom",prenom);
        cv_users.put("password", password);
        return bdd.update("USERS", cv_users, "nom = " + "'" + nom + "'", null);
    }
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM USERS";

        Cursor cursor = bdd.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setNom(cursor.getString(cursor.getColumnIndex("nom")));
                user.setPrenom(cursor.getString(cursor.getColumnIndex("prenom")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));


                String displayInfos = cursor.getString(cursor.getColumnIndex("nom")) +"\n"+ cursor.getString(cursor.getColumnIndex("prenom"));
                ContenuBaseActivity.usersArray.add(displayInfos);
                // Adding contact to list
               // userList.add(user);


            } while (cursor.moveToNext());
        }

        // return contact list
        return userList;
    }

}
