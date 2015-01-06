package com.tr.pvs.core.dbo;
// default package

import java.util.HashSet;
import java.util.Set;


/**
 * Instrument entity. @author MyEclipse Persistence Tools
 */

public class Instrument  implements java.io.Serializable {


    // Fields    

     private Integer instrumentId;
     private String identifier;
     private String currency;
     private Set BBGPrices = new HashSet(0);
     private Set EDMPrices = new HashSet(0);


    // Constructors

    /** default constructor */
    public Instrument() {
    }

	/** minimal constructor */
    public Instrument(String identifier, String currency) {
        this.identifier = identifier;
        this.currency = currency;
    }
    
    /** full constructor */
    public Instrument(String identifier, String currency, Set BBGPrices, Set EDMPrices) {
        this.identifier = identifier;
        this.currency = currency;
        this.BBGPrices = BBGPrices;
        this.EDMPrices = EDMPrices;
    }

   
    // Property accessors

    public Integer getInstrumentId() {
        return this.instrumentId;
    }
    
    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getIdentifier() {
        return this.identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set getBBGPrices() {
        return this.BBGPrices;
    }
    
    public void setBBGPrices(Set BBGPrices) {
        this.BBGPrices = BBGPrices;
    }

    public Set getEDMPrices() {
        return this.EDMPrices;
    }
    
    public void setEDMPrices(Set EDMPrices) {
        this.EDMPrices = EDMPrices;
    }
   








}