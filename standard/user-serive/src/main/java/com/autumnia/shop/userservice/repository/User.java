package com.autumnia.shop.userservice.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

//@JsonIgnoreProperties(value={"password"})
//@JsonFilter("UserInfo")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min=2, message = "이름은 2글자 이상 입력해 주세요.")
    private String name;

    @Past
    private Date createdAt;

    private String password;
    
    private String ssn;

    public User(int id, String name, Date createdAt, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.password = password;
        this.ssn = ssn;
    }


    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
