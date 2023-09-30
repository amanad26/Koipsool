package com.koipsool_new;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import com.google.mlkit.vision.objects.defaults.PredefinedCategory;

import java.io.IOException;
import java.util.List;

public class ObjectDetectActivity extends AppCompatActivity {
    ObjectDetector objectDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detect);


//        ObjectDetectorOptions options =
//                new ObjectDetectorOptions.Builder()
//                        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
//                        .enableClassification()  // Optional
//                        .build();


        ObjectDetectorOptions options =
                new ObjectDetectorOptions.Builder()
                        .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                        .enableMultipleObjects()
                        .enableClassification()  // Optional
                        .build();


        objectDetector = ObjectDetection.getClient(options);

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
                image = InputImage.fromFilePath(ObjectDetectActivity.this, data.getData());
                processImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processImage(InputImage image) {
        objectDetector.process(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<DetectedObject>>() {
                            @Override
                            public void onSuccess(List<DetectedObject> detectedObjects) {
                                // Task completed successfully
                                // ...
                                Log.e("TAG", "onSuccess() called with: detectedObjects = [" + detectedObjects.size() + "]");


                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                                Log.e("TAG", "onFailure: " + e.getLocalizedMessage());
                            }
                        });

    }
}