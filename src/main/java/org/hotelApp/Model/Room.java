package org.hotelApp.Model;

import java.math.BigDecimal;
import java.util.Date;

public class Room {
    long id;
    String roomName;
    Boolean isCheckedOut;
    BigDecimal price;
    Date checkinDate;

    public Room() {
    }

    public Room(long id, String roomName, Boolean isCheckedOut, BigDecimal price, Date checkinDate) {
        this.id = id;
        this.roomName = roomName;
        this.isCheckedOut = isCheckedOut;
        this.price = price;
        this.checkinDate = checkinDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Boolean getCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(Boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}

