package lt.vu.mif.jate.tasks.task03.jpa;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author
 */
public class Shop {

    private final DbManager dbManager;

    public Shop(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public <T> Set<T> filter(Class<T> c, Predicate<T> predicate) {
        return dbManager.getListOf(c).stream().filter(predicate).collect(Collectors.toSet());
    }

    public <T> Set<String> filterAndMap(Class<T> c, Predicate<T> predicate, Function<T, String> map) {
        return filter(c, predicate).stream().map(map).collect(Collectors.toSet());
    }
}
