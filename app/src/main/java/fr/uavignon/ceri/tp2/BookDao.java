package fr.uavignon.ceri.tp2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.*;


import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;

@Dao
public interface BookDao {
    @Update
    void updateBook(Book book);

    @Insert
    void insertBook(Book book);

    @Query("SELECT * FROM books WHERE bookId = :id")
    Book getBook(long id);

    @Query("DELETE FROM books WHERE bookId = :id")
    void deleteBook(long id);

    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAllBooks();


    @Query("DELETE FROM books")
    void deleteAllBooks();
}
