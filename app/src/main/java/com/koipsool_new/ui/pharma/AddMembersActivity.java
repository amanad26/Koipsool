package com.koipsool_new.ui.pharma;

import static com.koipsool_new.RetofitSetUp.BaseUrls.add_company_members;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.koipsool_new.RetofitSetUp.BaseUrls;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityAddMembersBinding;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddMembersActivity extends AppCompatActivity {
    private ActivityAddMembersBinding binding;
    private Session session;
    private AddMembersActivity activity;
    private String candidate_id = "";
    private Uri filepath = null;
    private Bitmap bitmap;
    private File userImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMembersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.confirmBtn.setOnClickListener(view -> {
            if (validate())
                addMember();
        });

        binding.userImage.setOnClickListener(view -> ImagePicker.Companion.with(activity)
                .crop()
                .compress(200) //Final image size will be less than 1 MB(Optional)
                .start(100));

      }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            assert data != null;

            if (requestCode == 100) {
                filepath = data.getData();
                bitmap = setBitmap(binding.userImage);
                userImage = bitmapToFile(activity, bitmap);
            }

        }


    }


    public Bitmap setBitmap(ImageView imageView) {

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
            imageView.setImageBitmap(bitmap);

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

    private void addMember() {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();

        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.BASEURL + add_company_members));
        anAdd.addMultipartParameter("company_id", session.getUserId());
        anAdd.addMultipartParameter("name", binding.memberName.getText().toString());
        anAdd.addMultipartParameter("department", binding.memberDepartment.getText().toString());
        anAdd.addMultipartParameter("location", candidate_id);
        anAdd.addMultipartParameter("work", binding.memberWorkRole.getText().toString());
        if (userImage != null) anAdd.addMultipartFile("image", userImage);


        anAdd.setPriority(Priority.HIGH);
        anAdd.build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            Log.d("---rrrProfile", "save_postsave_post" + jsonObject.toString());
                            // JSONObject obj = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            progressDialog.dismiss();

                            if (result.equalsIgnoreCase("true")) {
                                Toast.makeText(activity, "Member Added ", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(activity, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("TAG", "onError: " + anError);

                    }
                });
    }

    private boolean validate() {

        if (binding.memberDepartment.getText().toString().equalsIgnoreCase("")) {
            binding.memberDepartment.setError("Enter Department..!");
            binding.memberDepartment.requestFocus();
            return false;
        } else if (binding.memberName.getText().toString().equalsIgnoreCase("")) {
            binding.memberName.setError("Enter Name..!");
            binding.memberName.requestFocus();
            return false;
        } else if (binding.memberWorkRole.getText().toString().equalsIgnoreCase("")) {
            binding.memberWorkRole.setError("Enter Work role ..!");
            binding.memberWorkRole.requestFocus();
            return false;
        } else if (userImage == null) {
            Toast.makeText(activity, "Select Member Image..!", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            if (binding.memberLocationCandidateId.getText().toString().equalsIgnoreCase(""))
                candidate_id = "0";
            else candidate_id = binding.memberLocationCandidateId.getText().toString();


            return true;
        }

    }
}