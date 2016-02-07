package com.abhishek.travel;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Created by dell on 2/6/2016.
 */
public class UserAds extends GenericJson {

    @Key("_id")
    private String id;
    @Key
    private String fromCountry;
    @Key
    private String toCountry;
    @Key
    private String userID;
    @Key
    private String fromCurrency;
    @Key
    private String toCurrency;
    @Key
    private Integer currencyValue;
    @Key("_kmd")
    private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl; //Kinvey access control, OPTIONAL

    public UserAds(){}  //GenericJson classes must have a public empty constructor
}
