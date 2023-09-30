package com.koipsool_new.ui.chemist;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.koipsool_new.OtpVerificationActivity;
import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityChemistSignUpBinding;
import com.koipsool_new.kapsoolModels.LoginModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.model.ChemistSignupModel;
import com.koipsool_new.ui.medical.HomeMedicalResActivity;
import com.koipsool_new.ui.pharma.HomeActivity;
import com.koipsool_new.ui.pharma.PharmaLoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChemistSignUpActivity extends AppCompatActivity {

    private ActivityChemistSignUpBinding binding;
    private Activity activity;
    private String fcmId = "";
    private ProgressDialog pd;
    private Session session;

    private GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN = 200;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChemistSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        pd = new ProgressDialog(activity);
        session = new Session(activity);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this, gso);


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            String fcm_id3 = instanceIdResult.getToken();
            fcmId = fcm_id3;
            Log.e("refresh_tokentoken", fcm_id3);
        });

        binding.googleLoginBtn.setOnClickListener(view -> {
            pd.show();
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        });

        binding.signUpButton.setOnClickListener(view -> {

            if (binding.phoneNumber.getText().toString().equalsIgnoreCase("")) {
                binding.phoneNumber.setError("Enter Phone number..!");
                binding.phoneNumber.requestFocus();
            } else {
                chemistSignup();
            }
        });

        binding.signinBtn.setOnClickListener(view -> startActivity(new Intent(ChemistSignUpActivity.this, PharmaLoginActivity.class)));


    }

    private void chemistSignup() {
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.chemistSignUp(
                binding.phoneNumber.getText().toString(),
                fcmId
        ).enqueue(new Callback<ChemistSignupModel>() {
            @Override
            public void onResponse(@NonNull Call<ChemistSignupModel> call, @NonNull Response<ChemistSignupModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setType(response.body().getData().getType());
                            session.setOtp(String.valueOf(response.body().getOtp()));
                            session.setUser_id(response.body().getData().getId());
                            Toast.makeText(activity, String.valueOf(response.body().getOtp()), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ChemistSignUpActivity.this, OtpVerificationActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            );

                            finish();
                        } else {
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<ChemistSignupModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult();
                if (account != null) {
                    Log.e("OnREsponse", "Account Id = " + account.getId());
                }
                AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(authCredential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithCredential:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (user != null) {
                                        Log.e("onResponse", "Username :-" + user.getDisplayName());
                                    }
                                    Log.e("onResponse", "Email :-" + user.getEmail());
                                    ;
                                    Log.e("onResponse", "mobile :-" + user.getPhoneNumber());
                                    Log.e("onResponse", "id :-" + user.getUid());

                                    String name, email, mobile, image;
//
                                    name = user.getDisplayName();
                                    email = user.getEmail();
                                    mobile = user.getPhoneNumber();

                                    googlesignIn(name, email, mobile, user.getUid());

                                } else {
                                    Toast.makeText(activity, "Task Not Successful", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                    // If sign in fails, display a message to the user.
                                    Log.e("TAG", "signInWithCredential:failure", task.getException());
                                    //  updateUI(null);
                                }
                            }
                        });
            } catch (Exception e) {
                pd.dismiss();
                /// Toast.makeText(LoginSecondActivity.this, "On error"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("OnError", "Account Id = Not get" + e.getLocalizedMessage());
            }


        }


    }

    private void googlesignIn(String name, String email, String mobile, String id) {
        ApiInterfaceKapsool apiInterface = RetrofitClientKapsool.getClient(activity);

        apiInterface.googleLogin(
                email,
                name,
                fcmId,
                id,
                session.getType()
        ).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                try {

                    if (response.code() == 200)
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                session.setEmail(response.body().getData().getEmail());
                                session.setUser_id(response.body().getData().getId());

                                if (session.getType().equalsIgnoreCase("pharma")) {
                                    Intent intent = new Intent(activity, HomeActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    finish();
                                } else if (session.getType().equalsIgnoreCase("medical")) {
                                    Intent intent = new Intent(activity, HomeMedicalResActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    finish();
                                } else if (session.getType().equalsIgnoreCase("chemist")) {
                                    Intent intent = new Intent(activity, ChemistActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    finish();
                                }

                                Toast.makeText(activity, "Account Created..", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            } else {
                                pd.dismiss();
                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                            }
                        } else
                            Log.e("TAG", "onResponse() called with: call =  response = [" + response + "]");
                    else
                        Log.e("TAG", "onResponse() called with: call =  response = [" + response.code() + "]");
                } catch (Exception e) {
                    Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                    e.printStackTrace();
                    pd.dismiss();
                }

                pd.dismiss();
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }




}