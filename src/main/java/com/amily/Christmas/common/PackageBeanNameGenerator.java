package com.amily.Christmas.common;

import java.beans.Introspector;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

public class PackageBeanNameGenerator implements BeanNameGenerator {
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		String beanClassName = Introspector.decapitalize(definition.getBeanClassName());
		return beanClassName;
	}
}
