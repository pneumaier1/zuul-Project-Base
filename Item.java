
/**
 * Write a description of class Item here.
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
     * Constructor for objects of class Item
     */
    public Item()
    {
        // initialise instance variables
        itemWeight = 0.0;
        itemDescription = "";
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public Item(String description, double weight)
    {
        // put your code here
        itemDescription = description;
        itemWeight = weight;
    }
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
