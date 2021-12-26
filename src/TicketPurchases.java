/** Purpose of class is to store information on purchased tickets.
 */

public class TicketPurchases implements Comparable<TicketPurchases> {

    private final Event event;
    private int numberBought;

    public TicketPurchases(Event event, int numberBought) {
        this.event = event;
        this.numberBought = numberBought;
    }

    @Override
    public String toString() {
        return numberBought + " tickets purchased for " + event;
    }

    //compareTo override. TicketPurchases sorted into alphabetical order when added to SortedArrayList.
    public int compareTo(TicketPurchases t) {
        int eventComp = event.compareTo(t.getEvent());
        if (eventComp != 0) return eventComp;
        return 0;
    }

    public Event getEvent(){
        return this.event;
    }
    public int getNumberBought(){
        return this.numberBought;
    }

    public void setNumberBought(int i){
        this.numberBought=i;
    }
}