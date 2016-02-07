package com.abhishek.travel;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Created by dell on 2/6/2016.
 */
public class CurrencyStore extends GenericJson {
    @Key("_id")
    private String id;
    @Key("_id")
    private String country;
    @Key
    private String currency;
    @Key
    private Double currencyValue;
    @Key("_kmd")
    private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl; //Kinvey access control, OPTIONAL

    public CurrencyStore(){}
}
