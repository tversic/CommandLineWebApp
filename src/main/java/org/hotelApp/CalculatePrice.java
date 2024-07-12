package org.hotelApp;

import org.hotelApp.Model.Room;
import org.hotelApp.Repos.RepoImpl.RoomRepoImpl;
import org.hotelApp.Repos.RepoInterfaces.RoomRepo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CalculatePrice {

    private static Connection databaseConnection;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a room number.");
            return;
        }
        String roomNumber = args[0];

        databaseConnection = AppGlobal.returnDatabaseConnection();
        RoomRepo repo = new RoomRepoImpl(databaseConnection);

        Room room = repo.findByRoomName(roomNumber);

        if (room != null) {
            BigDecimal price = calculatePrice(room);
            System.out.println("Total price for room " + roomNumber + ": $" + price);
        } else {
            System.out.println("Room not found: " + roomNumber);
        }

        AppGlobal.closeDatabaseConnection();
    }

    private static BigDecimal calculatePrice(Room room) {
        // Assuming checkin_date is stored in room object as java.util.Date or java.sql.Date
        // For simplicity, let's assume checkin_date is a java.sql.Date

        Date checkinDate = room.getCheckinDate();
        long checkinTime = checkinDate.getTime();
        long currentTime = System.currentTimeMillis();
        long millisecondsPerDay = 24 * 60 * 60 * 1000L; // Number of milliseconds in one day

        int numberOfDays = (int) ((currentTime - checkinTime) / millisecondsPerDay);

        if (numberOfDays < 1) {
            numberOfDays = 1; // Minimum charge for at least one day
        }

        BigDecimal pricePerDay = room.getPrice();
        BigDecimal totalPrice = pricePerDay.multiply(BigDecimal.valueOf(numberOfDays));

        return totalPrice;
    }
}
