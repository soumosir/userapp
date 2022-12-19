package com.soumosir.userapp.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.soumosir.userapp.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data

public class UserRestResponse {

    private String name;
    private String username;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;
    private String address;

    public UserRestResponse(User user){
        this.name = user.getName();
        this.username = user.getUsername();
        this.dateOfBirth = user.getDateOfBirth();
        this.address = user.getAddress();
    }

}


