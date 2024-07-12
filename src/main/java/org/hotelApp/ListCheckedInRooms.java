package org.hotelApp;

import org.hotelApp.Model.Room;
import org.hotelApp.Repos.RepoImpl.RoomRepoImpl;
import org.hotelApp.Repos.RepoInterfaces.RoomRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListCheckedInRooms {

    private static Connection databaseConnection;

    public static void main(String[] args) {
        databaseConnection = AppGlobal.returnDatabaseConnection();
        RoomRepo repo = new RoomRepoImpl(databaseConnection);

        List<Room> checkedInRooms = repo.findCheckedInRooms();

        if (checkedInRooms.isEmpty()) {
            System.out.println("No rooms are currently checked in.");
        } else {
            System.out.println("Checked-in Rooms:");
            for (Room room : checkedInRooms) {
                System.out.println("Room Number: " + room.getRoomName() + ", Price: $" + room.getPrice());
            }
        }

        AppGlobal.closeDatabaseConnection();
    }
}
