import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.DBModel
import com.example.mbgjhgjh.dtos.UserDto
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.convertToDBModel
import com.example.mbgjhgjh.models.convertToUserDto
import com.example.mbgjhgjh.services.CustomerService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CustomerServiceTest {

    @Mock
    lateinit var userRepo: UserRepo

    lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        customerService = CustomerService()
        customerService.userRepo = userRepo
    }

    @Test
    fun testGetAllCustomers() {
        // Mock the behavior of userRepo.findAll()
        val user1 = Customer("user1")
        val user2 = Customer("user2")
        val userDtos = listOf(user1, user2).map { it.convertToUserDto() }
        Mockito.`when`(userRepo.findAll()).thenReturn(listOf(user1.convertToDBModel(), user2.convertToDBModel()))

        // Perform the test
        val result = customerService.getAllCustomers()

        // Assert the result
        assert(result == userDtos)
    }

    @Test
    fun testCreateNewUser() {
        // Mock the behavior of isUniqueUserName() and userRepo.save()
        Mockito.`when`(customerService.isUniqueUserName(Mockito.anyString())).thenReturn(true)
        val customer = Customer("newUser")
        customer.password = "password"
        val message = Messager.MessageWithStatus(true, "new user 'newUser' successfully created")
        Mockito.`when`(userRepo.save(Mockito.any(DBModel::class.java))).thenReturn(customer.convertToDBModel())

        // Perform the test
        val result = customerService.createNewUser(customer)

        // Assert the result
        assert(result == message)
    }

    @Test
    fun testFindByUserName1() {
        // Mock the behavior of userRepo.findByUserName()
        val customer = Customer("testUser")
        Mockito.`when`(userRepo.findByUserName("testUser")).thenReturn(customer.convertToDBModel())

        // Perform the test
        val result = customerService.findByUserName1("testUser")

        // Assert the result
        assert(result == customer)
    }

    @Test
    fun testFindByUserName() {
        // Mock the behavior of userRepo.findByUserName()
        val userDto = Customer("testUser")
        userDto.firstName = "John"
        userDto.lastName = "Doe"
        userDto.gutHaben = 0.0


        Mockito.`when`(userRepo.findByUserName("testUser")).thenReturn(userDto.convertToDBModel())

        // Perform the test
        val result = customerService.findByUserName("testUser")

        // Assert the result
        try {
            assert(result!!.userName == userDto.userName)
        } catch (exception: NumberFormatException) {
            print("its null")
        }
    }

    @Test
    fun testCounter() {
        // Mock the behavior of userRepo.count()
        Mockito.`when`(userRepo.count()).thenReturn(5)

        // Perform the test
        val result = customerService.counter()

        // Assert the result
        assert(result == CustomerService.UserCount(5))
    }

    @Test
    fun testEditUser() {
        // Mock the behavior of alreadyExistedUserPass() and userRepo.save()
        Mockito.`when`(customerService.alreadyExistedUserPass(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(false)
        val customer = Customer("existingUser")
        customer.password = "oldPassword"
        val message = Messager.MessageWithStatus(true, "user 'existingUser' successfully edited")
        Mockito.`when`(userRepo.save(Mockito.any(DBModel::class.java))).thenReturn(customer.convertToDBModel())

        // Perform the test
        val result = customerService.editUser(customer)

        // Assert the result
        assert(result == message)
    }

    @Test
    fun testIsUniqueUserName() {
        // Mock the behavior of userRepo.findAll()
        val user1 = Customer("user1")
        val user2 = Customer("user2")
        Mockito.`when`(userRepo.findAll()).thenReturn(listOf(user1.convertToDBModel(), user2.convertToDBModel()))

        // Perform the test
        val result1 = customerService.isUniqueUserName("user1")
        val result2 = customerService.isUniqueUserName("newUser")

        // Assert the results
        assert(!result1)
        assert(result2)
    }

    @Test
    fun testAlreadyExistedUserPass() {
        // Mock the behavior of userRepo.findAll()
        val user1 = Customer("user1")

        val user2 = Customer("user2")
        Mockito.`when`(userRepo.findAll()).thenReturn(listOf(user1.convertToDBModel(), user2.convertToDBModel()))

        // Perform the test
        val result1 = customerService.alreadyExistedUserPass("user1", "password1")
        val result2 = customerService.alreadyExistedUserPass("user2", "wrongPassword")

        // Assert the results
        assert(!result1)
        assert(result2)
    }
}
