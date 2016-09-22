package com.bootcamp.portal.mgr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.Category;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.dto.CategoryDto;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

//ANGULAR JS
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategoryDAO extends BaseManager {

	// @Transactional
	// public List<Category> getAllCategories() {
	// List<Category> result = objectList(Category.class, null);
	// return result;
	// }
	@Transactional
	public List<Category> getAll() { //get categories and subcategories
		try {
		      List<Category> result = objectList(Category.class, null);
		      return result;
		    } catch (Exception e) {
		      return null;
		    }    
	}
	
	@Transactional
	public List<Category> getAllCategories() { 
		//get categories that might have and might have not categories
		try {
		      List<Category> list = getAll();
		      List<Category> result = getCategories();
		      List<Category> categories = result;
		      for (Category maybeCategory : list) {
		    	  if (maybeCategory.getSubcategories().isEmpty()) {
		    		  boolean pr = false;
		    		  for (Category category : categories) {
		    			  List<Category> subcategories = category.getSubcategories();
		    			  pr = false;
		    			  for (Category subcategory : subcategories) {
		    				  if (maybeCategory.equals(subcategory)) {
		    					  pr = true;
		    					  break;
		    				  }
		    			  }
		    			  if (pr) {
		    				  break;
		    			  }
		    		  }
		    		  if (!pr) {
		    			  result.add(maybeCategory);
		    		  }
		    	  }
				
			}
		      
		      return result;
		    } catch (Exception e) {
		      return null;
		    }    
	}
	
	@Transactional
	public List<Category> getCategories() { //get categories that have subcategories.
		try{
			List<Category> list = getAll();
			List<Category> result = new ArrayList<Category>();
			for (Category maybeCategory: list) {
				if (!maybeCategory.getSubcategories().isEmpty()){
					result.add(maybeCategory);
				}
			}
			return result;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@Transactional
	public Category getParentById(Long id) {
		try{
			List<Category> parents = getCategories();
			Long parentId = null;
			for (Category category : parents) {
				for (Category subcategory : category.getSubcategories()) {
					if (subcategory.getId().equals(id)){
						parentId = category.getId();
					}
				}
			}
			if (parentId != null) {
				return entityById(Category.class, parentId);
			}
			else return null;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@Transactional
	public Category getById(Long id) throws ObjectRemovedException {
		return entityById(Category.class, id);
	}
	
	@Transactional
	public boolean addCategory(CategoryDto category) throws Exception {
		try {
			Category newcategory = new Category();
			Category parent = null;
			newcategory.setName(category.getName());
			if (category.getParentId() != null) {
				parent = entityById(Category.class, category.getParentId());
			}
			newcategory.setParentCategory(parent);
			newcategory.setCommission(category.getCommission());
			saveOrUpdate(newcategory);
			return true;
		} catch (Exception e) {
			throw new Exception("Exception on Category saving");
		}
	}

	@Transactional
	public void updateCategory(Category category) throws Exception {
		Category p;
		try {
			p = entityById(Category.class, category.getId());
		} catch (ObjectRemovedException e1) {
			return;
		}

		p.setName(category.getName());
		p.setParentCategory(category.getParentCategory());
		p.setCommission(category.getCommission());
		try {
			saveOrUpdate(p);
		} catch (Exception e) {
			throw new Exception("Exception on Category saving");
		}
	}

	@Transactional//DO NOT USE IT, LOOK CategoryController.deleteById
	public void deleteCategory(Long id) {
		try {
			hardDeleteObject(entityById(Category.class, id));
			
		} catch (ObjectRemovedException e) {
			// TODO implement me!
		} catch (BaseSaveObjectException e) {
			// TODO implement me!
		}
	}

	@Transactional
	public boolean CategoryExists(String name, String parentId) {
		// TODO implement me!
		return false;
	}
}
