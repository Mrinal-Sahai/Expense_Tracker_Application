package com.expensetracker.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import org.hibernate.Session;

import com.expensetracker.util.HibernateUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class VisualController {
	
	private static final Map<String, Integer> monthMap = Map.ofEntries(
	        new AbstractMap.SimpleEntry<>("January", 1), new AbstractMap.SimpleEntry<>("February", 2),
	        new AbstractMap.SimpleEntry<>("March", 3), new AbstractMap.SimpleEntry<>("April", 4),
	        new AbstractMap.SimpleEntry<>("May", 5), new AbstractMap.SimpleEntry<>("June", 6),
	        new AbstractMap.SimpleEntry<>("July", 7), new AbstractMap.SimpleEntry<>("August", 8),
	        new AbstractMap.SimpleEntry<>("September", 9), new AbstractMap.SimpleEntry<>("October", 10),
	        new AbstractMap.SimpleEntry<>("November", 11), new AbstractMap.SimpleEntry<>("December", 12)
	    );
	
	
    @FXML private ChoiceBox<String> monthChoiceBox;
    @FXML private ChoiceBox<String> yearChoiceBox;
    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis monthAxis;
    @FXML private NumberAxis expenseAxis;

    private String username;

    public void setUsername(String username) {
        this.username = username;
        initializeChoiceBoxes();
    }

    @FXML
    public void initialize() {
        monthAxis.setLabel("Months");
        expenseAxis.setLabel("Total Expense");
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.setTickLabelRotation(30);
        xAxis.setTickLabelGap(20);
        barChart.setPadding(new Insets(20, 50, 20, 50));
       
    }

    private void initializeChoiceBoxes() {
        monthChoiceBox.setItems(FXCollections.observableArrayList(
            "All Over", "January", "February", "March", "April", "May", "June", 
            "July", "August", "September", "October", "November", "December"
        ));

        ObservableList<String> years = FXCollections.observableArrayList();
        for (int year = LocalDate.now().getYear(); year >= 2024; year--) {
            years.add(String.valueOf(year));
        }
        yearChoiceBox.setItems(years);
        yearChoiceBox.setValue(LocalDate.now().getYear()+"");
        monthChoiceBox.setValue(LocalDate.now().getMonth().toString());
        loadPieChart();
        loadBarChart();
    }



    @FXML
    private void loadPieChart() {
    	
    	 String month = monthChoiceBox.getValue();
         String year = yearChoiceBox.getValue();
         
        pieChart.getData().clear();
        List<Object[]> results;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
       
            if(month.equalsIgnoreCase("All Over"))
            {
            	 results = session.createNativeQuery(
                        "SELECT category, SUM(amount) FROM " + username +
                        " WHERE YEAR(date) = "+year+" GROUP BY category"
                    ).list();	
            }
            else {
		 results = session.createNativeQuery(
                "SELECT category, SUM(amount) FROM " + username +
                " WHERE MONTH(date) =  "+monthMap.getOrDefault(month, LocalDate.now().getMonthValue()) +
                " AND YEAR(date) = "+year+" GROUP BY category"
            ).list();
            }
            for (Object[] row : results) {
                double amount=((BigDecimal)row[1]).doubleValue();
                pieChart.getData().add(new PieChart.Data((String) row[0], amount));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @FXML
	private void loadBarChart() {
		
		 String year = yearChoiceBox.getValue();
        barChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Expenses");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	List<Object[]> results = session.createNativeQuery(
        		    "SELECT MONTHNAME(date) AS month, SUM(amount) AS total FROM " + username +
        		    " WHERE YEAR(date) = :year GROUP BY month ORDER BY STR_TO_DATE(CONCAT('01-', month, '-2020'), '%d-%M-%Y')"
        		).setParameter("year", Integer.parseInt(year))
        		 .list();
            for (Object[] row : results) {
            	  double amount=((BigDecimal)row[1]).doubleValue();
                series.getData().add(new XYChart.Data<>((String) row[0], (Double) amount));
            }

            barChart.getData().add(series);
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
}
