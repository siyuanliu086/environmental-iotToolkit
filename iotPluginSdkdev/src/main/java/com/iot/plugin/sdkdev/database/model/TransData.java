package com.iot.plugin.sdkdev.database.model;

import java.io.Serializable;
import java.util.Date;

public class TransData implements Serializable{
    private static final long serialVersionUID = 3117366918746911198L;
    
    private String id;
    private String point_id;
    private String point_name;// '站点名称',
    private String device_id;// '设备编号',
    private String device_name;// '设备名称',
    private String region_code;// '行政区划编码',
    private String region_name;// '行政区划名称',
    private String sourcepoint_id;// '原始数据站点编号',
    private Date monitor_time;// '推送时间',
    private Date create_time;// '添加时间',
    private String time_key;// '时间标签',
    private String first_pollutant;// '首要污染物',
    private String level;// '空气质量级别',
    private Double aqi;// 'AOI',
    private Double so2;// '二氧化硫',
    private Double co;// '一氧化碳',
    private Double no;// '一氧化氮',
    private Double no2;// 'no2',
    private Double pm25;// 'pm2.5',
    private Double pm2524;// 'pm2524小时滑动值',
    private Double pm10;// 'pm10',
    private Double pm1024;// 'pm1024小时滑动值',
    private Double o3;// '臭氧',
    private Double o38;// '臭氧8小时滑动',
    private Double tsp;// '总悬浮颗粒物',
    private Double noise;// '噪声',
    private Double vocs;// '挥发性有机物',
    private Double tvocs;// '总挥发性有机物',
    private Double nh3;// '氮气',
    private Double gas;// '可燃气',
    private Double coc12;// '光气',
    private Double ch4s;// '甲硫醇',
    private Double h2s;// '硫化氢',
    private Double cl2;// '氯气',
    private Double secaline;// '三甲胺',
    private Double c2h6s;// '甲硫醚',
    private Double c2h6s2;// '二甲二硫',
    private Double cs2;// '二硫化碳',
    private Double c8h8;// '苯乙烯',
    private Double wd;// '风向',
    private Double ws;// '风速',
    private Double tem;// '温度',
    private Double pa;// '气压',
    private Double rh;// '湿度',
    private Double v1;// '预留1',
    private Double v2;// '预留2',
    private Double v3;// '预留3',
    private Double v4;// '预留4',
    private Double v5;// '预留5',

    private String tableName;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoint_id() {
        return point_id;
    }

