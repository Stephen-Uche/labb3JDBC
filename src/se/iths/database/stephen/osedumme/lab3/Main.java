package se.iths.database.stephen.osedumme.lab3;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\Admin\\labb3Jdbc2022.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static void printActions() {
        System.out.println("\nVälj:\n");
        System.out.println("0  - Stäng av\n" +

                "1  - Lägga till en ny recipe\n" +
                "2  - Lägga till en ny ingredient\n"+
                "3  - Visa alla \n" +
                "4  - Sök efter alla i recipe name\n" +
                "5  - Uppdatera en recipe\n" +
                "6  - Ta bort en recipe\n" +
                "7  - Visa en lista över alla val.");


    }

    // Användarens inmatningar
    private static void insertRecipes(){
        System.out.println("Skriv in name på recipe: ");
        String inputName = scanner.nextLine();
        System.out.println("Skriv in pris på recipe: ");
        int inputPris = scanner.nextInt();
        insertRecipes(inputName,inputPris);
        scanner.nextLine();
    }
    private static void insertIngredients(){
        System.out.println("Skriv in name på ingredient: ");
        String inputName = scanner.nextLine();
        System.out.println("Skriv in pris på ingredient: ");
        int inputPris = scanner.nextInt();
        insertIngredients(inputName, inputPris);
        scanner.nextLine();
    }
    private static void delete(){
        System.out.println("Skriv in id:t på recipe som ska tas bort: ");
        int inputId = scanner.nextInt();
        delete(inputId);
        scanner.nextLine();
    }

    private static void selectAll(){
        String sql = "SELECT * FROM recipes INNER JOIN ingredients";

        try {
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("ingredientId") +  "\t" +
                        rs.getString("ingredientName") + "\t" +
                        rs.getString("recipeName") + "\t" +
                        rs.getString("ingredientPris"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Search RecipesName
    private static void searchRecipes(){
        // String sql = "SELECT * FROM recipes WHERE recipeName = ? ";
        String sql = "SELECT * FROM recipes INNER JOIN ingredients WHERE recipeName = ? ";
        //String sql = "SELECT * FROM recipes INNER JOIN ingredients ON recipes.recipeId WHERE recipeName = ? ";

        try {
            Connection conn = connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            System.out.println("Skriv in name på recipe: ");
            String inputName = scanner.nextLine();

            // set the value
            pstmt.setString(1,inputName);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("recipeId") +  "\t" +
                        rs.getString("recipeName") + "\t" +
                        rs.getString("recipePris"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // insert Recipes
    private static void insertRecipes(String name, int pris) {
        String sql = "INSERT INTO recipes(recipeName, recipePris) VALUES(?,?)";

        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, pris);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny recipe");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Insert Ingredients
    private static void insertIngredients(String name, int pris) {
        String sql = "INSERT INTO ingredients(ingredientName, ingredientPris) VALUES(?,?)";

        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, pris);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny ingredient");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update
    private static void update(String name, int pris, int id) {
        String sql = "UPDATE recipes INNER JOIN ingredients ON recipes.recipeId SET recipeName = ? , "
                + "recipePris = ? "
                + "WHERE recipeId = ?";
        /*String sql = "UPDATE recipes SET recipeName = ? , "
                + "recipePris = ? "
                + "WHERE recipeId = ?";*/

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name );
            pstmt.setInt(2, pris );
            pstmt.setInt(3, id );
            // update
            pstmt.executeUpdate();
            System.out.println("Du har uppdaterat vald recipe");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void update(){
        System.out.println("Skriv in name på recipe: ");
        String inputName = scanner.nextLine();
        System.out.println("Skriv in pris på recipe: ");
        int inputPris = scanner.nextInt();
        System.out.println("Skriv in id på recipe: ");
        int inputId = scanner.nextInt();
        update(inputName, inputPris, inputId);
        scanner.nextLine();
    }

    // Delete mot recipes-tabellen i databasen
    private static void delete(int id) {
        String sql = "DELETE FROM recipes WHERE recipeId = ?";
        //String sql = "DELETE FROM recipes INNER JOIN ingredients ON recipes.recipeId WHERE recipeId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("Du har tagit bort recipe");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        boolean quit = false;
        printActions();
        while(!quit) {
            System.out.println("\nVälj (7 för att visa val):");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0 -> {
                    System.out.println("\nStänger ner...");
                    quit = true;
                }
                case 1 -> insertRecipes();
                case 2 -> insertIngredients();
                case 3 -> selectAll();
                case 4 -> searchRecipes();
                case 5 -> update();
                case 6 -> delete();
                case 7 -> printActions();
            }
        }

    }


}