package com.tarena.shout;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

//英雄机
public class Hero extends FlyingObject{
	private int life;
	private int doubleFire;
	private BufferedImage[] images;
	private int index;
   // Play0 play3 = new Play0("F:\\Chrome下载\\【批量下载】flys等\\分享\\项目\\MyShout\\src\\com\\tarena\\shout\\music\\pengzhuang.mp3");
	public Hero(){
		image=ShootGame.hero0;
		width=image.getWidth();//获取图片的宽
		heighet=image.getHeight();
		x=150;
		y=400;
		life=3;
		doubleFire=0;
		images=new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
		index=0;
	}


	public void hitHero()  {
	    images=new BufferedImage[]{ShootGame.hero1,ShootGame.hero2};
	    //play3.start();
        test1();
        //test2();



    }
//
//    //指定音乐放几秒
//    public  void test2() {
//        //创建Timer
//        final Timer timer2 = new Timer();
//        //设定定时任务
//        timer2.schedule(new TimerTask() {
//            //定时任务执行方法
//            @Override
//            public void run() {
//                play3.stop();
//            }
//        }, 1000);
//
//    }

    //指定就爆炸几秒
    public  boolean test1() {
        //创建Timer
        final Timer timer = new Timer();
        //设定定时任务
        timer.schedule(new TimerTask() {
            //定时任务执行方法
            @Override
            public void run() {
                images=new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
               //play3.start();
            }
        }, 1000);
            return true;
    }

	@Override
	public void step() {
		image=images[index++/10%images.length];
		
	}
	
	public Bullet[] shoot(){
		int xStep=this.width/4;
		int yStep=20;
		if (doubleFire>=50) {
			Bullet[] bs=new Bullet[3];
			bs[0]=new Bullet(this.x+1*xStep, this.y-yStep);
			bs[1]=new Bullet(this.x+2*xStep, this.y-yStep);
			bs[2]=new Bullet(this.x+3*xStep, this.y-yStep);
			doubleFire-=1;
			return bs;
		} else if (doubleFire<100&&doubleFire>0) {
			Bullet[] bs=new Bullet[2];
			bs[0]=new Bullet(this.x+1*xStep, this.y-yStep);
			bs[1]=new Bullet(this.x+3*xStep, this.y-yStep);
			doubleFire-=1;
			return bs;
		} else{
			Bullet[] bs=new Bullet[1];
			bs[0]=new Bullet(this.x+2*xStep, this.y-yStep);
			return bs;
		}
	}
	//英雄级机随鼠标移动
	public void moveTo(int x,int y){
		this.x=x-this.width/2;
		this.y=y-this.heighet/2;	
	}
	@Override
	public boolean outOfBounds() {
		return false;
	}
	//增命
	public void addLife(){
		life+=2;
		
	}
	public int getLife(){
		return life;
	}
	public void addDoubleFire(){
		doubleFire+=20;
	}
	public int getDoubleFire(){
		return doubleFire;
	}
	//other： 敌人
	public boolean hit(FlyingObject other){
		int x1=other.x-this.width/2;
		int x2=other.x+this.width/2+other.width;
		int y1=other.y-this.heighet/2;
		int y2=other.y+this.heighet/2+other.heighet;
		int x=this.x+this.width/2;
		int y=this.y+this.heighet;
		return x>x1&&x<x2&&y>y1&&y<y2;
	}
	//减命
	public void subtractLife(){
	    life--;
	}
	public void clealDoubleFire(){
		doubleFire=0;
	}
}
