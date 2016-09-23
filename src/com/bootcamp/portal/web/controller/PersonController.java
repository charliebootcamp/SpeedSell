package com.bootcamp.portal.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.bootcamp.portal.domain.Person;
import com.bootcamp.portal.domain.UActivities;
import com.bootcamp.portal.domain.UserActivities;
import com.bootcamp.portal.email.EmailSender;
import com.bootcamp.portal.mgr.ManagerFactory;
import com.bootcamp.portal.mgr.PersonDAO;
import com.bootcamp.portal.mgr.UserActivitiesDAO;
import com.bootcamp.portal.mgr.annotation.PermissionRequired;
import com.bootcamp.portal.mgr.dto.LetterDto;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.web.AuthenticatedUser;
import com.bootcamp.portal.web.WebConfig;

// ANJULAR JS 
// TODO improve exception handling

@Controller
@PermissionRequired
public class PersonController {

    private static final int USER_TIMEOUT = 2 * 60 * 60; // 2 hours
    // private static final String REDIRECT_AUTHRESULT = "redirect:/authresult";
    private static final String FIRST_PAGE_NO_REDIRECT = "index";
    // private static final String URL_PROFILE = "/user/profile";
    private static final String FIRST_PAGE = "redirect:"
            + FIRST_PAGE_NO_REDIRECT;
    private static final String ERROR_LOGIN_MESSAGE = "Wrong user or password";
    private static final String ERROR_NOT_VERIFIED = "Not verified email. Look for code in your email inbox";
    
    @Autowired
    private PersonDAO personManager;
    
    @Autowired
    private UserActivitiesDAO userActivitiesManager;

    @Autowired
    private EmailSender emailSender;
    
    private PasswordEncoder passwordEncoder = new ShaPasswordEncoder();

    protected static final Logger LOGGER = Logger
            .getLogger(PersonController.class);
    
