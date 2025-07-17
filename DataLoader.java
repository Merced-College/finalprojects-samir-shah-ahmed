import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DataLoader {

    // This is the main method that orchestrates loading all data.
    public static Map<String, Book> loadBooks() {
        System.out.println("Loading data from CSV files...");

        //Load descriptions from the new file first.
        Map<String, String> descriptionMap = loadDescriptions();

        //Load tags from the original tags.csv.
        Map<Integer, String> tagsMap = loadTags();

        //Load book-to-tag relationships from book_tags.csv.
        Map<Integer, List<Integer>> bookTagsMap = loadBookTags();

        //Load the main book data and combine everything.
        Map<String, Book> bookLibrary = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("books.csv"))) {
            scanner.nextLine(); // Skip header line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Use a split to handle commas inside titles.
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                try {
                    int goodreadsId = Integer.parseInt(parts[1]);
                    // Clean the title by removing extra quotes.
                    String title = parts[9].replace("\"", "");
                    List<String> authors = Arrays.asList(parts[7].split("/"));

                    // Find all the tags for the current book.
                    List<Integer> tagIds = bookTagsMap.getOrDefault(goodreadsId, Collections.emptyList());
                    List<String> tagNames = new ArrayList<>();
                    for (int tagId : tagIds) {
                        if (tagsMap.containsKey(tagId)) {
                            tagNames.add(tagsMap.get(tagId));
                        }
                    }

                    Book book = new Book(goodreadsId, title, authors, tagNames);

                    // Merge: Find and set the description for this book.
                    // Clean the title again to match the key in our description map.
                    String cleanTitle = title.replaceAll(" \\(.*\\)$", "");
                    if (descriptionMap.containsKey(cleanTitle)) {
                        book.setDescription(descriptionMap.get(cleanTitle));
                    }

                    bookLibrary.put(title, book);

                } catch (NumberFormatException e) {
                    // Ignore lines with errors.
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: books.csv not found.");
            return Collections.emptyMap();
        }

        System.out.println("Data loading complete.");
        return bookLibrary;
    }

    // to load descriptions
    private static Map<String, String> loadDescriptions() {
        Map<String, String> descriptionMap = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("books_1.Best_Books_Ever.csv"))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length > 5) {
                    String title = parts[1].replace("\"", "");
                    String description = parts[5].replace("\"", "");
                    if (!title.isEmpty() && !description.isEmpty()) {
                        descriptionMap.put(title, description);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Warning: Descriptions file (books_1.Best_Books_Ever.csv) not found.");
        }
        return descriptionMap;
    }
    // to load tags
    // This method loads tags from the tags.csv file.
    // It returns a map where the key is the tag ID and the value is the tag name.
    private static Map<Integer, String> loadTags() {
        Map<Integer, String> tagsMap = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("tags.csv"))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 2) {
                    tagsMap.put(Integer.parseInt(parts[0]), parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: tags.csv not found.");
        }
        return tagsMap;
    }

    private static Map<Integer, List<Integer>> loadBookTags() {
        Map<Integer, List<Integer>> bookTagsMap = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("book_tags.csv"))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 3) {
                    int bookId = Integer.parseInt(parts[0]);
                    int tagId = Integer.parseInt(parts[1]);
                    // Add the tagId to the list for the corresponding bookId.
                    bookTagsMap.computeIfAbsent(bookId, k -> new ArrayList<>()).add(tagId);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: book_tags.csv not found.");
        }
        return bookTagsMap;
    }
}