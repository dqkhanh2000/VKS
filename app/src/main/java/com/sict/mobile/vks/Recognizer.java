package com.sict.mobile.vks;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.sict.mobile.vks.MainActivity;
import com.sict.mobile.vks.libs.FaceNet;
import com.sict.mobile.vks.libs.LibSVM;

import java.nio.FloatBuffer;

public class Recognizer {
    private static final String TAG = "Recognition";
    private FaceNet faceNet;
    private LibSVM svm;

    public Recognizer(AssetManager assetManager) {
        this.faceNet = FaceNet.create(assetManager);
        this.svm = LibSVM.getInstance();
    }

}
