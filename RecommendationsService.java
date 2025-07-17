import java.util.*;
import java.util.stream.Collectors;

public class RecommendationsService {
    class BookScore {
        private final Book book;
        private final int score;
        
        public BookScore(Book book, int score){
            this.book = book;
            this.score = score;
        }
        public Book getBook(){ return this.book; }
        public int getScore(){ return this.score; }
    }

    private int calculateSimilarity(Book book1, Book book2) {
        int score = 0;
        // ArrayList used to store and manipulate author lists
        List<String> commonAuthors = new ArrayList<>(book1.getAuthor());  
        commonAuthors.retainAll(book2.getAuthor()); 
        score += commonAuthors.size() * 5;

        // ArrayList used to store and manipulate tags
        List<String> commonTags = new ArrayList<>(book1.getTags());
        commonTags.retainAll(book2.getTags());
        score += commonTags.size();

        return score;
    }

    public List<Book> getRecommendations(User user, Map<String, Book> library) {
        // PriorityQueue used as a max heap to automatically sort recommendations
        PriorityQueue<BookScore> recommendationsQueue = new PriorityQueue<>((a, b) -> 
            Integer.compare(b.getScore(), a.getScore()));

        if (user.getRatings().isEmpty()) {
            return Collections.emptyList();
        }         

        // Stream filtering to find highly rated books (rating >= 4)
        List<Map.Entry<String, Integer>> highRatings = user.getRatings().entrySet().stream()
                .filter(entry -> entry.getValue() >= 4)
                .collect(Collectors.toList());

        // Nested loop to compare each unrated book with user's favorites
        for (Book libraryBook : library.values()) {
            // HashMap lookup for O(1) access to check if book is rated
            if (!user.getRatings().containsKey(libraryBook.getTitle())) {
                int totalScore = 0;
                for (var favoriteEntry : highRatings) {
                    Book favoriteBook = library.get(favoriteEntry.getKey());
                    totalScore += calculateSimilarity(favoriteBook, libraryBook);
                }
      
                if (totalScore > 0) {
                    recommendationsQueue.add(new BookScore(libraryBook, totalScore));
                }
            }
        }

        // ArrayList to store final sorted recommendations
        // Extract top 5 elements from priority queue
        List<Book> topRecommendations = new ArrayList<>();
        while (!recommendationsQueue.isEmpty() && topRecommendations.size() < 5) {
            topRecommendations.add(recommendationsQueue.poll().getBook());
        }
        return topRecommendations;
    }
}
