package org.hotelApp;

import org.hotelApp.Model.Room;
import org.hotelApp.Repos.RepoImpl.RoomRepoImpl;
import org.hotelApp.Repos.RepoInterfaces.RoomRepo;

import java.sql.Connection;
import java.util.List;

public class ListAllFreeRooms {

    private static Connection databaseConnection;

    public static void main(String[] args) {
        databaseConnection = AppGlobal.returnDatabaseConnection();
        RoomRepo repo = new RoomRepoImpl(databaseConnection);

        List<Room> checkedInRooms = repo.findFreeInRooms();

        if (checkedInRooms.isEmpty()) {
            System.out.println("No rooms are currently free.");
        } else {
            System.out.println("Free Rooms:");
            for (Room room : checkedInRooms) {
                System.out.println("Room Number: " + room.getRoomName() + ", Price: $" + room.getPrice());
            }
        }

        AppGlobal.closeDatabaseConnection();
    }
}
