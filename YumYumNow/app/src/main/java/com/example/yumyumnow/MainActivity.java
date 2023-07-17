package com.example.yumyumnow;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yumyumnow.dao.CartDAO;
import com.example.yumyumnow.databinding.ActivityMainBinding;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.dto.UserDTO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static UserDTO user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserDataFromIntent();

        notifyItemInCart();

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

    private void notifyItemInCart() {
        CartDAO cartDAO = new CartDAO(this);
        List<CartDTO> cart = cartDAO.getCartOfUser(user.getId());
        if (cart != null && !cart.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("Notify Cart", "My Cart Notification", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Notify Cart");
            builder.setContentTitle("Cart Notification");
            String message = "You have " + cart.size() + " items in your shopping cart!";
            builder.setContentText(message);
            builder.setSmallIcon(R.drawable.cart_icon);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            managerCompat.notify(1, builder.build());
        }
    }
}