package com.meetup.university.ageVerification;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static io.holunda.camunda.bpm.data.CamundaBpmData.dateVariable;


@Component("ageCalculationDelegate")
@RequiredArgsConstructor
public class AgeCalculationDelegate implements JavaDelegate {
    private final VariableFactory<Date> birthdate = dateVariable("birthdate");
    @Override
    public void execute(DelegateExecution execution) throws Exception{

        //input:
        final Date birthdate = this.birthdate.from(execution).getLocal();

        //processing:
        Long now = Instant.now().toEpochMilli();
        Duration duration = new Duration(birthdate.getTime(), now);
        final int age = (int) (duration.getStandardDays()/365);

        //output:
        execution.setVariable("age", age);
    }
}
