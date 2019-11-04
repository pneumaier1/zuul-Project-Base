import java.lang.NullPointerException;
import java.util.Random;
import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    public Room currentRoom;
    public Room prevRoom;
    int randomRoom;
    ArrayList<Room> array_Rooms = new ArrayList<Room>();
   
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room lot, auditorium, lobby, cafe, office, playground, gym, sllobby, library, garden, dungeon, bathroom, tllobby, classroom, roof,
        transporter;
        
        Item office_Item, dungeon_Item, gym_Item, lot_Item, no_Item;
        
        Item dungeonItems[] = { new Item("red carpet", 2),
                new Item ("Lamp",200),
                new Item ("table",1000)};
        Item officeItems[] = { new Item("Cheeseburger", 2),
                new Item ("Desk", 2000),
                new Item ("Chair", 400)};
        Item gymItems[] = { new Item(" A ladder", 10),
                new Item ("Basketball", 10)};
        no_Item = new Item("", 0);
        
        
        // create the rooms
        transporter = new Room("inside of the transporter");
        lot = new Room("in the parking lot outside the school");
        auditorium = new Room("in the school auditorium");
        lobby = new Room("in the main lobby");
        cafe = new Room("in the cafeteria");
        office = new Room("in the principal's office");
        playground = new Room("outside in the fenced in playground");
        gym = new Room("in the gymnasium");
        sllobby = new Room("in the second floor lobby");
        library = new Room("in the library");
        garden = new Room("in the garden");
        dungeon = new Room("in the secret dungeon");
        bathroom = new Room("in the boys bathroom");
        tllobby = new Room("in the third floor lobby");
        classroom = new Room("in a classroom on the thirdfloor");
        roof = new Room("on the roof of the school");
        
        array_Rooms.add(transporter);
        array_Rooms.add(lot);
        array_Rooms.add(auditorium);
        array_Rooms.add(lobby);
        array_Rooms.add(cafe);
        array_Rooms.add(office);
        array_Rooms.add(playground);
        array_Rooms.add(gym);
        array_Rooms.add(sllobby);
        array_Rooms.add(library);
        array_Rooms.add(garden);
        array_Rooms.add(dungeon);
        array_Rooms.add(bathroom);
        array_Rooms.add(tllobby);
        array_Rooms.add(classroom);
        array_Rooms.add(roof);
        
        dungeon = addItemsToRoom(dungeon, dungeonItems);
        office = addItemsToRoom(office, officeItems);
        gym = addItemsToRoom(gym, gymItems);
        
        // initialise room exits
        lot.setExit("south", lobby);
        
        lobby.setExit("north", lot);
        lobby.setExit("west", gym);
        lobby.setExit("south", sllobby);
        lobby.setExit("east", cafe);
        lobby.setExit("office", office);    //user must use silver key found in bathroom to get into
        
        gym.setExit("east", lobby);

        cafe.setExit("west", lobby);
        cafe.setExit("east", playground);

        playground.setExit("west", cafe);
        
        office.setExit("lobby", lobby);
        office.setExit("tunnel", library);
        
        sllobby.setExit("north", lobby);
        sllobby.setExit("west", auditorium);
        sllobby.setExit("south", tllobby);
        sllobby.setExit("east", bathroom);
        
        auditorium.setExit("east", sllobby);
        
        library.setExit("west", sllobby);
        library.setExit("east", garden);
        library.setExit("secret", dungeon);
        library.setExit("office", office);
        
        garden.setExit("west", library);
        
        dungeon.setExit("North", library);
        dungeon.setExit("South", bathroom);
        dungeon.setExit("transporter", transporter);
        
        transporter.setExit("a", dungeon);
        transporter.setExit("b", lot);
        transporter.setExit("c", library);
        transporter.setExit("d", auditorium);
        transporter.setExit("e", lobby);
        transporter.setExit("f", cafe);
        transporter.setExit("g", office);
        transporter.setExit("h", playground);
        transporter.setExit("i", gym);
        transporter.setExit("j", sllobby);
        transporter.setExit("k", library);
        transporter.setExit("l", garden);
        transporter.setExit("m", bathroom);
        transporter.setExit("n", tllobby);
        transporter.setExit("o", classroom);
        transporter.setExit("p", roof);
        
        bathroom.setExit("secret", dungeon);
        bathroom.setExit("west", tllobby);
        
        tllobby.setExit("north", sllobby);
        tllobby.setExit("west", classroom);
        tllobby.setExit("south", roof);
        tllobby.setExit("east", bathroom);
        
        classroom.setExit("east", tllobby);
        
        roof.setExit("north", tllobby);
        roof.setExit("jump", lot);
        
        randomRoom = (int) (Math.random() * array_Rooms.size());
        transporter.setExit("special", array_Rooms.get(randomRoom));
        array_Rooms.remove(randomRoom);
        
        
        currentRoom = lot;  // start game outside
        prevRoom = null;
        
    }
   
  
    private Room addItemsToRoom(Room room, Item items[])
    {
        for (int i = 0; i < items.length; i++)
        {
            room.addItem(items[i]);
        }  
        return room;
    }
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
              
        }
        System.out.println("Thank you for playing.  Good bye.");
        
    }
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
                
            case BACK:
                back();
                break;
         
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around the school grounds.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }
    
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    private void eat()
    {
        System.out.println("YUMMY YUMMY IN MY TUMMY!");
    }
    private void back()
    {
        currentRoom = prevRoom;
        System.out.println(currentRoom.getLongDescription());
    }
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if(nextRoom.getShortDescription().contains("transporter"))
                {
                    System.out.println("You are currently being transported");
                    direction = "special";
                    nextRoom = nextRoom.getExit(direction);
                    
                }
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            prevRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
           
            
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
