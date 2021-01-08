package com.sisindia.mysis.Camera;

import android.graphics.Matrix;
import android.hardware.Camera;

/**
 * Manage the android face detection callbacks to be more ON, OFF than real time ON ON ON ON OFF
 */
public class OneShotFaceDetectionListener implements Camera.FaceDetectionListener {

    private static final int UPDATE_SPEED = 250;
    private static final int UPDATE_SPEED_UNITS = 1000;

    private final Listener listener;

    Matrix mFaceDetectionMatrix;
    private boolean timerComplete = true;

    OneShotFaceDetectionListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * The Android API call's this method over and over when a face is detected
     * the idea here is that we de-bounce all these calls, so that we get 1 callback when
     * a face is detected and 1 callback when it is lost
     * <p/>
     * i.e.
     * face, face, face, face, face, no face, face, face, face
     * becomes
     * face, no face, face
     */
    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if (faces.length == 0) {
            return;
        }

        tickFaceDetectionSession();
        if (sameFaceDetectionSession()) {
            return;
        }
        startFaceDetectionSession();
        listener.onFaceDetected();
    }

    private RestartingCountDownTimer tickFaceDetectionSession() {
        return timer.startOrRestart();
    }

    private boolean sameFaceDetectionSession() {
        return !timerComplete;
    }

    private void startFaceDetectionSession() {
        timerComplete = false;
    }

    private RestartingCountDownTimer timer = new RestartingCountDownTimer(UPDATE_SPEED, UPDATE_SPEED_UNITS) {
        @Override
        public void onFinish() {
            completeFaceDetectionSession();
            listener.onFaceTimedOut();
        }
    };

    private void completeFaceDetectionSession() {
        timerComplete = true;
    }

    interface Listener {
        void onFaceDetected();

        void onFaceTimedOut();
    }


  /*  private void setFaceDetectionMatrix() {
        int orientationOffset = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        Rect activeArraySizeRect = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);

        // Face Detection Matrix
        mFaceDetectionMatrix = new Matrix();
        // Need mirror for front camera.
        float s1 = mPreviewSize.getWidth() / (float)activeArraySizeRect.width();
        float s2 = mPreviewSize.getHeight() / (float)activeArraySizeRect.height();
//float s1 = mOverlayView.getWidth();
//float s2 = mOverlayView.getHeight();
        boolean mirror = (facing == CameraCharacteristics.LENS_FACING_FRONT); // we always use front face camera
        boolean weAreinPortrait = true;
        int offsetDxDy = 100;
        if (mSwappedDimensions) {
            // Display Rotation 0
            mFaceDetectionMatrix.setRotate(270);
            mFaceDetectionMatrix.postScale(mirror ? -s1 : s1, s2);
            mFaceDetectionMatrix.postTranslate(mPreviewSize.getHeight() + offsetDxDy, mPreviewSize.getWidth() + offsetDxDy);
        } else {
            // Display Rotation 90 e 270
            if (displayRotation == Surface.ROTATION_90) {
                mFaceDetectionMatrix.setRotate(0);
                mFaceDetectionMatrix.postScale(mirror ? -s1 : s1, s2);
                mFaceDetectionMatrix.postTranslate(mPreviewSize.getWidth() + offsetDxDy, -offsetDxDy);
            } else if (displayRotation == Surface.ROTATION_270) {
                mFaceDetectionMatrix.setRotate(180);
                mFaceDetectionMatrix.postScale(mirror ? -s1 : s1, s2);
                mFaceDetectionMatrix.postTranslate(-offsetDxDy, mPreviewSize.getHeight() + offsetDxDy);
            }
        }
    }*/
}
