package com.iss.iotcheck;

/**
 * 检验出的异常：
 * 1.212协议检测异常
 * 2.包含空格错误
 * 3.设备号不合规范 > 字母加数字
 * 4.数据包头错误
 * @author Liu Siyuan
 *
 */
public interface IOTCheck {
    String RE_CODE_OK = "检测成功！";
    String RE_MN_EMP = "请选择设备类型！";
    String RE_MESS_EMP = "请输入检测数据包！";
    String RE_CRC_ERR = "数据错误！（CRC校验算法错误）";
    String RE_CRC_STRING_ERR = "数据错误！（CRC段必须为16进制）";
    String RE_EMPTY_CHAR_ERR = "数据错误！（不允许包含空格）";
    String RE_START_CHAR_ERR = "数据错误！（包头：固定为##）";
    String RE_PROTOCOL212_OTHER_ERR = "数据错误！（其他）";
    String RE_MN_ERR = "设备号错误！（字母加数字构成或者数字长度超过10位）";
    String RE_HEADER_ERR = "协议头部描述错误！(包头：固定为 ##+数据段长度+ST系统类型)(或者设备类型选择错误)";
    
    public abstract String checkMessage(String mess, int type);

}
