import java.util.ArrayList;

/** Purpose of class is to store information on Clients and their purchases.
 *
 *    REFERENCES:
 *
 *         compareTo method for use with a first and last name (1/1):
 *         1) Lecture material from CSC8012 module at Newcastle University | Lecture Slides 1
 *         Original Author - Dr Konrad Dabrowski
 *         Modifying Author - Oliver Presland
 *
 */

public class Client implements Comparable<Client> {
    private final String firstName;
    private final String lastName;
    private final SortedArrayList<TicketPurchases> ticketsArray;
    //double ArrayList <Event, tickets> for each client?

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ticketsArray=new SortedArrayList<>();
    }

    public void addTicketPurchases(TicketPurchases ticketPurchase){
        this.ticketsArray.add(ticketPurchase);
    }

    public ArrayList<TicketPurchases> getTicketPurchases(){
        return this.ticketsArray;
    }

    @Override
    public int compareTo(Client c) {
        int lnCmp = lastName.compareTo(c.lastName);
        if (lnCmp != 0) return lnCmp;
        int fnCmp = firstName.compareTo(c.firstName);
        if (fnCmp != 0) return fnCmp;
        return 0;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
