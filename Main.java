// Magnus Nording, magnus.nording@iths.se
import se.java23.nording.utils.UserInput;

import java.util.*;

public class Main {
    public static Scanner input = new Scanner(System.in);

    public static ArrayList<Product> allProducts = new ArrayList<>(); // ArrayList allProducts

    public static void main(String[] args) {

        boolean exitMenu = false;
        // Presentation layer
        System.out.println("FRUKT OCH GRÖNT"); // Program start
        System.out.println("----------------");
        System.out.println("Programmet startas.");
        System.out.println("Här kan ni lägga till varor, ta bort dem och kontrollera priset.");

        displayMenu();

        allProducts.add(new Product("Nektarin", 10, new String[]{"Stenfrukt", "Frukt"}, false));
        allProducts.add(new Product("Morot", 16.48, new String[]{"Rotfrukt", "Grönsak"}, true));
        allProducts.add(new Product("Broccoli", 18.83, new String[]{"Kål", "Grönsak"}, true));


        do {
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
                    case 6 -> displayMenu();
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Felaktig inmatning, försök igen.");
            }
            System.out.println();
        } while (!exitMenu);

        System.out.println("Tack, programmet avslutas."); // Program End
    }

    // 1. Lägg till en produkt
    public static void addNewProduct() {
        String nameInput = getProductName();
        double priceInput = getProductPrice();
        String[] categoryArray = getProductCategories();
        boolean isWeightPrice = getProductPriceType();

        Product product = new Product(nameInput, priceInput, categoryArray, isWeightPrice);
        allProducts.add(product);

        System.out.println(product.getName() + " är tillagd.");
        displayMenu();
    }

    // 2. Ta bort en produkt
    public static void removeProduct() {
        System.out.println("Ange produkten du vill ta bort: ");
        String productToRemove = UserInput.readString();
        if (productToRemove.isEmpty()) {
            System.out.println("Ingen produkt angiven. Ingen ändring har gjorts.");
            System.out.println("Välj 2. Ange exakt produktnamn.");
            return;
        }
        boolean removed = false;
        Iterator<Product> iterator = allProducts.iterator();

        while (iterator.hasNext()) {
            Product product = iterator.next();
            String productName = product.getName().toLowerCase();

            if (productName.equalsIgnoreCase(productToRemove)) {
                iterator.remove();
                removed = true;
            }
        }
        if (removed) {
            System.out.println("Produkten har tagits bort.");
        } else {
            System.out.println("Ingen matchande produkt hittades.");
        }
        displayMenu();
    }

    // 3. Visa alla tillagda produkter
    public static void displayAllProducts() {
        if (!allProducts.isEmpty()) {
            Collections.sort(allProducts, Comparator.comparing(Product::getName));
            for (Product product : allProducts) {
                System.out.println(product.toString());
            }
            System.out.println();
            displayMenu();
        } else {
            System.out.println("Produktlistan är tom.");
            displayMenu();
        }
    }

    // 4. Söka produkt
    public static void searchProduct() {
        Product product;
        if (allProducts.isEmpty()) {
            System.out.println("Produktlistan är tom.");
        } else {
            System.out.println("Ange sökterm: ");
            String searchTerm = UserInput.readString();

            for (int i = 0; i < allProducts.size(); i++) {
                product = allProducts.get(i);
                if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    System.out.println(product);
                } else {
                    boolean foundInGroup = false;
                    String[] productGroup = product.getProductGroup();

                    if (productGroup != null) {
                        for (String group : productGroup) {
                            if (group.toLowerCase().contains(searchTerm.toLowerCase())) {
                                foundInGroup = true;
                                break; // Hittade söktermen i en produktgrupp, avsluta loopen
                            }
                        }
                    }
                    if (foundInGroup) {
                        System.out.println(product);
                    }
                }
            }
        }
        displayMenu();
    }

   // 5. Kolla totalpris
    public static void checkPrice() { // Tillbaka till menyn?
        Product productToCheck = null;
        System.out.println("Ange sökterm: ");
        String searchTerm = UserInput.readString();

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                productToCheck = product;
                System.out.println(product);
                break;
            }
        }
        if (productToCheck != null) {
            System.out.print("1. Viktpris\n2. Styckpris\n> ");
            int isWeightInput = UserInput.readInt();
            boolean isWeightPrice = isWeightInput == 1;

            if (isWeightPrice) {
                weightPrice(productToCheck);
            } else {
                unitPrice(productToCheck);
            }
        }
    }

    public static void weightPrice(Product productToCheck) {
        boolean validInput = true;
        while (validInput) {
            System.out.println("Ange vikten: ");
            double weightInput = UserInput.readDouble();

            // Använd priset från produkten
            double priceInput = productToCheck.getPrice();
            double result = priceInput * weightInput;
            System.out.printf("Priset för %.2f kg: %.2f kr.\n", weightInput, result);
            displayMenu();
            validInput = false;
        }
    }

    public static void unitPrice(Product productToCheck) {
        int numOfUnits;
        double result;
        System.out.print("Ange antalet enheter: ");
        numOfUnits = UserInput.readInt();
        // Använd priset från produkten
        double pricePerUnit = productToCheck.getPrice();
        result = pricePerUnit * numOfUnits;

        System.out.printf("Priset för %d st: %.2f kr.\n", numOfUnits, result);
        displayMenu();
    }

    // 6. Visa menyn
    public static void displayMenu() {
        // Skapa en array
        String[] menu = {
                "\n0. Avsluta programmet.",
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


    public static String getProductName() {
        String nameInput;
        while (true) {
            System.out.print("Ange produktens namn: ");
            nameInput = UserInput.readString();
            if (!nameInput.isEmpty()) {
                return nameInput;
            }
            System.out.println("Du måste ange ett namn.");
        }
    }

    public static double getProductPrice() {
        while (true) {
            try {
            System.out.print("Ange pris: ");
            String price = UserInput.readString();
            price = price.replace(",", ".");
            return Double.parseDouble(price);
            } catch (NumberFormatException nfe) {
                System.out.println("Felaktig inmatning, försök igen.");
            }
        }
    }

    public static String[] getProductCategories() {
        System.out.print("Ange varugrupp/-er (kommaseparerad lista): ");
        String categoryInput = UserInput.readString();
        return categoryInput.split(",");
    }

    public static boolean getProductPriceType() {
        while (true) {
            System.out.println("1. Viktpris\n2. Styckpris");
            int isWeightInput = UserInput.readInt();
            if (isWeightInput == 1 || isWeightInput == 2) {
                return isWeightInput == 1;
            } else {
                System.out.println("Felaktig inmatning, välj 1 eller 2.");
            }
        }
    }
}
