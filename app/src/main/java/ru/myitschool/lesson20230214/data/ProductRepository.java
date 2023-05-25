package ru.myitschool.lesson20230214.data;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Room;

import ru.myitschool.lesson20230214.App;

public class ProductRepository {

    private static ProductRepository instance = null;
    private final ProductDao productDao;
    private DataBaseHelper dataBaseHelper;
   //private  ProductDatabase database;
    private final ArrayList<ProductData> products = new ArrayList<>();

    private ProductRepository( ProductDatabase database) {


        productDao = database.productDao();
        // dataBaseHelper = new DataBaseHelper(context);

        for (int i = 0; i < 1; i++) {
            productDao.insertAll(
                    new ProductData("Хлеб", "бородинский", 45),
                    new ProductData("Молоко", "жирность 3,5", 60),
                    new ProductData("Сыр", "Российский", 50)
            );
        }
        //  dataBaseHelper.add(new ProductData("Хлеб", "бородинский", 45));
        //   dataBaseHelper.add(new ProductData("Молоко", "жирность 3,5", 60));
        //  dataBaseHelper.add(new ProductData("Сыр", "Российский", 50));
    }

    public ProductData getCachedProductByPosition(int position) {
        return products.get(position);
    }
    public LiveData<List<ProductData>> getProducts() {
        products.clear();
        return Transformations.map(productDao.getAll(), input -> {
            products.addAll(input);
            return input;
        });
    }

    public void addProduct(ProductData productData) {
        //TODO add product
        products.clear();
        productDao.insertAll(productData);
        //products.addAll(productDao.getAll());
        products.add(productData);

    }

    public LiveData<List<ProductData>> getLike(String search) {
        products.clear();
        return Transformations.map(productDao.getFromLike(search), input -> {
            products.addAll(input);
            return input;
        });

    }

    public void removeByPosition(ProductData product) {
        //TODO delete product
        productDao.delete(product);
        products.remove(product);
        //  dataBaseHelper.deleteProduct(product);

    }

    public void updateProduct(ProductData temp) {
        productDao.update(temp);
    }

    public static void init(final ProductDatabase database) {
        instance = new ProductRepository(database);

    }

    public static ProductRepository getInstance() {
        return instance;
    }
}
