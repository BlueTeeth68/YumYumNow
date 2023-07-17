package com.example.yumyumnow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yumyumnow.dao.UserDAO;
import com.example.yumyumnow.dto.UserDTO;
import com.example.yumyumnow.dto.UserRegisterDTO;

public class RegisterActivity extends AppCompatActivity {

    ImageButton registerBackBtn;
    Button confirmRegisterBtn;
    EditText username, fullName, password, rePassword;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDAO = new UserDAO(this);

        registerBackBtn = findViewById(R.id.registerBackBtn);
        confirmRegisterBtn = findViewById(R.id.confirmRegisterBtn);
        username = findViewById(R.id.usernameRegisterTxt);
        fullName = findViewById(R.id.fullNameRegisterTxt);
        password = findViewById(R.id.passwordRegisterTxt);
        rePassword = findViewById(R.id.confirmPasswordRegisterTxt);

        registerBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });

    }

    private void registerAccount() {
        String usernameTxt = username.getText().toString().trim();
        String passwordTxt = password.getText().toString().trim();
        String rePasswordTxt = rePassword.getText().toString().trim();
        String fullNameTxt = fullName.getText().toString().trim();

        // Validate inputs
        if(usernameTxt.isEmpty() ||
        passwordTxt.isEmpty() ||
        rePasswordTxt.isEmpty() ||
        fullNameTxt.isEmpty()){
            Toast.makeText(this, "All fields must be input!", Toast.LENGTH_SHORT).show();
        }
        else if(passwordTxt.length() < 4){
            Toast.makeText(this, "Password length cannot be less than 4 characters!", Toast.LENGTH_SHORT).show();
        }
        else if(!passwordTxt.equals(rePasswordTxt)){
            Toast.makeText(this, "Password and Confirm Password must be the same!", Toast.LENGTH_SHORT).show();
        }
        else{
            UserRegisterDTO user = new UserRegisterDTO();
            user.setUsername(usernameTxt);
            user.setFullName(fullNameTxt);
            user.setPassword(passwordTxt);

            UserDTO result = userDAO.register(user);
            if(result == null){
                Toast.makeText(this, "Error trying to register account. Please re-check input information!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Register account successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}