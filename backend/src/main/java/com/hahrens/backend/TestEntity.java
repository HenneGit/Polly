package com.hahrens.backend;


import jakarta.persistence.*;

@Entity
@Table(name="tb_user")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name ="name")
    private String name;

    @Column(name ="firstName")
    private String firstName;


    public TestEntity() {
    }

    public TestEntity(Long id, String name, String firstName) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
    }

    public TestEntity(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }

    public void setId(Long primaryKey) {
        this.id = primaryKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }
}
