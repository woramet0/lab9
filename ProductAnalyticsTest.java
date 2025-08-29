import java.util.List;

public class ProductAnalyticsTest {

    private static int passedCount = 0;
    private static int failedCount = 0;

    // Helper method for checking conditions
    private static void check(String testName, boolean condition) {
        if (condition) {
            System.out.println("PASSED: " + testName);
            passedCount++;
        } else {
            System.out.println("FAILED: " + testName);
            failedCount++;
        }
    }

    public static void main(String[] args) {
        // --- Setup ---
        List<Product> products = List.of(
            new Product("p1", "Laptop", "Electronics", 1200.0, 10),
            new Product("p2", "Mouse", "Electronics", 25.0, 150),
            new Product("p3", "Keyboard", "Electronics", 75.0, 0), // Out of stock
            new Product("p4", "Apple", "Fruit", 1.5, 200),
            new Product("p5", "Banana", "Fruit", 0.5, 500)
        );
        ProductAnalytics analytics = new ProductAnalytics(products);
        
        System.out.println("--- Running Product Analytics Manual Tests ---");

        // --- Test Cases ---
        
        // Test 1: findProductsByCategory
        List<Product> fruits = analytics.findProductsByCategory("Fruit");
        List<Product> electronics = analytics.findProductsByCategory("Electronics");
        check("Find 'Fruit' category should return 2 items", fruits.size() == 2);
        check("Find 'Electronics' category should return 3 items", electronics.size() == 3);

        // Test 2: getProductNamesWithPriceLessThan
        List<String> cheapProducts = analytics.getProductNamesWithPriceLessThan(50.0);
        check("Get names with price < 50.0 should return 3 names", cheapProducts.size() == 3);
        check("Result for price < 50.0 should contain 'Mouse'", cheapProducts.contains("Mouse"));
        check("Result for price < 50.0 should contain 'Apple'", cheapProducts.contains("Apple"));
        check("Result for price < 50.0 should contain 'Banana'", cheapProducts.contains("Banana"));

        // Test 3: calculateTotalStockValueForCategory
        double electronicsValue = analytics.calculateTotalStockValueForCategory("Electronics");
        double fruitValue = analytics.calculateTotalStockValueForCategory("Fruit");
        // Electronics: (1200*10) + (25*150) + (75*0) = 12000 + 3750 = 15750
        // Fruit: (1.5*200) + (0.5*500) = 300 + 250 = 550
        check("Total stock value for 'Electronics' should be 15750.0", Math.abs(electronicsValue - 15750.0) < 0.001);
        check("Total stock value for 'Fruit' should be 550.0", Math.abs(fruitValue - 550.0) < 0.1);

        // Test 4: hasProductOutOfStock
        check("Should detect that there is a product out of stock", analytics.hasProductOutOfStock());

        // --- Test Summary ---
        System.out.println("\n--------------------");
        System.out.println("--- Test Summary ---");
        System.out.println("Passed: " + passedCount + ", Failed: " + failedCount);
        if (failedCount == 0) {
            System.out.println("Excellent! All tests passed!");
        } else {
            System.out.println("Some tests failed.");
        }
    }
}