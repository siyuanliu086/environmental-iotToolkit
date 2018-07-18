package app.tools;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JsonHelper {

	/**
	 * Json字符串转对象
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonStrToBean(String jsonStr, Class<T> clazz) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonStr, clazz);
	}

	public static <T> T jsonStrToBeanList(String jsonStr, JavaType javaType) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		//JavaType javaType = getCollectionType(ArrayList.class, clazz);
		//List<YourBean> lst = (List<YourBean>) mapper.readValue(jsonString, javaType);

		return mapper.readValue(jsonStr, javaType);
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * 对象转Json字符串
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static String beanToJsonStr(Object bean) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(bean);
	}
	/**
	 * 对象转Json字符串
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static String beanToXMLStr(Object bean) throws Exception {
		 
		XmlMapper Mapper = new XmlMapper();  
		
		return Mapper.writeValueAsString(bean);
	}
	//中文转Unicode
    public static String gbEncoding(final String gbString) {   //gbString = "测试"
        char[] utfBytes = gbString.toCharArray();   //utfBytes = [测, 试]
        String unicodeBytes = "";   
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {   
            String hexB = Integer.toHexString(utfBytes[byteIndex]);   //转换为16进制整型字符串
              if (hexB.length() <= 2) {   
                  hexB = "00" + hexB;   
             }   
             unicodeBytes = unicodeBytes + "\\u" + hexB;   
        }   
        System.out.println("unicodeBytes is: " + unicodeBytes);   
        return unicodeBytes;   
    }
  //Unicode转中文
    public static String decodeUnicode(final String dataStr) {   
       int start = 0;   
       int end = 0;   
       final StringBuffer buffer = new StringBuffer();   
       while (start > -1) {   
           end = dataStr.indexOf("\\u", start + 2);   
           String charStr = "";   
           if (end == -1) {   
               charStr = dataStr.substring(start + 2, dataStr.length());   
           } else {   
               charStr = dataStr.substring(start + 2, end);   
           }   
           char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。   
           buffer.append(new Character(letter).toString());   
           start = end;   
       }   
       return buffer.toString();   
    }
}