package lt.vu.mif.jate.tasks.task03.jpa;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lt.vu.mif.jate.tasks.task03.jpa.base.ModelTestBase;
import lt.vu.mif.jate.tasks.task03.jpa.model.Company;
import lt.vu.mif.jate.tasks.task03.jpa.model.Person;
import lt.vu.mif.jate.tasks.task03.jpa.model.Product;
import lt.vu.mif.jate.tasks.task03.jpa.model.Sale;
import lt.vu.mif.jate.tasks.task03.jpa.model.Subject;

/**
 * Testing model classes.
 *
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite01Test implements ModelTestBase {

    @Test
    public void subjectTest() {

        Subject s1 = fill(new Subject() {
            private static final long serialVersionUID = 1L;
        });
        assertSubject(s1);

        Subject s2 = fill(new Subject() {
            private static final long serialVersionUID = 2L;
        });
        assertSubject(s2);

        Subject s3 = fill(new Subject() {
            private static final long serialVersionUID = 2L;
        });
        assertSubject(s3);
        s3.setId(99);

        assertObject(s1, s2, s3);

    }

    @Test
    public void personTest() {

        Person p1 = fill(new Person());

        assertSubject(p1);
        assertEquals(PERSON_FIRST_NAME, p1.getFirstName());
        assertEquals(PERSON_LAST_NAME, p1.getLastName());
        assertEquals(PERSON_GENDER, p1.getGender());

        Person p2 = fill(new Person());
        Person p3 = fill(new Person());
        p3.setId(88);

        assertObject(p1, p2, p3);

    }

    @Test
    public void companyTest() {

        Company c1 = fill(new Company());

        assertSubject(c1);
        assertEquals(COMPANY_TITLE, c1.getTitle());

        Company c2 = fill(new Company());
        Company c3 = fill(new Company());
        c3.setId(88);

        assertObject(c1, c2, c3);

    }

    @Test
    public void productTest() throws Exception {

        Product p1 = fill(new Product());

        assertEquals(PRODUCT_ID, p1.getId());
        assertEquals(PRODUCT_NAME, p1.getName());
        assertEquals(PRODUCT_BRAND, p1.getBrand());

        Product p2 = fill(new Product());
        Product p3 = fill(new Product());
        p3.setId(88);

        assertObject(p1, p2, p3);

    }

    @Test
    public void saleTest() throws Exception {

        Sale s1 = fill(new Sale());

        assertEquals(SALE_ID, s1.getId());
        assertEquals(SALE_COST, s1.getCost());
        assertEquals(SALE_UNITS, s1.getUnits());
        assertEquals(SALE_TIME, s1.getTime());

        Sale s2 = fill(new Sale());
        Sale s3 = fill(new Sale());
        s3.setId(881);

        assertObject(s1, s2, s3);

    }

    @Test
    public void modelTests() throws Exception {

        Subject sub1 = fill(new Person());
        Subject sub2 = fill(new Company());
        Product product = fill(new Product());

        Sale sale1 = fill(new Sale());
        sale1.setId(1);

        Sale sale2 = fill(new Sale());
        sale2.setId(2);

        sale1.setCustomer(sub1);
        sale1.setSeller(sub2);
        sale1.setProduct(product);
        sub1.getPurchases().add(sale1);
        sub2.getSales().add(sale1);

        sale2.setCustomer(sub2);
        sale2.setSeller(sub1);
        sale2.setProduct(product);
        sub2.getPurchases().add(sale2);
        sub1.getSales().add(sale2);

        assertTrue(sub1.getPurchases() instanceof List);
        assertTrue(sub1.getSales() instanceof List);
        assertTrue(sub2.getPurchases() instanceof List);
        assertTrue(sub2.getSales() instanceof List);

    }

    private static void assertSubject(Subject s) {

        assertEquals(SUBJECT_ID, s.getId());
        assertEquals(SUBJECT_ADDRESS, s.getAddress());
        assertEquals(SUBJECT_CITY, s.getCity());
        assertEquals(SUBJECT_COUNTRY, s.getCountry());
        assertEquals(SUBJECT_COUNTY, s.getCounty());
        assertEquals(SUBJECT_EMAIL, s.getEmail());
        assertEquals(SUBJECT_PHONE, s.getPhone());
        assertEquals(SUBJECT_STATE, s.getState());
        assertEquals(SUBJECT_WEB, s.getWeb());
        assertEquals(SUBJECT_ZIP, s.getZip());

    }

    private static <T> void assertObject(T o1, T o2, T o3) {

        assertEquals(o1, o1);
        assertEquals(o1, o2);
        assertFalse(o1.equals(o3));
        assertFalse(o2.equals(o3));
        assertFalse(o3.equals(o1));
        assertFalse(o3.equals(o2));

        Set<T> hset = new HashSet<>();
        hset.add(o1);
        hset.add(o2);
        hset.add(o3);

        assertEquals(2, hset.size());
        assertTrue(hset.contains(o1));
        assertTrue(hset.contains(o2));
        assertTrue(hset.contains(o3));

    }

}
