package org.example.person;

import jakarta.persistence.*;

import java.util.Objects;



@Entity
@NamedQuery(name = Person.FIND_PERSON_BY_EMAIL, query = "SELECT p from Person p where p.email = :email")
@NamedQuery(name = Person.FIND_PERSON_BY_UUID, query = "SELECT p from Person p where p.person_uuid = :uuid")
@NamedQuery(name = Person.FIND_ALL_PERSONS, query = "SELECT p from Person p")
public class Person {


    public static final String FIND_ALL_PERSONS = "Person.finAll";
    public static final String FIND_PERSON_BY_EMAIL = "Person.findByEmail";

    public static final String FIND_PERSON_BY_UUID = "Person.findByUuid";

    @Id
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    private int id;

    private String person_uuid;
    private String surname;
    private String firstname;

    private String email;

    private String grade;


    public int getId() {
        return id;
    }



    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Person(String uuid, String surname, String firstname) {
        this(uuid, surname, firstname, "");
    }

    public Person(String uuid, String surname, String firstname, String email) {
        this.person_uuid = uuid;
        this.surname = surname;
        this.firstname = firstname;
        this.email = email;
    }

    public Person() {
    }
}
