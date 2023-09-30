package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.MyAppliedJobInterLayoutBinding;
import com.koipsool_new.model.MyAppliedJobModel;
import com.koipsool_new.ui.medical.MyApplicationActivity;

import java.util.List;

public class MyAppliedJobAdapter extends RecyclerView.Adapter<MyAppliedJobAdapter.ViewHolder>{

    Context context;
    List<MyAppliedJobModel.MyAppliedData> models ;

    public MyAppliedJobAdapter(Context context, List<MyAppliedJobModel.MyAppliedData> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_applied_job_inter_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyAppliedJobModel.MyAppliedData data = models.get(position);

        holder.binding.jobCompanyName.setText(data.getCompanyName());

        if(data.getApplyStatus().equalsIgnoreCase("applied"))
            holder.binding.jobStatus.setText("Applied");
        else if(data.getApplyStatus().equalsIgnoreCase("accept"))
            holder.binding.jobStatus.setText("Selected");
        else if(data.getApplyStatus().equalsIgnoreCase("reject"))
            holder.binding.jobStatus.setText("Not Selected");
        else
            holder.binding.jobStatus.setText("Application Viewed");

        if(data.getType().equalsIgnoreCase("job"))
            holder.binding.jobTitle.setText(data.getPostJobName());
        else holder.binding.jobTitle.setText(data.getPostInternshipName());

        holder.binding.typeText.setText(data.getType());

        holder.binding.jobTotalApplicants.setText(data.getCount() + " Applicants");

        holder.binding.appliedDate.setText("Applied on "+formatDate(data.getApplyDate()));


        holder.binding.viewDetails.setOnClickListener(view -> context.startActivity(new Intent(context, MyApplicationActivity.class)
                .putExtra("data" , data)
        ));

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
        return getMonthName(Integer.parseInt(month)) + " " + day;
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


    public  class  ViewHolder extends RecyclerView.ViewHolder{
        MyAppliedJobInterLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding  = MyAppliedJobInterLayoutBinding.bind(itemView);
        }
    }
}
