package LoginEx.com.ex.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import LoginEx.com.ex.models.entity.EmailVerificationEntity;
import LoginEx.com.ex.models.entity.UserEntity;
import LoginEx.com.ex.services.EmailVerificationService;
import LoginEx.com.ex.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
    private EmailVerificationService emailVerificationService;
	
	@BeforeEach
	public void prepareData() {
		UserEntity userEntity = new UserEntity("test@test.com", "test", "123456",new Timestamp(new Date().getTime()));
		when(userService.createUser(eq("2543868527@qq.com"), eq("Alice"), eq("123456"))).thenReturn(true);
		when(userService.createUser(eq("2543868527@qq.com"), eq("test"), eq("123456"))).thenReturn(false);
		when(userService.createUser(eq("2543868527@qq.com"), eq(""), eq("123456"))).thenReturn(false);
		when(userService.createUser(eq("test@test.com"), eq("Alice"), eq("123456"))).thenReturn(false);
		when(userService.createUser(eq(""), eq("Alice"), eq("123456"))).thenReturn(false);
		when(userService.createUser(eq(""), eq(""), eq("123456"))).thenReturn(false);
		when(userService.createUser(eq("2543868527@qq.com"), eq("Alice"), eq(""))).thenReturn(false);
		when(userService.createUser(eq(""), eq("Alice"), eq(""))).thenReturn(false);
		when(userService.createUser(eq(""), eq(""), eq(""))).thenReturn(false);
		
	}
		
	@Test
	public void testGetRegisterPage_Succeed()throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/register");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}
	
	
	
	
	
	
	
	
	@Test
	public void testRegister_Succeed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/register/process").param("email", "2543868527@qq.com").param("userName","Alice").param("password", "123456");
		mockMvc.perform(request).andExpect(view().name("login.html"));
		
	}
	
	
	@Test
	public void testRegister_ExistUsername_Unsucceed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/register/process").param("userName","test").param("password", "123456").param("email", "2543868527@qq.com");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}
	
	@Test
	public void testRegister_BlankUsername_Unsucceed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/register/process").param("userName","").param("password", "123456").param("email", "2543868527@qq.com");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}
	
	@Test
	public void testRegister_WrongEmail_Unsucceed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/register/process").param("userName","Alice").param("password", "123456").param("email", "test@test.com");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}
	
	@Test
	public void testRegister_BlankEmail_Unsucceed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/register/process").param("userName","Alice").param("password", "123456").param("email", "");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}
	
	@Test
	public void testRegister_BlankEmailAndName_Unsucceed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/register/process").param("userName","").param("password", "123456").param("email", "");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}
	
	@Test
	public void testRegister_BlankPassword_Unsucceed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/register/process").param("userName","Alice").param("password", "").param("email", "2543868527@qq.com");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}
	
	@Test
	public void testRegister_BlankEmailAndPassword_Unsucceed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/register/process").param("userName","Alice").param("password", "").param("email", "");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}
	
	@Test
	public void testRegister_BlankEmailAndPasswordAndName_Unsucceed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/register/process").param("userName","").param("password", "").param("email", "");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}
	
	@Test
    public void testSendVerificationCode_Success() throws Exception {
        String email = "test@example.com";
        String verificationCode = "123456";

        EmailVerificationEntity mockVerificationEntity = new EmailVerificationEntity();
        mockVerificationEntity.setVerificationCode(verificationCode);
        when(emailVerificationService.generateVerificationCode(email)).thenReturn(mockVerificationEntity);

        mockMvc.perform(post("/register/sendCode").param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(emailVerificationService, times(1)).sendVerificationCodeEmail(email, verificationCode);
    }

    @Test
    public void testSendVerificationCode_Error() throws Exception {
        String email = "test@example.com";

        doThrow(new RuntimeException("Test exception")).when(emailVerificationService).generateVerificationCode(email);

        mockMvc.perform(post("/register/sendCode").param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("error"));

        verify(emailVerificationService, never()).sendVerificationCodeEmail(anyString(), anyString());
    }
	
}
