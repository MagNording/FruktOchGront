// Magnus Nording, magnus.nording@iths.se
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
        System.out.println("Här kan ni lägga till varor, ta bort dem och kontrollera priset.\n");

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
        System.out.println("Välj 1. Lägga till fler produkter.\nVälj 6. Tillbaka till menyn");
    }

    // 2. Ta bort en produkt
    public static void removeProduct() {
        System.out.println("Ange produkten du vill ta bort: ");
        String productToRemove = input.nextLine().trim().toLowerCase();
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

            if (productName.contains(productToRemove)) {
                iterator.remove();
                removed = true;
            }
        }
        if (removed) {
            System.out.println("Produkten har tagits bort.");
        } else {
            System.out.println("Ingen matchande produkt hittades.");
        }
        System.out.println("Välj 6. Tillbaka till menyn");
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
            String searchTerm = input.nextLine().trim();

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
        System.out.println("Välj 4. Fler sökningar\nVälj 6. Tillbaka till menyn");
    }

    // 5. Kolla totalpris
    public static void checkPrice() {
        System.out.println("Priskollen");
        boolean isWeight = false;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("1. Viktpris\n2. Styckpris");
                String userInput = input.nextLine();
                int choice = Integer.parseInt(userInput);

                if (choice == 1 || choice == 2) {
                    isWeight = (choice == 1); // Sätt isWeight baserat på användarens val
                    validInput = true;
                } else {
                    System.out.println("Felaktig inmatning, välj 1 eller 2.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Felaktig inmatning, försök igen.");
            }
        }
        if (isWeight) {
            weightPrice();
        } else {
            unitPrice();
        }
    }

    // Om det är pris/kg
    public static void weightPrice() {
            double weightInput = getInputDouble("Ange vikten: ");
            double priceInput = getInputDouble("Ange kilopris: ");

            double result = priceInput * weightInput;
            System.out.printf("Priset för %.2f kg: %.2f kr.%n", weightInput, result);
            System.out.println("Välj 5. Mer priskoll.\nVälj 6. Tillbaka till menyn.");
        }

    // Om det är pris/st
    public static void unitPrice() {
        int pricePerUnit = getInputInt("Ange ett heltal för pris/enhet: ");
        int numOfUnits = getInputInt("Ange antalet enheter: ");

        double result = pricePerUnit * numOfUnits;
        System.out.printf("Priset för %d st: %.2f kr.%n", numOfUnits, result);
        System.out.println("Välj 5. Mer priskoll.\nVälj 6. Tillbaka till menyn.");
    }

    // 6. Visa menyn
    public static void displayMenu() {
        // Skapa en array
        String[] menu = {
                "0. Avsluta programmet.",
                "1. Lägg till en produkt.",
                "2. Ta bort en produkt.",
                "3. Visa alla produkter.",
                "4. Sök produkt.",
                "5. Kolla totalpris.",
                "6. Visa menyn."
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
            nameInput = input.nextLine().trim();
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
                String price = input.nextLine();
                price = price.replace(",", ".");
                return Double.parseDouble(price);
            } catch (NumberFormatException nfe) {
                System.out.println("Felaktig inmatning, försök igen.");
            }
        }
    }

    public static String[] getProductCategories() {
        System.out.print("Ange varugrupp/-er (kommaseparerad lista): ");
        String categoryInput = input.nextLine();
        return categoryInput.split(",");
    }

    public static boolean getProductPriceType() {
        while (true) {
            System.out.println("1. Viktpris\n2. Styckpris");
            try {
                int isWeightInput = input.nextInt();
                if (isWeightInput == 1 || isWeightInput == 2) {
                    return isWeightInput == 1;
                } else {
                    System.out.println("Felaktig inmatning, välj 1 eller 2.");
                }
            } catch (InputMismatchException e) {
                input.nextLine(); // Rensa inmatningsbufferten
                System.out.println("Felaktig inmatning, välj 1 eller 2.");
            }
        }
    }

    public static int getInputInt(String prompt) {
        boolean validInput = false;
        int inputValue = 0;

        while (!validInput) {
            try {
                System.out.print(prompt);
                inputValue = input.nextInt();
                input.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Felaktig inmatning, ange ett heltal.");
            }
        }
        return inputValue;
    }

    public static double getInputDouble(String prompt) {
        boolean validInput = false;
        double inputValue = 0;

        while (!validInput) {
            try {
                System.out.print(prompt);
                String userInput = input.nextLine().trim().replace(",", ".");
                inputValue = Double.parseDouble(userInput);
                validInput = true;
            } catch (NumberFormatException nfe) {
                System.out.println("Felaktig inmatning, försök igen.");
            }
        }
        return inputValue;
    }
}
