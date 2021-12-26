/** Purpose of class is to store information on Events and keep a track of available tickets.
 */

public class Event implements Comparable<Event>  {
    private final String eventName;
    private int numberTickets;

    public Event(String eventName, int numberTickets) {
        this.eventName = eventName;
        this.numberTickets = numberTickets;
    }

    /*
    Getters and setters.
    Note that reduce and increase methods used instead of setters as this functionality was more important to the program.
     */
    public String getEventName(){
        return this.eventName;
    }
    public int getNumberTickets(){
        return this.numberTickets;
    }
    public void reduceAvailableTickets(int n){
        this.numberTickets = this.numberTickets - n;
    }
    public void increaseAvailableTickets(int n){
        this.numberTickets = this.numberTickets + n;
    }

    //compareTo override. Events sorted into alphabetical order when added to SortedArrayList.
    public int compareTo(Event e) {
        int eventComp = eventName.compareTo(e.eventName);
        if (eventComp != 0) return eventComp;
        return 0;
    }

    //toString override. Just eventName displayed when Event is printed.
    @Override
    public String toString() {
        return eventName;
    }
}
