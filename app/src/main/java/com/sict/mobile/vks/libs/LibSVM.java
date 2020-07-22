package com.sict.mobile.vks.libs;

import android.text.TextUtils;
import android.util.Log;

import com.sict.mobile.vks.interfaces.CallBackListener;
import com.sict.mobile.vks.model.TrainData;
import com.sict.mobile.vks.utils.APIUtils;
import com.sict.mobile.vks.utils.FileUtils;
import com.sict.mobile.vks.utils.UserUtils;

import java.io.File;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yctung on 9/26/17.
 * This is a java wrapper of LibSVM
 */

public class LibSVM {
    private static final String TAG = "LibSVM";
    private String LOG_TAG = "LibSVM";
    public static String MODEL_PATH = FileUtils.ROOT + File.separator + FileUtils.MODEL_FILE;

    private int index;
    private double prob;

    public class Prediction {
        private int index;
        private float prob;

        Prediction(int index, float prob) {
            this.index = index;
            this.prob = prob;
        }
        public int getIndex() {
            return index;
        }
        public float getProb() {
            return prob;
        }
    }

    static {
        System.loadLibrary("jnilibsvm");
    }

    // connect the native functions
    private native void jniSvmPredict(String cmd, FloatBuffer buf, int len);

    private void predict(String cmd, FloatBuffer buf, int len) {
        jniSvmPredict(cmd, buf, len);
    }

    public void train(int label, ArrayList<float[]> list, CallBackListener callBackListener) {
        StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator());
        for (int i = 0; i < list.size(); i++) {
            float[] array = list.get(i);
            builder.append(label);
            for (int j = 0; j < array.length; j++) {
                String f = String.format("%.6f", array[j]).replace(',','.');
                builder.append(" ").append(j).append(":").append(f);
            }
            if (i < list.size() - 1) builder.append(System.lineSeparator());
        }
        String s = builder.toString();

        APIUtils.getAPI().trainInServer(label, s).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");

                    boolean writtenToDisk = FileUtils.writeResponseBodyToDisk(response.body());
                    UserUtils.setHasData();
                    callBackListener.onDone();

                    Log.d(TAG, "file download was a success? " + writtenToDisk);
                } else {
                    Log.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                callBackListener.onError(t.getMessage());
            }
        });

    }

    public Prediction predict(FloatBuffer buffer) {
        String options = "-b 1";
        String cmd = TextUtils.join(" ", Arrays.asList(options, MODEL_PATH));

        predict(cmd, buffer, FaceNet.EMBEDDING_SIZE);
        return new Prediction(index, (float) prob);
    }

    private static LibSVM svm;
    public static LibSVM getInstance() {
        if (svm == null) {
            svm = new LibSVM();
        }
        return svm;
    }

    private LibSVM() {
        Log.d(LOG_TAG, "LibSVM init");
    }
}
