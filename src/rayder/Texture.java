
package rayder;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {
    public final int SIZE;
    public int[] pixels;
    
    public Texture(String name, int size){
        SIZE=size;
        pixels=new int[SIZE*SIZE];
        try{
            BufferedImage img=ImageIO.read(new File("images/textures/"+name+".png"));
            int w=img.getWidth();
            int h=img.getHeight();
            img.getRGB(0, 0, w, h, pixels, 0, w);
        }
        catch (IOException e){
            System.out.println("IOException");
            System.err.println(e);
        }
        
    }
    
    
    public static int[][] getMap(int[][] map, int mapnum)throws FileNotFoundException{
        String file="maps/map"+mapnum+".txt";
        Scanner mapdata=new Scanner(new File(file));
        int x=0, y=0;
        int size=Integer.parseInt(mapdata.nextLine());
        map=new int[size][size];
            while(mapdata.hasNext()){
                int val=Integer.parseInt(mapdata.next());
                map[x][y]=val;
                y++;
                if(y==size){
                   y=0;
                   x++;
                   //System.out.println();
                }
            }

        return map;
    }
    public static String getStartPos(int mapnum){
        String Pos="";
        if(mapnum==1)Pos="2.0 2.0";
        if(mapnum==2)Pos="2.0 2.0";
        return Pos;
    }
    
}
