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

public class Paused extends BasicGameState{
    private static StateBasedGame game;
    private boolean end = false;
    Image paused;
     @SuppressWarnings("empty-statement")
    public Paused(int stateID)throws SlickException{
    
    }
   @Override
    public void enter(GameContainer gc, StateBasedGame sg) throws SlickException {
       this.init(gc, sg);
       game=sg;
    }
    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
      paused = new Image("images/menu/paused.png");
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
      
        paused.draw(0,0);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(end)gc.exit(); 
    }
    
    @Override
    public void keyReleased(int key, char c) {
    switch(key) {
    case Input.KEY_ESCAPE:
        game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        break;
    case Input.KEY_Q:
        end=true;
        break;
    default:
        break;
    }
}
}
