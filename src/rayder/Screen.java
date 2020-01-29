package rayder;

import java.util.ArrayList;
import java.awt.Color;

public class Screen {
    public int[][] map;
    public int mapWidth, mapHeight, width, height;
    public ArrayList<Texture>text;
        public Screen(int[][] m, int mapW, int mapH, ArrayList<Texture> tex, int w, int h){
        map = m;
	mapWidth = mapW;
	mapHeight = mapH;
	text = tex;
	width = w;
	height = h;  
    }
    public int[] getNewFrame(boolean gotKit,int mapNum,int[] pixels, double xDir, double yDir, double xPlane, double yPlane, double xPos, double yPos){
        int cou=0;
        Color top=Color.DARK_GRAY;
        double rt=top.getRed();
        double gt=top.getGreen();
        double bt=top.getBlue();
        Color bot=Color.DARK_GRAY;
        double rb=0;
        double gb=0;
        double bb=0;
        for(int n=0; n<pixels.length/2; n++) {
                            cou++;
                            if(cou==800){
                                cou=0;
                                if(rt>=0.4)rt-=0.4;
                                if(gt>=0.4)gt-=0.4;
                                if(bt>=0.4)bt-=0.4;
                            }
                            Color topN=new Color((int)rt,(int)gt,(int)bt);
                            pixels[n] = topN.getRGB();
                        
		}
		for(int i=pixels.length/2; i<pixels.length; i++){
                            cou++;
                            if(cou==800){
                                cou=0;
                                if(rb<=64)rb+=0.6;
                                if(gb<=64)gb+=0.6;
                                if(bb<=64)bb+=0.6;
                            }
                            Color botN=new Color((int)rb,(int)gb,(int)bb);
                            pixels[i] = botN.getRGB();
                       
		}
	    
	    for(int x=0; x<width; x=x+1) {
		    double cameraX = 2 * x / (double)(width) -1;
		    double rayDirX = xDir +  xPlane * cameraX;
		    double rayDirY =  yDir +  yPlane * cameraX;
		    
		    int mapX = (int) xPos;
		    int mapY = (int) yPos;
                    
		    double sideDistX;
		    double sideDistY; //length of ray to next side
                    if(x%2==0&&mapNum==1){
                        if(!gotKit)map[18][8]=0;
                        map[6][10]=0;
                    }
                    else if (x%2!=0&&mapNum==1) {
                        if(!gotKit)map[18][8]=20;
                        map[6][10]=19;
                    }
		    double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX)); //length of ray between sides
		    double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
		    double perpWallDist;
		   
		    int stepX, stepY; // x y directions
		    boolean hit = false;
		    int side=0; //was the wall horizontal or vertical
		    
		    if (rayDirX < 0){
		    	stepX = -1;
		    	sideDistX =(xPos-mapX)*deltaDistX; //finds step direction and initial dist
		    }
		    else{
		    	stepX=1;
		    	sideDistX =(mapX+1.0-xPos)*deltaDistX;
		    }
		    if (rayDirY<0){
		    	stepY=-1;
		        sideDistY=(yPos-mapY)*deltaDistY;
		    }
		    else{
		    	stepY=1;
		        sideDistY=(mapY+1.0-yPos)*deltaDistY;
		    }
		    
		    while(!hit) { //finds where ray hits wall
		    	
		    	if (sideDistX<sideDistY){ // jumps between squares
		    		sideDistX += deltaDistX;
		    		mapX += stepX;
		    		side = 0;
		        }
		        else{
		        	sideDistY += deltaDistY;
		        	mapY += stepY;
		        	side = 1;
		        }
                        
		    	if(map[mapX][mapY]>0) hit = true; //check if wall is hit

		    }
		    
		    if(side==0)
		    	perpWallDist = Math.abs((mapX -  xPos + (1 - stepX) / 2) / rayDirX); //distance to the wall hit
		    else
		    	perpWallDist = Math.abs((mapY -  yPos + (1 - stepY) / 2) / rayDirY);	
                    
