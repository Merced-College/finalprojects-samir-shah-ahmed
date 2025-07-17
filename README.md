# cpsc39-finalProjects

# Book Recommendation Engine

## 1. Project Description

This project is a terminal based book recomendation engine. It provides personalized book suggestions to users based on their ratings of books they have previously read. The engine uses a content-based filtering approach, calculating a similarity score between books by comparing shared attributes like authors and genres.

I made this because I love reading books, but struggle with finding new books to read, it was a challenge to build the score logic because, how can I score a book if the system cannot understand my reasoning for disliking a book. So what I build instead was if the User likes the book it compares genre, author, and if the book is in a series to come up with a recommendation. 
 
 ## 2. Features

 **Large Library** Using multiple CSV files of GoodRead collections which I found online from public github repositories(see works cited for repositories) containing tags, descriptions, and books. By putting them together I was able to build a Library which contains over 9,000 books for the user to rate and be recommended.

 **Recommendations:** Takes the books that the user has read and provides new books for the user to read based on genre and author.

 **Interactive User Interface** In the Terminal I built a UI which helps the user understand how the system works, by giving them a series of commands and what they do.

 **Description** The system will give you a description for a book of your choosing this works best on popular books such as "The Hunger Games" during my testing I ran into issues with incorrect descriptions but, I was unable to figure out how to fix it in time. 

 ## 3. Usage

 The application uses these commands: 

 'rate "Book Title" [1-5]'     
 
 'recommend'

 'describe "Book Title"'

 'myratings'

 'help'

 'exit'

 The commands are fairly self explanatory but when the user runs 
 the program there is a description to go with each command for improved clairity.

 ## 4. Core components

* **`Engine.java`**: The main class which runs the application loop and user input.
* **`DataLoader.java`**: Reads the CSV files and merges them into the main book library data structure.
* **`RecommendationsService.java`**: Contains the logic and algorithms for calculating similarity scores and generating recommendations.
* **`Book.java`**: A class representing a single book, holding its title, authors, tags, and description.
* **`User.java`**: A class to store the current user's ratings.

## Data Structures and Algorithms Used

* **Data Structures**
    * **'HashMap'**: Used because of O(1) lookup time for books, tags, and user ratings.
    * **'PriorityQueue'**: Needed to rank and retrieve the top recommendations without needing to go through the entire database.
    * **'ArrayList'**: Used to store lists of authors and tags for the books.

* **Algorithms:**
    * **Similarity Score Algorithm**: determines similarity between two books.
    * **Retrieval Algorithm**: retrieval method using a heap through the priority queue.
    * **Loading Algorithm**: Parses, maps, and merges the data from the 4 respective CSV files(book_tags.csv, books_1.Best_Books_Ever.csv, tags.csv, books.csv)
## Future Additions(Notes for myself)
    Look into a continously updating Library--So as many books as possible are included

    Experiment with LLMs for reading and improving book recommendations
    
    If I build into a website, add friends and recommendations from friends.


*Made by: Samir Ahmed on 7/16/25*

Super fun project and I'm able to cross something off my summer checklist since I wanted to build this for months, but was unable to figure out the logic for it until I took this class.