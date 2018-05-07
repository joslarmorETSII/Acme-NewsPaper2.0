package services;

import domain.Advertisement;
import domain.Chirp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)

public class AdvertisementServiceTest extends AbstractTest {

    // The SUT
    // ====================================================

    @Autowired
    private AdvertisementService advertisementService;


    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is authenticated as an administrator must be able to:
     *      - List the advertisements that contain taboo words in its title.
     */

    public void listAdvertisementsTabooWords(final String username,final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {
            this.authenticate(username);

            this.advertisementService.findTabooAdvertisement();

            this.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();

        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

     /*  FUNCTIONAL REQUIREMENT:
            *  An actor who is authenticated as an administrator must be able to:
                - Remove an advertisement that he or she thinks is inappropriate.
    */


    public void deleteAdvertisementInappropiate(final String username, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();

        try {

            this.authenticate(username);

            Advertisement result= advertisementService.findAll().iterator().next();

            this.advertisementService.delete(result);

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }


    //Drivers
    // ===================================================

    @Test
    public void driverListAdvertisementsTabooWordsTest() {

        final Object testingData[][] = {
                // Intentando ver los advertisements taboo como administrator -> true
                {
                        "administrator", null
                },
                // Intentando ver los advertisements taboo como user2 -> false
                {
                        "user2", IllegalArgumentException.class
                },
                // Intentando ver los advertisements taboo como customer1 -> false
                {
                        "customer1", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.listAdvertisementsTabooWords((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverDeleteAdvertisementInappropiateTest() {

        final Object testingData[][] = {
                // Borrar un advertisement que considere inapropiado como admin -> true
                {
                        "administrator", null
                },
                // Borrar un advertisement que considere inapropiado como null -> false
                {
                        null, IllegalArgumentException.class
                },
                // Borrar un advertisement que considere inapropiado como customer -> false
                {
                        "customer1", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.deleteAdvertisementInappropiate((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }
}