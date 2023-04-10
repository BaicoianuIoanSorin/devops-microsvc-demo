package dk.via.doc1.microserviceuser.model;

import java.util.Objects;

public class Entry {
    private Long id = 0L;

    String data = "invalid entry";

    public Entry()
    {
    }

    public Entry(String data)
    {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", data='" + data + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry entry)) return false;
        return id.equals(entry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
