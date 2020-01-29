package rayder;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import java.awt.Font;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Menu extends BasicGameState{
    private static StateBasedGame game;
    private boolean end = false, controls=false;
    private Font UIFont1;
    private UnicodeFont uniFont;
    Image menu,h2p, tex;
    
   
public Menu(int stateID){
    
}
    
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
      menu = new Image("images/menu/menu.png");
      h2p = new Image("images/menu/how to play.png");
      
      game = sbg;
    }

    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       
      if(!controls)menu.draw(0,0);
      else h2p.draw(0,0);
       
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        
        if(end)gc.exit(); 
    }
    @Override
    public void keyReleased(int key, char c) {
    switch(key) {
    case Input.KEY_1:
        game.enterState(0x1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        break;
    case Input.KEY_2:
        controls=true;
        break;
    case Input.KEY_3:
        end = true;
        break;
    case Input.KEY_BACK:
        controls = false;
        break;  
    case Input.KEY_ENTER:
  
        break;
    default:
        break;
    }
}
}