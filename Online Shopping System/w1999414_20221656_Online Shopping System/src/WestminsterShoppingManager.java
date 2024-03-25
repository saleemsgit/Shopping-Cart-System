import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private static ArrayList<Product> products = new ArrayList<>(); 
    private static ArrayList<User> users = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static User loggedInUser;
    public static void main(String[] args) throws Exception {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.loadProducts();
        while(true){
            System.out.print("***************************************\n"+
                                "* Welcome to Online Shopping\n"+
                                "*\t1. Add Product\n"+
                                "*\t2. Remove Product\n"+
                                "*\t3. Print Products\n"+
                                "*\t4. Save Products\n"+
                                "*\t5. Open Customer Console\n"+
                                "***************************************\n"+
                                "Option : ");
            try {
                int option = Integer.parseInt(manager.scanner.nextLine());
                switch(option){
                    case 1 -> manager.addProduct();
                    case 2 -> manager.removeProduct();
                    case 3 -> manager.printProducts();
                    case 4 -> manager.saveProducts();
                    case 5 -> manager.GUI();
                    case 6 -> System.exit(0);
                    default -> System.out.println("Invalid Option");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input");
            }
            
            
        }
        
        
    }

    public Product productToAdd(){
        boolean itemAvailable = false;
        int noProductAvail;
        do {
            try {
                System.out.print("1. Electronic\n2. Clothing\nOption : ");
                int option = Integer.parseInt(scanner.nextLine());
    
                System.out.print("Enter Product ID : ");
                String productID = scanner.nextLine();
    
                for (Product product : products) {
                    if(product.getProductID().equals(productID)){
                        itemAvailable = true;
                        System.out.print("Do you need to update Product info (Y/N) : ");
                        String choice = scanner.nextLine();
                        switch (choice) {
                            case "Y":
                                do {
                                    try {
                                        System.out.println("Update Info");
                                        while(true){
                                            System.out.print("Enter Product Available : ");
                                            noProductAvail = Integer.parseInt(scanner.nextLine());
                                            product.setNoProductAvail(noProductAvail);
                                            if (product.getNoProductAvail() > 50){
                                                System.out.println("Enter Product Less than 50");
                                            } else{
                                                break;
                                            }
                                        }
                                        System.out.print("Enter Product Price : ");
                                        int productPrice = Integer.parseInt(scanner.nextLine());
                                        product.setProductPrice(productPrice);
                                        System.out.println("Product Updated");  
                                        itemAvailable = true; 
                                    } catch (Exception e) {
                                        System.out.println("Invalid Input");
                                        itemAvailable = false;
                                    }
                                } while (!itemAvailable);
                                
                                break;
                            case "N":
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (!itemAvailable){
    
    
                    System.out.print("Enter Product Name : ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter Product Price : ");
                    int productPrice = Integer.parseInt(scanner.nextLine());
                    while (true) {
                        System.out.print("Enter Product Available : ");
                        noProductAvail = Integer.parseInt(scanner.nextLine());
                        if (noProductAvail > 50){
                            System.out.println("Enter Product Less than 50");
                        } else{
                            break;
                        }
                        
                    }
                    
                    switch (option) {
                        case 1:
                            Electronic electronic = new Electronic(productID, productName, noProductAvail, productPrice);
                            System.out.print("Enter Product Brand : ");
                            String productBrand = scanner.nextLine();
                            System.out.print("Enter Product Warranty : ");
                            int productWarranty = Integer.parseInt(scanner.nextLine());
                            electronic.setProductBrand(productBrand);
                            electronic.setProductWarranty(productWarranty);
                            return electronic;
                        case 2:
                            Clothing clothing = new Clothing(productID, productName, noProductAvail, productPrice);
                            System.out.print("Enter Product Size : ");
                            String productSize = scanner.nextLine();
                            System.out.print("Enter Product Color : ");
                            String productColor = scanner.nextLine();
                            clothing.setProductSize(productSize);
                            clothing.setProductColor(productColor);
                            return clothing;
                        default:
                            System.out.println("Invalid Option");
                            break;
                    }
                }   
                itemAvailable = true;
            } catch (Exception e) {
                System.out.println("Invalid Input (1 or 2) expected!");
                itemAvailable = false;
            }    
        } while (!itemAvailable);
        
        
        return null;
    }

    public String productToRemove(){
        for (Product product : products) {
            System.out.println(product.toString());
        }

        System.out.print("Select Product ID : ");
        String productID = scanner.nextLine();

        return productID.strip();
    }

    public void GUI(){

        System.out.print("Login or Create an account\n1. Login\n2. Create an account\nOption : ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                System.out.print("Enter UserName: ");
                String userName = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                for (User user : users) {
                    if (user.getUserName().equals(userName) && user.getPassword().equals(password)){
                        loggedInUser = user;
                        SwingUtilities.invokeLater(() -> new GUIApplication());
                    } else {
                        System.out.println("\n****************\n* No User Found*\n****************\n");
                    }
                }
                break;
        
            case 2:
                System.out.print("Enter UserName: ");
                String createUserName = scanner.nextLine();
                System.out.print("Enter Password: ");
                String createPassword = scanner.nextLine();
                User user = new User(createUserName, createPassword);
                users.add(user);

                try {
                    FileWriter fileWriter = new FileWriter("userData.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    for (User userAccount : users) {
                        String userData = userAccount.getUserName()+","+userAccount.getPassword()+","+userAccount.getPurchaseHistory();
                        bufferedWriter.write(userData);
                        bufferedWriter.newLine(); 
                        
                    }
    
                    bufferedWriter.close();
                    fileWriter.close();
        
                    System.out.println("Data appended to the file successfully.");
        
                } catch (IOException e) {
                    System.err.println("Error writing to the file: " + e.getMessage());
                }
                break;

            default:
                break;
        }
        
    }

    public static User getLoggedUser(){
        return loggedInUser;
    }

    public void loadProducts(){
        try {
 
            FileInputStream fileIn = new FileInputStream("product.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            while(true){
                try {
                    Product product = (Product) in.readObject();
                    products.add(product);
                } catch (Exception e) {
                    break;
                }
            }
            in.close();
            fileIn.close();
            System.out.println("loaded products");

        } catch (Exception e) {
            System.out.println("File Not Found");
        }

        try {
            FileReader fileReader = new FileReader("userData.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] userData = line.split(",");
                User user = new User(userData[0], userData[1]);
                user.setPurchaseHistory(Integer.parseInt(userData[2]));
                users.add(user);
            }

            System.out.println("loaded users");

            bufferedReader.close();

        } catch (IOException e) {
            System.err.println("Error reading the file");
        }
        
    }

    public static ArrayList<Product> getProducts(){
        return products;
    }

    @Override
    public void addProduct() {
        Product product = productToAdd();
 
        if (product == null){
            System.out.println("Cannot Add Product");
           
        } else{
            products.add(product);
        }
      
    }

    @Override
    public void removeProduct() {
        String productID = productToRemove();
        Product productToRemove = null;
        for (Product product : products) {
            
            if (product.getProductID().equals(productID)){
                productToRemove = product;
            } 
        }
        products.remove(productToRemove);   
    }

    @Override
    public void printProducts() {
        Collections.sort(products, Comparator.comparing(Product::getProductName));
        System.out.println("***************\nProduct Details\n***************");
        for (Product product : products) {
            
            System.out.println("\n"+product+"\n");

        }
    }

    @Override
    public void saveProducts() {
        try {
    
            FileOutputStream fileOut = new FileOutputStream("product.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            for (Product product : products) {
                out.writeObject(product);
            }

            out.close();
            fileOut.close();
            
            System.out.println("Products saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
         
            FileWriter fileWriter = new FileWriter("userData.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (User userAccount : users) {
                String userData = userAccount.getUserName()+","+userAccount.getPassword()+","+userAccount.getPurchaseHistory();
                bufferedWriter.write(userData);
                bufferedWriter.newLine(); 
                
            }
           
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Users Saved to file");

        } catch (IOException e) {
            System.err.println("Error writing to the file");
        }
    }
}