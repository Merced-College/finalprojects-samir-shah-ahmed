//hold user name and thier ratings stored in hashmap
import java.util.HashMap;
import java.util.Map;

public class User{
    private final String name; 
    private final Map<String, Integer> ratings;// book title, rating

    public User(String name){
        this.name=name;
        this.ratings=new HashMap<>();

    }
    public void rateBook(String title, int rating){
        ratings.put(title, rating);
    }
    //getter for username and rating
    public String getUsername(){
        return username;
    }
    public Map<String, Integer> getRatings(){
        return ratings;
    }

}