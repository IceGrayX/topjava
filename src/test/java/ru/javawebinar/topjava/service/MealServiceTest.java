package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Валерий on 12.01.2017.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;


    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL_ID, USER_ID);
        MATCHER.assertEquals(MEAL_1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_3, MEAL_2, MEAL_1),
                service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID));
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_2, MEAL_1),
                service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 9, 00), LocalDateTime.of(2015, Month.MAY, 30, 15, 00), USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(MEALS, all);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL_ID, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
                service.update(MEAL_1, ADMIN_ID);
    }

    @Test
    public void testSave() throws Exception {
        Meal created = getCreated();
        service.save(created, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1), service.getAll(USER_ID));
    }
}