import java.util.*;

public class Engine {

    private static Map<String, Book> bookLibrary;

    public static void main(String[] args) {
        //Load the book library from CSV files.
        bookLibrary = DataLoader.loadBooks();
        if (bookLibrary.isEmpty()) {
            System.out.println("Could not load library. Please check for the required CSV files. Exiting.");
            return;
        }

        // Setup the recommendations service and user
        RecommendationsService recService = new RecommendationsService();
        User currentUser = new User("Guest");
        Scanner inputScanner = new Scanner(System.in);

        //Instructions for the user
        System.out.println("\nWelcome to the Book Recommender!");
        System.out.println("Loaded " + bookLibrary.size() + " books.");
        System.out.println("---------------------------------");
        printHelp();
        //Main loop to handle user commands
        while (true) {
            System.out.print("> ");
            String commandLine = inputScanner.nextLine();
            String[] parts = commandLine.split(" ", 2);
            String command = parts[0].toLowerCase();

            if (command.equals("rate")) {
                handleRating(parts, currentUser);
            } else if (command.equals("recommend")) {
                handleRecommendation(currentUser, recService);
            } else if (command.equals("describe")) { // ADDED THIS
                handleDescription(parts);
            } else if (command.equals("myratings")) {
                printUserRatings(currentUser);
            } else if (command.equals("help")) {
                printHelp();
            } else if (command.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Unknown command. Type 'help' for a list of commands.");
            }
        }
        inputScanner.close();
    }
    // This method handles the rating command.
    // It retrieves the book title and rating from the command arguments.
    // If the book is found in the library, it rates the book and informs the user
    private static void handleRating(String[] parts, User user) {
        if (parts.length < 2) {
            System.out.println("Error: Missing arguments. Usage: rate \"Book Title\" 5");
            return;
        }
        try {
            String argument = parts[1];
            String title = argument.substring(argument.indexOf("\"") + 1, argument.lastIndexOf("\""));
            int rating = Integer.parseInt(argument.substring(argument.lastIndexOf(" ") + 1));

            if (bookLibrary.containsKey(title)) {
                user.rateBook(title, rating);
                System.out.println("You rated '" + title + "' with " + rating + " stars.");
            } else {
                System.out.println("Error: Book '" + title + "' not found in the library.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid format. Usage: rate \"Book Title\" 5");
        }
    }
    // This method handles the recommendation command.
    // It retrieves the user's top recommendations based on their ratings.
    // If no recommendations are found, it informs the user.
    private static void handleRecommendation(User user, RecommendationsService recService) {
        System.out.println("\nRetrieving your top recommendations...");
        List<Book> recommendations = recService.getRecommendations(user, bookLibrary);

        if (recommendations.isEmpty()) {
            System.out.println("No recommendations found. Please rate more books (4+ stars).");
        } else {
            System.out.println("Here are your top 5 recommendations:");
            for (int i = 0; i < recommendations.size(); i++) {
                System.out.println((i + 1) + ". " + recommendations.get(i));
            }
        }
    }

    // This method handles the 'describe' command.
    // It retrieves the book title from the command arguments and prints its description.
    // If the book is not found, it informs the user.
    private static void handleDescription(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Error: Missing arguments. Usage: describe \"Book Title\"");
            return;
        }
        try {
            String title = parts[1].replace("\"", ""); // Get title from arguments
            if (bookLibrary.containsKey(title)) {
                Book book = bookLibrary.get(title);
                // Retrieve the description directly from the book object
                System.out.println("\nDescription for '" + book.getTitle() + "':");
                System.out.println(book.getDescription());
            } else {
                System.out.println("Error: Book '" + title + "' not found in the library.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid format. Usage: describe \"Book Title\"");
        }
    }
    // This method prints the user's rated books.
    // It iterates through the user's ratings and prints each book title with its rating.
    // If the user has not rated any books, it informs them.
    // It also handles the case where the user has not rated any books yet.
    private static void printUserRatings(User user) {
        System.out.println("\nYour rated books:");
        if (user.getRatings().isEmpty()) {
            System.out.println("You have not rated any books yet.");
        } else {
            for (Map.Entry<String, Integer> entry : user.getRatings().entrySet()) {
                System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " stars");
            }
        }
    }
 
    // This method prints the available commands and their usage.
    private static void printHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println("  rate \"Book Title\" <1-5>  - Rate a book (title must be in quotes).");
        System.out.println("  recommend                  - Get book recommendations.");
        System.out.println("  describe \"Book Title\"      - Get a short description of a book.(Note that some books may not work correctly)"); 
        System.out.println("  myratings                  - See the books you have rated.");
        System.out.println("  help                       - Show this help message.");
        System.out.println("  exit                       - Close the application.");
    }
}