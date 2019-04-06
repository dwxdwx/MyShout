package com.tarena.shout;


//子弹
public class Bullet extends FlyingObject{
	private int speed=3;
	public Bullet(int x,int y){
		image=ShootGame.bullet;
		width=image.getWidth();//获取图片的宽
		heighet=image.getHeight();
		this.x=x;
		this.y=y;
	}
	@Override
	public void step() {
		y-=speed;
		
	}
	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return this.y<=-this.heighet;
	}

}
