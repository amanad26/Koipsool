package com.koipsool_new.RetofitSetUp;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;

public class CustomTextview extends View implements ViewTreeObserver.OnPreDrawListener {

    public CustomTextview(Context context) {
        super(context);
    }

    @Override
    public boolean onPreDraw() {
        return false;
    }


}
