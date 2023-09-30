package com.koipsool_new;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.google.mlkit.vision.objects.DetectedObject;

import java.io.IOException;
import java.util.List;

public class ImageLabelActivity extends AppCompatActivity {
    ImageLabeler labeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_label);

        labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            InputImage image;
            try {
                assert data != null;
                image = InputImage.fromFilePath(ImageLabelActivity.this, data.getData());
                processImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processImage(InputImage image) {
        labeler.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> labels) {
                        Log.e("TAG", "onSuccess: ");
                        for (ImageLabel label : labels) {
                            String text = label.getText();
                            float confidence = label.getConfidence();
                            int index = label.getIndex();
                            Log.e("TAG", "onSuccess: Text  " + text);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                        Log.e("TAG", "onFailed: " + e.getLocalizedMessage());
                    }
                });

    }

}