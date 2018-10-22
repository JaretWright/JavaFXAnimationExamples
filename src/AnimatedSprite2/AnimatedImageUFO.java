package AnimatedSprite2;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.animation.AnimationTimer;


// Animation of Earth rotating around the sun. (Hello, world!)
public class AnimatedImageUFO extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "AnimatedImage Example" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image( "Images/earth.png" );
        Image sun   = new Image( "Images/sun.png" );
        Image space = new Image( "Images/space.png" );

        AnimatedImage ufo = new AnimatedImage();
        Image[] imageArray = new Image[6];
        for (int i = 0; i < 6; i++)
            imageArray[i] = new Image( "Images/ufo_" + i + ".png" );
        ufo.frames = imageArray;
        ufo.duration = 0.100;

        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                gc.drawImage( space, 0, 0 );
                gc.drawImage( earth, x, y );
                gc.drawImage( sun, 196, 196 );
                gc.drawImage( ufo.getFrame(t), x+50, y+50 );
            }
        }.start();

        theStage.show();
    }
}