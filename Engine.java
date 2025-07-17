import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;

public class Engine{
    //the hashmap holds the book library
    private static final Map<String, Book> bookLibrary = new HashMap<>();

    public static void main(String[] args){
        loadbooks();
        System.outprintln("Welcome to the Book reccomender");
        System.outprintln(bookLibrary.size() + " books loaded.");

        User currentUser=new User("Guest");
        String command="";

        while (!command.equalsIgnoreCase("exit")){
            System.out.println("> ");
            command = inputScanner.nextLine();
            String[] parts = command.split(" ");

            if(parts[0].equalsIgnoreCase("rate") && parts.length >2){
                //rejoin the title parts in case of spaces
                String title= command.substring(command.indexOf(" ")+1, command.lastIndexOf(" "));
                int rating = Integer.parseInt(parts[parts.length -1]);
                if(bookLibrary.containsKey(title)){
                    currentUser.rateBook(title, rating);
                    System.out.println("Rated '" + title + "' with " + rating + " stars.");
                } else {
                    System.out.println("Book not found in library.");
            }
        RecommendationsService recService = new RecommendationsService();

        if (parts[0].equalsIgnoreCase("recommend")) {
            List<Book> recommendations = recService.getRecommendations(currentUser, bookLibrary);
            if (recommendations.isEmpty()) {
                System.out.println("No recommendations available. Please rate more books (4+ stars).");
            } else {
                System.out.println("Here are your top recommendations:");
                recommendations.forEach(System.out::println);
            }
        }
    }
    public static void loadBooks(){
        try(Scanner scanner= new Scanner(new File("books.csv"))){
            while(scanner.hasNextLine()){
                String line= scanner.nextLine();
                String[] parts= line.split(",");
                if(parts.length==3){
                    Book book= new Book(parts[0], parts[1], parts[2]);
                    bookLibrary.put(book.getTitle(), book);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    System.out.println("Goodbye!");
    inputScanner.close();
}