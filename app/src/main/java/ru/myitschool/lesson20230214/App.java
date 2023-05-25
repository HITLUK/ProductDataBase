package ru.myitschool.lesson20230214;

import android.app.Application;

import androidx.room.Room;

import ru.myitschool.lesson20230214.data.ProductDatabase;
import ru.myitschool.lesson20230214.data.ProductRepository;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ProductRepository.init(OnLoadDB());
        // ProductDatabase productDatabase = OnLoadDB();
    }
    public ProductDatabase OnLoadDB(){
      return   Room.databaseBuilder(
                        getApplicationContext().getApplicationContext(),
                        ProductDatabase.class,
                        "database-name"
                )
                .allowMainThreadQueries()
                .build();
    }
}
