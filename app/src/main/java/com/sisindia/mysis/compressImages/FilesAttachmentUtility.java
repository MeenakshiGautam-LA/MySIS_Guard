package com.sisindia.mysis.compressImages;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AR@SalesMaxx on 11/4/2016.
 */

public class FilesAttachmentUtility {

    public static String getAbsolutePathOfFile(final Context context, final Uri uri) {
        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @return file extensions w.r.t Media URL or URI
     * @author AshuRajput
     */
    public String getFileExtension(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();
        }
    }

    /**
     * @return file name with extensions w.r.t Media URL or URI
     * @author AshuRajput
     */
    public static String getFileNameWithExtension(Uri uri, Context context) {
        String result = null;

        try {

            if (uri != null && uri.getScheme().equals("content")) {
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * @return File MimeType to identify whether file is [Plain text file, excel, document, xlsx, ppt, pdf etc]
     * @author AshuRajput
     */
    private String getFileMimeType(Uri uri, Context context) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }

    //***************************************************************************************************//
    // BELOW METHOD IS USED TO GET REAL PATH FROM URI    //
    //***************************************************************************************************//


    //********************************************************************************//
    // BELOW METHOD IS USED TO GET THE URI WHERE CAPTURED IMAGE FROM CAMERA IS STORED //
    //********************************************************************************//
    private Uri getCapturedPhotoURI(Context context, Bitmap bitmap, String fileName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, fileName, null);
        return Uri.parse(path);
    }

    //**************************************************************************************//
    // BELOW METHOD IS USED TO SAVE THE ATTACHMENT IN SD CARD ON TEMPORARY BASIS            //
    // SAVING, DISPLAYING AND DELETING IMAGE TEMPORARY ARE THE OPERATION PERFORMED          //
    //**************************************************************************************//
    private Uri saveTempAttachmentFilesToFolder(Bitmap scaledBitmap, String fileName) {
        String completeTempImagePath = "";
        try {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "SalesMaxx/MeetingNotes/TemporaryFilesFolder");

            if (!mediaStorageDir.exists()) {
                mediaStorageDir.mkdirs();
            }

            File tempImageFile = new File(mediaStorageDir, fileName);
            OutputStream os = new FileOutputStream(tempImageFile);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            completeTempImagePath = "file://" + tempImageFile.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Uri.fromFile(new File(completeTempImagePath));
    }

    private void saveOriginalAttachmentFilesToFolder(Bitmap scaledBitmap, String attachmentCase, String fileName) {
        File sisStorageDir = null;
        try {
            if (attachmentCase.equals("FILE_ATTACHMENT"))
                sisStorageDir = new File(Environment.getExternalStorageDirectory(), "SalesMaxx/MeetingNotes/FileAttachments");
            else if (attachmentCase.equals("PHOTO_ATTACHMENT"))
                sisStorageDir = new File(Environment.getExternalStorageDirectory(), "SalesMaxx/MeetingNotes/PhotoAttachments");

            if (!sisStorageDir.exists()) {
                sisStorageDir.mkdirs();
            }
            File realImageFile = new File(sisStorageDir, fileName);
            OutputStream os = new FileOutputStream(realImageFile);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return NAME GENERATOR FOR IMAGE TYPE FILES
     * @author AshuRajput
     */
    public static String imageFileNameGenerator(boolean needFileExtension) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if (needFileExtension)
            return "JPEG_" + timeStamp + ".jpg";
        else
            return "JPEG_" + timeStamp;
    }

}
