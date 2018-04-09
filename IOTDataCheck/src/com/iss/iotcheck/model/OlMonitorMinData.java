package com.iss.iotcheck.model;

import java.util.Date;

public class OlMonitorMinData implements IotData{
    private String id;

    private String pointId;

    private String pointName;

    private String deviceId;

    private String deviceName;

    private String regionCode;

    private String regionName;

    private String sourcepointId;

    private Date monitorTime;

    private Date createTime;

    private String firstPollutant;

    private String level;

    private Double aqi;

    private Double so2;

    private Double co;

    private Double no;

    private Double no2;

    private Double pm25;

    private Double pm10;

    private Double o3;

    private Double tsp;

    private Double noise;

    private Double vocs;

    private Double tvocs;

    private Double nh3;

    private Double gas;

    private Double coc12;

    private Double ch4s;

    private Double h2s;

    private Double cl2;

    private Double secaline;

    private Double c2h6s;

    private Double c2h6s2;

    private Double cs2;

    private Double c8h8;

    private Double wd;

    private Double ws;

    private Double tem;

    private Double pa;

    private Double rh;
    
    private Double v1;

    private Double v2;

    private Double v3;

    private Double v4;

    private Double v5;

    private Double o2_content;

    private Double stack_gas_velocity;

    private Double gas_tem;

    private Double gas_rh;

    private Double gas_pa;
    
    private Double waste_gas;
    
    private Double soot;
    
    private Double soot_zs;
    
    private Double so2_zs;
    
    private Double nox;
    
    private Double nox_zs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId == null ? null : pointId.trim();
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName == null ? null : pointName.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getSourcepointId() {
        return sourcepointId;
    }

    public void setSourcepointId(String sourcepointId) {
        this.sourcepointId = sourcepointId == null ? null : sourcepointId.trim();
    }

    public Date getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(Date monitorTime) {
        this.monitorTime = monitorTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFirstPollutant() {
        return firstPollutant;
    }

    public void setFirstPollutant(String firstPollutant) {
        this.firstPollutant = firstPollutant == null ? null : firstPollutant.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
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

    public Double getPm10() {
        return pm10;
    }

    public void setPm10(Double pm10) {
        this.pm10 = pm10;
    }

    public Double getO3() {
        return o3;
    }

    public void setO3(Double o3) {
        this.o3 = o3;
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

    public Double getO2_content() {
        return o2_content;
    }

    
    public void setO2_content(Double o2_content) {
        this.o2_content = o2_content;
    }

    
    public Double getStack_gas_velocity() {
        return stack_gas_velocity;
    }

    
    public void setStack_gas_velocity(Double stack_gas_velocity) {
        this.stack_gas_velocity = stack_gas_velocity;
    }

    
    public Double getGas_tem() {
        return gas_tem;
    }

    
    public void setGas_tem(Double gas_tem) {
        this.gas_tem = gas_tem;
    }

    
    public Double getGas_rh() {
        return gas_rh;
    }

    
    public void setGas_rh(Double gas_rh) {
        this.gas_rh = gas_rh;
    }

    
    public Double getGas_pa() {
        return gas_pa;
    }

    
    public void setGas_pa(Double gas_pa) {
        this.gas_pa = gas_pa;
    }

    
    public Double getWaste_gas() {
        return waste_gas;
    }

    
    public void setWaste_gas(Double waste_gas) {
        this.waste_gas = waste_gas;
    }

    
    public Double getSoot() {
        return soot;
    }

    
    public void setSoot(Double setSoot) {
        this.soot = setSoot;
    }

    
    public Double getSoot_zs() {
        return soot_zs;
    }

    
    public void setSoot_zs(Double soot_zs) {
        this.soot_zs = soot_zs;
    }

    
    public Double getSo2_zs() {
        return so2_zs;
    }

    
    public void setSo2_zs(Double so2_zs) {
        this.so2_zs = so2_zs;
    }

    
    public Double getNox() {
        return nox;
    }

    
    public void setNox(Double nox) {
        this.nox = nox;
    }

    
    public Double getNox_zs() {
        return nox_zs;
    }

    
    public void setNox_zs(Double nox_zs) {
        this.nox_zs = nox_zs;
    }

}