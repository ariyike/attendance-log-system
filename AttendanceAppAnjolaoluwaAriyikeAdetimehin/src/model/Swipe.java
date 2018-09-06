package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

/**
 *
 * @author mga
 */
public class Swipe {

    /**
     *
     */
    //field definition for swipe ID
    protected final int id;

    /**
     *
     */
    //field definition for card ID
    protected String cardId;

    /**
     *
     */
    //field definition for room name
    protected String room;

    /**
     *
     */
    //field definition for swipe date and swipe time
    protected final Calendar swipeDateTime;
    
    //lastSwipeIdUsed variable will be used to allocate swipe ID within the system
    //EOLN variable will be used to identify end of line within file
    //QUOTE variable will be used to identify quotation marks within file
    private static int lastSwipeIdUsed = 0;
    static final char EOLN='\n';       
    static final String QUOTE="\"";    
    
    //SwipeDateTimeComparator variable will be used to store swipes and execute a total ordering on them 
    //could give me a problem later, check it
    public Comparator<Swipe> SwipeDateTimeComparator;
    /**
     *
     */
    public Swipe() {
        this.id = ++lastSwipeIdUsed;
        this.cardId = "Unknown";
        this.room = "Unknown";
        this.swipeDateTime = getNow();
    }
    
    /**
     *
     * @param cardId
     * @param room
     */
    public Swipe(String cardId, String room) {
        this.id = ++lastSwipeIdUsed;
        this.cardId = cardId;
        this.room = room;        
        this.swipeDateTime = getNow();
    }    
   
    /**
     *
     * @param swipeId
     * @param cardId
     * @param room
     * @param swipeDateTime
     */
    public Swipe(int swipeId, String cardId, String room, Calendar swipeDateTime) {
        this.id = swipeId;
        this.cardId = cardId;
        this.room = room;
        this.swipeDateTime = swipeDateTime;
        if (swipeId > Swipe.lastSwipeIdUsed)
            Swipe.lastSwipeIdUsed = swipeId;          
    }     
    
         
    
    private Calendar getNow() {
        /*GETINSTANCE() is a Calendar class static method which 
        gets a calendar right now using the default time zone and locale*/
        Calendar now = Calendar.getInstance();  
        return now;
    }    

    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    // Methods required: getters, setters, hashCode, equals, compareTo, comparator
    
    /**
     * @return the card id
     */
    public String getCardId(){
        return this.cardId;
    }
    
    public void setCardId(String cardId){
        this.cardId = cardId;
    }
    
    /**
     * @return the room name
     */
    public String getRoom(){
        return this.room;
    }
    
    public void setRoom(String room){
        this.room = room;
    }
    
    /**
     * @return the swipe date and time
     */
    public Calendar getSwipeDateTime(){
        return this.swipeDateTime;
    }
    
    
    /**
     *
     * @param calendar
     * @return
     */
        
    public static String formatSwipeDateTime(Calendar calendar) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar now = Calendar.getInstance();  
        return dateFormat.format(calendar.getTime());
    }    

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "\nSwipe Id: " + this.id + " - Card Id: " + this.cardId +            
                " - Room: " + this.room + " - Swiped: " + formatSwipeDateTime(this.swipeDateTime);
    }
    //when writing from a repository to a text file
    public String toString(char delimiter){
     
        String myString;
        myString = Integer.toString(this.getId()) + delimiter + QUOTE + this.cardId + QUOTE + delimiter + QUOTE + this.room +
                QUOTE + delimiter + QUOTE + this.formatSwipeDateTime(swipeDateTime) + QUOTE + EOLN;
        
        return myString;
    }
    
    
    @Override
    public int hashCode(){
        int code;
        int temp = this.getId();
        code = temp * 2;
        return code;
    }

    @Override
    public boolean equals(Object cSwipe) {
        Swipe comparisonSwipe = (Swipe)cSwipe;
        return ((this.id == comparisonSwipe.id) && (cSwipe != null) && (this.getClass() == comparisonSwipe.getClass()));
    }
    
    public int compareTo(Swipe compareSwipe){
        int comparisonValue;
        if(this.equals(compareSwipe)){
            comparisonValue = 0;
        }
        else{
            //will return negative value compareSwipe comes after and positive value otherwise
            comparisonValue = (this.id)-(compareSwipe.id);
        }
        return comparisonValue;
    }
    
    public static Comparator<Swipe> SwipeComparatorId = new Comparator<Swipe>() {
        @Override
        public int compare(Swipe s1, Swipe s2) {
            Swipe swipe1 = s1;
            Swipe swipe2 = s2;
            
            
            return swipe1.compareTo(swipe2);
        }
    };
    
    public int compare(Swipe compareSwipe){
        int comparisonValue;
        Calendar compareSwipeDate = compareSwipe.getSwipeDateTime();
        
        
        if (this.getSwipeDateTime().after(compareSwipeDate)){
            comparisonValue = 1;
        }
        else if (compareSwipeDate.after(this.getSwipeDateTime())){
            comparisonValue = -1;
        }
        else{
            comparisonValue = 0;
        }
        return comparisonValue;
        //return this.getSwipeDateTime().compareTo(compareSwipeDate);
    }
    
    //lambda expressions
    public static Comparator<Swipe> SwipeComparator = (Swipe s1, Swipe s2) -> {
        Swipe swipe1 = s1;
        Swipe swipe2 = s2;
        
      
        return swipe1.compare(swipe2);
    };

}
