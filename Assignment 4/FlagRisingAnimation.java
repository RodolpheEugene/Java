import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FlagRisingAnimation extends Application {
  private double Flag_speed = 7;
  private double flagY;
  @Override
  public void start(Stage primaryStage) {
    Pane pane = new Pane();
    
    ImageView imageView = new ImageView("image/us.gif");
    imageView.setX(25);
    double flagHeight = imageView.getLayoutBounds().getHeight();
    pane.getChildren().add(imageView);
    
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while (true) {
            if ((-flagHeight/2 - 200) < (imageView.getY()))
              flagY = (imageView.getY() - Flag_speed);
            else
              flagY = (200 + flagHeight/2);
            
          Platform.runLater(()-> imageView.setY(flagY));
          Thread.sleep(90);
          }
        }
        catch (InterruptedException ex) {
        }
      }
    }).start();


    Scene scene = new Scene(pane, 250, 200);
    primaryStage.setTitle("FlagRisingByPathTransition");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}