package lt.vu.mif.jate.tasks.task03.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author
 */
@Entity
@Table(name = "subjects")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "sub_type", discriminatorType = DiscriminatorType.STRING)
public class Subject implements Serializable {

    @Id
    @Column(name = "sub_id")
    private Integer id;
    @Column(name = "sub_address")
    private String address;
    @Column(name = "sub_city")
    private String city;
    @Column(name = "sub_country")
    private String country;
    @Column(name = "sub_county")
    private String county;
    @Column(name = "sub_email")
    private String email;
    @Column(name = "sub_phone")
    private String phone;
    @Column(name = "sub_state")
    private String state;
    @Column(name = "sub_web")
    private String web;
    @Column(name = "sub_zip")
    private String zip;
    @OneToMany(mappedBy = "customer")
    private List<Sale> purchases = new ArrayList<>();
    @OneToMany(mappedBy = "seller")
    private List<Sale> sales = new ArrayList<>();

    public List<Sale> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Sale> purchases) {
        this.purchases = purchases;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(getId(), subject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
