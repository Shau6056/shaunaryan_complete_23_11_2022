package models;

import utils.Utilities;

import java.util.Objects;

//The Item class is responsible managing a single Item.

public class Item {

    /**
     * These are two private fields of Type String and boolean.
     */
    private String itemDescription = "No Description";
    private boolean isItemCompleted = false;

    /**
     * This is a constructor and it needs one parameter of type String.
     * @param itemDescriptionIn
     */
    public Item(String itemDescriptionIn)
    {
        itemDescription = utils.Utilities.truncateString(itemDescriptionIn, 50); //This is setting the field itemDescription to the parameter in but the max length is 50, this is going into
        //the Utilities class and calling a method called truncateString
    }

    /**
     * This is another constructor that needs two parameters.
     * @param itemDescriptionIn
     * @param isItemCompletedIn
     */
    public Item(String itemDescriptionIn, boolean isItemCompletedIn)
    {
        itemDescription = utils.Utilities.truncateString(itemDescriptionIn, 50);

        isItemCompleted = isItemCompletedIn;

    }

    /**
     * This is a method called getItemDescription and returns a String this is a getter.
     * @return
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * This is a setter that will set the Item Description
     * @param itemDescription
     */
    public void setItemDescription(String itemDescription)
    {
        if(Utilities.validateStringLength(itemDescription, 50)) //This is going into Utilities class and is using a method to put max length on String
        {
            this.itemDescription = itemDescription; //The ItemDescription will only change it is less => 50 characters.
        }
    }
    /**
     *
     * @return boolean if item is completed or not.
     */
    public boolean isItemCompleted()
    {
        if(isItemCompleted == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Setting the itemCompletion.
     * @param itemCompleted
     */
    public void setItemCompleted(boolean itemCompleted)
    {
        isItemCompleted = itemCompleted;
    }

    /**
     *
     * @param o this is a generated equals method that is taking in and seeing if two objects are the same by
     *          seeing if the itemCompleted are the same && && etc and its returning true or false depending on the
     *          answer.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return isItemCompleted == item.isItemCompleted && Objects.equals(itemDescription, item.itemDescription);
    }

    /**
     *
     * @return status of Todo or Complete - using the boolean
     */
    public String status()
    {
        String comp ="";

        if(isItemCompleted)//if true then String this.
        {
            comp ="[Completed]";
        }
        else
        {
            comp = "[TODO]"; //Anything else it is this.
        }
        return comp;
    }

    /**
     * @return toString created. Its just the itemDescrpiton and then the status which is todo or complete.
     */
    public String toString() {
        return itemDescription + ". " + status()  + "\n";
    }

}

