package com.idisc.web.pu.delegates;

import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Comment;
import com.idisc.pu.entities.Country;
import com.idisc.pu.entities.Feeduser;
import com.idisc.pu.entities.Gender;
import com.idisc.pu.entities.Howdidyoufindus;
import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.bc.jpa.PersistenceMetaData;
import com.bc.jpa.fk.EnumReferences;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @(#)FeeduserOutput.java   28-Jan-2015 13:19:56
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class FeeduserOutput extends Feeduser {

    private Feeduser feeduser;

    public FeeduserOutput() { }
    
    public FeeduserOutput(Feeduser feeduser) {
        this.feeduser = feeduser;
    }

    @Override
    public Integer getFeeduserid() {
        return feeduser.getFeeduserid();
    }

    @Override
    public void setFeeduserid(Integer feeduserid) {
        feeduser.setFeeduserid(feeduserid);
    }

    @Override
    public String getEmailAddress() {
        return feeduser.getEmailAddress();
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        feeduser.setEmailAddress(emailAddress);
    }

    @Override
    public String getLastName() {
        return feeduser.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        feeduser.setLastName(lastName);
    }

    @Override
    public String getFirstName() {
        return feeduser.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        feeduser.setFirstName(firstName);
    }

    @Override
    public Date getDateOfBirth() {
        return feeduser.getDateOfBirth();
    }

    @Override
    public void setDateOfBirth(Date dateOfBirth) {
        feeduser.setDateOfBirth(dateOfBirth);
    }

    @Override
    public String getPhoneNumber1() {
        return feeduser.getPhoneNumber1();
    }

    @Override
    public void setPhoneNumber1(String phoneNumber1) {
        feeduser.setPhoneNumber1(phoneNumber1);
    }

    @Override
    public String getPhoneNumber2() {
        return feeduser.getPhoneNumber2();
    }

    @Override
    public void setPhoneNumber2(String phoneNumber2) {
        feeduser.setPhoneNumber2(phoneNumber2);
    }

    @Override
    public String getFax() {
        return feeduser.getFax();
    }

    @Override
    public void setFax(String fax) {
        feeduser.setFax(fax);
    }

    @Override
    public String getStateOrRegion() {
        return feeduser.getStateOrRegion();
    }

    @Override
    public void setStateOrRegion(String stateOrRegion) {
        feeduser.setStateOrRegion(stateOrRegion);
    }

    @Override
    public String getCity() {
        return feeduser.getCity();
    }

    @Override
    public void setCity(String city) {
        feeduser.setCity(city);
    }

    @Override
    public String getCounty() {
        return feeduser.getCounty();
    }

    @Override
    public void setCounty(String county) {
        feeduser.setCounty(county);
    }

    @Override
    public String getStreetAddress() {
        return feeduser.getStreetAddress();
    }

    @Override
    public void setStreetAddress(String streetAddress) {
        feeduser.setStreetAddress(streetAddress);
    }

    @Override
    public String getPostalCode() {
        return feeduser.getPostalCode();
    }

    @Override
    public void setPostalCode(String postalCode) {
        feeduser.setPostalCode(postalCode);
    }

    @Override
    public String getImage1() {
        return feeduser.getImage1();
    }

    @Override
    public void setImage1(String image1) {
        feeduser.setImage1(image1);
    }

    @Override
    public Date getDatecreated() {
        return feeduser.getDatecreated();
    }

    @Override
    public void setDatecreated(Date datecreated) {
        feeduser.setDatecreated(datecreated);
    }

    @Override
    public Date getTimemodified() {
        return feeduser.getTimemodified();
    }

    @Override
    public void setTimemodified(Date timemodified) {
        feeduser.setTimemodified(timemodified);
    }

    @Override
    public String getExtradetails() {
        return feeduser.getExtradetails();
    }

    @Override
    public void setExtradetails(String extradetails) {
        feeduser.setExtradetails(extradetails);
    }

    @Override
    public Gender getGender() {
        return feeduser.getGender();
    }

    @Override
    public void setGender(Gender gender) {
        feeduser.setGender(gender);
    }

    @Override
    public Country getCountry() {
        return feeduser.getCountry();
    }

    @Override
    public void setCountry(Country country) {
        feeduser.setCountry(country);
    }

    @Override
    public Howdidyoufindus getHowDidYouFindUs() {
        return feeduser.getHowDidYouFindUs();
    }

    @Override
    public void setHowDidYouFindUs(Howdidyoufindus howDidYouFindUs) {
        feeduser.setHowDidYouFindUs(howDidYouFindUs);
    }

    public Feeduser getFeeduser() {
        return feeduser;
    }

    public void setFeeduser(Feeduser feeduser) {
        this.feeduser = feeduser;
    }

    @Override
    public int hashCode() {
        return feeduser.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return feeduser.equals(object);
    }

    @Override
    public String toString() {
        
        final ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
        
        EntityController<Feeduser, Object> userCtrl = factory.getEntityController(Feeduser.class);
        
        Map map = userCtrl.toMap(feeduser, false);
        
        EnumReferences refs = factory.getEnumReferences();
        
        HashMap update = new HashMap();
        PersistenceMetaData metaData = factory.getMetaData();
        for(Object key:map.keySet()) {
            String col = key.toString();
            if(refs.isReference(col)) {
                Class enumType = refs.getEnumType(col);
                Enum en = (Enum)enumType.getEnumConstants()[0];
                String table = refs.getTableName(en);
                String database = this.getDatabaseName(metaData, table);
                EntityController refCtrl = factory.getEntityController(database, table);
                Object entityValue = map.get(key);
                Object idValue = refCtrl.getValue(entityValue, refCtrl.getIdColumnName());
                update.put(key, idValue);
            }
        }
        
        map.putAll(update);
        
        return map.toString();
    }

    public Map toMap(Feeduser user) {
        
        final ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
        
        EntityController<Feeduser, Object> userCtrl = factory.getEntityController(Feeduser.class);
        
        Map map = userCtrl.toMap(user, false);
        
        EnumReferences refs = factory.getEnumReferences();
        
        HashMap update = new HashMap();
        PersistenceMetaData metaData = factory.getMetaData();
        for(Object key:map.keySet()) {
            String col = key.toString();
            if(refs.isReference(col)) {
                Class enumType = refs.getEnumType(col);
                Enum en = (Enum)enumType.getEnumConstants()[0];
                String table = refs.getTableName(en);
                String database = this.getDatabaseName(metaData, table);
                EntityController refCtrl = factory.getEntityController(database, table);
                Object entityValue = map.get(key);
                Object idValue = refCtrl.getValue(entityValue, refCtrl.getIdColumnName());
                update.put(key, idValue);
            }
        }
        
        map.putAll(update);
        
        return map;
    }
    
    private String getDatabaseName(PersistenceMetaData metaData, String table) {
        String database = null;
        outer:
        for(String puName:metaData.getPersistenceUnitNames()) {
            Class [] classes = metaData.getEntityClasses(puName);
            for(Class aClass:classes) {
                if(metaData.getTableName(aClass).equals(table)) {
                    database = metaData.getDatabaseName(aClass);
                    break outer;
                }
            }
        }
        return database;
    }
}
