//package com.sisindia.guardattendance;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.sisindia.guardattendance.activity.MarkDuty_Confirmation_ViewScreen;
//import com.google.android.gms.vision.CameraSource;
//import com.google.android.gms.vision.Tracker;
//import com.google.android.gms.vision.face.Face;
//import com.google.android.gms.vision.face.FaceDetector;
//import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;
//
//import java.io.IOException;
//
//public class SelfieActivity extends AppCompatActivity {
//    private static final int MY_CAMERA_REQUEST_CODE = 100;
//    private SurfaceView surfaceView;
//    private CameraSource cameraSource;
//    private SurfaceHolder surfaceHolder;
//    private FaceDetector detector;
//
//    TextView retake_TV, submit_TV;
//    ImageView cancelBtn;
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.capture_image_of_user_layout);
//
//        surfaceView = findViewById(R.id.surfaceView);
//        retake_TV = findViewById(R.id.retake_TV);
//        submit_TV = findViewById(R.id.submit_TV);
//        cancelBtn = findViewById(R.id.cancelBtn);
//
//
//        detector = new FaceDetector.Builder(this)
//                .setProminentFaceOnly(true) // optimize for single, relatively large face
//                .setTrackingEnabled(true) // enable face tracking
//                .setClassificationType(/* eyes open and smile */ FaceDetector.ALL_CLASSIFICATIONS)
//                .setMode(FaceDetector.FAST_MODE) // for one face this is OK
//                .build();
//
//        if (!detector.isOperational()) {
//            Log.w("MainActivity", "Detector Dependencies are not yet available");
//        } else {
//            Log.w("MainActivity", "Detector Dependencies are available");
//            if (surfaceView != null) {
//                boolean result = checkPermission();
//                if (result) {
//                    setViewVisibility(R.id.captureLY);
//                    setViewVisibility(R.id.surfaceView);
//                    setupSurfaceHolder();
//                }
//            }
//        }
//
//        findViewById(R.id.captureLY).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickImage();
//            }
//        });
//
//        submit_TV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SelfieActivity.this, MarkDuty_Confirmation_ViewScreen.class));
//                finish();
//            }
//        });
//        cancelBtn.setOnClickListener(view -> onBackPressed());
//
//        retake_TV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                findViewById(R.id.surfaceView).setVisibility(View.VISIBLE);
//                findViewById(R.id.bottomLinearView).setVisibility(View.VISIBLE);
//                findViewById(R.id.retakeSubmitLinear).setVisibility(View.GONE);
//                findViewById(R.id.viewCaptureImageIV).setVisibility(View.GONE);
//            }
//        });
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public boolean checkPermission() {
//        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
//        }
//        return true;
//    }
//
//    private void requestPermissions() {
//        //  ActivityCompat.requestPermissions(MainActivity.this, permissions, 1001);
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.CAMERA},
//                100);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 100:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
//
//                    // main logic
//                } else {
//                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                                != PackageManager.PERMISSION_GRANTED) {
//                            showMessageOKCancel("You need to allow access permissions",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions();
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                }
//                break;
//        }
//    }
//
//    private void setViewVisibility(int id) {
//        View view = findViewById(id);
//        if (view != null) {
//            view.setVisibility(View.VISIBLE);
//        }
//    }
//
//
//    private void setupSurfaceHolder() {
//        cameraSource = new CameraSource.Builder(this, detector)
//                .setFacing(CameraSource.CAMERA_FACING_FRONT)
//                .setRequestedFps(2.0f)
//                .setAutoFocusEnabled(true)
//                .build();
//
//        surfaceHolder = surfaceView.getHolder();
//        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                try {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                            return;
//                        }
//                    }
//                    cameraSource.start(surfaceHolder);
//                    detector.setProcessor(new LargestFaceFocusingProcessor(detector,
//                            new Tracker<Face>()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                cameraSource.stop();
//            }
//        });
//    }
//
//    private void clickImage() {
//        if (cameraSource != null) {
//            cameraSource.takePicture(null, new CameraSource.PictureCallback() {
//                @Override
//                public void onPictureTaken(byte[] bytes) {
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                    ((ImageView) findViewById(R.id.viewCaptureImageIV)).setImageBitmap(bitmap);
//                    findViewById(R.id.surfaceView).setVisibility(View.GONE);
//                    findViewById(R.id.bottomLinearView).setVisibility(View.GONE);
//                    findViewById(R.id.retakeSubmitLinear).setVisibility(View.VISIBLE);
//                    setViewVisibility(R.id.viewCaptureImageIV);
//                }
//            });
//        }
//    }
//
//
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(SelfieActivity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//}
