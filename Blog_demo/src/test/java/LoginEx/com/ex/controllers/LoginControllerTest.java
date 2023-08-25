package LoginEx.com.ex.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import LoginEx.com.ex.models.entity.UserEntity;
import LoginEx.com.ex.services.UserService;
import jakarta.servlet.http.HttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@BeforeEach
	public void prepareData() {
		UserEntity userEntity = new UserEntity("test@test.com", "Alice", "123456",new Timestamp(new Date().getTime()));
		when(userService.loginCheck(eq("test@test.com"),eq("123456"))).thenReturn(userEntity);
		when(userService.loginCheck(eq("c1234567"),eq("123456"))).thenReturn(null);
		when(userService.loginCheck(eq(""),eq("123456"))).thenReturn(null);
		when(userService.loginCheck(eq("test@test.com"),eq("12345aaaa"))).thenReturn(null);
		when(userService.loginCheck(eq("test@test.com"),eq(""))).thenReturn(null);
		when(userService.loginCheck(eq(""),eq(""))).thenReturn(null);
		when(userService.loginCheck(eq("c1234567"),eq("12345aaaa"))).thenReturn(null);
	}
	
	@Test
	public void testGetLoginPage_Succeed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.get("/login");
		mockMvc.perform(request).andExpect(view().name("login.html"));
	}
	
	@Test
	public void testLogin_Succeed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/login/process").param("email", "test@test.com").param("password", "123456");
		MvcResult result =mockMvc.perform(request).andExpect(redirectedUrl("/index")).andReturn();
		
		HttpSession session = result.getRequest().getSession();
		
		UserEntity userList = (UserEntity) session.getAttribute("user");
		
		assertNotNull(userList);
		assertEquals("Alice", userList.getuserName());
		assertEquals("test@test.com", userList.getemail());
		assertEquals("123456", userList.getPassword());
	}
	
	@Test
	public void testLogin_Unsucceed_WrongEmail() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/login/process").param("email", "c1234567").param("password", "123456");
		MvcResult result =mockMvc.perform(request).andExpect(redirectedUrl("/login")).andReturn();
		
		HttpSession session = result.getRequest().getSession();
		
		UserEntity userList = (UserEntity) session.getAttribute("user");
		
		assertNull(userList);
	}
	
	@Test
	public void testLogin_Unsucceed_BlankEmail() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/login/process").param("email", "").param("password", "123456");
		MvcResult result =mockMvc.perform(request).andExpect(redirectedUrl("/login")).andReturn();
		
		HttpSession session = result.getRequest().getSession();
		
		UserEntity userList = (UserEntity) session.getAttribute("user");
		
		assertNull(userList);
	}
	
	@Test
	public void testLogin_Unsucceed_WrongPassword() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/login/process").param("email", "test@test.com").param("password", "12345aaaa");
		MvcResult result =mockMvc.perform(request).andExpect(redirectedUrl("/login")).andReturn();
		
		HttpSession session = result.getRequest().getSession();
		
		UserEntity userList = (UserEntity) session.getAttribute("user");
		
		assertNull(userList);
	}
	
	@Test
	public void testLogin_Unsucceed_BlankPassword() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/login/process").param("email", "test@test.com").param("password", "");
		MvcResult result =mockMvc.perform(request).andExpect(redirectedUrl("/login")).andReturn();
		
		HttpSession session = result.getRequest().getSession();
		
		UserEntity userList = (UserEntity) session.getAttribute("user");
		
		assertNull(userList);
	}
	
	@Test
	public void testLogin_Unsucceed_BlankPasswordAndBlankEmail() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/login/process").param("email", "").param("password", "");
		MvcResult result =mockMvc.perform(request).andExpect(redirectedUrl("/login")).andReturn();
		
		HttpSession session = result.getRequest().getSession();
		
		UserEntity userList = (UserEntity) session.getAttribute("user");
		
		assertNull(userList);
	}
	
	@Test
	public void testLogin_Unsucceed_WrongPasswordAndWrongEmail() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/login/process").param("email", "c1234567").param("password", "12345aaaa");
		MvcResult result =mockMvc.perform(request).andExpect(redirectedUrl("/login")).andReturn();
		
		HttpSession session = result.getRequest().getSession();
		
		UserEntity userList = (UserEntity) session.getAttribute("user");
		
		assertNull(userList);
	}
	
	@Test
	public void testLogout() throws Exception{
		UserEntity userEntity = new UserEntity("test@test.com", "Alice", "123456",new Timestamp(new Date().getTime()));
	    HttpSession session = mockMvc.perform(MockMvcRequestBuilders.get("/login")).andReturn().getRequest().getSession();
	    session.setAttribute("user", userEntity);

	    
	    RequestBuilder request = MockMvcRequestBuilders.get("/logout");
	    mockMvc.perform(request).andExpect(redirectedUrl("/login"));

	    
	    
	}
	
}
