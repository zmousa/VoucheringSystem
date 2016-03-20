package com.jee.api.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class Filter {

	private static final Logger log = Logger.getLogger(Filter.class);

	private Class<?> clazz;
	private Session session;

	private Map<String, Object> filterEMap = new HashMap<String, Object>();
	private Map<String, Object> likeMap = new HashMap<String, Object>();
	private Map<String, Object> filterNEMap = new HashMap<String, Object>();
	private Map<String, Object[]> filterBetweenMap = new HashMap<String, Object[]>();
	
	private List<String> ascList = new ArrayList<String>();
	private List<String> descList = new ArrayList<String>();
	
	class Pair {
		public Pair(String key, Object value) {
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		private String key;
		private Object value;
	}
	
	private ArrayList<Pair> filterOrEList = new ArrayList<Pair>();
	private ArrayList<Pair> filterOrNEList = new ArrayList<Pair>();
	private ArrayList<Pair> filterOrBetweenList = new ArrayList<Pair>();
	private ArrayList<Pair> filterOrLikeList = new ArrayList<Pair>();
	
	public Filter(Class<?> _clazz,Session _session) {
		this.clazz = _clazz;
		this.session = _session;
	}

	public Filter filterE(String key, Object value) {
		filterEMap.put(key, value);
		return this;
	}
	public Filter like(String key, Object value) {
		likeMap.put(key, value);
		return this;
	}

	public Filter filterNE(String key, Object value) {
		filterNEMap.put(key, value);
		return this;
	}


	public Filter filterBetween(String key, Object value1,Object value2) {
		filterBetweenMap.put(key, new Object[] {value1,value2});
		return this;
	}

	public Filter filterOrBetween(String key, Object value1,Object value2) {
		filterOrBetweenList.add(new Pair(key, new Object[] {value1,value2}));
		return this;
	}
	
	public Filter filterOrE(String key, Object value) {
		filterOrEList.add(new Pair(key, value));
		return this;
	}


	public Filter filterOrNE(String key, Object value) {
		filterOrNEList.add(new Pair(key, value));
		return this;
	}
	
	public Filter orLike(String key, Object value) {
		filterOrLikeList.add(new Pair(key, value));
		return this;
	}
	
	public Filter asc(String fieldName) {
		ascList.add(fieldName);
		return this;
	}
	
	public Filter desc(String fieldName) {
		descList.add(fieldName);
		return this;
	}
	
	public List<?> list() throws Exception {

		try {
			return createFilterCriteria().list();
		} catch (Exception e) {
			log.error("list operation failed!",e);
			throw new Exception(e);
		}
	}

	public List<?> list(int firstResult, int maxResults) throws Exception {

		try {
			return createFilterCriteria().setFirstResult(firstResult).setMaxResults(maxResults).list();
		} catch (Exception e) {
			log.error("list page operation failed!",e);
			throw new Exception(e);
		}
	}

	public Object single() throws Exception {

		try {
			return createFilterCriteria().uniqueResult();
		} catch (Exception e) {
			log.error("single result operation failed!",e);
			throw new Exception(e);
		}
	}

	public Object max(String propertyName) throws Exception {

		try {
			return  createFilterCriteria().setProjection(Projections.max(propertyName)).uniqueResult();
		} catch (Exception e) {
			log.error("max operation failed!",e);
			throw new Exception(e);
		}
	}
	
	public Object min(String propertyName) throws Exception {

		try {
			return  createFilterCriteria().setProjection(Projections.min(propertyName)).uniqueResult();
		} catch (Exception e) {
			log.error("min operation failed!",e);
			throw new Exception(e);
		}
	}

	public int count() throws Exception {

		try {
			return (Integer) createFilterCriteria().setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			log.error("count operation failed!",e);
			throw new Exception(e);
		}
	}

	public boolean exist() throws Exception {

		try {
			return (count()>0);
		} catch (Exception e) {
			log.error("exist operation failed!",e);
			throw new Exception(e);
		}
	}
	
	Criterion orCriterion(Criterion criterion, Criterion another) {
		if(criterion == null)
			return another;
		return Restrictions.or(criterion, another);
	}
	
	Criterion andCriterion(Criterion criterion, Criterion another) {
		if(criterion == null)
			return another;
		return Restrictions.and(criterion, another);
	}
	
	private Criteria createFilterCriteria() {

		Criteria c = session.createCriteria(clazz);
		Criterion criterion = null;
		
		for(Entry<String, Object> e:filterEMap.entrySet())
		{
			String[] entries = e.getKey().split("\\.");

			if(entries.length == 2)
			{
				c.createAlias(entries[0], entries[0]  + "_a");
				if(e.getValue() != null)
					criterion = andCriterion(criterion, Restrictions.eq(entries[0]  + "_a."  + entries[1], e.getValue()));
				else
					criterion = andCriterion(criterion, Restrictions.isNull(entries[0]  + "_a."  + entries[1]));
			}
			else {
				if(e.getValue() != null)
					criterion = andCriterion(criterion, Restrictions.eq(e.getKey(), e.getValue()));
				else
					criterion = andCriterion(criterion, Restrictions.isNull(e.getKey()));
			}
		}

		for(Entry<String, Object> e:filterNEMap.entrySet())
		{
			String[] entries = e.getKey().split("\\.");

			if(entries.length == 2)
			{
				c.createAlias(entries[0], entries[0]  + "_a");
				if(e.getValue() != null)
					criterion = andCriterion(criterion, Restrictions.ne(entries[0]  + "_a."  + entries[1], e.getValue()));
				else
					criterion = andCriterion(criterion, Restrictions.isNotNull(entries[0]  + "_a."  + entries[1]));
			}
			else {
				if(e.getValue() != null)
					criterion = andCriterion(criterion, Restrictions.ne(e.getKey(), e.getValue()));
				else
					criterion = andCriterion(criterion, Restrictions.isNotNull(e.getKey()));
			}
		}

		for(Entry<String, Object[]> e:filterBetweenMap.entrySet())
		{
			Object lo = e.getValue()[0];
			Object hi = e.getValue()[1];

			String[] entries = e.getKey().split("\\.");

			if(entries.length == 2)
			{
				c.createAlias(entries[0], entries[0]  + "_a");
				criterion = andCriterion(criterion, Restrictions.between(entries[0]  + "_a."  + entries[1],lo,hi));
			}
			else
				criterion = andCriterion(criterion, Restrictions.between(e.getKey(), lo, hi));
		}
		for(Entry<String, Object> e:likeMap.entrySet())
		{
			String[] entries = e.getKey().split("\\.");

			if(entries.length == 2)
			{
				c.createAlias(entries[0], entries[0]  + "_a");
				if(e.getValue() != null)
					criterion = andCriterion(criterion, Restrictions.like(entries[0]  + "_a."  + entries[1], e.getValue()));
				else
					criterion = andCriterion(criterion, Restrictions.isNull(entries[0]  + "_a."  + entries[1]));
			}
			else {
				if(e.getValue() != null)
					criterion = andCriterion(criterion, Restrictions.like(e.getKey(), e.getValue()));
				else
					criterion = andCriterion(criterion, Restrictions.isNull(e.getKey()));
			}
		}
		//// Added support for the OR semantic
		for(Pair e : filterOrEList)
		{
			String[] entries = e.getKey().split("\\.");

			if(entries.length == 2)
			{
				c.createAlias(entries[0], entries[0]  + "_a");
				if(e.getValue() != null)
					criterion = orCriterion(criterion, Restrictions.eq(entries[0]  + "_a."  + entries[1], e.getValue()));
				else
					criterion = orCriterion(criterion, Restrictions.isNull(entries[0]  + "_a."  + entries[1]));
			}
			else {
				if(e.getValue() != null)
					criterion = orCriterion(criterion, Restrictions.eq(e.getKey(), e.getValue()));
				else
					criterion = orCriterion(criterion, Restrictions.isNull(e.getKey()));
			}
		}

		for(Pair e : filterOrNEList)
		{
			String[] entries = e.getKey().split("\\.");

			if(entries.length == 2)
			{
				c.createAlias(entries[0], entries[0]  + "_a");
				if(e.getValue() != null)
					criterion = orCriterion(criterion, Restrictions.ne(entries[0]  + "_a."  + entries[1], e.getValue()));
				else
					criterion = orCriterion(criterion, Restrictions.isNotNull(entries[0]  + "_a."  + entries[1]));
			}
			else {
				if(e.getValue() != null)
					criterion = orCriterion(criterion, Restrictions.ne(e.getKey(), e.getValue()));
				else
					criterion = orCriterion(criterion, Restrictions.isNotNull(e.getKey()));
			}
		}

		for(Pair e : filterOrBetweenList)
		{
			Object lo = ((Object[])e.getValue())[0];
			Object hi = ((Object[])e.getValue())[1];

			String[] entries = e.getKey().split("\\.");

			if(entries.length == 2)
			{
				c.createAlias(entries[0], entries[0]  + "_a");
				criterion = orCriterion(criterion, Restrictions.between(entries[0]  + "_a."  + entries[1],lo,hi));
			}
			else
				criterion = orCriterion(criterion, Restrictions.between(e.getKey(), lo, hi));
		}
	
		for(Pair e : filterOrLikeList)
		{
			String[] entries = e.getKey().split("\\.");

			if(entries.length == 2)
			{
				c.createAlias(entries[0], entries[0]  + "_a");
				if(e.getValue() != null)
					criterion = orCriterion(criterion, Restrictions.like(entries[0]  + "_a."  + entries[1], e.getValue()));
				else
					criterion = orCriterion(criterion, Restrictions.isNull(entries[0]  + "_a."  + entries[1]));
			}
			else {
				if(e.getValue() != null)
					criterion = orCriterion(criterion, Restrictions.like(e.getKey(), e.getValue()));
				else
					criterion = orCriterion(criterion, Restrictions.isNull(e.getKey()));
			}
		}
		if(criterion != null)
			c.add(criterion);
		
		for(String ascToken : ascList) {
			c.addOrder(Order.asc(ascToken));
		}
		
		for(String descToken : descList) {
			c.addOrder(Order.desc(descToken));
		}		
		
		c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return c;
	}
}

