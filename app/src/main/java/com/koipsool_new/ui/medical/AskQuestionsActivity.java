package com.koipsool_new.ui.medical;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityAskQuestionsBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.model.MedicalInternshipModel;
import com.koipsool_new.model.MedicalJobModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskQuestionsActivity extends AppCompatActivity {

    ActivityAskQuestionsBinding binding;
    private boolean isOpen = false;
    private String type = "";
    private Session session;
    private MedicalJobModel.JobData jobData;
    private MedicalInternshipModel.MedicalInternshipData internshipData;
    private AskQuestionsActivity activity;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAskQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");

            Log.e(TAG, "onCreate() called with: type = [" + type + "]");

            if (type.equalsIgnoreCase("job")) {
                jobData = (MedicalJobModel.JobData) getIntent().getSerializableExtra("data");
                setJobData();
            } else {
                internshipData = (MedicalInternshipModel.MedicalInternshipData) getIntent().getSerializableExtra("data");
                setInternshipData();
            }
        }

        if(!session.getMyCoverLetter().equalsIgnoreCase(""))
              binding.copyCoverLetter.setVisibility(View.VISIBLE);

        binding.highlightTv.setText(Html.fromHtml("<pre>\n" + "<b>Highlight your strengths </b>-   mention any experience (internships/job experience or extra-curricular activities), accomplishments,skills that are relevant to this role </pre>"));

        binding.doNotCopy.setText(Html.fromHtml("<pre>\n" + "<b>Do not copy answers </b>- from internet </pre>"));

        binding.showEnthusiasm.setText(Html.fromHtml("<pre>\n" + "<b>Show Enthusiasm  </b>- mention what excites you about this role and the company . you can go through their website/social media to understand what interest you.</pre>"));

        binding.showHintLinear.setOnClickListener(view -> {
            if (isOpen) {
                binding.hintLiner.setVisibility(View.GONE);
                isOpen = false;
                binding.arrowImage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            } else {
                binding.hintLiner.setVisibility(View.VISIBLE);
                isOpen = true;
                binding.arrowImage.setImageResource(R.drawable.ic_arrow_up);
            }

        });

        binding.copyCoverLetter.setOnClickListener(view -> binding.answer1.setText(session.getMyCoverLetter()));

    }

    private void setInternshipData() {
        binding.titleTv.setText(internshipData.getInternshipTitle() + " at " + internshipData.getCompanyName());

        if (!internshipData.getQuestions_1().equalsIgnoreCase("")) {
            binding.question1Tv.setText(internshipData.getQuestions_1());
        }

        if (!internshipData.getQuestions_2().equalsIgnoreCase("")) {
            binding.question2Tv.setText(internshipData.getQuestions_2());
        }

        if (!internshipData.getQuestions_3().equalsIgnoreCase("")) {
            binding.questions3.setVisibility(View.VISIBLE);
            binding.question3Tv.setText(internshipData.getQuestions_3());
        }

        if (!internshipData.getQuestions_4().equalsIgnoreCase("")) {
            binding.questions4.setVisibility(View.VISIBLE);
            binding.question4Tv.setText(internshipData.getQuestions_4());
        }

        if (!internshipData.getQuestions_5().equalsIgnoreCase("")) {
            binding.questions5.setVisibility(View.VISIBLE);
            binding.question5Tv.setText(internshipData.getQuestions_5());
        }


        binding.submitApplication.setOnClickListener(view -> {
            if (binding.answer1.getText().toString().equalsIgnoreCase("")) {
                binding.answer1.setError("Enter Your Answer");
                binding.answer1.requestFocus();
            } else if (binding.answer2.getText().toString().equalsIgnoreCase("")) {
                binding.answer2.setError("Enter Your Answer");
                binding.answer2.requestFocus();
            } else {
                applyInternship("internship", internshipData.getId(), internshipData.getCompanyId(), binding.answer1.getText().toString(), binding.answer2.getText().toString(), binding.answer3.getText().toString(), binding.answer4.getText().toString(), binding.answer5.getText().toString());
            }
        });

    }

    private void setJobData() {
        binding.titleTv.setText(jobData.getName() + " at " + jobData.getCompanyName());


        if (!jobData.getQuestions_1().equalsIgnoreCase("")) {
            binding.question1Tv.setText(jobData.getQuestions_1());
        }

        if (!jobData.getQuestions_2().equalsIgnoreCase("")) {
            binding.question2Tv.setText(jobData.getQuestions_2());
        }

        if (!jobData.getQuestions_3().equalsIgnoreCase("")) {
            binding.questions3.setVisibility(View.VISIBLE);
            binding.question3Tv.setText(jobData.getQuestions_3());
        }

        if (!jobData.getQuestions_4().equalsIgnoreCase("")) {
            binding.questions4.setVisibility(View.VISIBLE);
            binding.question4Tv.setText(jobData.getQuestions_4());
        }

        if (!jobData.getQuestions_5().equalsIgnoreCase("")) {
            binding.questions5.setVisibility(View.VISIBLE);
            binding.question5Tv.setText(jobData.getQuestions_5());
        }

        binding.submitApplication.setOnClickListener(view -> {
            if (binding.answer1.getText().toString().equalsIgnoreCase("")) {
                binding.answer1.setError("Enter Your Answer");
                binding.answer1.requestFocus();
            } else if (binding.answer2.getText().toString().equalsIgnoreCase("")) {
                binding.answer2.setError("Enter Your Answer");
                binding.answer2.requestFocus();
            } else {
                applyInternship("job", jobData.getId(), jobData.getCompanyId(), binding.answer1.getText().toString(), binding.answer2.getText().toString(), binding.answer3.getText().toString(), binding.answer4.getText().toString(), binding.answer5.getText().toString());
            }
        });

    }

    private void applyInternship(String applyType, String id, String companyId, String ans1, String ans2, String ans3, String ans4, String ans5) {

        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.applyJobInternship(session.getUserId(), id, companyId, applyType, ans1, ans2, ans3, ans4, ans5).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200) if (response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        Toast.makeText(activity, "Apply Successfully", Toast.LENGTH_SHORT).show();
                        session.setMyCoverLetter(binding.answer1.getText().toString());
                        startActivity(new Intent(activity,HomeMedicalResActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    } else {
                        Toast.makeText(activity, "Failed...!", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }


}