package pl.funnyqrz.services.aop.email;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.funnyqrz.services.AbstractService;

@Aspect
@Component
public class EmailAspectService extends AbstractService{


    @Before("execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))")
    public void logBeforeAllMethodsJobExecute1(JoinPoint joinPoint) {
       getLogger().info("Executing method:" + joinPoint.getSignature().getName());
    }
    //TODO - afer executing downloadAndSaveExchangeRate method-> send email with reports( for example, velocity, fremarker template)
}
