package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.ParksSkillsLayoutBinding;

import java.util.List;

public class SkillsParksAdapter extends RecyclerView.Adapter<SkillsParksAdapter.ViewHolder>{

    Context context ;
    List<String> models ;

    public SkillsParksAdapter(Context context, List<String> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.parks_skills_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.skillsTv.setText(models.get(position));

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        ParksSkillsLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ParksSkillsLayoutBinding.bind(itemView);
        }
    }
}
