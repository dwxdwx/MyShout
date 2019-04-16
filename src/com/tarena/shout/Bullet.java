package com.tarena.shout;


//子弹
public class Bullet extends FlyingObject{
	private int speed=3;
	//Play0 play4 = new Play0("F:\\Chrome下载\\【批量下载】flys等\\分享\\项目\\MyShout\\src\\com\\tarena\\shout\\music\\zidan1.mp3");
	public Bullet(int x,int y){
		image=ShootGame.bullet;
		//play4.start();
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
