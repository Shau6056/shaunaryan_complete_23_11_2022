package main;

import controllers.NoteAPI;
import models.Item;
import models.Note;
import utils.ScannerInput;
import utils.Utilities;

import java.util.Scanner;

public class Driver {

    private NoteAPI noteAPI = new NoteAPI(); //creating new object called noteapi of type noteapi

    public static void main(String [] args) //main method
    {
        new Driver();
    }

    public Driver()
    {
        runMenu();
    }

    /**
     *
     * creating mainMenu method with below input of menu etc.
     */
    private int mainMenu()
    {
        return ScannerInput.readNextInt("""
    
            ------------------------------------------------------------------
            |                       NOTE KEEPER APP                          |
            ------------------------------------------------------------------
            |NOTE MENU                                                       |
            |   1) Add a note                                                |
            |   2) List all notes (all, active, archived)                    |
            |   3) Update a note                                             |
            |   4) Delete a note                                             |
            |   5) Archive a note                                            |
            ------------------------------------------------------------------
            |ITEM MENU                                                       |
            |   6) Add an item to a note                                     |
            |   7) Update item description on a note                         |
            |   8) Delete an item from a note                                |
            |   9) Mark item as complete/todo                                |
            ------------------------------------------------------------------
            |REPORT MENU FOR NOTES
            |   10) All notes and their items active & archived)             |
            |   11) Archive notes whose items are all complete               |
            |   12) All notes within a selected Category                     |
            |   13) All notes within a selected Priority                     |
            |   14) Search for all notes (by note title)                      |
            ------------------------------------------------------------------
            |REPORT MENU FOR ITEMS                                           |
            |   15) All items that are todo (with note title)                |
            |   16) Overall number of items todo/complete                    |
            |   17) Todo/complete items by specific Category                 |
            |   18) Search for all items (by item description)               |
            ------------------------------------------------------------------
            |SETTINGS MENU                                                   |
            |   20) Save                                                     |
            |   21) Load                                                     |
            |   0)  Exit                                                     |
            ------------------------------------------------------------------
            ==>>  """);

    }

    /**
     * using switch to go through options on menu list.
     */
    private void runMenu()
    {
        int option = mainMenu();

        while(option != 0)
        {
            switch(option)
            {
                case 1 -> addNote();
                case 2 -> viewNotes();
                case 3 -> updateNote();
                case 4 -> deleteNote();
                case 5 -> archiveNote();
                case 6 -> addItemToNote();
                case 7 -> updateItemDescInNote();
                case 8 -> deleteItemFromNote();
                case 9 -> markCompletionOfItem();
                case 10 -> printActiveAndArchivedReport();
                case 11 -> archiveNotesWithAllItemsComplete();
                case 12 -> printNotesBySelectedCategory();
                case 13 -> printNotesByPriority();
                case 14 -> searchNotesByTitle();
                case 15 -> printAllTodoItems();
                case 16 -> printOverallItemsTodoCompleted();
                case 17 -> printItemCompletionStatusByCategory();
                case 18 -> searchItemsByDescription();
                case 20 -> save();
                case 21 -> load();
                case 0 -> exit();
                default -> System.out.println("Not a valid option " + option);

            }
            ScannerInput.readNextLine("\nPress any key to continue...");

            option = mainMenu();


        }
    }

    //creating add method and asking for user input when needed.
    private void addNote() {
        String noteTitle = ScannerInput.readNextLine("Enter the Note Title: ");
        int notePriority = ScannerInput.readNextInt("Enter the Note Priority: ");
        String noteCategory = ScannerInput.readNextLine("Enter the note Category: ");

        boolean isAdded = noteAPI.add(new Note(noteTitle, notePriority, noteCategory));

        if (isAdded) {
            System.out.println("The note has been successfully added!");
        } else {
            System.out.println("There has been an error, the note could NOT be added.");
        }

    }

    //Lists of all types of notes - active, all and archived.
    private void viewNotes()
    {
        if(noteAPI.numberOfNotes() == 0)
        {
            System.out.println("There are no notes stored in the system.");
        }
        else {
            int option = ScannerInput.readNextInt("""
                    --------------------------------
                    |   1) View ALL notes          |
                    |   2) View ACTIVE notes       |
                    |   3) View ARCHIVED notes     |
                    --------------------------------
                    ==>>  """);

            if (option == 1) {
                System.out.println(noteAPI.listAllNotes());
            }
            if (option == 2) {
                System.out.println(noteAPI.listActiveNotes());
            }
            if (option == 3) {
                System.out.println(noteAPI.listArchivedNotes());
            }
        }
    }

