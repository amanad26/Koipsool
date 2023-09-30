package com.koipsool_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.kapsoolModels.OtpVerifyModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.ui.chemist.ChemistActivity;
import com.koipsool_new.ui.medical.HomeMedicalResActivity;
import com.koipsool_new.ui.pharma.HomeActivity;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtpVerificationActivity extends AppCompatActivity {
    private static final String TAG = "OtpVerificationActivity";
    private TextView AG_OTPVERIFYE;
    private TextView txt_resend;
    private String timeLeftFormatted;
    private EditText edt_one, edt_two, edt_three, edt_four;
    private EditText[] editTexts;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private Session session;
    private ProgressDialog pd;
    private TextView your_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        session = new Session(OtpVerificationActivity.this);
        AG_OTPVERIFYE = findViewById(R.id.AG_OTPVERIFYE);
        txt_resend = findViewById(R.id.txt_resend);
        your_otp = findViewById(R.id.your_otp);


        your_otp.setText("Your Otp : " + session.getOtp());
        Toast.makeText(OtpVerificationActivity.this, session.getOtp(), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onCreate() called with: session.getOtp() = [" + session.getOtp() + "]");

        pd = new ProgressDialog(OtpVerificationActivity.this);
        AG_OTPVERIFYE.setOnClickListener(v -> {
            String one = edt_one.getText().toString();
            String two = edt_two.getText().toString();
            String three = edt_three.getText().toString();
            String four = edt_four.getText().toString();
            final String otp = one + two + three + four;
            if (valid()) {
                verifyOtp(otp);
            }
        });

        edt_one = findViewById(R.id.edt_one);
        edt_two = findViewById(R.id.edt_two);
        edt_three = findViewById(R.id.edt_three);
        edt_four = findViewById(R.id.edt_four);

        editTexts = new EditText[]{edt_one, edt_two, edt_three, edt_four};

        edt_one.addTextChangedListener(new PinTextWatcher(0));
        edt_two.addTextChangedListener(new PinTextWatcher(1));
        edt_three.addTextChangedListener(new PinTextWatcher(2));
        edt_four.addTextChangedListener(new PinTextWatcher(3));

        edt_one.setOnKeyListener(new PinOnKeyListener(0));
        edt_two.setOnKeyListener(new PinOnKeyListener(1));
        edt_three.setOnKeyListener(new PinOnKeyListener(2));
        edt_four.setOnKeyListener(new PinOnKeyListener(3));

        edt_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0)
                    hideKeyboard();
            }
        });
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        new CountDownTimer(45000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                mTimeLeftInMillis = millisUntilFinished;
                //  txt_resend.setText(String.valueOf(counter));
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                txt_resend.setClickable(true);
                txt_resend.setText(R.string.resend_otp);
            }


        }.start();


        txt_resend.setOnClickListener(view -> resendOtp());


    }

    private void resendOtp() {

        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(OtpVerificationActivity.this);
        apiInterfaceKapsool.resendOtp(session.getUserId()).enqueue(new Callback<OtpVerifyModel>() {
            @Override
            public void onResponse(@NonNull Call<OtpVerifyModel> call, @NonNull Response<OtpVerifyModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(OtpVerificationActivity.this, response.body().getData().getOtp(), Toast.LENGTH_SHORT).show();
                            your_otp.setText("Your Otp : " + response.body().getData().getOtp());
                        } else {
                            Toast.makeText(OtpVerificationActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<OtpVerifyModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void verifyOtp(String otp) {
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(OtpVerificationActivity.this);

        apiInterfaceKapsool.pharmaVerifyOtp(
                session.getUserId(),
                otp
        ).enqueue(new Callback<OtpVerifyModel>() {
            @Override
            public void onResponse(@NonNull Call<OtpVerifyModel> call, @NonNull Response<OtpVerifyModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                Toast.makeText(OtpVerificationActivity.this, "Verification Success..!", Toast.LENGTH_SHORT).show();

                                session.setEmail(response.body().getData().getEmail());
                                session.setMobile(response.body().getData().getMobile());
                                session.setUserName(response.body().getData().getName());
                                session.setOtp("");

                                if (!response.body().getData().getImage().equalsIgnoreCase(""))
                                    session.setImage(response.body().getData().getImage());


                                if (session.getType().equalsIgnoreCase("pharma")) {
                                    Intent intent = new Intent(OtpVerificationActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    finish();
                                } else if (session.getType().equalsIgnoreCase("medical")) {
                                    Intent intent = new Intent(OtpVerificationActivity.this, HomeMedicalResActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    finish();
                                } else if (session.getType().equalsIgnoreCase("chemist")) {
                                    Intent intent = new Intent(OtpVerificationActivity.this, ChemistActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    finish();
                                }
                                pd.dismiss();
                            } else {
                                pd.dismiss();
                                Toast.makeText(OtpVerificationActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                } catch (Exception e) {
                    pd.dismiss();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<OtpVerifyModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }

    /*
    private void otpVerification(String otp) {

        requestQueue = Volley.newRequestQueue(this);
        mstringRequest = new StringRequest(Request.Method.POST, Base_Url.verify_otp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("==========RESPONZE===========" + response);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);

                    String result = obj.getString("result");
                    String msg = obj.getString("msg");
                    if (result.equalsIgnoreCase("true")) {
                        JSONObject DATA = obj.getJSONObject("data");
                        String otp = DATA.getString("otp");
                        String id = DATA.getString("id");
                        session.setUserId(id);

                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OtpVerificationActivity.this, SelectionActivity.class);

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                System.out.println(" object    ===" + obj);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("=========================" + error);
                Log.i(TAG, "Error :" + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("otp", otp);
                params.put("user_id", id);
                System.out.println("============Sent Data=============" + params);

                return params;
            }
        };

        requestQueue.add(mstringRequest);


    }*/

    private boolean valid() {
        boolean flag = true;
        if (edt_one.getText().toString().isEmpty()) {
            edt_one.setError("Fill Otp");
            flag = false;
        } else if (edt_two.getText().length() <= 0) {
            edt_two.setError("Fill Otp");
            flag = false;
        } else if (edt_three.getText().length() <= 0) {
            edt_three.setError("Fill Otp");
            flag = false;
        } else if (edt_four.getText().length() <= 0) {
            edt_four.setError("Fill Otp");
            flag = false;

        }
        return flag;
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        txt_resend.setClickable(false);
        txt_resend.setText(timeLeftFormatted);
    }

    public class PinTextWatcher implements TextWatcher {

        private final int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();

        }


        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);
            editTexts[currentIndex].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sotpbackground));
            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }


    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private final int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }


    }

}
//sotpbackground