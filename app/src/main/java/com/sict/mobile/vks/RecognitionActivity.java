package com.sict.mobile.vks;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import com.sict.mobile.vks.libs.FaceNet;
import com.sict.mobile.vks.libs.LibSVM;
import com.sict.mobile.vks.utils.ImageUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecognitionActivity extends AppCompatActivity implements ImageAnalysis.Analyzer {

    private PreviewView previewView;
    private FaceDetector detector;
    private String MODE = "";
    private ArrayList<float[]> embeddingList;
    private FaceNet faceNet;
    private String TAG = "Recognition activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        previewView = findViewById(R.id.preview_view);

        MODE = getIntent().getStringExtra("MODE");

        FaceDetectorOptions options =
                new FaceDetectorOptions.Builder()
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .build();
        detector = FaceDetection.getClient(options);

        faceNet = FaceNet.create(getAssets());

        startCamera();
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                    Preview preview = new Preview.Builder().build();

                    CameraSelector cameraSelector = new CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                            .build();

                    preview.setSurfaceProvider(previewView.createSurfaceProvider());


                    ImageAnalysis imageAnalysis =
                            new ImageAnalysis.Builder()
                                    .setTargetResolution(new Size(480, 320))
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build();

                    imageAnalysis.setAnalyzer(getMainExecutor(), RecognitionActivity.this);
                    cameraProvider.unbindAll();
                    cameraProvider.bindToLifecycle(RecognitionActivity.this, cameraSelector, preview, imageAnalysis);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));


    }

    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        @SuppressLint("UnsafeExperimentalUsageError") Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
            detector.process(image)
                    .addOnSuccessListener(
                            faces -> {
                                if(faces.size() == 1){
                                    Face face = faces.get(0);
                                    Rect rect = face.getBoundingBox();

//                                    for anti spoofing
//                                    float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
//                                    float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees

                                    FaceLandmark leftEye = face.getLandmark(FaceLandmark.LEFT_EYE);
                                    FaceLandmark rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE);
                                    if (leftEye != null && rightEye != null) {
                                        try {
                                            Bitmap bitmap =
                                                    new ImageUtils(RecognitionActivity.this)
                                                            .yuvToRgb(mediaImage, mediaImage.getWidth(), mediaImage.getHeight());
                                             process(bitmap, rect);
                                        } catch (Throwable throwable) {
                                            throwable.printStackTrace();
                                        }
                                    }
                                }
                                imageProxy.close();
                            })
                    .addOnFailureListener(
                            e -> {
                                e.printStackTrace();
                                imageProxy.close();
                            });
        }
    }

    private void process(Bitmap bitmap, Rect rect){
        if(MODE.equals("recognition")) new Recognizer(getAssets()).recognition(bitmap, rect);
        else if(MODE.equals("add")) {
            if(embeddingList == null) embeddingList = new ArrayList<>();
           float[] buffer = new float[FaceNet.EMBEDDING_SIZE];
            faceNet.getEmbeddings(bitmap, rect).get(buffer);
            if(buffer != null) embeddingList.add(buffer);
            if(embeddingList.size() == 3 ) LibSVM.getInstance().train(5, embeddingList);
            Log.d(TAG, "process: "+buffer[0]);
        }
    }

}