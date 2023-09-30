package com.koipsool_new.kapsoolAdapters;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.R;
import com.koipsool_new.databinding.MyProductLayoutBinding;
import com.koipsool_new.kapsoolModels.CategoriesModel;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProductModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    String selectedTax = "", selectedCategory;
    List<ProductModel.ProductData> models;
    private List<String> categoryName = new ArrayList<>();
    private List<Integer> categoryIds = new ArrayList<>();

    public ProductAdapter(Context context, List<ProductModel.ProductData> models) {
        this.context = context;
        this.models = models;
        getCategory();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_product_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ProductModel.ProductData data = models.get(position);

        holder.binding.productName.setText(data.getName());
        holder.binding.productBatchNumber.setText(data.getBatchNo());
        holder.binding.productExpreDate.setText(data.getExpiry());
        holder.binding.productMrp.setText("MRP " + data.getMrp());
        holder.binding.productTexClass.setText("Tax Class " + data.getTaxClass());
        holder.binding.productDescription.setText(data.getDesciption());
        holder.binding.productHsnCode.setText("HSN " + data.getHsn_code());

        holder.binding.productRemove.setOnClickListener(view -> deleteProduct(data.getId(), position));

        holder.binding.productEdit.setOnClickListener(view -> showUpdateProductDialog(data, holder));

    }

    private void showUpdateProductDialog(ProductModel.ProductData data, ViewHolder holder) {


        RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(context);
        View sheetView = mBottomSheetDialog.getLayoutInflater().inflate(R.layout.update_product_layout, null);
        mBottomSheetDialog.setContentView(sheetView);

        EditText product_name = mBottomSheetDialog.findViewById(R.id.product_name);
        EditText product_description = mBottomSheetDialog.findViewById(R.id.product_description);
        EditText product_mrp = mBottomSheetDialog.findViewById(R.id.product_mrp);
        EditText product_batch_number = mBottomSheetDialog.findViewById(R.id.product_batch_number);
        EditText product_hsn_code = mBottomSheetDialog.findViewById(R.id.product_hsn_code);
        TextView product_expire_date = mBottomSheetDialog.findViewById(R.id.product_expire_date);
        MaterialSpinner category_spiner = mBottomSheetDialog.findViewById(R.id.category_spiner);
        MaterialSpinner product_tax_class = mBottomSheetDialog.findViewById(R.id.product_tax_class);
        CardView update_product = mBottomSheetDialog.findViewById(R.id.update_product);


        product_name.setText(data.getName());
        product_batch_number.setText(data.getBatchNo());
        product_expire_date.setText(data.getExpiry());
        product_hsn_code.setText(data.getHsn_code());
        product_mrp.setText( data.getMrp());
        product_description.setText(data.getDesciption());

        product_expire_date.setOnClickListener(view -> selectDate(product_expire_date));

        List<String> arrId = new ArrayList<>();
        arrId.add("5%");
        arrId.add("12%");
        arrId.add("20%");
        arrId.add("28%");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, arrId);
        product_tax_class.setAdapter(spinnerAdapter);

        product_tax_class.setOnItemSelectedListener((view, position, id, item) -> selectedTax = arrId.get(position));


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, categoryName);
        category_spiner.setAdapter(categoryAdapter);

        category_spiner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                selectedCategory = String.valueOf(categoryIds.get(position));
            }
        });

        selectedCategory = data.getCate_name();
        selectedTax = data.getTaxClass();

        int pos = 0;
        if (!data.getCate_name().equalsIgnoreCase("")) {
            for (int i = 0; i < categoryName.size(); i++) {
                if (categoryName.get(i).equalsIgnoreCase(data.getCate_name()))
                    pos = categoryIds.get(i);
            }
            category_spiner.setSelectedIndex(pos);
        }


        int taxPos = 0;
        if (!data.getTaxClass().equalsIgnoreCase("")) {
            for (int i = 0; i < arrId.size(); i++) {
                if (arrId.get(i).equalsIgnoreCase(data.getTaxClass()))
                    taxPos = i;
            }
            product_tax_class.setSelectedIndex(taxPos);
        }

        update_product.setOnClickListener(view -> {

            ProgressDialog pd = new ProgressDialog(context);
            pd.show();

            ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
            apiInterfaceKapsool.updateProduct(
                    data.getId(),
                    product_name.getText().toString(),
                    product_description.getText().toString(),
                    product_batch_number.getText().toString(),
                    product_expire_date.getText().toString(),
                    product_mrp.getText().toString(),
                    selectedTax
            ).enqueue(new Callback<LogoutModel>() {
                @Override
                public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                    pd.dismiss();
                    if (response.code() == 200) {
                        assert response.body() != null;
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(context, "Product Updated..!", Toast.LENGTH_SHORT).show();
                            mBottomSheetDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Failed...!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                    pd.dismiss();
                    mBottomSheetDialog.dismiss();
                    Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                }
            });


        });

        mBottomSheetDialog.show();


    }


    public String getMonthName(int month) {

        String monthString;
        switch (month) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            case 12:
                monthString = "Dec";
                break;
            default:
                monthString = "Inv month";
                break;
        }
        System.out.println(monthString);

        return monthString;
    }

    private void getCategory() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.getCategories().enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesModel> call, @NonNull Response<CategoriesModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                categoryName.add(response.body().getData().get(i).getCateName());
                                categoryIds.add(Integer.parseInt(response.body().getData().get(i).getId()));
                            }
                        }

            }

            @Override
            public void onFailure(@NonNull Call<CategoriesModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void selectDate(TextView DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (datePicker, year1, month1, day1) -> {
            String date = day1 + " /" + getMonthName((month1 + 1)) + "/ " +
                    "/" + year1;
            DateBtn.setText(date);

        }, year, month, day);
        datePickerDialog.show();
    }

    private void deleteProduct(String id, int position) {

        ProgressDialog pd = new ProgressDialog(context);
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(context);
        apiInterfaceKapsool.deleteProduct(id).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(context, "Product Deleted..", Toast.LENGTH_SHORT).show();

                            try {
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(0, models.size() - 1);
                                models.remove(position);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(context, "Failed..", Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        MyProductLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MyProductLayoutBinding.bind(itemView);
        }
    }
}
