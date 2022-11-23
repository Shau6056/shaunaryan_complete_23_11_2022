package models;

import utils.CategoriesUtility;
import utils.Utilities;

import java.util.ArrayList;
import java.util.Objects;

//This is the class for a single Note. Notes have multiple Items.

public class Note {

    private String noteTitle = "No Title";
    private int notePriority = 1;
    private String noteCategory = "";
    private boolean isNoteArchived = false;
    private ArrayList<Item> items = new ArrayList();

    /**
     *
     * @param noteTitle - The title of the note
     * @param notePriority - The note priorty set to one.
     * @param noteCategory - The note category is empty String.
     */
    public Note(String noteTitle, int notePriority, String noteCategory) {

        if(Utilities.validateStringLength(noteTitle, 20)) //Here we are calling a method in Utilities called Validate String its taking in the note tile but the max length is 20
        {
            this.noteTitle = noteTitle; //if the above condition is met this the title is set to the input
        }
        else
        {
            this.noteTitle = Utilities.truncateString(noteTitle, 20); //if conditon is not met then cut off the length at 20.
        }

        setNotePriority(notePriority); //Calling the method

        setNoteCategory(noteCategory);

    }

    /**
     *
     * @return Note Title
     */

    public String getNoteTitle() {
        return noteTitle;

    }

    /**
     *
     * @param noteTitle - setting the note title but the max length of being sent is 20
     */

    public void setNoteTitle(String noteTitle) {

        if(Utilities.validateStringLength(noteTitle, 20))
        {
            this.noteTitle = noteTitle;
        }

    }

    public int getNotePriority() {
        return notePriority;
    }

    /**
     *
     * @param notePriorityIn
     * setting the priorty of the note but only note can only be max of 5. Using Utilities methods
     */
    public void setNotePriority(int notePriorityIn) {
        boolean notePriorityCheck = utils.Utilities.validRange(notePriorityIn, 0, 5);

        if (notePriorityCheck) {

            notePriority = notePriorityIn;
        }

    }

    /**
     *
     * @param noteCategoryIn - setting the category but has conditions to be met set in the CategoriesUtility class
     */
    public void setNoteCategory(String noteCategoryIn)
    {
        if(CategoriesUtility.isValidCategory(noteCategoryIn))
        {
            noteCategory = noteCategoryIn;
        }

    }

    /**
     *
     * @return note category
     */
    public String getNoteCategory()
    {
        return noteCategory;
    }

    /**
     *
     * @return if the note archived or note
     */

    public boolean isNoteArchived() {

        return isNoteArchived;
    }

    /**
     *
     * @param isNoteArchivedIn setting if the note is archived.
     */

    public void setNoteArchived(boolean isNoteArchivedIn) {
        isNoteArchived = isNoteArchivedIn;
    }

    /**
     *
     * @return items that are in the arrayList of type Item.
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     *
     * @param
     * itemsIn in an ArrayList of items that will loop and add an item.
     */
    public void setItems(ArrayList<Item> itemsIn)
    {
        for (Item item: itemsIn)
        {
            addItem(item);
        }
    }



    /**
     *
     * @return the number of items.
     */

    public int numberOfItems() {
        return items.size();
    }

    /**
     *
     * @return boolean true of false.
     * This is checking if the note completion status. The loop is checking every item in items and its using a while loop
     * saying while the loop is false then return false. When the loop fails and the condition is true return true. Its because
     * all the items have to have the status as true to be true.
     */

