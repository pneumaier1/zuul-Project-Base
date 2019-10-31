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
    private Room currentRoom;
           
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
        Room lot, auditorium, lobby, cafe, office, playground, gym, sllobby, library, garden, dungeon, bathroom, tllobby, classroom, roof;
      
        // create the rooms
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
        library.setExit("tunnel", office);
        
        garden.setExit("west", library);
        
        dungeon.setExit("library", library);
        dungeon.setExit("bathroom", bathroom);
        
        bathroom.setExit("secret", dungeon);
        bathroom.setExit("west", tllobby);
        
        tllobby.setExit("north", sllobby);
        tllobby.setExit("west", classroom);
        tllobby.setExit("south", roof);
        tllobby.setExit("east", bathroom);
        
        classroom.setExit("east", tllobby);
        
        roof.setExit("north", tllobby);
        roof.setExit("jump", lot);
        
        currentRoom = lot;  // start game outside
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
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
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

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
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
