// Magnus Nording, magnus.nording@iths.se
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);

    private static ArrayList<Product> allProducts = new ArrayList<>(); // ArrayList allProducts

    public static void main(String[] args) {

        boolean exitMenu = false;

        System.out.println("FRUKT OCH GRÖNT\nProgrammet startas.\n"); // Program start

        do {
            displayMenu();

            try {
                int menuChoice = input.nextInt();
                input.nextLine();
                switch (menuChoice) {
                    case 0 -> exitMenu = true;
                    case 1 -> addNewProduct();
                    case 2 -> removeProduct();
                    case 3 -> searchProduct();
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Felaktig inmatning, försök igen.");
            }
            System.out.println();
        } while (!exitMenu);

        System.out.println("Tack, programmet avslutas."); // Program End
    }

    public static void displayMenu() {
        // Skapa en array
        String[] menu = {
                "0. Avsluta programmet.",
                "1. Lägg till en produkt.",
                "2. Ta bort en produkt.",
                "3. Sök produkt.",
                "Ange ditt menyval: "};
        for (String choice : menu) {
            System.out.println(choice);
        }
    }

    public static void addNewProduct() {
        boolean validInput = true;
        while (validInput) {
            try {
                System.out.print("Ange varans namn: ");
                String nameInput = input.nextLine();
                System.out.print("Ange kilopris eller styckpris: ");
                double priceInput = input.nextDouble();
                input.nextLine();
                System.out.print("Ange varugrupp: ");
                String categoryInput = input.nextLine();
                
                Product product = new Product(nameInput, priceInput, categoryInput);
                allProducts.add(product);
                validInput = false;
            } catch (InputMismatchException ime) {
                input.nextLine();
                System.out.println("Felaktig inmatning, försök igen.\n" +
                        "Tänk på att använda kommatecken i decimaltal.");
            }
        }
    }

    public static void removeProduct() {
    System.out.println("Ange produkten du vill ta bort: ");
    String productToRemove = input.nextLine();
    boolean found = false;
    ArrayList<Product> productsToRemove = new ArrayList<>();

    for (int i = 0; i < allProducts.size(); i++) {
        Product product = allProducts.get(i);
        // om varunamn eller varugrupp påminner om inputen
        if (product.getName().toLowerCase().contains(productToRemove.toLowerCase()) ||
            product.getProductGroup().toLowerCase().contains(productToRemove.toLowerCase())) {
            productsToRemove.add(product);
        }
    }
    if (productsToRemove.isEmpty()) {
        System.out.println("Inga produkter hittades.");
    } else {
        System.out.println("Följande produkter hittades:");

        for (Product product : productsToRemove) {
            System.out.println(product.getName());
        }
        System.out.println("Är du säker på att du vill ta bort alla dessa produkter? (j/n)");
        String answer = input.nextLine();
        if (answer.equalsIgnoreCase("j")) {
            for (Product product : productsToRemove) {
                allProducts.remove(product);
            }
            System.out.println("Alla matchande produkter har tagits bort.");
        } else if (answer.equalsIgnoreCase("n")) {
            // Användaren vill gå tillbaka till menyn
            return;
        }
    }
}

        if (found) {
            System.out.println(searchProduct() + " har tagits bort.");
        } else {
            System.out.println("Produkten hittades inte.");
        }
    }

    public static Product searchProduct() {
        if (allProducts.isEmpty()) {
            System.out.println("Produktlistan är tom.");
        } else {
            System.out.println("Ange sökterm: ");
            String searchTerm = input.nextLine();
            
            for (int i = 0; i < allProducts.size(); i++) {
                Product product = allProducts.get(i);
                if (product.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        product.getProductGroup().toLowerCase().contains(searchTerm.toLowerCase())) {
                    System.out.println(product);
                    return product;
                }
            }
        }
    }
}
