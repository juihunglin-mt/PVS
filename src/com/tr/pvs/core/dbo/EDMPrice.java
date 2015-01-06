package com.tr.pvs.core.dbo;
// default package

import java.sql.Timestamp;


/**
 * EDMPrice entity. @author MyEclipse Persistence Tools
 */

public class EDMPrice  implements java.io.Serializable {


    // Fields    

     private Integer edmId;
     private Instrument instrument;
     private Timestamp tradeDate;
     private String value;
     private String field;


    // Constructors

    /** default constructor */
    public EDMPrice() {
    }

	/** minimal constructor */
    public EDMPrice(Instrument instrument, Timestamp tradeDate, String field) {
        this.instrument = instrument;
        this.tradeDate = tradeDate;
        this.field = field;
    }
    
    /** full constructor */
    public EDMPrice(Instrument instrument, Timestamp tradeDate, String value, String field) {
        this.instrument = instrument;
        this.tradeDate = tradeDate;
        this.value = value;
        this.field = field;
    }

   
    // Property accessors

    public Integer getEdmId() {
        return this.edmId;
    }
    
    public void setEdmId(Integer edmId) {
        this.edmId = edmId;
    }

    public Instrument getInstrument() {
        return this.instrument;
    }
    
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Timestamp getTradeDate() {
        return this.tradeDate;
    }
    
    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public String getField() {
        return this.field;
    }
    
    public void setField(String field) {
        this.field = field;
    }
   








}