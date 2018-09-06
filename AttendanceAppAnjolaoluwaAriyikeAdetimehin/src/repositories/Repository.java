package repositories;

//import java.util.function.Predicate;

import daos.DAOTextImpl;
import java.util.ArrayList;
import java.util.function.Predicate;
import model.Swipe;



public class Repository implements RepositoryInterface {
    //private CollectionChoice<Swipe> items;
    private ArrayList<Swipe> items;
    char delimeter = ',';
    
    public Repository() {
        this.items = new ArrayList<>();      
    }
    
    public Repository(ArrayList<Swipe> items) {        
        this.items = items;
    }
    
    public Repository(String filename) {
        this();
        // Create dao and execute load 
        DAOTextImpl myDataAccessObject = new DAOTextImpl();
        this.items = myDataAccessObject.load(filename).getItems();
    }
    
    @Override
    public ArrayList<Swipe> getItems() {        
        return this.items;
    }
    
  //---------
    public ArrayList<Swipe> getItemsByCardId(String cardId){
        ArrayList<Swipe> specialArrayList = new ArrayList<>();
        for (Swipe item:this.items) {
            
            if (item.getCardId().toLowerCase().equals(cardId)){

                specialArrayList.add(item);
            }
        }
        return specialArrayList;
    }
    //----------
    @Override
    public void setItems(ArrayList<Swipe> items) {        
        this.items = items;
    }
    
    @Override
    public void add(Swipe item) {
        this.items.add(item);
    }
       
    @Override
    public void remove(int id) {
        Predicate<Swipe> predicate = e->e.getId() == id;       
        this.items.removeIf(predicate);
    }
    
    @Override
    public Swipe getItem(int id) {
        for (Swipe item:this.items) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "\nItems: " + this.items;
    }    
    
    @Override
    public void store(String filename) {       
        // create dao and execute store 
        DAOTextImpl myDataAccessObject = new DAOTextImpl();
        myDataAccessObject.store(filename, this);
    }    
    
    public String toString(char delimeter) {
        String toPrint = "";
        for (Swipe swipe: this.items) {
            toPrint += swipe.toString(delimeter);
        }
        return toPrint;
    }
   
}
