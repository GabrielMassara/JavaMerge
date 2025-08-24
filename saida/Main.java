// Arquivo gerado automaticamente, Feito por MergeJava
// Repositório: https://github.com/GabrielMassara/JavaMerge.git

//===== INÍCIO DOS IMPORTS =====
import java.io.File;
import java.nio.file.Paths;
//===== FIM DOS IMPORTS =====

//===== INICIO DO ARQUIVO: AreaCalculator.java =====

class AreaCalculator {

    public static double calculateArea(Circle circle) {
        double radius = circle.getRadius();
        return Math.PI * radius * radius;
    }
}
//===== FIM DO ARQUIVO: AreaCalculator.java =====

//===== INICIO DO ARQUIVO: Circle.java =====

class Circle {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
//===== FIM DO ARQUIVO: Circle.java =====

//===== INICIO DO ARQUIVO: Main.java =====

public class Main {
    public static void main(String[] args) {
        Circle myCircle = new Circle(5.0);

        double area = AreaCalculator.calculateArea(myCircle);

        System.out.println("O raio do cÃ­rculo Ã©: " + myCircle.getRadius());
        System.out.println("A Ã¡rea do cÃ­rculo Ã©: " + area);
    }
}
//===== FIM DO ARQUIVO: Main.java =====

