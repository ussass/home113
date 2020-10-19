package ru.trofimov.service;

import ru.trofimov.model.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe findById(int id);

    void save(Recipe recipe);

    void update(Recipe recipe);

    void delete(Recipe recipe);

    List<Recipe> findAll(int category);

    List<Recipe> findAll();
}
