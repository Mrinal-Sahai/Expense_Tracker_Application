package com.expensetracker.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;

import com.expensetracker.dao.ExpenseDao;
import com.expensetracker.model.Transaction;
import com.expensetracker.util.HibernateUtil;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;



public class TransactionController {
	
    @FXML 
    private TableView<Transaction> transactionTable;
    @FXML 
    private TableColumn<Transaction, Integer> id;
    @FXML 
    private TableColumn<Transaction, String> date;
    @FXML 
    private TableColumn<Transaction, String> category;
    @FXML 
    private TableColumn<Transaction, BigDecimal> amount;
    @FXML 
    private TableColumn<Transaction, String> description;
    @FXML
    private Label info;

    private String username; 
    private static ExpenseDao dao = new ExpenseDao();

    public void setUsername(String username) {
        this.username = username;
        loadTransactions();
    }

    @FXML
    public void initialize() {
    	id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadTransactions() {
  
        ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("deprecation")
			List<Object[]> results = session.createNativeQuery("SELECT id,date, category, amount, description FROM " + username+" ORDER BY id DESC").list();
   
            for (Object[] row : results) {
                transactionList.add(new Transaction((Integer) row[0],(Date) row[1], (String) row[2], (BigDecimal) row[3], (String) row[4]));
            }

            transactionTable.setItems(transactionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
	public void goToDashboard(ActionEvent event) {
	    try {
	       
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
		    Parent root = loader.load();
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        DashboardController controller = loader.getController();
		    controller.setReceivedData(username); 

	        stage.setScene(new Scene(root));
	        stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
    
    @FXML
    private void removeSelectedTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

        if (selectedTransaction != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Are you sure you want to delete this transaction?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
             
            	 boolean success = dao.deleteTransaction(selectedTransaction.getId(), username);
                 if (success) {
                	 ObservableList<Transaction> transactions = transactionTable.getItems();
                     transactions.remove(selectedTransaction);
                     info.setText("Transaction removed succesfully!");
                 } else {
                    info.setText("Failed to remove transaction.");
                 }          
            }
        } else {
            
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a transaction to delete.");
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.show();
        }
    }

}
