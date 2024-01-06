package ru.myitschool.lesson20230214.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ru.myitschool.lesson20230214.R;
import ru.myitschool.lesson20230214.data.ProductData;
import ru.myitschool.lesson20230214.data.ProductRepository;
import ru.myitschool.lesson20230214.databinding.FragmentMainBinding;
import ru.myitschool.lesson20230214.ui.details.DetailFragment;
import ru.myitschool.lesson20230214.utils.TextListener;

public class MainFragment extends Fragment {

    private final ProductRepository repository = ProductRepository.getInstance();
    private final ProductAdapter.OnProductDataClickListener productClickListener = holder -> {
        ProductData data = repository.getCachedProductByPosition(holder.getAdapterPosition());
        Intent bri = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getDescription()));
        startActivity(bri);

    };
    private final ProductAdapter adapter = new ProductAdapter(productClickListener);
    private final ItemTouchHelper.SimpleCallback swipeToDelete = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                @NonNull RecyclerView.ViewHolder viewHolder,
                @NonNull RecyclerView.ViewHolder target
        ) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            repository.removeByPosition(
                    repository.getCachedProductByPosition(viewHolder.getAdapterPosition()));
            adapter.removeItemByPosition(viewHolder.getAdapterPosition());
        }
    };

    private FragmentMainBinding binding;


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.container.setAdapter(adapter);
        new ItemTouchHelper(swipeToDelete).attachToRecyclerView(binding.container);

        binding.input.addTextChangedListener(
                new TextListener() {
                    public void afterTextChanged(Editable s) {
                        adapter.setData(new ArrayList<>());
                        repository.getLike("%" + s + "%").observe(
                                getViewLifecycleOwner(),
                                adapter::setData
                        );
                    }
                }
        );

        binding.add.setOnClickListener(v -> {
                    getParentFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.rootContainer, DetailFragment.newInstance(null))
                            .commit();

                    repository.getProducts().observe(getViewLifecycleOwner(), list -> {
                        Log.d("Add", "" + list.size());
                        adapter.setData(list);
                    });
                }
        );

        if (binding.input.getText().toString().isEmpty()) {
            repository.getProducts().observe(getViewLifecycleOwner(), adapter::setData);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static Fragment newInstance() {
        return new MainFragment();
    }


}
