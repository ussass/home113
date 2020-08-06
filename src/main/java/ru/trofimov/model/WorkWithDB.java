package ru.trofimov.model;

import ru.trofimov.entity.Recipe;

import java.lang.reflect.InvocationTargetException;
        import java.sql.*;
        import java.util.ArrayList;
        import java.util.List;

public class WorkWithDB {

    private static final String URL = "jdbc:mysql://localhost:3306/home113?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345678";

    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            System.out.println("Кака-то херь попалась: " + e);
        }
    }

    public static List<Recipe> findAll(int category) {
        String sql;
        if (category == 0) sql = "SELECT * FROM recipes;";
        else sql = "SELECT * FROM recipes  WHERE category = " + category + ";";
        List<Recipe> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {
//                Recipe recipe = new Recipe();
//                recipe.setId(resultSet.getInt("id"));
//                recipe.setRecipeName(resultSet.getString("recipeName"));
//                recipe.setCategory(resultSet.getInt("category"));
//                recipe.setPortion(resultSet.getInt("portion"));
//                recipe.setTime(resultSet.getInt("cookingTime"));
//                recipe.setIngredient(resultSet.getString("ingredients"));
//                recipe.setQuantity(resultSet.getString("quantity"));
//                recipe.setMeasure(resultSet.getString("measure"));
//                recipe.setSteps(resultSet.getString("steps"));
//                recipe.setPhotoPath(resultSet.getString("photos"));
//                list.add(recipe);
            }
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
        return list;
    }

    public static int save(Recipe recipe){
        int lastId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO recipes (recipeName, category, portion, cookingTime, ingredients, steps, photos) " + recipe.insertIntoDb());
//                    "VALUES ('" + recipe.getRecipeName() + "', " + recipe.getCategory() + ", " + recipe.getPortion() + ", " + recipe.getTime() + ", '" + recipe.getIngredient() + "', '" + recipe.getQuantity() + "', '" + recipe.getMeasure() + "', '" + recipe.getSteps() + "', '" + recipe.getPhotoPath() + "');");
            ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
            while (resultSet.next()){
                lastId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            System.out.println("Кака-то херь попалась");
        }
        return lastId;
    }
}

