package org.hotelApp;

import org.hotelApp.Model.Room;
import org.hotelApp.Repos.RepoImpl.RoomRepoImpl;
import org.hotelApp.Repos.RepoInterfaces.RoomRepo;

import java.sql.Connection;
import java.util.List;

public class ListAllRoomsInfo {

    public static void main(String[] args) {
        // Assuming you have a way to establish the database connection
        Connection databaseConnection = AppGlobal.returnDatabaseConnection();

        // Initialize RoomRepo
        RoomRepo roomRepo = new RoomRepoImpl(databaseConnection);

        // Call method to list all rooms and their information
        listAllRoomsInfo(roomRepo);

        // Close database connection
        AppGlobal.closeDatabaseConnection();
    }

    public static void listAllRoomsInfo(RoomRepo roomRepo) {
        List<Room> rooms = roomRepo.findAll();

        if (rooms.isEmpty()) {
            System.out.println("No rooms found in the database.");
        } else {
            System.out.println("Listing all rooms:");

            for (Room room : rooms) {
                System.out.println("Room ID: " + room.getId());
                System.out.println("Room Name: " + room.getRoomName());
                System.out.println("Checked Out: " + room.getCheckedOut());
                System.out.println("Price: " + room.getPrice());
                System.out.println("Check-in Date: " + room.getCheckinDate());
                System.out.println();
            }
        }
    }
}
