package app.iot.process.database.entity;


public interface IotData {
    /**
     * 站点（数据）类型定义（大气）
     */
    public static final String DEVICE_TYPE_AIR = "air";
    /**
     * 站点（数据）类型定义（水）
     */
    public static final String DEVICE_TYPE_WATER = "water";
    /**
     * 站点（数据）类型定义（位置）
     */
    public static final String DEVICE_TYPE_POSITION = "position";
    
    public static final String DEVICE_SOURCE = "IOT";
}
