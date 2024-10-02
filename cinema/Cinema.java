package cinema;
import java.util.Scanner;

public class Cinema {
    // Variables
    private int rows;
    private int seats;
    private int totalSeats;
    private char roomSize;
    private int totalIncome;
    private char [][] room;
    private int totalPurchasedTickets;
    private float percentage;
    private int currentIncome;

    // Constructor
    public Cinema(int rows, int seats) {
        this.rows = rows;
        this.seats = seats;
        this.totalSeats = totalSeats();
        this.roomSize = roomSize();
        this.totalIncome = totalIncome();
        this.room = constructRoom();
        this.totalPurchasedTickets = 0;
        this.percentage = 0;
        this.currentIncome = 0;
    }

    // Validation
    public boolean isNotValid(int row, int seat) {
        return row <= 0 || seat <= 0 || row > rows || seat > seats || room[row-1][seat-1] == 'B';
    }


    // Functions

    // Construct cinema room
    public char[][] constructRoom() {
        char[][] room = new char[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                room[i][j] = 'S';
            }
        }
        return room;
    }

    // Print cinema
    public void printCinema() {
        // Top row
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 1; i <= seats; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Rows
        for (int i = 1; i <= rows; i++) {
            System.out.print(i + " ");
            for (int j = 1; j <= seats; j++) {
                System.out.print(room[i-1][j-1] + " ");
            }
            System.out.println();
        }
        // New line
        System.out.println();
    }

    // Calculate total seats
    public int totalSeats() {
        return rows * seats;
    }

    // Calculate the room size
    public char roomSize() {
        return totalSeats() <= 60 ? 's' : 'b';
    }


    // Calculate the total income
    public int totalIncome() {
        if (roomSize() == 's') {
            return totalSeats() * 10;
        } else {
            int frontHalf = Math.floorDiv(rows, 2);
            int backHalf = rows - frontHalf;
            int frontHalfIncome = frontHalf * seats * 10;
            int backHalfIncome = backHalf * seats * 8;
            return frontHalfIncome + backHalfIncome;
        }
    }


    // Calculate ticket price
    public int ticketPrice(int row) {
        if (roomSize() == 's') {
            return 10;
        } else {
            return row <= Math.floorDiv(rows, 2) ? 10 : 8;
        }
    }


    // Buy ticket
    public void buyTicket(Scanner scan, Cinema cinema) {
        int row, seat;
        do {
            System.out.println("Enter a row number:");
            row = scan.nextInt();
            System.out.println("Enter a seat number in that row:");
            seat = scan.nextInt();
            try {
                if (isNotValid(row, seat) && room[row-1][seat-1] == 'B') {
                    System.out.println("That ticket has already been purchased!");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong input!");
            } catch (Exception e) {
                System.out.println("Something went wrong!");
            }
        } while (cinema.isNotValid(row, seat));

        // Calculate ticket price
        int price = cinema.ticketPrice(row);
        System.out.println("Ticket price: $" + price);

        // Update the room
        cinema.room[row-1][seat-1] = 'B';

        // Update the total purchased tickets
        cinema.totalPurchasedTickets++;
        cinema.percentage = (float) cinema.totalPurchasedTickets / cinema.totalSeats * 100;
        cinema.currentIncome += price;
    }


    public static void main(String[] args) {
        // Get rows and seats
        Scanner scan = new Scanner(System.in);
        int rows, seats = -1;
        do {
            System.out.println("Enter the number of rows:");
            rows = scan.nextInt();
            System.out.println("Enter the number of seats in each row:");
            seats = scan.nextInt();
        } while (rows <= 0 || seats <= 0);

        // Construct cinema
        Cinema cinema = new Cinema(rows, seats);

        // Menu
        int choice;
        boolean exit = false;
        do {
            System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            choice = scan.nextInt();

            // Choices
            if (choice == 0) {
                exit = true;
            } else if (choice == 1) {
                cinema.printCinema();
            } else if (choice == 2) {
                cinema.buyTicket(scan, cinema);
            } else if (choice == 3) {
                System.out.printf("Number of purchased tickets: %d\n", cinema.totalPurchasedTickets);
                System.out.printf("Percentage: %.2f%%\n", cinema.percentage);
                System.out.printf("Current income: $%d\n", cinema.currentIncome);
                System.out.printf("Total income: $%d\n", cinema.totalIncome);
            } else {
                System.out.println("Invalid choice");
            }
        } while (!exit);

        // Close scanner
        scan.close();
    }
}