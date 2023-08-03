package com.example.mbgjhgjh.backend

import com.example.mbgjhgjh.controller.repository.Repository
import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.model.convertToCustomer
import com.example.mbgjhgjh.db.LiveDB
import com.example.mbgjhgjh.model.Customer
import com.example.mbgjhgjh.model.Transaction
import com.example.mbgjhgjh.model.convertToDBModel
import com.example.mbgjhgjh.model.convertToTransactionModel
import org.springframework.web.bind.annotation.*
import java.util.Date

@RestController
@RequestMapping("transaction")

class TransactionController(val repository: Repository, val transactionRepo: TransactionRepo) {
    @PostMapping("auszahlen")
    fun reduceAmount(@RequestBody request: Transaction): String {

        //   var transaction = Transaction(request.customerId, request.transactionValue)
        // transaction.susessfullTransaction()


        transactioner(request, false)

//        LiveDB.dabaseSaticObj.listOfTransaction.add(transaction)
//        transactionRepo.save(transaction.convertToDBModel())
//        if (transaction.status == false) return "failure due low currency"
        return "success"


    }

    @PostMapping("einzahlen")
    fun increaseAmount(@RequestBody request: Transaction): String {

        var transaction = Transaction(request.customerId, -request.transactionValue)

        var currentUser = repository.findById(request.customerId)
        // transaction.susessfullTransaction()

        transactioner(request, true)
//        repository.findById(request.customerId).map { customer ->
//            val updatedCustoemr: Customer = customer.convertToCustomer()
//            updatedCustoemr.gutHaben += request.transactionValue
//            repository.delete(customer)
//            repository.save(updatedCustoemr.convertToDBModel())
//
//        }


        //   LiveDB.dabaseSaticObj.listOfTransaction.add(transaction)


        //if (transaction.status == false) return "failure due low currency"
        return "success"

    }

//    @PutMapping("/gadgets/{id}")
//    fun updateGadgetById(@PathVariable("id") gadgetId: Long, @RequestBody gadget: Gadget): ResponseEntity<Gadget> {
//        return gadgetRepository.findById(gadgetId).map { gadgetDetails ->
//            val updatedGadget: Customer = gadgetDetails.copy(
//                gadgetCategory = gadget.gadgetCategory,
//                gadgetName = gadget.gadgetName,
//                gadgetPrice = gadget.gadgetPrice,
//                gagdetAvailability = gadget.gagdetAvailability
//            )
//            ResponseEntity(gadgetRepository.save(updatedGadget), HttpStatus.OK)
//        }.orElse(ResponseEntity<Gadget>(HttpStatus.INTERNAL_SERVER_ERROR))
//    }

    @GetMapping("/all")
    fun getAll(): String {

        // if (LiveDB.dabaseSaticObj.listOfCustomers.isNotEmpty())
        return repository.findAll().map { it.convertToCustomer() }

        var date = Date()
        transactionRepo.findAll().map { it. }
        if (transactionRepo.)
        return "heute hatten wir ${transactionRepo.findById()} Transaktionen und ${}gesamt betrag"
        return listOf(LiveDB.dabaseSaticObj.listOfTransaction)
        //catch here

    }


    fun transactioner(request: Transaction, istEinzahlen: Boolean) {
        repository.findById(request.customerId).map { customer ->
            val updatedCustoemr: Customer = customer.convertToCustomer()
            if (istEinzahlen)
                updatedCustoemr.gutHaben += request.transactionValue
            else updatedCustoemr.gutHaben -= request.transactionValue

            repository.delete(customer)
            repository.save(updatedCustoemr.convertToDBModel())
        }
        transactionRepo.save(request.convertToTransactionModel())

    }

}


