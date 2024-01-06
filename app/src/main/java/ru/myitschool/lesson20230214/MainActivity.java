package ru.myitschool.lesson20230214;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import ru.myitschool.lesson20230214.data.DataBaseHelper;
import ru.myitschool.lesson20230214.databinding.ActivityMainBinding;
import ru.myitschool.lesson20230214.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootContainer, MainFragment.newInstance())
                    .commit();
        }
    }
}
