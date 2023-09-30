package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.MedicalHomeLayoutBinding;
import com.koipsool_new.model.MedicalJobModel;
import com.koipsool_new.model.TimeAgo2;
import com.koipsool_new.ui.medical.JobInternshipDetailsMedicalActivity;

import java.io.Serializable;
import java.util.List;

public class MedicalHomeAdapter extends RecyclerView.Adapter<MedicalHomeAdapter.ViewHolder> {

    private Context context;
    private List<MedicalJobModel.JobData> modelList;
    private TimeAgo2 timeAgo2;

    public MedicalHomeAdapter(Context context, List<MedicalJobModel.JobData> modelList) {
        this.context = context;
        this.modelList = modelList;
        timeAgo2 = new TimeAgo2();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.medical_home_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MedicalJobModel.JobData data = modelList.get(position);
        holder.binding.jobTitle.setText(data.getName());
        holder.binding.companyName.setText(data.getCompanyName());
        holder.binding.jobLocation.setText("Location : " + data.getCompanyAddress());
        holder.binding.jobExperiance.setText("No. of opening : " + data.getNumberOfOpening());
        holder.binding.jobPostedTime.setText("Posted : " + timeAgo2.covertTimeToText(data.getCreatedDate()));
        holder.binding.jobSalary.setText("Salary : " + data.getFixedPay() +" " +data.getCtcBreakup());
        holder.binding.jobTotalApplicants.setText("No. of applicants : ");

        holder.binding.cardViewParent.setOnClickListener(view -> {
            Intent intent = new Intent(context, JobInternshipDetailsMedicalActivity.class);
            intent.putExtra("data", (Serializable) data);
            intent.putExtra("type", "1");
            context.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MedicalHomeLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MedicalHomeLayoutBinding.bind(itemView);
        }
    }
}
