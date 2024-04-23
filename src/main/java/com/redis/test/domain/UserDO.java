package com.redis.test.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user_data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}
