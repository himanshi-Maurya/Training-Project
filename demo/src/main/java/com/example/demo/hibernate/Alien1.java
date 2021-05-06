package com.example.demo.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="Alien1")
public class Alien1 {
    @Id
    @GeneratedValue(generator = "incrementator")
   @GenericGenerator(name="incrementator",strategy = "increment")
  //  @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer aid;
    @Column(name = "name")
    private String aname;

    public Alien1() {
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

}
