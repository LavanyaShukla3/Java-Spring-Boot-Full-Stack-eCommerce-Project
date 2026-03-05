package car.example.constrctor.injection;

public class Car {
    private Specification specification;

    public void displayDetails(){
        System.out.println(specification.toString());
    }

    public Car(Specification specification) {
        this.specification = specification;
    }
}
