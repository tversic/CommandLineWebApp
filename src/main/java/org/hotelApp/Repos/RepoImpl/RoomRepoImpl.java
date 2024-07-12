package org.hotelApp.Repos.RepoImpl;


import org.hotelApp.Model.Room;
import org.hotelApp.Repos.RepoInterfaces.RoomRepo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepoImpl implements RoomRepo {
    private final Connection connection;

    public RoomRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Room room) {
        String insertSql = "INSERT INTO Room (room_name, is_checked_out, price, checkin_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            insertStatement.setString(1, room.getRoomName());
            insertStatement.setBoolean(2, room.getCheckedOut());
            insertStatement.setBigDecimal(3, room.getPrice());
            Date checkinDateSql = new Date(room.getCheckinDate().getTime());
            insertStatement.setDate(4, checkinDateSql);
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Room room) {
        String updateSql = "UPDATE Room SET room_name = ?,is_checked_out = ?, price = ?, checkin_date = ? WHERE id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            updateStatement.setString(1, room.getRoomName());
            updateStatement.setBoolean(2, room.getCheckedOut());
            updateStatement.setBigDecimal(3, room.getPrice());
            Date checkinDateSql = new Date(room.getCheckinDate().getTime());
            updateStatement.setDate(4, checkinDateSql);
            updateStatement.setLong(5, room.getId());
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Room findById(long id) {
        return null;
    }

    @Override
    public Room findByRoomName(String roomName) {
        String sql = "SELECT id, room_name, is_checked_out, price, checkin_date FROM Room WHERE room_name = ?";
        Room room = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "Room " + roomName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                room = new Room();
                room.setId(resultSet.getInt("id"));
                room.setRoomName(resultSet.getString("room_name"));
                room.setCheckedOut(resultSet.getBoolean("is_checked_out"));
                room.setPrice(resultSet.getBigDecimal("price"));
                room.setCheckinDate(resultSet.getDate("checkin_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return room;
    }


    @Override
    public List<Room> findCheckedInRooms() {
        List<Room> checkedInRooms = new ArrayList<>();

        String sql = "SELECT id, room_name, is_checked_out, price, checkin_date FROM Room WHERE is_checked_out = false";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getInt("id"));
                room.setRoomName(resultSet.getString("room_name"));
                room.setCheckedOut(resultSet.getBoolean("is_checked_out"));
                room.setPrice(resultSet.getBigDecimal("price"));
                room.setCheckinDate(resultSet.getDate("checkin_date"));

                checkedInRooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checkedInRooms;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT id, room_name, is_checked_out, price, checkin_date FROM Room";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getInt("id"));
                room.setRoomName(resultSet.getString("room_name"));
                room.setCheckedOut(resultSet.getBoolean("is_checked_out"));
                room.setPrice(resultSet.getBigDecimal("price"));
                room.setCheckinDate(resultSet.getDate("checkin_date"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

}