/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanager.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import storemanager.models.Article;
import storemanager.models.Item;
import storemanager.models.Recipe;

/**
 *
 * @author Haris
 */
public class RecipeView {
    public TableView tableRecipes,tableRecipeItems;
    public TableColumn dateRecipe,RecipeID,numOfItems,nameItems,priceItems,itemsQuantity;
    public SplitPane sp;
    public StackPane stackP1,stackP2;
    public VBox vbRecipes,vbItems;
    public HBox hb1,hb2,hb3;
    public TextField searchArticle;
    public Label total;
    public Button dayReport,monthReport,yearReport;
    public DatePicker recipeSearchDate,dayReportDatePicker;
    public ChoiceBox monthsPick,yearPick;
    public List<String> months = new ArrayList<String>();
    
    public RecipeView(){
        this.fillMonthsArray();
        // creating NON editable Tables
        tableRecipes = new TableView();
        tableRecipes.setEditable(false);
        
        tableRecipeItems = new TableView();
        tableRecipeItems.setEditable(false);
//         Creating columns for Recipe Table
        dateRecipe = new TableColumn("Recipe Made On");
        dateRecipe.prefWidthProperty().bind(tableRecipes.widthProperty().multiply(0.4));
        dateRecipe.setCellValueFactory(
                    new PropertyValueFactory<Recipe, String>("date"));
        dateRecipe.setCellFactory(TextFieldTableCell.forTableColumn());
        
        RecipeID = new TableColumn("Recipe ID");
        RecipeID.prefWidthProperty().bind(tableRecipes.widthProperty().multiply(0.3));
        RecipeID.setCellValueFactory(
                    new PropertyValueFactory<Recipe, String>("ID"));
        RecipeID.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        numOfItems = new TableColumn("Number of Items");
        numOfItems.prefWidthProperty().bind(tableRecipes.widthProperty().multiply(0.3));
        numOfItems.setCellValueFactory(new PropertyValueFactory<Recipe, String>("numberOfItems"));
        numOfItems.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        nameItems = new TableColumn("Name");
        nameItems.prefWidthProperty().bind(tableRecipeItems.widthProperty().multiply(0.3));
        nameItems.setCellValueFactory(
                    new PropertyValueFactory<Item, String>("Article"));
        nameItems.setCellFactory(TextFieldTableCell.forTableColumn());
        
        priceItems = new TableColumn("Price");
        priceItems.prefWidthProperty().bind(tableRecipeItems.widthProperty().multiply(0.3));
        priceItems.setCellValueFactory(
                    new PropertyValueFactory<Item, Float>("Price"));
        priceItems.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        
        itemsQuantity = new TableColumn("Quantity");
        itemsQuantity.prefWidthProperty().bind(tableRecipeItems.widthProperty().multiply(0.4));
        itemsQuantity.setCellValueFactory(
                    new PropertyValueFactory<Item, Integer>("Quantity"));
        itemsQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        recipeSearchDate = new DatePicker();

        //storing columns into Table
        tableRecipes.getColumns().addAll(dateRecipe,RecipeID,numOfItems);
        tableRecipeItems.getColumns().addAll(nameItems,itemsQuantity,priceItems);
        
        vbRecipes = new VBox();
        vbRecipes.setAlignment(Pos.TOP_LEFT);
        vbRecipes.setPrefSize(300, 100);
        vbRecipes.setMinSize(300, 100);
        vbRecipes.setMaxWidth(300);
        vbRecipes.setSpacing(10);
        vbRecipes.getChildren().addAll(tableRecipes,recipeSearchDate);
        
        stackP1 = new StackPane();
        stackP1.setAlignment(Pos.TOP_LEFT);
        stackP1.getChildren().add(vbRecipes);
        
        // stack1 finsihed
        dayReportDatePicker = new DatePicker();
        dayReport = new Button("Print Day Report");
        
        hb1 = new HBox();
        hb1.setSpacing(10);
        hb1.setAlignment(Pos.BOTTOM_CENTER);
        hb1.getChildren().addAll(dayReportDatePicker, dayReport );
        
        monthsPick = new ChoiceBox(FXCollections.observableArrayList(months));
        monthReport = new Button("Print Month Report");
        
        hb2 = new HBox();
        hb2.setSpacing(10);
        hb2.setAlignment(Pos.BOTTOM_CENTER);
        hb2.getChildren().addAll(monthsPick, monthReport );
        
        yearPick = new ChoiceBox(FXCollections.observableArrayList("2016","2017"));
        yearReport = new Button("Print Year Report");
        
        hb3 = new HBox();
        hb3.setSpacing(10);
        hb3.setAlignment(Pos.BOTTOM_CENTER);
        hb3.getChildren().addAll(yearPick, yearReport);
        

        
        vbItems = new VBox();
        vbItems.setAlignment(Pos.TOP_LEFT);
        vbItems.setPrefSize(300, 100);
        vbItems.setMinSize(300, 100);
        vbItems.setMaxSize(300, 300);
        vbItems.setSpacing(10);
        vbItems.getChildren().addAll(tableRecipeItems,hb1,hb2,hb3);
        
        stackP2 = new StackPane();
        stackP2.setAlignment(Pos.TOP_LEFT);
        stackP2.getChildren().add(vbItems);
        
        sp = new SplitPane();
        sp.getItems().addAll(stackP1,stackP2);
        sp.setDividerPositions(0.1f, 0.6f, 0.9f);
        
        System.out.println("This is done ");
    }
    public SplitPane getSplitP(){
        return this.sp;
    }
    public void fillTableRecipes(ObservableList<Recipe> recipes){
        System.out.println("Comes into this method ");
        this.tableRecipes.setItems(recipes);
        
    }
    public void fillTableItems(ObservableList<Item> items){
        this.tableRecipeItems.setItems(items);
    }
    public void fillMonthsArray(){
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("Avgust");
        months.add("September");
        months.add("Octomber");
        months.add("November");
        months.add("December");
    }
}
