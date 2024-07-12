package org.hotelApp;

import org.hotelApp.Model.Room;
import org.hotelApp.Repos.RepoImpl.RoomRepoImpl;
import org.hotelApp.Repos.RepoInterfaces.RoomRepo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

// This is app for checkin
public class CheckIn {
    private static Connection databaseConnection;

    public static void main(String[] args) {
        doCheckIn(args);
    }

    private static void doCheckIn(String[] args) {
        databaseConnection = AppGlobal.returnDatabaseConnection();
        RoomRepo repo = new RoomRepoImpl(databaseConnection);
        Room checkinRoom = repo.findByRoomName(args[0]);

        // Create a new room object
        if (args.length == 0) {
            System.out.println("Room number empty, please select room number to finish check in!");
        }
        if (checkRoomNoFormat(args)) return;

        if (checkinRoom != null)
            if (doCheckInAction(repo, checkinRoom)) return;
        saveNewRoom(args, repo);

         // Statistics
        int noOfRooms = returnNoOfRooms();
        System.out.println("NUMBER OF ROOMS IN DATABASE " + noOfRooms + " " + Arrays.toString(args));

    }

    private static boolean doCheckInAction(RoomRepo repo, Room checkinRoom) {
        System.out.println("do checkin action");
        if (checkinRoom.getCheckedOut()) {
            System.out.println("Room already checked out, please select another room!");
            return true;
        }
        checkinRoom.setCheckedOut(true);
        repo.update(checkinRoom);
        return false;
    }

    private static void saveNewRoom(String[] args, RoomRepo repo) {
        System.out.println("SAVING NEW ROOM!");
        Room room = new Room();
        room.setRoomName("Room " + args[0]);
        room.setCheckedOut(true);
        room.setPrice(BigDecimal.valueOf(100.00));
        room.setCheckinDate(new Date(System.currentTimeMillis()));
        repo.save(room);
    }

    private static int returnNoOfRooms() {
        if (databaseConnection != null) {
            try {
                System.out.println("Connected to the database!");

                // Query to get the count of rooms
                String query = "SELECT COUNT(*) FROM Room";
                try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            } finally {
                // Close the database connection
                AppGlobal.closeDatabaseConnection();
            }
        } else {
            return 0;
        }
        return 0;
    }

    private static boolean checkRoomNoFormat(String[] args) {
        try {
            int roomNo = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Entered room number/argument is not number, please try again");
            return true;
        }
        return false;
    }
}

