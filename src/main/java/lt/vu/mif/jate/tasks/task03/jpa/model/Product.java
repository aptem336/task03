package lt.vu.mif.jate.tasks.task03.jpa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "pro_id")
    private Integer id;
    @Column(name = "pro_brand")
    private String brand;
    @Column(name = "pro_name")
    private String name;

    @OneToMany(mappedBy = "product")
    private List<Sale> sales = new ArrayList<>();

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
