package com.example.user010.vero_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.example.user010.vero_project.controller.MyInfoManager;


public class CameraControlActivity extends Activity {

	public static final int REQUEST_CODE_TAKE_FROM_CAMERA = 0;
	private static final int PHOTO_W = 250;
	private static final int PHOTO_H = 150;
	public ImageView profilePic;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_activity);
		dispatchTakePictureIntent();
		profilePic = (ImageView) findViewById( R.id.profilePic );

	}


	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_FROM_CAMERA);
		}
	}

	// Handle data after activity returns.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		/*if (requestCode == REQUEST_CODE_TAKE_FROM_CAMERA && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			if(imageBitmap!=null){
				Bitmap scaledImageBitmap  = getScaledImage(imageBitmap);
				profilePic.setImageBitmap( scaledImageBitmap );
			}

			finish();
		}*/
	}


	private Bitmap getScaledImage(Bitmap imageBitmap) {
		// Get the dimensions of the View
		Bitmap scaledBitmap = null;
		try {

			Matrix matrix = new Matrix();
			matrix.postRotate(90);

			Bitmap rotatedBitmap =  Bitmap.createScaledBitmap(imageBitmap, PHOTO_W, PHOTO_H, false);
			scaledBitmap = Bitmap.createBitmap(rotatedBitmap , 0, 0, rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), matrix, true);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return scaledBitmap;
	}

}