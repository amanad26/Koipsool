package com.koipsool_new.ui.medical;

import static com.koipsool_new.Session.Session.medical;

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
import com.koipsool_new.databinding.ActivityMedicalSignupBinding;
import com.koipsool_new.kapsoolModels.LoginModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.SignupModel;
import com.koipsool_new.ui.chemist.ChemistActivity;
import com.koipsool_new.ui.pharma.HomeActivity;
import com.koipsool_new.ui.pharma.PharmaLoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalSignupActivity extends AppCompatActivity {

    private ActivityMedicalSignupBinding binding;
    private Session session;
    private MedicalSignupActivity activity;
    private ProgressDialog pd;
    private String fcmId = "";

    private GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN = 200;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicalSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        binding.signinBtn.setOnClickListener(view -> startActivity(new Intent(activity, PharmaLoginActivity.class)));

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
                binding.phoneNumber.setError("Enter Mobile Number..!");
                binding.phoneNumber.requestFocus();
            } else {
                login();
            }


        });

    }

    private void login() {

        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.medicalSignUp(
                binding.phoneNumber.getText().toString(),
                fcmId
        ).enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(@NonNull Call<SignupModel> call, @NonNull Response<SignupModel> response) {


                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            session.setType(medical);
                            session.setUser_id(response.body().getId());
                            session.setOtp(String.valueOf(response.body().getOtp()));

                            Intent intent = new Intent(activity, OtpVerificationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            pd.dismiss();
                        }else if(response.body().getMsg().equalsIgnoreCase("mobile already exist!")){
                            login2(binding.phoneNumber.getText().toString());
                        }else {
                            pd.dismiss();
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }


            }

            @Override
            public void onFailure(@NonNull Call<SignupModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void login2(String mobile) {
        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);

        apiInterfaceKapsool.pharmaLogin(
                mobile,
                fcmId
        ).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                try {
                    if (response.code() == 200)
                        if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                            session.setUser_id(response.body().getData().getId());
                            session.setOtp(String.valueOf(response.body().getData().getOtp()));
                            session.setType(response.body().getData().getType());
                            Toast.makeText(activity, response.body().getData().getOtp(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity, OtpVerificationActivity.class);
                            startActivity(intent);
                            pd.dismiss();
                        } else {
                            pd.dismiss();
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                } catch (Exception e) {
                    pd.dismiss();
                    e.getLocalizedMessage();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
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