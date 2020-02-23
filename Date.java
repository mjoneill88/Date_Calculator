
import java.util.GregorianCalendar;
import java.util.*;

/**
 * This file contains the Date class.  It contains fields and methods
 * for calculating days between dates, adding days to a date, and 
 * subtracting days from a date.
 *
 * @author Matthew O'Neill
 * @version 3/25/19
 */
public class Date
{
    //fields for Date class
    private int month;
    private int day;
    private int year;
    
    //int representation of day of year, 1 to 365
    private int dayOfYear;
    
    /**
     * This no-args constructor uses the GregorianCalendar class to
     * set the Date object's fields to the current date
     */
    public Date()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        
        dayOfYear = calculateDayOfYearWithArray();
    }
    
    /**
     * This parameterized constructor sets the values of the Date object
     * to the given days and then calculates the dayOfYear.
     * @param int m -the month of the date
     * @param int d -the day of th date
     * @param int y -the year of the date
     */
    public Date(int m, int d, int y)
    {
        month = m;
        day = d;
        year = y;
       
        dayOfYear = calculateDayOfYearWithArray();
        
    }
    
    /**
     * This copy constructor makes another identical copy (hard copy)
     * of the refenence variable passed into the method.
     * @param Date otherDate - the reference to a date object
     */
    public Date(Date otherDate)
    {
        this.month = otherDate.month;
        this.day = otherDate.day;
        this.year = otherDate.year;
        
        this.dayOfYear = calculateDayOfYearWithArray();
    }
    
    /**
     * This constructor takes in a date String with "/" as a delimeter, then
     * uses the StringTokenizer class to parse the string into the Date
     * fields.
     * @param String stringDate - the String representation of the date
     */
    public Date(String stringDate)
    {   
        //make tokenizer object
        StringTokenizer tokenizedString = new StringTokenizer(stringDate, "/");
        
        //parse tokenized strings
        month = Integer.parseInt(tokenizedString.nextToken());
        day = Integer.parseInt(tokenizedString.nextToken());
        year = Integer.parseInt(tokenizedString.nextToken());
        
        dayOfYear = calculateDayOfYearWithArray();
        
    }
    
    /**
     * This method calculates the dayOfYear field in the Date class
     * @return int dayOfYear -int representation of the day of the year,
     * from 1 to 365
     */
    public int calculateDayOfYearWithArray()
    {   
        //first set dayOfYear to days in last month
        int dayOfYear = day;                    
        
        //now loop through each month and add the days
        int[] daysInMonthArray = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30};
        
        for(int i = month - 2; i >= 0; i-- ){
            dayOfYear += daysInMonthArray[i];
        }
        return dayOfYear;
    }
    /**
     * This method calculates the number of days in between this.Date and
     * another Date object passed into the method.
     * @param Date otherDate -the reference to another Date object
     * @return int daysBetween -number of days between 2 dates
     */
    public int daysBetween(Date otherDate)
    {
        boolean otherDateLarger = false;
        int wholeYearDays;
        int daysBetween = 0;
        
        //Determine which date is smaller and which is larger
        if(otherDate.year >= this.year){
            otherDateLarger = true;
        }
        else{
            if(otherDate.month >= this.month){
                otherDateLarger = true;
            }
            else{
                if(otherDate.day >= this.day){
                    otherDateLarger = true;
                }
                else{
                    otherDateLarger = false;
                }
            }
        }
        
        //calculate whole years in between two dates
        wholeYearDays = ((otherDate.year) - (this.year + 1)) * 365;
        
        //Calculate non-whole year days
        //assuming otherDate is larger
        if(otherDateLarger == true){
            daysBetween = (365 - this.dayOfYear) + wholeYearDays + otherDate.dayOfYear;
        }
        else{
            //assuming thisDateis larger
            daysBetween = (365 - otherDate.dayOfYear) + wholeYearDays + this.dayOfYear;
        }
        
        //Finally, do the leap year calculation
        daysBetween += this.calculateLeapYears(otherDate);
        
        return daysBetween;
    }
    
    /**
     * This method calculates the number of leap days on an interval of
     * two dates, this.Date and the Date reference passed into the method.
     * @param Date otherDate -one end of the interval
     * @return int leapDays -the number of leap days over the interval
     */
    public int calculateLeapYears(Date otherDate)
    {
        Date shorter;
        Date larger;
        
        int leapDays = 0;
        
        //Determine which date is smaller, and which is larger
        //Note the use of the copy constructor here
        if(otherDate.year < this.year){
            shorter = new Date(otherDate);
            larger = new Date(this);
        }
        else{
            shorter = new Date(this);
            larger = new Date(otherDate);
        }
        
        //make adjustment if range is above february 28th
        if(shorter.month > 2){
            shorter.year++;
        }

        //make adjustment if range is below february
        if(larger.month <= 2){
        larger.year--;
        }
        
        //Actually do leap year test over corrected interval
        for(int i = shorter.year; i <= larger.year; i++){
            if((i % 4 == 0 && i % 100 != 0) || i % 400 == 0){
                leapDays++;
            }
        }
        return leapDays;
    }
    
    /**
     * This method adds an int number of days to this.Date and returns
     * a reference to a new Date object of the correct date
     * @param int addedDays -number of days to be added
     * @return Date() -the new date object with the added days
     */
    public Date addDays(int addedDays){
        //local variables for storing new date
        int newMonth = 1;
        int newDay;
        int newYear;
        
        int leftOverDays = 0;
        
        //determine new year
        newYear = addedDays / 365;
        newYear += this.year;
        
        //determine how many non-whole-year daysto add
        leftOverDays = addedDays % 365;

        //Simple conversion if addition does not pass the year boundary
        if(leftOverDays + this.dayOfYear < 365){
            leftOverDays = leftOverDays + this.dayOfYear; 
        }
        //edge case if day falls exactly on the 31st of Dec
        else if(leftOverDays + this.dayOfYear == 365){
            newMonth = 1;
            newDay = 1;
            return new Date(newMonth, newDay,newYear+1);
        }
        else{
            //case where days pass the year boundary
            leftOverDays = (365 - leftOverDays);
            newYear++;
        }

        //Recreate the date using theis array technique 
        int[] daysInMonthArray = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        int i = 0;
        while(leftOverDays >= daysInMonthArray[i]){
            leftOverDays = leftOverDays - daysInMonthArray[i];
            newMonth++;
            i++;
            if(leftOverDays == 0){
                leftOverDays = 1;
                newMonth--;
            }
            
        }

        //call and perform leap year conversion
        Date needsLeapYears = new Date(newMonth, leftOverDays, newYear);
        
        int leapDays = this.calculateLeapYears(needsLeapYears);

        
        needsLeapYears = needsLeapYears.subtractDays(leapDays);
        
        return new Date(needsLeapYears);
        //return new Date(newMonth, leftOverDays, newYear);
    }
    
    /**
     * This method subtracts an int number of days to this.Date and returns
     * a reference to a new Date object of the correct date
     * @param int days -number of days to be subtracted
     * @return Date() -the new date object with the subtracted days 
     */
    public Date subtractDays(int days){
        
        //local variables for calculating new date
        boolean minusOneMonthFlag = false;
        int newMonth = 0;
        int newDay = 0;
        int newYear = this.year;
        
        //calculate non-whole-year days
        int leftOverDays = dayOfYear % 365;
        newDay = days % 365;
        

        //find new year
        newYear = newYear - (days / 365);

        //easy case where the year barrier is not passes
        if(dayOfYear - days > 0){
            leftOverDays = dayOfYear - days;
        }
        //special case
        else if(dayOfYear - days == 0){
            newYear--;
            newDay = 31;
            newMonth = 12;
            return new Date(newMonth, newDay, newYear);
        }
        //more difficult case where you cross the year barrier
        else{
            
            leftOverDays = 365 - (newDay - dayOfYear);
            newYear--;
            minusOneMonthFlag = true;
        }
        
        //recreate the final year using array
        int[] daysInMonthArray = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        int i = 0;
        while(leftOverDays >= daysInMonthArray[i] && i < 11){
            leftOverDays = leftOverDays - daysInMonthArray[i];
            newMonth++;
            i++;
        }
        newMonth++;

        
        //Below is code for implementing the leap year calculation
        //in the subtract method, but currently there is a stack
        //overflow recursion error that needs to be fixed
        
        //Date needsLeapYears = new Date(newMonth, leftOverDays, newYear);
        
        //int leapDays = this.calculateLeapYears(needsLeapYears);
        
        //problem
        //needsLeapYears = needsLeapYears.addDays(leapDays);
 
        //return new Date(needsLeapYears);
        
        return new Date(newMonth, leftOverDays, newYear);
    }
    
    /**
     * This method returns the dayOfYear field from the Date class
     * @return int this.dayOfYear - the dayOfYear field
     */
    public int getDayOfYear()
    {
        return dayOfYear;
    }
    
    /**
     * This method returns the day field from the Date class
     * @return int day -the day field
     */
    public int getDay(){
        return day;
    }
    
    /**
     * This method returns the year field from the Date class
     * @return int this.year - the year field
     */
    public int getYear()
    {
        return year;
    }
    
    /**
     * This method returns the month field from the Date class
     * @return int month -the month field
     */
    public int getMonth()
    {
        return month;
    }
    
    /**
     * This method returns a String representation of the Date object
     * @return String - the string representation of the date
     */
    public String toString(){
        return this.month + "/" + this.day + "/" + this.year;
    }
    
    /**
     * This method returns true if two dates are equal and false if two
     * dates are not equal, this.Date and another passed into the method
     * @param other - the other Date to compare to this.Date
     * @return boolean -true if equal, false if different
     */
    public boolean equals(Date other){
        //return boolean true if equal, false if not equal
        if(this.year == other.year && this.month == other.month && this.day == other.day){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * This method compares two dates, this.Date and another passed into the
     * method.  The method returns 0 if they are equal, 1 if this.Date is
     * larger and -1 is other.Date is larger
     * @param Date other -the other Date being compared
     * @return int - 0 if equal, 1 if this.Date larger, -1 if other.Date larger
     */
    public int compareTo(Date other){
        //if they are equal
        if(this.year == other.year && this.month == other.month && this.day == other.day){
            return 0;
        }
        else{
            //test years
            if(this.year != other.year){
                if(this.year > other.year){
                    return 1;
                }
                else{
                    return -1;
                }
            }
            //test months
            if(this.month != other.month){
                if(this.month > other.month){
                    return 1;
                }
                else{
                    return -1;
                }
            }
            //test days
            if(this.day != other.day){
                if(this.day > other.day){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        }
        return 0;
    }
    
    /**
     * This method returns a String representation of the Date.  With a
     * char 'a', the date will be formatted in the m/d/yyyy format, with
     * any other char (let's say 'f') as a parameter, the date will be
     * displayed in the Month day, YYYY format.
     * @param char format - format specifier
     * @return String -String representation of the date
     */
    public String getDate(char format){
        String monthLocal = "Wrong";
        //abridged format in "/" delimited form
         if(format == 'a'){
            return this.month + "/" + this.day + "/" + this.year;
        }
        else{
            //find month based on integer
            switch(this.month){
                case 1:
                    monthLocal = "January";
                    break;
                case 2:
                    monthLocal = "February";
                    break;
                case 3:
                    monthLocal = "March";
                    break;
                case 4:
                    monthLocal = "April";
                    break;
                case 5:
                    monthLocal = "May";
                    break;
                case 6:
                    monthLocal = "June";
                    break;
                case 7:
                    monthLocal = "July";
                    break;
                case 8:
                    monthLocal = "August";
                    break;
                case 9:
                    monthLocal = "September";
                    break;
                case 10:
                    monthLocal = "October";
                    break;
                case 11:
                    monthLocal = "November";
                    break;
                case 12:
                    monthLocal = "December";
                    break;
            }
            //return String of date
            return monthLocal + " " + this.day + ", " + this.year;
        }
    }
    
    /**
     * This method is another method for calculating the dayOfYear
     * field. Instead of using an Array to calculate, this method
     * uses a switch statement
     */
    public void calculateDayOfYear()
    {
        //This method does the exact same thing as the
        //calculateDayOfYearWithArray() method
        //set dayOfYear to days in last month
        dayOfYear = day;
        //use switch statement with no breaks to add days from all
        //previous months
        switch(month){
            case 12:
                //Days in November
                dayOfYear += 30;
            case 11:
                //Days in October
                dayOfYear += 31;
            case 10:
                //Days in September
                dayOfYear += 30;
            case 9:
                //Days in August
                dayOfYear += 31;
            case 8:
                //Days in July
                dayOfYear += 31;
            case 7:
                //Days in June
                dayOfYear += 30;
            case 6:
                //Days in May
                dayOfYear += 31;
            case 5:
                //Days in April
                dayOfYear += 30;
            case 4:
                // Days in March
                dayOfYear += 31;
            case 3:
                //Days in February
                dayOfYear += 28;
            case 2:
                //Days in January
                dayOfYear += 31;
        }
        
    }
}
