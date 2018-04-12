package com.iss.iotcheck.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OlMonitorWaterData implements IotData, Serializable{
    /**
     * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
     */
    private static final long serialVersionUID = 6533210358413529797L;

    private String id;

    private String pointId;

    private String pointName;

    private String sectionName;

    private String deviceId;

    private String deviceName;

    private String regionCode;

    private String regionName;

    private String sourcepointId;

    private Date monitorTime;

    private Date createTime;

    private String timeKey;

    private String firstPollutant;

    private String level;

    private String targetLevel;

    private double Cod;

    private Integer  CodLevel;

    private String  CodOverStandardState;

    private String  CodBrokenLineState;

    private double Bod;

    private Integer  BodLevel;

    private String  BodOverStandardState;

    private String  BodBrokenLineState;

    private double  NH3NH4;

    private Integer   NH3NH4Level;

    private String   NH3NH4OverStandardState;

    private String   NH3NH4BrokenLineState;

    private double KMnO4;

    private Integer  KMnO4Level;

    private String  KMnO4OverStandardState;

    private String  KMnO4BrokenLineState;

    private double PH;

    private Integer  PHLevel;

    private String  PHOverStandardState;

    private String  PHBrokenLineState;

    private double DO;

    private Integer  DOLevel;

    private String  DOOverStandardState;

    private String  DOBrokenLineState;

    private double Conductivity;

    private Integer  ConductivityLevel;

    private String  ConductivityOverStandardState;

    private String  ConductivityBrokenLineState;

    private double FTU;

    private Integer  FTULevel;

    private String  FTUOverStandardState;

    private String  FTUBrokenLineState;

    private double Temperature;

    private Integer  TemperatureLevel;

    private String  TemperatureOverStandardState;

    private String  TemperatureBrokenLineState;

    private double TP;

    private Integer  TPLevel;

    private String  TPOverStandardState;

    private String  TPBrokenLineState;

    private double TN;

    private Integer  TNLevel;

    private String  TNOverStandardState;

    private String  TNBrokenLineState;

    private BigDecimal Cu;

    private Integer  CuLevel;

    private String  CuOverStandardState;

    private String  CuBrokenLineState;

    private double Zn;

    private Integer  ZnLevel;

    private String  ZnOverStandardState;

    private String  ZnBrokenLineState;

    private double F;

    private Integer  FLevel;

    private String  FOverStandardState;

    private String  FBrokenLineState;

    private double As;

    private Integer  AsLevel;

    private String  AsOverStandardState;

    private String  AsBrokenLineState;

    private BigDecimal Hg;

    private Integer  HgLevel;

    private String  HgOverStandardState;

    private String  HgBrokenLineState;

    private BigDecimal Cd;

    private Integer  CdLevel;

    private String  CdOverStandardState;

    private String  CdBrokenLineState;

    private double Cr6;

    private Integer  Cr6Level;

    private String  Cr6OverStandardState;

    private String  Cr6BrokenLineState;

    private double Pb;

    private Integer  PbLevel;

    private String  PbOverStandardState;

    private String  PbBrokenLineState;

    private double Fe;

    private Integer  FeLevel;

    private String  FeOverStandardState;

    private String  FeBrokenLineState;

    private double CN;

    private Integer  CNLevel;

    private String  CNOverStandardState;

    private String  CNBrokenLineState;

    private double ArOH;

    private Integer  ArOHLevel;

    private String  ArOHOverStandardState;

    private String  ArOHBrokenLineState;

    private double Oil;

    private Integer  OilLevel;

    private String  OilOverStandardState;

    private String  OilBrokenLineState;

    private double anionics;

    private Integer  anionicsLevel;

    private String  anionicsOverStandardState;

    private String  anionicsBrokenLineState;

    private double sulfide;

    private Integer  sulfideLevel;

    private String  sulfideOverStandardState;

    private String  sulfideBrokenLineState;

    private double NO3N;

    private Integer  NO3NLevel;

    private String  NO3NOverStandardState;

    private String  NO3NBrokenLineState;

    private double biotoxicity;

    private Integer  biotoxicityLevel;

    private String  biotoxicityOverStandardState;

    private String  biotoxicityBrokenLineState;

    private double chlorophyla;

    private Integer  chlorophylaLevel;

    private String  chlorophylaOverStandardState;

    private String  chlorophylaBrokenLineState;

    private double algae;

    private Integer  algaeLevel;

    private String  algaeOverStandardState;

    private String  algaeBrokenLineState;

    private String standardState;//超标

    private String lineState = "false";//断线
    
    public String getId() {
        return id;
    }



    
    public void setId(String id) {
        this.id = id;
    }



    
    public String getPointId() {
        return pointId;
    }



    
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }



    
    public String getPointName() {
        return pointName;
    }



    
    public void setPointName(String pointName) {
        this.pointName = pointName;
    }



    
    public String getDeviceId() {
        return deviceId;
    }



    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }



    
    public String getDeviceName() {
        return deviceName;
    }



    
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }



    
    public String getRegionCode() {
        return regionCode;
    }



    
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }



    
    public String getRegionName() {
        return regionName;
    }



    
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }



    
    public String getSourcepointId() {
        return sourcepointId;
    }



    
    public void setSourcepointId(String sourcepointId) {
        this.sourcepointId = sourcepointId;
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



    
    public String getTimeKey() {
        return timeKey;
    }



    
    public void setTimeKey(String timeKey) {
        this.timeKey = timeKey;
    }



    
    public String getFirstPollutant() {
        return firstPollutant;
    }



    
    public void setFirstPollutant(String firstPollutant) {
        this.firstPollutant = firstPollutant;
    }



    
    public String getLevel() {
        return level;
    }



    
    public void setLevel(String level) {
        this.level = level;
    }



    
    public String getTargetLevel() {
        return targetLevel;
    }



    
    public void setTargetLevel(String targetLevel) {
        this.targetLevel = targetLevel;
    }



    
    public double getCod() {
        return Cod;
    }



    
    public void setCod(double cod) {
        Cod = cod;
    }



    
    public Integer getCodLevel() {
        return CodLevel;
    }



    
    public void setCodLevel(Integer codLevel) {
        CodLevel = codLevel;
    }



    
    public String getCodOverStandardState() {
        return CodOverStandardState;
    }



    
    public void setCodOverStandardState(String codOverStandardState) {
        CodOverStandardState = codOverStandardState;
    }



    
    public String getCodBrokenLineState() {
        return CodBrokenLineState;
    }



    
    public void setCodBrokenLineState(String codBrokenLineState) {
        CodBrokenLineState = codBrokenLineState;
    }



    
    public double getBod() {
        return Bod;
    }



    
    public void setBod(double bod) {
        Bod = bod;
    }



    
    public Integer getBodLevel() {
        return BodLevel;
    }



    
    public void setBodLevel(Integer bodLevel) {
        BodLevel = bodLevel;
    }



    
    public String getBodOverStandardState() {
        return BodOverStandardState;
    }



    
    public void setBodOverStandardState(String bodOverStandardState) {
        BodOverStandardState = bodOverStandardState;
    }



    
    public String getBodBrokenLineState() {
        return BodBrokenLineState;
    }



    
    public void setBodBrokenLineState(String bodBrokenLineState) {
        BodBrokenLineState = bodBrokenLineState;
    }



    
    public double getNH3NH4() {
        return NH3NH4;
    }



    
    public void setNH3NH4(double nH3NH4) {
        NH3NH4 = nH3NH4;
    }



    
    public Integer getNH3NH4Level() {
        return NH3NH4Level;
    }



    
    public void setNH3NH4Level(Integer nH3NH4Level) {
        NH3NH4Level = nH3NH4Level;
    }



    
    public String getNH3NH4OverStandardState() {
        return NH3NH4OverStandardState;
    }



    
    public void setNH3NH4OverStandardState(String nH3NH4OverStandardState) {
        NH3NH4OverStandardState = nH3NH4OverStandardState;
    }



    
    public String getNH3NH4BrokenLineState() {
        return NH3NH4BrokenLineState;
    }



    
    public void setNH3NH4BrokenLineState(String nH3NH4BrokenLineState) {
        NH3NH4BrokenLineState = nH3NH4BrokenLineState;
    }



    
    public double getKMnO4() {
        return KMnO4;
    }



    
    public void setKMnO4(double kMnO4) {
        KMnO4 = kMnO4;
    }



    
    public Integer getKMnO4Level() {
        return KMnO4Level;
    }



    
    public void setKMnO4Level(Integer kMnO4Level) {
        KMnO4Level = kMnO4Level;
    }



    
    public String getKMnO4OverStandardState() {
        return KMnO4OverStandardState;
    }



    
    public void setKMnO4OverStandardState(String kMnO4OverStandardState) {
        KMnO4OverStandardState = kMnO4OverStandardState;
    }



    
    public String getKMnO4BrokenLineState() {
        return KMnO4BrokenLineState;
    }



    
    public void setKMnO4BrokenLineState(String kMnO4BrokenLineState) {
        KMnO4BrokenLineState = kMnO4BrokenLineState;
    }



    
    public double getPH() {
        return PH;
    }



    
    public void setPH(double pH) {
        PH = pH;
    }



    
    public Integer getPHLevel() {
        return PHLevel;
    }



    
    public void setPHLevel(Integer pHLevel) {
        PHLevel = pHLevel;
    }



    
    public String getPHOverStandardState() {
        return PHOverStandardState;
    }



    
    public void setPHOverStandardState(String pHOverStandardState) {
        PHOverStandardState = pHOverStandardState;
    }



    
    public String getPHBrokenLineState() {
        return PHBrokenLineState;
    }



    
    public void setPHBrokenLineState(String pHBrokenLineState) {
        PHBrokenLineState = pHBrokenLineState;
    }



    
    public double getDO() {
        return DO;
    }



    
    public void setDO(double dO) {
        DO = dO;
    }



    
    public Integer getDOLevel() {
        return DOLevel;
    }



    
    public void setDOLevel(Integer dOLevel) {
        DOLevel = dOLevel;
    }



    
    public String getDOOverStandardState() {
        return DOOverStandardState;
    }



    
    public void setDOOverStandardState(String dOOverStandardState) {
        DOOverStandardState = dOOverStandardState;
    }



    
    public String getDOBrokenLineState() {
        return DOBrokenLineState;
    }



    
    public void setDOBrokenLineState(String dOBrokenLineState) {
        DOBrokenLineState = dOBrokenLineState;
    }



    
    public double getConductivity() {
        return Conductivity;
    }



    
    public void setConductivity(double conductivity) {
        Conductivity = conductivity;
    }



    
    public Integer getConductivityLevel() {
        return ConductivityLevel;
    }



    
    public void setConductivityLevel(Integer conductivityLevel) {
        ConductivityLevel = conductivityLevel;
    }



    
    public String getConductivityOverStandardState() {
        return ConductivityOverStandardState;
    }



    
    public void setConductivityOverStandardState(String conductivityOverStandardState) {
        ConductivityOverStandardState = conductivityOverStandardState;
    }



    
    public String getConductivityBrokenLineState() {
        return ConductivityBrokenLineState;
    }



    
    public void setConductivityBrokenLineState(String conductivityBrokenLineState) {
        ConductivityBrokenLineState = conductivityBrokenLineState;
    }



    
    public double getFTU() {
        return FTU;
    }



    
    public void setFTU(double fTU) {
        FTU = fTU;
    }



    
    public Integer getFTULevel() {
        return FTULevel;
    }



    
    public void setFTULevel(Integer fTULevel) {
        FTULevel = fTULevel;
    }



    
    public String getFTUOverStandardState() {
        return FTUOverStandardState;
    }



    
    public void setFTUOverStandardState(String fTUOverStandardState) {
        FTUOverStandardState = fTUOverStandardState;
    }



    
    public String getFTUBrokenLineState() {
        return FTUBrokenLineState;
    }



    
    public void setFTUBrokenLineState(String fTUBrokenLineState) {
        FTUBrokenLineState = fTUBrokenLineState;
    }



    
    public double getTemperature() {
        return Temperature;
    }



    
    public void setTemperature(double temperature) {
        Temperature = temperature;
    }



    
    public Integer getTemperatureLevel() {
        return TemperatureLevel;
    }



    
    public void setTemperatureLevel(Integer temperatureLevel) {
        TemperatureLevel = temperatureLevel;
    }



    
    public String getTemperatureOverStandardState() {
        return TemperatureOverStandardState;
    }



    
    public void setTemperatureOverStandardState(String temperatureOverStandardState) {
        TemperatureOverStandardState = temperatureOverStandardState;
    }



    
    public String getTemperatureBrokenLineState() {
        return TemperatureBrokenLineState;
    }



    
    public void setTemperatureBrokenLineState(String temperatureBrokenLineState) {
        TemperatureBrokenLineState = temperatureBrokenLineState;
    }



    
    public double getTP() {
        return TP;
    }



    
    public void setTP(double tP) {
        TP = tP;
    }



    
    public Integer getTPLevel() {
        return TPLevel;
    }



    
    public void setTPLevel(Integer tPLevel) {
        TPLevel = tPLevel;
    }



    
    public String getTPOverStandardState() {
        return TPOverStandardState;
    }



    
    public void setTPOverStandardState(String tPOverStandardState) {
        TPOverStandardState = tPOverStandardState;
    }



    
    public String getTPBrokenLineState() {
        return TPBrokenLineState;
    }



    
    public void setTPBrokenLineState(String tPBrokenLineState) {
        TPBrokenLineState = tPBrokenLineState;
    }



    
    public double getTN() {
        return TN;
    }



    
    public void setTN(double tN) {
        TN = tN;
    }



    
    public Integer getTNLevel() {
        return TNLevel;
    }



    
    public void setTNLevel(Integer tNLevel) {
        TNLevel = tNLevel;
    }



    
    public String getTNOverStandardState() {
        return TNOverStandardState;
    }



    
    public void setTNOverStandardState(String tNOverStandardState) {
        TNOverStandardState = tNOverStandardState;
    }



    
    public String getTNBrokenLineState() {
        return TNBrokenLineState;
    }



    
    public void setTNBrokenLineState(String tNBrokenLineState) {
        TNBrokenLineState = tNBrokenLineState;
    }



    
    public BigDecimal getCu() {
        return Cu;
    }



    
    public void setCu(BigDecimal cu) {
        Cu = cu;
    }



    
    public Integer getCuLevel() {
        return CuLevel;
    }



    
    public void setCuLevel(Integer cuLevel) {
        CuLevel = cuLevel;
    }



    
    public String getCuOverStandardState() {
        return CuOverStandardState;
    }



    
    public void setCuOverStandardState(String cuOverStandardState) {
        CuOverStandardState = cuOverStandardState;
    }



    
    public String getCuBrokenLineState() {
        return CuBrokenLineState;
    }



    
    public void setCuBrokenLineState(String cuBrokenLineState) {
        CuBrokenLineState = cuBrokenLineState;
    }



    
    public double getZn() {
        return Zn;
    }



    
    public void setZn(double zn) {
        Zn = zn;
    }



    
    public Integer getZnLevel() {
        return ZnLevel;
    }



    
    public void setZnLevel(Integer znLevel) {
        ZnLevel = znLevel;
    }



    
    public String getZnOverStandardState() {
        return ZnOverStandardState;
    }



    
    public void setZnOverStandardState(String znOverStandardState) {
        ZnOverStandardState = znOverStandardState;
    }



    
    public String getZnBrokenLineState() {
        return ZnBrokenLineState;
    }



    
    public void setZnBrokenLineState(String znBrokenLineState) {
        ZnBrokenLineState = znBrokenLineState;
    }



    
    public double getF() {
        return F;
    }



    
    public void setF(double f) {
        F = f;
    }



    
    public Integer getFLevel() {
        return FLevel;
    }



    
    public void setFLevel(Integer fLevel) {
        FLevel = fLevel;
    }



    
    public String getFOverStandardState() {
        return FOverStandardState;
    }



    
    public void setFOverStandardState(String fOverStandardState) {
        FOverStandardState = fOverStandardState;
    }



    
    public String getFBrokenLineState() {
        return FBrokenLineState;
    }



    
    public void setFBrokenLineState(String fBrokenLineState) {
        FBrokenLineState = fBrokenLineState;
    }



    
    public double getAs() {
        return As;
    }



    
    public void setAs(double as) {
        As = as;
    }



    
    public Integer getAsLevel() {
        return AsLevel;
    }



    
    public void setAsLevel(Integer asLevel) {
        AsLevel = asLevel;
    }



    
    public String getAsOverStandardState() {
        return AsOverStandardState;
    }



    
    public void setAsOverStandardState(String asOverStandardState) {
        AsOverStandardState = asOverStandardState;
    }



    
    public String getAsBrokenLineState() {
        return AsBrokenLineState;
    }



    
    public void setAsBrokenLineState(String asBrokenLineState) {
        AsBrokenLineState = asBrokenLineState;
    }



    
    public BigDecimal getHg() {
        return Hg;
    }



    
    public void setHg(BigDecimal hg) {
        Hg = hg;
    }



    
    public Integer getHgLevel() {
        return HgLevel;
    }



    
    public void setHgLevel(Integer hgLevel) {
        HgLevel = hgLevel;
    }



    
    public String getHgOverStandardState() {
        return HgOverStandardState;
    }



    
    public void setHgOverStandardState(String hgOverStandardState) {
        HgOverStandardState = hgOverStandardState;
    }



    
    public String getHgBrokenLineState() {
        return HgBrokenLineState;
    }



    
    public void setHgBrokenLineState(String hgBrokenLineState) {
        HgBrokenLineState = hgBrokenLineState;
    }



    
    public BigDecimal getCd() {
        return Cd;
    }



    
    public void setCd(BigDecimal cd) {
        Cd = cd;
    }



    
    public Integer getCdLevel() {
        return CdLevel;
    }



    
    public void setCdLevel(Integer cdLevel) {
        CdLevel = cdLevel;
    }



    
    public String getCdOverStandardState() {
        return CdOverStandardState;
    }



    
    public void setCdOverStandardState(String cdOverStandardState) {
        CdOverStandardState = cdOverStandardState;
    }



    
    public String getCdBrokenLineState() {
        return CdBrokenLineState;
    }



    
    public void setCdBrokenLineState(String cdBrokenLineState) {
        CdBrokenLineState = cdBrokenLineState;
    }



    
    public double getCr6() {
        return Cr6;
    }



    
    public void setCr6(double cr6) {
        Cr6 = cr6;
    }



    
    public Integer getCr6Level() {
        return Cr6Level;
    }



    
    public void setCr6Level(Integer cr6Level) {
        Cr6Level = cr6Level;
    }



    
    public String getCr6OverStandardState() {
        return Cr6OverStandardState;
    }



    
    public void setCr6OverStandardState(String cr6OverStandardState) {
        Cr6OverStandardState = cr6OverStandardState;
    }



    
    public String getCr6BrokenLineState() {
        return Cr6BrokenLineState;
    }



    
    public void setCr6BrokenLineState(String cr6BrokenLineState) {
        Cr6BrokenLineState = cr6BrokenLineState;
    }



    
    public double getPb() {
        return Pb;
    }



    
    public void setPb(double pb) {
        Pb = pb;
    }



    
    public Integer getPbLevel() {
        return PbLevel;
    }



    
    public void setPbLevel(Integer pbLevel) {
        PbLevel = pbLevel;
    }



    
    public String getPbOverStandardState() {
        return PbOverStandardState;
    }



    
    public void setPbOverStandardState(String pbOverStandardState) {
        PbOverStandardState = pbOverStandardState;
    }



    
    public String getPbBrokenLineState() {
        return PbBrokenLineState;
    }



    
    public void setPbBrokenLineState(String pbBrokenLineState) {
        PbBrokenLineState = pbBrokenLineState;
    }



    
    public double getFe() {
        return Fe;
    }



    
    public void setFe(double fe) {
        Fe = fe;
    }



    
    public Integer getFeLevel() {
        return FeLevel;
    }



    
    public void setFeLevel(Integer feLevel) {
        FeLevel = feLevel;
    }



    
    public String getFeOverStandardState() {
        return FeOverStandardState;
    }



    
    public void setFeOverStandardState(String feOverStandardState) {
        FeOverStandardState = feOverStandardState;
    }



    
    public String getFeBrokenLineState() {
        return FeBrokenLineState;
    }



    
    public void setFeBrokenLineState(String feBrokenLineState) {
        FeBrokenLineState = feBrokenLineState;
    }



    
    public double getCN() {
        return CN;
    }



    
    public void setCN(double cN) {
        CN = cN;
    }



    
    public Integer getCNLevel() {
        return CNLevel;
    }



    
    public void setCNLevel(Integer cNLevel) {
        CNLevel = cNLevel;
    }



    
    public String getCNOverStandardState() {
        return CNOverStandardState;
    }



    
    public void setCNOverStandardState(String cNOverStandardState) {
        CNOverStandardState = cNOverStandardState;
    }



    
    public String getCNBrokenLineState() {
        return CNBrokenLineState;
    }



    
    public void setCNBrokenLineState(String cNBrokenLineState) {
        CNBrokenLineState = cNBrokenLineState;
    }



    
    public double getArOH() {
        return ArOH;
    }



    
    public void setArOH(double arOH) {
        ArOH = arOH;
    }



    
    public Integer getArOHLevel() {
        return ArOHLevel;
    }



    
    public void setArOHLevel(Integer arOHLevel) {
        ArOHLevel = arOHLevel;
    }



    
    public String getArOHOverStandardState() {
        return ArOHOverStandardState;
    }



    
    public void setArOHOverStandardState(String arOHOverStandardState) {
        ArOHOverStandardState = arOHOverStandardState;
    }



    
    public String getArOHBrokenLineState() {
        return ArOHBrokenLineState;
    }



    
    public void setArOHBrokenLineState(String arOHBrokenLineState) {
        ArOHBrokenLineState = arOHBrokenLineState;
    }



    
    public double getOil() {
        return Oil;
    }



    
    public void setOil(double oil) {
        Oil = oil;
    }



    
    public Integer getOilLevel() {
        return OilLevel;
    }



    
    public void setOilLevel(Integer oilLevel) {
        OilLevel = oilLevel;
    }



    
    public String getOilOverStandardState() {
        return OilOverStandardState;
    }



    
    public void setOilOverStandardState(String oilOverStandardState) {
        OilOverStandardState = oilOverStandardState;
    }



    
    public String getOilBrokenLineState() {
        return OilBrokenLineState;
    }



    
    public void setOilBrokenLineState(String oilBrokenLineState) {
        OilBrokenLineState = oilBrokenLineState;
    }



    
    public double getAnionics() {
        return anionics;
    }



    
    public void setAnionics(double anionics) {
        this.anionics = anionics;
    }



    
    public Integer getAnionicsLevel() {
        return anionicsLevel;
    }



    
    public void setAnionicsLevel(Integer anionicsLevel) {
        this.anionicsLevel = anionicsLevel;
    }



    
    public String getAnionicsOverStandardState() {
        return anionicsOverStandardState;
    }



    
    public void setAnionicsOverStandardState(String anionicsOverStandardState) {
        this.anionicsOverStandardState = anionicsOverStandardState;
    }



    
    public String getAnionicsBrokenLineState() {
        return anionicsBrokenLineState;
    }



    
    public void setAnionicsBrokenLineState(String anionicsBrokenLineState) {
        this.anionicsBrokenLineState = anionicsBrokenLineState;
    }



    
    public double getSulfide() {
        return sulfide;
    }



    
    public void setSulfide(double sulfide) {
        this.sulfide = sulfide;
    }



    
    public Integer getSulfideLevel() {
        return sulfideLevel;
    }



    
    public void setSulfideLevel(Integer sulfideLevel) {
        this.sulfideLevel = sulfideLevel;
    }



    
    public String getSulfideOverStandardState() {
        return sulfideOverStandardState;
    }



    
    public void setSulfideOverStandardState(String sulfideOverStandardState) {
        this.sulfideOverStandardState = sulfideOverStandardState;
    }



    
    public String getSulfideBrokenLineState() {
        return sulfideBrokenLineState;
    }



    
    public void setSulfideBrokenLineState(String sulfideBrokenLineState) {
        this.sulfideBrokenLineState = sulfideBrokenLineState;
    }



    
    public double getNO3N() {
        return NO3N;
    }



    
    public void setNO3N(double nO3N) {
        NO3N = nO3N;
    }



    
    public Integer getNO3NLevel() {
        return NO3NLevel;
    }



    
    public void setNO3NLevel(Integer nO3NLevel) {
        NO3NLevel = nO3NLevel;
    }



    
    public String getNO3NOverStandardState() {
        return NO3NOverStandardState;
    }



    
    public void setNO3NOverStandardState(String nO3NOverStandardState) {
        NO3NOverStandardState = nO3NOverStandardState;
    }



    
    public String getNO3NBrokenLineState() {
        return NO3NBrokenLineState;
    }



    
    public void setNO3NBrokenLineState(String nO3NBrokenLineState) {
        NO3NBrokenLineState = nO3NBrokenLineState;
    }



    
    public double getBiotoxicity() {
        return biotoxicity;
    }



    
    public void setBiotoxicity(double biotoxicity) {
        this.biotoxicity = biotoxicity;
    }



    
    public Integer getBiotoxicityLevel() {
        return biotoxicityLevel;
    }



    
    public void setBiotoxicityLevel(Integer biotoxicityLevel) {
        this.biotoxicityLevel = biotoxicityLevel;
    }



    
    public String getBiotoxicityOverStandardState() {
        return biotoxicityOverStandardState;
    }



    
    public void setBiotoxicityOverStandardState(String biotoxicityOverStandardState) {
        this.biotoxicityOverStandardState = biotoxicityOverStandardState;
    }



    
    public String getBiotoxicityBrokenLineState() {
        return biotoxicityBrokenLineState;
    }



    
    public void setBiotoxicityBrokenLineState(String biotoxicityBrokenLineState) {
        this.biotoxicityBrokenLineState = biotoxicityBrokenLineState;
    }



    
    public double getChlorophyla() {
        return chlorophyla;
    }



    
    public void setChlorophyla(double chlorophyla) {
        this.chlorophyla = chlorophyla;
    }



    
    public Integer getChlorophylaLevel() {
        return chlorophylaLevel;
    }



    
    public void setChlorophylaLevel(Integer chlorophylaLevel) {
        this.chlorophylaLevel = chlorophylaLevel;
    }



    
    public String getChlorophylaOverStandardState() {
        return chlorophylaOverStandardState;
    }



    
    public void setChlorophylaOverStandardState(String chlorophylaOverStandardState) {
        this.chlorophylaOverStandardState = chlorophylaOverStandardState;
    }



    
    public String getChlorophylaBrokenLineState() {
        return chlorophylaBrokenLineState;
    }



    
    public void setChlorophylaBrokenLineState(String chlorophylaBrokenLineState) {
        this.chlorophylaBrokenLineState = chlorophylaBrokenLineState;
    }



    
    public double getAlgae() {
        return algae;
    }



    
    public void setAlgae(double algae) {
        this.algae = algae;
    }



    
    public Integer getAlgaeLevel() {
        return algaeLevel;
    }



    
    public void setAlgaeLevel(Integer algaeLevel) {
        this.algaeLevel = algaeLevel;
    }



    
    public String getAlgaeOverStandardState() {
        return algaeOverStandardState;
    }



    
    public void setAlgaeOverStandardState(String algaeOverStandardState) {
        this.algaeOverStandardState = algaeOverStandardState;
    }



    
    public String getAlgaeBrokenLineState() {
        return algaeBrokenLineState;
    }



    
    public void setAlgaeBrokenLineState(String algaeBrokenLineState) {
        this.algaeBrokenLineState = algaeBrokenLineState;
    }

    
    public String getStandardState() {
        return standardState;
    }


    
    public void setStandardState(String standardState) {
        this.standardState = standardState;
    }




    
    public String getLineState() {
        return lineState;
    }




    
    public void setLineState(String lineState) {
        this.lineState = lineState;
    }




    @Override
    public String toString() {
        return "OlMonitorWaterData{" +
                "id='" + id + '\'' +
                ", pointId='" + pointId + '\'' +
                ", pointName='" + pointName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", regionName='" + regionName + '\'' +
                ", sourcepointId='" + sourcepointId + '\'' +
                ", monitorTime=" + monitorTime +
                ", createTime=" + createTime +
                ", timeKey='" + timeKey + '\'' +
                ", firstPollutant='" + firstPollutant + '\'' +
                ", level='" + level + '\'' +
                ", targetLevel='" + targetLevel + '\'' +
                ", Cod=" + Cod +
                ", CodLevel=" + CodLevel +
                ", CodOverStandardState='" + CodOverStandardState + '\'' +
                ", CodBrokenLineState='" + CodBrokenLineState + '\'' +
                ", Bod=" + Bod +
                ", BodLevel=" + BodLevel +
                ", BodOverStandardState='" + BodOverStandardState + '\'' +
                ", BodBrokenLineState='" + BodBrokenLineState + '\'' +
                ",  NH3NH4=" +  NH3NH4 +
                ",  NH3NH4Level=" +  NH3NH4Level +
                ",  NH3NH4OverStandardState='" +  NH3NH4OverStandardState + '\'' +
                ",  NH3NH4BrokenLineState='" +  NH3NH4BrokenLineState + '\'' +
                ", KMnO4=" + KMnO4 +
                ", KMnO4Level=" + KMnO4Level +
                ", KMnO4OverStandardState='" + KMnO4OverStandardState + '\'' +
                ", KMnO4BrokenLineState='" + KMnO4BrokenLineState + '\'' +
                ", PH=" + PH +
                ", PHLevel=" + PHLevel +
                ", PHOverStandardState='" + PHOverStandardState + '\'' +
                ", PHBrokenLineState='" + PHBrokenLineState + '\'' +
                ", DO=" + DO +
                ", DOLevel=" + DOLevel +
                ", DOOverStandardState='" + DOOverStandardState + '\'' +
                ", DOBrokenLineState='" + DOBrokenLineState + '\'' +
                ", Conductivity=" + Conductivity +
                ", ConductivityLevel=" + ConductivityLevel +
                ", ConductivityOverStandardState='" + ConductivityOverStandardState + '\'' +
                ", ConductivityBrokenLineState='" + ConductivityBrokenLineState + '\'' +
                ", FTU=" + FTU +
                ", FTULevel=" + FTULevel +
                ", FTUOverStandardState='" + FTUOverStandardState + '\'' +
                ", FTUBrokenLineState='" + FTUBrokenLineState + '\'' +
                ", Temperature=" + Temperature +
                ", TemperatureLevel=" + TemperatureLevel +
                ", TemperatureOverStandardState='" + TemperatureOverStandardState + '\'' +
                ", TemperatureBrokenLineState='" + TemperatureBrokenLineState + '\'' +
                ", TP=" + TP +
                ", TPLevel=" + TPLevel +
                ", TPOverStandardState='" + TPOverStandardState + '\'' +
                ", TPBrokenLineState='" + TPBrokenLineState + '\'' +
                ", TN=" + TN +
                ", TNLevel=" + TNLevel +
                ", TNOverStandardState='" + TNOverStandardState + '\'' +
                ", TNBrokenLineState='" + TNBrokenLineState + '\'' +
                ", Cu=" + Cu +
                ", CuLevel=" + CuLevel +
                ", CuOverStandardState='" + CuOverStandardState + '\'' +
                ", CuBrokenLineState='" + CuBrokenLineState + '\'' +
                ", Zn=" + Zn +
                ", ZnLevel=" + ZnLevel +
                ", ZnOverStandardState='" + ZnOverStandardState + '\'' +
                ", ZnBrokenLineState='" + ZnBrokenLineState + '\'' +
                ", F=" + F +
                ", FLevel=" + FLevel +
                ", FOverStandardState='" + FOverStandardState + '\'' +
                ", FBrokenLineState='" + FBrokenLineState + '\'' +
                ", As=" + As +
                ", AsLevel=" + AsLevel +
                ", AsOverStandardState='" + AsOverStandardState + '\'' +
                ", AsBrokenLineState='" + AsBrokenLineState + '\'' +
                ", Hg=" + Hg +
                ", HgLevel=" + HgLevel +
                ", HgOverStandardState='" + HgOverStandardState + '\'' +
                ", HgBrokenLineState='" + HgBrokenLineState + '\'' +
                ", Cd=" + Cd +
                ", CdLevel=" + CdLevel +
                ", CdOverStandardState='" + CdOverStandardState + '\'' +
                ", CdBrokenLineState='" + CdBrokenLineState + '\'' +
                ", Cr6=" + Cr6 +
                ", Cr6Level=" + Cr6Level +
                ", Cr6OverStandardState='" + Cr6OverStandardState + '\'' +
                ", Cr6BrokenLineState='" + Cr6BrokenLineState + '\'' +
                ", Pb=" + Pb +
                ", PbLevel=" + PbLevel +
                ", PbOverStandardState='" + PbOverStandardState + '\'' +
                ", PbBrokenLineState='" + PbBrokenLineState + '\'' +
                ", Fe=" + Fe +
                ", FeLevel=" + FeLevel +
                ", FeOverStandardState='" + FeOverStandardState + '\'' +
                ", FeBrokenLineState='" + FeBrokenLineState + '\'' +
                ", CN=" + CN +
                ", CNLevel=" + CNLevel +
                ", CNOverStandardState='" + CNOverStandardState + '\'' +
                ", CNBrokenLineState='" + CNBrokenLineState + '\'' +
                ", ArOH=" + ArOH +
                ", ArOHLevel=" + ArOHLevel +
                ", ArOHOverStandardState='" + ArOHOverStandardState + '\'' +
                ", ArOHBrokenLineState='" + ArOHBrokenLineState + '\'' +
                ", Oil=" + Oil +
                ", OilLevel=" + OilLevel +
                ", OilOverStandardState='" + OilOverStandardState + '\'' +
                ", OilBrokenLineState='" + OilBrokenLineState + '\'' +
                ", anionics=" + anionics +
                ", anionicsLevel=" + anionicsLevel +
                ", anionicsOverStandardState='" + anionicsOverStandardState + '\'' +
                ", anionicsBrokenLineState='" + anionicsBrokenLineState + '\'' +
                ", sulfide=" + sulfide +
                ", sulfideLevel=" + sulfideLevel +
                ", sulfideOverStandardState='" + sulfideOverStandardState + '\'' +
                ", sulfideBrokenLineState='" + sulfideBrokenLineState + '\'' +
                ", NO3N=" + NO3N +
                ", NO3NLevel=" + NO3NLevel +
                ", NO3NOverStandardState='" + NO3NOverStandardState + '\'' +
                ", NO3NBrokenLineState='" + NO3NBrokenLineState + '\'' +
                ", biotoxicity=" + biotoxicity +
                ", biotoxicityLevel=" + biotoxicityLevel +
                ", biotoxicityOverStandardState='" + biotoxicityOverStandardState + '\'' +
                ", biotoxicityBrokenLineState='" + biotoxicityBrokenLineState + '\'' +
                ", chlorophyla=" + chlorophyla +
                ", chlorophylaLevel=" + chlorophylaLevel +
                ", chlorophylaOverStandardState='" + chlorophylaOverStandardState + '\'' +
                ", chlorophylaBrokenLineState='" + chlorophylaBrokenLineState + '\'' +
                ", algae=" + algae +
                ", algaeLevel=" + algaeLevel +
                ", algaeOverStandardState='" + algaeOverStandardState + '\'' +
                ", algaeBrokenLineState='" + algaeBrokenLineState + '\'' +
                '}';
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}