package fr.uavignon.ceri.tp2;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import fr.uavignon.ceri.tp2.data.Book;

import static fr.uavignon.ceri.tp2.BookRoomDatabase.databaseWriteExecutor;

public class BookRepository {
    private MutableLiveData<Book> selectedBook =
            new MutableLiveData<>();
    private LiveData<List<Book>> allBooks;

    private BookDao BookDao;

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public BookRepository(Application application) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        BookDao = db.BookDao();
        allBooks = BookDao.getAllBooks();
    }

    public void insertBook(Book newBook) {
        databaseWriteExecutor.execute(() -> {
            BookDao.insertBook(newBook);
        });
    }


    public void getBook(long id) {

        Future<Book> book = databaseWriteExecutor.submit(() -> {
            return BookDao.getBook(id);
        });
        try {
            selectedBook.setValue(book.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        databaseWriteExecutor.execute(() -> {
            BookDao.updateBook(book);
        });
    }

    public void deleteBook(Long id) {
        databaseWriteExecutor.execute(() -> {
            BookDao.deleteBook(id);
        });
    }

    public void deleteAllBooks() {
        databaseWriteExecutor.execute(() -> {
            BookDao.deleteAllBooks();
        });
    }
    public MutableLiveData<Book> getSelectedBook(){
        return selectedBook;
    }

}
