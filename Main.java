// Magnus Nording, magnus.nording@iths.se
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);

    private static ArrayList<Product> allProducts = new ArrayList<>(); // ArrayList allProducts

    public static void main(String[] args) {

        boolean exitMenu = false;

        System.out.println("FRUKT OCH GRÖNT"); // Program start
        System.out.println("----------------");
        System.out.println("Programmet startas.");
        System.out.println("Här kan ni lägga in varor, ta bort dem och kontrollera priset.\n");

        do {
            displayMenu();

            try {
                int menuChoice = input.nextInt();
                input.nextLine();
                switch (menuChoice) {
                    case 0 -> exitMenu = true;
                    case 1 -> addNewProduct();
                    case 2 -> removeProduct();
                    case 3 -> displayAllProducts();
                    case 4 -> searchProduct();
                    case 5 -> checkPrice();
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Felaktig inmatning, försök igen.");
            }
            System.out.println();
        } while (!exitMenu);

        System.out.println("Tack, programmet avslutas."); // Program End
    }

    private static void displayMenu() {
        // Skapa en array
        String[] menu = {
                "0. Avsluta programmet.",
                "1. Lägg till en produkt.",
                "2. Ta bort en produkt.",
                "3. Visa alla produkter.",
                "4. Sök produkt.",
                "5. Kolla totalpris."
                };
        for (String choice : menu) {
            System.out.println(choice);
        }
        System.out.print("\nAnge ditt menyval: ");
    }

    private static void addNewProduct() {
        boolean validInput = true;
        while (validInput) {
            try {
                System.out.print("Ange produktens namn: ");
                String nameInput = input.nextLine();
                System.out.print("Ange kilopris eller styckpris: ");
                String price = input.nextLine();
                price = price.replace(",", "."); // Ersätt komma med punkt
                double priceInput = Double.parseDouble(price); // Parse price till en double
                System.out.print("Ange varugrupp: ");
                String categoryInput = input.nextLine();
                System.out.println("Ange om det är viktpris eller ej (true/false): ");
                String isWeightInput = input.nextLine();
                boolean isWeightPrice = Boolean.parseBoolean(isWeightInput);

                Product product = new Product(nameInput, priceInput, categoryInput, isWeightPrice);
                allProducts.add(product);
                validInput = false;
            } catch (InputMismatchException ime) {
                input.nextLine();
                System.out.println("Felaktig inmatning, försök igen.\n");
            }
        }
    }

    private static void removeProduct() {
        System.out.println("Ange produkten du vill ta bort: ");
        String productToRemove = input.nextLine().toLowerCase();

        ArrayList<Product> productsToRemove = new ArrayList<>(); // ArrayList

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if (product.getName().toLowerCase().contains(productToRemove) ||
                    product.getProductGroup().toLowerCase().contains(productToRemove)) {
                productsToRemove.add(product);
            }
        }
        if (productsToRemove.isEmpty()) {
            System.out.println("Inga matchande produkter hittades.");
        } else {
            for (Product product : productsToRemove) {
                allProducts.remove(product);
            }
            System.out.println("Alla matchande produkter har tagits bort.");
        }
    }

    private static void displayAllProducts() {
        if (!allProducts.isEmpty()) {
            for (Product product : allProducts) {
                System.out.println(product.toString());
            }
        } else {
            System.out.println("Produktlistan är tom.");
        }
    }


    private static Product searchProduct() {
        Product product = null;
        if (allProducts.isEmpty()) {
            System.out.println("Produktlistan är tom.");
        } else {
            System.out.println("Ange sökterm: ");
            String searchTerm = input.nextLine().trim();
            for (int i = 0; i < allProducts.size(); i++) {
                product = allProducts.get(i);
                if (product.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        product.getProductGroup().toLowerCase().contains(searchTerm.toLowerCase())) {
                    System.out.println(product);
                }
            }
        }
        return product;
    }

    private static void checkPrice() {
        System.out.println("Ange om det är viktpris(true) eller styckpris(false).");
        String isWeightInput = input.nextLine().trim();
        boolean isWeight = Boolean.parseBoolean(isWeightInput);

        if (isWeight) {
            System.out.println("Ange vikten: ");
            String weight = input.nextLine().trim();
            weight = weight.replace(",", "."); // Ersätt komma med punkt
            double weightInput = Double.parseDouble(weight);

            System.out.println("Ange kilopris: ");
            String price = input.nextLine();
            price = price.replace(",", ".");
            double priceInput = Double.parseDouble(price);
            double result = priceInput * weightInput;
            System.out.printf("Priset för %.2f kg av varan: %.2f kr.\n", weightInput, result);

         } else {
            int pricePerUnit;
            int numOfUnits = 0;
            double result = 0;
            try {
                System.out.print("Ange ett heltal för pris/enhet: ");
                pricePerUnit = input.nextInt();
                input.nextLine();
                System.out.print("Ange antalet enheter: ");
                numOfUnits = input.nextInt();
                input.nextLine();
                result = pricePerUnit * numOfUnits;
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Felaktig inmatning, ange en siffra.");
            }
            System.out.printf("Priset för %d st av varan: %.2f kr.\n", numOfUnits, result);
        }
    }
}



