import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.DBModel
import com.example.mbgjhgjh.dtos.UserDto
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.convertToDBModel
import com.example.mbgjhgjh.models.convertToUserDto
import com.example.mbgjhgjh.services.CustomerService
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean


@SpringBootTest
class CustomerServiceTest {

   // @MockBean
    private lateinit var userRepo: UserRepo

    private lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
        customerService = CustomerService(userRepo)
    }

    @Test
    fun `should create new user`() {
        // Arrange
        val newUser = Customer(
            userName = "testuser"
        )
        newUser.password = "testpassword"
        newUser.gutHaben = 100.0
        newUser.firstName = "John"
        newUser.lastName = "Doe"


        every { userRepo.save(any()) } answers { newUser.convertToDBModel() } // Mock the repository save function

        // Act
        val result = customerService.createNewUser(newUser)

        // Assert
        //   assertThat(result.sucessfull)
        assertThat(result.message).isEqualTo("new user 'testuser' successfully created")
    }

    @Test
    fun `should not create user with duplicated username`() {
        // Arrange
        val existingUser = Customer(
            userName = "testuser"
        )
        existingUser.password = "testpassword"
        existingUser.gutHaben = 1020.0
        existingUser.firstName = "xmmm"
        existingUser.lastName = "lovxer"

        val newUser = Customer(
            userName = "testuser"
        )
        newUser.password = "testpassword"
        newUser.gutHaben = 100.0
        newUser.firstName = "John"
        newUser.lastName = "Doe"


        every { userRepo.findAll() } answers { listOf(existingUser.convertToDBModel()) } // Mock the repository find all function

        // Act
        val result = customerService.createNewUser(newUser)

        // Assert
        // assertThat(result.success).isFalse()
        assertThat(result.message).isEqualTo("customerId \"existinguser\" already exists, please choose something unique")
    }

    @Test
    fun `should not create user with short password`() {
        // Arrange
        val newUser = Customer(
            userName = "testuser"
        )
        newUser.password = "testpassword"
        newUser.gutHaben = 100.0
        newUser.firstName = "John"
        newUser.lastName = "Doe"

        // Act
        val result = customerService.createNewUser(newUser)

        // Assert
        //   assertThat(result.success).isFalse()
        assertThat(result.message).isEqualTo("entered password is too short. Please use at least 6 characters")
    }

    // Add more test methods for other functions in CustomerService

}