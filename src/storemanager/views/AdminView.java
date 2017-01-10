/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanager.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import storemanager.models.Article;

/**
 *
 * @author Haris
 */
public class AdminView {
    public Group root;
    public TabPane tabPane;
    public BorderPane mainPane;
    public Tab lagerTab,cashierTab,recipeTab;
    public Scene scene;
    public TableView table;
    public Button addButton,deleteBtn;
    public TextField addArticle,addPrice,searchArticles,addQuantity;
    public HBox hb,wraper;
    public VBox vb, sideB;
    public StackPane  lagerStackPt1,lagerStackPt2;
    public SplitPane  sp;
    public TableColumn priceClmn ,nameClmn,quantityClmn ;
    
    
    
    public AdminView(){
        root = new Group();
        //new Scene 
        scene = new Scene(root,600,600);
        //creating the pane for the tabs
        tabPane = new TabPane();
        mainPane = new BorderPane();
        //Create Tabs 
        lagerTab = new Tab();
        lagerTab.setText("Lager");
        lagerTab.setClosable(false);
        //add Data to tab
        table = new TableView();
        table.setEditable(true);
        // creating the button for adding new elements
        addButton = new Button("Add");
        addButton.setDisable(true);
        // creating the columns and data that they accept
        nameClmn = new TableColumn("Artical Name");
        nameClmn.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        nameClmn.setCellValueFactory(
                    new PropertyValueFactory<Article, String>("Name"));
        nameClmn.setCellFactory(TextFieldTableCell.forTableColumn());
      
        priceClmn = new TableColumn("Artical Price");
        priceClmn.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        priceClmn.setCellValueFactory(
                    new PropertyValueFactory<Article, Float>("Price"));
        priceClmn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        //column for quantity in admin view
        quantityClmn = new TableColumn("Artical Quantity");
        quantityClmn.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        quantityClmn.setCellValueFactory(
                    new PropertyValueFactory<Article, Integer>("Quantity"));
        quantityClmn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        //creating text fields and their functionalities denying to enter empty values
        addArticle = new TextField();
        addArticle.setPromptText("Add Article");
        addArticle.setPrefWidth(170);
        
        addPrice = new TextField();
        addPrice.setPromptText("Add Price");
        addPrice.setPrefWidth(170);
        
        addQuantity = new TextField();
        addQuantity.setPromptText("Add Price");
        addQuantity.setPrefWidth(170);
        //functionalty to enable text field when they are filled out 
        addPrice.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    addPrice.setText(newValue.replaceAll("[^\\d]", ""));
                    System.out.println(addPrice.getText().toCharArray().length);   
                }
                addButton.setDisable(!((addPrice.getText().toCharArray().length > 0) && (addArticle.getText().toCharArray().length > 0)&& (addQuantity.getText().toCharArray().length > 0)) );
            }
        });
        addQuantity.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    addPrice.setText(newValue.replaceAll("[^\\d]", ""));
                    System.out.println(addPrice.getText().toCharArray().length);   
                }
                addButton.setDisable(!((addPrice.getText().toCharArray().length > 0) && (addArticle.getText().toCharArray().length > 0)&& (addQuantity.getText().toCharArray().length > 0)) );
            }
        });
        addArticle.textProperty().addListener((observable,oldValue,newValue)->{
            addButton.setDisable(!((addPrice.getText().toCharArray().length > 0) && (addArticle.getText().toCharArray().length > 0) && (addQuantity.getText().toCharArray().length > 0)) );
        });
        

        table.getColumns().addAll(nameClmn, priceClmn,quantityClmn);
    
        hb = new HBox();
        hb.setSpacing(10);
        hb.setAlignment(Pos.BOTTOM_LEFT);
        hb.getChildren().addAll(addArticle, addPrice, addButton,addQuantity);

        //creating to Vertical Boxes to separate content and the 
        //left
        searchArticles = new TextField();
        searchArticles.setPromptText("Search Articles");
        searchArticles.setPrefWidth(400);
        vb = new VBox();
        vb.setAlignment(Pos.TOP_LEFT);
        vb.setPrefSize(400, 200);
        vb.setMinSize(400, 200);
        vb.setMaxSize(400, 500);
        vb.setSpacing(10);
        vb.getChildren().addAll(table,searchArticles,hb);
        //right side
        sideB = new VBox();
        sideB.setAlignment(Pos.TOP_RIGHT);
        sideB.setPrefSize(100, 200);
        sideB.setMinSize(100, 200);
        sideB.setMaxSize(100, 500);
        deleteBtn = new Button("Delete");
        deleteBtn.setDisable(true);
        sideB.getChildren().addAll(deleteBtn);
        //wrapper for  sideB which is vertical box for button
        wraper = new HBox();
        wraper.getChildren().addAll(sideB);
        // stack pane for right wrapping it into node
        lagerStackPt2 = new StackPane();
        lagerStackPt2.setAlignment(Pos.TOP_RIGHT);
        lagerStackPt2.getChildren().add(wraper);
        // stack pane for left wrapping it into node
        lagerStackPt1 = new StackPane();
        lagerStackPt1.setAlignment(Pos.TOP_LEFT);
        lagerStackPt1.getChildren().add(vb);
        //split pane like a node for splited TAB
        sp = new SplitPane();
        sp.getItems().addAll(lagerStackPt1,lagerStackPt2);
        sp.setDividerPositions(0.3f, 0.6f, 0.9f);
        //adding them into TAB
        lagerTab.setContent(sp);
                
        cashierTab = new Tab();
        cashierTab.setText("Cashier");
        cashierTab.setClosable(false);
        
        recipeTab = new Tab();
        recipeTab.setText("Reports");
        recipeTab.setClosable(false);
        //adding tabs to tabpane
        tabPane.getTabs().addAll(lagerTab,cashierTab,recipeTab);
        
        //assigning tab to mainPane which is then assigned to group named ROOT
        mainPane.setCenter(tabPane);
        mainPane.prefHeightProperty().bind(scene.heightProperty());
        mainPane.prefWidthProperty().bind(scene.heightProperty());
        
        root.getChildren().add(mainPane);
  
    }
    public Scene getScene(){
        return this.scene;
    }

    public void fillTable(ObservableList<Article> articles){
        table.setItems(articles);
    }
}
