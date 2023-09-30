package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.SkillsLayoutBinding;

import java.util.List;

public class SelectedSkillAdapter extends RecyclerView.Adapter<SelectedSkillAdapter.ViewHolder>{

    Context context ;
    List<String> models ;

    public SelectedSkillAdapter(Context context, List<String> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.skills_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.skillImage.setImageResource(R.drawable.ic_baseline_check_24);
        holder.binding.skillLinear.setBackgroundResource(R.drawable.selected_skills);
        holder.binding.skilsName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        holder.binding.skilsName.setText(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        SkillsLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SkillsLayoutBinding.bind(itemView);
        }
    }
}
