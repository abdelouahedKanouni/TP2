package fr.uavignon.ceri.tp2;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


import com.google.android.material.snackbar.Snackbar;

import fr.uavignon.ceri.tp2.data.Book;

public class DetailFragment extends Fragment {

    private EditText textTitle, textAuthors, textYear, textGenres, textPublisher;

    private DetailViewModel viewModel;
    private long bookId;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        // Get selected book
        DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
        bookId = args.getBookNum();
        if(bookId!=-1) {
            viewModel.setBook(bookId);
            observerSetup();
        }
        textTitle = (EditText) getView().findViewById(R.id.nameBook);
        textAuthors = (EditText) getView().findViewById(R.id.editAuthors);
        textYear = (EditText) getView().findViewById(R.id.editYear);
        textGenres = (EditText) getView().findViewById(R.id.editGenres);
        textPublisher = (EditText) getView().findViewById(R.id.editPublisher);
        listenerSetup();
        view.findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(fr.uavignon.ceri.tp2.DetailFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);

            }
        });

    }
    private void observerSetup() {
        viewModel.getSelectedBook().observe(getViewLifecycleOwner(),
                new Observer<Book>() {
                    @Override
                    public void onChanged(Book book) {
                        textTitle.setText(book.getTitle());
                        textAuthors.setText(book.getAuthors());
                        textYear.setText(book.getYear());
                        textGenres.setText(book.getGenres());
                        textPublisher.setText(book.getPublisher());
                    }
                });
    }
    private void listenerSetup() {
        Button updateButton = getView().findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = textTitle.getText().toString();
                String author = textAuthors.getText().toString();
                String year = textYear.getText().toString();
                String genre = textGenres.getText().toString();
                String publisher = textPublisher.getText().toString();
                Book book = new Book(title, author, year, genre, publisher);
                if (!title.equals("") && !author.equals("")) {
                    if (bookId != -1) {
                        book.setId(bookId);
                    }
                    viewModel.InsertOrUpdateBook(book);
                } else {
                    Snackbar.make(view, "Complétez vos informations", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

    }
}