package ru.myitschool.lesson20230214.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProductData.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
