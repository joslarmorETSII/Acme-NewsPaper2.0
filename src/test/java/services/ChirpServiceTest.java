package services;

import domain.Chirp;
import domain.NewsPaper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ChirpServiceTest extends AbstractTest{

    // The SUT ---------------------------------
    @Autowired
    private ChirpService chirpService;

     /*  FUNCTIONAL REQUIREMENT:
       * Post a chirp. Chirps may not be changed or deleted once they are posted.  */

    public void chirpCreateTest(String username, String title, Date moment, String description,
                                boolean posted, boolean taboo, Class<?> expected) {
        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            final Chirp result = this.chirpService.create();

            result.setTitle(title);
            result.setMoment(moment);
            result.setDescription(description);
            result.setPosted(posted);
            result.setTaboo(taboo);

            Chirp c= this.chirpService.save(result);
            chirpService.findOneToPublish(result);
            c = result;

            this.unauthenticate();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    /*  FUNCTIONAL REQUIREMENT:
       * An actor who is authenticated as an administrator must be able to:
               - List the chirps that contain taboo words.
    */

    public void listChirpsTabooWords(final String username,final Class<?> expected){
        Class<?> caught = null;
        startTransaction();
        try {
            this.authenticate(username);

            this.chirpService.findTabooChirps();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    // Drivers
    // ===================================================

    @Test
    public void driverCreateChirpTest() {

        final Date date = new Date();

        final Object testingData[][] = {
                // Crear un chirp logueado como usuario -> true
                {
                        "user1","title1",date ,"description1", false, false, null
                },
                // Crear un chirp sin estar logueado -> false
                {
                        null, "title1",date ,"description1", false, false, IllegalArgumentException.class
                },
                // Crear un chirp con taboo y posted a true ->false
                {
                        "user1","title1", date,"description1", true, true, IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.chirpCreateTest((String) testingData[i][0],(String)testingData[i][1] ,
                    (Date)testingData[i][2],(String)testingData[i][3],(Boolean)testingData[i][4],
                    (Boolean)testingData[i][5],(Class<?>) testingData[i][6]);
    }

    @Test
    public void driverListChirpsTabooWordsTest() {

        final Object testingData[][] = {
                // Intentando ver los chirps taboo como administrator -> true
                {
                        "administrator", null
                },
                // Intentando ver los chirps taboo como user2 -> false
                {
                        "user2", IllegalArgumentException.class
                },
                // Intentando ver los chirps taboo como customer1 -> false
                {
                        "customer1", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.listChirpsTabooWords((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

}
