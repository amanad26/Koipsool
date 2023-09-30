package com.koipsool_new.kapsoolAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.SkillsLayoutBinding;
import com.koipsool_new.kapsoolModels.SkillsInterface;
import com.koipsool_new.kapsoolModels.SkillsModel;

import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder>{

    Context context ;
    List<SkillsModel.SkillsData> models ;
    SkillsInterface skillsInterface ;

    public SkillsAdapter(Context context, List<SkillsModel.SkillsData> models,SkillsInterface skillsInterface ) {
        this.context = context;
        this.models = models;
        this.skillsInterface = skillsInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.skills_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.skilsName.setText(models.get(position).getSkillName());


        if(models.get(holder.getAdapterPosition()).isSelected()){
            holder.binding.skilsName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            models.get(position).setSelected(true);
            holder.binding.skillLinear.setBackgroundResource(R.drawable.selected_skills);
            holder.binding.skillImage.setImageResource(R.drawable.ic_baseline_check_24);
            skillsInterface.onSelected(models.get(position).getSkillName(), models.get(position).getId());
        }

        holder.binding.skilsName.setOnClickListener(view -> {
            if(models.get(holder.getAdapterPosition()).isSelected()){
                models.get(position).setSelected(false);
                holder.binding.skillLinear.setBackgroundResource(R.drawable.unselected_skils);
                holder.binding.skillImage.setImageResource(R.drawable.ic_baseline_add);
                holder.binding.skilsName.setTextColor(context.getResources().getColor(R.color.gray));
                skillsInterface.onRemove(models.get(holder.getAdapterPosition()).getSkillName(), models.get(position).getId());
            }else {
                holder.binding.skilsName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                models.get(position).setSelected(true);
                holder.binding.skillLinear.setBackgroundResource(R.drawable.selected_skills);
                holder.binding.skillImage.setImageResource(R.drawable.ic_baseline_check_24);
                skillsInterface.onSelected(models.get(position).getSkillName(), models.get(position).getId());
            }
        });

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
