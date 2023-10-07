public class Ticket {

    int row;
    int seat;
    float price;
    Person person;

    public Ticket(int row, int seat, float price,Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public void Printinfo(){
        System.out.println("-----------------------------");
        System.out.println("Name: "+person.name);
        System.out.println("Surname: "+person.surName);
        System.out.println("Email: "+person.email);
        System.out.println("Row no: "+row);
        System.out.println("Seat no: "+seat);
        System.out.println("price: "+price);
        System.out.println("-----------------------------");
    }
}
