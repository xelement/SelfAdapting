package com.adapt.library;

import java.io.InputStream;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Vibrator;

public class SelfAdaptUtils {

	/**
	 * 触摸点是否在某个区域内
	 * 
	 * @param pressX
	 *            触摸点的x坐标
	 * @param pressY
	 *            触摸点的y坐标
	 * @param left
	 *            Rect的left坐标
	 * @param top
	 *            Rect的top坐标
	 * @param width
	 *            Rect的宽度
	 * @param height
	 *            Rect的高度
	 * @return 在区域内返回true，否则返回false
	 */
	public static boolean isPointInRect(float pressX, float pressY, float left,
			float top, float width, float height) {
		return (pressX > left && pressX < (left + width) && pressY > top && pressY < (top + height));
	}

	/**
	 * decodeBitmap 适应屏幕
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap decodeBitmap(Context context, int resId) {
		return decodeBitmap(context, resId, SelfAdapting.actualScale,
				SelfAdapting.actualScale);
	}

	/**
	 * 指定scaleX,scaleY
	 * 
	 * @param context
	 * @param resId
	 * @param scaleX
	 * @param scaleY
	 * @return
	 */
	public static Bitmap decodeBitmap(Context context, int resId, float scaleX,
			float scaleY) {

		Bitmap bmp = decodeBitmapWithStream(context, resId);
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);
		return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
				matrix, true);// 得到放大的图片
	}

	/**
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap decodeBitmapWithStream(Context context, int resId) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inSampleSize = 1;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, options);
	}

	/**
	 * 获取width*height尺寸的bitmap
	 * 
	 * @param context
	 * @param width
	 * @param height
	 * @param resId
	 * @return
	 */
	public static Bitmap decodeBitmap(Context context, int width, int height,
			int resId) {
		Bitmap bmp = decodeBitmapWithStream(context, resId);
		Matrix matrix = new Matrix();
		matrix.postScale(1.0f * width / bmp.getWidth(),
				1.0f * height / bmp.getHeight());
		return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
				matrix, true);
	}

	/**
	 * 根据文件名获取SCREEN_WIDTH*SCREEN_HEIGHT的Bitmap
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap decodeBitmpFile(String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inSampleSize = 1;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
		return decodeBitmap(bmp);
	}

	/**
	 * 将Bitmap缩放为scaleX*scale尺寸
	 * 
	 * @param bmp
	 * @param scaleX
	 * @param scaleY
	 * @return
	 */
	public static Bitmap decodeBitmap(Bitmap bmp, float scaleX, float scaleY) {
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);
		return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
				matrix, true);

	}

	/**
	 * 将Bitmap 缩放为适应屏幕尺寸的大小
	 * 
	 * @param bmp
	 * @return
	 */
	public static Bitmap decodeBitmap(Bitmap bmp) {
		Matrix matrix = new Matrix();
		matrix.postScale(1.0f * SelfAdapting.widthPixels / bmp.getWidth(), 1.0f
				* SelfAdapting.heightPixels / bmp.getHeight());
		return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
				matrix, true);
	}

	/**
	 * 获取桌面背景图片,同时设置尺寸为ScreenWidth*ScreenHeight
	 * 
	 * @param context
	 * @return
	 */
	public static Bitmap getWallDrawable(Context context) {

		WallpaperManager wallpaperManager = WallpaperManager
				.getInstance(context);
		// 获取当前壁纸
		Drawable wallpaperDrawable = wallpaperManager.getDrawable();
		// 将Drawable,转成Bitmap ,Bitmap作为一种逐像素的显示对象执行效率高,RGB888编码
		Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();

		return decodeBitmap(bm);
	}

	/**
	 * (自适应屏幕时)获取对应屏幕绘制时X方向上的坐标(left)
	 * 
	 * @see #getDrawX(float)
	 * @param x
	 *            实际像素值,比如480x800上x=150,则对应320x480上就不一定是150px了
	 * @return
	 */
	public static float getAdaptDrawX(float x) {
		return (x * SelfAdapting.actualScale + SelfAdapting.screenOffsetX);
	}

	/**
	 * 获取对应屏幕上的绝对位置坐标,如480x800上 x= 24 则将屏幕拉伸到600x1000时x=32 此时保证了比例不变
	 * 
	 * @see #getAdaptDrawX(float)
	 * @param x
	 * @return
	 */
	public static float getDrawX(float x) {
		return SelfAdapting.widthPixels / SelfAdapting.referenceScreenWidth * x;
	}

	/**
	 * (自适应屏幕时) 获取对应屏幕绘制时Y方向上的坐标(top)
	 * 
	 * @see #getDrawY(float)
	 * @param y
	 * @return
	 */
	public static float getAdaptDrawY(float y) {

		return (y * SelfAdapting.actualScale + SelfAdapting.screenOffsetY);

	}

	/**
	 * 获取对应屏幕上的绝对位置坐标
	 * 
	 * @see #getDrawX(float)
	 * @see #getAdaptDrawY(float)
	 * @param y
	 * @return
	 */
	public static float getDrawY(float y) {
		return SelfAdapting.heightPixels / SelfAdapting.referenceScreenHeight
				* y;
	}


	/**
	 * 使用DrawText时,计算文字宽度
	 * 
	 * @param paint
	 * @param str
	 * @return
	 */
	public static int getTextWidth(Paint paint, String str) {
		int iRet = 0;
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len];
			paint.getTextWidths(str, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}

	/**
	 * 获取字体高度
	 * 
	 * @param paint
	 * @return
	 */
	public static int getFontHeight(Paint paint) {

		FontMetrics fm = paint.getFontMetrics();
		return (int) Math.ceil(fm.descent - fm.ascent);
	}

	/**
	 * 震动一下
	 * 
	 * @param context
	 * @param time
	 *            震动时间
	 */
	public static void virbate(Context context, long time) {

		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(time);
	}

}
