package services;


import domain.NewsPaper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class CasoDeUsoEditNewsPaper extends AbstractTest {

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private ActorService actorService;


    // Tests
    // ====================================================

    /*  CASO DE USO:
     *
     *   Un Actor se authentifica como User,
     *   elige uno de los que creado para editar lo edita y lo guarda.
     */

    public void newsPaperEditTest(final String username, final String title,String description1,String publicationDate1,String picture1,Boolean res1,Boolean res2,Boolean res3,String newsPaperBean, final Class<?> expected) {
        Class<?> caught = null;
        NewsPaper newsPaperToEdit;
        startTransaction();
        try {

            this.authenticate(username);

            newsPaperToEdit = newsPaperService.findOneToEdit(super.getEntityId(newsPaperBean));

            newsPaperToEdit.setTitle(title);
            newsPaperToEdit.setDescription(description1);

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            newsPaperToEdit.setPublicationDate(formatter.parse(publicationDate1));
            newsPaperToEdit.setPicture(picture1);

            newsPaperToEdit.setPublished(res1);
            newsPaperToEdit.setTaboo(res2);
            newsPaperToEdit.setModePrivate(res3);


            NewsPaper n= this.newsPaperService.save(newsPaperToEdit);
            n = newsPaperToEdit;
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
    public void driverNewsPaperEditTest() {


        final Object testingData[][] = {
                // 1-.Editar un newsPaper que no este publicada estando logueado como user -> true
                {
                        "user1", "name1","description1","12/03/2020 12:00","www.picture.com",false,false,false,"newsPaper2", null
                },
                // 2-.Editar un newsPaper sin estar logueado --> false
                {
                        null,"name1","description1","12/03/2020 12:00","www.picture.com",false,false,false,"newsPaper2", IllegalArgumentException.class
                },
                // 3-.Editar un newsPaper logueado como manager  -> false
                {
                        "manager1","name1","description1","12/03/2020 12:00","www.picture.com",false,false,false,"newsPaper2", IllegalArgumentException.class
                },
                // 4-.Editar un newsPaper publicada estando logueado como user -> false
                {
                        "user1", "name1","description1","12/03/2020 12:00","www.picture.com",true,false,false,"newsPaper1", IllegalArgumentException.class
                },
                // 5-.Editar un rendezvous logueado como admin  -> false
                {
                        "administrator","name1","description1","12/03/2020 12:00","www.picture.com",false,false,false,"newsPaper2", IllegalArgumentException.class
                },
               /* // 6-.Editar un newsPaper dejando la descripcion vacia -> false
                {
                        "user1", "name1", " ","12/03/2020 12:00","www.picture.com",true,false,false,"newsPaper2", IllegalArgumentException.class
                },
                // 7-.Editar un newsPaper metiendo un script en la descripcion -> false
                {
                        "user1", "name1", "<script>","12/03/2020 12:00","www.picture.com",true,false,false,"newsPaper2", IllegalArgumentException.class
                },
                // 8-.Editar un newsPaper dejando el titulo vacio -> false
                {
                        "user1", "", "description1","12/03/2020 12:00","www.picture.com",true,false,false,"newsPaper2", IllegalArgumentException.class
                },*/
                // 9-.Editar un newsPaper de user1 estando logueado  como user2 -> false
                {
                        "user2", "name1","description1","12/03/2020 12:00","www.picture.com",false,false,false,"newsPaper2", IllegalArgumentException.class
                },
                // 10-.Editar un newsPaper publicandola -> true
                {
                        "user1", "name1","description1","12/03/2020 12:00","www.picture.com",true,false,false,"newsPaper2",null
                },


        };
        for (int i = 0; i < testingData.length; i++)
            this.newsPaperEditTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String)testingData[i][3] , (String)testingData[i][4],(Boolean) testingData[i][5],(Boolean) testingData[i][6],(Boolean) testingData[i][7], (String)testingData[i][8], (Class<?>) testingData[i][9]);

    }


}
