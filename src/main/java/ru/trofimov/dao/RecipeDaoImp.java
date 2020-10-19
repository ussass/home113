package ru.trofimov.dao;

import org.hibernate.Session;
import ru.trofimov.model.Recipe;
import ru.trofimov.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class RecipeDaoImp implements RecipeDao{
    @Override
    public Recipe findById(int id) {
        Recipe recipe;
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            recipe = session.get(Recipe.class, id);
        }
        return recipe;
    }

    @Override
    public void save(Recipe recipe) {

    }

    @Override
    public void update(Recipe recipe) {

    }

    @Override
    public void delete(Recipe recipe) {

    }

    @Override
    public List<Recipe> findAll(int category) {
        String sql = category == 0? "FROM Recipe" : "FROM Recipe where category = " + category;
        return (List<Recipe>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(sql).list();
    }
}
