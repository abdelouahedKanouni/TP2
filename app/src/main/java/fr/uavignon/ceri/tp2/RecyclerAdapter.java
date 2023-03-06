package fr.uavignon.ceri.tp2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;

public class RecyclerAdapter extends RecyclerView.Adapter<fr.uavignon.ceri.tp2.RecyclerAdapter.ViewHolder> {

    private static final String TAG = fr.uavignon.ceri.tp2.RecyclerAdapter.class.getSimpleName();
    private static List<Book> bookList;
    private int bookItemLayout;
    private static BookRepository repository = new BookRepository(new Application());
    private ListViewModel listViewModel;




    public RecyclerAdapter(int layoutId) {
        bookItemLayout = layoutId;
    }

    public void setBookList(List<Book> books) {
        bookList = books;
        notifyDataSetChanged();
    }
    public void setListViewModel(ListViewModel viewModel) {
        listViewModel = viewModel;
    }
    private void deleteItem(long id) {
        if (listViewModel != null)
            listViewModel.deleteBook(id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.itemTitle.setText(bookList.get(i).getTitle());
        viewHolder.itemDetail.setText(bookList.get(i).getAuthors());


    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0 : bookList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView itemDetail;
        long idSelected;

        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetail = itemView.findViewById(R.id.item_detail);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    long id = RecyclerAdapter.bookList.get((int) getAdapterPosition()).getId();

                    ListFragmentDirections.ActionFirstFragmentToSecondFragment action = ListFragmentDirections.actionFirstFragmentToSecondFragment();
                    action.setBookNum(id);
                    Navigation.findNavController(v).navigate(action);

                }
            });


            ActionMode.Callback actionModeCallBack = new ActionMode.Callback() {

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.context_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                    switch(item.getItemId()){

                        case R.id.supprimer:

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext());

                            alertDialog.setTitle("Confirm Delete…");
                            alertDialog.setMessage("Are you sure you want to delete?");
                            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    RecyclerAdapter.this.deleteItem(idSelected);
                                    Snackbar.make(itemView, "Book deleted !",
                                                    Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();

                                }
                            });
                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                            mode.finish();
                            return true;

                    };

                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            };

            itemView.setOnLongClickListener(new View.OnLongClickListener(){

                public ActionMode actionMode;
                @Override
                public boolean onLongClick(View view) {
                    idSelected = RecyclerAdapter.this.bookList.get((int) getAdapterPosition()).getId();
                    if (actionMode != null) {
                        return false;
                    }
                    actionMode = view.startActionMode(actionModeCallBack);
                    view.setSelected(true);
                    return true;
                }
            });
        }

    }


}