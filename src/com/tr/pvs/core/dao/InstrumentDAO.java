package com.tr.pvs.core.dao;
// default package

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tr.pvs.core.dbo.Instrument;

/**
 	* A data access object (DAO) providing persistence and search support for Instrument entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Instrument
  * @author MyEclipse Persistence Tools 
 */
public class InstrumentDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(InstrumentDAO.class);
		//property constants
	public static final String IDENTIFIER = "identifier";
	public static final String CURRENCY = "currency";



	protected void initDao() {
		//do nothing
	}
    
    public void save(Instrument transientInstance) {
        log.debug("saving Instrument instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Instrument persistentInstance) {
        log.debug("deleting Instrument instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Instrument findById( java.lang.Integer id) {
        log.debug("getting Instrument instance with id: " + id);
        try {
            Instrument instance = (Instrument) getHibernateTemplate()
                    .get("com.tr.pvs.core.dbo.Instrument", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Instrument instance) {
        log.debug("finding Instrument instance by example");
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
      log.debug("finding Instrument instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Instrument as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByIdentifier(Object identifier
	) {
		return findByProperty(IDENTIFIER, identifier
		);
	}
	
	public List findByCurrency(Object currency
	) {
		return findByProperty(CURRENCY, currency
		);
	}
	
	public Instrument findByIdentifierOne(String identifier) {
		List<Instrument> instList = findByIdentifier(identifier);
		if(instList.size() > 0) {
			return instList.get(0);
		} else {
			return null;
		}
	}

	public List findAll() {
		log.debug("finding all Instrument instances");
		try {
			String queryString = "from Instrument";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Instrument merge(Instrument detachedInstance) {
        log.debug("merging Instrument instance");
        try {
            Instrument result = (Instrument) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Instrument instance) {
        log.debug("attaching dirty Instrument instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Instrument instance) {
        log.debug("attaching clean Instrument instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static InstrumentDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (InstrumentDAO) ctx.getBean("InstrumentDAO");
	}
}