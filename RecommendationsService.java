import java.util.*;
import java.util.stream.Collectors;

public class RecommendationsService {
    // This class provides book recommendations based on user ratings
    
    // class to hold book and its similarity score can't use record since this is not Java 14+
    class BookScore{
        private final Book book;
        private final int score;
        
        public BookScore(Book book, int score){
            this.book =book;
            this.score = score;
        }
        public Book getBook(){
            return this.book;
        }
        public int getScore(){
            return this.score;
        }
    }

    //Similarity Score Calculator
    private int calculateSimilarity(Book book1, Book book2) {
        int score = 0;
        // Give 5 points for each author in common
        List<String> commonAuthors = new ArrayList<>(book1.getAuthor());  
        commonAuthors.retainAll(book2.getAuthor()); 
        score += commonAuthors.size() * 5;

        // Give 1 point for each tag (genre) in common
        List<String> commonTags = new ArrayList<>(book1.getTags());
        commonTags.retainAll(book2.getTags());
        score += commonTags.size();

        return score;
    }

    //Get top reccomendation 
    public List<Book> getRecommendations(User user, Map<String, Book> library) {  // Fixed spelling
        //Priority queue to sort rank reccomendations automatically 
        PriorityQueue<BookScore> recommendationsQueue = new PriorityQueue<>((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        // Check if user has rated any books
        if (user.getRatings().isEmpty()) {
            return Collections.emptyList(); // No recommendations if no ratings
        }         
        //find the users highly rated books
        List<Map.Entry<String, Integer>> highRatings = user.getRatings().entrySet().stream()
                .filter(entry -> entry.getValue() >= 4)
                .collect(Collectors.toList());

        // Iterate through all books in library
        for (Book libraryBook : library.values()) {
            // Check if user hasn't rated this book yet
            if (!user.getRatings().containsKey(libraryBook.getTitle())) {
                int totalScore = 0;
                //calculate similarity score to each of the users favorite books
                for (var favoriteEntry : highRatings) {
                    Book favoriteBook = library.get(favoriteEntry.getKey());
                    totalScore += calculateSimilarity(favoriteBook, libraryBook);
                }
                if (totalScore > 0) {
                    recommendationsQueue.add(new BookScore(libraryBook, totalScore));
                }
            }
        }

        //get top 5 recommendations
        int count = 0;
        List<Book> topRecommendations = new ArrayList<>();
        while (!recommendationsQueue.isEmpty() && count < 5) {
            topRecommendations.add(recommendationsQueue.poll().getBook());
            count++;
        }
        return topRecommendations;
    }
}
