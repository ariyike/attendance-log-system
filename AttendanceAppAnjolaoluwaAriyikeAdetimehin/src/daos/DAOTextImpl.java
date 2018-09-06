/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Swipe;
import model.VisitorSwipe;
import repositories.Repository;

/**
 *
 * @author Student
 */
public class DAOTextImpl implements DAOInterface{
    private static final char DELIMETER =',';
    //a delimeter variable to help split the strings that make up each line of the txt file
    private static final String LOADDELIMETER =",";
   
    @Override
    public Repository load(String filename){
    Repository appRepository = new Repository();
    
        
            try (BufferedReader appBufferedReader = new BufferedReader(new FileReader(filename))){
                    String line = appBufferedReader.readLine();
                    String[] wordsOnLine;
                    Swipe aNewSwipe;
                    while (line != null){
                        wordsOnLine = line.split(LOADDELIMETER);

                        int swipeId = Integer.parseInt(wordsOnLine[0]);
                        String cardId = stripQuotes(wordsOnLine[1]);
                        String room = stripQuotes(wordsOnLine[2]);
                        String stringDate = stripQuotes(wordsOnLine[3]);
                        SimpleDateFormat swipeDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            try{

                                Date actualDate = swipeDateFormat.parse(stringDate);
                              }
                            catch (ParseException ex) {
                                Logger.getLogger(DAOTextImpl.class.getName()).log(Level.SEVERE, null, ex);
                             }
                        Calendar swipeDateTime = swipeDateFormat.getCalendar();
                        if (wordsOnLine.length >4){
                            String visitorName = stripQuotes(wordsOnLine[4]);
                            String visitorCompany = stripQuotes(wordsOnLine[5]);

                            aNewSwipe = new VisitorSwipe(swipeId, cardId, room, swipeDateTime, visitorName, visitorCompany);
                        }
                        else {
                            aNewSwipe = new Swipe(swipeId, cardId, room, swipeDateTime);
                        }
                        appRepository.add(aNewSwipe);

                        line = appBufferedReader.readLine();                    
                }
                appBufferedReader.close();
            } catch(IOException ex){} 
       
    
        return appRepository;
    }
    public String stripQuotes(String aString){
        String removedQuotes;
        removedQuotes = aString.replace('"', ' ').trim();
        return removedQuotes;
    }
    
    //will use a Print Writer object to write details of swipe objects within specified repository into specified text file 
    @Override
    public void store(String filename, Repository repository){
        PrintWriter toFile;
        try {
            toFile = new PrintWriter(filename);
            toFile.print(repository.toString(DELIMETER));
            toFile.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAOTextImpl.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
}
