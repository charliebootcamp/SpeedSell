package com.bootcamp.portal.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.bootcamp.portal.domain.Bid;
import com.bootcamp.portal.domain.Category;
import com.bootcamp.portal.domain.Lot;
import com.bootcamp.portal.domain.LotStates;
import com.bootcamp.portal.domain.State;
import com.bootcamp.portal.mgr.CategoryDAO;
import com.bootcamp.portal.mgr.LotDAO;
import com.bootcamp.portal.mgr.PersonDAO;
import com.bootcamp.portal.mgr.StateDAO;
import com.bootcamp.portal.mgr.annotation.PermissionRequired;
import com.bootcamp.portal.mgr.dto.LotDto;
import com.bootcamp.portal.mgr.dto.SearchLotDto;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;
import com.bootcamp.portal.domain.Person;
// ANJULAR JS
// TODO improve exception handling

@Controller
@PermissionRequired
public class LotController {

	@Autowired
	private LotDAO LotManager;

	@Autowired
	private StateDAO StateManager;

	@Autowired
	private PersonDAO PersonManager;

	@Autowired
	private CategoryDAO CategoryManager;

	protected static final Logger LOGGER = Logger
			.getLogger(LotController.class);


    @RequestMapping(value = "lotdata/{id}", method = RequestMethod.GET)
	public ResponseEntity<Lot> getLotById(@PathVariable("id") Long id)
			throws Exception {
		Lot lot = LotManager.getById(id);
		return new ResponseEntity<Lot>(lot, HttpStatus.OK);
	}

	@RequestMapping(value = "lotdata/{id}/bestbid", method = RequestMethod.GET)
	public  ResponseEntity<Bid> getLotBestBid(@PathVariable("id") Long id)
			throws Exception {
		Lot lot = LotManager.getById(id);
		return new ResponseEntity<Bid>(lot.getBestBid(), HttpStatus.OK);
	}

	@RequestMapping(value = "alllotdata", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getAllLots() {
		List<Lot> list = LotManager.getAllLots();
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}


	@RequestMapping(value = "lotdata", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getLots() {
		List<Lot> list = LotManager.getLots();
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/ldlimit/{f}/{m}", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getLotsLimi(@PathVariable("f") int f,@PathVariable("m") int m) throws ObjectRemovedException {
		List<Lot> list = LotManager.limit(f, m);
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/limitlotsbycat/{categoryId}/{f}/{m}", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getLotsLimitByCat(@PathVariable("categoryId") Long categoryId,@PathVariable("f") int f,@PathVariable("m") int m) throws ObjectRemovedException {
		List<Lot> list = LotManager.limitByCategory(CategoryManager.getById(categoryId), f, m);
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/limitlotsbyscat/{categoryId}/{f}/{m}", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getLotsLimitBySCat(@PathVariable("categoryId") Long categoryId,@PathVariable("f") int f,@PathVariable("m") int m) throws ObjectRemovedException {
		Category parrent = new Category();
		parrent = CategoryManager.getById(categoryId);
		List<Category> sub =  parrent.getSubcategories();
		List<Lot> list = LotManager.limitBySCategory(sub, f, m);
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}

	@RequestMapping(value="lots/premium", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getPremium() {
		List<Lot> list;
		try {
			list = LotManager.getPremium();
			return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
		} catch (ObjectRemovedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value="premium/requests", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getPremiumRequests() {
		List<Lot> list;
		try {
			list = LotManager.getPremiumRequests();
			return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
		} catch (ObjectRemovedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value="premium", method = RequestMethod.PUT)
	public ResponseEntity<Void> makePremium(@RequestBody Long id){
		try {
			boolean flag = false;
			flag = LotManager.makePremium(id, true);
			if (!flag){
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			} else return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (ObjectRemovedException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

	}


	@RequestMapping(value = "create_lot", method = RequestMethod.POST)
	public ResponseEntity<Void> addLot(@RequestBody LotDto lot,
			UriComponentsBuilder builder) throws Exception {
		boolean flag = false;
		flag = LotManager.addLot(lot);
		if (!flag) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("lotdata/{id}")
				.buildAndExpand(lot.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}



	@RequestMapping(value = "state/{stateId}", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getLotsByState(
			@PathVariable("stateId") Long stateId) throws Exception {
		State state = StateManager.getById(stateId);
		List<Lot> list = LotManager.getByState(state);
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "lotsrequests", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getLotsRequests() throws Exception {
		List<Lot> list = LotManager.getLotsRequests();
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "lots/{categoryId}", method = RequestMethod.GET)//lot state = 3
	public ResponseEntity<List<Lot>> getLotsByCategory(
			@PathVariable("categoryId") Long categoryId) throws Exception {
		List<Lot> list = LotManager.getByCategory(CategoryManager.getById(categoryId));
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "search/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getLotsByName(@PathVariable("name") String name) throws Exception {
		List<Lot> list = LotManager.getByName(name);
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ResponseEntity<Object> getLotsSearch(@RequestBody SearchLotDto lot) throws Exception {
		List<Lot> list = LotManager.getBySearch(lot);
		return list == null ? new ResponseEntity(HttpStatus.NOT_FOUND) : (ResponseEntity) new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}



	@RequestMapping(value = "persons/{personId}/lots", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getLotsByPerson(
			@PathVariable("personId") Long personId) throws Exception {
		Person p = PersonManager.getById(personId);
		List<Lot> list = LotManager.getByOwner(p);
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}


	@RequestMapping(value = "update_lot", method = RequestMethod.PUT)
	public ResponseEntity<LotDto> updateLot(@RequestBody LotDto lot) throws Exception {
		LotManager.updateLot(lot);
		return new ResponseEntity<LotDto>(lot, HttpStatus.OK);
	}
	
	@RequestMapping(value = "edit_lot", method = RequestMethod.PUT)
	public ResponseEntity<LotDto> editLot(@RequestBody LotDto lot) throws Exception {
		lot.setStateId(LotStates.CREATED.getId());
		LotManager.updateLot(lot);
		return new ResponseEntity<LotDto>(lot, HttpStatus.OK);
	}

	public void updateLotState(LotDto lot) throws Exception{
		LotManager.updateLot(lot);
	}

	@RequestMapping(value = "delete_lot/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteLot(@PathVariable("id") Long id) {
		LotManager.deleteLot(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
