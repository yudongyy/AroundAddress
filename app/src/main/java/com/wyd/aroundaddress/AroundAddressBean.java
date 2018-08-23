package com.wyd.aroundaddress;

import java.io.Serializable;
import java.util.List;

public class AroundAddressBean implements Serializable {

    public String status;
    public String count;
    public String info;
    public String infocode;
    public List<PoisBean> pois;


    public static class PoisBean implements Serializable {
        public String id;
        public String name;
        public String type;
        public String typecode;
        public String address;
        public String location;
        public String tel;
        public String pcode;
        public String pname;
        public String citycode;
        public String cityname;
        public String adcode;
        public String adname;
        public String shopinfo;
        public String gridcode;
        public String distance;
        public String business_area;
        public String match;
        public String recommend;
        public String indoor_map;
        public String groupbuy_num;
        public String discount_num;
    }
}
