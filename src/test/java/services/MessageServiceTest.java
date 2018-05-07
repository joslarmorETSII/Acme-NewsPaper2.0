package services;

import domain.Chirp;
import domain.Folder;
import domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)


public class MessageServiceTest extends AbstractTest {

    // The SUT
    // ====================================================

    @Autowired
    private MessageService messageService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private ActorService actorService;


    /*  CASO DE USO:
     *
     *   An actor who is authenticated must be able to:
     *      - Exchange messages with other actors and manage them, which includes deleting and
              moving them from one folder to another folder.
     *
     *
     *
     * COMO SE VA HA HACER?
     *
     * En este caso de uso vamos a hacer tests positivos y negativos:
     *
     * Como caso positivo:
     *
     * � Enviar mensaje logueado como actor.
     *
     * Para forzar el error pueden darse varios casos negativos, como son:
     *
     * � Intentar editar un Newspaper siendo autenticado como de customer.
     * . Intentar editar un Newspaper de otro user.
     * � Introducir la descripcion y el titulo vac�os.
     * � Introducir un script.
     * � Introducir una url invalida para la picture.
     * �
     */

    //TODO: Hacer test de message
    public void manageMessage(final String username, String messageBean, Folder folder, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {
            this.authenticate(username);

            Message message1 = messageService.findOne(getEntityId(messageBean));

            messageService.moveMessage(folder, message1);
            messageService.sendMessage(message1);
            messageService.delete(message1);

            this.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();

        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    /*  FUNCTIONAL REQUIREMENT:
            *  An actor who is authenticated must be able to:
                - Manage his or her message folders, except for the system folders.
    */




    /*  FUNCTIONAL REQUIREMENT:
            *  An actor who is authenticated as an administrator must be able to:
                - Broadcast a message to the actors of the system.
    */

//    public void broadCastMessage(String username, String messageBean, Class<?> expected) {
//        Class<?> caught=null;
//        startTransaction();
//        try {
//            this.authenticate(username);
//
//            Message result = messageService.findOne(getEntityId(messageBean));
//
//            messageService.sendBroadcast(result);
//
//            messageService.flush();
//            this.unauthenticate();
//
//        } catch (final Throwable oops) {
//            caught = oops.getClass();
//        }
//        this.checkExceptions(expected, caught);
//        rollbackTransaction();
//    }

    public void broadCastMessage(String username, Date moment, String body, String subject, String priority, Class<?> expected) {
        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            Message message = messageService.create();

            message.setMoment(moment);
            message.setSubject(subject);
            message.setBody(body);
            message.setPriority(priority);

            messageService.sendBroadcast(message);

            messageService.flush();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    //Drivers
    // ===================================================

//    @Test
//    public void driverManageMessageTest() {
//
//
//        Folder folder = new Folder();
//        final Object testingData[][] = {
//
//                // 1. Enviar mensaje logueado como actor  -> true
//                {
//                        "user1","message1", folder,null
//                },
//                // 2. Editar una newspaper estando logueado como User sin especificar la foto -> true
//                {
//                        "user1", "title edited", "description1", null, false, "newsPaper2", null
//                },
//                // 3. Editar una newspaper sin loguearse -> false
//                {
//                        null, "title edited", "description1", "http://www.picture.com", true, "newsPaper2", IllegalArgumentException.class
//                },
//                // 4. Editar una newspaper sin rellenar un campo obligatorio -> false
//                {
//                        "user1", "", "description1", null, false, "newsPaper2", ConstraintViolationException.class
//                },
//
//                // 5. Editar una newspaper estando logueado como customer -> false
//                {
//                        "customer1", "title1", "description1", null, true, "newsPaper2", IllegalArgumentException.class
//                },
//
//                // 6. Editar una newspaper de otro usuario -> false
//                {
//                        "user3", "title2", "description1", "http://www.picture.com", false, "newsPaper2", IllegalArgumentException.class
//                },
//
//                // 7. Editar una news paper estando logueado como customer -> false
//                {
//                        "user3", "title2", "description1", "http://www.picture3.com", false, "newsPaper2", IllegalArgumentException.class
//                },
//
//                // 8. Editar una newspaper proporcionando una url no valida de la picture-> false
//                {
//                        "user1", "title edited", "description1", "htpt://www.picture.com",false,"newsPaper2", ConstraintViolationException.class
//                },
//
//                // 9. Editar una newspaper con un srcipt-> false
//                {
//                        "user1", "<script>", "description1", "http://www.picture.com",false,"newsPaper2", ConstraintViolationException.class
//                },
//                // 10. Editar una newspaper publica y ponerla privada -> true
//                {
//                        "user1", "Dailymail", "news paper privada", "http://www.picture.com",true,"newsPaper2", null
//                },
//
//
//        };
//        for (int i = 0; i < testingData.length; i++)
//            this.manageMessage((String) testingData[i][0],(String) testingData[i][1], (Folder) testingData[i][2]
//                    , (Class<?>) testingData[i][3]);
//    }

//    @Test
//    public void driverBroadcastMessageTest() {
//
//        final Date date = new Date();
//
//        final Object testingData[][] = {
//
//                // Intentar mandar un broadcast con mensaje a null ->false
//                {
//                        "administrator",null, IllegalArgumentException.class
//                },
//                // Intentar mandar un broadcast logueado como admin -> true
//                {
//                        "administrator","message1", null
//                },
//                // Intentar mandar un broadcast logueado como user -> false
//                {
//                        "user1","message1", IllegalArgumentException.class
//                },
//
//        };
//        for (int i = 0; i < testingData.length; i++)
//            this.broadCastMessage((String) testingData[i][0],(String)testingData[i][1],(Class<?>) testingData[i][2]);
//    }

    @Test
    public void driverBroadcastMessageTest() {

        final Date date = new Date();

        final Object testingData[][] = {

                // Intentar mandar un broadcast logueado como admin -> true
                {
                        "administrator",date,"body1","subject1","neutral", null
                },
//                // Intentar mandar un broadcast logueado como user -> false
//                {
//                        "user1","body1","subject1","NEUTRAL", IllegalArgumentException.class
//                },
//                // Intentar mandar un broadcast con mensaje a null ->false
//                {
//                        "administrator","body1","subject1","NEUTRAL", IllegalArgumentException.class
//                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.broadCastMessage((String) testingData[i][0],(Date)testingData[i][1],
                    (String) testingData[i][2],(String) testingData[i][3],
                    (String) testingData[i][4],(Class<?>) testingData[i][5]);
    }


}
