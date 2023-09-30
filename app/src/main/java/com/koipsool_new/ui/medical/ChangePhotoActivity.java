package com.koipsool_new.ui.medical;

import static com.koipsool_new.RetofitSetUp.BaseUrls.update_medical_image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.tabs.TabLayoutMediator;
import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.BaseUrls;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.adapter.ChangePhotoViewPagerAdapter;
import com.koipsool_new.databinding.ActivityChangePhotoBinding;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.model.MedicalProfileModel;
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

public class ChangePhotoActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityChangePhotoBinding binding;
    private File userImage = null;
    private Uri filePath = null;
    private Session session;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);


        binding.icBack.setOnClickListener(view -> onBackPressed());

        Log.e("TAG", "onCreate() called with: session.getImage() = [" + session.getImage() + "]");

        if (!session.getImage().equalsIgnoreCase(""))
            Picasso.get().load(session.getImage()).placeholder(R.drawable.user_profile).into(binding.userImage);

        binding.changePhoto.setOnClickListener(view -> ImagePicker.Companion.with(activity)
                .crop()
                .compress(200) //Final image size will be less than 1 MB(Optional)
                .start(100));


        ChangePhotoViewPagerAdapter adapter = new ChangePhotoViewPagerAdapter(ChangePhotoActivity.this);
        binding.CreatTeamViewpager.setAdapter(adapter);

        String[] name = new String[]{"Basic Detail", "Resume"};
        new TabLayoutMediator(binding.tabLay, binding.CreatTeamViewpager, (tab, position) -> tab.setText(name[position])).attach();

    }


    private void uploadImage() {

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.BASEURL + update_medical_image));
        anAdd.addMultipartParameter("medical_id", session.getUserId());
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
                                getMedicalProfile();
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

    private void getMedicalProfile() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMedicalProfile(session.getUserId()).enqueue(new Callback<MedicalProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<MedicalProfileModel> call, @NonNull Response<MedicalProfileModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setImage(response.body().getPath() + response.body().getData().get(0).getImage());
                            session.setUserName(response.body().getData().get(0).getName());
                        }

            }

            @Override
            public void onFailure(@NonNull Call<MedicalProfileModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


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