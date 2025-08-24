import java.io.File;

public class AreaCalculator {

    public static double calculateArea(Circle circle) {
        double radius = circle.getRadius();
        return Math.PI * radius * radius;
    }
}