package com.jvg.samples.backend.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.jvg.samples.backend.data.Availability;
import com.jvg.samples.backend.data.Category;
import com.jvg.samples.backend.data.Product;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MockDataGenerator {
    private static int nextCategoryId = 1;
    private static int nextProductId = 1;
    private static final Random random = new Random(1);
    private static final String categoryNames[] = new String[] {
            "Children's books", "Best sellers", "Romance", "Mystery",
            "Thriller", "Sci-fi", "Non-fiction", "Cookbooks" };

    private static String[] word1 = new String[] { "The art of", "Mastering",
            "The secrets of", "Avoiding", "For fun and profit: ",
            "How to fail at", "10 important facts about",
            "The ultimate guide to", "Book of", "Surviving", "Encyclopedia of",
            "Very much", "Learning the basics of", "The cheap way to",
            "Being awesome at", "The life changer:", "The Vaadin way:",
            "Becoming one with", "Beginners guide to",
            "The complete visual guide to", "The mother of all references:" };

    private static String[] word2 = new String[] { "gardening",
            "living a healthy life", "designing tree houses", "home security",
            "intergalaxy travel", "meditation", "ice hockey",
            "children's education", "computer programming", "Vaadin TreeTable",
            "winter bathing", "playing the cello", "dummies", "rubber bands",
            "feeling down", "debugging", "running barefoot",
            "speaking to a big audience", "creating software", "giant needles",
            "elephants", "keeping your wife happy" };

    public static List<Category> createCategories(MongoTemplate mo) {
        List<Category> categories = new ArrayList<Category>();
        for (String name : categoryNames) {
            Category c = createCategory(name);
            mo.save(c);
            categories.add(c);
        }
        return categories;

    }

    public static List<Product> createProducts(List<Category> categories, MongoTemplate mo) {
        List<Product> products = new ArrayList<Product>();
        for (int i = 0; i < 100; i++) {
            Product p = createProduct(categories);
            mo.save(p);
            products.add(p);
        }

        return products;
    }

    private static Category createCategory(String name) {
        Category c = new Category();
        c.setName(name);
        return c;
    }

    private static Product createProduct(List<Category> categories) {
        Product p = new Product();
        p.setProductName(generateName());

        p.setPrice(new BigDecimal((random.nextInt(250) + 50) / 10.0));
        p.setAvailability(Availability.values()[random.nextInt(Availability
                .values().length)]);
        if (p.getAvailability() == Availability.AVAILABLE) {
            p.setStockCount(random.nextInt(523));
        }

        p.setCategory(getCategory(categories, 1, 2));
        return p;
    }

    private static Set<Category> getCategory(List<Category> categories,
            int min, int max) {
        int nr = random.nextInt(max) + min;
        HashSet<Category> productCategories = new HashSet<Category>();
        for (int i = 0; i < nr; i++) {
            productCategories.add(categories.get(random.nextInt(categories
                    .size())));
        }

        return productCategories;
    }

    private static String generateName() {
        return word1[random.nextInt(word1.length)] + " "
                + word2[random.nextInt(word2.length)];
    }

}
