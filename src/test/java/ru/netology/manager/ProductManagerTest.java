package ru.netology.manager;

import org.junit.jupiter.api.Test;
import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.repository.NotFoundException;
import ru.netology.repository.Repository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductManagerTest {
    private Repository repository = new Repository();
    private ProductManager productManager = new ProductManager(repository);
    private Book story = new Book(1, "Story", 222, "John");
    private Book legend = new Book(2, "g45", 333, "Michael");
    private Smartphone samsong = new Smartphone(3, "g45", 1000, "samsong");
    private Smartphone iponel = new Smartphone(4, "i12", 2000, "iponel");

    @Test
    public void shouldAddAndSearchBookByAuthor() {
        productManager.add(story);

        Product[] actual = productManager.searchBy("John");
        Product[] expected = new Product[]{story};
        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldAddAndSearchBookByName() {
        productManager.add(story);

        Product[] actual = productManager.searchBy("Story");
        Product[] expected = new Product[]{story};
        assertArrayEquals(actual, expected);
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
    public void shouldSearchBookWithWrongText() {
        productManager.add(story);
        Product[] actual = productManager.searchBy("random");
        Product[] expected = new Product[]{};
        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldSearchSmartphoneWithWrongText() {
        productManager.add(iponel);
        Product[] actual = productManager.searchBy("random");
        Product[] expected = new Product[]{};
        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldSearchProductsWithSameRequest() {
        productManager.add(legend);
        productManager.add(samsong);
        Product[] actual = productManager.searchBy("g45");
        Product[] expected = new Product[]{legend, samsong};
        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldSearchProductWithEmptyRequest() {
        productManager.add(legend);
        Product[] actual = productManager.searchBy(" ");
        Product[] expected = new Product[]{};
        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldDeleteSmartphone() {
        productManager.add(samsong);
        productManager.add(iponel);
        productManager.delete(3);

        Product[] actual = repository.findAll();
        Product[] expected = new Product[]{iponel};
        assertArrayEquals(actual, expected);
    }

    @Test
    public void shouldGenerateNotFoundException() {
        productManager.add(legend);
        productManager.add(samsong);

        assertThrows(NotFoundException.class, () -> {
            repository.removeById(234);
        });
    }
}