package com.example.yumyumnow;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleMap mMap;
    private Marker storeMarker;
    private LatLng storeLocation;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView welcomeTxt;
    SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeTxt = view.findViewById(R.id.welcomeTxt);
        welcomeTxt.setText("Welcome, " + MainActivity.user.getUsername());

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        if (marker.equals(storeMarker)) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(storeLocation, 17);
            mMap.animateCamera(cameraUpdate);
            return true; // Return true to indicate that the click event has been consumed
        }
        return false;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        storeLocation = new LatLng(38.8976763, -77.0365298);

        storeMarker = mMap.addMarker(new MarkerOptions().position(storeLocation).title("Yum yum now Store").visible(true).draggable(true));
        storeMarker.showInfoWindow();
        storeMarker.setTag(0);

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 17));

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }
}