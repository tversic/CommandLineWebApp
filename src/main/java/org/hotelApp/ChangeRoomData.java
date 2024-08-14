package org.hotelApp;

import org.hotelApp.Model.Room;
import org.hotelApp.Repos.RepoImpl.RoomRepoImpl;
import org.hotelApp.Repos.RepoInterfaces.RoomRepo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ChangeRoomData {

    private static Connection databaseConnection;

    public static void main(String[] args) {
        changeRoomData(args);
    }

    private static void changeRoomData(String[] args) {
        databaseConnection = AppGlobal.returnDatabaseConnection();
        RoomRepo repo = new RoomRepoImpl(databaseConnection);

        System.out.println("CHANGE ROOM DATA STARTED!");
        List<Room> rooms = repo.findAll();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found in the database!");
            return;
        }

        // name, price, checkedOut, checkInDate
        // java
        if (args.length < 4) {
            System.out.println("Arguments list too short");
            return;
        }

        // get selected room with room id
        Room room = rooms.stream()
                .filter(_room -> _room.getId() == Long.parseLong(args[1]))
                .findFirst()
                .orElse(null);
        if (room != null)
            for (int i = 2; i < args.length; i+=2) {
                try {
                    switch (args[i]) {
                        case "price":
                            room.setPrice(BigDecimal.valueOf(Long.parseLong(args[i+1])));
                            break;
                        case "name":
                            room.setRoomName("Room " + args[i+1]);
                            break;
                        case "checkedOut":
                            room.setCheckedOut(Boolean.valueOf(args[i+1]));
                            break;
                        case "checkInDate":
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            room.setCheckinDate(sdf.parse(args[i+1]));
                            break;
                        default:
                            System.out.println("Argument " + args[i] + " is not accepted argument please use arguments: name, price, checkedOut, checkInDate!");
                    }
                } catch (Exception e) {
                    System.out.println("Argument " + args[i] + " has wrong argument " + args[i+1] + " and will be skipped!");
                }

            }
        else {
            System.out.println("There is no room under requested id " + args[1]);
            return;
        }

        repo.update(room);
        System.out.println("Room data updated successfully!");
    }

}
