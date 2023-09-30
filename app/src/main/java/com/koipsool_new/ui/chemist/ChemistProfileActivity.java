package com.koipsool_new.ui.chemist;

import static com.koipsool_new.RetofitSetUp.BaseUrls.USER_IMAGE;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_chemist_image;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.BaseUrls;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityChemistProfileBinding;
import com.koipsool_new.kapsoolModels.ChemistProfileModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChemistProfileActivity extends AppCompatActivity {

    private ChemistProfileActivity activity;
    private ActivityChemistProfileBinding binding;
    private Session session;
    private Uri filePath = null;
    private Bitmap bitmap;
    private File userImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChemistProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);


        binding.icBack.setOnClickListener(view -> onBackPressed());

        if (!session.getImage().equalsIgnoreCase(""))
            Picasso.get().load(session.getImage()).placeholder(R.drawable.user_profile).into(binding.userImage);

        binding.changePhoto.setOnClickListener(view -> ImagePicker.Companion.with(activity)
                .crop()
                .compress(200) //Final image size will be less than 1 MB(Optional)
                .start(100));

        binding.updateProfile.setOnClickListener(view -> startActivity(new Intent(activity, UpdateProfileChemistActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getChemistProfile();

        if (!session.getImage().equalsIgnoreCase(""))
            Picasso.get().load(session.getImage()).placeholder(R.drawable.user_profile).into(binding.userImage);

        binding.userName.setText(session.getUserName());
        binding.userEmail.setText(session.getEmail());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {
            assert data != null;
            filePath = data.getData();
            bitmap = setBitmap();
            userImage = bitmapToFile(activity, bitmap);
            uploadImage();
        }

    }

    private void getChemistProfile() {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getChemistProfile(session.getUserId()).enqueue(new Callback<ChemistProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ChemistProfileModel> call, @NonNull Response<ChemistProfileModel> response) {
                binding.updateProfile.setVisibility(View.VISIBLE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setImage(USER_IMAGE + response.body().getData().get(0).getImage());
                            session.setUserName(response.body().getData().get(0).getName());
                            session.setEmail(response.body().getData().get(0).getEmail());
                            session.setMobile(response.body().getData().get(0).getMobile());

                            binding.userName.setText(response.body().getData().get(0).getName());
                            binding.userEmail.setText(response.body().getData().get(0).getEmail());
                            binding.userState.setText(response.body().getData().get(0).getState());
                            binding.userCity.setText(response.body().getData().get(0).getCity());
                            binding.userAddress.setText(response.body().getData().get(0).getAddress());

                            session.setImage(USER_IMAGE+response.body().getData().get(0).getImage());
                            Log.e("TAG", "onResponse() User Image = [" + session.getImage() + "]");
                            Picasso.get().load(session.getImage()).placeholder(R.drawable.user_profile).into(binding.userImage);

                        }
            }

            @Override
            public void onFailure(@NonNull Call<ChemistProfileModel> call, @NonNull Throwable t) {

            }
        });


    }

    private void uploadImage() {

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.BASEURL + update_chemist_image));
        anAdd.addMultipartParameter("chemist_id", session.getUserId());
        if (userImage != null) anAdd.addMultipartFile("image", userImage);

        anAdd.setPriority(Priority.HIGH);
        anAdd.build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pd.dismiss();
                        try {
                            Log.d("---rrrProfile", "save_postsave_post" + jsonObject.toString());
                            // JSONObject obj = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            pd.dismiss();

                            if (result.equalsIgnoreCase("true")) {
                                Toast.makeText(activity, "Image Updated...", Toast.LENGTH_SHORT).show();
                                binding.userImage.setImageBitmap(bitmap);
                                //getMedicalProfile();
                            } else {
                                Toast.makeText(activity, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            pd.dismiss();
                            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Log.e("TAG", "onError: " + anError);

                    }
                });


    }

    public Bitmap setBitmap() {

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static File bitmapToFile(Context mContext, Bitmap bitmap) {
        try {
            String name = System.currentTimeMillis() + ".png";
            File file = new File(mContext.getCacheDir(), name);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bArr);
            fos.flush();
            fos.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}