import java.io.*;
import java.util.Scanner;

/** Driver class for program
 */

public class MainProgram {

    //Creates SortedArrayLists for Events and Clients
    SortedArrayList<Event> eventList = new SortedArrayList<>();
    SortedArrayList<Client> clientList = new SortedArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        new MainProgram().setup();
    }

    //main driver method
    private void setup() throws FileNotFoundException {

        //Creating outFile for apology letters. Requires FileNotFoundException exception thrown.
        PrintWriter outFile = new PrintWriter("src/letters.txt");

        //Imports from input.txt
        try {
            readTextFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Main switch statement
        boolean exit = false;
        while (!exit) {
            //Calls main Menu
            switch (mainMenuSelection()) {
                case "f": //ends program
                    exit=true;
                    break;
                case "e": //display info on events
                    eventInfo();
                    break;
                case "c": //display info on clients
                    clientInfo();
                    break;
                case "b": //allow client to buy ticket
                    Client clientChoice1 = findClientName(); //Finds client. Will return 'null' or a client.
                    if(clientChoice1!=null) { //valid client choice
                        Event eventChoice = eventSelection(); //Finds event selection. Will return 'null' or an event.
                        if(eventChoice!=null) { //valid event choice
                            int ticketChoice = numberTickets(outFile,clientChoice1, eventChoice); //Finds number of requested tickets for event. Will return '-1' or a valid number.
                            if(ticketChoice!=-1){ //valid ticket number choice
                                buyTickets(clientChoice1, eventChoice, ticketChoice); //buys tickets
                            }
                        }
                    }
                    break;
                case "r": //allow client to return ticket
                    Client clientChoice2 = findClientName(); //Finds client. Will return 'null' or a client.
                    if(clientChoice2!=null) { //valid client choice
                        Event eventChoice2 = returnTicketEventSelection(clientChoice2); //Finds event selection based on client's purchased tickets. Will return 'null' or an event.
                        if(eventChoice2!=null){ //valid event choice
                            returnTickets(clientChoice2,eventChoice2); //returns tickets
                        }
                    }
                    break;
                default:
                    System.out.println("Please enter a valid option and try again.");
            }
        }
        outFile.close();
    }

    //Prints info on events
    private void eventInfo(){
        System.out.println("\nInformation on events and their available tickets are displayed below:");

        for (Event event : eventList) {
            System.out.printf("Event: %s " + "| Available Tickets: %s%n",
                    event.toString(), event.getNumberTickets());
        }
    }

    //Prints info on clients (name + purchased tickets)
    private void clientInfo(){
        System.out.println("\nInformation on clients and their purchased tickets are displayed below:");

        for (Client client : clientList) {
            System.out.print(client.toString() + " | ");
            if (client.getTicketPurchases().size() > 0) {
                for (int j = 0; j < client.getTicketPurchases().size(); j++) {
                    System.out.print(client.getTicketPurchases().get(j).toString() + ". ");
                }
                System.out.print("\n");
            } else {
                System.out.println("No tickets purchased.");
            }
        }
    }

    //Prints main menu
    private String mainMenuSelection(){
        Scanner in = new Scanner(System.in);

        System.out.println("\nPlease select one of the following options:");
        System.out.println("f - End program");
        System.out.println("e - Display information about all events");
        System.out.println("c - Display information about all clients");
        System.out.println("b - Buy tickets for client");
        System.out.println("r - Return ticket");
        return in.nextLine();
    }

    //Reads in text file
    private void readTextFile() throws IOException { //Add try and catch blocks for specific formatting errors
        Scanner inFile = new Scanner(new FileReader("src/input.txt"));

        //Reads in number of events, creates as events with name/capacity, puts in 'eventList'
        int numberEvents = Integer.parseInt(inFile.nextLine());
        for (int i = 0; i < numberEvents ; i++) {
            String eventName = inFile.nextLine();
            int numberTickets = Integer.parseInt(inFile.nextLine());
            Event e = new Event(eventName, numberTickets);
            eventList.add(e);
        }

        //Reads in number of clients, creates as events with fName/lName, puts in 'clientList'
        int numberClients = Integer.parseInt(inFile.nextLine());
        for (int i = 0; i < numberClients; i++) {
            String[] elements = inFile.nextLine().split(" ");
            Client c = new Client(elements[0],elements[1]);
            clientList.add(c);
        }
    }

    //Matches user typed input to a Client in clientList. If not found, returns null.
    private Client findClientName(){
        Scanner in = new Scanner(System.in);
        Client buyingClient = null; //Initialising buyingClient to be used if client is found

        boolean valid = false;
        while(!valid){
            try{
                System.out.println("\nPlease input the name of the client, or press 'q' to leave:");
                String choice = in.nextLine();
                if(choice.equals("q")) {
                    valid = true;
                }else{
                    String[] names = choice.split(" "); //Splitting up first and second names
                    Client inputClient = new Client(names[0],names[1]); //Creating dummy Client class to use compareTo
                    for(int i = 0; i<clientList.size(); i++){
                        if(inputClient.compareTo(clientList.get(i))==0){
                            buyingClient=clientList.get(i);
                        }
                    }

                    if(buyingClient != null){
                        System.out.println("Client '"+buyingClient+"' found!");
                        valid = true;
                    }else{
                        System.out.println("Client not found.");
                        System.out.println("Please enter the first name and second name of the Client, separated by a space \n(e.g. 'John Smith')");
                    }
                }
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Please enter the first name and second name of the Client, separated by a space \n(e.g. 'John Smith')");
            }
        }
        return buyingClient;
    }

    //Matches user typed input to an Event in eventList. If not found, returns null.
    private Event eventSelection() {
        Scanner in = new Scanner(System.in);

        //eventChoice will store chosen event. If returned as null, ticket purchase will not proceed.
        Event eventChoice = null;

        //Takes user input. Repeats loop until 'q' or event successfully chosen.
        boolean valid = false;
        while(!valid) {
            System.out.println("\nPlease input the name of the event you would like to buy for, or press 'q' to leave");

            //Takes user input
            String choice = in.nextLine();

            if (choice.equals("q")) { //leaves while loop if q
                valid = true;
            } else { //checks if input is an event
                for (Event e : eventList) {
                    if (e.getEventName().equals(choice)) {
                        eventChoice = e;
                        valid =true;
                        System.out.println(eventChoice + " recognised!");
                    }
                }
            }
            if(eventChoice==null){
                System.out.println("Event not recognised. Please try again.");
            }
        }
        return eventChoice;
    }

    //Given an Event input, returns either a valid user input for number of tickets, or -1.
    private int numberTickets(PrintWriter outFile, Client clientChoice, Event eventChoice) {
        Scanner in = new Scanner(System.in);

        //numberTickets will store number of tickets bought for event. If returned as -1, buying tickets will not proceed.
        int numberTickets = -1;

        //Loop to take user input and check there are enough tickets for event
        boolean valid = false;
        while(!valid){
            try{
                System.out.println("\nHow many tickets would you like to buy for "+eventChoice.getEventName()+"?");
                System.out.println("Alternatively, press 'q' to leave");

                String input = in.nextLine();

                //Checks user input. If 'q', leaves
                if(input.equals("q")) {
                    valid = true;
                }else { //Else checks tickets are available
                    numberTickets = Integer.parseInt(input);
                    if (eventChoice.getNumberTickets() >= numberTickets) { //Tickets are available
                        valid = true;
                    } else { //Tickets aren't available
                        System.out.println("\nSorry, " + eventChoice.getEventName() + " only has " + eventChoice.getNumberTickets() + " tickets remaining.");
                        System.out.println("A letter has been printed in letters.txt to convey our apologies.");
                        printLetter(outFile,clientChoice,eventChoice); //prints letter in letters.txt
                        System.out.println("Please try again.");
                        numberTickets = -1;
                    }
                }
            }catch(NumberFormatException e) {
                System.out.println("Incorrect format. Please try again.");
            }
        }
        return numberTickets;
    }

    //Given a Client, Event and int (number of tickets) input, updates client ticketPurchases and total numberOfTickets for selected event based on purchase
    private void buyTickets(Client c, Event e, int n){

        //Checks if client already has tickets bought for an event.
        for(int i = 0; i<c.getTicketPurchases().size(); i++){
            if(c.getTicketPurchases().get(i).getEvent().equals(e)){

                //increases numberBought for client
                c.getTicketPurchases().get(i).setNumberBought(c.getTicketPurchases().get(i).getNumberBought()+n);

                //Reduces ticket count for event
                e.reduceAvailableTickets(n);
                System.out.println("Success - "+c+" has bought "+n+" more tickets for "+e+".");
                System.out.println(c+" now has "+c.getTicketPurchases().get(i).getNumberBought()+" tickets for "+e+".");
                return;
            }
        }

        //Checks if client already has 3 ticket purchases
        if(c.getTicketPurchases().size()==3){ //No more tickets
            System.out.println(c+" already has tickets purchased for 3 different events; No more tickets can be purchased.");
            System.out.println("If tickets for different events are required, please return some first.");
        }else{ //Client can buy more tickets
            //Reduces ticket count for event
            e.reduceAvailableTickets(n);

            //Creates new ticketPurchase for that client.
            TicketPurchases ticketPurchase = new TicketPurchases(e,n);
            c.addTicketPurchases(ticketPurchase);
            System.out.println("Success - "+c+" has bought "+n+" tickets for "+e+".");
        }
    }

    //Matches user typed input to an Event in Client's ticketPurchases. If not found, returns null.
    private Event returnTicketEventSelection(Client c){
        Scanner in = new Scanner(System.in);

        //event selection from already purchased tickets
        if(c.getTicketPurchases().size()==0){
            System.out.println(c+" does not currently have any tickets purchased.");
            return null;
        }

        Event eventChoice = null;
        //Takes user input. Only valid if 'q' or int in range of events.
        boolean valid = false;
        while(!valid) {


            //Chooses event from list of events
            System.out.println("\n" + c + " currently has tickets for the following events: ");

            for (int i = 0; i < c.getTicketPurchases().size(); i++) {
                System.out.println(c.getTicketPurchases().get(i).getEvent().toString() + " (" + c.getTicketPurchases().get(i).getNumberBought() + " tickets purchased)");
            }

            System.out.println("\nPlease input the name of the event you would like to return tickets for, or press 'q' to leave");

            //Takes user input
            String choice = in.nextLine();

            if (choice.equals("q")) { //leaves while loop if q
                return null;
            } else { //checks if event inputted that client has tickets for
                for (int i = 0; i < c.getTicketPurchases().size(); i++) {
                    if (c.getTicketPurchases().get(i).getEvent().toString().equals(choice)) {
                        eventChoice = c.getTicketPurchases().get(i).getEvent();
                        valid = true;
                    }
                }
            }

            //If input not recognised (loop will be repeated)
            if (eventChoice == null){ //checks if event inputted that client does not have tickets for
                for (Event e : eventList){
                    if(e.getEventName().equals(choice)){
                        System.out.println(c+" does not currently have any tickets for "+e+" to return.");
                        break;
                    }
                } //input is not of an event
                System.out.println("Please check your input and try again.");
            }

        }
        return eventChoice;
    }

    //Given Client and Event input, updates Client ticketPurchases and total numberOfTickets for selected event based on return
    private void returnTickets(Client c, Event e){
        Scanner in = new Scanner(System.in);

        //indexOfEvent will store index of event within client's ticketPurchases arraylist.
        int indexOfEvent = -1;

        //Finds event within client's ticketPurchases arraylist and if found stores in indexOfEvent.
        for(int i=0; i<c.getTicketPurchases().size();i++){
            if(c.getTicketPurchases().get(i).getEvent().equals(e)){
                indexOfEvent=i;
            }
        }

        //Checks if indexOfEvent has been found. Else client does not have tickets to return and leaves method.
        if(indexOfEvent==-1){
            System.out.println(c+" has no tickets for "+e+".");
            return;
        }

        //Finds number of tickets client has purchased for that event.
        int numberBought = c.getTicketPurchases().get(indexOfEvent).getNumberBought();

        //Loop to return tickets where numberToReturn is valid
        boolean valid = false;
        while(!valid) {

            try {
                System.out.println(c+" has "+numberBought+" tickets for "+e+".");
                System.out.println("Please enter how many you would like to return, or press 'q' to cancel.");

                //Takes user input
                String choice = in.nextLine();
                if(choice.equals("q")) { //leaves while loop if q
                    valid = true;
                }else{
                    int numberToReturn = Integer.parseInt(choice);
                    if(numberToReturn==numberBought) { //If returning maximum amount of tickets, deletes arraylist
                        c.getTicketPurchases().remove(indexOfEvent);
                        e.increaseAvailableTickets(numberToReturn);
                        System.out.println("All tickets returned for "+e+".");
                        valid = true;
                    }else if(numberToReturn>=1 && numberToReturn<numberBought){ //If returning some not all tickets, removes those from ticketPurchases
                        c.getTicketPurchases().get(indexOfEvent).setNumberBought(numberBought-numberToReturn);
                        e.increaseAvailableTickets(numberToReturn);
                        System.out.println(numberToReturn+" tickets returned for "+e+".");
                        valid = true;
                    }else
                        System.out.println("Must return between 1 and " + numberBought + " tickets.");

                    }

            }catch(NumberFormatException ex) {
                System.out.println("Incorrect format. Please try again.");
            }
        }

        //return;
    }

    //Prints letter in case of not enough tickets for an event.
    private void printLetter(PrintWriter outFile, Client c, Event e){
        //Prints apology into letters.txt
        outFile.println("Dear "+c+
                    ",\nUnfortunately we only have "+e.getNumberTickets()+" remaining for "+e.getEventName()+
                    ".\nPlease accept our apologies and consider different options.\n");
    }

}