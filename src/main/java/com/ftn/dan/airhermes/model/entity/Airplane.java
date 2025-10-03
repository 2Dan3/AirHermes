package com.ftn.dan.airhermes.model.entity;

public class Airplane {
    private Long id;
    private String name;
    private int seatRows;
    private int seatColumns;

    public Airplane(Long airplane_id, String airplane_name, Integer seat_rows, Integer seat_columns) {
        this.id = airplane_id;
        this.name = airplane_name;
        this.seatRows = seat_rows;
        this.seatColumns = seat_columns;
    }
    public Airplane(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(int seatRows) {
        this.seatRows = seatRows;
    }

    public int getSeatColumns() {
        return seatColumns;
    }

    public void setSeatColumns(int seatColumns) {
        this.seatColumns = seatColumns;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result + ((id == null) ? 0 : id.hashCode());
        return 31 + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Airplane other = (Airplane) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seatRows=" + seatRows +
                ", seatColumns=" + seatColumns +
                '}';
    }
}
