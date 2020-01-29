package rayder;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Rayder extends StateBasedGame {

    public static void main(String[] args)throws SlickException {
        AppGameContainer app = new AppGameContainer(new Rayder("Rayder"));
        app.setShowFPS(true);
        app.setDisplayMode(800,600,true);
        app.setMouseGrabbed(true);
        app.start();
    }
    public Rayder(String name){
        super(name);
    }
    @Override
    public void initStatesList(GameContainer gc)throws SlickException{
        addState(new Menu(0));
        addState(new Game(1));
        addState(new Paused(2));
    }
}
