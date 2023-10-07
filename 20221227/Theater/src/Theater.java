import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

    public class Theater {
        static Scanner input = new Scanner(System.in);
        static int[] row_1 = new int[12];
        static int[] row_2 = new int[16];
        static int[] row_3 = new int[20];
        static ArrayList<Ticket> ticketslist = new ArrayList<Ticket>();

        static boolean quit = true;

        public static void main(String[] args) {
            arrayCreator(12, row_1);
            arrayCreator(16, row_2);
            arrayCreator(20, row_3);

            while (quit) {
                while (true) {
                    /*Scanner input = new Scanner(System.in);
                     */
                    System.out.println("\n \n Welcome to the theatre");

                    System.out.println("--------------------------------------------");
                    System.out.println("Please select an option");
                    System.out.println("1) Buy a ticket");
                    System.out.println("2) Print seating area");
                    System.out.println("3) Cancel ticket");
                    System.out.println("4) list available seats");
                    System.out.println("5) Save to file");
                    System.out.println("6) Load from file");
                    System.out.println("7) Print ticket information ");
                    System.out.println("8) Sort ticket by price");
                    System.out.println("0) Quit");
                    System.out.println("----------------------------------------------");

                    System.out.print("Enter your option: ");////check the option
                    int option = inputData_checker(0, 8);

                    if (option == 1) {
                        buy_ticket();
                    } else if (option == 2) {
                        print_seating_area();
                    } else if (option == 3) {
                        cancel_ticket();
                    } else if (option == 4) {
                        show_available(row_1, 12, 1);
                        show_available(row_2, 16, 2);
                        show_available(row_3, 20, 3);
                    } else if (option == 5) {
                        save_to_file();
                    } else if (option == 6) {
                        load();
                    } else if (option == 7) {
                        showTicketInfo();
                    }else if(option==8){
                        sortTicket();
                    } else if (option == 0) {
                        System.out.println("Goodbye!! Have a nice day.");
                        quit = false;
                        break;
                    }
                }

            }
        }

        public static void arrayCreator(int size, int[] row) {
            for (int i = 0; i < size; i++) {
                row[i] = 0;
            }
        }///to create arrays

        public static int inputData_checker(int min, int max) {
            int data;
            while (true) {
                try {
                    data = input.nextInt();
                    if (min <= data && data <= max) {
                        return data;
                    } else {
                        System.out.println("Out of range");
                        System.out.print("Please enter a valid integer between" + min + "-" + max + ":");////////*****************
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid integer");
                    input.nextInt();
                }
            }
        }///integer input checker

        public static boolean seatcheaker(int row, int seat) {
            if (row == 1) {
                if (row_1[seat - 1] == 0) {
                    return true;
                }
            } else if (row == 2) {
                if (row_2[seat - 1] == 0) {
                    return true;
                }
            } else {
                if (row_3[seat - 1] == 0) {
                    return true;
                }
            }
            return false;
        }

        public static String userInput(String promt) {
            System.out.print(promt);
            String data = input.next();
            return data;
        }///string inputs checker

        public static void seatBooker(int row, int seat, boolean book) {
            if (book) {
                if (row == 1) {
                    row_1[seat - 1] = 1;
                } else if (row == 2) {
                    row_2[seat - 1] = 1;
                } else if (row == 3) {
                    row_3[seat - 1] = 1;
                }
            } else {
                if (row == 1) {
                    row_1[seat - 1] = 0;
                } else if (row == 2) {
                    row_2[seat - 1] = 0;
                } else if (row == 3) {
                    row_3[seat - 1] = 0;
                }
            }
        }

        public static void buy_ticket() {

            while (true) {
                System.out.print("Enter the row number: ");
                int row_numb = inputData_checker(1, 3);

                System.out.print("Enter the seat number: ");
                int seatnum;
                if (row_numb == 1) {
                    seatnum = inputData_checker(1, 12);//validate inputs
                } else if (row_numb == 2) {
                    seatnum = inputData_checker(1, 16);
                } else {
                    seatnum = inputData_checker(1, 20);
                }
                if (seatcheaker(row_numb, seatnum)) {
                    seatBooker(row_numb, seatnum, true);

                    System.out.print("Enter seat price: ");
                    float price = inputData_checker(1, 99999);

                    String name = userInput("Enter your name:");
                    String surname = userInput("Enter your surname:");
                    String email = userInput("Enter your email:");

                    Person person = new Person(name, surname, email);
                    Ticket ticket = new Ticket(row_numb, seatnum, price, person);
                    ticketslist.add(ticket);

                    System.out.println("Thank you for buying row-" +row_numb+ ":seat " + seatnum);
                    System.out.println("--------------------------------------------------------");
                    if (userInput("Do you want to buy a another ticket(y/n):").equalsIgnoreCase("y")) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    System.out.println("This Seat is booked,book another one.");
                }

            }
        }

        public static void cancel_ticket() {////////////////////////////////////////////////////////////////////////////////////////////

            System.out.print("Enter the row number: ");
            int row_numb = inputData_checker(1, 3);

            System.out.print("Enter the seat number: ");
            int seatnum;
            if (row_numb == 1) {
                seatnum = inputData_checker(1, 12);
            } else if (row_numb == 2) {
                seatnum = inputData_checker(1, 16);
            } else {
                seatnum = inputData_checker(1, 20);
            }
            if (!seatcheaker(row_numb, seatnum)) {
                seatBooker(row_numb, seatnum, false);
                int count = 0;
                for (Ticket ticket : ticketslist) {
                    if (ticket.row == row_numb && ticket.seat == seatnum) {
                        ticketslist.remove(count);
                        System.out.println("Ticket row:" + row_numb + " seat:" + seatnum + " cancel successful.");
                        break;
                    }
                    count++;

                }
            } else {
                System.out.println("Seat is not booked.");
            }

        }

        public static void print_seating_area() { ////////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\t" + "************");
            System.out.println("\t" + "*   STAGE  *");
            System.out.println("\t" + "************");

            System.out.print("    ");
            for (int i = 0; i < row_1.length; i++) {
                if (row_1[i] == 0 && i == (row_1.length / 2) - 1) {////divide row
                    System.out.print("0 ");
                } else if (row_1[i] == 1 && i == (row_1.length / 2) - 1) {///divide row
                    System.out.print("x ");
                } else if (row_1[i] == 0) {
                    System.out.print("0");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
            System.out.print("  ");
            for (int i = 0; i < row_2.length; i++) {
                if (row_2[i] == 0 && i == (row_2.length / 2) - 1) {
                    System.out.print("0 ");
                } else if (row_2[i] == 1 && i == (row_2.length / 2) - 1) {
                    System.out.print("x ");
                } else if (row_2[i] == 0) {
                    System.out.print("0");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();

            for (int i = 0; i < row_3.length; i++) {
                if (row_3[i] == 0 && i == (row_3.length / 2) - 1) {
                    System.out.print("0 ");
                } else if (row_3[i] == 1 && i == (row_3.length / 2) - 1) {
                    System.out.print("x ");
                } else if (row_3[i] == 0) {
                    System.out.print("0");
                } else {
                    System.out.print("X");
                }
            }
        }

        public static void show_available(int[] row, int size, int rowNum) {
            System.out.print("Seats available in row " + rowNum + ":");
            for (int i = 0; i < size; i++) {
                if (row[i] == 0) {
                    System.out.print(i + 1 + ",");
                }
            }
            System.out.println();
        }

        public static void save_to_file() {
            int[][] list = new int[][]{row_1, row_2, row_3};
            try {
                FileWriter writer = new FileWriter("seatdetails.txt");
                writer.write("");
                for (int[] i : list) {
                    for (int j : i) {
                        writer.append(Integer.toString(j));
                    }
                    writer.append("\n");

                }
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public static void arrayCreator(ArrayList<Integer> Row, int[] row) {
            int count = 0;
            for (int seat : Row) {
                row[count] = seat;
                count++;
            }
        }

        public static void load() {

            ArrayList<Character> read_data = new ArrayList<Character>();
            int count = 1;
            String conveted_char_value;
            ArrayList<Integer> Row1 = new ArrayList<Integer>();
            ArrayList<Integer> Row2 = new ArrayList<Integer>();
            ArrayList<Integer> Row3 = new ArrayList<Integer>();


            try {
                FileReader reader = new FileReader("seatdetails.txt");
                int data = reader.read();
                while (data != -1) {
                    read_data.add((char) data);
                    data = reader.read();
                }
                int positioncount = 0;
                for (char i : read_data) {
                    conveted_char_value = String.valueOf(i);

                    if (conveted_char_value.equals("0")) {
                        if (count <= 12) {
                            Row1.add(0);
                            count++;
                        } else if (count >= 13 && count <= 28) {
                            Row2.add(0);
                            count++;
                        } else if (count >= 29 && count <= 48) {
                            Row3.add(0);
                            count++;
                        }
                    } else if (conveted_char_value.equals("1")) {
                        if (count <= 12) {
                            Row1.add(1);
                            count++;
                        } else if (count >= 13 && count <= 28) {
                            Row2.add(1);
                            count++;
                        } else if (count >= 29 && count <= 48) {
                            Row3.add(1);
                            count++;
                        }

                    }
                    positioncount++;
                }
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            arrayCreator(Row1, row_1);
            arrayCreator(Row2, row_2);
            arrayCreator(Row3, row_3);
        }

        public static void showTicketInfo() {
            float total = 0;
            for (Ticket ticket : ticketslist) {
                ticket.Printinfo();
                total += ticket.price;
            }
            System.out.println("Total ticket price: " + total);
        }

        public static void sortTicket() {
            double [] pricearray=new double[ticketslist.size()];
            int count=0;
            for(Ticket ticket:ticketslist){
                pricearray[count]=ticket.price;
                count++;
            }
            int n=pricearray.length;

            for(int i=0;i<n-1;i++){
                int min_index=i;

                for (int j=i+1;j<n;j++){
                    if(pricearray[j]<pricearray[min_index]){
                        min_index=j;
                    }
                    double temp =pricearray[min_index];
                    pricearray[min_index]=pricearray[i];
                    pricearray[i]=temp;
                }
            }
    for(double price:pricearray) {
        for (Ticket ticket : ticketslist) {
            if (ticket.price == price) {
                ticket.Printinfo();
            }
        }
    }


        }

    }