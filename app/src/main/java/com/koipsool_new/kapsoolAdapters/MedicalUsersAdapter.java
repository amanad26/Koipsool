package com.koipsool_new.kapsoolAdapters;

import static com.koipsool_new.RetofitSetUp.BaseUrls.USER_IMAGE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.databinding.MedicalUsersLayoutBinding;
import com.koipsool_new.model.UsersListModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MedicalUsersAdapter extends RecyclerView.Adapter<MedicalUsersAdapter.ViewHolder>{

    Context context ;
    List<UsersListModel.UsersData> models ;

    public MedicalUsersAdapter(Context context, List<UsersListModel.UsersData> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.medical_users_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UsersListModel.UsersData data = models.get(position);

        holder.binding.userName.setText(data.getName());
        holder.binding.userCity.setText(data.getCity());

        if(!data.getImage().equalsIgnoreCase("")){
            Picasso.get().load(USER_IMAGE + data.getImage()).into(holder.binding.userImage);
        }



    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        MedicalUsersLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding  = MedicalUsersLayoutBinding.bind(itemView);
        }
    }
}