		    int lineHeight;
		    if(perpWallDist > 0) lineHeight = Math.abs((int)(height / perpWallDist)); // calculates wall height based on distance
		    else lineHeight = height;
		    int drawStart = -lineHeight/2+ height/2; //calculates lowest and highest pixel in vertical strip
		    if(drawStart < 0)
		    	drawStart = 0;
		    int drawEnd = lineHeight/2 + height/2;
		    if(drawEnd >= height) 
		    	drawEnd = height - 1;
		    
		    int texNum = map[mapX][mapY] - 1; //for textures
		    double wallX;//Exact position of where wall was hit
		    if(side==1) {//If its a y-axis wall
		    	wallX = ( xPos + ((mapY -  yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
		    } else {//X-axis wall
		    	wallX = ( yPos + ((mapX -  xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
		    }
		    wallX-=Math.floor(wallX);
		    
		    int texX = (int)(wallX * (text.get(texNum).SIZE)); //x coordinate on the texture image
		    if(side == 0 && rayDirX > 0) texX = text.get(texNum).SIZE - texX - 1;
		    if(side == 1 && rayDirY < 0) texX = text.get(texNum).SIZE - texX - 1;
		    
		    for(int y=drawStart; y<drawEnd; y++) {  //calculate y coordinate on texture
		    	int texY = (((y*2 - height + lineHeight) << 6) / lineHeight) / 2; 
		    	int color=0;
                        Color mycolor=new Color(text.get(texNum).pixels[texX + (texY * text.get(texNum).SIZE)]);
                        double r=0,g=0,b=0;
                        
                            r=mycolor.getRed()/(perpWallDist*0.8);
                            g=mycolor.getGreen()/(perpWallDist*0.8);
                            b=mycolor.getBlue()/(perpWallDist*0.8);
                            if(r>=256)r=240;
                            if(g>=256)g=240;
                            if(b>=256)b=240;
                        

                        Color newCol=new Color((int)r,(int)g,(int)b);
                        color=newCol.getRGB();
                        
		    	if(side==0)color = color;
		    	else {
                            if(perpWallDist>1){
                            r=mycolor.getRed()*0.8;
                            g=mycolor.getGreen()*0.8;
                            b=mycolor.getBlue()*0.8;
                        }
                        else {
                            r=mycolor.getRed();
                            g=mycolor.getGreen();
                            b=mycolor.getBlue();
                        }
                        }
                        
                        pixels[x + y*(width)] = color;
		    }
                    
		}
           
		return pixels;
    }
    public int[] gunShot(int[] hit,double xDir, double yDir, double xPlane, double yPlane, double xPos, double yPos){
           //same raycasting technique; used for a bullet
           hit = new int[3];
           int x=width/2; 
           double cameraX = 2 * x / (double)(width) -1;
		    double rayDirX = xDir +  xPlane * cameraX;
		    double rayDirY =  yDir +  yPlane * cameraX;
		    int mapX = (int) xPos;
		    int mapY = (int) yPos;
		    double sideDistX;
		    double sideDistY;
                    double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX));
		    double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));

                    int stepX, stepY;
		    boolean hit1 = false;
                    
		    if (rayDirX < 0){
		    	stepX = -1;
		    	sideDistX = ( xPos - mapX) * deltaDistX;
		    }
		    else{
		    	stepX = 1;
		    	sideDistX = (mapX + 1.0 -  xPos) * deltaDistX;
		    }
		    if (rayDirY < 0){
		    	stepY = -1;
		        sideDistY = ( yPos - mapY) * deltaDistY;
		    }
		    else{
		    	stepY = 1;
		        sideDistY = (mapY + 1.0 -  yPos) * deltaDistY;
		    }
		    while(!hit1) {
		    	if (sideDistX < sideDistY){
		    		sideDistX += deltaDistX;
		    		mapX += stepX;
		        }
		        else{
		        	sideDistY += deltaDistY;
		        	mapY += stepY;
		        }                        
		    	if(map[mapX][mapY] > 0) {
                            hit1 = true;
                            hit[0]=map[mapX][mapY];
                            hit[1]=mapX;
                            hit[2]=mapY;
                        }

		    }
        return hit;
    }

    
   
}
