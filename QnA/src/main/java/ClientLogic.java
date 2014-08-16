import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class ClientLogic {

	 public static void main(String[] args)
	    {
	        Resource res = new ClassPathResource("mail-config.xml");
	        BeanFactory factory = new XmlBeanFactory(res);
	 
	        Object o = factory.getBean("id2");
	        MailLogic ml = (MailLogic)o;
	 
	        ml.sendM("harish.kaparwan@gmail.com","harish.kaparwan@gmail.com","MySubject","This is body");
	 
	    }
}
