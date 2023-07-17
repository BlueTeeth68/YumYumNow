package com.example.yumyumnow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yumyumnow.dao.UserDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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

    UserDAO userDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        userDAO = new UserDAO(getActivity());

        bindElements(view);

        return view;
    }


    ImageButton backBtn;
    EditText oldPass, newPass, confirmPass;
    Button save;

    private void bindElements(View view) {
        backBtn = view.findViewById(R.id.changePasswordBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new ProfileFragment());
            }
        });

        oldPass = view.findViewById(R.id.oldPasswordTxt);
        newPass = view.findViewById(R.id.newPasswordTxt);
        confirmPass = view.findViewById(R.id.reNewPasswordTxt);

        save = view.findViewById(R.id.confirmChangePasswordBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String oldPassword = oldPass.getText().toString().trim();
        String newPassword = newPass.getText().toString().trim();
        String confirmPassword = confirmPass.getText().toString().trim();

        // Validate inputs
        if (oldPassword.isEmpty() ||
                newPassword.isEmpty() ||
                confirmPassword.isEmpty()) {
            Toast.makeText(getActivity(), "All fields cannot be empty!", Toast.LENGTH_SHORT).show();
        }else if(newPassword.length() < 4){
            Toast.makeText(getActivity(), "New Password length cannot be less than 4 characters!", Toast.LENGTH_SHORT).show();
        }
        else if(!newPassword.equals(confirmPassword)){
            Toast.makeText(getActivity(), "New Password and Confirm New Password must be the same!", Toast.LENGTH_SHORT).show();
        }
        else{
            boolean result = userDAO.changePassword(MainActivity.user.getId(), oldPassword, newPassword);
            if(!result){
                Toast.makeText(getActivity(), "Error trying to change password. Please re-check input information!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Change password successfully!!", Toast.LENGTH_SHORT).show();
                switchFragment(new ProfileFragment());
            }
        }
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }
}