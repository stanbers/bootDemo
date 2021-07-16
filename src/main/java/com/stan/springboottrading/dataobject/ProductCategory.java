package com.stan.springboottrading.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@DynamicUpdate
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer categoryId;
    private String categoryName;
    private Integer categoryType;
//    private Date createTime;
//    private Date updateTime;

    //command + N show get/set

}
