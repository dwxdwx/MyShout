package com.tarena.shout;
import java.util.Random;
//敌机类
public class Airplane extends FlyingObject implements Enemy {
	private int speed=2;//敌机走步的步数
	public Airplane(){
		image=ShootGame.airplane;
		width=image.getWidth();//获取图片的宽
		heighet=image.getHeight();
		Random rand=new Random();
		x=rand.nextInt(ShootGame.WIDTH-this.width);//敌机的初始位置
		y=-this.heighet;

	}
	//重写getScore()
	public int getScore(){
		return 5;
	}
	//重写step（）
	public void step() {
		y+=speed;
		
	}
	@Override
	public boolean outOfBounds() {
		return this.y>=ShootGame.HEIGHT;
	}
}
