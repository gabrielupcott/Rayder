
package rayder;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.Scanner;
public class Item {
    public double itemXpos=0;
    public double itemYpos=0;
    public double itemWs=0;
    public double itemHs=0;
    public double itemDXs=0;
    public int itemType=0;
    public Item(double itemX,double itemY,double itemW,double itemH,double itemDX, int type){
        itemXpos=itemX;
        itemYpos=itemY;
        itemWs=itemW;
        itemHs=itemH;
        itemDXs=itemDX;
        itemType=type;
    }
    
    public static Item[] getSpriteList(int mapNum)throws FileNotFoundException{
        String file="spawns/map"+mapNum+"items.txt";
        Scanner spawnData=new Scanner(new File(file));
        
        int totSpawns=Integer.parseInt(spawnData.nextLine());
        Item[] spriteList=new Item[totSpawns];
        
        int intA=0;
        while(spawnData.hasNextLine()){
            int type=Integer.parseInt(spawnData.nextLine());
            double xp=Double.parseDouble(spawnData.next());
            double yp=Double.parseDouble(spawnData.next());
            spriteList[intA]=new Item(xp,yp,itemDetails(type,1),itemDetails(type,2),0,type);
            intA++;
        }
        
        return spriteList;
    }

    private static double itemDetails(int type, int num){ //stores height and width information about sprites
        double value=0;
        if(type==1){ //if the sprite is a guard
            if(num==1)return 64;//for height of sprite
            else return 64; //for width
        }
        return 0;
    }
}
