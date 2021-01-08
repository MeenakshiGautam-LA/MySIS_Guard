package com.sisindia.mysis.Camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.List;

/**
 * Manages the android camera and sets it up for face detection
 * can throw an error if face detection is not supported on this device
 */
public class FaceDetectionCamera implements OneShotFaceDetectionListener.Listener {

    Camera camera;

    private Listener listener;
    private Camera.PictureCallback mPicture;

    public FaceDetectionCamera(Camera camera, Camera.PictureCallback mPicture) {
        this.camera = camera;
        this.mPicture = mPicture;
    }

    /**
     * Use this to detect faces when you have a custom surface to display upon
     *
     * @param listener    the {@link Listener} for when faces are detected
     * @param holder      the {@link SurfaceHolder} to display upon
     * @param frameLayout
     */
    public void initialise(Listener listener, SurfaceHolder holder, FrameLayout frameLayout) {
        this.listener = listener;
        try {
            camera.stopPreview();
        } catch (Exception swallow) {
            swallow.printStackTrace();
        }
        //  try {
        /*camera.setPreviewDisplay(holder);
        camera.startPreview();
        camera.setFaceDetectionListener(new OneShotFaceDetectionListener(this));
        camera.startFaceDetection();
        camera.setDisplayOrientation(90);*/
        setCameraParameter(holder, frameLayout);
        // refreshCamera(holder);
        /*} catch (IOException e) {
            this.listener.onFaceDetectionNonRecoverableError();
        }*/
    }

    @Override
    public void onFaceDetected() {
        listener.onFaceDetected(camera);

    }

    @Override
    public void onFaceTimedOut() {
        listener.onFaceTimedOut();
    }

    public void recycle() {
        if (camera != null) {
            camera.release();
        }
    }

    public interface Listener {
        void onFaceDetected(Camera camera);

        void onFaceTimedOut();

        void onFaceDetectionNonRecoverableError();

    }

    public void setCameraParameter(SurfaceHolder holder, FrameLayout frameLayout) {


        try {

            camera.setPreviewDisplay(holder);
            camera.startPreview();
            camera.setFaceDetectionListener(new OneShotFaceDetectionListener(this));
            camera.startFaceDetection();
            camera.setDisplayOrientation(90);
        } catch (IOException ioe) {
            this.listener.onFaceDetectionNonRecoverableError();
        }

    }

    public void previewCamera(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
//            isPreviewRunning = true;
        } catch (Exception e) {
            e.printStackTrace();
//            Log.d(APP_CLASS, "Cannot start preview", e);
        }
    }


    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }


}
