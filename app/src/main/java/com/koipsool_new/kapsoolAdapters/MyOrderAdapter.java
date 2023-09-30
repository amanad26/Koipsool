package com.koipsool_new.kapsoolAdapters;

import static com.koipsool_new.RetofitSetUp.BaseUrls.PDF_BASEURL;

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
import com.koipsool_new.databinding.MyOrderBillLayoutBinding;
import com.koipsool_new.kapsoolModels.LoginModel;
import com.koipsool_new.model.MyOrderModel;
import com.koipsool_new.ui.pharma.ViewOrderDetailsActivity;
import com.rajat.pdfviewer.PdfViewerActivity;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>{

    Context context ;
    List<MyOrderModel.MyOrderData> models ;

    public MyOrderAdapter(Context context, List<MyOrderModel.MyOrderData> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_order_bill_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MyOrderModel.MyOrderData data = models.get(position);
        holder.binding.clientName.setText(data.getCustomerName());
        holder.binding.orderPrice.setText(data.getTotalAmount());

        holder.binding.linearView.setOnClickListener(view -> context.startActivity(new Intent(context, ViewOrderDetailsActivity.class)
                .putExtra("order_id",models.get(position).getId())
        ));

        holder.binding.orderView.setOnClickListener(view -> {
            String id = models.get(position).getId() ;
            notifyItemRemoved(position);
            notifyItemRangeChanged(0, models.size() -1 );
            models.remove(position);
            deleteOrder(id);
        });

        holder.binding.orderDownloadPdf.setOnClickListener(view -> {
            int num = new Random().nextInt(1000000);
            context.startActivity(
                    PdfViewerActivity.Companion.launchPdfFromUrl(
                            context,
                            PDF_BASEURL + "?order_id=" + models.get(position).getId(),
                            models.get(position).getCustomerName()+"invoice"+num,
                            "downloads/kapsool",
                            true
                    )
            );
        });



    }
    private  void deleteOrder(String orderId){

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.deleteMyOrder(orderId).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                        }

            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        MyOrderBillLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MyOrderBillLayoutBinding.bind(itemView);
        }
    }
}
