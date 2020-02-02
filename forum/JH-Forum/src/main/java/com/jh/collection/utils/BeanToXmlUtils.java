package com.jh.collection.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * javabean转化为XML
 * @version <1> 2018/11/19 15:45 xy: Created.
 */
public class BeanToXmlUtils {
    /**
     * java对象转换为xml文件
     * @return    xml文件的String
     * @throws JAXBException
     */
    public static String beanToXml(Object obj,Class<?> load){
        StringWriter writer = new StringWriter();
        try {
        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
        marshaller.marshal(obj,writer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    /**
     * xml文件转化为java对象
     * @return    xml文件的String
     * @throws JAXBException
     */
    public static <T> T xml2Bean(Class<T> bean, String xmlStr) {
        T s = null;
        try {
            JAXBContext context = JAXBContext.newInstance(bean);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            s = (T)unmarshaller.unmarshal(new StringReader(xmlStr));
        } catch (JAXBException e) {
            return s;
        }
        return s;
    }
}
