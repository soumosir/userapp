package com.soumosir.userapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soumosir.userapp.common.ValidationConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserPostRequest {
    @NotBlank(message= ValidationConstants.NAME_NOT_EMPTY)
    @Size(min = 3, message = ValidationConstants.NAME_TOO_SHORT)
    @Size(max = 200, message = ValidationConstants.NAME_TOO_LONG)
    private String name;

    @Pattern(regexp="^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",message=ValidationConstants.USERNAME_REGEX_ERROR)
    private String username;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    @Past(message=ValidationConstants.DATE_IN_FUTURE)
    @NotNull(message= ValidationConstants.DATE_OF_BIRTH_NOT_EMPTY)
    private LocalDate dateOfBirth;

    @NotBlank(message= ValidationConstants.ADDRESS_NOT_EMPTY)
    @Size(min = 5, message = ValidationConstants.ADDRESS_TOO_SHORT)
    @Size(max = 200, message = ValidationConstants.ADDRESS_TOO_LONG)
    private String address;
}
