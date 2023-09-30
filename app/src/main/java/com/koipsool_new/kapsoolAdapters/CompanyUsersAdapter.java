package com.koipsool_new.kapsoolAdapters;

import static com.koipsool_new.RetofitSetUp.BaseUrls.USER_IMAGE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.CompanyListLayoutBinding;
import com.koipsool_new.model.UsersListModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyUsersAdapter extends RecyclerView.Adapter<CompanyUsersAdapter.ViewHolder>{

    Context context ;
    List<UsersListModel.UsersData> models ;

    public CompanyUsersAdapter(Context context, List<UsersListModel.UsersData> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.company_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UsersListModel.UsersData data = models.get(position);

        holder.binding.city.setText(data.getCity());
        holder.binding.companyName.setText(data.getCompanyName());

        if(data.getImage().equalsIgnoreCase(""))
            Picasso.get().load(USER_IMAGE+data.getImage()).placeholder(R.drawable.company_logo).into(holder.binding.companyImage);


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        CompanyListLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CompanyListLayoutBinding.bind(itemView);
        }
    }
}
