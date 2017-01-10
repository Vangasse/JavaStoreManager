/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanager;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import storemanager.models.Article;
import storemanager.models.DataBase;
import storemanager.models.Item;
import storemanager.models.Recipe;
import storemanager.views.AdminView;
import storemanager.views.Cashier;
import storemanager.views.RecipeView;
import storemanager.views.WelcomeFrame;

/**
 *
 * @author Haris
 */
public class StoreManager extends Application {
    
    DataBase db = new DataBase();
    public ObservableList<Article> articles = db.GetData();
    public ObservableList<Article> dataContainer = FXCollections.observableArrayList(articles);
    public ObservableList<Item> recipe = FXCollections.observableArrayList();
    Article articleSwitch;
    Item toRecipe;
    int itemID = db.getLastRecipeId();
    @Override
    public void start(Stage primaryStage) {
        //Welcoming frame 
        //WelcomeFrame frame = new WelcomeFrame();
        //Admin Frame
         AdminView admin = new AdminView();
        //Cashier Tab
        Cashier cashier = new Cashier();
        //Recipe manager Tab
        RecipeView recipeView = new RecipeView();
       //Continue Button continue just to Cashier 
//        frame.btnCont.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Bello Cashier!");
//            }
//        });
//        // Log In button for Accesing the admin section
//        frame.btnLog.setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent e){
//                if(frame.pass.getText().intern() == "admin"){
//                    frame.warnText.setText("");
//
//                }
//                else{
//                    frame.warnText.setText("The Password is Incorrect!!!");
//                }
//
//            }
//        });
        admin.cashierTab.setContent(cashier.sp);
        admin.recipeTab.setContent(recipeView.sp);
        cashier.fillTable(articles);
        cashier.fillTableItems(recipe);
        recipeView.fillTableRecipes(db.getRecipes());
        primaryStage.setTitle("Admin View");
//        primaryStage.setScene(admin.getScene());
        admin.fillTable(articles);
        System.out.println("Bello Admin !!");
        // btn adding the element that is entered into Input Boxes
        admin.addButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent e){
                Article article =new Article(admin.addArticle.getText(),Float.valueOf(admin.addPrice.getText()),Integer.parseInt(admin.addQuantity.getText()));
                articles.add(article);
                dataContainer.add(article);
                db.setArticle(article);
                admin.addArticle.clear();
                admin.addPrice.clear();
            }
        });
        // Delete btn from admin tab deleting the transition element
        admin.deleteBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Article article = (Article)admin.table.getSelectionModel().getSelectedItem();
                articles.remove(article);
                dataContainer.remove(article);
                db.removeFromDb(article);
            }
        });
        // Assigning object to transition object Article Switch that is uset for Deleting assigned to DeleteBtn in Admin Tab
        admin.table.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            admin.deleteBtn.setDisable(!(newSelection != null)); 
            if(newSelection != null){
                Article ar = (Article) newSelection;
                articleSwitch = new Article(ar.getName(),ar.getPrice(),ar.getQuantity());
            }
        });
        // Commiting the edited Name Column in Article Table in Admin Tab
        admin.nameClmn.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Article, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Article, String> t) {
                    System.out.println(t.getNewValue().length());
                    if( t.getNewValue().length() > 0){
                        Article ar = (Article) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        ((Article) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setName(t.getNewValue());
                        db.updateArticle(articleSwitch,ar);
                        System.out.println("String isn't empty" );
                    }
                    else{
                        Article ar = (Article) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        ((Article) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setName(t.getOldValue());
                        articles.set(articles.indexOf(ar), new Article(t.getOldValue(),ar.getPrice(),ar.getQuantity()));
                        System.out.println("String is empty");
                    }
                    
                }
            }
        );
        // Commiting the edited Price Column in Article Table in Admin Tab
        admin.priceClmn.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Article, Float>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Article, Float> t) {
                    System.out.println(t.getNewValue());
                    if(t.getNewValue() != null){
                        Article ar = (Article) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        ((Article) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setPrice(t.getNewValue());
                        db.updateArticle(articleSwitch,ar);
                    }
                    else{
                        Article ar = (Article) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        ((Article) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setPrice(t.getOldValue());
                        articles.set(articles.indexOf(ar), new Article(ar.getName(),t.getOldValue(),ar.getQuantity()));
                        System.out.println("String is empty");
                    }
                }
            });
        admin.quantityClmn.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Article, Integer>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Article, Integer> t) {
                    System.out.println(t.getNewValue());
                    if(t.getNewValue() != null){
                        Article ar = (Article) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        ((Article) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setQuantity(t.getNewValue());
                        db.updateArticle(articleSwitch,ar);
                    }
                    else{
                        Article ar = (Article) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        ((Article) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setQuantity(t.getOldValue());
                        articles.set(articles.indexOf(ar), new Article(ar.getName(),ar.getPrice(),t.getOldValue()));
                        System.out.println("String is empty");
                    }
                }
            });
