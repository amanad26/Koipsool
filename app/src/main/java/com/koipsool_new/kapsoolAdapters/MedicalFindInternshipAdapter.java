package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.MyJobInternshipLayoutBinding;
import com.koipsool_new.model.MedicalInternshipModel;
import com.koipsool_new.model.TimeAgo2;
import com.koipsool_new.ui.medical.JobInternshipDetailsMedicalActivity;

import java.io.Serializable;
import java.util.List;

public class MedicalFindInternshipAdapter extends RecyclerView.Adapter<MedicalFindInternshipAdapter.ViewHolder>{

    Context context;
    TimeAgo2 ago2 ;
    List<MedicalInternshipModel.MedicalInternshipData> models;

    public MedicalFindInternshipAdapter(Context context, List<MedicalInternshipModel.MedicalInternshipData> models) {
        this.context = context;
        this.models = models;
        ago2 = new TimeAgo2();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_job_internship_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MedicalInternshipModel.MedicalInternshipData data = models.get(position);

        holder.binding.titleText.setText(data.getInternshipTitle());
        holder.binding.typeText.setText("Internship");
        holder.binding.cityText.setText(data.getCity());


        holder.binding.applicationReceived.setText(data.getCount() + " Applicants");


        holder.binding.companyNameText.setText(data.getCompanyName());
        holder.binding.priceText.setText(data.getPriceTo() + " LPA");

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, JobInternshipDetailsMedicalActivity.class);
            intent.putExtra("data", (Serializable) data);
            intent.putExtra("type", "2");
            context.startActivity(intent);
        });

        holder.binding.createDateText.setText(ago2.covertTimeToText(data.getCreatedDate()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MyJobInternshipLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MyJobInternshipLayoutBinding.bind(itemView);

        }
    }
}
