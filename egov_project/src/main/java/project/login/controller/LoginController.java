package project.login.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	Logger log = Logger.getLogger(this.getClass());
	// https://debugdaldal.tistory.com/89?category=925275
	
// 페이스북 oAuth 관련
    @Autowired
    private FacebookConnectionFactory connectionFactory;
    @Autowired
    private OAuth2Parameters oAuth2Parameters;
	
    // 사용자가 입력한 정보로부터 POST 요청은 Spring Security를 거친 후 해당 메서드로 들어온다.
	@RequestMapping("/customLogin")
    public String login(@RequestParam(value="error", required=false) String error, 
                        @RequestParam(value="logout", required=false) String logout, 
                        Model model) {
    	
    	log.info("error : " + error); 
    	log.info("logout : " + logout);

    	
        if(error != null) {
            model.addAttribute("errorMsg", "Invalid username and password");
        }
        if(logout != null) {
            model.addAttribute("logoutMsg", "You have been logged out successfully");
        }
        return "login/login"; // login.jsp(Custom Login Page)
    }    
	
	

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loout(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){ 
			//HttpSession session = request.getSession();
			//session.invalidate();
			new SecurityContextLogoutHandler().logout(request, response, auth); 
		} 
		return "redirect:/customLogin.do";
	} 

	@GetMapping("/member/test")
	public void doMember() {
		log.info("logined member");
	}
	
	@GetMapping("/admin/test")
	public void doAdmin() {
		log.info("admin only");
	}
	
	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	public void errorTest(HttpServletRequest request, Model model) throws Exception {
        throw new Exception("test.do에서 에러발생");    
    }


	
	@GetMapping("/accessError") 
	public String accessDenied(Authentication auth, Model model) { 
		log.info("access Denied : "+ auth);
		model.addAttribute("msg", "접근권한이 없습니다."); 
		
		return "login/accessError";
	}

}
