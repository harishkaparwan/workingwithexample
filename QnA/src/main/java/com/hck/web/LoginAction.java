package com.hck.web;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.formula.functions.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.hck.service.LoginServiceImpl;
public class LoginAction  extends MultiActionController implements UserDetailsService{
	Logger logger = LoggerFactory.getLogger(LoginAction.class);
	private LoginServiceImpl loginServiceImpl;

	boolean enabled = true;
	boolean accountNonExpired = true;
	boolean credentialsNonExpired = true;
	boolean accountNonLocked = true;
	
	public LoginServiceImpl getLoginServiceImpl() {
		return loginServiceImpl;
	}



	public void setLoginServiceImpl(LoginServiceImpl loginServiceImpl) {
		this.loginServiceImpl = loginServiceImpl;
	}



	public ModelAndView login(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse) throws Exception{
		boolean isLoginSuccesfull = false;

		System.out.println("ModelAndView login::httpServletResponse.sendRedirect::");
		String userName="Harish";
		UserDetails userDetails=null;
		String userNamePassword="username= '"+httpServletRequest.getParameter("username")+"' and password='"+httpServletRequest.getParameter("password") +"'";
		System.out.println("--userNamePassword---"+userNamePassword);
		 Authentication authentication=null;
			try {
				userDetails=  loadUserByUsername(userNamePassword);
				System.out.println("userDetails.getAuthorities()--"+userDetails.getAuthorities());
				authentication=new UsernamePasswordAuthenticationToken(httpServletRequest.getParameter("username"), httpServletRequest.getParameter("password")); 
				System.out.println("getAuthorities--"+authentication.getAuthorities());
				System.out.println("isAuthenticated--"+authentication.isAuthenticated());
				System.out.println("getPrincipal--"+authentication.getPrincipal());


			} catch (Exception e) {
				System.out.println(httpServletRequest.getContextPath()+"/login.jsp");
				 httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login.jsp");
			} 
			
			 
			 System.out.println("******getPrincipal*******::::"+SecurityContextHolder.getContext().getAuthentication().getPrincipal());

			

			 SecurityContextHolder.getContext().setAuthentication(authentication);
			 System.out.println("******authentication***getName****::::"+SecurityContextHolder.getContext().getAuthentication().getName());
			 System.out.println("******authentication***getPrincipal****::::"+SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		return new ModelAndView("success","userDetails",userDetails);
		
	}
	
	public UserDetails getLoggedUserDetails() {
		UserDetails loggedUserDetails = null;
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		System.out.println("---------"+isAuthenticated(authentication));
		if (isAuthenticated(authentication)) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				loggedUserDetails = ((UserDetails) principal);
			} else {
				try {
					throw new Exception(
							"Expected class of authentication principal is AuthenticationUserDetails. Given: "
									+ principal.getClass());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return loggedUserDetails;
	}

	

public UserDetails loadUserByUsername(String userNamePassword)
		throws UsernameNotFoundException, DataAccessException {
	// TODO Auto-generated method stub
	UserDetails userDetails= getLoggedUserDetails();
	com.hck.model.User domainUser;
	if(null==userDetails){
		domainUser = loginServiceImpl.findByUsername(userNamePassword);
		System.out.println("domainUser.getRoleBean().getRoleid()--"+domainUser.getRoleBean().getRoleid());
		 if(null!=domainUser){
			 
			 
			// System.out.println("roles--"+getAuthorities(domainUser.getRole().getRole()));
			 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		        userDetails =new User(
				domainUser.getUsername(), 
				domainUser.getPassword().toLowerCase(),
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				getAuthorities(domainUser.getRoleBean().getRolename()));
	   }
	}
	return userDetails;
}



/**
 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
 * @param role the numerical role
 * @return a collection of {@link GrantedAuthority
 */
public Collection<? extends GrantedAuthority> getAuthorities(String roleName) {
	List<String> roles = new ArrayList<String>();
	roles.add(roleName);
	List<GrantedAuthority> authList = getGrantedAuthorities(roles);
	return authList;
}

/**
 * Converts a numerical role to an equivalent list of roles
 * @param role the numerical role
 * @return list of roles as as a list of {@link String}
 */
/*
public List<String> getRoles(Integer role) {
	List<String> roles = new ArrayList<String>();
	
	if (role.intValue() == 1) {
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_QA");
	} else if (role.intValue() == 2) {
		roles.add("ROLE_USER");
	}else if(role.intValue() == 3){
		roles.add("ROLE_JSP");
	}
	
	return roles;
}
*?
/**
 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
 * @param roles {@link String} of roles
 * @return list of granted authorities
 */
public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	for (String role : roles) {
		authorities.add(new GrantedAuthorityImpl(role));
	}
	return authorities;
}

private boolean isAuthenticated(Authentication authentication) {
	return authentication != null
			&& !(authentication instanceof AnonymousAuthenticationToken)
			&& authentication.isAuthenticated();
}


public ModelAndView loginout(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse) throws Exception{
	System.out.println("::loginout:::");
	SecurityContextHolder.getContext().setAuthentication(null);
	return new ModelAndView("loginout","","");}




}
