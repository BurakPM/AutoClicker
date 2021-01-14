import java.awt.*;

public class Clicker {
    private Robot robot;
    private static final int CLICK_DELAY = 100;
    private static final int RELEASE_DELAY = 100;

    public Clicker() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void clickButton(int button) {
        robot.mousePress(button);
        robot.delay(RELEASE_DELAY);
        robot.mouseRelease(button);
        robot.delay(CLICK_DELAY);
    }
}
