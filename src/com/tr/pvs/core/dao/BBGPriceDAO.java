package com.tr.pvs.core.dao;
// default package

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tr.pvs.core.dbo.BBGPrice;

/**
 	* A data access object (DAO) providing persistence and search support for BBGPrice entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .BBGPrice
  * @author MyEclipse Persistence Tools 
 */
public class BBGPriceDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(BBGPriceDAO.class);
		//property constants
	public static final String VALUE = "value";
	public static final String FIELD = "field";



	protected void initDao() {
		//do nothing
	}
    
    public void save(BBGPrice transientInstance) {
        log.debug("saving BBGPrice instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void deleteByField(String field) {
        log.debug("deleting BBGPrice instance");
        try {
        	Session session = getSession();
        	
        	String queryString = "DELETE PVS_BBG where FIELD = '" + field + "'";
        	
        	Query tempQuery = session.createSQLQuery(queryString);
        	
        	tempQuery.executeUpdate();
        	
        	session.flush();
            session.clear();
    		session.close();
        	
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public void truncate() {
        log.debug("truncate BBGPrice instance");
        try {
        	Session session = getSession();
        	
        	String queryString = "TRUNCATE TABLE PVS_BBG";
        	
        	Query tempQuery = session.createSQLQuery(queryString);
        	
        	tempQuery.executeUpdate();
        	
        	session.flush();
            session.clear();
    		session.close();
        	
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    
	public void delete(BBGPrice persistentInstance) {
        log.debug("deleting BBGPrice instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BBGPrice findById( java.lang.Integer id) {
        log.debug("getting BBGPrice instance with id: " + id);
        try {
            BBGPrice instance = (BBGPrice) getHibernateTemplate()
                    .get("com.tr.pvs.core.dbo.BBGPrice", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BBGPrice instance) {
        log.debug("finding BBGPrice instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding BBGPrice instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BBGPrice as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByValue(Object value
	) {
		return findByProperty(VALUE, value
		);
	}
	
	public List findByField(Object field
	) {
		return findByProperty(FIELD, field
		);
	}
	

	public List findByInstrumentAndField(List<Integer> instIds, String field) {
		log.debug("finding all BBGPrice instances");
		
		String ids = "";
		for(Integer id : instIds) {
			if(ids.lastIndexOf(",") != ids.length() -1) ids += ",";
			ids += id;
		}
		
		try {
			String queryString = "from BBGPrice as model "
					+ "left join fetch model.instrument as inst "
					+ "where model.field = ? and inst.instrumentId in ( " + ids + " ) "
							+ "order by inst.instrumentId, model.tradeDate desc";
		 	return getHibernateTemplate().find(queryString, field);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	public List findAll() {
		log.debug("finding all BBGPrice instances");
		try {
			String queryString = "from BBGPrice";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BBGPrice merge(BBGPrice detachedInstance) {
        log.debug("merging BBGPrice instance");
        try {
            BBGPrice result = (BBGPrice) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BBGPrice instance) {
        log.debug("attaching dirty BBGPrice instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BBGPrice instance) {
        log.debug("attaching clean BBGPrice instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static BBGPriceDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (BBGPriceDAO) ctx.getBean("BBGPriceDAO");
	}
}