package com.example;

public class Main {
    public static void main(String args[]){
        App mydb = new App();

        try {
            Note addNote = mydb.addNote("go and vote for Trump", "very high");
            
            // check if the note was added
            if (addNote != null){
                System.out.println("Note added: " + addNote);
            } else {
                System.out.println("Failed to add the note.");
            }

            // try fetching the note by id
            Note fetchNote = mydb.getNoteById(1);

            if (fetchNote != null){
                System.out.println("Fetched Note: "+ fetchNote);
            } else {
                System.out.println("Note with ID 1 not found.");
            }

        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}