import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private final String type;
    private final double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": " + amount;
    }
}

public class Main {
    private static final String USER_ID = "123";
    private static final String USER_PIN = "456";
    private static double balance = 1000.0;
    private static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter user PIN: ");
        String userPin = scanner.nextLine();

        if (authenticate(userId, userPin)) {
            System.out.println("Authentication successful. Welcome to the ATM!");

            while (true) {
                displayMenu();
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        printTransactionsHistory();
                        break;
                    case 2:
                        withdraw(scanner);
                        break;
                    case 3:
                        deposit(scanner);
                        break;
                    case 4:
                        transfer(scanner);
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    private static boolean authenticate(String userId, String userPin) {
        return userId.equals(USER_ID) && userPin.equals(USER_PIN);
    }

    private static void displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    private static void printTransactionsHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions history available.");
        } else {
            System.out.println("Transactions History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    private static void recordTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount));
    }

    private static void withdraw(Scanner scanner) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            recordTransaction("Withdraw", amount);
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    private static void deposit(Scanner scanner) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            balance += amount;
            recordTransaction("Deposit", amount);
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    private static void transfer(Scanner scanner) {
        System.out.print("Enter recipient's account number: ");
        String recipientAccount = scanner.next();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            recordTransaction("Transfer to " + recipientAccount, amount);
            System.out.println("Transfer successful. New balance: " + balance);
        } else {
            System.out.println("Invalid transfer amount.");
        }
    }
}
