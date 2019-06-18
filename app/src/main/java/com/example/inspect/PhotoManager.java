package com.example.inspect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;


public class PhotoManager extends AppCompatActivity {

    private ImageView imageView;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    private String currentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_manager);
        configureBackBtn();
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTOMANAGER", "takePhoto");

        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        Button btnGallery = (Button)findViewById(R.id.btnGallery);
        imageView = (ImageView)findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Context context = App.getContext();
        //data.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            LogManager.reportStatus(context, "PHOTOMANAGER", "imagePickedFromGallery");
        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            LogManager.reportStatus(context, "PHOTOMANAGER", "gotPhotoFromCamera");
        }
        else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            if(data.getData() != null) {
                imageUri = data.getData();
            }
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
                LogManager.reportStatus(context, "PHOTOMANAGER", "gotUriFromCamera");
            }
            else{
                LogManager.reportStatus(context, "PHOTOMANAGER", "uriFromDataIsNull");
            }
        }
        else if (requestCode != RESULT_OK){
            LogManager.reportStatus(context, "PHOTOMANAGER", "couldNotGetPhoto");
        }
    }

    /**
     * Allows you to get photo from image gallery.
     */
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTOMANAGER", "getPhotoFromGallery");
    }

    /**
     * Create an image file
     * @return
     */
    private File createImageFile() {
        Context context = App.getContext();
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Inspect_" + timeStamp + "_.jpg";
        File storageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images");
        // Create the storage directory if it does not exist
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                LogManager.reportStatus(context, "PHOTOMANAGER", "failedToCreateDirectory");
                return null;
            }
        }
        // Create file
        File image = new File(storageDir.getPath() + File.separator + imageFileName);
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * Generate the intent and send
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Context context = App.getContext();
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = createImageFile();
            LogManager.reportStatus(context, "PHOTOMANAGER", "createdImageFile");
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.inspect.fileprovider",
                        photoFile);
                LogManager.reportStatus(context, "PHOTOMANAGER", "photoUriRetrieved: " + photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                imageUri = photoURI;
            }
        }
    }

    /**
     * Adds picture to gallery
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

    /**
     * Decode a scaled image
     */
    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        //BitmapFactory.Options boptions = null;
        BitmapFactory.decodeFile(currentPhotoPath, null);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, null);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * Configures the back button
     */
    public void configureBackBtn(){
        Button backBtn = (Button)findViewById(R.id.btnPhotoManagerBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTOMANAGER", "configureBackBtn");
    }

    /**
     * Pack the image Uri into the newIntent and open Inspector activity
     * @param view
     */
    public void onAccept(View view){
        Intent newIntent = new Intent(PhotoManager.this, Inspector.class);
        Intent intent = this.getIntent();
        String blueprint = intent.getExtras().getString("blueprint");
        boolean isInspecting = intent.getExtras().getBoolean("isInspecting");
        String filename = intent.getExtras().getString("filename");
        newIntent.putExtra("blueprint", blueprint);
        if (imageUri != null){
            newIntent.putExtra("imageUriString", imageUri.toString());
            newIntent.putExtra("isInspecting", isInspecting);
            newIntent.putExtra("filename", filename);
        }
        startActivity(newIntent);
        finish();
    }
}
