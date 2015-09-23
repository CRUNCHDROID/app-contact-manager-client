package net.crunchdroid.app.client.contact.manager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.crunchdroid.module.business.contact.manager.service.RemoteContactService;
import net.crunchdroid.module.ejb.contat.manager.entity.Contact;

/**
 *
 * @author Riad YOUSFI <riad.yousfi@crunchdroid.net>
 */
public class Main {

    private static final String contactServiceName = "java:global/app-contact-manager-ear/app-contact-manager-business-1.0-SNAPSHOT/ContactServiceImp!net.crunchdroid.module.business.contact.manager.service.RemoteContactService";
    private static final String circleServiceName = "java:global/app-contact-manager-ear/app-contact-manager-business-1.0-SNAPSHOT/CircleServiceImp!net.crunchdroid.module.business.contact.manager.service.RemoteCircleService";
    private static final String userServiceName = "java:global/app-contact-manager-ear/app-contact-manager-business-1.0-SNAPSHOT/UserServiceImp!net.crunchdroid.module.business.contact.manager.service.RemoteUserService";
    private static final String roleServiceName = "java:global/app-contact-manager-ear/app-contact-manager-business-1.0-SNAPSHOT/RoleServiceImp!net.crunchdroid.module.business.contact.manager.service.RemoteRoleService";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Get Context JNDI GlassFish");
            InitialContext context = new InitialContext();

            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Get Business layout services");
            RemoteContactService contactService = (RemoteContactService) context.lookup(contactServiceName);
//            RemoteCircleService circleService = (RemoteCircleService) context.lookup(circleServiceName);
//            RemoteUserService userService = (RemoteUserService) context.lookup(userServiceName);
//            RemoteRoleService roleService = (RemoteRoleService) context.lookup(roleServiceName);

            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Get all contact");
            List<Contact> contacts = contactService.findAll();
            for (Contact contact : contacts) {
                Logger.getLogger(Main.class.getName()).log(Level.INFO, contact.toString());
            }
        } catch (NamingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
