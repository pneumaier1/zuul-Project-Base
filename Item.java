
/**
 * Item class allows for items to be created an given descriptions.
 *
 * @author (Paul Neumaier)
 * @version (version 11.2.19)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String itemDescription;
    private double itemWeight;

    /**
     * Constructor for objects of class Item.
     */
    public Item()
    {
        // initialise instance variables
        itemWeight = 0.0;
        itemDescription = "";
    }

    /**
     * Initializing item description and weight.
     */
    public Item(String description, double weight)
    {
        // put your code here
        itemDescription = description;
        itemWeight = weight;
    }
    /**
     *  Create a string of a certain item's description and return the string.
     *  @return a string of an item's description.
     */
    public String getItemDescription()
    {
        String itemString = "";
        if(itemWeight != 0)
        {   
            itemString = "Item Description: ";
            itemString += this.itemDescription + "\n";
            itemString += "Item Weight: " +this.itemWeight;
            return itemString;
        }
        else
            itemString = "" ;
            return itemString;
    }
}
