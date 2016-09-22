package com.bootcamp.portal.mgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.Bid;
import com.bootcamp.portal.domain.Category;
import com.bootcamp.portal.domain.Lot;
import com.bootcamp.portal.domain.LotStates;
import com.bootcamp.portal.domain.Person;
import com.bootcamp.portal.domain.State;
import com.bootcamp.portal.email.*;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.dto.LotDto;
import com.bootcamp.portal.mgr.dto.SearchLotDto;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

//ANGULAR JS
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LotDAO extends BaseManager {

	public LotDAO() {
		super();
		if (threads == 0) {
			threads++;
			(new Timer()).scheduleAtFixedRate(new WorkerTimer(), DELAY, PERIOD);
		}
	}

	private static int threads = 0;

	private final long DELAY = 10000;
	private final long PERIOD = 20000;

	private class WorkerTimer extends TimerTask {

		@Autowired
		private EmailSender emailSender = new EmailSender();

		private final Long WAITPAY = 900000L;// 15 minutes

		@Override
		public void run() {
			ReloadData();
		}

		private void CheckStartedLots(List<Lot> lots, long curTime) {
			try {
				for (Lot lot : lots) {
					if ((lot.getStartDate().getTime() + lot.getDuration()) < curTime) {
						State st;
						if (lot.getBestBid() == null) {
							st = entityById(State.class, LotStates.FINISHED.getId());
						} else {
							st = entityById(State.class, LotStates.SOLD.getId());
							lot.setDuration(lot.getDuration() + WAITPAY);
							emailSender.sendWinnerReminder(lot.getBestBid().getBidder(), lot.getId(), lot.getName());
						}
						lot.setState(st);
						updateLot(new LotDto(lot));
					}
				}
			} catch (HibernateException e) {
				System.out.println("Function: CheckStartedLots().HibernateException! Stopping thread.");
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				System.out.println("Got error!");
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}

		private void CheckSoldLots(List<Lot> lots, long curTime) {
			try {
				for (Lot lot : lots) {
					if ((lot.getStartDate().getTime() + lot.getDuration()) < curTime) {
						lot.getBestBid().setDeleted(true);
						saveOrUpdate(lot.getBestBid());

						List<Bid> bids = objectList(Bid.class, Restrictions.and(Restrictions.eq("lotId", lot.getId()),
								Restrictions.eq("isDeleted", false)));
						if (!bids.isEmpty()) {

							lot.setBestBid(bids.get(bids.size() - 1));
							lot.setDuration(lot.getDuration() + WAITPAY);
							saveOrUpdate(lot);
						} else {

							lot.setState(entityById(State.class, LotStates.FINISHED.getId()));
							lot.setBestBid(null);
							updateLot(new LotDto(lot));
						}
					}
				}
			} catch (HibernateException e) {
				System.out.println("Function: CheckSoldLots().HibernateException! Stopping thread.");
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				System.out.println("Got error!");
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}

		private void CheckApprovedLots(List<Lot> lots, long curTime) {
			try {
				for (Lot lot : lots) {
					updateLot(new LotDto(lot.getId(), LotStates.STARTED.getId()));
				}

			} catch (HibernateException e) {
				System.out.println("Function: CheckApprovedLots().HibernateException! Stopping thread.");
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				System.out.println("Got error!");
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}

		private void ReloadData() {
			List<Lot> lots = new ArrayList<Lot>();
			long millis = System.currentTimeMillis();

			try {
				lots = getByState(entityById(State.class, LotStates.APPROVED.getId()));
			} catch (ObjectRemovedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			if (lots != null) {
				try {
					// for admin to see accepted lot for a while.
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				CheckApprovedLots(lots, millis);
			}

			try {
				lots = getByState(entityById(State.class, LotStates.STARTED.getId()));
			} catch (ObjectRemovedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			millis = System.currentTimeMillis();
			if (lots != null) {
				CheckStartedLots(lots, millis);
			}

			try {
				lots = getByState(entityById(State.class, LotStates.SOLD.getId()));
			} catch (ObjectRemovedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			millis = System.currentTimeMillis();
			if (lots != null) {
				CheckSoldLots(lots, millis);
			}
		}

	}

	@Transactional
	public List<Lot> getAllLots() {
		List<Lot> result = objectList(Lot.class, null);
		return result;
	}

	@Transactional
	public List<Lot> getLots() {
		List<Lot> result;
		try {
			result = (List<Lot>) objectList(Lot.class,
					Restrictions.eq("state", entityById(State.class, LotStates.STARTED.getId())));
			return result;
		} catch (ObjectRemovedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public Lot getById(Long id) throws ObjectRemovedException {
		return entityById(Lot.class, id);
	}

	@Transactional
	public List<Lot> getPremium() throws ObjectRemovedException {
		return (List<Lot>) objectList(Lot.class,
				Restrictions.and(Restrictions.eq("state", entityById(State.class, LotStates.STARTED.getId())),
						Restrictions.eq("isPaidCommission", true), Restrictions.eq("premium", true)));
	}

	@Transactional
	public List<Lot> getPremiumRequests() throws ObjectRemovedException {
		return (List<Lot>) objectList(Lot.class,
				Restrictions.and(Restrictions.eq("state", entityById(State.class, LotStates.CREATED.getId())),
						Restrictions.eq("premium", false)));
	}

	@Transactional
	public boolean makePremium(Long id, Boolean t) throws ObjectRemovedException {
		Lot lot = entityById(Lot.class, id);
		lot.setPremium(t);
		try {
			saveOrUpdate(lot);
			return true;
		} catch (BaseSaveObjectException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public List<Lot> getLotsRequests() {
		try {
			return (List<Lot>) objectList(Lot.class,
					Restrictions.and(Restrictions.eq("state", entityById(State.class, LotStates.CREATED.getId())),
							Restrictions.eq("isPaidCommission", true)));
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<Lot> getByState(State state) {
		try {
			return (List<Lot>) objectList(Lot.class, Restrictions.eq("state", state));
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<Lot> getByCategory(Category subcategory) {
		try {

			return (List<Lot>) objectList(Lot.class,
					Restrictions.and(Restrictions.eq("state", entityById(State.class, LotStates.STARTED.getId())),
							Restrictions.eq("subcategory", subcategory)));
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<Lot> getByOwner(Person owner) {
		try {
			return (List<Lot>) objectList(Lot.class, Restrictions.eq("owner", owner));
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<Lot> getByName(String name) {
		try {
			return (List<Lot>) objectList(Lot.class,
					Restrictions.and(Restrictions.like("name", name, MatchMode.ANYWHERE),
							Restrictions.eq("state", entityById(State.class, LotStates.STARTED.getId()))));
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<Lot> getBySearch(SearchLotDto lot) {
		try {
			List<Lot> result;
			Criterion criterion = Restrictions.eq("state", entityById(State.class, LotStates.STARTED.getId()));
			criterion = Restrictions.and(criterion, Restrictions.like("name", lot.getName(), MatchMode.ANYWHERE));
			criterion = Restrictions.and(criterion, Restrictions.in("subcategory",
					objectList(Category.class, Restrictions.in("id", lot.getCategoryIds()))));
			if (lot.getIsRedemption() == (byte) 1)
				criterion = Restrictions.and(criterion, Restrictions.isNotNull("redemption"));
			if (lot.getPriceFrom() != null) {
				criterion = Restrictions.and(criterion,
						Restrictions.or(
								Restrictions.and(Restrictions.isNull("bestBid"),
										Restrictions.ge("startPrice", lot.getPriceFrom())),
								Restrictions.in("bestBid", getHigherBids(lot.getPriceFrom()))));
			}
			if (lot.getPriceTo() != null) {
				criterion = Restrictions.and(criterion,
						Restrictions.or(
								Restrictions.and(Restrictions.isNull("bestBid"),
										Restrictions.le("startPrice", lot.getPriceTo())),
								Restrictions.in("bestBid", getLowerBids(lot.getPriceTo()))));

			}

			result = objectList(Lot.class, criterion);
			return result;
		} catch (Exception e) {
			return null;
		}

	}

	private Bid[] getLowerBids(Long to) {
		List<Bid> bids = objectList(Bid.class,
				Restrictions.and(Restrictions.eq("isDeleted", false), Restrictions.le("amount", to)));
		Bid[] bidArray = new Bid[bids.size()];
		for (int i = 0; i < bids.size(); i++) {
			bidArray[i] = bids.get(i);
		}
		return bidArray;
	}

	private Bid[] getHigherBids(Long from) {
		List<Bid> bids = objectList(Bid.class,
				Restrictions.and(Restrictions.eq("isDeleted", false), Restrictions.ge("amount", from)));
		Bid[] bidArray = new Bid[bids.size()];
		for (int i = 0; i < bids.size(); i++) {
			bidArray[i] = bids.get(i);
		}
		return bidArray;
	}

	@Transactional
	public boolean addLot(LotDto lot) throws Exception {

		Lot l = new Lot();
		l.setName(lot.getName());
		l.setInfo(lot.getInfo());
		l.setPaidCommission(false);
		l.setStartPrice(lot.getStartPrice());
		l.setDuration(lot.getDuration());
		l.setState(entityById(State.class, LotStates.CREATED.getId()));
		l.setRedemption(lot.getRedemption());
		l.setSubcategory(entityById(Category.class, lot.getCategoryId()));
		l.setOwner(entityById(Person.class, lot.getOwnerId()));
		l.setImg(lot.getImg());

		try {
			saveOrUpdate(l);
		} catch (Exception e) {
			throw new Exception("Exception on Lot saving");
		}

		return true;
	}

	@Transactional
	public void updateLot(LotDto lot) throws Exception {
		Lot l = entityById(Lot.class, lot.getId());

		if (lot.getName() != null)
			l.setName(lot.getName());
		if (lot.getBuyPrice() != null)
			l.setBuyPrice(lot.getBuyPrice());
		if (lot.getCategoryId() != null)
			l.setSubcategory(entityById(Category.class, lot.getCategoryId()));
		if (lot.isCommission() != null)
			l.setPaidCommission(lot.isCommission());
		if (lot.getDuration() != null)
			l.setDuration(lot.getDuration());
		if (lot.getInfo() != null)
			l.setInfo(lot.getInfo());
		if (lot.getRedemption() != null) {
			if (lot.getRedemption() == 0) {
				l.setRedemption(null);
			} else {
				l.setRedemption(lot.getRedemption());
			}
		}
		if (lot.getStartDate() != null)
			l.setStartDate(lot.getStartDate());
		if (lot.getStartPrice() != null)
			l.setStartPrice(lot.getStartPrice());
		if (lot.getStateId() != null)
			l.setState(entityById(State.class, lot.getStateId()));

		if (lot.getBidId() != null) {
			l.setBestBid(entityById(Bid.class, lot.getBidId()));
		} else {
			l.setBestBid(null);
		}
		if (lot.getImg() != null)
			l.setImg(lot.getImg());
		if (lot.getPremium() != null)
			l.setPremium(lot.getPremium());

		try {
			saveOrUpdate(l);
		} catch (Exception e) {
			throw new Exception("Exception on Lot saving");
		}
	}

	@Transactional
	public void deleteLot(Long id) {
		try {
			hardDeleteObject(entityById(Lot.class, id));
		} catch (ObjectRemovedException e) {

		} catch (BaseSaveObjectException e) {

		}
	}

	@Transactional
	public boolean LotExists(String name, String parentId) {

		return false;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Lot> limit(int firstResult, int maxResults) throws ObjectRemovedException {
		int paginatedCount = 0;
		List<Lot> products = null;
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Lot.class);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		criteria.add(Restrictions.eq("state", entityById(State.class, LotStates.STARTED.getId())));
		products = (List<Lot>) criteria.list();
		if (products != null) {
			paginatedCount = products.size();
			System.out.println("Total Results: " + paginatedCount);
			for (Lot product : products) {
				System.out.println("Retrieved Product using Criteria. Name: " + product.getName());
			}
		}

		return products;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Lot> limitByCategory(Category subcategory, int firstResult, int maxResults)
			throws ObjectRemovedException {
		int paginatedCount = 0;
		List<Lot> products = null;
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Lot.class);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		criteria.add(Restrictions.and(Restrictions.eq("state", entityById(State.class, LotStates.STARTED.getId())),
				Restrictions.eq("subcategory", subcategory)));
		System.out.println(subcategory);
		products = (List<Lot>) criteria.list();
		if (products != null) {
			paginatedCount = products.size();
			System.out.println("Total Results: " + paginatedCount);
			for (Lot product : products) {
				System.out.println("Retrieved Product using Criteria. Name: " + product.getName());
			}
		}

		return products;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Lot> limitBySCategory(List<Category> arr, int firstResult, int maxResults)
			throws ObjectRemovedException {
		int paginatedCount = 0;
		List<Lot> products = null;

		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Lot.class);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);

		Disjunction disjunction = Restrictions.disjunction();

		for (int i = 0; i < arr.size(); i++) {

			Criterion cat = Restrictions.and(Restrictions.eq("subcategory", arr.get(i)),
					Restrictions.eq("state", entityById(State.class, LotStates.STARTED.getId())));

			disjunction.add(cat);

		}

		criteria.add(disjunction);
		products = (List<Lot>) criteria.list();
		if (products != null) {
			paginatedCount = products.size();
			System.out.println("Total Results Par: " + paginatedCount);
			for (Lot product : products) {
				System.out.println("Retrieved Product using Criteria. Name: " + product.getName());
			}
		}
		System.out.println("Total Results Parent CAt: " + paginatedCount);

		return products;
	}
}
