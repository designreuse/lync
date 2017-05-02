package com.lync.core.toolbox.kit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/*普通类调用Spring bean对象*/
public class SpringKit implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextAware.class);
    private static ApplicationContext applicationContext = null;



    @Override

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        if(SpringKit.applicationContext == null){

            SpringKit.applicationContext  = applicationContext;

        }
        logger.info("---------------------------------------------------------------------");
        logger.info("---------------------------------------------------------------------");
        logger.info("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+ SpringKit.applicationContext+"========");
        logger.info("---------------------------------------------------------------------");
    }



    //获取applicationContext

    public static ApplicationContext getApplicationContext() {

        return applicationContext;

    }



    //通过name获取 Bean.

    public static Object getBean(String name){

        return getApplicationContext().getBean(name);

    }



    //通过class获取Bean.

    public static <T> T getBean(Class<T> clazz){

        return getApplicationContext().getBean(clazz);

    }



    //通过name,以及Clazz返回指定的Bean

    public static <T> T getBean(String name,Class<T> clazz){

        return getApplicationContext().getBean(name, clazz);

    }

}