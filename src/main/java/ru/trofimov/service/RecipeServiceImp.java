package ru.trofimov.service;

import ru.trofimov.dao.RecipeDao;
import ru.trofimov.dao.RecipeDaoImp;
import ru.trofimov.model.Recipe;

import java.util.List;

public class RecipeServiceImp implements RecipeService {

    private RecipeDao recipeDao = new RecipeDaoImp();
    @Override
    public Recipe findById(int id) {
        return recipeDao.findById(id);
    }

    @Override
    public void save(Recipe recipe) {
        recipeDao.save(recipe);
    }

    @Override
    public void update(Recipe recipe) {
        recipeDao.update(recipe);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeDao.delete(recipe);
    }

    @Override
    public List<Recipe> findAll(int category) {
        return recipeDao.findAll(category);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeDao.findAll(0);
    }
}
