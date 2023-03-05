package ru.myitschool.lesson20230214;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.lesson20230214.databinding.FragmentMainBinding;


public class MainFragment extends Fragment {
    private final ProductRepository repository = ProductRepository.getInstance(getContext());
    SearchView searchView;
    private static List<ProductData> filterList = new ArrayList<>();
    ProductAdapter.OnProductDataClickListener productClickListener = new ProductAdapter.OnProductDataClickListener() {

        @Override
        public void onProductClick(ProductAdapter.ViewHolder holder) {
            Toast.makeText(getContext(), "Был выбран пункт " + repository.getProducts().get(holder.getAdapterPosition()).getName(),
                    Toast.LENGTH_SHORT).show();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootContainer, DetailFragment.newInstance(repository, holder.getAdapterPosition(), 1))
                    .commit();
        }
    };


    private final ProductAdapter adapter = new ProductAdapter(productClickListener);
    private final ItemTouchHelper.SimpleCallback swipeToDelete = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // repository.removeByPosition(viewHolder.getAdapterPosition());
            repository.removeByPosition(repository.getProducts().get(viewHolder.getAdapterPosition()));
            adapter.removeItemByPosition(viewHolder.getAdapterPosition());
            // Log.d("Remove ", "" + repository.getProducts().size());
        }
    };
    //  private static int count;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);
        binding.container.setAdapter(adapter);

        binding.input.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // filterList = ;
                adapter.setData(repository.getLike("%"+s+"%"));
                //adapter.notifyDataSetChanged();
                //repository.getLike(s+"");
                //TextView textView = findViewById(R.id.textView);
                //textView.setText(s);
            }
        });

        new ItemTouchHelper(swipeToDelete).attachToRecyclerView(binding.container);
        binding.add.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootContainer, DetailFragment.newInstance(repository, -1, 0))
                    .commit();

            //   repository.addProduct(new ProductData("Product " + count++, count % 2 == 0 ? "test description\nmultiLine\nexample\ntest" : "", (int) (Math.random() * 100)));
            Log.d("Add", "" + repository.getProducts().size());
            adapter.setData(repository.getProducts());
        });
        if (binding.input.getText().toString().isEmpty()) {
            adapter.setData(repository.getProducts());

        }
        for (ProductData p : repository.getProducts()) {
            Log.d("products", p.getId() + " " + p.getName() + " " + p.getDescription() + " " + p.getCount() + "\n");
        }
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fm = getParentFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.rootContainer);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.rootContainer, fragment, String.valueOf(false))
                    .commit();
        }
    }


}
