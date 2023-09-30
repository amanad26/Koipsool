package com.koipsool_new.ui.medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityMyApplicationBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.model.MyAppliedJobModel;
import com.koipsool_new.ui.pharma.MyJobsAndInternshipsActivity;
import com.koipsool_new.ui.resume.MyResumeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApplicationActivity extends AppCompatActivity {

    private MyAppliedJobModel.MyAppliedData data;
    private Session session;
    private MyApplicationActivity activity;
    private ActivityMyApplicationBinding binding;
    private  String type =  "0";
    private ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);
        data = (MyAppliedJobModel.MyAppliedData) getIntent().getSerializableExtra("data");

        type = getIntent().getStringExtra("type");



        if (type != null) {
            if(type.equalsIgnoreCase("1")){
                binding.btnReject.setVisibility(View.GONE);
                binding.btnAccept.setText("Accepted");
                binding.btnAccept.setClickable(false);
            }else if(type.equalsIgnoreCase("2")){
                binding.btnReject.setText("Rejected");
                binding.btnReject.setClickable(false);
                binding.btnAccept.setVisibility(View.GONE);
            }
        }


        if (data.getType().equalsIgnoreCase("job"))
            binding.jobTitle.setText(data.getPostJobName());
        else binding.jobTitle.setText(data.getPostInternshipName());


        if(session.getType().equalsIgnoreCase("medical")){
            binding.companyLinear.setVisibility(View.GONE);
            binding.jobCompanyName.setText(data.getCompanyName());
        }else {
            binding.companyLinear.setVisibility(View.VISIBLE);
            binding.jobCompanyName.setText(data.getMedicalName());
            binding.editResume.setVisibility(View.INVISIBLE);
            if(data.getApplyStatus().equalsIgnoreCase("applied"))
                changeStatus("view", false);

        }

        binding.btnAccept.setOnClickListener(view -> changeStatus("accepted", true));
        binding.btnReject.setOnClickListener(view -> changeStatus("rejected", true));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_application, new MyResumeFragment(data.getMedicalId())).commit();

        binding.editResume.setOnClickListener(view -> startActivity(new Intent(activity, ResumeActivity.class)));


        binding.appliedDate.setText(formatDate(data.getApplyDate()));
        binding.coverLetterQuestion.setText(data.getQuestions1());
        binding.coverLetterAnswer.setText(data.getAnswer1());

        binding.availableQuestion.setText(data.getQuestions2());
        binding.availableAnswer.setText(data.getAnswer2());

        if (!data.getQuestions3().equalsIgnoreCase("")) {
            binding.que3Linear.setVisibility(View.VISIBLE);
            binding.question3.setText(data.getQuestions3());
            binding.answer3.setText(data.getAnswer3());
        }

        if (!data.getQuestions4().equalsIgnoreCase("")) {
            binding.que4Linear.setVisibility(View.VISIBLE);
            binding.question4.setText(data.getQuestions4());
            binding.answer4.setText(data.getAnswer4());
        }

        if (!data.getQuestions5().equalsIgnoreCase("")) {
            binding.que5Linear.setVisibility(View.VISIBLE);
            binding.question5.setText(data.getQuestions5());
            binding.answer5.setText(data.getAnswer5());
        }


    }

    public String formatDate(String s) {
        //  s = "2022-10-21 08:08:18";

        String[] dateTime = s.split(" ");

        String dateS = dateTime[0];
        String[] datess = dateS.split("-");

        String year = datess[2];
        String month = datess[1];
        String day = datess[0];
        return  year +" "+ getMonthName(Integer.parseInt(month)) + " " + day;
    }

    public String getMonthName(int month) {

        String monthString;
        switch (month) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Novr";
                break;
            case 12:
                monthString = "Dec";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        System.out.println(monthString);

        return monthString;
    }

    private  void changeStatus(String status , boolean isView ){
       if(isView)  pd.show();

       ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
       apiInterfaceKapsool.acceptRejectCandidate(
               data.getId(),
               session.getUserId(),
               status
       ).enqueue(new Callback<LogoutModel>() {
           @Override
           public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
               pd.dismiss();
               if(response.code() == 200)
                   if(response.body() != null)
                       if(response.body().getResult().equalsIgnoreCase("true")){
                         if(isView){
                             Toast.makeText(activity, "Status Updated...!", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(activity, MyJobsAndInternshipsActivity.class)
                                     .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                     .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                             );
                             finish();
                         }
                       }else {
                           Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                       }

           }

           @Override
           public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
               pd.dismiss();
           }
       });

    }

}