package com.tarena.shout;
import java.awt.image.BufferedImage;
//飞行物
public abstract class FlyingObject {
	protected BufferedImage image;
	protected int width;
	protected int heighet;
	protected int x;
	protected int y;
	
	public abstract void step();
	//检查飞行物是否越界
	public abstract boolean outOfBounds();
	//敌人被子弹射 
	public boolean shootBy(Bullet bullet){
		int x1=this.x;
		int x2=this.x+this.width;
		int y1=this.y;
		int y2=this.y+this.heighet;
		int x=bullet.x;
		int y=bullet.y;
		return x>x1&&x<x2&&y>y1&&y<y2;
	}
}
