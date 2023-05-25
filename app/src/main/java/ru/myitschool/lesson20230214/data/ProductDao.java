package ru.myitschool.lesson20230214.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM ProductData")
    LiveData<List<ProductData>> getAll();

    @Query("SELECT * FROM ProductData WHERE title like :search or description like :search")
    LiveData<List<ProductData>> getFromLike(String search);

    @Insert
    void insertAll(ProductData... productData);

    @Delete
    void delete(ProductData productData);

    @Update
    void update(ProductData productData);


}
