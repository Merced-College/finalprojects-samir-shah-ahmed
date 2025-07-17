import java.util.*;
import java.util.stream.Collectors;

public class ReccomendationService{
    //Similarity Score Calculator
    private int calculateSimilarity(Book book1, Book book2){
        int score=0;
     // Give 5 points for each author in common
    List<String> commonAuthors = new ArrayList<>(book1.getAuthors());
    commonAuthors.retainAll(book2.getAuthors()); // Finds the intersection of the two lists
    score += commonAuthors.size() * 5;

    // Give 1 point for each tag (genre) in common
    List<String> commonTags = new ArrayList<>(book1.getTags());
    commonTags.retainAll(book2.getTags());
    score += commonTags.size();

    return score;
}
    //Get top reccomendation 
    public List<Book> getRecomendations(User user, Map<String, Book> library){
        //helper to hold book and score
        record BookScore(Book book, int score){}
        //Priority queue to sort rank reccomendations automaitcally 
        //comparator to sort by score descending
        PriorityQueue<BookScore> reccomendationsQueue=new PriorityQueue<>((a,b)-> Integer.compare(b.score, a.score));
        //find the users highly rated books
        List<Map.Entry<String, Integer>> highRatings= user.getRatings().entrySet().stream().filter(entry-> entry.getValue()>=4).toList();
        if (!usergetRatings().containsKey(libraryBook.getTitle())){
            int totalScore=0;
            //calculate similarity score to each of the users favorite books
            for(var favoriteEntry: highRatings){
                Book favoriteBook= library.get(favoriteEntry.getKey());
                totalScore+= calculateSimilarity(favoriteBook, libraryBook);

            }
            if(totalScore>0){
                reccomendationsQueue.add(new BookScore(libraryBook, totalScore));
            }
        }
        //get top 5 reccomendations
        List<Book> topRecomendations=new ArrayList<>();
        for(int i=0; i<5 && !reccomendationsQueue.isEmpty(); i++){
            topRecomendations.add(reccomendationsQueue.poll().book);

        }
        return topRecomendations;
    }
}
