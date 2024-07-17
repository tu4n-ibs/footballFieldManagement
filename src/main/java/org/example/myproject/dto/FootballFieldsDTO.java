package org.example.myproject.dto;

public class FootballFieldsDTO {
    private String name;
    private String location;
    private String typeFields;


    public FootballFieldsDTO(String name, String location, String typeFields) {
        this.name = name;
        this.location = location;
        this.typeFields = typeFields;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTypeFields() {
        return typeFields;
    }

    public void setTypeFields(String typeFields) {
        this.typeFields = typeFields;
    }

}
