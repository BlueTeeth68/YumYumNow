package com.example.yumyumnow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yumyumnow.dto.UserDTO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    ImageView avatarImg;
    EditText idTxt, usernameTxt, fullnameTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Bind elements
        bindUserData(view);

        // return view
        return view;
    }

    private void bindUserData(View view) {

        avatarImg = view.findViewById(R.id.avatarImg);
        idTxt = view.findViewById(R.id.idTxt);
        usernameTxt = view.findViewById(R.id.usernameTxt);
        fullnameTxt = view.findViewById(R.id.fullnameTxt);

        idTxt.setFocusable(false);
        usernameTxt.setFocusable(false);

        if (MainActivity.user == null) {
            Toast.makeText(getActivity(), "No User Data!", Toast.LENGTH_SHORT).show();
        } else {
            avatarImg.setImageResource(MainActivity.user.getAvatar());
            idTxt.setText(String.valueOf(MainActivity.user.getId()));
            usernameTxt.setText(MainActivity.user.getUsername());
            fullnameTxt.setText(MainActivity.user.getFullName());
        }
    }
}