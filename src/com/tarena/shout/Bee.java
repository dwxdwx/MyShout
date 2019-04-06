package com.tarena.shout;

import java.util.Random;

//小蜜蜂
public class Bee extends FlyingObject implements Award{
	private int xSpeed=1;
	private int ySpeed=2;
	private int awardType;//奖励的类型
	public Bee(){
		image=ShootGame.bee;
		width=image.getWidth();//获取图片的宽
		heighet=image.getHeight();
		Random rand=new Random();
		x=rand.nextInt(ShootGame.WIDTH-this.width);//小蜜蜂的初始位置
		y=-this.heighet;
		awardType=rand.nextInt(2);
	}
	public int getType() {
		return awardType;
	}
	public void step() {
		y+=ySpeed;
		x+=xSpeed;
		if(x>=ShootGame.WIDTH-this.width){
			xSpeed=-1;
		}
		if(x<=0){
			xSpeed=1;
		}
	}
	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return this.y>=ShootGame.HEIGHT;
	}
}
