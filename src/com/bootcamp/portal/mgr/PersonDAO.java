package com.bootcamp.portal.mgr;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.Person;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;
import com.bootcamp.portal.utils.TextUtil;

// ANGULAR JS
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PersonDAO extends BaseManager {

	private PasswordEncoder passwordEncoder = new ShaPasswordEncoder();
	
	@Transactional
	public List<Person> getAllPersons(HttpServletRequest request) {
		List<Person> result = objectList(Person.class, null);
		return result;
	}
	
	@Transactional
	  public Person getByEmail(String email) {
	    try {
	      return (Person) entityByCriteria(Person.class, Restrictions.eq("email", email));
	    } catch (ObjectRemovedException e) {
	      return null;
	    }    
	  }
	
	@Transactional
	  public Person getByParam(String propertyName, String param) {
	    try {
	      return (Person) entityByCriteria(Person.class, Restrictions.eq(propertyName, param));
	    } catch (ObjectRemovedException e) {
	      return null;
	    }    
	  }
	
//	@Transactional
//	  public Person getByUsername(String username) {
//	    try {
//	      return (Person) entityByCriteria(Person.class, Restrictions.eq("username", username));
//	    } catch (ObjectRemovedException e) {
//	      return null;
//	    }    
//	  }
//	
//	@Transactional
//	  public Person getByPhone(String phone) {
//	    try {
//	      return (Person) entityByCriteria(Person.class, Restrictions.eq("phone", phone));
//	    } catch (ObjectRemovedException e) {
//	      return null;
//	    }    
//	  }	
	
	@Transactional
	  public Person getByCode(String code) {
	    try {
	      return (Person) entityByCriteria(Person.class, Restrictions.eq("verificationCode", code));
	    } catch (ObjectRemovedException e) {
	      return null;
	    }    
	  }

	@Transactional
	public Person getById(Long id) throws ObjectRemovedException {
		return entityById(Person.class, id);
	}
	@Transactional
	public Person addPerson(Person person) throws Exception {
		Person added = person;
		String encodedPwd = passwordEncoder.encodePassword(person.getPasswordHash(), null);
		added.setPasswordHash(encodedPwd);
		added.setVerificationCode(TextUtil.getRandomString());
		added.setCreateDate(Calendar.getInstance().getTime());
		added.setActive(true);
		try {
			saveOrUpdate(person);
		} catch (Exception e) {
			throw new Exception("Exception on Person saving");
		}
		return person;
	}
	
	@Transactional
	public Person changePswdReq(Person person) throws Exception {
		Person added =getByEmail(person.getEmail());		
		added.setPasswordChangeCode(TextUtil.getRandomString());	
		try {
			saveOrUpdate(added);
		} catch (Exception e) {
			throw new Exception("Exception on Person saving");
		}
		return added;
	}	
	
	@Transactional
	public void resetPassword(Person person, String newPassword) throws Exception {
		Person added = getByEmail(person.getEmail());		
		String encodedPwd = passwordEncoder.encodePassword(newPassword, null);
		added.setPasswordHash(encodedPwd);
		added.setPasswordChangeCode(null);	
		try {
			saveOrUpdate(added);
		} catch (Exception e) {
			throw new Exception("Exception on password reset");
		}		
	}
	
	@Transactional
	  public void verifyUser(Person user, String code) throws Exception {
	    if (user.getVerified() == Boolean.TRUE) {
	      throw new Exception("User is already verified");
	    }
	    
	    if (!StringUtils.equals(user.getVerificationCode(), code)) {
	      throw new Exception("Code is wrong");
	    }
	    
	    try {
	      user.setVerified(true);
	      user.setVerificationCode(null);
	      saveOrUpdate(user);
	    } catch (Exception e) {
	      throw new Exception("Error on saving user");
	    }
	  }  
	
	@Transactional
	  public Person authenticate(String email, String password) {
		Person t = getByEmail(email);
	    if (t == null) {
	      return null;
	    }
	    
	    if ((passwordEncoder.isPasswordValid(t.getPasswordHash(), password , null)) == false) {
	      return null;
	    }

	    return t;
	  }

	@Transactional
	public void updatePerson(Person person) throws Exception {
		Person p;
		
		try {
			p = entityById(Person.class, person.getId());
		} catch (ObjectRemovedException e1) {
			return;
		}
		if(person.getEmail() != null){
			p.setEmail(person.getEmail());
			p.setVerified(false);
			p.setVerificationCode(TextUtil.getRandomString());
		}
		if(person.getPasswordHash() != null){
			String encodedPwd = passwordEncoder.encodePassword(person.getPasswordHash(), null);
			p.setPasswordHash(encodedPwd);
		}
		if(person.getFullName() != null)
			p.setFullName(person.getFullName());
		if(person.getHomeAddress() != null)
			p.setHomeAddress(person.getHomeAddress());
		if(person.getUpdateDate()!= null)
			p.setUpdateDate(person.getUpdateDate());
		if(person.getPhone() != null)
			p.setPhone(person.getPhone());
		try {
			saveOrUpdate(p);
		} catch (Exception e) {
			throw new Exception("Exception on Person saving");
		}
	}

	@Transactional
	public void deletePerson(Long id) {
		try {
			hardDeleteObject(entityById(Person.class, id));
		} catch (ObjectRemovedException e) {
			// TODO implement me!
		} catch (BaseSaveObjectException e) {
			// TODO implement me!
		}
	}
	

	@Transactional
	public boolean personExists(String name, String location) {
		// TODO implement me!
		return false;
	}
}
