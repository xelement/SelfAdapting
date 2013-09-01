package com.adapt.example;

import com.adapt.library.SelfAdaptUtils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class VirtualButton {
	
	private float left;
	private float top;
	private float width;
	private float height;

	public VirtualButton(float left, float top, float width, float height) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}
	/**
	 * 空方法,由子类重写
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas,Paint paint){
		
	}
	/**
	 * 点击是否在区域类
	 * @param pressX
	 * @param pressY
	 * @return
	 */
	public boolean isOnTouch(float pressX, float pressY) {
		return SelfAdaptUtils.isPointInRect(pressX, pressY, left, top, width, height);
	}
	/**
	 * 设置位置(左上角坐标)
	 * @param left
	 * @param top
	 */
	public void setPosition(float left, float top) {
		this.left = left;
		this.top = top;
	}
	/**
	 * 重载方法，设置位置(左上角坐标)
	 * @param p
	 */
	public void setPosition(Point p) {
		this.left = p.x;
		this.top = p.y;
	}
	/**
	 * 获取区域left坐标
	 * @return
	 */
	public float getLeft() {
		return this.left;
	}
	/**
	 * 获取区域top坐标
	 * @return
	 */
	public float getTop() {
		return this.top;
	}
	/**
	 * 获取区域宽度
	 * @return
	 */
	public float getWidth() {
		return this.width;
	}
	/**
	 * 获取区域高度
	 * @return
	 */
	public float getHeight() {
		return this.height;
	}
}
