package lt.vu.mif.jate.tasks.task03.jpa;

import java.util.Set;
import static junit.framework.TestCase.*;
import lt.vu.mif.jate.tasks.task03.jpa.model.Subject;
import lt.vu.mif.jate.tasks.task03.jpa.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Testing Shop class to retrieve info.
 *
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite03Test {

    @Test
    public void testFilter() throws Exception {
        try (DbManager db = new DbManager()) {

            Shop shop = new Shop(db);

            Set<Subject> ausies = shop.filter(Subject.class,
                    c -> c.getCountry().equals("Australia"));
            assertEquals(500, ausies.size());

            Set<Product> goldenWaffles = shop.filter(Product.class,
                    p -> p.getBrand().equals("Golden")
                    && p.getName().contains("Waffles"));
            assertEquals(4, goldenWaffles.size());

        }
    }

    @Test
    public void testFilterAndMap() throws Exception {
        try (DbManager db = new DbManager()) {

            Shop shop = new Shop(db);

            Set<String> usaCities = shop.filterAndMap(Subject.class,
                    c -> c.getCountry().equals("USA"), c -> c.getCity());
            assertEquals(342, usaCities.size());
            assertTrue(usaCities.contains("New York"));
            assertTrue(usaCities.contains("Berkeley"));

            Set<String> canadaCities = shop.filterAndMap(Subject.class,
                    c -> c.getCountry().equals("Canada"), c -> c.getCity());
            assertEquals(227, canadaCities.size());
            assertTrue(canadaCities.contains("Burnaby"));
            assertTrue(canadaCities.contains("Richmond"));

        }
    }

}
