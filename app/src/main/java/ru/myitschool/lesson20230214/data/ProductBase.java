package ru.myitschool.lesson20230214.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.myitschool.lesson20230214.ProductData;

@Database(entities = {ProductData.class}, version = 1)
public abstract class ProductBase extends RoomDatabase {
    public abstract ProductDao productDao();
}
