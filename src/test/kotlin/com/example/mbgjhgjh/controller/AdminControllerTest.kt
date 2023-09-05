import com.example.mbgjhgjh.controller.AdminController
import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.dtos.LoginDTO
import com.example.mbgjhgjh.dtos.UserDto
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.Transaction
import com.example.mbgjhgjh.models.convertToTransactionModel
import com.example.mbgjhgjh.services.CustomerService
import com.example.mbgjhgjh.services.TransactionService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.http.ResponseEntity
import java.util.*
import kotlin.collections.ArrayList

class AdminControllerTest {

    @Mock
    lateinit var transactionRepo: TransactionRepo

    @Mock
    lateinit var repository: UserRepo

    @Mock
    lateinit var loggedInRepo: LoggedInUserRepo

    lateinit var adminController: AdminController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val transactionService = Mockito.mock(TransactionService::class.java)
        val customerService = Mockito.mock(CustomerService::class.java)
        adminController = AdminController(transactionRepo, repository, loggedInRepo, "your-build-number")
        adminController.service = transactionService
        adminController.customerService = customerService
    }

//    @Test
//    fun testGetAllUsers() {
//        // Mock the behavior of isAdminLoggedIn() and the service method
//        Mockito.`when`(adminController.isAdminLoggedIn()).thenReturn(true)
//        val expectedResponse = listOf(UserDto()) // Initialize with expected data
//        Mockito.`when`(adminController.customerService.getAllCustomers()).thenReturn(expectedResponse)
//
//        // Perform the test
//        val result = adminController.getAllUsers()
//        println("---xxx-")
//        println("---xxx-"+result)
//
//        // Assert the result
//        assert(result == expectedResponse)
//    }


    @Test
    fun testLoginAsAdminX() {
        // Mock the behavior of loggedInRepo.count()
        Mockito.`when`(loggedInRepo.count()).thenReturn(0)

        // Test with valid admin credentials
        val validAdminRequest = LoginDTO()
        validAdminRequest.userName = "admin"
        validAdminRequest.password = "admin"
        val validAdminResponse = ResponseEntity.badRequest().body(Messager.PlainMessage("successfully logged in as Admin"))
        val resultValidAdmin = adminController.loginAsAdmin(validAdminRequest)

        // Assert the results
        assertEquals(validAdminResponse, resultValidAdmin)
    }




    @Test
    fun testReduceAmount() {
        // Mock the behavior of isAdminLoggedIn() and the service method
        Mockito.`when`(adminController.isAdminLoggedIn()).thenReturn(true)
        val request = Transaction("customerId", 100.0, Date()) // Initialize request
        val expectedResponse = TransactionService.TransactionerMessage(true, "Transaction Successful", 100.0)
        Mockito.`when`(adminController.service.reduceAmount(request)).thenReturn(expectedResponse)

        // Perform the test
        val result = adminController.reduceAmount(request)

        // Assert the result
        assert(result == expectedResponse)
    }

    @Test
    fun testIncreaseAmount() {
        // Mock the behavior of isAdminLoggedIn() and the service method
        Mockito.`when`(adminController.isAdminLoggedIn()).thenReturn(true)
        val request = Transaction("customerId", 100.0, Date()) // Initialize request
        val expectedResponse = TransactionService.TransactionerMessage(true, "Transaction Successful", 100.0)
        Mockito.`when`(adminController.service.increaseAmount(request)).thenReturn(expectedResponse)

        // Perform the test
        val result = adminController.increaseAmount(request)

        // Assert the result
        assert(result == expectedResponse)
    }

    @Test
    fun testGetAll() {
        // Mock the behavior of isAdminLoggedIn() and the service method
        Mockito.`when`(adminController.isAdminLoggedIn()).thenReturn(true)
        val expectedResponse = Messager.TransactionsMessage(10, 5, 1000.0, 500.0, 500.0) // Initialize with expected data
        Mockito.`when`(adminController.service.getAll()).thenReturn(expectedResponse)

        // Perform the test
        val result = adminController.getAll()

        // Assert the result
        assert(result == expectedResponse)
    }

    @Test
    fun testGetUserTransactions() {
        // Mock the behavior of isAdminLoggedIn() and the service method
        Mockito.`when`(adminController.isAdminLoggedIn()).thenReturn(true)
        val expectedUserTransactions = ArrayList<TransactionDb>() // Initialize with expected data
        Mockito.`when`(adminController.service.getUserTransactions("userName")).thenReturn(expectedUserTransactions)

        // Perform the test
        val result = adminController.getUserTransactions("userName")

        // Assert the result
        assert(result == expectedUserTransactions)
    }

    @Test
    fun testGetDetail() {
        // Mock the behavior of isAdminLoggedIn() and the service method
        Mockito.`when`(adminController.isAdminLoggedIn()).thenReturn(true)
        val expectedDetail = ArrayList<TransactionDb>() // Initialize with expected data
        Mockito.`when`(adminController.service.getDetail()).thenReturn(expectedDetail)

        // Perform the test
        val result = adminController.getDetail()

        // Assert the result
        assert(result == expectedDetail)
    }

    @Test
    fun testLoginAsAdmin() {
        // Mock the behavior of loggedInRepo.count()
        Mockito.`when`(loggedInRepo.count()).thenReturn(0)

        // Test with valid admin credentials
        val validAdminRequest = LoginDTO()
        validAdminRequest.userName="admin"
        validAdminRequest.password="admin"
        val validAdminResponse = ResponseEntity.badRequest().body(Messager.PlainMessage("successfully logged in as Admin"))
        val resultValidAdmin = adminController.loginAsAdmin(validAdminRequest)

        // Test with invalid credentials
        val invalidRequest = LoginDTO()
        invalidRequest.userName="invalid"
        invalidRequest.password="invalid"
        val invalidResponse = ResponseEntity.badRequest().body(Messager.PlainMessage("invalid combination of username and password!"))
        val resultInvalid = adminController.loginAsAdmin(invalidRequest)

        // Assert the results
        assert(resultValidAdmin == validAdminResponse)
        assert(resultInvalid == invalidResponse)
    }

    @Test
    fun testLogoutAsAdmin() {
        // Mock the behavior of loggedInRepo.count()
        Mockito.`when`(loggedInRepo.count()).thenReturn(1)

        // Perform the test
        val result = adminController.logoutAsAdmin()

        // Assert the result
        assert(result.statusCodeValue == 200)
        assert(result.body == Messager.PlainMessage("Admin successfully logout"))
    }

    @Test
    fun testIsAdminLoggedIn() {
        // Mock the behavior of loggedInRepo.count() and findById()
        Mockito.`when`(loggedInRepo.count()).thenReturn(1)
        val loggedInUser = Optional.of(LoggedInUserDb(2, "Admin"))
        Mockito.`when`(loggedInRepo.findById(2)).thenReturn(loggedInUser)

        // Test when admin is logged in
        val resultLoggedIn = adminController.isAdminLoggedIn()

        // Test when no admin is logged in
        Mockito.`when`(loggedInRepo.count()).thenReturn(0)
        val resultNotLoggedIn = adminController.isAdminLoggedIn()

        // Assert the results
        assert(resultLoggedIn)
        assert(!resultNotLoggedIn)
    }
}
