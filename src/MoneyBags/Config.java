package MoneyBags;

public class Config {

    private int timeInSec, moneyInBag, numberOfMoneyBags;
    private int speedInPixels;
    private String briefCaseImageFile;

    public Config()
    {
        //1.  Change the timer to give your player more time
        timeInSec = 15;

        //2.  Every cycle of the animation (60x per sec) the sprite will
        //    move the number of pixels below.  Increase and decrease the
        //    the value to see the impact on the game
        speedInPixels = 20;


        //3.  Everyone loves more money, change the variable called moneyInBag
        //    to increase the amount of money earned each time a bag is touched
        moneyInBag = 100;


        //4.  A briefcase is old school, try replacing the file with
        //    "Images/ufo_0.png"
        briefCaseImageFile = "Images/briefcase.png";

        //5.  We can control the amount of bags of money randomly created on
        //    the scene.  Try changing the variable below up and down to see the
        //    impact
        numberOfMoneyBags = 30;
    }

    public int getSpeedInPixels() {
        return speedInPixels;
    }

    public int getNumberOfMoneyBags() {
        return numberOfMoneyBags;
    }

    public String getBriefCaseImageFile() {
        return briefCaseImageFile;
    }

    public int getTimeInSec() {
        return timeInSec;
    }

    public int getMoneyInBag() {
        return moneyInBag;
    }
}
