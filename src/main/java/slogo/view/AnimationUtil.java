package slogo.view;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class AnimationUtil {
    public static Animation makeAnimation(Node agent, double d, double e, double heading) {
        // create something to follow
        Path path = new Path();
        path.getElements().addAll(
            new MoveTo(agent.getBoundsInParent().getMinX(), agent.getBoundsInParent().getMinY()),
            new LineTo(d, e));
        // create an animation where the shape follows a path
        PathTransition pt = new PathTransition(Duration.seconds(2), path, agent);
        // create an animation that rotates the shape
        RotateTransition rt = new RotateTransition(Duration.seconds(1));
        rt.setByAngle(heading);
        // put them together in order
        return new SequentialTransition(agent, pt, rt);
      }
}
