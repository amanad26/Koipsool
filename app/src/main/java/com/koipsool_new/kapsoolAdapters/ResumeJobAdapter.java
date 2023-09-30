package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.R;
import com.koipsool_new.databinding.ResumeJobInternshipLayoutBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ResumeJobModel;
import com.koipsool_new.ui.resume.UpdateResumeJobActivity;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeJobAdapter extends RecyclerView.Adapter<ResumeJobAdapter.ViewHolder> {
    Context context;
    List<ResumeJobModel.ResumeJobData> models;
    private  int type  =  0 ;

    public ResumeJobAdapter(Context context, List<ResumeJobModel.ResumeJobData> models, int type) {
        this.context = context;
        this.models = models;
        this.type = type ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.resume_job_internship_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResumeJobModel.ResumeJobData data = models.get(position);

        if(type == 1) holder.binding.linearEdit.setVisibility(View.GONE);


        holder.binding.className.setText(data.getJobProfile());
        holder.binding.companyLocation.setText(data.getOrganization() + " , " + data.getLocation());

        String endDate = data.getEndDate();

         if (endDate.equalsIgnoreCase("present")) {
            holder.binding.year.setText(formatDate(data.getStartDate()) + "  -  " + data.getEndDate());
         } else {
            holder.binding.year.setText(formatDate(data.getStartDate()) + "  -  " + formatDate(data.getEndDate()));
         }

         holder.binding.deleteBtn.setOnClickListener(view -> {
             String id  = data.getId();
             notifyItemRemoved(position);
             notifyItemRangeChanged(0, models.size() -1 );
             models.remove(position);
             deleteJobInternship(id);
         });

         holder.binding.editBtn.setOnClickListener(view -> {
           context.startActivity(new Intent(context, UpdateResumeJobActivity.class)
                   .putExtra("data" , (Serializable) data )
                   .putExtra("type" , data.getType())
           );
         });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public String formatDate(String s) {
        //  s = "2022-10-21 08:08:18";

        String[] dateTime = s.split(" ");

        String dateS = dateTime[0];
        String[] datess = dateS.split("-");

        String year = datess[2];
        String month = datess[1];
        String day = datess[0];
        return getMonthName(Integer.parseInt(month)) + " " + year;
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

    private  void deleteJobInternship(String id){

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.deleteResumeJob(id).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {

                if(response.code() == 200 )
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            Toast.makeText(context, "Deleted..!", Toast.LENGTH_SHORT).show();
                        }
                
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ResumeJobInternshipLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ResumeJobInternshipLayoutBinding.bind(itemView);
        }
    }
}
