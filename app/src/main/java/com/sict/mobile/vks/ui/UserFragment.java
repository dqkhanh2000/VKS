package com.sict.mobile.vks.ui;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.sict.mobile.vks.MainActivity;
import com.sict.mobile.vks.R;

public class UserFragment extends Fragment {
    private static final String PREFS_NAME = "user_prefs";
    private GoogleSignInClient mGoogleSignInClient;
    private Context context;
    int RC_SIGN_IN = 1;
    private SharedPreferences prefs;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        int layout;
        if(prefs.getBoolean("hasLogin", false))
            layout = R.layout.fragment_user_logined;
        else
            layout = R.layout.fragment_user_guest;

        root = inflater.inflate(layout, container, false);
        if(layout == R.layout.fragment_user_guest){
            SignInButton signInButton = root.findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
        }

        if(layout == R.layout.fragment_user_logined){
            Button btnSignOut = root.findViewById(R.id.btn_signOut);
            TextView txtName = root.findViewById(R.id.txt_fragmentUserLogin_userName);
            txtName.setText(prefs.getString("name", null));
            btnSignOut.setOnClickListener(v -> {
                signOut();
            });
        }


        return root;
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnSuccessListener(aVoid -> {
            prefs.edit().putBoolean("hasLogin", false).apply();
            Navigation.findNavController(root).navigate(R.id.navigation_user);
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private boolean saveLoginValue(GoogleSignInAccount account){
        if(account == null) return false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", account.getEmail());
        editor.putString("name", account.getDisplayName());
        editor.putBoolean("hasLogin",true);  // set the prefs true after success login
        editor.apply();
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if(saveLoginValue(account)){
                Toast.makeText(context, "Xin chào "+account.getDisplayName(), Toast.LENGTH_SHORT).show();
                Navigation.findNavController(root).navigate(R.id.navigation_user);
            }
        } catch (ApiException e) {
            Toast.makeText(context, "Chỉ sử dụng email của khoa", Toast.LENGTH_SHORT).show();
        }
    }
}
