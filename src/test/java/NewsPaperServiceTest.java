
import domain.Actor;
import domain.Article;
import domain.NewsPaper;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ActorService;
import services.NewsPaperService;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsPaperServiceTest extends AbstractTest {


    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private ActorService actorService;

    // Tests
    // ====================================================

       /*  FUNCTIONAL REQUIREMENT:
       * An actor who is not authenticated must be able to:
            -.List the newspapers that are published and browse their articles. */

    public void listOfNewsPaperTest(final String username,final Class<?> expected){
        Class<?> caught = null;
        startTransaction();
        try {
            this.authenticate(username);

            this.newsPaperService.findPublishedNewsPaper();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as a user must be able to:
               -. Create a newspaper. A user who has created a newspaper is commonly referred to
                as a publisher.
    */

    @SuppressWarnings("deprecation")
    public void newsPaperCreateTest(final String username, final String title,String description1,String publicationDate1,String picture1,Boolean res1,Boolean res2,Boolean res3, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {

            this.authenticate(username);

            final NewsPaper result = this.newsPaperService.create();

            result.setTitle(title);
            result.setDescription(description1);

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            result.setPublicationDate(formatter.parse(publicationDate1));
            result.setPicture(picture1);

            result.setPublished(res1);
            result.setTaboo(res2);
            result.setModePrivate(res3);


            NewsPaper n= this.newsPaperService.save(result);
            n = result;
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as a user must be able to:
               -. Create a newspaper. A user who has created a newspaper is commonly referred to
                as a publisher.
    */

    @SuppressWarnings("deprecation")
    public void newsPaperPublishTest(final String username, final String title,String description1,String publicationDate1,String picture1,Boolean res1,Boolean res2,Boolean res3, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {

            this.authenticate(username);

            final NewsPaper result = this.newsPaperService.create();


            result.setTitle(title);
            result.setDescription(description1);

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            result.setPublicationDate(formatter.parse(publicationDate1));
            result.setPicture(picture1);

            result.setPublished(res1);
            result.setTaboo(res2);
            result.setModePrivate(res3);


            NewsPaper n= newsPaperService.save(result);
            n = result;
            newsPaperService.findOneToPublish(n);
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }


      /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as a admin must be able to:
                -.Remove an article that he or she thinks is inappropriate.

    */


    public void deleteNewsPaperTest(final String username, String newsPaperBean,Boolean published, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();

        try {
            NewsPaper result= newsPaperService.findOne(super.getEntityId(newsPaperBean));

            this.authenticate(username);


            this.newsPaperService.delete(result);

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
    public void driverListNewsPaperTest() {

        final Object testingData[][] = {
                // Alguien sin registrar/logueado -> true
                {
                        null, null
                },
                // Un Usuario -> true
                {
                        "user1", null
                },
                // Otro Usuario -> true
                {
                        "user2", null
                },
                // Un administrador -> true
                {
                        "administrator", null
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.listOfNewsPaperTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverNewsPaperCreateTest() {


        final Object testingData[][] = {
                // Crear un newsPaper estando logueado como user -> true
                {
                        "user1", "name1","description1","12/03/2020 12:00","www.picture.com",false,false,false, null
                },
                // Crear un rendezvous sin estar logueado --> false
               {
                        null,"name1","description1","12/03/2020 12:00","www.picture.com",false,false,false, IllegalArgumentException.class
                },
                // Crear un rendezvous logueado como manager  -> false
                {
                        "manager1","name1","description1","12/03/2020 12:00","www.picture.com",false,false,false, IllegalArgumentException.class
                },


        };
        for (int i = 0; i < testingData.length; i++)
            this.newsPaperCreateTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String)testingData[i][3] , (String)testingData[i][4],(Boolean) testingData[i][5],(Boolean) testingData[i][6],(Boolean) testingData[i][7], (Class<?>) testingData[i][8]);

    }

    @Test
    public void driverNewsPaperPublishTest() {


        final Object testingData[][] = {
                // Crear un newsPaper estando logueado como user -> true
                {
                        "user1", "name1","description1","12/03/2020 12:00","www.picture.com",false,false,false, null
                },
                // Crear un rendezvous sin estar logueado --> false
                {
                        null,"name1","description1","12/03/2020 12:00","www.picture.com",false,false,false, IllegalArgumentException.class
                },
                // Crear un rendezvous logueado como manager  -> false
                {
                        "manager1","name1","description1","12/03/2020 12:00","www.picture.com",false,false,false, IllegalArgumentException.class
                },


        };
        for (int i = 0; i < testingData.length; i++)
            this.newsPaperCreateTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String)testingData[i][3] , (String)testingData[i][4],(Boolean) testingData[i][5],(Boolean) testingData[i][6],(Boolean) testingData[i][7], (Class<?>) testingData[i][8]);

    }

    @Test
    public void driverDeleteNewsPaperTest() {

        final Object testingData[][] = {
                // Borrar un newsPaper estando logueado como admin -> true
                {
                        "administrator", "newsPaper1",true, null
                },
                // Borrar un newsPaper sin estar logueado -> false
                {
                        null, "newsPaper1",true, IllegalArgumentException.class
                },
                // Borrar un newsPaper que no esta publicado -> false
                {
                        "administrator1","newsPaper1",false, IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.deleteNewsPaperTest((String) testingData[i][0], (String) testingData[i][1],(Boolean) testingData[i][2], (Class<?>) testingData[i][3]);
    }
}