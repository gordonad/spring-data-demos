package com.gordondickens.orm.querydsl.domain;

import com.gordondickens.orm.querydsl.config.RepositoryConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;

/**
 * @author gordon
 */
@ContextConfiguration(classes = RepositoryConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
public class BeansInContextTest {
    private static final Logger logger = LoggerFactory
            .getLogger(BeansInContextTest.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BeanFactory beanFactory;

    @Test
    public void showBeansInContext() {
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
        if (factory != null) {
            logger.debug("Bean Factory: '{}'", factory);
        }


        if (applicationContext.getParent() != null) {
            logger.debug("Bean Factory: '{}'", applicationContext.getParentBeanFactory());
        }
        logger.debug("**** ORM ORM ORM ORM ORM ORM ORM ORM ORM ****");
        String[] beans = applicationContext.getBeanDefinitionNames();
        for (String o : beans) {
            Boolean isFactory = (null != factory) && factory.isFactoryBean(o);
            logger.debug("BEAN id: {}|{}|{}",
                    new String[]{applicationContext.getType(o).toString(), o, isFactory.toString()});
            if (logger.isTraceEnabled()) {
                String[] aliases = applicationContext.getAliases(o);
                if (null != factory && factory.isFactoryBean(o)) logger.trace("\tFACTORY");
                if (aliases != null && aliases.length > 0) {
                    for (String a : aliases) {
                        logger.trace("\tAliased as: '{}'", a);
                    }
                }
                if (null != factory && factory.getBeanDefinition(o).isAbstract()) {
                    logger.debug("\tABSTRACT");
                } else {
                    if (applicationContext.isPrototype(o)) logger.trace("\tScope: 'Prototype'");
                    if (applicationContext.isSingleton(o)) logger.trace("\tScope: 'Singleton'");

                    Annotation[] annotations = applicationContext.getBean(o).getClass().getAnnotations();
                    if (annotations != null && annotations.length > 0) {
                        logger.trace("\tAnnotations:");

                        for (Annotation annotation : annotations) {
                            logger.trace("\t\t'{}'", annotation.annotationType());
                        }
                    }
                    if (!applicationContext.getBean(o).toString().startsWith(applicationContext.getType(o).toString() + "@")) {
                        logger.trace("\tContents: {}", applicationContext.getBean(o).toString());
                    }
                }
            }
        }

        logger.debug("******************************************************************************");
        logger.trace("*** Number of Beans={} ***",
                applicationContext.getBeanDefinitionCount());
        if (null != factory) {
            logger.trace("*** Number of Bean Post Processors={} ***", factory.getBeanPostProcessorCount());
        }
        logger.trace("******************************************************************************");
    }
}
