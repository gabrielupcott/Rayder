package rayder;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import org.newdawn.slick.util.BufferedImageUtil;
import java.io.IOException;
import java.util.Random;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Sound;

public class Game extends BasicGameState{
    private static StateBasedGame game;
    private boolean end = false;
    Image paused;
    public  int[][] map, damagezones;
    public  int mapNum=1;
    private BufferedImage img;
    Image winP,loseP,injure;
    public SpriteSheet guard, pistol;
    public int[] pixels;
    public Screen disp;
    public BufferStrategy bs;
    public ArrayList<Texture>text;
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public int gunX, gunY, shotcount;
    public double hp, time=0;
    public float inAl=1;
    public double perpXDir, perpYDir;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .045;
    public Animation pistolshot, pistolreload;
    public Sound pistolF, pistolE, pistolR, step1, death1, death2, death3, youdied, healthPack;
    public boolean move, shot, reloading, winG, lose, gotKit=false;
    public int co=0, kills=0, doubleK=0;
    public Random rand = new Random();

    
    public Game(int stateID){        
    }
   
    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
      //System.out.println("START");
      game = sbg;
      winP = new Image("images/menu/win.png");
      loseP = new Image("images/menu/lose.png");
      injure = new Image("images/menu/injure.png");
      img=new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB);
      pixels=((DataBufferInt)img.getRaster().getDataBuffer()).getData();
      text=new ArrayList<Texture>();
      Texture wood = new Texture("wood",64);
      Texture brick = new Texture("brick",64);
      Texture bluestone = new Texture("bluestone",64);
      Texture stone = new Texture("stone",64);
      Texture bluecell = new Texture("bluecell",64);
      Texture achtung = new Texture("achtung",64);
      Texture brickwall = new Texture("brickwall",64);
      Texture bluewall = new Texture("bluewall",64);
      Texture cbble = new Texture("cbble",64);
      Texture mdoor = new Texture("metaldoor",64); ////wall textures
      Texture win = new Texture("win",64);
      Texture healthP = new Texture("health",64);
      Texture brickwallguard = new Texture("brickwallguard",64); ////guard sprite test
      Texture achtungguard = new Texture("achtungguard",64);
      Texture bluewallguard = new Texture("bluewallguard",64);
      Texture bluecellguard = new Texture("bluecellguard",64);
      Texture deadbrickwallguard = new Texture("deadbrickwallguard",64); ////guard sprite test
      Texture deadachtungguard = new Texture("deadachtungguard",64);
      Texture deadbluewallguard = new Texture("deadbluewallguard",64);
      Texture deadbluecellguard = new Texture("deadbluecellguard",64);
      time=0;
      text.add(wood);text.add(bluestone);//1,2
      text.add(brick);text.add(stone);//3,4
      text.add(bluecell); text.add(achtung); //5,6
      text.add(brickwall); text.add(cbble); //7,8
      text.add(bluewall); text.add(mdoor); //9,10
      text.add(brickwallguard); text.add(bluewallguard);//11,12
      text.add(bluecellguard); text.add(achtungguard);//13,14
      text.add(deadbrickwallguard); text.add(deadbluewallguard);//15,16
      text.add(deadbluecellguard); text.add(deadachtungguard);//17,18
      text.add(win); text.add(healthP);//18,19
      
      pistolF = new Sound("sounds/Pistol.ogg");
      pistolE = new Sound("sounds/emptyP.ogg");
      pistolR = new Sound("sounds/9mmclip1.ogg");
      step1 = new Sound("sounds/boots1.ogg");
      death1 = new Sound("sounds/death1.ogg");
      death2 = new Sound("sounds/death2.ogg");
      death3 = new Sound("sounds/death3.ogg");
      youdied = new Sound("sounds/scream02.ogg");
      healthPack = new Sound("sounds/smallmedkit1.ogg");
      guard = new SpriteSheet("images/sprites/guardsheet.png",64,64,1);
      pistol = new SpriteSheet("images/sprites/pistspritesHD.png",128,128);
      pistolshot = new Animation(pistol, 25);
      pistolreload = new Animation(new SpriteSheet("images/sprites/reloadpistol.png",128,128),100);
      pistolshot.stopAt(5);
      pistolreload.stopAt(5);
      
      
      try{
          map=Texture.getMap(map,mapNum).clone();
          damagezones=Texture.getMap(damagezones,3).clone();
          for(int c=0;c<map.length;c++){
          }
      }
      catch(FileNotFoundException e){
          System.err.println(e.getMessage());
          System.out.println("FileNotFoundException");
    }
      String pos=Texture.getStartPos(mapNum);
      xPos=Double.parseDouble(pos.substring(0,pos.indexOf(" ")));
      yPos=Double.parseDouble(pos.substring(pos.indexOf(" ")+1));
      xDir=1;
      if(mapNum==2)yDir=1;
      else yDir=0;
      xPlane=0;
      yPlane=-.66;
      perpXDir=0;
      perpYDir=0;
      gunY=344;
      gunX=525;
      shotcount=0;
      move=false;
      reloading=false;
      disp=new Screen(map, map.length, map.length, text, 640, 480);
      hp=100;
      kills=0;
      doubleK=0;
      winG=false;
      lose=false;
      gotKit=false;
      mapNum=1;
      
      
    }
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        if(!winG&&!lose){
        try{
        org.newdawn.slick.opengl.Texture text1 = BufferedImageUtil.getTexture("", img); 
        Image slickImage = new Image(text1.getImageWidth(), text1.getImageHeight());
        slickImage.setTexture(text1);
        slickImage.draw(0,0,1.25f);

        }
        catch (IOException e){
                System.err.println(e);
        }
        
        
            
         
        
        g.drawLine(392, 300, 396, 300); g.drawLine(404, 300, 408, 300);
        g.drawLine(400, 292, 400, 296); g.drawLine(400,304, 400, 308);
        g.drawString(""+(12-shotcount)+"/12",750,550);
        g.drawString("HP: "+((int)hp),35,550);
        
        if(shot && reloading == false){ //if a shot has been fired
            pistolshot.draw(gunX,gunY,256,256);
            if(pistolshot.isStopped()){
                shot=false;
                pistolshot.restart();
                shotcount++;
            }
        }
        else if(reloading){ //reload gun
            pistolreload.draw(gunX,gunY,256,256);
            if(pistolreload.isStopped()){
            reloading=false;
            pistolreload.restart();
            } 
        }
        else pistol.getSprite(0,0).draw(gunX,gunY,2f); //no action
            
        if(damagezones[(int)xPos][(int)yPos]>0&&hp>0)hp-=(8-((double)damagezones[(int)xPos][(int)yPos]))*0.05;
        if(map[(int)xPos][(int)yPos]==20&&!gotKit){
            healthPack.play();
            if(hp<50)hp+=50;
            else hp=100;
            gotKit=true;
            map[(int)xPos][(int)yPos]=0;
        }
        
        if(hp<45){
            inAl=1-((float)hp/45);
            if(inAl<(float)0.13)injure.setAlpha(inAl);
            injure.draw(0,0);
        }
        

        
    }
       else if(winG){
            winP.draw(0,0);
            g.drawString(Integer.toString(kills), 450, 450);
            String timeS="Time: ";
            timeS+=Double.toString((time/1000));
            timeS+="s";
            g.drawString(timeS,450,500);
            g.drawString("Press Enter to Close",400,550);
            g.drawString("Dev Record: 15.157s - Noah",400,525);
        }
        else if (lose){
            loseP.draw(0,0);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(map[(int)xPos][(int)yPos]==19){
            winG=true;
            step1.stop();
        }
        if(hp<=0&&!lose){
            youdied.play(1,(float)0.1);
            lose=true;
        }
        if(!winG&&!lose){
        disp.getNewFrame(gotKit,mapNum,pixels,xDir,yDir,xPlane, yPlane, xPos, yPos);
        
        time+=(double)i;
        
        
        /*
        public void update(GameContainer container, int delta) {
time += delta;
}

public void render(GameContainer container, Graphics g) {
g.drawString("Time : " + time/1000, 100, 100);
}
        */
        
        Input input=gc.getInput();
        move=false;
        if(input.isKeyDown(Input.KEY_A)){
            move=true;
                       perpXDir=-yDir;
                       perpYDir=xDir;
                        if(map[(int)(xPos + perpXDir * MOVE_SPEED)][(int)yPos] < 1||map[(int)(xPos + perpXDir * MOVE_SPEED)][(int)yPos] > 18) xPos+=perpXDir*MOVE_SPEED*.65;
                        if(map[(int)(xPos)][(int)(yPos + perpYDir * MOVE_SPEED)] < 1||map[(int)(xPos)][(int)(yPos + perpYDir * MOVE_SPEED)] > 18) yPos+=perpYDir*MOVE_SPEED*.65;
                        if(gunX>521)gunX-=1;
        }
        if(input.isKeyDown(Input.KEY_D)){
                        move=true;
                        perpXDir=yDir;
                        perpYDir=-xDir;
                        if(map[(int)(xPos + perpXDir * MOVE_SPEED)][(int)yPos] < 1||map[(int)(xPos + perpXDir * MOVE_SPEED)][(int)yPos] >18) xPos+=perpXDir*MOVE_SPEED*.65;
                        if(map[(int)(xPos)][(int)(yPos + perpYDir * MOVE_SPEED)]< 1||map[(int)(xPos)][(int)(yPos + perpYDir * MOVE_SPEED)]>18) yPos+=perpYDir*MOVE_SPEED*.65;
                        if(gunX<529)gunX+=1;
        }
        if(input.isKeyDown(Input.KEY_W)){ //move forward
                        move=true;
                        if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] < 1||map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] > 18) xPos+=xDir*MOVE_SPEED;
			if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] < 1||map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] >18) yPos+=yDir*MOVE_SPEED;
                        if(gunY<352)gunY+=1;
                        
        }
        if(input.isKeyDown(Input.KEY_S)){ //move backwards
                        move=true;
                        if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] < 1||map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] >18) xPos-=xDir*MOVE_SPEED;
			if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]< 1||map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]>18) yPos-=yDir*MOVE_SPEED;
                        if(gunY<352)gunY+=1;
        }
        if(input.isKeyDown(Input.KEY_J)){ //look left
                        double oldxDir=xDir;
			xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
			yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
        }
        if(input.isKeyDown(Input.KEY_L)){ //look right
                        double oldxDir=xDir;
			xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
			yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
        }
        if(input.isKeyDown(Input.KEY_SPACE)){
            
            shot=true;
            int[] hit=disp.gunShot(new int[3],xDir,yDir,xPlane, yPlane, xPos, yPos);
            int info=hit[0];
            int hitX=hit[1];
            int hitY=hit[2];
            if(info>10&&info<15){ //hit enemy
                info+=4;
                kills++;
                int n = rand.nextInt(4);
                //.out.println(n);
                if(n==0)death1.play(1,(float)10);
                else if(n==2)death2.play(1,(float)10);
                else if(n==3)death3.play(1,(float)10);
                map[hitX][hitY]=info;
                if(hitX==7&&hitY==7){
                    for(int c=1;c<7;c++){
                        for(int d=3;d<9;d++){
                            damagezones[c][d]=8;
                        }
                    }
                }
                if(hitX==17){
                    for(int d=2;d<7;d++){
                        damagezones[18][d]=8;
                    }
                }
                if(hitX==19){
                    doubleK++;
                    if(doubleK==1)damagezones[14][16]=8;
                    if(doubleK==2){
                     for(int c=14;c<19;c++){
                        for(int d=16;d<20;d++){
                            damagezones[c][d]=8;
                        }
                    }
                    }
                }
                if(hitX==10&&hitY<10){
                     for(int c=9;c<12;c++){
                        for(int d=2;d<8;d++){
                            damagezones[c][d]=8;
                        }
                    }
                }
                if(hitX==10&&hitY>10){
                     for(int c=7;c<14;c++){
                        for(int d=12;d<20;d++){
                            damagezones[c][d]=8;
                        }
                    }
                }
                if(hitX==7&&hitY>7){
                    for(int c=2;c<7;c++){
                        for(int d=10;d<17;d++){
                            damagezones[c][d]=8;
                        }
                    }
                }
            }
        }
        
        if(shotcount==12){
            reloading=true;
            shotcount=0;
            pistolE.play();
            pistolR.play();
        }
        
        if(end)gc.exit(); 
        if(move&&co<1){
            co++;
            step1.loop(1,(float)0.5);
        }
        else if (!move){
            step1.stop();
            co=0;
        }
    }
        if(lose){
            Input input=gc.getInput();
        if(input.isKeyPressed(Input.KEY_T)){
            sbg.getState(0x1).init(gc, sbg);
            sbg.enterState(0x1);
        }
        }
        if(end)gc.exit(); 
    }
    
    
    
    @Override
    public void keyReleased(int key, char c) {
    if(!winG&&!lose){
        
    switch(key) {
    case Input.KEY_ESCAPE:
        game.enterState(0x2);
        break;
    case Input.KEY_N:
        mapNum++;
        try{
          map=Texture.getMap(map,mapNum);
          disp=new Screen(map, map.length, map.length, text, 640, 480);
          String pos=Texture.getStartPos(mapNum);
         xPos=Double.parseDouble(pos.substring(0,pos.indexOf(" ")));
         yPos=Double.parseDouble(pos.substring(pos.indexOf(" ")+1));
      }
      catch(FileNotFoundException e){
          System.err.println(e.getMessage());
          System.out.println("FileNotFoundException");
    }
        game.enterState(0x1,new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        break;
    case Input.KEY_W:
        gunY=344;
        break;
    case Input.KEY_S:
        gunY=344;
        break;
    case Input.KEY_A:
        gunX=525;
        break;
    case Input.KEY_D:
        gunX=525;
        break;
    case Input.KEY_R:
        if(shotcount!=0){
        reloading=true;
        pistolR.play();
        shotcount=0;
        }
        break;
        case Input.KEY_SPACE:
        pistolF.play();
        break;
    default:
        break;
    }
    }
    if(winG){
        switch(key){
            case Input.KEY_ENTER:
                end = true;
        break;
        
        default:
        break;
        }
    }
    if(lose){
       switch(key){
            case Input.KEY_Q:
                end = true;
        break;
        case Input.KEY_R:
        break;
        default:
        break;
        } 
    }
}
}