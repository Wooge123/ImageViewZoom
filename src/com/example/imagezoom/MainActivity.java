package com.example.imagezoom;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView mImageView;
	private RelativeLayout layout;
	private Button btnBig, btnSmall;
	private Bitmap bmp;
	private int id = 0;
	private int displayWidth;
	private int displayHeight;
	private float scaleWidth = 1;
	private float scaleHeight = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* 取得屏幕分辨率大小 */
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		displayWidth = dm.widthPixels;
		displayHeight = dm.heightPixels;
		/* 初始化相关变量 */
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.test);
		mImageView = (ImageView) findViewById(R.id.mImageView);
		layout = (RelativeLayout) findViewById(R.id.layout);
		btnBig = (Button) findViewById(R.id.btnBig);
		btnSmall = (Button) findViewById(R.id.btnSmall);

		btnBig.setOnClickListener(this);
		btnSmall.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		double scale = 1.25;
		if (v.getId() == R.id.btnSmall) {
			scale = 0.8;
		}
		/* 计算缩放比例 */
		scaleWidth = (float) (scaleWidth * scale);
		scaleHeight = (float) (scaleHeight * scale);
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);

		if (id == 0)
			/* 如果是第一次按，就移除原来设定的ImageView */
			layout.removeView(mImageView);
		else
			/* 如是不是，就移除上次放大缩小所生成的ImageView */
			layout.removeView((ImageView) findViewById(id));

		/* 产生新的ImageView,放入resize的Bitmap对象，再放入Layout中 */
		id++;
		ImageView imageView = new ImageView(this);
		imageView.setId(id);
		imageView.setImageBitmap(resizeBmp);
		layout.addView(imageView);
		setContentView(layout);
		if (v.getId() == R.id.btnSmall)
			/* 缩小时把放大按钮重设为enable */
			btnBig.setEnabled(true);
		if (v.getId() == R.id.btnBig) {
			/* 如果再放大会超过屏幕大小，就把Button disable */
			if (scaleWidth * scale * bmpWidth > displayWidth || scaleHeight * scale * bmpHeight > displayHeight)
				btnBig.setEnabled(false);
		}
	}

}
