package com.autumnia.shop.userservice.domain.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    @Size(min=2, message = "이믈은 2글자 이상 입력해 주세요.")
    private String name;

    @Past
    private Date createdAt;

    private String password;

    private String ssn;
}
