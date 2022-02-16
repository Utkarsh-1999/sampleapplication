
package com.example.sampleapplication.ratelimiter.parser;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Component
public class SpelParser implements BeanFactoryAware {

    BeanFactory beanFactory;

    public String parseExpression(String expression,JoinPoint joinPoint) {
        CodeSignature codeSignature=(CodeSignature) joinPoint.getSignature();
        ExpressionParser expressionParser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        String [] parameterNames= codeSignature.getParameterNames();
        Class[] parameterTypes= codeSignature.getParameterTypes();
        Object[] args=joinPoint.getArgs();
        context.setBeanResolver(new BeanFactoryResolver(beanFactory));

        for(int i=0;i<args.length;i++)
        {
            context.setVariable(parameterNames[i],parameterTypes[i].cast(args[i]));
//            System.out.println(parameterNames[i]+":"+parameterTypes[i]);
        }






        return (String) expressionParser.parseExpression(expression).getValue(context,String.class);
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }
}
