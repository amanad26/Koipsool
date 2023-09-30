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

import com.koipsool_new.ui.pharma.PharmaHomeAllDetailsActivity;
import com.koipsool_new.R;
import com.koipsool_new.databinding.MyJobInternshipLayoutBinding;
import com.koipsool_new.kapsoolModels.MyJobsModel;
import com.koipsool_new.ui.pharma.ViewApplicatnsActivity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyJobsInternshipAdapter extends RecyclerView.Adapter<MyJobsInternshipAdapter.ViewHolder> {

    Context context;
    List<MyJobsModel.MyJobInternshipData.PostJob> jobModel;
    List<MyJobsModel.MyJobInternshipData.PostInternship> internshipModel;
    TimeAgo2 ago2;

    public MyJobsInternshipAdapter(Context context, List<MyJobsModel.MyJobInternshipData.PostJob> jobModel, List<MyJobsModel.MyJobInternshipData.PostInternship> internshipModel) {
        this.context = context;
        this.jobModel = jobModel;
        this.internshipModel = internshipModel;
        ago2 = new TimeAgo2();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_job_internship_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position < jobModel.size()) {
            MyJobsModel.MyJobInternshipData.PostJob data = jobModel.get(position);

            holder.binding.titleText.setText(data.getName());
            holder.binding.typeText.setText("Job");
            holder.binding.companyNameText.setText(data.getCompanyName());

            holder.binding.priceText.setText(data.getFixedPay() + " " + data.getCtcBreakup());
            holder.binding.applicationReceived.setText(data.getCount() + " Applicants");

            holder.binding.details.setOnClickListener(view -> {
                Intent intent = new Intent(context, PharmaHomeAllDetailsActivity.class);
                intent.putExtra("data", (Serializable) data);
                intent.putExtra("type", "1");
                context.startActivity(intent);

            });

            if(data.getCompanyAddress().equalsIgnoreCase("")){
                holder.binding.cityText.setText("Indore");
            }else {
                holder.binding.cityText.setText(data.getCompanyAddress());
            }

            holder.binding.applicationReceived.setOnClickListener(view -> context.startActivity(new Intent(context, ViewApplicatnsActivity.class)
                    .putExtra("c_id", data.getCompanyId())
                    .putExtra("j_id", data.getId())
            ));

            holder.binding.createDateText.setText(ago2.covertTimeToText(data.getCreatedDate()));


        } else {

            int pos = position - jobModel.size();
            MyJobsModel.MyJobInternshipData.PostInternship data = internshipModel.get(pos);

            holder.binding.titleText.setText(data.getInternshipTitle());
            holder.binding.typeText.setText("Internship");
            holder.binding.cityText.setText(data.getCity());
            holder.binding.applicationReceived.setText(data.getCount() + " Applicants");
            holder.binding.companyNameText.setText(data.getCompanyName());
            holder.binding.priceText.setText(data.getPriceFrom() + " /Months");
            holder.binding.applicationReceived.setText(data.getCount() + " Applicants");

//            holder.binding.durationTv.setVisibility(View.VISIBLE);
//            holder.binding.durationTv.setText(data.getDuration());

            holder.binding.details.setOnClickListener(view -> {
                Intent intent = new Intent(context, PharmaHomeAllDetailsActivity.class);
                intent.putExtra("data", (Serializable) data);
                intent.putExtra("type", "2");
                context.startActivity(intent);
            });

            holder.binding.createDateText.setText(ago2.covertTimeToText(data.getCreatedDate()));

            holder.binding.applicationReceived.setOnClickListener(view -> {
                if (!data.getCount().equalsIgnoreCase("0")) {
                       context.startActivity(new Intent(context, ViewApplicatnsActivity.class)
                          .putExtra("c_id", data.getCompanyId())
                          .putExtra("j_id", data.getId())
                  );
                }else {
                    Toast.makeText(context, "No Applicatns Found..!", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return jobModel.size() + internshipModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MyJobInternshipLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MyJobInternshipLayoutBinding.bind(itemView);
        }
    }

    public class TimeAgo2 {

        public String covertTimeToText(String dataDate) {

            String convTime = null;

            String prefix = "";
            String suffix = "Ago";

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date pasTime = dateFormat.parse(dataDate);

                Date nowTime = new Date();

                long dateDiff = nowTime.getTime() - pasTime.getTime();

                long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
                long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
                long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
                long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

                if (second < 60) {
                    convTime = second + " Seconds " + suffix;
                } else if (minute < 60) {
                    convTime = minute + " Minutes " + suffix;
                } else if (hour < 24) {
                    convTime = hour + " Hours " + suffix;
                } else if (day >= 7) {
                    if (day > 360) {
                        convTime = (day / 360) + " Years " + suffix;
                    } else if (day > 30) {
                        convTime = (day / 30) + " Months " + suffix;
                    } else {
                        convTime = (day / 7) + " Week " + suffix;
                    }
                } else if (day < 7) {
                    convTime = day + " Days " + suffix;
                }

            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("ConvTimeE", e.getMessage());
            }

            return convTime;
        }

    }

}
