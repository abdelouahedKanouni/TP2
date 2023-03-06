package fr.uavignon.ceri.tp2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    private ListViewModel viewModel;
    private TextView bookTitle;
    private TextView bookDetails;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        bookTitle = getView().findViewById(R.id.item_title);
        bookDetails = getView().findViewById(R.id.item_detail);
        observerSetup();
        recyclerSetup();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long id=-1;
                ListFragmentDirections.ActionFirstFragmentToSecondFragment action = ListFragmentDirections.actionFirstFragmentToSecondFragment();
                action.setBookNum(id);
                Navigation.findNavController(view).navigate(action);
            }
        });

    }

    private void recyclerSetup() {
        RecyclerView recyclerView;
        adapter = new RecyclerAdapter(R.layout.fragment_list);
        recyclerView = getView().findViewById(R.id.recyclerView);
        adapter.setListViewModel(viewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    private void observerSetup() {
        viewModel.getAllBooks().observe(getViewLifecycleOwner(),
                new Observer<List<Book>>() {
                    @Override
                    public void onChanged(@Nullable final List<Book> books) {
                        adapter.setBookList(books);
                    }
                });
    }
}