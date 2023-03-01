package ru.myitschool.lesson20230214.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Collection;
import java.util.List;

import ru.myitschool.lesson20230214.ProductData;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM productdata")
    List<ProductData> getAll();

    @Insert
    void insertAll(ProductData... productData);

    @Delete
    void delete(ProductData productData);

    @Update
    void update(ProductData productData);


}
