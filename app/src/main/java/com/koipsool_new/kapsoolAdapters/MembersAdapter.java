package com.koipsool_new.kapsoolAdapters;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.IMAGE_URL;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.R;
import com.koipsool_new.databinding.MemberLayoutBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.TeamMemberModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder>{

    Context context ;
    List<TeamMemberModel.MemberData> models ;
    ProgressDialog pd ;

    public MembersAdapter(Context context, List<TeamMemberModel.MemberData> models) {
        this.context = context;
        this.models = models;
        pd = new ProgressDialog(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.member_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.myMemberName.setText(models.get(position).getName());
        holder.binding.myMemberWorkRole.setText(models.get(position).getWork());
        holder.binding.myMemberDepartment.setText(models.get(position).getDepartment());

        holder.binding.editRole.setOnClickListener(view -> addBottomSheet(models.get(holder.getAdapterPosition())));
        
        holder.binding.myMemberRemove.setOnClickListener(view -> {
            try {
                deleteMember(models.get(position).getId());
                notifyItemRemoved(position);
                notifyItemRangeChanged(0,models.size()-1);
                models.remove(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if(models.get(position).getImage() != null)
            Picasso.get().load(IMAGE_URL+models.get(position).getImage()).placeholder(R.drawable.dummyuser).into(holder.binding.myMemberImage);


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    private void addBottomSheet(TeamMemberModel.MemberData data) {
        RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(context);
        View sheetView = mBottomSheetDialog.getLayoutInflater().inflate(R.layout.activity_edit_role, null);

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);

        EditText name = mBottomSheetDialog.findViewById(R.id.memebr_name);
        EditText member_department = mBottomSheetDialog.findViewById(R.id.member_department);
        EditText member_role = mBottomSheetDialog.findViewById(R.id.member_role);

        name.setText(data.getName());
        member_department.setText(data.getDepartment());
        member_role.setText(data.getWork());

        CardView save_btn = mBottomSheetDialog.findViewById(R.id.save_btn);

        save_btn.setOnClickListener(view -> {
           pd.show();
           updateTemMemberDetails(name.getText().toString(),member_department.getText().toString(),member_role.getText().toString(),data.getId() , data.getCompanyId(),mBottomSheetDialog);
        });

        mBottomSheetDialog.show();
    }

    private  void updateTemMemberDetails(String s, String toString, String string, String id, String companyId, RoundedBottomSheetDialog mBottomSheetDialog){

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);

        apiInterfaceKapsool.updatePharmacyMembers(
                id,
                s,
                string,
                companyId,
                toString
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                              mBottomSheetDialog.dismiss();
                              pd.dismiss();
                            }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                mBottomSheetDialog.dismiss();
                pd.dismiss();
            }
        });


    }

    private  void deleteMember(String id){

        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.deletePharmacyMembers(id).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                          if (response.body().getResult().equalsIgnoreCase("true")) {
                              Toast.makeText(context, "Member Deleted..!", Toast.LENGTH_SHORT).show();
                         }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
            }
        });
        
        
    }
    
    public  class  ViewHolder extends RecyclerView.ViewHolder{
        MemberLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MemberLayoutBinding.bind(itemView);
        }
    }

}
