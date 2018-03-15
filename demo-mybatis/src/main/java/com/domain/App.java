package com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Timestamp;

/**
 * Created by epcm on 2017/8/25.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class App {

    @Id
//    @Column(name="app_id")
    private int app_id;

//    @Column(name="app_name")
    private String app_name;

//    @Column(name="app_description")
    private String app_description;

//    @Column(name="created_at")
    private Timestamp created_at ;

//    @Column(name="modified_at")
    private Timestamp modified_at;

    @Transient
    private String owner;

}
