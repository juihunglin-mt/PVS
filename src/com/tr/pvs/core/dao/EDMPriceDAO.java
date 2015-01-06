package com.tr.pvs.core.dao;
// default package

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tr.pvs.core.bean.Edmenv;
import com.tr.pvs.core.bean.Sqlloader;
import com.tr.pvs.core.bean.TimeUtil;
import com.tr.pvs.core.dbo.EDMPrice;

/**
 	* A data access object (DAO) providing persistence and search support for EDMPrice entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .EDMPrice
  * @author MyEclipse Persistence Tools 
 */
public class EDMPriceDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(EDMPriceDAO.class);
		//property constants
	public static final String VALUE = "value";
	public static final String FIELD = "field";



	protected void initDao() {
		//do nothing
	}
    
    public void save(EDMPrice transientInstance) {
        log.debug("saving EDMPrice instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void deleteByField(String field) {
        log.debug("deleting EDMPrice instance");
        try {
        	Session session = getSession();
        	
        	String queryString = "DELETE PVS_EDM where FIELD = '" + field + "'";
        	
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
        log.debug("truncate EDMPrice instance");
        try {
        	Session session = getSession();
        	
        	String queryString = "TRUNCATE TABLE PVS_EDM";
        	
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
    
	public void delete(EDMPrice persistentInstance) {
        log.debug("deleting EDMPrice instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public EDMPrice findById( java.lang.Integer id) {
        log.debug("getting EDMPrice instance with id: " + id);
        try {
            EDMPrice instance = (EDMPrice) getHibernateTemplate()
                    .get("com.tr.pvs.core.dbo.EDMPrice", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(EDMPrice instance) {
        log.debug("finding EDMPrice instance by example");
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
      log.debug("finding EDMPrice instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from EDMPrice as model where model." 
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
		log.debug("finding all EDMPrice instances");
		
		String ids = "";
		for(Integer id : instIds) {
			if(ids.lastIndexOf(",") != ids.length() -1) ids += ",";
			ids += id;
		}
		
		try {
			String queryString = "from EDMPrice as model "
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
		log.debug("finding all EDMPrice instances");
		try {
			String queryString = "from EDMPrice";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public EDMPrice merge(EDMPrice detachedInstance) {
        log.debug("merging EDMPrice instance");
        try {
            EDMPrice result = (EDMPrice) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EDMPrice instance) {
        log.debug("attaching dirty EDMPrice instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(EDMPrice instance) {
        log.debug("attaching clean EDMPrice instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void createSqlldrFileAndTrigger(String filename, List<EDMPrice> priceList) {
		log.info("start create sqlldr data file");
		
		Edmenv edmenv = new Edmenv();
		Sqlloader sqlldr = new Sqlloader();
		TimeUtil tu = new TimeUtil();
		
		ResourceBundle rb = ResourceBundle.getBundle("pvs_" + edmenv.getEnvironment());
		String sqlldrPath = rb.getString("sqlldr_upload_path");
		String osSystem = rb.getString("os_system");
		
		List<String> dataLines = new ArrayList<String>();
		
		String fileName = "BBGPRICE_" + System.currentTimeMillis();
		
		boolean appendType = false;
		if(priceList != null && priceList.size() > 0) {
			for(int i = 0; i < priceList.size(); i++) {
				EDMPrice price = priceList.get(i);
				String dataline = "," + 
						tu.getDateFormat(price.getTradeDate().getTime(), "yyyy/MM/dd HH:mm:ss") + "," +
						price.getValue() + "," +
						price.getField() + "," +
						price.getInstrument().getInstrumentId() + ",";
				dataLines.add(dataline);
				
				if(i % 50000 == 0) {
					sqlldr.createDataFile(sqlldrPath, fileName, appendType, dataLines);
					if(!appendType) {
						appendType = true;
					}
					dataLines.clear();
				}
			}
			if(dataLines.size() > 0) {
				sqlldr.createDataFile(sqlldrPath, fileName, appendType, dataLines);
				dataLines.clear();
			}
			
			String tableFormat = "EDM_ID \"EDM_SEQ.NEXTVAL\",TRADE_DATE DATE \"yyyy/MM/dd HH24:MI:SS\",VALUE,FIELD,INSTRUMENT_ID";
			
			sqlldr.createCtrlFile(sqlldrPath, fileName, "PVS_EDM", tableFormat);
			
			sqlldr.createCmdOrShFile(osSystem, sqlldrPath, fileName);
			
			log.info("trigger sqlldr");
			//String commandLine = sqlldr.getCommandLine(osSystem, sqlldrPath, fileName);
			
			String osType = "sh";
			if(osSystem.equals("windows")) {
				osType = "bat";
			} else {
				sqlldrPath = "/bin/sh "+sqlldrPath;
			}
			
			Process p = null;
			try {
//				String commandLine = sqlldr.getCommandLine(osSystem, sqlldrPath, fileName);
				p = Runtime.getRuntime().exec(sqlldrPath+fileName+"."+osType);
				
				BufferedReader stdInput = new BufferedReader(new 
				InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(new 
				InputStreamReader(p.getErrorStream()));

				// read the output from the command
				log.info("SQLLoader standard output :");
				String s = null;
				while ((s = stdInput.readLine()) != null) {
//					log.info(s);
				}

				// read any errors from the attempted command
				log.info("SQLLoader error output (if any):");
				while ((s = stdError.readLine()) != null) {
					log.info(s);
				}
				
				p.waitFor();
			} catch (Exception e) {
				log.info("sqlldr runtime error!", e);
			}
			
			log.info("delete ctl file");
			
			try {
				File file = new File(sqlldrPath + fileName + "." + osType);
				file.delete();
			} catch(Exception e) {
				log.error("delete failed", e);
			}
			
			try {
				File file = new File(sqlldrPath + fileName + ".data");
				file.delete();
			} catch(Exception e) {
				log.error("delete failed", e);
			}
			
			try {
				File file = new File(sqlldrPath + fileName + ".ctl");
				file.delete();
			} catch(Exception e) {
				log.error("delete failed", e);
			}
			
			try {
				File file = new File(sqlldrPath + sqlldrPath + ".bad");
				file.delete();
			} catch(Exception e) {
				log.error("delete failed", e);
			}
			log.info("end sqlldr save to database");
		}
	}

	public static EDMPriceDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (EDMPriceDAO) ctx.getBean("EDMPriceDAO");
	}
}