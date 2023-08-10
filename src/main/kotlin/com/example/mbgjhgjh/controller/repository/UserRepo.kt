package com.example.mbgjhgjh.controller.repository

import com.example.mbgjhgjh.controller.repository.dbmodel.DBModel
import org.springframework.data.repository.CrudRepository

interface UserRepo:CrudRepository<DBModel,String> {
    //JpaRepository

}