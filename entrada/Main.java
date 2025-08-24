public class Main {
    public static void main(String[] args) {
        Circle myCircle = new Circle(5.0);

        double area = AreaCalculator.calculateArea(myCircle);

        System.out.println("O raio do círculo é: " + myCircle.getRadius());
        System.out.println("A área do círculo é: " + area);
    }
}