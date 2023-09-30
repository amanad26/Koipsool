package com.koipsool_new.kapsoolAdapters;

import android.annotation.SuppressLint;
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
import com.koipsool_new.databinding.MySkillsLayoutBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.MySkillsModel;
import com.koipsool_new.ui.resume.AddResumeSkillActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySkillsAdapter extends RecyclerView.Adapter<MySkillsAdapter.ViewHolder> {


    private Context context;
    private List<MySkillsModel.MySkillData> models;
    private  int type = 0 ;

    public MySkillsAdapter(Context context, List<MySkillsModel.MySkillData> models, int type) {
        this.context = context;
        this.models = models;
        this.type = type ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_skills_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MySkillsModel.MySkillData data = models.get(position);

        if(type == 1) holder.binding.editLinear.setVisibility(View.GONE);
        holder.binding.name.setText(data.getSkillName());
        holder.binding.rate.setText(data.getSkillRate());


        holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = data.getId();
                notifyItemRemoved(position);
                notifyItemRangeChanged(0, models.size()-1);
                models.remove(position);
                deleteSkill(id);
            }
        });


        holder.binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AddResumeSkillActivity.class)
                        .putExtra("type","1")
                        .putExtra("skill_id",data.getId())
                );
            }
        });


    }


    private void deleteSkill(String id) {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.deleteSkill(id).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MySkillsLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MySkillsLayoutBinding.bind(itemView);
        }
    }
}
