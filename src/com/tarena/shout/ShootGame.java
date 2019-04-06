package com.tarena.shout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;
import java.awt.event.MouseAdapter;//适配器
import java.awt.event.MouseEvent;//事件
import java.awt.Color;
import java.awt.Font;
//主程序类
public class ShootGame extends JPanel{
	public static final int WIDTH=400;
	public static final int HEIGHT=654;
	public static BufferedImage background;//背景图
	public static BufferedImage start;//开始图
	public static BufferedImage pause;//暂停图
	public static BufferedImage gameover;//结束图
	public static BufferedImage airplane;//敌机图
	public static BufferedImage bee;//奖励图
	public static BufferedImage bullet;//子弹图
	public static BufferedImage hero0;//英雄机0---和1一起构成动态图
	public static BufferedImage hero1;//英雄机1
	public static BufferedImage hero2;//英雄机2
	public static BufferedImage bigBee;//大怪图
	public static final int START=0;//启动
	public static final int RUNNING=1;//运行
	public static final int PAUSE=2;//暂停
	public static final int GAMEOVER=3;
	private int state=START;
	private Hero hero=new Hero();
	private FlyingObject[] flyings={};
	private Bullet[] bullets={};
	
	static{
		try {
			background=ImageIO.read(ShootGame.class.getResource("image/background.png"));
			start=ImageIO.read(ShootGame.class.getResource("image/start.png"));
			pause=ImageIO.read(ShootGame.class.getResource("image/pause.png"));
			gameover=ImageIO.read(ShootGame.class.getResource("image/gameover.png"));
			airplane=ImageIO.read(ShootGame.class.getResource("image/airplane.png"));
			bee=ImageIO.read(ShootGame.class.getResource("image/bee.png"));
			bullet=ImageIO.read(ShootGame.class.getResource("image/bullet.png"));
			hero0=ImageIO.read(ShootGame.class.getResource("image/hero0.png"));
			hero1=ImageIO.read(ShootGame.class.getResource("image/hero1.png"));
			hero2=ImageIO.read(ShootGame.class.getResource("image/hero2.png"));
			bigBee=ImageIO.read(ShootGame.class.getResource("image/bigBee.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//画图
	public void paint(Graphics g){
		g.drawImage(background,0,0,null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScroreAndLife(g);
		patintState(g);
	}
	//画分画命
		public void paintScroreAndLife(Graphics g){
			g.setColor(new Color(0xD8FFA3));//设置颜色
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
			g.drawString("分数:"+score,10,25);//画分
			g.drawString("生命:"+hero.getLife(),10,50);
			g.drawString("超级火力:"+hero.getDoubleFire(),10,75);
		}
	//画英雄机
	public void paintHero(Graphics g){
		g.drawImage(hero.image,hero.x,hero.y,null);
	}
	
	//画敌人
    public void paintFlyingObjects(Graphics g){
		for(int i=0;i<flyings.length;i++){
			FlyingObject f=flyings[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
    //画子弹
    public void paintBullets(Graphics g){
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			g.drawImage(b.image,b.x,b.y,null);
		}
	}
    //画状态
    public void patintState(Graphics g){
    	switch(state){
    	case START:
    		g.drawImage(start, 0, 0, null);
    		break;
    	case PAUSE:
    		g.drawImage(pause, 0, 0, null);
    		break;
    	case GAMEOVER:
    		g.drawImage(gameover, 0, 0, null);
    	}
    }

	//生成敌人
	public FlyingObject nextOne(){
		Random rand=new Random();
		int type=rand.nextInt(20);
		if(type<4){
			return new BigBee();
		}else if (type==5||type==6) {
			return new Bee();
		}else{
			return new Airplane();
		}
	}

	int flyEnteredIndex=0;
	public void enterAction(){//10毫秒走一次
		//创建敌人对象
		//将敌人对象添加到FlyingObject中
		flyEnteredIndex++;
		if(flyEnteredIndex%40==0){
			FlyingObject obj=nextOne();//获取敌人对象
			flyings=Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1]=obj;
		}
	}
	//子弹入场
	int shootIndex=0;
	public void shootAxtion(){
		//获取子弹对象
		//添加到bullets数组中
		shootIndex++;
		if (shootIndex%30==0) {
		   Bullet[] bs=hero.shoot();
		   bullets=Arrays.copyOf(bullets, bullets.length+bs.length);
		   System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);//bs的追加
		} 
	}
	//飞行物走一步
	public void stepAction(){
		hero.step();
		for(int i=0;i<flyings.length;i++){
			FlyingObject f=flyings[i];
			f.step();
		}
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			b.step();
		}
	}
	//删除越界的敌人和子弹
	public void outOfBoundsAction(){
		int index=0;//不越界的敌人数组系下表
		FlyingObject[] flyinglives=new FlyingObject[flyings.length];
		for(int i=0;i<flyings.length;i++){
			FlyingObject f=flyings[i];
			if (!f.outOfBounds()) {//不越界
				flyinglives[index]=f;
				index++;
			} 	
		}
		flyings=Arrays.copyOf(flyinglives, index);//将不越界的值赋给flyings
		index=0;//归零
		Bullet[] bulletLives=new Bullet[bullets.length];//不越界的子弹数
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			if(!b.outOfBounds()){
				bulletLives[index]=b;
				index++;
			}			
		}
		bullets=Arrays.copyOf(bulletLives, index);
	}
	//子弹与敌人相撞
	//所有子弹和所有敌人撞
	int j=0;
	public void bangAction(){
		for(int i=0;i<bullets.length;i++){
			bang(bullets[i],i);
		}
	}
	//一个子弹和与所有敌人撞
	int score=0;
	
	public void bang(Bullet b,int bu){
		int index=-1;
		for(int i=0;i<flyings.length;i++){
			FlyingObject f=flyings[i];
			if (f.shootBy(b)) {//撞上了
				index=i;
				break;//其余的敌人不比较
			} 
			
		}
		if(index!=-1){
			FlyingObject one=flyings[index];
			if(one instanceof Airplane){//是敌人
				Enemy e=(Enemy)one;//强转类型
				score+=e.getScore();
				FlyingObject t=flyings[index];
				flyings[index]=flyings[flyings.length-1];
				flyings[flyings.length-1]=t;
				flyings=Arrays.copyOf(flyings, flyings.length-1);//去掉被子弹撞上的敌人
			}
			if(one instanceof BigBee){
				j++;			
				if(j==5){
					Enemy e=(Enemy)one;
					score+=e.getScore();
					j=0;
					FlyingObject t=flyings[index];
					flyings[index]=flyings[flyings.length-1];
					flyings[flyings.length-1]=t;
					flyings=Arrays.copyOf(flyings, flyings.length-1);//去掉被子弹撞上的敌人
				}
			}
			if(one instanceof Award){//是奖励
				Award a=(Award)one;
				int type=a.getType();
				switch(type){
				case Award.DOUBLE_FIRE://奖励为火力值
					hero.addDoubleFire();
					break;
				case Award.LIFE://奖励为生命
					hero.addLife();
					break;
				}
				FlyingObject t=flyings[index];
				flyings[index]=flyings[flyings.length-1];
				flyings[flyings.length-1]=t;
				flyings=Arrays.copyOf(flyings, flyings.length-1);//去掉被子弹撞上的敌人
			}
			
			Bullet tBullet=bullets[bu];
			bullets[bu]=bullets[bullets.length-1];
			bullets[bullets.length-1]=tBullet;
			bullets=Arrays.copyOf(bullets, bullets.length-1);//去掉子弹
		}
	}
	
	//敌人和英雄机相撞
	public void checkGameOverAction(){
		if(isGameOver()){
			state=GAMEOVER;
		}
	}
	//判断游戏是否结束，返回trun结束
	public boolean isGameOver(){
		for(int i=0;i<flyings.length;i++){
			FlyingObject f=flyings[i];
			if(hero.hit(f)){//撞上
                hero.hitHero();
				hero.subtractLife();
				hero.clealDoubleFire();
				FlyingObject t=flyings[i];//将被撞敌人与最后的一个元素交换
				flyings[i]=flyings[flyings.length-1];
				flyings[flyings.length-1]=t;
				flyings=Arrays.copyOf(flyings, flyings.length-1);//去掉被子弹撞上的敌人
			}
		}
		return hero.getLife()<=0;//命数小于等于0结束
	}
	
	//启动程序的执行
	public void action(){
		MouseAdapter l=new MouseAdapter() {
			//重写鼠标移动事件
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
				int x=e.getX();//获取鼠标的x坐标
				int y=e.getY();
				hero.moveTo(x, y);
				}
			}
			//鼠标点击事件
			public void mouseClicked(MouseEvent e){
				switch(state){
				case START:
					state=RUNNING;
					break;
				case GAMEOVER:
					state=START;
					//清理现场
					score=0;
					hero=new Hero();
					flyings=new FlyingObject[0];
					bullets=new Bullet[0];
					break;
				}
			}
			//鼠标移除
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state=PAUSE;
				}
			}
			//鼠标移入
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state=RUNNING;
				}
			}
		};
		this.addMouseListener(l);//处理鼠标操作事件
		this.addMouseMotionListener(l);//鼠标的滑动
		Timer timer=new Timer();//定时器
		int intervel=10;//定时间隔（以毫秒为单位）
		timer.schedule(new TimerTask() {
			public void run(){
				if(state==RUNNING){
					enterAction();//敌人入场
					stepAction();//敌人移动
					shootAxtion();//子弹入场
					bangAction();//子弹与敌人相撞
					outOfBoundsAction();//删除越界的敌人和子弹
					checkGameOverAction();//敌人和英雄机相撞					
				}
				repaint();//重画
			}
		},intervel,intervel);//第一个要求timertask类型；第二个是开始到第一次触发的间隔；第三个是第二次到第三次触发的间隔
	}



    public static void main(String[] args) {
		JFrame frame=new JFrame("fly");
		ShootGame game=new ShootGame();
		frame.add(game);//代表把面板添加到窗口上
		frame.setSize(WIDTH,HEIGHT);
		frame.setAlwaysOnTop(true);//设置窗体一直据上
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置默认关闭操作，窗体关闭程序结束
		frame.setLocationRelativeTo(null);//设置窗体居中
		frame.setVisible(true);//设置窗体可见;尽快调用paint（）；
		
		game.action();
	}

}
