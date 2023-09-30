package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.databinding.ResumeProjectLayoutBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.MyResumeProjectsModel;
import com.koipsool_new.ui.resume.UpdateResumeProjectActivity;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyResumeProjectAdapter extends RecyclerView.Adapter<MyResumeProjectAdapter.ViewHolder>{

    Context context ;
    List<MyResumeProjectsModel.ResumeProjectData> models ;
    private  int type = 0 ;

    public MyResumeProjectAdapter(Context context, List<MyResumeProjectsModel.ResumeProjectData> models, int type) {
        this.context = context;
        this.models = models;
        this.type = type ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.resume_project_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyResumeProjectsModel.ResumeProjectData data = models.get(position);

        if(type == 1) holder.binding.linearEdit.setVisibility(View.GONE);

        holder.binding.projectDescription.setText(data.getDiscription());
        holder.binding.projecName.setText(data.getTitle());

        String endTime = data.getCurrentlyGoing() ;

        if(endTime.equalsIgnoreCase("yes")){
            holder.binding.totalTime.setText(formatDate(data.getStartDate())+ " - present");
        }else {
            holder.binding.totalTime.setText(formatDate(data.getStartDate()) + " - "+formatDate(data.getEndDate()) );
        }

        holder.binding.deleteBtn.setOnClickListener(view -> {
            String id = data.getId();
            notifyItemRemoved(position);
            notifyItemRangeChanged(0, models.size()-1);
            models.remove(position);
            deleteProject(id);
        });

        holder.binding.editBtn.setOnClickListener(view -> context.startActivity(new Intent(context, UpdateResumeProjectActivity.class)
                .putExtra("data" ,(Serializable) data )
        ));


    }

    private void deleteProject(String id) {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.deleteMyResumeProject(id).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {

               if(response.code() == 200 && response.body() != null)
                   Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getResult() + "]");

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


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

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ResumeProjectLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ResumeProjectLayoutBinding.bind(itemView);
        }
    }
}
