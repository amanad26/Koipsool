package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.ResumeSkillLayoutBinding;
import com.koipsool_new.kapsoolModels.ResumeSkillInterface;
import com.koipsool_new.kapsoolModels.SkillsModel;

import java.util.List;

public class ResumeSkillAdapter extends RecyclerView.Adapter<ResumeSkillAdapter.ViewHolder>{

    Context context ;
    List<SkillsModel.SkillsData> models ;
    ResumeSkillInterface resumeSkillInterface ;

    public ResumeSkillAdapter(Context context, List<SkillsModel.SkillsData> models,  ResumeSkillInterface resumeSkillInterface) {
        this.context = context;
        this.models = models;
        this.resumeSkillInterface = resumeSkillInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.resume_skill_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SkillsModel.SkillsData data = models.get(position);

        holder.binding.skillName.setText(data.getSkillName());

        holder.itemView.setOnClickListener(view -> resumeSkillInterface.addSkill(data.getSkillName(), data.getId()));

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ResumeSkillLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ResumeSkillLayoutBinding.bind(itemView);
        }
    }

}
