package ru.myitschool.lesson20230214.data;

import java.io.Serializable;
import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class ProductData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "title")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "count")
    private int count;


    public ProductData(String name, String description, int count) {
        this.id = 0;
        this.name = name;
        this.description = description;
        this.count = count;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductData that = (ProductData) o;
        return count == that.count && Objects.equals(name, that.name) && Objects.equals(description,
                that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, count);
    }


}