    public boolean checkNoteCompletionStatus()
    {
        for (Item item : items) {
            while (item.isItemCompleted() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return total of complete items.
     */
    public int numberOfCompleteItems()
    {
        int total = 0;

        for (Item item: items)
        {
            if(item.isItemCompleted())
            {
                total++;
            }
        }
        return total;
    }

    /**
     *
     * @return total of todo items.
     */
    public int numberOfToDoItems()
    {
        int total = 0;

        for (Item item: items)
        {
            if(!item.isItemCompleted())
            {
                total++;
            }
        }
        return total;
    }

    /**
     *
     * @param descriptionIn
     * The result is set as empty string and if the condition is met by contain the same text that is being
     * sent in meaning the item description contains the parameter then the result adds the item to string.
     *
     * If the condition is not met emaning it does not contain the parameter it will remain empty string.
     * @return the result
     */

    public String searchItemByDescription(String descriptionIn)
    {
        String result = "";
        for (Item item: items)
        {
            if(item.getItemDescription().toLowerCase().contains(descriptionIn.toLowerCase()))
            {
                result += item.toString();
            }
        }
        return result;
    }

    /**
     *
     * @param itemIn
     * adding an item to the arrayList called items.
     * @return
     */

    public boolean addItem(Item itemIn) {

        return items.add(itemIn);
    }

    /**
     * creating a string of the list of Items and return it.
     * @return
     */

    public String listItems()
    {
        String listOf = "";

        if (items.isEmpty() == false)
        {
            for(int i = 0; i < items.size(); i++)
            {
                listOf += "Index number: " + i + " " + items.get(i).toString();
            }

            return listOf;

        }
        else
        {
            return "No items added";
        }
    }

    /**
     * Creating a list of todo items and returning it. By meeting the condition of not being completed the staus is todo.
     * @return
     */

    public String listTodoItems()
    {
        String listOf = "";
        if(numberOfToDoItems() > 0)
        {
            listOf = getNoteTitle() + "\n";
        }
        for (Item item: items)
        {
            if(!item.isItemCompleted())
            {
                listOf += item.toString();
            }
        }
        return listOf;
    }

    /**
     *
     * @param numIn
     * If the param numIn is greater or equal to 0 and less then the size of the arrayList then return true as it is valid
     * @return
     */


    public boolean isValidIndex(int numIn) {
        if (numIn >= 0 && numIn < items.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param indexIn
     *
     * @return the item at the index sent in if it is valid
     */
    public Item findItem(int indexIn) {
        if (isValidIndex(indexIn)) {
            return items.get(indexIn);
        } else {
            return null;
        }
    }

    /**
     *
     * @param indexIn
     * @return the items that is being removed at the index sent in if it is valid
     */
    public Item deleteItem(int indexIn) {

        if (isValidIndex(indexIn)) {
            return items.remove(indexIn);
        } else {
            return null;
        }
    }

    /**
     *
     * @param indexIn
     * getting the item status using the index sent in.
     * @return
     */
    public boolean getItemStatus(int indexIn)
    {
        return findItem(indexIn).isItemCompleted();
    }

    /**
     *
     * @param indexIn
     * @param input
     * setting the item status by getting using the index of the item sent in and setting the boolean to t/f.
     * @return
     */

    public boolean setItemStatus(int indexIn, boolean input)
    {
        if(isValidIndex(indexIn))
        {
            items.get(indexIn).setItemCompleted(input);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     *
     * @param index
     * @param itemDescription
     * Finding the item and then setting the item by updating each part, description, complete etc.
     * @param itemCompleted
     * @return
     */

    public boolean updateItem(int index, String itemDescription, boolean itemCompleted) {

        Item searchItem = findItem(index);

        if (searchItem != null) {
            searchItem.setItemCompleted(itemCompleted);
            searchItem.setItemDescription(itemDescription);
            return true;

        } else {
            return false;
        }
    }

    /**
     *
     * @param o
     * checking if the object sent in is equal.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return notePriority == note.notePriority
                && isNoteArchived
                == note.isNoteArchived
                && Objects.equals(noteTitle, note.noteTitle)
                && Objects.equals(noteCategory, note.noteCategory)
                && Objects.equals(items, note.items);
    }

    /**
     *
     * @return string for the two string.
     */

    public String makeItemString()
    {
        String itemString = "";

        for(int i = 0; i < items.size(); i++)
        {
            itemString += "\t" + i + ": " + items.get(i).toString();
        }

        return itemString;

    }

    /**
     *
     * @return toString.
     */
    public String toString() {
        return "Title = " + noteTitle +
                ", Priority = " + notePriority +
                ", Category = " + noteCategory +
                ", Archived=" + Utilities.booleanToYN(isNoteArchived) +
                "\n" + makeItemString() + "\n";
    }

}