    @RequestMapping(value = "persons/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id)
            throws Exception {
        Person person = personManager.getById(id);
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @RequestMapping(value = "persons", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllPersons(HttpServletRequest request) {
        List<Person> list = ManagerFactory.getUserManager().getAllPersons(
                request);
        return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
    }
    
    @RequestMapping(value = "checkUsername", method = RequestMethod.POST)
    public ResponseEntity<Object> checkUsername(@RequestBody Person person) throws Exception {
        try {			
            Person t = personManager.getByParam("name", person.getName());
            if (t != null) {
                Map<String,String> result = new HashMap<String,String>();
            	result.put("message", "This username is busy.");
                return new ResponseEntity<Object>(result, HttpStatus.CONFLICT);
            }
            else {
                return new ResponseEntity<Object>(HttpStatus.OK);				
            }			
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            Map<String,String> result = new HashMap<String,String>();
        	result.put("message", e.getMessage());
            return new ResponseEntity<Object>(result,HttpStatus.CONFLICT);
        }		
    }	
    

    @RequestMapping(value = "check_password", method = RequestMethod.POST)
    public ResponseEntity<Object> checkPassword(@RequestBody Person person) throws Exception {
        Person t = personManager.getById(person.getId());
        if(passwordEncoder.isPasswordValid(t.getPasswordHash(), person.getPasswordHash() , null)){
            return new ResponseEntity<Object>(HttpStatus.OK);	
        }else{
            Map<String,String> result = new HashMap<String,String>();
        	result.put("message", "Wrong password.");
            return new ResponseEntity<Object>(result, HttpStatus.CONFLICT);
        }			
    }
    
    @RequestMapping(value = "checkEmail", method = RequestMethod.POST)
    public ResponseEntity<Object> checkEmail(@RequestBody Person person) throws Exception {
        if(person.getEmail().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){}
        else if(person.getEmail()!= ""){
            throw new Exception("Wrong email format. Try like this: user@domain.com");
        }
                
        Person t = personManager.getByParam("email", person.getEmail());
        if (t != null) {
            Map<String,String> result = new HashMap<String,String>();
        	result.put("message", "This email is busy.");
            return new ResponseEntity<Object>(result, HttpStatus.CONFLICT);
        }
        else {
            return new ResponseEntity<Object>(HttpStatus.OK);					
        }				
    }
    

    @RequestMapping(value = "checkPhone", method = RequestMethod.POST)
        public ResponseEntity<Object> checkPhone(@RequestBody Person person) throws Exception {
        //validate phone numbers of format "1234567890"
        if (person.getPhone().matches("\\d{10}")){}
        //validating phone number with -, . or spaces
        else if(person.getPhone().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")){}
        //validating phone number with extension length from 3 to 5
        else if(person.getPhone().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")){}
        //validating phone number where area code is in braces ()
        else if(person.getPhone().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")){}
        //return false if nothing matches the input
        else {
            Map<String,String> result = new HashMap<String,String>();
        	result.put("message", "Wrong phone format. Try like this: 0123456789");
            return new ResponseEntity<Object>(result, HttpStatus.CONFLICT);
        }		

        Person t = personManager.getByParam("phone", person.getPhone());
        if (t != null) {
            Map<String,String> result = new HashMap<String,String>();
        	result.put("message", "This phone number is busy.");
            return new ResponseEntity<Object>(result, HttpStatus.CONFLICT);
        }
        else {
            return new ResponseEntity<Object>(t,HttpStatus.OK);				
        }			
    }	

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public ResponseEntity<Void> addPerson(@RequestBody Person person,
            UriComponentsBuilder builder) throws Exception {
        try {
            // add new user to the system (status = created pending)
            Person added = personManager.addPerson(person);
            
            if (added == null) {
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            //create userActivity report
            UserActivities userActivities = new UserActivities(
                    added.getId(), 
                    added.getCreateDate(),
                    "New user registered",
                    UActivities.NEW_USER_REGISTERED.getUActId());
            
            userActivitiesManager.addUserActivity(userActivities);
            
            // send email with verification
            emailSender.sendUserVerificationCode(added);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/persons/{id}")
                .buildAndExpand(person.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "persons/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(
            @RequestBody Person person, HttpSession session)
            throws Exception {

        person.setUpdateDate(Calendar.getInstance().getTime());

        try {
            personManager.updatePerson(person);
        } catch (BaseSaveObjectException e) {
            LOGGER.error(e.getMessage());
        }

        if (person.getEmail() != null) {
            Person updated = personManager.getById(person.getId());
            try {
                emailSender.sendEmailChangeCode(updated);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        
        //create userActivity report
        
        UserActivities userActivities = new UserActivities(
                person.getId(), 
                Calendar.getInstance().getTime(),
                "User changed personal data",
                UActivities.CHANGED_PERSONAL_DATA.getUActId());
        
        userActivitiesManager.addUserActivity(userActivities);
        
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @RequestMapping(value = { "registration/loginPage" })
    public String login(HttpSession session) {
        session.setMaxInactiveInterval(USER_TIMEOUT);
        AuthenticatedUser user = WebConfig.getCurrentUser(session);
        if (user == null) {
            return "registration/loginPage";
        }
        return FIRST_PAGE;
    }
    
    @RequestMapping(value = { "registration/resetPassword/{h}" })
    public String reset(HttpSession session, @PathVariable("h") String h) {
        session.setMaxInactiveInterval(USER_TIMEOUT);
        AuthenticatedUser user = WebConfig.getCurrentUser(session);
        if (user == null) {
            return "registration/resetPassword";
        }
        return FIRST_PAGE;
    }
    
    
    @RequestMapping(value = "resetPass", method = RequestMethod.POST)
    public ResponseEntity<Void> resetPassword(@RequestBody Person person,
            HttpServletResponse response) {
        try {			
            Person t = personManager.getByParam("passwordChangeCode", person.getPasswordChangeCode());
            if (t == null) {
                throw new Exception("Unknown user");
            }
            
            personManager.resetPassword(t, person.getPasswordHash());
            
            //create userActivity report
            UserActivities userActivities = new UserActivities(
                    t.getId(), 
                    Calendar.getInstance().getTime(),
                    "User reseted password",
                    UActivities.RESETED_PASSWORD.getUActId());
            userActivitiesManager.addUserActivity(userActivities);

//			StringBuilder redirectUrl = new StringBuilder("#/login");
//			response.sendRedirect(redirectUrl.toString());

            
        } catch (Exception e) {
            // show any errors
            LOGGER.error("Verification Error: " + e.getMessage());
            ;
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
        
    }

    @RequestMapping(value = { "logoutUser" })
    public String logoutUser(HttpServletRequest request,
            HttpServletResponse response) {
        WebConfig.logout(request, response);
        return "registration/loginPage";
    }

    @RequestMapping(value = "do-login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticatedUser> login(@RequestBody Person person,
            HttpServletRequest request, HttpSession session) throws Exception {
                Person t = personManager.authenticate(person.getEmail(),
                person.getPasswordHash());
        AuthenticatedUser authUser;
        try {
            if (t == null) {
                throw new Exception(ERROR_LOGIN_MESSAGE);
            }

            if (t.getVerified() != Boolean.TRUE) {

                throw new Exception(ERROR_NOT_VERIFIED);
            }

            t.setLastLogin(Calendar.getInstance().getTime());
            try {
                personManager.saveOrUpdate(t);
            } catch (BaseSaveObjectException e) {
                LOGGER.error(e.getMessage());
            }
            
            //create userActivity report
            UserActivities userActivities = new UserActivities(
                    t.getId(), 
                    t.getLastLogin(),
                    "User logged in",
                    UActivities.LOGGED_IN.getUActId());
            userActivitiesManager.addUserActivity(userActivities);
            
            authUser = new AuthenticatedUser(t);
            request.getSession().setMaxInactiveInterval(USER_TIMEOUT);
            WebConfig.setCurrentUser(request.getSession(true), authUser);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            LOGGER.error("Username: "+person.getEmail()+", pass:"+person.getPasswordHash());
            return new ResponseEntity<AuthenticatedUser>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<AuthenticatedUser>(authUser, HttpStatus.OK);
    }

    @RequestMapping(value = "verification")
    public void doVerification(@RequestParam(value = "code") String code,
            HttpServletResponse response) {
        try {

            Person t = personManager.getByCode(code);
            if (t == null) {
                throw new Exception("Unknown user");
            }
            personManager.verifyUser(t, code);
            //create userActivity report
            UserActivities userActivities = new UserActivities(
                    t.getId(), 
                    Calendar.getInstance().getTime(),
                    "User verified",
                    UActivities.USER_VERIFIED.getUActId());
            userActivitiesManager.addUserActivity(userActivities);
            
            StringBuilder redirectUrl = new StringBuilder("/speedsell/#/login");
            response.sendRedirect(redirectUrl.toString());
        } catch (Exception e) {
            // show any errors
            LOGGER.error("Verification Error: " + e.getMessage());
            ;
        }
    }
    
    @RequestMapping(value = "changePswdReq", method = RequestMethod.POST)
    public ResponseEntity<Void> changePasswordRequest(@RequestBody Person person,
            UriComponentsBuilder builder) throws Exception {
        try {
            Person p = personManager.getByEmail(person.getEmail());
            if(p == null){
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            p = personManager.changePswdReq(person);

            if (p == null) {
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            
            emailSender.sendUserResetPswdCode(p);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(builder.path("/persons/{id}")
//				.buildAndExpand(person.getId()).toUri());

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "resetPswd")
    public void redirectToResetPassword(@RequestParam(value = "code") String code,
            HttpServletResponse response) {
        try {

            Person t = personManager.getByParam("passwordChangeCode", code);
            if (t == null) {
                throw new Exception("Unknown user");
            }	

            StringBuilder redirectUrl = new StringBuilder("/speedsell/#/resetPassword/" + code);//add link to view with RESET
            response.sendRedirect(redirectUrl.toString());
        } catch (Exception e) {
            // show any errors
            LOGGER.error("Verification Error: " + e.getMessage());
            ;
        }
    }
    
    
    
    @RequestMapping(value = "currentuser", method = RequestMethod.GET)
    public ResponseEntity<AuthenticatedUser> getCurrentUserId(
            HttpServletRequest request, HttpSession session) throws Exception {
        AuthenticatedUser currUser = WebConfig.getCurrentUser(session);
        if (currUser == null) {
            return null;
        } else {
            return new ResponseEntity<AuthenticatedUser>(currUser,
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = "persons/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePerson(@PathVariable("id") Long id) {
        personManager.deletePerson(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "valid/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> validPerson(@RequestParam(value = "email") String email, 
                                            @RequestParam(value = "pass") String pass) {
        Person p = personManager.authenticate(email, pass);
        if(p == null){
            return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        }else{
            return new ResponseEntity<Boolean>(Boolean.TRUE,HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "send_email", method = RequestMethod.POST)
    public ResponseEntity<Boolean> validPerson(@RequestBody LetterDto ltr) {
        try {
            Person sender = personManager.getByEmail(ltr.getSenderEmail());
            ltr.setSenderId(sender.getId());
            ltr.setSenderName(sender.getName());
        } catch (Exception e) {
            //TO-DO
        }
        
        ltr.setSubmitDate(Calendar.getInstance().getTime());
        try {
            Person recipient = personManager.getByEmail(ltr.getRecEmail());
            ltr.setRecipientId(recipient.getId());
            ltr.setRecipientName(recipient.getName());
        } catch (Exception e) {
            //TO-DO
        }

        try {
            emailSender.sendQuestion(ltr);
            
            //create userActivity report
            if(ltr.getSenderId()!=null){

                UserActivities userActivities = new UserActivities(
                        ltr.getSenderId(), 
                        ltr.getSubmitDate(),
                        "User send question to other user id = " + ltr.getRecipientId(),
                        UActivities.SEND_EMAIL.getUActId());
                userActivitiesManager.addUserActivity(userActivities);
            }

            return new ResponseEntity<Boolean>(Boolean.TRUE,HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        }
    }
}