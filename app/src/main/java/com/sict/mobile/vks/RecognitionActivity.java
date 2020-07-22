package com.sict.mobile.vks;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

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

import com.developer.kalert.KAlertDialog;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import com.sict.mobile.vks.adapter.AdapterToday;
import com.sict.mobile.vks.interfaces.CallBackListener;
import com.sict.mobile.vks.libs.FaceNet;
import com.sict.mobile.vks.libs.LibSVM;
import com.sict.mobile.vks.utils.APIUtils;
import com.sict.mobile.vks.utils.FileUtils;
import com.sict.mobile.vks.utils.ImageUtils;
import com.sict.mobile.vks.utils.UserUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecognitionActivity extends AppCompatActivity implements ImageAnalysis.Analyzer {
    private static final  float MIN_PROB = 0.7f;
    private PreviewView previewView;
    private FaceDetector detector;
    private String MODE = "";
    private ArrayList<float[]> embeddingList;
    private FaceNet faceNet;
    private String TAG = "Recognition activity";
    private LibSVM svm;
    private ArrayList<LibSVM.Prediction> predictList = new ArrayList<>();
    private KAlertDialog pDialog;
    private ProcessCameraProvider cameraProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        previewView = findViewById(R.id.preview_view);

        MODE = getIntent().getStringExtra("MODE");
        if(MODE.equals("add"))
            Toast.makeText(this, "Chưa có dữ liệu, bắt đầu thêm", Toast.LENGTH_SHORT).show();
        FaceDetectorOptions options =
                new FaceDetectorOptions.Builder()
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .build();
        detector = FaceDetection.getClient(options);

        faceNet = FaceNet.create(getAssets());

        getSupportActionBar().hide();
        svm = LibSVM.getInstance();

        pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Đang chuẩn bị dữ liệu");
        pDialog.setCancelable(false);

        if(MODE.equals("recognition")) {
            pDialog.show();
            APIUtils.getAPI().getModel().enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "server contacted and has file");

                        boolean writtenToDisk = FileUtils.writeResponseBodyToDisk(response.body());
                        UserUtils.setHasData();
                        pDialog.cancel();
                        startCamera();

                        Log.d(TAG, "file download was a success? " + writtenToDisk);
                    } else {
                        Log.d(TAG, "server contact failed");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
        else{
            startCamera();
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void run() {
                try {
                    cameraProvider = cameraProviderFuture.get();

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
                } catch (ExecutionException | InterruptedException e) {
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
        if(MODE.equals("recognition")) {
            FloatBuffer buffer = faceNet.getEmbeddings(bitmap, rect);
            if(predictList.size() < 10) predictList.add(svm.predict(buffer));
            else calculateResult();


        }
        else if(MODE.equals("add")) {
            if(embeddingList == null) embeddingList = new ArrayList<>();
            float[] buffer = new float[FaceNet.EMBEDDING_SIZE];
            faceNet.getEmbeddings(bitmap, rect).get(buffer);
            embeddingList.add(buffer);

            if(embeddingList.size() == 10 ) {
                int id = UserUtils.getId();
                if(id != -1)
                    LibSVM.getInstance().train(id, embeddingList, new CallBackListener() {
                        @Override
                        public void onDone() {
                            if(pDialog != null)pDialog.cancel();
                            MODE = "recognition";
                        }

                        @Override
                        public void onDone(Object obj) {

                        }

                        @Override
                        public void onError(String message) {
                            new KAlertDialog(RecognitionActivity.this, KAlertDialog.ERROR_TYPE)
                                    .setTitleText("Có lỗi xuất hiện, thử lại sau")
                                    .setCancelText("OK")
                                    .showCancelButton(true)
                                    .show();
                        }
                    });
            }
        }
    }

    private void calculateResult() {
        int id = UserUtils.getId(),
            countMatch = 0;
        boolean isMatch = false;
        for(LibSVM.Prediction prediction : predictList){
            if(prediction.getIndex() == id && (1-prediction.getProb()) > MIN_PROB) countMatch++;
        }
        isMatch = countMatch > 6;
        Intent data = new Intent();
        data.putExtra("POSITION", getIntent().getIntExtra("POSITION", -1));
        data.putExtra("isMatch", isMatch);
        setResult(AdapterToday.REQUEST_CODE_RECOG, data);
        onStop();
        super.onBackPressed();

        Log.d(TAG, "calculateResult: "+countMatch);
    }

    @Override
    protected void onStop() {
        if(faceNet != null) faceNet.close();
        cameraProvider.unbindAll();
        detector.close();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(faceNet != null) faceNet.close();
        cameraProvider.unbindAll();
        detector.close();
        super.onDestroy();
    }
}