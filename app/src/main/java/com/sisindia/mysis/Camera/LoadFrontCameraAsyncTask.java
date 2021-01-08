package com.sisindia.mysis.Camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * manages loading the front facing camera off of the main thread
 * can throw an error if no front facing camera
 * or camera has not been released by a naughty app
 */
public class LoadFrontCameraAsyncTask extends AsyncTask<Void, Void, FaceDetectionCamera> {

    private static final String TAG = "FDT" + LoadFrontCameraAsyncTask.class.getSimpleName();

    private final Listener listener;
    private Camera.PictureCallback mPicture;

    public LoadFrontCameraAsyncTask(Listener listener) {
        this.listener = listener;
    }

    public void load() {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected FaceDetectionCamera doInBackground(Void... params) {
        try {
            int id = getFrontFacingCameraId();
            Log.e("camera id...   ",""+id);
            Camera camera = Camera.open(id);
            mPicture = getPictureCallback();
            if (camera.getParameters().getMaxNumDetectedFaces() == 0) {
                Log.e(TAG, "Face detection not supported");
                return null;
            }
//            update daILY_ATTENDANCE set duty_status='DUTY_IN'

            return new FaceDetectionCamera(camera, mPicture);
        } catch (RuntimeException e) {
            Log.e(TAG, "Likely hardware / non released camera / other app fail", e);
            return null;
        }
    }

    private int getFrontFacingCameraId() {
        int i = 0;
        try{

            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

            for (; i < Camera.getNumberOfCameras(); i++) {
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    break;
                }
            }
            return i;
        }catch (Exception e){

        }
        return i;
    }

    @Override
    protected void onPostExecute(FaceDetectionCamera camera) {
        super.onPostExecute(camera);
        if (camera == null) {
            listener.onFailedToLoadFaceDetectionCamera();
        } else {
            listener.onLoaded(camera);
        }
    }

    public interface Listener {
        void onLoaded(FaceDetectionCamera camera);

        void onFailedToLoadFaceDetectionCamera();
    }


    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Log.e("image data  "," Load front camera length of image...   "+data.length);
                Log.e("image bitmap  ","bitmap of image...   "+bitmap);

               /* try {
                    ImageProcessing imageProcessing = new ImageProcessing(myContext);
                    imageProcessing.createImage(pictureFile, data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/
                //refresh camera to continue preview
                //  mPreview.refreshCamera(mCamera);
            }
        };
        return picture;
    }



    public File generateMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Selfie Flashlight");
        if(!mediaStorageDir.exists()){
            mediaStorageDir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Log.e("tag ","file path.. "+mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }



}
