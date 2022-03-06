package ru.netology.manager;

import org.junit.jupiter.api.Test;
import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.domain.TV;
import ru.netology.repository.Repository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ProductManagerTest {
    private Repository repository = new Repository();
    private ProductManager productManager = new ProductManager(repository);
    private Book story = new Book(1, "Story", 222, "John");
    private Book legend = new Book(2, "Legend", 333, "Michael");
    private Smartphone samsong = new Smartphone(3, "g45", 1000, "samsong");
    private Smartphone iponel = new Smartphone(4, "i12", 2000, "iponel");
    private TV sunny = new TV(1, "sunny", 2500);

    @Test
    public void shouldAddAndSearchBook() {
        productManager.add(story);
        productManager.searchBy("John");

        Product[] actual = productManager.searchBy("John");
        Product[] expected = new Product[]{story};

        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldDeleteSmartphone() {
        productManager.add(samsong);
        productManager.add(iponel);
        productManager.delete(3);

        Product[] actual = repository.findAll();
        Product[] expected = new Product[]{iponel};
    }

    @Test
    public void shouldSearchBookByName() {
        productManager.add(story);

        Boolean actual = productManager.matches(story, "Story");
        Boolean expected = true;
    }

    @Test
    public void shouldNotFindAnyMatches() {
        productManager.add(sunny);

        Boolean actual = productManager.matches(sunny, "sunny");
        Boolean expected = false;
    }

    @Test
    public void shouldAddAndSearchSmartphoneByManufacturer() {
        productManager.add(samsong);

        Product[] actual = productManager.searchBy("samsong");
        Product[] expected = new Product[]{samsong};
        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldAddAndSearchSmartphoneByName() {
        productManager.add(samsong);
        Product[] actual = productManager.searchBy("g45");
        Product[] expected = new Product[]{samsong};
        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldNotSearchBookWithWrongText() {
        productManager.add(story);
        Product[] actual = productManager.searchBy("random");
        Product[] expected = new Product[]{};
        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldNotSearchSmartpnoneWithWrongText() {
        productManager.add(iponel);
        Product[] actual = productManager.searchBy("random");
        Product[] expected = new Product[]{};
        assertArrayEquals(actual, expected);
    }
}