package at.htlstp.bejinariu.launch;

import java.awt.Toolkit;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * Bejinariu Alexandru Klasse: 3AHIF AufnahmeNummer: 20130041 Katalognummer: 1
 */
public class Intro_Slide_FX {

    private long millies;
    private Stage myStage;
    private double ALTERNATIVE_START_X,
            ALTERNATIVE_START_Y,
            ALTERNATIVE_END_X,
            ALTERNATIVE_END_Y;
    private final Timeline visualAnimation = new Timeline();
    private Position stageStartPosition;
    private double START_X, START_Y;
    private double END_X, END_Y;
    private final DoubleProperty yProperty = new SimpleDoubleProperty(0);
    private final DoubleProperty xProperty = new SimpleDoubleProperty(0);

    public Intro_Slide_FX(long millies, Stage myStage, double ALTERNATIVE_START_X, double ALTERNATIVE_START_Y, double ALTERNATIVE_END_X, double ALTERNATIVE_END_Y) {
        this(millies, myStage, Position.NONE);
        this.ALTERNATIVE_START_X = ALTERNATIVE_START_X;
        this.ALTERNATIVE_START_Y = ALTERNATIVE_START_Y;
        this.ALTERNATIVE_END_X = ALTERNATIVE_END_X;
        this.ALTERNATIVE_END_Y = ALTERNATIVE_END_Y;

    }

    public Intro_Slide_FX(long millies, Stage myStage, Position stageStartPosition) {
        this.millies = millies;
        this.myStage = myStage;
        this.stageStartPosition = stageStartPosition;
    }

    public void slideAndShowStage() {
        myStage.setOpacity(0.0);
        myStage.show();

        double SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double STAGE_W = myStage.getWidth();
        double STAGE_H = myStage.getHeight();

        myStage.setFullScreen(false);
        myStage.setResizable(false);

        calculate(SCREEN_H, SCREEN_W, STAGE_H, STAGE_W);
        myStage.setY(START_Y);
        myStage.setX(START_X);
        xProperty.set(START_X);
        yProperty.set(START_Y);

        KeyValue kv_mv_y = new KeyValue(yProperty, END_Y);
        KeyValue kv_mv_x = new KeyValue(xProperty, END_X);
        KeyValue kv_opa = new KeyValue(myStage.opacityProperty(), 1);

        KeyFrame kf = new KeyFrame(Duration.millis(millies), kv_opa, kv_mv_y, kv_mv_x);

        yProperty.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            myStage.setY((Double) newValue);
        });

        xProperty.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            myStage.setX((Double) newValue);
        });
        visualAnimation.getKeyFrames().add(kf);
        visualAnimation.play();

    }

    private void calculate(double SCREEN_H, double SCREEN_W, double STAGE_H, double STAGE_W) {
        END_Y = SCREEN_H / 2 - STAGE_H / 2;
        END_X = SCREEN_W / 2 - STAGE_W / 2;
        switch (stageStartPosition) {
            case TOP:
                START_Y = 0;
                START_X = SCREEN_W / 2 - STAGE_W / 2;
                break;
            case BOTTOM:
                START_Y = SCREEN_H - STAGE_H;
                START_X = SCREEN_W / 2 - STAGE_W / 2;
                break;
            case RIGHT:
                START_Y = SCREEN_H / 2 - STAGE_H / 2;
                START_X = SCREEN_W - STAGE_W;
                break;
            case LEFT:
                START_Y = SCREEN_H / 2 - STAGE_H / 2;
                START_X = 0;
                break;
            case MID:
                START_Y = SCREEN_H / 2 - STAGE_H / 2;
                START_X = SCREEN_W / 2 - STAGE_W / 2;
                break;
            case NONE:
                START_X = ALTERNATIVE_START_X;
                START_Y = ALTERNATIVE_START_Y;
                END_X = ALTERNATIVE_END_X;
                END_Y = ALTERNATIVE_END_Y;
        };
    }

    public static enum Position {
        RIGHT, LEFT, TOP, BOTTOM, NONE, MID;
    }
}
