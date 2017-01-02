/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanager.models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Haris
 */
public class Article {
    public SimpleStringProperty name;
    public SimpleFloatProperty price;
    
    public Article(String articleName,float articlePrice){
        this.name = new SimpleStringProperty(articleName);
        this.price = new SimpleFloatProperty(articlePrice);
    }
    public void setName(String articleName){
        this.name.set(articleName);
    }
    public String getName(){
        return this.name.get();
    }
    public void setPrice(float articlePrice){
        this.price.set(articlePrice);
    }
    public float getPrice(){
        return this.price.get();
    }
}
