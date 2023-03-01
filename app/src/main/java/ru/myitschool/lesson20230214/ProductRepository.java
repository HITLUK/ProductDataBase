package ru.myitschool.lesson20230214;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.lesson20230214.data.ProductBase;

public class ProductRepository {

   // private DataBaseHelper dataBaseHelper;
    private static ProductRepository instance = null;
    private ProductBase roomdb;

    public static ProductRepository getInstance(Context context) {
        if (instance == null) instance = new ProductRepository(context);
        return instance;
    }

    private final ArrayList<ProductData> products = new ArrayList<>();

    public ProductRepository(Context context) {
      //   dataBaseHelper = new DataBaseHelper(context);
        roomdb = Room.databaseBuilder(context, ProductBase.class, "database-name").allowMainThreadQueries().build();

        roomdb.productDao().insertAll(new ProductData("Хлеб", "бородинский", 45), new ProductData("Молоко", "жирность 3,5", 60), new ProductData("Сыр", "Российский", 50));
      //  dataBaseHelper.add(new ProductData("Хлеб", "бородинский", 45));
     //   dataBaseHelper.add(new ProductData("Молоко", "жирность 3,5", 60));
      //  dataBaseHelper.add(new ProductData("Сыр", "Российский", 50));
    }


    public List<ProductData> getProducts() {
        products.clear();
        products.addAll(roomdb.productDao().getAll());
        return products;
    }

    public void addProduct(ProductData productData) {
        //TODO add product
        products.clear();
        roomdb.productDao().insertAll(productData);
        products.addAll(roomdb.productDao().getAll());
        products.add(productData);

    }


    public void removeByPosition(ProductData product) {
        //TODO delete product
        roomdb.productDao().delete(product);
        products.remove(product);
      //  dataBaseHelper.deleteProduct(product);

    }

    public void updateProduct(ProductData temp) {

        roomdb.productDao().update(temp);

    }
}