//         Search bar for admin tab 
        admin.searchArticles.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if(newValue.toCharArray().length > 0){
                    articles.clear();
                    for(Article a : dataContainer){
                        if(a.getName().toLowerCase().contains(newValue.toLowerCase())){
                            articles.add(a);
                        }   
                    }
                }
                else if(newValue.toCharArray().length == 0){
                    articles.addAll(dataContainer);
                }
            }
        });
        //When tabs are changed to re set the value of the Article Table because it is used in both Tabs
        admin.tabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                    if(t.getText().equalsIgnoreCase("Cashier")){
                        clearItemTable();
                    }
//                    if(t1.getText().equalsIgnoreCase("Lager")){
//                        
//                        articles.addAll(dataContainer);
//                    }
                    
                }
            }
        );
        //selection of Transition object "toRecipe" on One click 
        cashier.tableArticles.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            admin.deleteBtn.setDisable(!(newSelection != null)); 
            if(newSelection != null){
                Article ar = (Article) newSelection;
                toRecipe = new Item(ar.getName(),ar.getPrice());
            }
        });
        // adding article into Item Table with double click
        cashier.tableArticles.setRowFactory(tv ->{
            TableRow<Article> row = new TableRow();
                row.setOnMouseClicked(event ->{
                    if(event.getClickCount() == 2 &&(!row.isEmpty())){
                        boolean contains = true;
                        for(int i =0; i < recipe.size(); i++){
                            if(recipe.get(i).getArticle().equals(toRecipe.getArticle())){
                                recipe.get(i).setQuantity(recipe.get(i).getQuantity()+1);
                                ObservableList<Item> recipeData = FXCollections.observableArrayList(recipe);
                                recipe.clear();
                                recipe.addAll(recipeData);
                                contains = false;
                            }
                        }
                        for(int i = 0; i < articles.size(); i++){
                            if(articles.get(i).getName().equals(toRecipe.getArticle())){
                                articles.get(i).setQuantity(articles.get(i).getQuantity() - 1);
                                ObservableList<Article> arts =  FXCollections.observableArrayList(articles);
                                articles.clear();
                                articles.addAll(arts);
                            }
                        }
                        if(contains){
                            recipe.add(toRecipe);
                        }
                    }
                });
            return row;
        });
        // search in cashier table of articles and passing it into recipe table with ENTER button
        cashier.searchArticle.addEventFilter(KeyEvent.KEY_PRESSED,e ->{
            System.out.println(e.getText());
            if(e.getText().matches("[a-zA-Z0-9]") || e.getCode() == KeyCode.BACK_SPACE){
                if(cashier.searchArticle.getText().toCharArray().length > 0){
                    articles.clear();
                    dataContainer.stream().filter((a) -> (a.getName().toLowerCase().contains(cashier.searchArticle.getText().toLowerCase()))).forEachOrdered((a) -> {
                        articles.add(a);
                    });
                    if(!articles.isEmpty()){
                        cashier.tableArticles.getSelectionModel().selectFirst();
                        Article ar = (Article) cashier.tableArticles.getSelectionModel().getSelectedItem();
                        toRecipe = new Item(ar.getName(),ar.getPrice());
                    }
                }
                else{
                    articles.addAll(dataContainer);
                }
            }
            else if(e.getCode() == KeyCode.ENTER){
                if(!cashier.tableArticles.getSelectionModel().isEmpty()){
                    Article ar = (Article) cashier.tableArticles.getSelectionModel().getSelectedItem();
                    recipe.add(new Item(ar.getName(),ar.getPrice()));
                }
            }
        });
        cashier.tableItems.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            cashier.deleteItem.setDisable(!(newSelection != null));
        });
        cashier.deleteItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Item it = (Item) cashier.tableItems.getSelectionModel().getSelectedItem();
                for(int i =0; i < articles.size(); i++){
                    if(articles.get(i).getName().contains(it.getArticle())){
                        articles.get(i).setQuantity(articles.get(i).getQuantity() + it.getQuantity());
                        dataContainer = FXCollections.observableArrayList(articles);
                        articles.clear();
                        articles.addAll(dataContainer);
                    }
                }
                recipe.remove(it);
            }
        });
        cashier.restartRecipe.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
               clearItemTable();
           }
        });
        cashier.newRecipe.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                db.setRecipeToDb(new Recipe(recipe));
                db.updateArticlesQuantity(articles);
                recipe.clear();
            }
        });
        recipeView.tableRecipes.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            Recipe r = (Recipe) newSelection;
            recipeView.fillTableItems(r.items);
        });
        System.out.println("This is executed");
        primaryStage.setTitle("Store Manager");
        primaryStage.setScene(admin.getScene());
        primaryStage.show();
    }
    public void clearItemTable(){
        for(int i =0; i < articles.size(); i++){
            for(Item it : recipe){
                if(articles.get(i).getName().equals(it.getArticle())){
                 articles.get(i).setQuantity(articles.get(i).getQuantity() + it.getQuantity());
                 dataContainer = FXCollections.observableArrayList(articles);
                 articles.clear();
                 articles.addAll(dataContainer);
             }
            }  
         }
        recipe.clear();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
