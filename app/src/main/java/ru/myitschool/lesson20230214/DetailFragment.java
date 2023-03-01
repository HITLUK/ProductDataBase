package ru.myitschool.lesson20230214;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;

import ru.myitschool.lesson20230214.databinding.DetailMainBinding;

public class DetailFragment extends Fragment {
    private final ProductRepository repository = ProductRepository.getInstance(getContext());
    private static ProductData data;
    private static int flag1 = 0;


    public static DetailFragment newInstance(ProductRepository repository, int position, int flag) {
        DetailFragment fragment = new DetailFragment();

        Bundle bundle = new Bundle();
        flag1 = flag;
        if (position >= 0) {
            data = repository.getProducts().get(position);
            bundle.putString("id", String.valueOf(data.getId()));
            bundle.putString("title", data.getName());
            bundle.putString("description", data.getDescription());
            bundle.putString("count", String.valueOf(data.getCount()));
        } else {
            bundle.putString("title", "Test product " + repository.getProducts().size());
            bundle.putString("description", "Test Description");
            bundle.putString("count", "0");
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    private DetailMainBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String titleText = getArguments().getString("title");
        String descriptionText = getArguments().getString("description");
        String countText = getArguments().getString("count");

        binding.title.setText(titleText);
        binding.description.setText(descriptionText);
        binding.count.setText(countText);

        binding.back.setOnClickListener(view1 -> {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.rootContainer, new MainFragment())
                            .commit();
                }
        );

        String[] extern = {"Save", "Add"};
        binding.saveoradd.setText(flag1 == 1 ? extern[0] : extern[1]);
        binding.saveoradd.setOnClickListener(view1 -> {
                    if (flag1 == 1) {
                        data.setName(binding.title.getText().toString());
                        data.setDescription(binding.description.getText().toString());
                        data.setCount(Integer.valueOf(binding.count.getText().toString()));
                        repository.updateProduct(data);

                    } else {
                        repository.addProduct(new ProductData(
                                binding.title.getText().toString(),
                                binding.description.getText().toString(),
                                Integer.valueOf(binding.count.getText().toString())));
                    }

                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.rootContainer, new MainFragment())
                            .commit();
                }
        );

    }


}