    //Allowing user to mark when a item is complete
    private void markCompletionOfItem()
    {
        System.out.println(noteAPI.listActiveNotes());

        int indexOne = ScannerInput.readNextInt("What is the note index item you want to change status of? ");

        if(noteAPI.isValidIndex(indexOne) && noteAPI.numberOfItems() > 0)
        {
            int indexTwo = ScannerInput.readNextInt("What is the item index that you want to change the status of? ");

            if(noteAPI.isValidIndex(indexTwo))
            {
                char yesOrNo = ScannerInput.readNextChar("Do you want to change status to complete -> Y/y or do you want to change staus to TODO -> N/n ");

                boolean itemStatus =  Utilities.YNtoBoolean(yesOrNo);

                boolean didItWork = noteAPI.setItemStatus(indexOne, indexTwo, itemStatus);

                if(didItWork == true)
                {
                    System.out.println("The item status has been successfully changed!");
                }
                else
                {
                    System.out.println("The item status has NOT been changed.");
                }

            }
        }

    }

    //listing all the notes by calling methods predefined in NoteApi
    private void listAllNotes()
    {
        if(noteAPI.numberOfNotes()==0)
        {
            System.out.println("There are no notes in the system.");
        }
        else {
            System.out.println(noteAPI.listAllNotes());
        }
    }

    //Update method in NoteAPI predefined
    private void updateNote() {
        listAllNotes();

        if (noteAPI.numberOfNotes() > 0) {
            int indexToUpdate = ScannerInput.readNextInt("Enter the index of the note you would like to update  ");

            if (noteAPI.isValidIndex(indexToUpdate)) {
                String noteTitle = ScannerInput.readNextLine("Enter the note Title: ");
                int notePriority = ScannerInput.readNextInt("Enter the note priority: ");
                String noteCategory = ScannerInput.readNextLine("Enter the note category: ");

                if (noteAPI.updateNote(indexToUpdate, noteTitle, notePriority, noteCategory)) {
                    System.out.println("Update Successful");
                } else if (noteAPI.updateNote(indexToUpdate,noteTitle, notePriority, noteCategory) == false) {
                    System.out.println("Update was NOT successful");
                }
                else
                {
                    System.out.println("There was no notes for this index");

                }
            }
        }
    }

    //Doing checks in method to make sure the there are notes to delete.
    private void deleteNote()
    {
        if(noteAPI.numberOfNotes()>0)
        {

            System.out.println(noteAPI.listAllNotes());

            int deleteNoteIndex = ScannerInput.readNextInt("What note would you like to delete? ");

            if(noteAPI.isValidIndex(deleteNoteIndex))
            {
                noteAPI.deleteNote(deleteNoteIndex);
                System.out.println("Your note is deleted");
            }
            else if(noteAPI.isValidIndex(deleteNoteIndex) == false)
            {
                System.out.println("Your note index could not be found");
            }
            else if(noteAPI.numberOfNotes() == 0)
            {
                System.out.println("There are no notes stored.");
            }
            else
            {
                System.out.println("We could NOT delete this note");
            }
        }
    }

    //Archiving the note that the user wants to archive.
    private void archiveNote()
    {
        System.out.println(noteAPI.listActiveNotes());

        if(noteAPI.numberOfActiveNotes() > 0)
        {
            int index = ScannerInput.readNextInt("What active note do you want to archive? ");
            boolean check = noteAPI.archiveNote(index);

            if(check == true)
            {
                System.out.println("Note was SUCCESSFULLY archived");
            }
            else if(noteAPI.numberOfNotes() == 0)
            {
                System.out.println("There are no notes to archive.");
            }
            else
            {
                System.out.println("The note was NOT archived");
            }
        }
    }

    //Adding an item to Note.
    public void addItemToNote()
    {
        if(noteAPI.numberOfNotes() > 0) {
            System.out.println(noteAPI.listActiveNotes());

            int index = ScannerInput.readNextInt("Select index of active note: ");

            if (noteAPI.isValidIndex(index)) {
                String description = ScannerInput.readNextLine("What is the item description: ");

                boolean didWork = noteAPI.addItemToNote(index, description);

                if (didWork == true) {
                    System.out.println("Item added successfully");
                } else {
                    System.out.println("Unsuccessful");
                }

            }
        }
        else
        {
            System.out.println("There are no notes to add an item too.");
        }
    }

