package MoneyBags;

import BullseyeMouseClicker.IntValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


// Collect the Money Bags!
public class MoneyBags extends Application
{
    private Config config;
    private Timeline timeline;
    static Timer timer;
    static int timeRemaining;

    public static void main(String[] args)
    {
        timer = new Timer();
        launch(args);
    }


    @Override
    public void start(Stage theStage)
    {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        config = new Config();
        timeRemaining=config.getTimeInSec();

        theStage.setTitle( "Collect the Money Bags!" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        theStage.getIcons().add(new Image("Images/moneybag.png"));

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        ArrayList<String> input = new ArrayList<String>();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                setTimeRemaining();
            }
        }, 1000, 1000);

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );
                    }
                });

        //Configure the font for the GraphicsContext
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setFill( Color.GREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        //Load the image of the briefcase
        //try adjusting the position of the briefcase (x,y)
        Sprite briefcase = new Sprite();
        briefcase.setImage(config.getBriefCaseImageFile());
        briefcase.setPosition(200, 00);

        //create an ArrayList of moneyBag images in random
        //locations around the scene
        ArrayList<Sprite> moneybagList = new ArrayList<Sprite>();
        getMoreMoney(moneybagList);

        //create variables to hold the time and score
        LongValue lastNanoTime = new LongValue( System.nanoTime() );
        IntValue score = new IntValue(0);

        //create a new AnimationTimer to redraw the scene
        //when the clock cycle happens
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 100000000.0;
                lastNanoTime.value = currentNanoTime;

                //This adjusts how fast the briefcase moves
                //try adjusting the numbers inside the brackets
                briefcase.setVelocity(0,0);
                if (input.contains("LEFT") && timeRemaining != 0)
                    briefcase.addVelocity(-config.getSpeedInPixels(),0);
                if (input.contains("RIGHT") && timeRemaining != 0)
                    briefcase.addVelocity(config.getSpeedInPixels(),0);
                if (input.contains("UP") && timeRemaining != 0)
                    briefcase.addVelocity(0,-config.getSpeedInPixels());
                if (input.contains("DOWN") && timeRemaining != 0)
                    briefcase.addVelocity(0,config.getSpeedInPixels());

                briefcase.update(elapsedTime);

                // collision detection
                Iterator<Sprite> moneybagIter = moneybagList.iterator();
                while (moneybagIter.hasNext())
                {
                    Sprite moneybag = moneybagIter.next();
                    if ( briefcase.intersects(moneybag) )
                    {
                        moneybagIter.remove();
                        score.value++;
                    }
                }

                //if they run out of money, add more bags
                if (moneybagList.size() == 0)
                    getMoreMoney(moneybagList);

                // render
                gc.clearRect(0, 0, 512,512);
                briefcase.render(gc);

                for (Sprite moneybag : moneybagList )
                    moneybag.render(gc);

                //Update the cash collected
                String pointsText = "Cash: $" + (config.getMoneyInBag() * score.value);
                gc.setFill( Color.GREEN);
                gc.fillText( pointsText, 360, 36 );
                gc.strokeText( pointsText, 360, 36 );

                String timerText = "Timer: " + timeRemaining;
                gc.setFill( Color.RED );
                gc.fillText( timerText, 360, 76 );
                gc.strokeText( timerText, 360, 76 );
            }
        }.start();

        theStage.show();
    }

    private static final int setTimeRemaining() {
        if (timeRemaining == 1)
            timer.cancel();
        return --timeRemaining;
    }

    private void getMoreMoney(ArrayList<Sprite> moneybagList) {
        for (int i = 0; i < config.getNumberOfMoneyBags(); i++) {
            Sprite moneybag = new Sprite();
            moneybag.setImage("Images/moneybag.png");
            double px = 350 * Math.random() + 50;
            double py = 350 * Math.random() + 50;
            moneybag.setPosition(px, py);
            moneybagList.add(moneybag);
        }
    }

}