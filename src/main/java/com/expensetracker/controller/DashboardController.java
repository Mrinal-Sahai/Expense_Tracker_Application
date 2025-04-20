package com.expensetracker.controller;



import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.expensetracker.dao.ExpenseDao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DashboardController {

    private String username;
    private static ExpenseDao dao = new ExpenseDao();
    private List<String> allCategories=Arrays.asList("Food","Travel","Rent","Education","Health","Others");
    
    @FXML
    private Label message;
    @FXML
    private Label info;
    @FXML
    private ChoiceBox<String> category;
    @FXML
    private TextField amount;
    @FXML
    private TextField desc;
    @FXML
    private DatePicker date;
  
    public void setReceivedData(String s) {
    	message.setText("Hello "+s.toUpperCase());
    	username=s;
    }
    
    @FXML
	public void initialize()
	{
		category.getItems().addAll(allCategories);
		category.setValue("Enter Category");
	}
    
    @FXML
    private void addExpense() {
        System.out.println("Add Expense button pressed");

        String categoryy = category.getValue();
        LocalDate datee = date.getValue();
        String amountText = amount.getText().trim();
        String description = desc.getText().trim();
        
        if (categoryy == null || categoryy.isEmpty() || categoryy.equalsIgnoreCase("Enter Category")) {
            info.setText("Error: Please select a category.");
            return;
        }

        if (datee == null) {
            info.setText("Error: Please select a date.");
            return;
        }

        
        double amountt;
        try {
            amountt = Double.parseDouble(amountText);
            if (amountt <= 0) {
                info.setText("Error: Amount must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            info.setText("Error: Invalid amount entered.");
            return;
        }

        if (description.isEmpty()) {
            info.setText("Error: Description cannot be empty.");
            return;
        }

        boolean success = dao.submitTransaction(username, datee, categoryy, amountt, description);
        if (success) {
            info.setText("Transaction saved! for " + description);
        } else {
            info.setText("Failed to save transaction.");
        }
    }

    
	 @FXML
	    private void viewAll() {

		 try {
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transactions.fxml"));
			    Parent root = loader.load();

			    TransactionController controller = loader.getController();
			    controller.setUsername(username); 

			    Stage stage = (Stage) message.getScene().getWindow(); 
			    stage.setScene(new Scene(root));
			    stage.show();
			} catch (IOException e) {
			    e.printStackTrace(); 
			} catch (Exception e) {
			    e.printStackTrace();
			}

	        
	    }
	 @FXML
	    private void viewVisuals() {

		 try {
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Visual.fxml"));
			    Parent root = loader.load();

			    VisualController controller = loader.getController();
			    controller.setUsername(username); 

			    Stage stage = (Stage) message.getScene().getWindow(); 
			    stage.setScene(new Scene(root));
			    stage.show();
			} catch (IOException e) {
			    e.printStackTrace(); 
			} catch (Exception e) {
			    e.printStackTrace();
			}

	        
	    }
	 
	 @FXML
		public void goToWelcome(ActionEvent event) {
		    try {
		       
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
			    Parent root = loader.load();
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		        stage.setScene(new Scene(root));
		        stage.show();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
    
}

