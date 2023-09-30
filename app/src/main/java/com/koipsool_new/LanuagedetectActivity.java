package com.koipsool_new;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

public class LanuagedetectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanuagedetect);

        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(" छपाई और अक्षर योजन उद्योग का एक साधारण डमी पाठ है. Lorem Ipsum सन १५०० के बाद से अभी तक ...")
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@Nullable String languageCode) {
                                if (languageCode.equals("und")) {
                                    Log.e("TAG", "Can't identify language.");
                                } else {
                                    Log.e("TAG", "Language: " + languageCode);
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be loaded or other internal error.
                                // ...
                                Log.e("TAG", "onFailure: "+e.getLocalizedMessage() );
                            }
                        });


    }


}