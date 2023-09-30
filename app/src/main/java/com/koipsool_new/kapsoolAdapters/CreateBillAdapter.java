package com.koipsool_new.kapsoolAdapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.kapsoolModels.CreateBillProductModel;
import com.koipsool_new.kapsoolModels.LogoutModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBillAdapter extends RecyclerView.Adapter<CreateBillAdapter.ViewHolder> {

    private int type = 0;
    private Context context;
    private List<CreateBillProductModel.OrderItemData> models;


    public CreateBillAdapter(Context context, List<CreateBillProductModel.OrderItemData> models, int i) {
        this.context = context;
        this.models = models;
        this.type = i;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (type == 0)
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bill_product_layout, parent, false));
        else
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_bill_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CreateBillProductModel.OrderItemData data = models.get(position);

        holder.bill_product_name.setText(data.getProductName());
        holder.bill_product_price.setText(data.getProductMrp());
        holder.bill_product_quantity.setText(data.getProductQuantity());
        holder.bill_product_sellingPrice.setText(data.getProductSellingPrice());

        holder.remove.setOnClickListener(view -> {
            String id = models.get(position).getId();
            notifyItemRemoved(position);
            notifyItemRangeChanged(0, models.size() - 1);
            models.remove(position);
            deleteProduct(id);
        });

    }

    private void deleteProduct(String id) {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.deleteProduct(id).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call,@NonNull Response<LogoutModel> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call,@NonNull Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bill_product_quantity, bill_product_name, remove, bill_product_price, bill_product_sellingPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bill_product_quantity = itemView.findViewById(R.id.bill_product_quantity);
            bill_product_name = itemView.findViewById(R.id.bill_product_name);
            bill_product_price = itemView.findViewById(R.id.bill_product_price);
            bill_product_sellingPrice = itemView.findViewById(R.id.bill_product_sellingPrice);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
