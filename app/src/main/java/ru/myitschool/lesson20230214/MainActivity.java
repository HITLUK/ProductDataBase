package ru.myitschool.lesson20230214;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import ru.myitschool.lesson20230214.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ProductRepository repository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        if (repository == null) repository = ProductRepository.getInstance(getApplicationContext());

        setContentView(binding.getRoot());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.rootContainer);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    // .addToBackStack(null)
                    .replace(R.id.rootContainer, fragment, String.valueOf(false))
                    .commit();
        }
    }
}