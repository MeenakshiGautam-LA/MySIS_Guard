//package com.sisindia.guardattendance.asynctask;
//
//import android.app.IntentService;
//import android.content.ContentValues;
//import android.content.Intent;
//import android.net.Uri;
//import android.util.Log;
//
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.crashlytics.android.Crashlytics;
//import com.sisindia.guardattendance.application.CSApplicationHelper;
//import com.sisindia.guardattendance.utils.Constants;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//
///**
// * Created by Ashu Rajput on 11/30/2016.
// */
//
//public class MultipleEmailAttachmentService extends IntentService {
//
//    public MultipleEmailAttachmentService() {
//        super(MultipleEmailAttachmentService.class.getName());
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        try {
//            int index = intent.getIntExtra("INDEX", 0);
//            final String emailGUId = intent.getStringExtra("EMAIL_GUID");
//
//
//                ProfilePictureMultipartsRequest imageUploadReq = new ProfilePictureMultipartsRequest(Constants.BASE_URL,
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e("EmailBGService", "Service $$$$$ Multiple Attatachmet failed area");
//
////                                ContentValues attachmentCV = new ContentValues();
////                                attachmentCV.put("ID", documentId);
////                                attachmentCV.put("dirty_flag", 1);
////                                attachmentCV.put("document_type", "EMAIL");
////                                attachmentCV.put("document_name", attachmentFileName);
////                                attachmentCV.put("document_path", attachmentFilePathName);
////                                attachmentCV.put("parent_ID", emailGUId);
////                                attachmentCV.put("modified_date", SalesMaxxUtility.getServerFormatDateWithoutMilliSeconds());
////                                databaseManager.insert(TABLE_DOCUMENTS, attachmentCV);
//
////                                ArrayList<String> id = EmailAttachmentSingleton.getInstance().getFailureAttachmentId();
////                                id.add(documentId);
////                                EmailAttachmentSingleton.getInstance().setFailureAttachmentId(id);
////
////                                //***************BROADCASTING THE MESSAGE TO NOTIFY, FAILED TO UPLOAD ATTACHMENT TO SERVER*****************//
////                                LocalBroadcastManager.getInstance(MultipleEmailAttachmentService.this).
////                                        sendBroadcast(new Intent("com.ar.upload_attachment").putExtra("BROADCAST_MESSAGE", false));
////                                AppController.getInstance().addErrorLog(error.toString(), error.getStackTrace()[error.getStackTrace().length - 1].getLineNumber(), MultipleEmailAttachmentService.this.getClass().getSimpleName());
//
//                            }
//                        }, new File(attachmentFilePathName), "testing",
//
//                        new ProfilePictureMultipartsRequest.ProfilePicUpdateListener() {
//                            @Override
//                            public void onProfilePicUpdateSuccessfully(String response) {
//
////                                Log.e("EmailBGService", "Service UUID Id:" + documentId);
//                                Log.e("EmailBGService", "Service Server Returned Successs ID :" + response);
//
////                                ContentValues attachmentCV = new ContentValues();
////                                attachmentCV.put("ID", response);
////                                attachmentCV.put("dirty_flag", 0);
////                                attachmentCV.put("document_type", "EMAIL");
////                                attachmentCV.put("document_name", attachmentFileName);
////                                attachmentCV.put("document_path", attachmentFilePathName);
////                                attachmentCV.put("parent_ID", emailGUId);
////                                attachmentCV.put("modified_date", SalesMaxxUtility.getServerFormatDateWithoutMilliSeconds());
////
////                                databaseManager.insert(TABLE_DOCUMENTS, attachmentCV);
////
////                                ArrayList<String> getSuccessfulUploadedIdList = EmailAttachmentSingleton.getInstance().getSuccessfulAttachmentId();
////                                getSuccessfulUploadedIdList.add(response);
////                                EmailAttachmentSingleton.getInstance().setSuccessfulAttachmentId(getSuccessfulUploadedIdList);
////
////                                //***************BROADCASTING THE MESSAGE TO NOTIFY, SUCCESSFUL UPLOAD OF ATTACHMENT TO SERVER*****************//
////                                LocalBroadcastManager.getInstance(MultipleEmailAttachmentService.this).
////                                        sendBroadcast(new Intent("com.ar.upload_attachment").putExtra("BROADCAST_MESSAGE", true));
//                            }
//                        }, documentId, attachmentFileName);
//                CSApplicationHelper.application().addToRequestQueue(imageUploadReq,"");
////                AppController.getInstance().addToRequestQueue(imageUploadReq);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Crashlytics.logException(e);
//        }
//    }
//}
