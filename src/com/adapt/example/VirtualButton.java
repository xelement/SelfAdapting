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
	 * �շ���,��������д
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas,Paint paint){
		
	}
	/**
	 * ����Ƿ���������
	 * @param pressX
	 * @param pressY
	 * @return
	 */
	public boolean isOnTouch(float pressX, float pressY) {
		return SelfAdaptUtils.isPointInRect(pressX, pressY, left, top, width, height);
	}
	/**
	 * ����λ��(���Ͻ�����)
	 * @param left
	 * @param top
	 */
	public void setPosition(float left, float top) {
		this.left = left;
		this.top = top;
	}
	/**
	 * ���ط���������λ��(���Ͻ�����)
	 * @param p
	 */
	public void setPosition(Point p) {
		this.left = p.x;
		this.top = p.y;
	}
	/**
	 * ��ȡ����left����
	 * @return
	 */
	public float getLeft() {
		return this.left;
	}
	/**
	 * ��ȡ����top����
	 * @return
	 */
	public float getTop() {
		return this.top;
	}
	/**
	 * ��ȡ������
	 * @return
	 */
	public float getWidth() {
		return this.width;
	}
	/**
	 * ��ȡ����߶�
	 * @return
	 */
	public float getHeight() {
		return this.height;
	}
}
