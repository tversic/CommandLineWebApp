package org.hotelApp;

import org.hotelApp.Model.Room;
import org.hotelApp.Repos.RepoImpl.RoomRepoImpl;
import org.hotelApp.Repos.RepoInterfaces.RoomRepo;

import java.sql.Connection;
import java.util.Arrays;

public class CheckOut {

    private static Connection databaseConnection;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Niste odabrali sobu za check out, za nastavak molimo odaberite broj sobe!");
            return;
        }
        if (checkRoomNoFormat(args)) return;
        boolean checkinResult = checkOut(args);
        if (checkinResult)
            System.out.println("CHECKOUT IN USPJESAN");
        else
            System.out.println("CHECKOUT IN NEUSPJESAN");
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

    private static boolean checkOut(String[] roomNo) {

        databaseConnection = AppGlobal.returnDatabaseConnection();
        RoomRepo repo = new RoomRepoImpl(databaseConnection);
        Room checkOutRoom = repo.findByRoomName(roomNo[0]);

        System.out.println("Korisnik trazi checkout iz sobe: " + Arrays.toString(roomNo));
        if (checkOutRoom == null) {
            System.out.println("Soba koju ste unjeli za checkout ne postoji!");
            return false;
        }
        if (checkOutRoom.getCheckedOut()) {
            checkOutRoom.setCheckedOut(false);
            System.out.println("Room " + roomNo[0] + " is successfully checked out!");
            repo.update(checkOutRoom);
            return true;
        }
        System.out.println("Soba koju ste odabrali za check out je vec check outa-ana!");
        return false;
    }
}
