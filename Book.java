import java.util.List;

public class Book {
    private final int bookId;
    private final String title;
    private final List<String> authors;
    private final List<String> tags;
    private String description;

    public Book(int bookId, String title, List<String> authors, List<String> tags) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.tags = tags;
        this.description = "No available description";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthor() {
        return authors;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        // Clean the title to remove any trailing year in parentheses
        String cleanTitle = this.title.replaceAll(" \\(.*\\)$", "");  // Fixed syntax error here
        String authorString = String.join(", ", this.authors);
        return "'" + cleanTitle + "' by " + authorString;
    }
}