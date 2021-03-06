package project.login.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import egovframework.interceptor.CommandMap;
import net.minidev.json.JSONObject;
import project.util.JsonUtil;

//import com.nhncorp.lucy.security.xss.XssPreventer;

@Controller
public class LoginController {
	
	Logger log = Logger.getLogger(this.getClass());
	// https://debugdaldal.tistory.com/89?category=925275
	
    // 사용자가 입력한 정보로부터 POST 요청은 Spring Security를 거친 후 해당 메서드로 들어온다.
	@RequestMapping("/customLogin")
    public String login(@RequestParam(value="error", required=false) String error, 
                        @RequestParam(value="logout", required=false) String logout, 
                        Model model , CommandMap commandMap) {
    	
		// https://to-dy.tistory.com/60
		if(commandMap.isEmpty() == false){
            Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
            Entry<String,Object> entry = null;
            while(iterator.hasNext()){
                entry = iterator.next();
                System.out.println("key : "+entry.getKey()+", value : "+entry.getValue());
            }
        }
		
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/updateUser.do", method = {RequestMethod.GET, RequestMethod.POST})
	//@RequestBody를 적어야 Map으로 값을 받아올 수 있다.
	public @ResponseBody String  updateUser( @RequestParam Map<String, Object> map){
		log.info( map );
		System.out.println("zzzz " + map.get("data"));
		// 구글의 json paser 라이브러리
		/*JSONObject obj = JsonUtil.getJsonStringFromMap(map);
		System.out.println(obj);
		System.out.println(obj.get("data"));*/
		
		/*ObjectMapper mapper = new ObjectMapper();
		Map<String, String> m;
		try {
			m = mapper.readValue(map.get("data").toString(), Map.class);
			System.out.println("####  m :: " + m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return map.get("data").toString();
	}
	
	// https://hailey0.tistory.com/39
	public static void main(String[] args) {
		
		/*String dirty = "\"><script>alert('xss');</script>"; 
		String clean = XssPreventer.escape(dirty); // => "&quot;&gt;&lt;script&gt;alert(&#39xss&#39);&lt;/script&gt;"
		System.out.println("#### clean :: " + clean);
		dirty = XssPreventer.unescape(clean); // => "\"><script>alert('xss');</script>" }
		System.out.println("### dirty :: " + dirty);
		*/
	}


}
