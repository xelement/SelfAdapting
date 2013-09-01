package com.adapt.library;

import java.math.BigDecimal;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;

public class SelfAdapting {
	/**
	 * 参考屏幕宽度
	 */
	public static int referenceScreenWidth = 480;
	/**
	 * 参考屏幕高度
	 */
	public static int referenceScreenHeight = 800;
	/**
	 * 屏幕宽度
	 */
	public static int widthPixels;
	/**
	 * 屏幕高度
	 */
	public static int heightPixels;

	/**
	 * 屏幕密度
	 */
	public static float density;
	/**
	 * 实际放大(缩小)倍数
	 */
	public static float actualScale = 1.0f;
	/**
	 * 屏幕X轴上的偏移量
	 */
	public static float screenOffsetX;
	/**
	 * 屏幕Y轴上的偏移量
	 */
	public static float screenOffsetY;
	/**
	 * 屏幕方向为横向
	 */
	public static final int SCREEN_ORIENTATION_LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	/**
	 * 屏幕方向为纵向
	 */
	public static final int SCREEN_ORIENTATION_PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
	/**
	 * 当前屏幕方向,默认为纵向
	 */
	private static int currentOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

	public SelfAdapting(Context mContext, int orientation) {
		currentOrientation = orientation;
		init(mContext);
	}

	public SelfAdapting(Context mContext) {
		init(mContext);
	}

	public static void init(Context mContext) {
		getScreenSize(mContext);
		calculateScale(mContext);
	}

	public static void init(Context mContext, int orientation) {
		currentOrientation = orientation;
		getScreenSize(mContext);
		calculateScale(mContext);
	}

	/**
	 * 获取屏幕尺寸
	 */
	private static void getScreenSize(Context mContext) {

		DisplayMetrics display = mContext.getResources().getDisplayMetrics();
		// 屏幕密度
		density = display.density;
		widthPixels = display.widthPixels;
		heightPixels = display.heightPixels;
		// 如果屏幕方向为横向,确保width>height
		if (currentOrientation == SCREEN_ORIENTATION_LANDSCAPE) {
			if (widthPixels < heightPixels) {
				int temp = widthPixels;
				widthPixels = heightPixels;
				heightPixels = temp;
			}
			// 横向时确保参考屏幕也为横向
			if (referenceScreenWidth < referenceScreenHeight) {
				int temp = referenceScreenWidth;
				referenceScreenWidth = referenceScreenHeight;
				referenceScreenHeight = temp;
			}
		} else {
			// 如果为纵向,确保width<height
			if (widthPixels > heightPixels) {
				int temp = widthPixels;
				widthPixels = heightPixels;
				heightPixels = temp;
			}
			if (referenceScreenWidth > referenceScreenHeight) {
				int temp = referenceScreenWidth;
				referenceScreenWidth = referenceScreenHeight;
				referenceScreenHeight = temp;
			}
		}
	}

	/**
	 * 计算需要放大(缩小)的比率,要在获取屏幕尺寸之后计算
	 */
	private static void calculateScale(Context mContext) {
		// 获取实际放大或缩小倍数
		actualScale = Math.min(1.0f * heightPixels / referenceScreenHeight,
				1.0f * widthPixels / referenceScreenWidth);
		// 只保留2位有效数字
		actualScale = new BigDecimal(actualScale).setScale(2,
				BigDecimal.ROUND_HALF_UP).floatValue();
		// 获取X轴方向上的偏移量(默认屏幕为纵向)
		screenOffsetX = Math.abs((widthPixels - referenceScreenWidth
				* actualScale) / 2);
		// 获取Y轴方向上的偏移量(默认屏幕为纵向)
		screenOffsetY = Math.abs((heightPixels - referenceScreenHeight
				* actualScale) / 2);
		// 只保留2位有效数字
		screenOffsetX = new BigDecimal(screenOffsetX).setScale(2,
				BigDecimal.ROUND_HALF_UP).floatValue();
		screenOffsetY = new BigDecimal(screenOffsetY).setScale(2,
				BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 获取当前屏幕方向
	 * 
	 * @return
	 */
	public static int getOrientation() {
		return currentOrientation;
	}

	/**
	 * 设置当前屏幕方向,重新设置屏幕方向后会重新设置相关参数
	 * 
	 * @param orientation
	 */
	public static void setOrientation(Context mContext, int orientation) {
		setOrientation(orientation);
		init(mContext);
	}

	/**
	 * 设置屏幕方向,不重新初始化相关参数
	 * 
	 * @param orientation
	 */
	public static void setOrientation(int orientation) {
		if (orientation == currentOrientation) {
			return;
		}
		currentOrientation = orientation;
	}

	/**
	 * 设置参考屏幕宽度和高度并且重新初始化参数
	 * 
	 * @param mContext
	 * @param referenceWidth
	 * @param referenceHeight
	 */
	public static void setReferenceScreen(Context mContext, int referenceWidth,
			int referenceHeight) {
		setReferenceScreen(referenceWidth, referenceHeight);
		init(mContext);
	}

	/**
	 * 设置参考屏幕宽度和高度(不重新初始化参数)
	 * 
	 * @param referenceWidth
	 * @param referenceHeight
	 */
	public static void setReferenceScreen(int referenceWidth,
			int referenceHeight) {
		referenceScreenWidth = referenceWidth;
		referenceScreenHeight = referenceHeight;
	}
}
