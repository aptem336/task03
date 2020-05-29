package lt.vu.mif.jate.tasks.task03.jpa.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author
 */
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @Column(name = "sal_id")
    private Integer id;
    @Column(name = "sal_cost")
    private BigDecimal cost;
    @Column(name = "sal_units")
    private BigInteger units;
    @Column(name = "sal_time")
    private Calendar time;
    @ManyToOne
    @JoinColumn(name = "sal_seller_sub_id")
    private Subject seller;
    @ManyToOne
    @JoinColumn(name = "sal_customer_sub_id")
    private Subject customer;
    @ManyToOne(fetch = FetchType.LAZY) //ar reikia FetchType??
    @JoinColumn(name = "sal_pro_id")
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigInteger getUnits() {
        return units;
    }

    public void setUnits(BigInteger units) {
        this.units = units;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public Subject getCustomer() {
        return customer;
    }

    public void setCustomer(Subject customer) {
        this.customer = customer;
    }

    public Subject getSeller() {
        return seller;
    }

    public void setSeller(Subject seller) {
        this.seller = seller;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sale)) return false;
        Sale subject = (Sale) o;
        return Objects.equals(getId(), subject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
