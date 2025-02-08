package com.expensetracker.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.expensetracker.dao.ExpenseDao;
import com.expensetracker.util.HibernateUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WelcomeController {
	
	private static ExpenseDao dao = new ExpenseDao();
	private static List<String> allUserNames = dao.getAllTableNames();


	@FXML
	private ChoiceBox<String> userChoice;
	@FXML
	private TextField userName;
	@FXML
	private Label warning;
	
	@FXML
	public void initialize()
	{
		userChoice.getItems().addAll(allUserNames);
		userChoice.setValue("Select the User");
	}
	@FXML
    private void addUser() {
		System.out.println("List :" +allUserNames);
		
        String username = userName.getText().trim().toLowerCase();

        if (username.isEmpty()) {
        	 warning.setText("Enter a username.");
            return;
        }

        if (username.contains(" ")) {
        	 warning.setText("Username should not contain spaces.");
            return;
        }

        if (username.length() < 3 || username.length() > 15) {
        	 warning.setText("Username must be 3-15 characters long.");
            return;
        }

        if (!username.matches("[a-zA-Z0-9_]+")) {
        	 warning.setText("Username can only contain letters, numbers, and underscores.");
            return;
        }

      
        if (allUserNames.contains(username)) {
           warning.setText("Username already exists.");
            return;
        }
        
        if (dao.createUserTable(username)) {
            allUserNames.add(username); 
            warning.setText("User '" + username + "' added successfully.");
            userChoice.getItems().add(username);
        } else {
            warning.setText("Error creating user. Try again.");
        }
        
        userName.clear();
    }
	
	@FXML
	private void removeUser () {
	    String user = userChoice.getSelectionModel().getSelectedItem();
	    
	    if (user != null && !(user.equalsIgnoreCase("Select the User"))) {
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Are you sure you want to delete this user ?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("UserName : "+user);
            alert.showAndWait();  
            if (alert.getResult() == ButtonType.YES) {
	    	if (dao.removeUserTable(user)) {
	              allUserNames.remove(user); 
	              userChoice.getItems().remove(user);
	              warning.setText("User '" + user + "' removed successfully.");
	              userChoice.setValue("Select the User");
	              
	          } else {
	              warning.setText("Error removing  user. Try again.");
	          }
            }
	    }
	    else {
	        warning.setText("No user Selected");
	    }
	}
	
	 @FXML
	    private void enter() {
		 String user =userChoice.getValue();
          System.out.println("Enter button pressed");
		 if (user == null || (user.equals("Select the User"))) {
	            warning.setText("No option selected!");
	            return;
	        }

		 try {
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
			    Parent root = loader.load();

			    DashboardController controller = loader.getController();
			    controller.setReceivedData(user); 

			    Stage stage = (Stage) userChoice.getScene().getWindow(); 
			    stage.setScene(new Scene(root));
			    stage.show();
			} catch (IOException e) {
			    e.printStackTrace(); 
			} catch (Exception e) {
			    e.printStackTrace();
			}

	        
	    }
	}
	 
	    
