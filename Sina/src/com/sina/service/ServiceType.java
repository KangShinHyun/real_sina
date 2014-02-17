package com.sina.service;

import java.util.Hashtable;

public class ServiceType {

	public static final String ROOT_DOMAIN = "http://kangshinhyun2.cafe24.com/";

    public static final int MSG_LOGIN = 1;
    public static final int MSG_JOIN = 2;
    public static final int MSG_JOIN_DOUBLE_ID = 3;

    private static final String URL_LOGIN 				 = 	 ROOT_DOMAIN + "login.php";
    private static final String URL_JOIN 				 =   ROOT_DOMAIN + "join.php";
    private static final String URL_JOIN_DOUBLE_ID 		 =   ROOT_DOMAIN + "join_double_id.php";

    private Hashtable<Integer, String> msgURLTbl = new Hashtable<Integer, String>();

    private static ServiceType msgState = new ServiceType();

    private ServiceType() {
        msgURLTbl.put(new Integer(MSG_LOGIN), URL_LOGIN);
        msgURLTbl.put(new Integer(MSG_JOIN), URL_JOIN);
        msgURLTbl.put(new Integer(MSG_JOIN_DOUBLE_ID), URL_JOIN_DOUBLE_ID);
    }

    public static ServiceType getInstance() {
        return msgState;
    }

    public String getUrl(int serviceType) {
        return msgURLTbl.get(serviceType);
    }
}
