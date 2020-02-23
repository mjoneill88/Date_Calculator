
/**
 * This file contains the DateDriver class.  Here, the functionality of
 * the Date class is implemented in the main method.
 *
 * @author Matthew O'Neill
 * @version 3/25/19
 */
public class DateDriver
{
    /**
     * This method is the main method.  Here, the functionality of the
     * Date class is demonstrated.
     */
    public static void main(String[] args)
    {
        //Make a date object
        System.out.println("First we make a Date object");
        Date date1 = new Date(1,2,2019);
        System.out.println("And display it using the toString() method");
        System.out.println(date1.toString());
        System.out.println("------------------------------");
        
        System.out.println("Now let's see how many days are between a couple dates");
        //Using alternative String parameter constructor
        Date date2 = new Date("8/5/2019");
       
        //calculate days between
        System.out.print(date1.daysBetween(date2));
        System.out.print(" days from 1/2/2019 to 8/5/2019");
        
        //create new date object
        date2 = new Date(5,29,2034);
        System.out.print("\n" + date1.daysBetween(date2));
        System.out.print(" days from 1/2/2019 to 5/29/2034");
        
        System.out.println("\n------------------------------");
        System.out.println("Now let's add some days to 1/2/2019");
        //add days
        date1.addDays(50);
        System.out.println("New date after 50 days added: " + date1.addDays(50));
        System.out.println("New Date after 500 days: " + date1.addDays(500));
        System.out.println("Note, this calculation includes leap years");
        
        System.out.println("\n------------------------------");
        //subtract days
        System.out.println("Now let's subtract some days from 1/2/2019");
        System.out.println("New Date after 50 days subtracted: " + date1.subtractDays(50));
        System.out.println("New date after 700 days subtracted: " + date1.subtractDays(700));
        
        System.out.println("\n------------------------------");
        //equals method
        System.out.println("Now let's try the equals method");
        System.out.println("Comparing 1/2/2019 with 5/29/2034");
        System.out.println(date1.equals(date2));
        
        Date date3 = new Date(1,2,2019);
        System.out.println("Now comparing 1/2/2019 to 1/2/2019");
        System.out.println(date1.equals(date3));
        
        System.out.println("\n------------------------------");
        //compareTo method
        System.out.println("Now let's try the compareTo() method");
        System.out.println("Comparing 1/2/2019 with 5/29/2034");
        System.out.println(date1.compareTo(date2));
        System.out.println("Now comparing 1/2/2019 to 1/2/2019");
        System.out.println(date1.compareTo(date3));
        System.out.println("Now comparing 5/29/2019 to 1/2/2019");
        System.out.println(date2.compareTo(date3));
        
        System.out.println("\n------------------------------");
        //display dates in different formats
        System.out.println("Now let's display the date 1/2/2019 in a couple formats");
        System.out.println(date1.getDate('a'));
        System.out.println(date1.getDate('f'));

        
    }
}
