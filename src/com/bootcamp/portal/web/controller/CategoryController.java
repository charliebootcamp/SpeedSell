package com.bootcamp.portal.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.bootcamp.portal.domain.Category;
import com.bootcamp.portal.mgr.CategoryDAO;
import com.bootcamp.portal.mgr.annotation.PermissionRequired;
import com.bootcamp.portal.mgr.dto.CategoryDto;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

// ANJULAR JS 
// TODO improve exception handling

@Controller
@PermissionRequired
public class CategoryController {

	@Autowired
	private CategoryDAO categoryManager;

	protected static final Logger LOGGER = Logger
			.getLogger(CategoryController.class);

  @RequestMapping(value = "categories/{id}", method = RequestMethod.GET)
  public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) throws Exception {
	Category category = categoryManager.getById(id);
    return new ResponseEntity<Category>(category, HttpStatus.OK);
  }
  
  @RequestMapping(value = "categories/{id}/parent", method = RequestMethod.GET)
  public ResponseEntity<Category> getCategoryParentById(@PathVariable("id") Long id) throws Exception {
	Category category = categoryManager.getParentById(id);
    return new ResponseEntity<Category>(category, HttpStatus.OK);
  }
  
  @RequestMapping(value = "categories", method = RequestMethod.GET)
  public ResponseEntity<List<Category>> getCategories() {
    List<Category> list = categoryManager.getCategories();
    return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
  }
  
  @RequestMapping(value = "allcategories", method = RequestMethod.GET)
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> list = categoryManager.getAllCategories();
    return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
  }
  
  @RequestMapping(value = "categories", method = RequestMethod.POST)
  public ResponseEntity<Void> addCategory(@RequestBody CategoryDto category, UriComponentsBuilder builder) throws Exception {
    boolean flag = false;
    flag = categoryManager.addCategory(category);
    if (!flag) {
      return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(builder.path("categories/{id}").buildAndExpand(category.getId()).toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = "categories/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Category> updateCategory(@RequestBody Category category) throws Exception {
	categoryManager.updateCategory(category);
    return new ResponseEntity<Category>(category, HttpStatus.OK);
  }

  @RequestMapping(value = "categories/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
	  Category cat;
	try {
		cat = categoryManager.getById(id);
		categoryManager.hardDeleteObject(cat);
	} catch (ObjectRemovedException e) {
		e.printStackTrace();
	}
	  catch (BaseSaveObjectException e) {
		e.printStackTrace();
	}
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
}