package ru.trofimov.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.save(recipe);
            transaction.commit();
        }
    }

    @Override
    public void update(Recipe recipe) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(recipe);
            transaction.commit();
        }
    }

    @Override
    public void delete(Recipe recipe) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.delete(recipe);
            transaction.commit();
        }
    }

    @Override
    public List<Recipe> findAll(int category) {
        String sql = category == 0? "FROM Recipe ORDER BY id desc" : "FROM Recipe where category = " + category + " ORDER BY id desc";
        return (List<Recipe>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(sql).list();
    }
}
