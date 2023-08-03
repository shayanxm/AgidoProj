package com.example.mbgjhgjh.controller.repository

import com.example.mbgjhgjh.controller.repository.model.DBModel
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface Repository:CrudRepository<DBModel,UUID> {

}