package com.adapt.example;

import com.adapt.library.SelfAdaptUtils;
import com.adapt.library.SelfAdapting;
import com.adapt.screensize.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainMenuView extends SurfaceView implements SurfaceHolder.Callback {

	private static String TAG = "MainMenuView";
	private GameActivity activity;
	private SurfaceHolder sHolder = null;
	private int currentAlpha = 0;
	private Paint paint;
	private Paint paintText;
	// ����˵���ť
	private MenuButton btnStartGame;
	private MenuButton btnSettings;
	private MenuButton btnExit;
	private MenuButton btnRank;
	private MenuButton btnAbout;
	// ����
	private Bitmap bmpBackground = null;
	// �˵���ť����
	private Bitmap bmpStartPressed;
	private Bitmap bmpStartNormal;
	private Bitmap bmpSettingsPressed;
	private Bitmap bmpSettingsNormal;
	private Bitmap bmpExitPressed;
	private Bitmap bmpExitNormal;
	private Bitmap bmpRankPressed;
	private Bitmap bmpRankNormal;
	private Bitmap bmpAboutPressed;
	private Bitmap bmpAboutNormal;
	private Bitmap bmpCircle;
	private boolean isThdNeedRunning = true;
	private boolean isAlphaRunned = false;
	private DrawViewThread thdDrawView;
	// ���ƽ�����߳�
	private DrawAlphaViewThread thdDrawAlphaView;

	public MainMenuView(GameActivity context) {
		super(context);
		this.activity = context;
		sHolder = getHolder();
		sHolder.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paintText = new Paint();
		paintText.setAntiAlias(true);
		paintText.setColor(Color.WHITE);
		paintText.setStyle(Style.STROKE);
		paintText.setTextSize(5);
		// ���ñ���ɫΪ͸������л�ʱ��������
		this.setBackgroundColor(Color.TRANSPARENT);
		// ������,��ȡ��Ļ���㣬���̽����������setFocusable(),
		// Ȼ������setFocusableInTouchMode(),Ȼ��������requestFocus();
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		this.requestFocus();
		// ����ͼƬ��Դ
		loadBitmaps();
		// �������ⰴť
		createMenuButtons();
	}

	public void createThreads() {
		if (!isAlphaRunned) {
			thdDrawAlphaView = new DrawAlphaViewThread();
		}
		// ÿ�ζ����´����߳�
		thdDrawView = null;
		thdDrawView = new DrawViewThread();
		isThdNeedRunning = true;
	}

	public void startThreads() {
		if (!isAlphaRunned) {
			thdDrawAlphaView.start();
			isAlphaRunned = true;
		} else {
			thdDrawView.start();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// ��������һ��
		drawSelf();
		createThreads();
		startThreads();
		Log.i(TAG, "MainMenuView surfaceCreated !");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.i(TAG, "MainMenuView surfaceChanged !");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// ���ñ��
		this.isThdNeedRunning = false;
		Log.i(TAG, "MainMenuView surfaceDestroyed !");
	}

	private void drawSelf() {
		Canvas canvas = sHolder.lockCanvas();
		if (canvas == null) {
			return;
		}
		// ����
		canvas.drawColor(Color.BLACK);
		paint.setAlpha(currentAlpha);
		canvas.drawBitmap(bmpBackground, SelfAdapting.screenOffsetX,
				SelfAdapting.screenOffsetY, paint);
		// (104,80)
		canvas.drawBitmap(bmpCircle, SelfAdaptUtils.getAdaptDrawX(104),
				SelfAdaptUtils.getAdaptDrawY(80), paint);
		// ���ư�ť
		btnStartGame.draw(canvas, paint);
		btnSettings.draw(canvas, paint);
		btnExit.draw(canvas, paint);
		btnRank.draw(canvas, paint);
		btnAbout.draw(canvas, paint);
		// �ύ����
		if (canvas != null) {
			sHolder.unlockCanvasAndPost(canvas);
		}
	}

	private void loadBitmaps() {

		bmpBackground = SelfAdaptUtils
				.decodeBitmap(activity, R.drawable.menu_background);
		bmpStartPressed = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_start_pressed);
		bmpStartNormal = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_start_normal);
		bmpSettingsPressed = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_setting_pressed);
		bmpSettingsNormal = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_setting_normal);
		bmpExitPressed = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_exit_pressed);
		bmpExitNormal = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_exit_normal);
		bmpRankPressed = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_rank_pressed);
		bmpRankNormal = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_rank_normal);
		bmpAboutPressed = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_about_pressed);
		bmpAboutNormal = SelfAdaptUtils.decodeBitmap(activity,
				R.drawable.game_about_normal);
		bmpCircle = SelfAdaptUtils.decodeBitmap(activity, R.drawable.circle);
	}

	private void createMenuButtons() {
		// (133,77)
		btnStartGame = new MenuButton(SelfAdaptUtils.getAdaptDrawX(133),
				SelfAdaptUtils.getAdaptDrawY(77), bmpStartNormal, bmpStartPressed);
		// (130,332)
		btnSettings = new MenuButton(SelfAdaptUtils.getAdaptDrawX(130),
				SelfAdaptUtils.getAdaptDrawY(332), bmpSettingsNormal,
				bmpSettingsPressed);
		// (471,396)
		btnExit = new MenuButton(SelfAdaptUtils.getAdaptDrawX(471),
				SelfAdaptUtils.getAdaptDrawY(396), bmpExitNormal, bmpExitPressed);
		// (260,193)
		btnRank = new MenuButton(SelfAdaptUtils.getAdaptDrawX(260),
				SelfAdaptUtils.getAdaptDrawY(193), bmpRankNormal, bmpRankPressed);
		// (577,46)
		btnAbout = new MenuButton(SelfAdaptUtils.getAdaptDrawX(577),
				SelfAdaptUtils.getAdaptDrawY(46), bmpAboutNormal, bmpAboutPressed);
	}

	private float lastX = 0;
	private float lastY = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float pressX = event.getX();
		float pressY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			lastY = event.getY();
			if (btnStartGame.isOnTouch(pressX, pressY)) {
				btnStartGame.pressed(true);
			} else if (btnSettings.isOnTouch(pressX, pressY)) {
				btnSettings.pressed(true);
			} else if (btnExit.isOnTouch(pressX, pressY)) {
				btnExit.pressed(true);
			} else if (btnRank.isOnTouch(pressX, pressY)) {
				btnRank.pressed(true);
			} else if (btnAbout.isOnTouch(pressX, pressY)) {
				btnAbout.pressed(true);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float curX = event.getX();
			float curY = event.getY();
			if (btnStartGame.isOnTouch(curX, curY)
					&& btnStartGame.isOnTouch(lastX, lastY)) {
				btnStartGame.pressed(true);
			} else {
				btnStartGame.pressed(false);
			}
			if (btnSettings.isOnTouch(curX, curY)
					&& btnSettings.isOnTouch(lastX, lastY)) {
				btnSettings.pressed(true);
			} else {
				btnSettings.pressed(false);

			}
			if (btnExit.isOnTouch(curX, curY)
					&& btnExit.isOnTouch(lastX, lastY)) {
				btnExit.pressed(true);
			} else {
				btnExit.pressed(false);
			}
			if (btnRank.isOnTouch(curX, curY)
					&& btnRank.isOnTouch(lastX, lastY)) {
				btnRank.pressed(true);
			} else {
				btnRank.pressed(false);
			}
			if (btnAbout.isOnTouch(curX, curY)
					&& btnAbout.isOnTouch(lastX, lastY)) {
				btnAbout.pressed(true);
			} else {
				btnAbout.pressed(false);
			}
			break;
		case MotionEvent.ACTION_UP:
			if (btnStartGame.isOnTouch(pressX, pressY)
					&& btnStartGame.isOnTouch(lastX, lastY)) {
				// �����ʼ��Ϸ��ť

			} else if (btnSettings.isOnTouch(pressX, pressY)
					&& btnSettings.isOnTouch(lastX, lastY)) {
				// �����Ϸ���ð�ť
			} else if (btnExit.isOnTouch(pressX, pressY)
					&& btnExit.isOnTouch(lastX, lastY)) {
				// ����˳���Ϸ��ť
				activity.finish();
			} else if (btnRank.isOnTouch(pressX, pressY)
					&& btnRank.isOnTouch(lastX, lastY)) {
				// ������а�ť
			} else if (btnAbout.isOnTouch(pressX, pressY)
					&& btnAbout.isOnTouch(lastX, lastY)) {
				// ������ڰ�ť
			}
			btnStartGame.pressed(false);
			btnSettings.pressed(false);
			btnExit.pressed(false);
			btnRank.pressed(false);
			btnAbout.pressed(false);
			break;
		}
		return true;
	}

	private class DrawViewThread extends Thread {

		@Override
		public void run() {
			super.run();
			while (isThdNeedRunning) {
				try {
					long start = System.currentTimeMillis();
					drawSelf();
					int interval = (int) (System.currentTimeMillis() - start);
					// ���ÿ60ms drawһ�����ж�drawʱ��
					if (interval < 60) {
						Thread.sleep(60 - interval);
					}
				} catch (Exception e) {
					Log.e(TAG, "DrawViewThread :" + e.getMessage());
				}
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("MainMenuView onKeyDown !");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			System.out.println("MainMenuView KEYCODE_BACK");
			showExitDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showExitDialog() {
		AlertDialog alert = new AlertDialog.Builder(activity)
				.setPositiveButton("��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						activity.finish();
					}
				}).setNegativeButton("��", null).create();
		alert.setMessage("�����Ҫ�˳���?");
		alert.show();
	}

	private class DrawAlphaViewThread extends Thread {

		@Override
		public void run() {
			super.run();
			for (int i = 0; i < 255; i += 10) {
				if (!isThdNeedRunning) {
					return;
				}
				currentAlpha = i;
				if (currentAlpha > 255) {
					currentAlpha = 255;
				}
				try {
					long time = System.currentTimeMillis();
					drawSelf();
					long invertal = System.currentTimeMillis() - time;
					if (invertal < 60) {
						Thread.sleep(60 - invertal);
					}
				} catch (Exception e) {
					Log.e(TAG, "DrawAlphaViewThread :" + e.getMessage());
				}
			}
			thdDrawView.start();
		}
	}

	// ���˵������һֱֻ�ã����� ������Դ��GameActivity�л���
	public void recycle() {
		bmpBackground.recycle();
		bmpStartPressed.recycle();
		bmpStartNormal.recycle();
		bmpSettingsPressed.recycle();
		bmpSettingsNormal.recycle();
		bmpExitPressed.recycle();
		bmpExitNormal.recycle();
		bmpRankPressed.recycle();
		bmpRankNormal.recycle();
		bmpAboutPressed.recycle();
		bmpAboutNormal.recycle();
		Log.i(TAG, "resources has been recycled !");
	}
}
