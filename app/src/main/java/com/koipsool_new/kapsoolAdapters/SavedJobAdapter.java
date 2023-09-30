package com.koipsool_new.kapsoolAdapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.SavedJobLayoutBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.SavedJobModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedJobAdapter extends RecyclerView.Adapter<SavedJobAdapter.ViewHolder> {

    Context context;
    List<SavedJobModel.SavedData> models;
    String url ;
    private Session session ;

    public SavedJobAdapter(Context context, List<SavedJobModel.SavedData> models, String ul) {
        this.context = context;
        this.models = models;
        this.url  =ul;
        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.saved_job_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SavedJobModel.SavedData data = models.get(position);


        if (data.getType().equalsIgnoreCase("job")) {
            holder.binding.jobTitle.setText(data.getPostJobName());
            holder.binding.price.setText(data.getFixed_pay()+" LPA");
            holder.binding.linearDuration.setVisibility(View.GONE);
        } else {
            holder.binding.jobTitle.setText(data.getPost_internship_name());
            holder.binding.price.setText(data.getPrice_from() +" /Months");
            holder.binding.duration.setText(data.getInternship_duration());
        }

        holder.binding.jobCompanyName.setText(data.getCompanyName());
        holder.binding.typeText.setText(data.getType());
        holder.binding.jobTotalApplicants.setText(data.getCount() + " Applicants");

        if(data.getCompanyImage().equalsIgnoreCase("")){
            holder.binding.companyLogo.setVisibility(View.GONE);
        }else {
            Picasso.get().load(url +data.getCompanyImage()).into(holder.binding.companyLogo);
        }

        holder.binding.removeTv.setOnClickListener(view -> {

            String cid = data.getCompanyId();
            String id = data.getJobInternshipId() ;
            String type  = data.getType();

            notifyItemRemoved(position);
            notifyItemRangeChanged(0, models.size()-1);
            models.remove(position);
            removeFromSaved(cid, id , type);

        });

    }

    private void removeFromSaved(String cid, String id, String type) {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.saveUnSaveJob(
                session.getUserId(),
                id,
                cid,
                "0",
                type
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            Log.e(TAG, "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                        }


            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SavedJobLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SavedJobLayoutBinding.bind(itemView);
        }
    }
}
