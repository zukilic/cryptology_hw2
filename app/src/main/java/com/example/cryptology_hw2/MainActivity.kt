package com.example.cryptology_hw2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var user_name : EditText
    lateinit var password : EditText
    lateinit var signin : Button
    lateinit var login : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_name = findViewById(R.id.user_name)
        password = findViewById(R.id.password)
        signin = findViewById(R.id.signin)
        login = findViewById(R.id.login)

        signin.setOnClickListener{
            val db = DBHelper(this, null)
            if(true == db.checkUser(user_name.text.toString(), password.text.toString().hashCode().toString())){
                Toast.makeText(this, "Yeni Üye Tanımlanamıyor", Toast.LENGTH_LONG).show()
                user_name.text.clear()
                password.text.clear()
            }else{
                db.tanimlama(user_name.text.toString(), password.text.toString().hashCode().toString())
                Toast.makeText(this, "Yeni Üye Tanımlandı", Toast.LENGTH_LONG).show()
                user_name.text.clear()
                password.text.clear()
            }
        }

        login.setOnClickListener{
            val db = DBHelper(this, null)
            if(true == db.checkUser(user_name.text.toString(), password.text.toString().hashCode().toString())){
                Toast.makeText(this, "Giriş Yapabilirsiniz.", Toast.LENGTH_LONG).show()
                user_name.text.clear()
                password.text.clear()
            }else{
                Toast.makeText(this, "Giriş Yapamazsınız.", Toast.LENGTH_LONG).show()
                user_name.text.clear()
                password.text.clear()
            }
        }

    }
}