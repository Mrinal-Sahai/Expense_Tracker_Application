package com.expensetracker.model;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;

@Entity
public class Transaction {
	
	private int id;
    private Date date;
    private String category;
    private BigDecimal amount;
    private String description;

    public Transaction(int id,Date date, String category, BigDecimal amount, String description) {
    	this.id=id;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }
    
    public int getId() { return id; }
    public Date getDate() { return date; }
    public String getCategory() { return category; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
}
