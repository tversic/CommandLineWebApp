package org.hotelApp.Repos.RepoInterfaces;

import org.hotelApp.Model.Room;

import java.util.List;

public interface RoomRepo {
    void save(Room room);
    void update(Room room);
    Room findById(long id);
    Room findByRoomName(String roomName);
    List<Room> findCheckedInRooms();
    public List<Room> findFreeInRooms();
    List<Room> findAll();
}
