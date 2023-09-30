package com.koipsool_new.ui.pharma;

import static com.koipsool_new.RetofitSetUp.BaseUrls.USER_IMAGE;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_company_profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.BaseUrls;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.R;
import com.koipsool_new.Session.Session;
import com.koipsool_new.adapter.ProfilepageViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.koipsool_new.kapsoolModels.CompanyProfileModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ImageView ic_back;
    TabLayout tab_lay;
    ViewPager2 CreatTeam_viewpager;
    TextView change_phpto;
    CircleImageView image_img;
    private Uri filepath = null;
    private Bitmap bitmap;
    private File userImage = null;
    Session session;
    ProfileActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activity = this;
        session = new Session(activity);

        tab_lay = findViewById(R.id.tab_lay);
        CreatTeam_viewpager = findViewById(R.id.CreatTeam_viewpager);
        change_phpto = findViewById(R.id.change_phpto);
        image_img = findViewById(R.id.image_img);

        ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(view -> onBackPressed());

        change_phpto.setOnClickListener(view -> ImagePicker.Companion.with(activity)
                .crop()
                .compress(200) //Final image size will be less than 1 MB(Optional)
                .start(100));

        image_img.setOnClickListener(view -> ImagePicker.Companion.with(activity)
                .crop()
                .compress(200) //Final image size will be less than 1 MB(Optional)
                .start(100));

        ProfilepageViewPagerAdapter adapter = new ProfilepageViewPagerAdapter(ProfileActivity.this);
        CreatTeam_viewpager.setAdapter(adapter);

        String[] name = new String[]{"Basic Detail", "Billing Address"};
        new TabLayoutMediator(tab_lay, CreatTeam_viewpager, (tab, position) -> tab.setText(name[position])).attach();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {
            assert data != null;
            filepath = data.getData();
            bitmap = setBitmap();
            userImage = bitmapToFile(activity, bitmap);
            uploadImage();
        }


    }

    private void getCompanyProfile() {
        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getCompanyProfile(session.getUserId()).enqueue(new Callback<CompanyProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<CompanyProfileModel> call, @NonNull Response<CompanyProfileModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            CompanyProfileModel.CompanyProfileData data = response.body().getData().get(0);
                            session.setImage(USER_IMAGE + data.getImage());

                            Log.e("TAG", "onResponse() called with: Data  = [" + data.toString() + "]");

                        }
            }

            @Override
            public void onFailure(@NonNull Call<CompanyProfileModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }


    private void uploadImage() {

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.BASEURL + update_company_profile));
        anAdd.addMultipartParameter("company_id", session.getUserId());
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
                                image_img.setImageBitmap(bitmap);
                                getCompanyProfile();
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



    @Override
    protected void onResume() {
        super.onResume();
        if (!session.getImage().equalsIgnoreCase("")) {
            Picasso.get().load(session.getImage()).placeholder(R.drawable.profile).into(image_img);
        }
    }


    public Bitmap setBitmap() {

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}