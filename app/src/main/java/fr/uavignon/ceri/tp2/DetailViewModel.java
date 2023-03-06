package fr.uavignon.ceri.tp2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.MutableLiveData;

import fr.uavignon.ceri.tp2.data.Book;


public class DetailViewModel extends AndroidViewModel {

    private BookRepository repository;
    private MutableLiveData<Book> selectedBook;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        selectedBook = repository.getSelectedBook();
    }

    public void setBook(long bookId) {
        repository.getBook(bookId);
        selectedBook=repository.getSelectedBook();
    }

    public void InsertOrUpdateBook(Book book) {
        if(book.getId()==0){
            repository.insertBook(book);
        }else{
            repository.updateBook(book);
        }

    }

    public MutableLiveData<Book> getSelectedBook() {
        return selectedBook;
    }
}