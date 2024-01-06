package ru.myitschool.lesson20230214.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.myitschool.lesson20230214.R;
import ru.myitschool.lesson20230214.data.ProductData;
import ru.myitschool.lesson20230214.data.ProductRepository;
import ru.myitschool.lesson20230214.databinding.DetailMainBinding;
import ru.myitschool.lesson20230214.ui.main.MainFragment;

public class DetailFragment extends Fragment {

    private static final String ARG_ITEM = "ARG_ITEM";
    private final ProductRepository repository = ProductRepository.getInstance();

    private DetailMainBinding binding;


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = DetailMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductData data = (ProductData) requireArguments().getSerializable(ARG_ITEM);
        if (data != null) {
            binding.title.setText(data.getName());
            binding.description.setText(data.getDescription());

        }
        binding.back.setOnClickListener(v -> requireActivity().onBackPressed());
        binding.saveoradd.setText(data == null ? "Add" : "Save");
        binding.saveoradd.setOnClickListener(v -> applyChange(data));

    }

    private void applyChange(final ProductData data) {
        ProductData newProduct = new ProductData(
                binding.title.getText().toString(),
                binding.description.getText().toString()
        );
        if (data != null) {
            newProduct.setId(data.getId());
            repository.updateProduct(newProduct);
        } else {
            repository.addProduct(newProduct);
        }
        requireActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static DetailFragment newInstance(ProductData data) {
        DetailFragment fragment = new DetailFragment();

        Bundle bundle = new Bundle();
        if (data != null) {
            bundle.putSerializable(ARG_ITEM, data);
        }
        fragment.setArguments(bundle);
        return fragment;
    }
}

