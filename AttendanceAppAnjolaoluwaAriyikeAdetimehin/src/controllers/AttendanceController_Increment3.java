package controllers;

import helpers.InputHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import model.Swipe;
import model.VisitorSwipe;
import repositories.Repository;

/**
 *
 * @author mga
 */
public class AttendanceController_Increment3 {
    private final Repository repository;
    
    /**
     *
     */
        
    public AttendanceController_Increment3() {
        // to be completed
        InputHelper inputHelper = new InputHelper();
        char response = inputHelper.readCharacter("Would you like to load an already existing Swipes File (Y/N)?");
  
        if (response == 'Y' || response == 'y') {
            String fileName = inputHelper.readString("Please enter required filename");               
            this.repository = new Repository(fileName);
        }
        else {
            this.repository = new Repository();
        }
    }
   
    /**
     *
     */
    public void run() {
        boolean finished = false;
        
        do {
            char choice = displayAttendanceMenu();
            switch (choice) {
                case 'A': 
                    addSwipe();
                    break;
                case 'B':  
                    listSwipes();
                    break;
                case 'C': 
                    listSwipesByCardIdOrderedByDateTime(); // 
                    break;                    
                case 'D': 
                    listSwipeStatistics(); //
                    break;
                case 'Q': 
                    finished = true;
            }
        } while (!finished);
    }
    
    private char displayAttendanceMenu() {
        InputHelper inputHelper = new InputHelper();
        System.out.print("\nA. Add Swipe");
        System.out.print("\tB. List Swipes");        
        System.out.print("\tC. List Swipes In Date Time Order");
        System.out.print("\tD. List Swipes Which Match Card Id");       
        System.out.print("\tQ. Quit\n");         
        return inputHelper.readCharacter("Enter choice", "ABCDQ");
    }    
    
       private void addSwipe() {
        System.out.format("\033[31m%s\033[0m%n", "Add Swipe");
        System.out.format("\033[31m%s\033[0m%n", "========="); 
        
        InputHelper inputHelper = new InputHelper();
        char response = inputHelper.readCharacter("What type of Swipe would you be adding? "
                                                 + "\n Normal, N | Visitor, V", "NV");
        
        if (response == 'N' || response == 'n') {
            String cardId = inputHelper.readString("Please enter card ID");
            String room = inputHelper.readString("please enter room");
            Calendar swipeDateTime = inputHelper.readDate("Please enter date and time in the format yyyy/MM/dd HH:mm:ss",
                                                           "yyyy/MM/dd HH:mm:ss");
            int swipeId = inputHelper.readInt("Please enter swipe ID");
            //Swipe aNewSwipe = new Swipe(cardId, room);
            Swipe aNewSwipe = new Swipe(swipeId, cardId, room, swipeDateTime);
            repository.add(aNewSwipe);
            
        }
        else {
            String cardId = inputHelper.readString("Please enter card ID");
            String room = inputHelper.readString("Please enter room");
            String visitorName = inputHelper.readString("Please enter visitor name");
            String visitorCompany = inputHelper.readString("Please enter visitor company");
            Calendar swipeDateTime = inputHelper.readDate("Please enter date and time in the format yyyy/MM/dd HH:mm:ss",
                                                           "yyyy/MM/dd HH:mm:ss");
            int swipeId = inputHelper.readInt("Please enter swipe ID");
            
            //Swipe aNewSwipe = new VisitorSwipe(cardId, room, visitorName, visitorCompany);
            Swipe aNewSwipe = new VisitorSwipe(swipeId, cardId, room, swipeDateTime, visitorName, visitorCompany);
            repository.add(aNewSwipe);
            
        }
    }
    
   private void listSwipes() {   
        System.out.format("\033[31m%s\033[0m%n", "Swipes");
        System.out.format("\033[31m%s\033[0m%n", "======");
        ArrayList<Swipe> temp = new ArrayList<>();
        temp = repository.getItems();
        Collections.sort(temp, Swipe.SwipeComparatorId);
        System.out.println(temp);
    }       
      

    private void listSwipesByCardIdOrderedByDateTime() {        
        System.out.format("\033[31m%s\033[0m%n", "Swipes By Card Id");
        System.out.format("\033[31m%s\033[0m%n", "=================");
        
        InputHelper inputHelper = new InputHelper();
        String response = inputHelper.readString("Please enter a valid card Id");
        String convertResponse = response.toLowerCase();
        /*created a method in repository which returns an array of swipes that match a specific cardId
        then I used that array as a parameter to create a new repository which is only active within this method*/
        ArrayList<Swipe> temp = new ArrayList<>();
        temp = repository.getItemsByCardId(convertResponse);
        Repository listSwipesByCardRepository = new Repository(temp);
        
       Collections.sort(temp, Swipe.SwipeComparator);
       for(Swipe s: temp){
           System.out.println(s);
       }
    }    
    
    private void listSwipeStatistics() {
        System.out.format("\033[31m%s\033[0m%n", "Swipe Statistics for room");
        System.out.format("\033[31m%s\033[0m%n", "========================="); 
    }
}
