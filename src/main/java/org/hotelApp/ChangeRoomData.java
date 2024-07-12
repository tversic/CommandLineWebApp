package org.hotelApp;

import org.hotelApp.Model.Room;
import org.hotelApp.Repos.RepoImpl.RoomRepoImpl;
import org.hotelApp.Repos.RepoInterfaces.RoomRepo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class ChangeRoomData {

    private static Connection databaseConnection;

    public static void main(String[] args) {
        changeRoomData();
    }

    private static void changeRoomData() {
        databaseConnection = AppGlobal.returnDatabaseConnection();
        RoomRepo repo = new RoomRepoImpl(databaseConnection);

        List<Room> rooms = repo.findAll();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found in the database!");
            return;
        }

        // List all rooms with an index
        System.out.println("List of all rooms:");
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            System.out.println(i + 1 + ") Room Name: " + room.getRoomName() + ", Checked Out: " + room.getCheckedOut() + ", Price: " + room.getPrice() + ", Check-in Date: " + room.getCheckinDate());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the index of the room you want to change:");

        String roomIndexInput = scanner.nextLine();
        if (!isValidNumber(roomIndexInput)) {
            System.out.println("Entered value is not a valid number, please try again");
            return;
        }

        int roomIndex = Integer.parseInt(roomIndexInput);
        if (roomIndex < 1 || roomIndex > rooms.size()) {
            System.out.println("Entered number is out of range, please try again");
            return;
        }

        Room room = rooms.get(roomIndex - 1);

        System.out.println("Room selected > Room Name: " + room.getRoomName() + ", Checked Out: " + room.getCheckedOut() + ", Price: " + room.getPrice() + ", Check-in Date: " + room.getCheckinDate());

        System.out.println("Enter data number which you want to change:");
        System.out.println("1) Room name");
        System.out.println("2) Checked Out");
        System.out.println("3) Price");
        System.out.println("4) Check-in Date");

        String input = scanner.nextLine();

        if (!isValidNumber(input)) {
            System.out.println("Entered value is not a valid number, please try again");
            return;
        }

        int option = Integer.parseInt(input);

        switch (option) {
            case 1:
                System.out.println("Room selected > Room Name: " + room.getRoomName());
                System.out.println("Data selected to edit > Room name");
                System.out.println("Enter new room name:");
                String newName = scanner.nextLine();
                room.setRoomName(newName);
                break;
            case 2:
                System.out.println("Room selected > Checked Out: " + room.getCheckedOut());
                System.out.println("Data selected to edit > Checked Out");
                System.out.println("Enter new checked out status (true/false):");
                String newStatus = scanner.nextLine();
                if (!newStatus.equalsIgnoreCase("true") && !newStatus.equalsIgnoreCase("false")) {
                    System.out.println("Invalid value for checked out status, please enter true or false");
                    return;
                }
                room.setCheckedOut(Boolean.parseBoolean(newStatus));
                break;
            case 3:
                System.out.println("Room selected > Price: " + room.getPrice());
                System.out.println("Data selected to edit > Price");
                System.out.println("Enter new price:");
                String newPrice = scanner.nextLine();
                if (!isValidBigDecimal(newPrice)) {
                    System.out.println("Invalid value for price, please enter a valid number");
                    return;
                }
                room.setPrice(new BigDecimal(newPrice));
                break;
            case 4:
                System.out.println("Room selected > Check-in Date: " + room.getCheckinDate());
                System.out.println("Data selected to edit > Check-in Date");
                System.out.println("Enter new check-in date (yyyy-mm-dd):");
                String newDate = scanner.nextLine();
                if (!isValidDate(newDate)) {
                    System.out.println("Invalid date format, please enter date in yyyy-mm-dd format");
                    return;
                }
                room.setCheckinDate(Date.valueOf(newDate));
                break;
            default:
                System.out.println("Entered number is out of range, please try again");
                return;
        }

        repo.update(room);
        System.out.println("Room data updated successfully!");
    }

    private static boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidBigDecimal(String input) {
        try {
            new BigDecimal(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidDate(String input) {
        try {
            Date.valueOf(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
