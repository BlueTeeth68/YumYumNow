package com.example.yumyumnow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yumyumnow.dao.UserDAO;
import com.example.yumyumnow.dto.UserDTO;
import com.example.yumyumnow.dto.UserLoginDTO;

public class LoginActivity extends AppCompatActivity {

    EditText usernameTxt, passwordTxt;
    Button loginBtn;

    UserDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDao = new UserDAO(this);

        // Bind elements
        usernameTxt = findViewById(R.id.usernameTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.loginBtn);

        // Add listeners
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        String username = usernameTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();

        UserLoginDTO userLogin = new UserLoginDTO();
        userLogin.setUsername(username);
        userLogin.setPassword(password);

        UserDTO result = userDao.login(userLogin);

        if(result == null){
            Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            intent.putExtra("username", username);

            startActivity(intent);
        }

    }
}