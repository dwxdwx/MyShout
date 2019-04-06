package com.tarena.shout;

import java.util.Random;

public class BigBee extends FlyingObject implements Enemy{
	private int speed=1;//敌机走步的步数
	public BigBee(){
		image=ShootGame.bigBee;
		width=image.getWidth();//获取图片的宽
		heighet=image.getHeight();
		Random rand=new Random();
		x=rand.nextInt(ShootGame.WIDTH-this.width);//敌机的初始位置
		y=-this.heighet;
	}

	@Override
	public void step() {
		y+=speed;
		
	}

	@Override
	public boolean outOfBounds() {
		
		return this.y>=ShootGame.HEIGHT;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 40;
	}

}
