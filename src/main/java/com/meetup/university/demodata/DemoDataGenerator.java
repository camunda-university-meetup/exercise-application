package com.meetup.university.demodata;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.authorization.Permissions.READ;
import static org.camunda.bpm.engine.authorization.Resources.FILTER;

@Component
@RequiredArgsConstructor
public class DemoDataGenerator {

    private final IdentityService identityService;
    private final TaskService taskService;
    private final FilterService filterService;
    private final AuthorizationService authorizationService;

    @PostConstruct
    public void init() {

        final User singleResult = this.identityService.createUserQuery().userId("john").singleResult();
        if (singleResult != null) {
            return;
        }

        final User user2 = this.identityService.newUser("john");
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setPassword("john");
        user2.setEmail("john@camunda.org");
        this.identityService.saveUser(user2);

        final User user3 = this.identityService.newUser("mary");
        user3.setFirstName("Mary");
        user3.setLastName("Anne");
        user3.setPassword("mary");
        user3.setEmail("mary@camunda.org");
        this.identityService.saveUser(user3);

        final User user4 = this.identityService.newUser("peter");
        user4.setFirstName("Peter");
        user4.setLastName("Meter");
        user4.setPassword("peter");
        user4.setEmail("peter@camunda.org");
        this.identityService.saveUser(user4);

        final Group salesGroup = this.identityService.newGroup("sales");
        salesGroup.setName("Sales");
        salesGroup.setType("WORKFLOW");
        this.identityService.saveGroup(salesGroup);

        final Group accountingGroup = this.identityService.newGroup("accounting");
        accountingGroup.setName("Accounting");
        accountingGroup.setType("WORKFLOW");
        this.identityService.saveGroup(accountingGroup);

        final Group deliveryGroup = this.identityService.newGroup("delivery");
        deliveryGroup.setName("Delivery");
        deliveryGroup.setType("WORKFLOW");
        this.identityService.saveGroup(deliveryGroup);

        this.identityService.createMembership("demo", "sales");
        this.identityService.createMembership("demo", "accounting");
        this.identityService.createMembership("demo", "delivery");

        this.identityService.createMembership("john", "sales");
        this.identityService.createMembership("john", "camunda-admin");

        this.identityService.createMembership("mary", "accounting");
        this.identityService.createMembership("mary", "camunda-admin");

        this.identityService.createMembership("peter", "delivery");
        this.identityService.createMembership("peter", "camunda-admin");


        final Map<String, Object> filterProperties = new HashMap<>();
        filterProperties.put("description", "Tasks assigned to me");
        filterProperties.put("priority", -10);
        TaskQuery query = this.taskService.createTaskQuery().taskAssigneeExpression("${currentUser()}");
        final Filter myTasksFilter = this.filterService.newTaskFilter().setName("My Tasks").setProperties(filterProperties).setOwner("demo").setQuery(query);
        this.filterService.saveFilter(myTasksFilter);

        filterProperties.clear();
        filterProperties.put("description", "Tasks assigned to my Groups");
        filterProperties.put("priority", -5);
        query = this.taskService.createTaskQuery().taskCandidateGroupInExpression("${currentUserGroups()}").taskUnassigned();
        final Filter groupTasksFilter = this.filterService.newTaskFilter().setName("My Group Tasks").setProperties(filterProperties).setOwner("demo").setQuery(query);
        this.filterService.saveFilter(groupTasksFilter);

        final Authorization globalMyTaskFilterRead = this.authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
        globalMyTaskFilterRead.setResource(FILTER);
        globalMyTaskFilterRead.setResourceId(myTasksFilter.getId());
        globalMyTaskFilterRead.addPermission(READ);
        this.authorizationService.saveAuthorization(globalMyTaskFilterRead);

        final Authorization globalGroupFilterRead = this.authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
        globalGroupFilterRead.setResource(FILTER);
        globalGroupFilterRead.setResourceId(groupTasksFilter.getId());
        globalGroupFilterRead.addPermission(READ);
        this.authorizationService.saveAuthorization(globalGroupFilterRead);

    }

}
