package ru.trofimov.dao;

import ru.trofimov.model.Recipe;

import java.util.List;

public interface RecipeDao {

    Recipe findById(int id);

    void save(Recipe recipe);

    void update(Recipe recipe);

    void delete(Recipe recipe);

    List<Recipe> findAll(int category);
}
