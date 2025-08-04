
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class GetTodayDate {
    public static void main(String[] args) {
        // Get today's date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todayf= today.format(formatter);
        System.out.println("Today's date is: " + todayf);
        today =today.plusMonths(1);
        String todayff= today.format(formatter);
        // Print the date
        System.out.println("Today's date is: " + todayff);
    }
}
