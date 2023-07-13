package com.example.yumyumnow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yumyumnow.databinding.ActivityMainBinding;
import com.example.yumyumnow.dto.UserDTO;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static UserDTO user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserDataFromIntent();

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.products) {
                replaceFragment(new ProductFragment());
            } else if (item.getItemId() == R.id.cart) {
                replaceFragment(new CartFragment());
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void getUserDataFromIntent() {
        UserDTO user = null;
        if (getIntent().hasExtra("id")
                && getIntent().hasExtra("username")
                && getIntent().hasExtra("fullname")
                && getIntent().hasExtra("avatar")) {
            user = new UserDTO();
            user.setId(getIntent().getIntExtra("id", 0));
            user.setUsername(getIntent().getStringExtra("username"));
            user.setFullName(getIntent().getStringExtra("fullname"));
            user.setAvatar(getIntent().getIntExtra("avatar", 0));
        }

        this.user = user;

    }
}