    public void setPoint_id(String point_id) {
        this.point_id = point_id;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getSourcepoint_id() {
        return sourcepoint_id;
    }

    public void setSourcepoint_id(String sourcepoint_id) {
        this.sourcepoint_id = sourcepoint_id;
    }

    public Date getMonitor_time() {
        return monitor_time;
    }

    public void setMonitor_time(Date monitor_time) {
        this.monitor_time = monitor_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getTime_key() {
        return time_key;
    }

    public void setTime_key(String time_key) {
        this.time_key = time_key;
    }

    public String getFirst_pollutant() {
        return first_pollutant;
    }

    public void setFirst_pollutant(String first_pollutant) {
        this.first_pollutant = first_pollutant;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    
    public Double getAqi() {
        return aqi;
    }

    
    public void setAqi(Double aqi) {
        this.aqi = aqi;
    }

    
    public Double getSo2() {
        return so2;
    }

    
    public void setSo2(Double so2) {
        this.so2 = so2;
    }

    
    public Double getCo() {
        return co;
    }

    
    public void setCo(Double co) {
        this.co = co;
    }

    
    public Double getNo() {
        return no;
    }

    
    public void setNo(Double no) {
        this.no = no;
    }

    
    public Double getNo2() {
        return no2;
    }

    
    public void setNo2(Double no2) {
        this.no2 = no2;
    }

    
    public Double getPm25() {
        return pm25;
    }

    
    public void setPm25(Double pm25) {
        this.pm25 = pm25;
    }

    
    public Double getPm2524() {
        return pm2524;
    }

    
    public void setPm2524(Double pm2524) {
        this.pm2524 = pm2524;
    }

    
    public Double getPm10() {
        return pm10;
    }

    
    public void setPm10(Double pm10) {
        this.pm10 = pm10;
    }

    
    public Double getPm1024() {
        return pm1024;
    }

    
    public void setPm1024(Double pm1024) {
        this.pm1024 = pm1024;
    }

    
    public Double getO3() {
        return o3;
    }

    
    public void setO3(Double o3) {
        this.o3 = o3;
    }

    
    public Double getO38() {
        return o38;
    }

    
    public void setO38(Double o38) {
        this.o38 = o38;
    }

    
    public Double getTsp() {
        return tsp;
    }

    
    public void setTsp(Double tsp) {
        this.tsp = tsp;
    }

    
    public Double getNoise() {
        return noise;
    }

    
    public void setNoise(Double noise) {
        this.noise = noise;
    }

    
    public Double getVocs() {
        return vocs;
    }

    
    public void setVocs(Double vocs) {
        this.vocs = vocs;
    }

    
    public Double getTvocs() {
        return tvocs;
    }

    
    public void setTvocs(Double tvocs) {
        this.tvocs = tvocs;
    }

    
    public Double getNh3() {
        return nh3;
    }

    
    public void setNh3(Double nh3) {
        this.nh3 = nh3;
    }

    
    public Double getGas() {
        return gas;
    }

    
    public void setGas(Double gas) {
        this.gas = gas;
    }

    
    public Double getCoc12() {
        return coc12;
    }

    
    public void setCoc12(Double coc12) {
        this.coc12 = coc12;
    }

    
    public Double getCh4s() {
        return ch4s;
    }

    
    public void setCh4s(Double ch4s) {
        this.ch4s = ch4s;
    }

    
    public Double getH2s() {
        return h2s;
    }

    
    public void setH2s(Double h2s) {
        this.h2s = h2s;
    }

    
    public Double getCl2() {
        return cl2;
    }

    
    public void setCl2(Double cl2) {
        this.cl2 = cl2;
    }

    
    public Double getSecaline() {
        return secaline;
    }

    
    public void setSecaline(Double secaline) {
        this.secaline = secaline;
    }

    
    public Double getC2h6s() {
        return c2h6s;
    }

    
    public void setC2h6s(Double c2h6s) {
        this.c2h6s = c2h6s;
    }

    
    public Double getC2h6s2() {
        return c2h6s2;
    }

    
    public void setC2h6s2(Double c2h6s2) {
        this.c2h6s2 = c2h6s2;
    }

    
    public Double getCs2() {
        return cs2;
    }

    
    public void setCs2(Double cs2) {
        this.cs2 = cs2;
    }

    
    public Double getC8h8() {
        return c8h8;
    }

    
    public void setC8h8(Double c8h8) {
        this.c8h8 = c8h8;
    }

    
    public Double getWd() {
        return wd;
    }

    
    public void setWd(Double wd) {
        this.wd = wd;
    }

    
    public Double getWs() {
        return ws;
    }

    
    public void setWs(Double ws) {
        this.ws = ws;
    }

    
    public Double getTem() {
        return tem;
    }

    
    public void setTem(Double tem) {
        this.tem = tem;
    }

    
    public Double getPa() {
        return pa;
    }

    
    public void setPa(Double pa) {
        this.pa = pa;
    }

    
    public Double getRh() {
        return rh;
    }

    
    public void setRh(Double rh) {
        this.rh = rh;
    }

    
    public Double getV1() {
        return v1;
    }

    
    public void setV1(Double v1) {
        this.v1 = v1;
    }

    
    public Double getV2() {
        return v2;
    }

    
    public void setV2(Double v2) {
        this.v2 = v2;
    }

    
    public Double getV3() {
        return v3;
    }

    
    public void setV3(Double v3) {
        this.v3 = v3;
    }

    
    public Double getV4() {
        return v4;
    }

    
    public void setV4(Double v4) {
        this.v4 = v4;
    }

    
    public Double getV5() {
        return v5;
    }

    
    public void setV5(Double v5) {
        this.v5 = v5;
    }

    
    public String getTableName() {
        return tableName;
    }

    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return point_name + "\t" + region_code + "\t" + monitor_time;
    }

}