    //Updating the item description on a specific note.
    public void updateItemDescInNote() {
        System.out.println(noteAPI.listActiveNotes());

        int index = ScannerInput.readNextInt("Enter the index of an active note: ");

        if (noteAPI.isValidIndex(index) && noteAPI.numberOfItems()>0)
        {

            int indexTwo = ScannerInput.readNextInt("Enter the index of an active item: ");
            String description = ScannerInput.readNextLine("Enter in a new Item description: ");

            boolean yesOrNo = noteAPI.getItemStatus(index, indexTwo);
            boolean didItWork = noteAPI.updateItemDescriptionInNote(indexTwo, description, yesOrNo);

            if(didItWork)
            {
                System.out.println("Update was successful");
            }
            else {
                System.out.println("Update was NOT unsuccessful");
            }
        }
    }

    //This is to delete an Item from note using Scanner to take in user indexes etc and using classes to update
    public void deleteItemFromNote()
    {
        System.out.println(noteAPI.listActiveNotes());

        int index = ScannerInput.readNextInt("Select index of active note: ");

        if(noteAPI.isValidIndex(index))
        {
            int description = ScannerInput.readNextInt("What is the item index you want to delete: ");

            boolean didWork = noteAPI.deleteItemFromNote(index, description);

            if(didWork == true)
            {
                System.out.println("Item deleted successfully");
            }
            else
            {
                System.out.println("Unsuccessful");
            }

        }
    }

    public void printAllNotes()
    {
        System.out.println("------------------------------------------------------------------" + "\n" + noteAPI.numberOfActiveNotes() + " active note(s): "
                + "\n" + noteAPI.listActiveNotes() + "------------------------------------------------------------------" + "\n" + noteAPI.numberOfArchivedNotes() +" archived note(s): " + "\n"
                + noteAPI.listArchivedNotes() + "\n" + "------------------------------------------------------------------");
    }

    public void archiveNotesWithAllItemsComplete()
    {
        noteAPI.archiveNotesWithAllItemsComplete();
    }

    public void printArchivedNotes()
    {
        System.out.println(noteAPI.listArchivedNotes());
    }

    public void printActiveNotes()
    {
        System.out.println(noteAPI.listActiveNotes());
    }

    public void printActiveAndArchivedReport()
    {
        System.out.println("------------------------------------------------------------------" +
                "\n" + " Report for active and archived notes" + "\n" + noteAPI.numberOfActiveNotes() + " active note(s): "
                + "\n" + noteAPI.listActiveNotes() + "------------------------------------------------------------------" + "\n" + noteAPI.numberOfArchivedNotes() +" archived note(s): " + "\n"
                + noteAPI.listArchivedNotes() + "\n" + "------------------------------------------------------------------");
    }

    public void printNotesBySelectedCategory()
    {
        String category = ScannerInput.readNextLine("What note category would you like to see? ");

        System.out.println(noteAPI.listNotesBySelectedCategory(category));

    }

    private void printNotesByPriority()
    {
        if(noteAPI.numberOfNotes() > 0)
        {
            int notePriority = ScannerInput.readNextInt("Enter a note priority you want to search for? 1-5 ");

            if(noteAPI.numberOfNotesByPriority(notePriority) == 0)
            {
                System.out.println("There are no notes by the note priorty of number " + notePriority);
            }
            else
            {
                System.out.println(noteAPI.listNotesBySelectedPriority(notePriority));
            }
        }
    }
    public void printOverallItemsTodoCompleted()
    {
        System.out.println("The number of completed items: " + noteAPI.numberOfCompleteItems() + "\n" + "The number of todo items: " + noteAPI.numberOfTodoItems());

    }
    public void searchNotesByTitle()
    {
        String title = ScannerInput.readNextLine("What note title do you want to search for? ");

        System.out.println(noteAPI.searchNotesByTitle(title));
    }

    public void printAllTodoItems()
    {
        System.out.println(noteAPI.listTodoItems());
    }

    public void printItemCompletionStatusByCategory()
    {
        String category = ScannerInput.readNextLine("Enter the category [Home, Work, Hobby, Holiday, College]: ");

        System.out.println(noteAPI.listItemStatusByCategory(category));
    }

    public void searchItemsByDescription()
    {
        String getItem = ScannerInput.readNextLine("What item description do you want to search for? ");

        if(noteAPI.searchItemByDescription(getItem) == "")
        {
            System.out.println("There are no items with the description of " + getItem + ".");
        }
        else
        {
            System.out.println(noteAPI.searchItemByDescription(getItem));
        }
    }

    private void exit()
    {
        System.out.println("Thank you, goodbye!");
    }
    private void save() {
        try {
            noteAPI.save();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
    }

    //load all the products into the store from a file on the hard disk
    private void load() {
        try {
            noteAPI.load();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }
    }

}


