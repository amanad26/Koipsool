package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.ApplicantsListLayoutBinding;
import com.koipsool_new.model.MyAppliedJobModel;
import com.koipsool_new.ui.medical.MyApplicationActivity;

import java.util.List;

public class ApplicantsAdapter extends RecyclerView.Adapter<ApplicantsAdapter.ViewHolder>{

    Context context ;
    List<MyAppliedJobModel.MyAppliedData> models;
    int type = 0 ;

    public ApplicantsAdapter(Context context, List<MyAppliedJobModel.MyAppliedData> models, int type) {
        this.context = context;
        this.type = type;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.applicants_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyAppliedJobModel.MyAppliedData data = models.get(position);


        if(data.getType().equalsIgnoreCase("job")){
         holder.binding.jobName.setText(data.getPostJobName());
        }else {
         holder.binding.jobName.setText(data.getPostInternshipName());
        }

        holder.binding.status.setText("Status : " +data.getApplyStatus());

        if(data.getApplyStatus().equalsIgnoreCase("applied")){
            holder.binding.status.setText("Status : Applied");
            holder.binding.status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else if(data.getApplyStatus().equalsIgnoreCase("accepted")){
            holder.binding.status.setText("Status : Accepted");
            holder.binding.status.setTextColor(context.getResources().getColor(R.color.green));
        }else if(data.getApplyStatus().equalsIgnoreCase("rejected")){
            holder.binding.status.setText("Status : Rejected");
            holder.binding.status.setTextColor(context.getResources().getColor(R.color.red));
        }else if(data.getApplyStatus().equalsIgnoreCase("view")){
            holder.binding.status.setText("Status : Viewed");
            holder.binding.status.setTextColor(context.getResources().getColor(R.color.yellow));
        }


        holder.binding.userName.setText(data.getMedicalName());

        holder.binding.date.setText(formatDate(data.getApplyDate()));

        holder.binding.cardViewParent.setOnClickListener(view -> context.startActivity(new Intent(context, MyApplicationActivity.class)
                .putExtra("data" , data)
                .putExtra("type" , String.valueOf(type))
        ));

    }


    public String formatDate(String s) {
        //  s = "2022-10-21 08:08:18";

        String[] dateTime = s.split(" ");

        String dateS = dateTime[0];
        String[] datess = dateS.split("-");

        String year = datess[2];
        String month = datess[1];
        String day = datess[0];
        return   year +" " + getMonthName(Integer.parseInt(month)) + " " + day;
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

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        ApplicantsListLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ApplicantsListLayoutBinding.bind(itemView);
        }
    }
}
