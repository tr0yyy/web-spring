package com.fmi.springweb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    private long username;
    private long password;
    private String email;
    private String role;
    private long funds;
    private Date dateCreated;
    private Date lastRecordUpdate;
}
