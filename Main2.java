// Magnus Nording, magnus.nording@iths.se
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    static ArrayList<Product> allProducts = new ArrayList<>(); // ArrayList allProducts

    public static void main(String[] args) {

        boolean exitMenu = false;

        System.out.println("FRUKT OCH GRÖNT\nProgrammet startas.\n"); // Programstart

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

        System.out.println("Tack, programmet avslutas."); // Programslut
    }

    public static void displayMenu() {
        // Skapa en array
        String[] menu = {
                "0. Avsluta programmet.",
                "1. Lägg till en vara.",
                "2. Ta bort en vara.",
                "3. Söka efter en vara.",
                "Ange ditt menyval: "};
        for (String choice : menu) {
            System.out.println(choice);  
        
        }
    }

    public static void addNewProduct() {
        boolean validInput = true;
        while (validInput) {
            try {
                System.out.print("Ange produktnamn: ");
                String nameInput = input.nextLine();
                System.out.print("Ange kilopris eller styckpris: ");
                double priceInput = input.nextDouble();
                input.nextLine();
                System.out.print("Ange varugrupp: ");
                String categoryInput = input.nextLine();
                
                ProductType product = new ProductType(nameInput, priceInput, categoryInput);
                allProducts.add(fruit);
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
        for (int i = 0; i < allProducts.size(); i++) {
            ProductType product = allProducts.get(i);
            // Om varunamn eller varugrupp innehåller input
            if (product.getName().toLowerCase().contains(productToRemove.toLowerCase()) ||
                    product.getProductCategory().toLowerCase().contains(productToRemove.toLowerCase())) {
                System.out.println("Är du säker på att du vill ta bort: " + product.getName() + " (j/n)");
                String answer = input.nextLine();
                
                if (answer.equalsIgnoreCase("j")) {
                    allProducts.remove(i);
                    found = true;
                    break;
                } else if (answer.equalsIgnoreCase("n")) {
                // Användaren vill gå tillbaka till menyn
                return;
                }
            }
        }
        if (found) {
            System.out.println(productToRemove + " har tagits bort.");
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
