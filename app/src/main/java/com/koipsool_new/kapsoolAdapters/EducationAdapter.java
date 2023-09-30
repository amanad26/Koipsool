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
import com.koipsool_new.databinding.EducationLayoutBinding;
import com.koipsool_new.kapsoolModels.EducationModel;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.ui.resume.UpdateEducationActivity;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {

    private  Context context;
    private  List<EducationModel.EducationData> models;
    private  int type = 0 ;

    public EducationAdapter(Context context, List<EducationModel.EducationData> models,int type ) {
        this.context = context;
        this.models = models;
        this.type = type ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.education_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EducationModel.EducationData data = models.get(position);
         if(type == 1) holder.binding.linearEdit.setVisibility(View.GONE);

        if (data.getEducation().equalsIgnoreCase("10th")) {
            holder.binding.className.setText("X (Secondary) . " + formatDate(data.getStartDate()));
            holder.binding.year.setText(data.getBoard() + " (" + data.getSchoolCollage() + ") " + data.getPerformance());

        } else if (data.getEducation().equalsIgnoreCase("12th")) {
            holder.binding.className.setText("X|| (Senior Secondary) . " + formatDate(data.getStartDate()));
            holder.binding.year.setText(data.getBoard() + " (" + data.getSchoolCollage() + ") " + data.getPerformance());

        } else {
            holder.binding.year.setText(data.getSchoolCollage());
            holder.binding.className.setText(data.getDegree() + "(" + formatDate(data.getStartDate()) + " - " + formatDate(data.getEndDate()) + ")");
        }


        holder.binding.editBtn.setOnClickListener(view -> context.startActivity(new Intent(context, UpdateEducationActivity.class)
                .putExtra("data", (Serializable) data)
        ));

        holder.binding.deleteBtn.setOnClickListener(view -> {
            String id = data.getId();
            notifyItemRemoved(position);
            notifyItemRangeChanged(0, models.size()-1 );
            models.remove(position);
            deleteEducation(id);
        });

    }

    private void deleteEducation(String id) {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.deleteEducation(id).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(context, "Deleted.!", Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
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
        return year;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EducationLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = EducationLayoutBinding.bind(itemView);
        }
    }
}
