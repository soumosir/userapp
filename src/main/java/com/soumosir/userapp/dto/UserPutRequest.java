package com.soumosir.userapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soumosir.userapp.common.ValidationConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserPutRequest {

    private Optional<
            @NotBlank(message= ValidationConstants.NAME_NOT_EMPTY)
            @Size(min = 3, message = ValidationConstants.NAME_TOO_SHORT)
            @Size(max = 200, message = ValidationConstants.NAME_TOO_LONG)
                    String> name;


    private Optional<
            @Pattern(regexp="^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",message=ValidationConstants.USERNAME_REGEX_ERROR)
                    String> username;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Optional<
            @NotNull(message= ValidationConstants.DATE_OF_BIRTH_NOT_EMPTY)
            @Past(message=ValidationConstants.DATE_IN_FUTURE)
        LocalDate> dateOfBirth;

    private Optional<
            @NotBlank(message= ValidationConstants.ADDRESS_NOT_EMPTY)
            @Size(min = 2, message = ValidationConstants.ADDRESS_TOO_SHORT)
            @Size(max = 200, message = ValidationConstants.ADDRESS_TOO_LONG)
        String> address;
}
