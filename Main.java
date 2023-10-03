// Magnus Nording, magnus.nording@iths.se
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);

    private static ArrayList<Product> allProducts = new ArrayList<>(); // ArrayList allProducts

    public static void main(String[] args) {

        boolean exitMenu = false;
        // Presentation layer
        System.out.println("FRUKT OCH GRÖNT"); // Program start
        System.out.println("----------------");
        System.out.println("Programmet startas.");
        System.out.println("Här kan ni lägga in varor, ta bort dem och kontrollera priset.\n");

        displayMenu();

        allProducts.add(new Product("Banan", 25.90, new String[]{"Chiquita", "Frukt"}, true));
        allProducts.add(new Product("Äpple", 25.99, new String[]{"Granny Smith", "Frukt"}, true));
        allProducts.add(new Product("Nektarin", 10, new String[]{"Stenfrukt", "Frukt"}, false));
        allProducts.add(new Product("Apelsin", 29.90, new String[]{"Jaffa", "Frukt"}, true));
        allProducts.add(new Product("Persika", 15, new String[]{"Stenfrukt", "Frukt"}, false));

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
                    //case 6 -> updateProductInfo();
                    case 7 -> displayMenu();
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
                "5. Kolla totalpris.",
                //"6. Uppdatera en produkt.",
                "7. Visa menyn."
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

                System.out.print("Ange varugrupp/-er (kommaseparerad lista): ");
                String categoryInput = input.nextLine();
                String[] categoryArray = categoryInput.split(",");
                System.out.println("Ange om det är viktpris eller ej (true/false): ");
                String isWeightInput = input.nextLine();
                boolean isWeightPrice = Boolean.parseBoolean(isWeightInput);

                Product product = new Product(nameInput, priceInput, categoryArray, isWeightPrice);
                allProducts.add(product);
                System.out.println(product.getName() + " är tillagd.");
                System.out.println("Välj 1. Lägga till fler produkter.\nVälj 7. Tillbaka till menyn");
                validInput = false;
            } catch (InputMismatchException ime) {
                input.nextLine();
                System.out.println("Felaktig inmatning, försök igen.\n");
            }
        }
    }

    // 2. Ta bort en produkt
    private static void removeProduct() {
        System.out.println("Ange produkten du vill ta bort: ");
        String productToRemove = input.nextLine().trim().toLowerCase();

        boolean removed = false;
        Iterator<Product> iterator = allProducts.iterator();

        while (iterator.hasNext()) {
            Product product = iterator.next();
            String productName = product.getName().toLowerCase();

            // Kolla om produkten ska tas bort baserat på namnet
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
        System.out.println("Välj 7. Tillbaka till menyn");
    }

    // 3. Visa alla tillagda produkter
    private static void displayAllProducts() {
        if (!allProducts.isEmpty()) {
            for (Product product : allProducts) {
                System.out.println(product.toString());
            }
            System.out.println("Välj 7. Tillbaka till menyn");
        } else {
            System.out.println("Produktlistan är tom.");
            System.out.println("Välj 7. Tillbaka till menyn");
        }
    }

    // Sök produkt är fan inte bra just nu!!!
    private static void searchProduct() {
        Product product = null;
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
        System.out.println("Välj 4. Fler sökningar\nVälj 7. Tillbaka till menyn");
    }


    // 5. Kolla totalpris
    private static void checkPrice() {
        System.out.println("Ange om det är viktpris(true) eller styckpris(false).");
        String isWeightInput = input.nextLine().trim();
        boolean isWeight = Boolean.parseBoolean(isWeightInput);

        // Om det är pris/kg
        boolean validInput = true;
        if (isWeight) {
            while (validInput) {
                try {
                    System.out.println("Ange vikten: ");
                    String weight = input.nextLine().trim();
                    weight = weight.replace(",", "."); // Ersätt komma med punkt
                    double weightInput = Double.parseDouble(weight);

                    System.out.println("Ange kilopris: ");
                    String price = input.nextLine();
                    price = price.replace(",", ".");
                    double priceInput = Double.parseDouble(price);
                    double result = priceInput * weightInput;
                    System.out.printf("Priset för %.2f kg: %.2f kr.\n", weightInput, result);
                    System.out.println("Välj 5. Mer priskoll.\nVälj 7. Tillbaka till menyn.");
                    validInput = false;
                } catch (NumberFormatException nfe) {
                    input.nextLine();
                    System.out.println("Felaktig inmatning, försök igen.\n");
                }
            }
            // om det är pris/st
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
            System.out.printf("Priset för %d st: %.2f kr.\n", numOfUnits, result);
            System.out.println("Välj 5. Mer priskoll.\nVälj 7. Tillbaka till menyn.");
        }
    }
}


