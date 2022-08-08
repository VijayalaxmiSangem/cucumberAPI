package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Getter@Setter
public class User {
    private String name;

    private int id;
    private String gender;
    private String email;
    private String status;

     public User(){

     }
    public User(String name, String gender, String email, String status){
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.status = status;
    }


}

