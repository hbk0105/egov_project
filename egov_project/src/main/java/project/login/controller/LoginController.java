package project.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	Logger log = Logger.getLogger(this.getClass());
	// https://debugdaldal.tistory.com/89?category=925275
	
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
	
	@RequestMapping(value="/logout")
    public String pageLogin(HttpServletRequest request, HttpSession session){
           session.invalidate();
           return "login/login";
     }

	@GetMapping("/member/test")
	public void doMember() {
		log.info("logined member");
	}
	
	@GetMapping("/admin/test")
	public void doAdmin() {
		log.info("admin only");
	}
	
	@GetMapping("/accessError") 
	public String accessDenied(Authentication auth, Model model) { 
		log.info("access Denied : "+ auth);
		model.addAttribute("msg", "접근권한이 없습니다."); 
		
		return "login/accessError";
	}

	
}
