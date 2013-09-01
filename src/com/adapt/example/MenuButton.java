package com.adapt.example;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MenuButton extends VirtualButton {

	private Bitmap bmpPressed;
	private Bitmap bmpNormal;
	private boolean isPressed = false;
	public MenuButton(float left, float top, Bitmap normal, Bitmap pressed) {
		super(left, top, normal.getWidth(), normal.getHeight());

		this.bmpNormal = normal;
		this.bmpPressed = pressed;
	}
	@Override
	public void draw(Canvas canvas, Paint paint) {
		if (isPressed) {
			canvas.drawBitmap(bmpPressed, getLeft(), getTop(), paint);
		} else {
			canvas.drawBitmap(bmpNormal, getLeft(), getTop(), paint);
		}
	}

	public void pressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	public void setPressedBmp(Bitmap bmp) {
		this.bmpPressed = bmp;
	}

}
