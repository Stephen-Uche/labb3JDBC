package se.iths.database.stephen.osedumme.lab3;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);


    // SQLite connection string
    private static Connection connect() {

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

                "1  - Lägga till alla\n" +
                "2  - Visa alla \n" +
                "3  - Sök efter alla\n" +
                "4  - Uppdatera en recipe\n" +
                "5  - Ta bort alla\n" +
                "6  - Visa en lista över alla val.");
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

    private static void insertRecipes(String name, int pris) {
        //String sql = "INSERT INTO recipes(recipeName, recipePris) VALUES(?,?)";
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

    public static void insertAll() {
        System.out.println("\n Välj:\n");
        System.out.println(
                "1 - Insert Recipes\n" +
                        "2 - Insert Ingredients");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> insertRecipes();
            case 2 -> insertIngredients();
        }

    }




        private static void selectRecipes() {

        String sql = "SELECT * FROM recipes;";

        try {
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getInt("recipeId") +  "\t" +
                        rs.getString("recipeName") + "\t" +
                        rs.getInt("recipePris")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void selectIngredients(){

        String sql = "SELECT * FROM ingredients ";

        try {
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getInt("ingredientId") + "\t" +
                        rs.getString("ingredientName") + "\t" +
                        rs.getInt("ingredientPris")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectAll() {
        System.out.println("\n Välj:\n");
        System.out.println(
                "1 - Read Recipes\n" +
                "2 - Read Ingredients");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> selectRecipes();
            case 2 -> selectIngredients();
        }

    }

    //Search RecipesName
    private static void searchRecipes(){

        String sql = "SELECT * FROM recipes WHERE recipeName = ? ";


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
    private static void searchIngredients(){

        String sql = "SELECT * FROM ingredients WHERE ingredientName = ? ";

        try {
            Connection conn = connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            System.out.println("Skriv in name på ingredient: ");
            String inputName = scanner.nextLine();

            // set the value
            pstmt.setString(1,inputName);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("ingredientId") +  "\t" +
                        rs.getString("ingredientName") + "\t" +
                        rs.getString("ingredientPris"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void searchAll() {
        System.out.println("\n Välj:\n");
        System.out.println(
                "1 - Search Recipes\n" +
                        "2 - Search Ingredients");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> searchRecipes();
            case 2 -> searchIngredients();
        }

    }




    // Update Recipe
    private static void updateRecipes(String name, int pris, int id) {
        String sql = "UPDATE recipes SET recipeName = ? , "
                + "recipePris = ? "
                + "WHERE recipeId = ?";

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

        // Uppdate Ingredient
    } private static void updateIngredients(String name, int pris, int id) {
        String sql = "UPDATE ingredients SET ingredientName = ? , "
                + "ingredientPris = ? "
                + "WHERE ingredientId = ?";

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

    private static void uppdateRecipes(){
        System.out.println("Skriv in name på recipe: ");
        String inputName = scanner.nextLine();
        System.out.println("Skriv in pris på recipe: ");
        int inputPris = scanner.nextInt();
        System.out.println("Skriv in id på recipe: ");
        int inputId = scanner.nextInt();
        updateRecipes(inputName, inputPris, inputId);
        scanner.nextLine();
    }
    private static void updateIngredients(){
        System.out.println("Skriv in name på recipe: ");
        String inputName = scanner.nextLine();
        System.out.println("Skriv in pris på recipe: ");
        int inputPris = scanner.nextInt();
        System.out.println("Skriv in id på recipe: ");
        int inputId = scanner.nextInt();
        updateIngredients(inputName, inputPris, inputId);
        scanner.nextLine();
    }

    public static void uppdateAll() {
        System.out.println("\n Välj:\n");
        System.out.println(
                "1 - Uppdate Recipes\n" +
                        "2 - Uppdate Ingredients");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> uppdateRecipes();
            case 2 -> updateIngredients();
        }

    }
    private static void deleteFromIngredients(){
        System.out.println("Skriv in id:t på ingredient som ska tas bort: ");
        int inputId = scanner.nextInt();
        deleteIngredients(inputId);
        scanner.nextLine();
    }
    private static void deleteIngredients(int id) {

        String sql = "DELETE FROM ingredients WHERE ingredientId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("Du har tagit bort ingredient");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void deleteFromRecipes(){
        System.out.println("Skriv in id:t på recipe som ska tas bort: ");
        int inputId = scanner.nextInt();
        deleteRecipes(inputId);
        scanner.nextLine();
    }
    private static void deleteRecipes(int id) {

        String sql = "DELETE FROM recipes WHERE recipeId = ?";

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



    public static void deleteAll() {
        System.out.println("\n Välj:\n");
        System.out.println(
                "1 - Delete Recipes\n" +
                        "2 - Delete Ingredients");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> deleteFromRecipes();
            case 2 -> deleteFromIngredients();
        }
    }




    public static void main(String[] args) {

        boolean quit = false;
        printActions();
        while(!quit) {
            System.out.println("\nVälj (6 för att visa val):");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0 -> {
                    System.out.println("\nStänger ner...");
                    quit = true;
                }
                case 1 -> insertAll();
                case 2 -> selectAll();
                case 3 -> searchAll();
                case 4 -> uppdateAll();
                case 5 -> deleteAll();
                case 6 -> printActions();
            }
        }

    }


}