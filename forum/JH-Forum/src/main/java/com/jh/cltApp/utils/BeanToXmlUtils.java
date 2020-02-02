package com.jh.cltApp.utils;

import com.jh.cltApp.vo.TemplateVO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * javabean和XML间转换
 * @version <1> 2019/4/9 15:54 zhangshen:Created.
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
            //marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
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


    public static void main(String[] args){

        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<attir>\n" +
                "    <item fieldNameCh=\"我我我我我我我我我\" fieldNameEn=\"wowowowowowowoowowo\" value=\"1\"/>\n" +
                "    <item fieldNameCh=\"你你你你你你你领你\" fieldNameEn=\"nininininininininin\" value=\"2\"/>\n" +
                "    <item fieldNameCh=\"他他他他他他他他他\" fieldNameEn=\"tatatatatatatatatat\" value=\"3\"/>\n" +
                "</attir>";

        TemplateVO obj = xml2Bean(TemplateVO.class, s);
        System.out.println(obj);

        System.out.println(beanToXml(obj, TemplateVO.class));

        /*String s2 = "";

        TemplateVO obj2 = xml2Bean(TemplateVO.class, s2);
        System.out.println(obj2);

        System.out.println(beanToXml(obj2, TemplateVO.class));*/

    }
}
