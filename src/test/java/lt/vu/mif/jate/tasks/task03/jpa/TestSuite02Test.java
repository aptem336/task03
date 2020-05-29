package lt.vu.mif.jate.tasks.task03.jpa;

import static junit.framework.TestCase.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lt.vu.mif.jate.tasks.task03.jpa.model.Company;
import lt.vu.mif.jate.tasks.task03.jpa.model.Person;
import lt.vu.mif.jate.tasks.task03.jpa.model.Product;
import lt.vu.mif.jate.tasks.task03.jpa.model.Subject;

/**
 * Testing DbManager class to retrieve list of entities.
 *
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite02Test {

    @Test
    public void customerTests() throws Exception {
        try (DbManager db = new DbManager()) {

            assertEquals(798, db.getListOf(Company.class).size());

            List<Person> pl1 = db.getListOf(Person.class, "firstName");
            assertEquals("Aaron", pl1.iterator().next().getFirstName());
            assertEquals("Zona", pl1.get(pl1.size() - 1).getFirstName());

            List<Company> cl2 = db.getListOf(Company.class, "city");
            assertEquals("Abbey Road Ward", cl2.iterator().next().getCity());
            assertEquals("Yorkrakine", cl2.get(cl2.size() - 1).getCity());

            Company c566 = cl2.stream()
                    .filter(c -> c.getId() == 566)
                    .findFirst().orElse(null);
            assertNotNull(c566);
            assertEquals("Sams Liquor & Deli", c566.getTitle());
            assertEquals("1 Nw 167th St", c566.getAddress());
            assertEquals("Winnipeg", c566.getCity());
            assertEquals("R3J 2Z1", c566.getState());
            assertEquals("MB", c566.getCounty());
            assertEquals("Canada", c566.getCountry());
            assertEquals("204-438-6204", c566.getZip());
            assertEquals("204-941-5253", c566.getPhone());
            assertEquals("jaime.lowrance@lowrance.org", c566.getEmail());
            assertEquals("http://www.samsliquordeli.com", c566.getWeb());
            assertEquals(118, c566.getSales().size());
            assertEquals(139, c566.getPurchases().size());

            Subject s566 = db.getById(Subject.class, 566);
            assertEquals(c566, s566);
            assertSame(c566, s566);

            Subject s1015 = db.getById(Subject.class, 1015);
            assertTrue(s1015 instanceof Person);
            Person p1015 = (Person) s1015;
            assertEquals("Niesha", p1015.getFirstName());
            assertEquals("Bruch", p1015.getLastName());
            assertEquals("niesha.bruch@yahoo.com", p1015.getEmail());

        }
    }

    @Test
    public void productTests() throws Exception {
        try (DbManager db = new DbManager()) {

            assertEquals(1560, db.getListOf(Product.class).size());

            List<Product> pl1 = db.getListOf(Product.class, "brand");
            assertEquals("ADJ", pl1.iterator().next().getBrand());
            assertEquals("Washington", pl1.get(pl1.size() - 1).getBrand());

            List<Product> pl2 = db.getListOf(Product.class, "name");
            assertEquals("ADJ Rosy Sunglasses", pl2.iterator().next().getName());
            assertEquals("Washington Strawberry Drink", pl2.get(pl2.size() - 1).getName());

            Product p1 = pl2.stream().filter(c -> c.getId() == 0).findFirst().orElse(null);
            assertNull(p1);

            Product p2 = pl2.stream().filter(c -> c.getId() == 1234).findFirst().orElse(null);
            assertNotNull(p2);
            assertEquals("Plato Columbian Coffee", p2.getName());
            assertEquals("Plato", p2.getBrand());
            assertEquals(145, p2.getSales().size());

            String[] countriesPurchased = new String[]{"UK", "Australia", "USA", "Canada"};
            assertTrue(
                    p2.getSales().stream()
                    .map(s -> s.getCustomer().getCountry())
                    .distinct()
                    .allMatch(c -> Arrays.stream(countriesPurchased)
                            .filter(s -> s.equals(c))
                            .findFirst()
                            .isPresent()));

        }
    }

}
