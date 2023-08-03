package com.example.mbgjhgjh.model

import com.example.mbgjhgjh.controller.UserManger
import com.example.mbgjhgjh.controller.repository.model.DBModel
import com.example.mbgjhgjh.controller.repository.model.TransactionDb


class Transaction(
    val customerId: String,
    val transactionValue: Double
) {
    var status: Boolean = false

    fun susessfullTransaction() {
        var foundedUser = UserManger.findCustomerById(customerId)
        if (foundedUser != null) {
            UserManger.removeCustomer(foundedUser)
        }
        if (foundedUser != null) {
            if (foundedUser.gutHaben -transactionValue<0){
                status=false
            }else{

                foundedUser.gutHaben -=transactionValue
                status=true
            }
        }
        if (foundedUser != null) {
            UserManger.createCustomer(foundedUser)
        }

    }

}
public fun Transaction.convertToDBModel() = TransactionDb(
    customerId = this.customerId,
    transactionValue = this.transactionValue,
    status = this.status,

)
