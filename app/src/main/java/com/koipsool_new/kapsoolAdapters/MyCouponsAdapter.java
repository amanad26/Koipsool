package com.koipsool_new.kapsoolAdapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.koipsool_new.R;
import com.koipsool_new.databinding.MyCouponsLayoutBinding;
import com.koipsool_new.kapsoolModels.CouponsModel;

import java.util.ArrayList;
import java.util.List;

public class MyCouponsAdapter extends RecyclerView.Adapter<MyCouponsAdapter.ViewHolder> {

    Context context;
    String updateavailable = "";
    List<CouponsModel.CouponsData> models;

    public MyCouponsAdapter(Context context, List<CouponsModel.CouponsData> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_coupons_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CouponsModel.CouponsData data = models.get(position);

        holder.binding.productName.setText(data.getProduct());
        holder.binding.compositions.setText(data.getCorporation());
        holder.binding.discountParcentage.setText(data.getDiscount() + "%");
        holder.binding.offer.setText(data.getOffer());
        holder.binding.offerPrice.setText(data.getDiscountPrice());
        holder.binding.mrp.setText(Html.fromHtml("<s>" + data.getMrp() + "</s>"));
        holder.binding.validity.setText(data.getCouponValidity());

        holder.itemView.setOnClickListener(view -> updateCouponsDialog(data));

    }

    public void updateCouponsDialog(CouponsModel.CouponsData data) {

        List<String> arrId = new ArrayList<>();

        RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(context);
        View sheetView = mBottomSheetDialog.getLayoutInflater().inflate(R.layout.update_coupons_layout, null);
        mBottomSheetDialog.setContentView(sheetView);

        EditText product_name, product_compositions, product_mrp, product_offer, product_discount_percentage, product_discount_price;
        product_name = mBottomSheetDialog.findViewById(R.id.product_name);
        product_compositions = mBottomSheetDialog.findViewById(R.id.product_compositions);
        product_mrp = mBottomSheetDialog.findViewById(R.id.product_mrp);
        product_offer = mBottomSheetDialog.findViewById(R.id.product_offer);
        product_discount_percentage = mBottomSheetDialog.findViewById(R.id.product_discount_percentage);
        product_discount_price = mBottomSheetDialog.findViewById(R.id.product_discount_price);
        TextView coupon_validity = mBottomSheetDialog.findViewById(R.id.coupon_validity);

        MaterialSpinner available_in = mBottomSheetDialog.findViewById(R.id.available_in);
        CardView update_coupons = mBottomSheetDialog.findViewById(R.id.update_coupons);

        product_name.setText(data.getProduct());
        product_compositions.setText(data.getCorporation());
        product_discount_percentage.setText(data.getDiscount() + "%");
        product_offer.setText(data.getOffer());
        product_discount_price.setText(data.getDiscountPrice());
        product_mrp.setText(Html.fromHtml("<s>" + data.getMrp() + "</s>"));
        coupon_validity.setText(data.getCouponValidity());

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, arrId);
        available_in.setAdapter(spinnerAdapter);
        available_in.setOnItemSelectedListener((view, position, id, item) -> {
            updateavailable = arrId.get(position);
        });

        try {

            arrId.add("Available for*");
            arrId.add("All");
            arrId.add("New");
            arrId.add("Old");

            int pos = 0;
            if (data.getAvailable().equalsIgnoreCase("All"))
                pos = 1;
            else if (data.getAvailable().equalsIgnoreCase("New"))
                pos = 2;
            else if (data.getAvailable().equalsIgnoreCase("Old"))
                pos = 3;
            else pos = 0;


            available_in.setSelectedIndex(pos);


        } catch (Exception e) {
            e.printStackTrace();
        }


        update_coupons.setOnClickListener(view -> {
            if (updateavailable.equalsIgnoreCase(""))
                updateavailable = data.getAvailable();

            Log.e("TAG", "updateCouponsDialog() called with: Available  = [" + updateavailable + "]");

//            ProgressDialog pd = new ProgressDialog(context);
//            pd.show();
//            Log.e("TAG", "updateCouponsDialog() called with: Price = [" + product_mrp.getText().toString() + "]");
//
//            ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
//            apiInterfaceKapsool.updateCouponDetails(
//                    data.getId(),
//                    product_name.getText().toString(),
//                    product_compositions.getText().toString(),
//                    product_offer.getText().toString(),
//                    product_mrp.getText().toString(),
//                    product_discount_percentage.getText().toString(),
//                    product_discount_price.getText().toString(),
//                    coupon_validity.getText().toString(),
//                    updateavailable
//            ).enqueue(new Callback<LogoutModel>() {
//                @Override
//                public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
//
//                    pd.dismiss();
//                    if (response.code() == 200)
//                        if (response.body() != null)
//                            if (response.body().getResult().equalsIgnoreCase("true")) {
//                                Toast.makeText(context, "Updated...!", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(context, "Failed To Update details...!", Toast.LENGTH_SHORT).show();
//                            }
//
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
//                    pd.dismiss();
//                    Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
//                }
//            });

            mBottomSheetDialog.dismiss();

        });

        mBottomSheetDialog.show();


    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MyCouponsLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MyCouponsLayoutBinding.bind(itemView);
        }
    }
}
