package com.expensetracker.dao;

import com.expensetracker.model.Transaction;
import com.expensetracker.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ExpenseDao {

    public List<String> getAllTableNames() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNativeQuery("SHOW TABLES", String.class).list();
        }
    }

    @SuppressWarnings("deprecation")
	public boolean createUserTable(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String createTable = "CREATE TABLE " + username +
                    " (id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "date DATE, " +
                    "category VARCHAR(50), " +
                    "amount DECIMAL(10,2), " +
                    "description TEXT)";
            session.createNativeQuery(createTable).executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @SuppressWarnings("deprecation")
	public boolean removeUserTable(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String removeTable = "DROP TABLE "+ username;
            session.createNativeQuery(removeTable).executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @SuppressWarnings("deprecation")
	public boolean submitTransaction(String username, LocalDate date, String category, double amount, String description) {
    
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Date sqlDate = Date.valueOf(date);

          
            String insert = "INSERT INTO " + username+ " (date, category, amount, description) " +
                               "VALUES (:date, :category, :amount, :description)";

            session.createNativeQuery(insert)
                    .setParameter("date", sqlDate)  
                    .setParameter("category", category)
                    .setParameter("amount", amount)
                    .setParameter("description", description)
                    .executeUpdate();

            session.getTransaction().commit();
            System.out.println("Transaction added successfully for " + username);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean deleteTransaction(int id, String username) {
    	 try (Session session = HibernateUtil.getSessionFactory().openSession()) {
             session.beginTransaction();

             String insert = "DELETE FROM "+username+" WHERE id =" +id;
             session.createNativeQuery(insert).executeUpdate();
             session.getTransaction().commit();
             System.out.println("Transaction removed successfully for id = " + id);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
    }
}
