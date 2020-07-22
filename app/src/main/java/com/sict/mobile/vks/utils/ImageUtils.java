package com.sict.mobile.vks.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Utility class for manipulating images.
 **/
public class ImageUtils {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = new Logger();

    static {
        try {
            System.loadLibrary("tensorflow_demo");
        } catch (UnsatisfiedLinkError e) {
            LOGGER.w("Native library not found, native RGB -> YUV conversion may be unavailable.");
        }
    }

    // Always prefer the native implementation if available.
    private static boolean useNativeConversion = true;

    private final RenderScript rs;
    private final ScriptIntrinsicYuvToRGB scriptYuvToRgb;
    private int pixelCount;
    private ByteBuffer yuvBuffer;
    private Allocation inputAllocation;
    private Allocation outputAllocation;

    public ImageUtils(@NotNull Context context) {
        this.rs = RenderScript.create(context);
        this.scriptYuvToRgb = ScriptIntrinsicYuvToRGB.create(this.rs, Element.U8_4(this.rs));
        this.pixelCount = -1;
    }

    public final synchronized Bitmap yuvToRgb(@NotNull Image image, int width, int height) throws Throwable {

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        if (((ImageUtils)this).yuvBuffer == null) {
            this.pixelCount = image.getCropRect().width() * image.getCropRect().height();
            int pixelSizeBits = ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888);
            yuvBuffer = ByteBuffer.allocateDirect(this.pixelCount * pixelSizeBits / 8);
        }

        yuvBuffer.rewind();

        imageToByteBuffer(image, yuvBuffer.array());

        if (((ImageUtils)this).inputAllocation == null) {
            Type elemType = (new Type.Builder(this.rs, Element.YUV(this.rs))).setYuvFormat(17).create();
            this.inputAllocation = Allocation.createSized(rs, elemType.getElement(), yuvBuffer.array().length);
        }

        if (((ImageUtils)this).outputAllocation == null) {
            outputAllocation = Allocation.createFromBitmap(rs, output);
        }
        // Convert NV21 format YUV to RGB
        inputAllocation.copyFrom(yuvBuffer.array());
        scriptYuvToRgb.setInput(inputAllocation);
        scriptYuvToRgb.forEach(outputAllocation);
        outputAllocation.copyTo(output);

        Matrix matrix = new Matrix();
        matrix.postRotate(-90);
//        return Bitmap.createScaledBitmap(Bitmap.createBitmap(output, 0, 0, output.getWidth(), output.getHeight(), matrix, true), 160, 160, false);
        return Bitmap.createBitmap(output, 0, 0, output.getWidth(), output.getHeight(), matrix, true);
    }

    private final void imageToByteBuffer(Image image, byte[] outputBuffer) throws Throwable {

        if(image.getFormat() != ImageFormat.YUV_420_888) throw (Throwable)(new AssertionError("Assertion failed"));

        Rect imageCrop = image.getCropRect();
        Image.Plane[] imagePlanes = image.getPlanes();

        for(int planeIndex = 0; planeIndex < imagePlanes.length; planeIndex++) {
            int outputStride;
            int outputOffset;
            switch(planeIndex) {
                case 0:
                    outputStride = 1;
                    outputOffset = 0;
                    break;
                case 1:
                    outputStride = 2;
                    outputOffset = this.pixelCount + 1;
                    break;
                case 2:
                    outputStride = 2;
                    outputOffset = this.pixelCount;
                    break;
                default:
                    continue;
            }

            ByteBuffer planeBuffer = imagePlanes[planeIndex].getBuffer();
            int rowStride = imagePlanes[planeIndex].getRowStride();
            int pixelStride = imagePlanes[planeIndex].getPixelStride();
            Rect planeCrop = planeIndex == 0
                    ? imageCrop
                    : new Rect(imageCrop.left / 2,
                    imageCrop.top / 2,
                    imageCrop.right / 2,
                    imageCrop.bottom / 2);
            int planeWidth = planeCrop.width();
            int planeHeight = planeCrop.height();
            byte[] rowBuffer = new byte[imagePlanes[planeIndex].getRowStride()];
            int rowLength = pixelStride == 1 && outputStride == 1
                    ? planeWidth : (planeWidth - 1) * pixelStride + 1;


            for( int row = 0; row < planeHeight; row++) {
                planeBuffer.position((row + planeCrop.top) * rowStride + planeCrop.left * pixelStride);
                if (pixelStride == 1 && outputStride == 1) {
                    planeBuffer.get(outputBuffer, outputOffset, rowLength);
                    outputOffset += rowLength;
                } else {
                    planeBuffer.get(rowBuffer, 0, rowLength);
                    int col = 0;

                    for(int var28 = planeWidth; col < var28; ++col) {
                        outputBuffer[outputOffset] = rowBuffer[col * pixelStride];
                        outputOffset += outputStride;
                    }
                }
            }
        }

    }

    public static void prewhiten(float[] input, FloatBuffer output) {
        if (useNativeConversion) {
            try {
                ImageUtils.prewhiten(input, input.length, output);
                return;
            } catch (UnsatisfiedLinkError e) {
                LOGGER.w(
                        "Native prewhiten implementation not found, falling back to Java implementation");
                useNativeConversion = false;
            }
        }

        double sum = 0f;
        for (float value : input) {
            sum += value;
        }
        double mean = sum / input.length;
        sum = 0f;

        for (int i = 0; i < input.length; ++i) {
            input[i] -= mean;
            sum += Math.pow(input[i], 2);
        }
        double std = Math.sqrt(sum / input.length);
        double std_adj = Math.max(std, 1.0 / Math.sqrt(input.length));

        output.clear();
        for (float value : input) {
            output.put((float) (value / std_adj));
        }
        output.rewind();
    }

    private static native float prewhiten(float[] input, int length, FloatBuffer output);
}
