package net.crunchdroid.app.client.contact.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.crunchdroid.module.business.contact.manager.service.RemoteCircleService;
import net.crunchdroid.module.business.contact.manager.service.RemoteContactService;
import net.crunchdroid.module.business.contact.manager.service.RemoteRoleService;
import net.crunchdroid.module.business.contact.manager.service.RemoteUserService;
import net.crunchdroid.module.ejb.contat.manager.entity.Address;
import net.crunchdroid.module.ejb.contat.manager.entity.Circle;
import net.crunchdroid.module.ejb.contat.manager.entity.Civility;
import net.crunchdroid.module.ejb.contat.manager.entity.Contact;
import net.crunchdroid.module.ejb.contat.manager.entity.Email;
import net.crunchdroid.module.ejb.contat.manager.entity.Label;
import net.crunchdroid.module.ejb.contat.manager.entity.Messaging;
import net.crunchdroid.module.ejb.contat.manager.entity.Phone;
import net.crunchdroid.module.ejb.contat.manager.entity.Role;
import net.crunchdroid.module.ejb.contat.manager.entity.Type;
import net.crunchdroid.module.ejb.contat.manager.entity.User;
import net.crunchdroid.module.ejb.contat.manager.entity.Website;

/**
 *
 * @author Riad YOUSFI <riad.yousfi@crunchdroid.net>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            initServices();
            createRole();
            createUser();
            createContacts();
            getAllContacts();
            createContact();
            updateContact();
            addCircleToContact();
        } catch (NamingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private static RemoteContactService contactService;
    private static RemoteCircleService circleService;
    private static RemoteUserService userService;
    private static RemoteRoleService roleService;

    public static void initServices() throws NamingException {

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Init Context ::: ");

        String contactServiceName = "java:global/app-contact-manager-ear/app-contact-manager-business-1.0-SNAPSHOT/ContactServiceImp!net.crunchdroid.module.business.contact.manager.service.RemoteContactService";
        String circleServiceName = "java:global/app-contact-manager-ear/app-contact-manager-business-1.0-SNAPSHOT/CircleServiceImp!net.crunchdroid.module.business.contact.manager.service.RemoteCircleService";
        String userServiceName = "java:global/app-contact-manager-ear/app-contact-manager-business-1.0-SNAPSHOT/UserServiceImp!net.crunchdroid.module.business.contact.manager.service.RemoteUserService";
        String roleServiceName = "java:global/app-contact-manager-ear/app-contact-manager-business-1.0-SNAPSHOT/RoleServiceImp!net.crunchdroid.module.business.contact.manager.service.RemoteRoleService";

        InitialContext context = new InitialContext();

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Init RemoteContactService ::: ");
        contactService = (RemoteContactService) context.lookup(contactServiceName);
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Init RemoteCircleService ::: ");
        circleService = (RemoteCircleService) context.lookup(circleServiceName);
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Init RemoteUserService ::: ");
        userService = (RemoteUserService) context.lookup(userServiceName);
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Init RemoteRoleService ::: ");
        roleService = (RemoteRoleService) context.lookup(roleServiceName);
    }

    private static Role role;

    public static void createRole() {
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Create role ::: ");
        role = roleService.save(new Role("USER"));
        Logger.getLogger(Main.class.getName()).log(Level.INFO, role.toString());

    }

    private static User user;

    public static void createUser() {
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Create user ::: ");
        user = userService.save(new User("riad.yousfi@gmail.com", "Riad", "YOUSFI", role));
        Logger.getLogger(Main.class.getName()).log(Level.INFO, user.toString());
    }

    public static void createContacts() {
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Create contacts ::: ");
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Adel", user));
        contacts.add(new Contact("Shanaiz", user));
        contacts.add(new Contact("Papa", user));
        contacts.add(new Contact("Oumy", user));
        contacts.add(new Contact("Jamila", user));
        contactService.save(contacts);
    }

    public static Contact contact;

    public static void createContact() {
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Create contact ::: ");

        List<Address> addresses = new ArrayList();
        addresses.add(new Address("11 rue guy curat"));
        addresses.add(new Address("11 rue guy curat", new Type(Label.ADDRESS, "HOME", user)));
        addresses.add(new Address("11 rue guy curat", "94000", "CRETEIL", "FRANCE"));

        List<Website> websites = new ArrayList();
        websites.add(new Website("www.google.com"));
        websites.add(new Website("www.youtube.com", new Type(Label.WEBSITE, "VIDEO", user)));

        List<Email> emails = new ArrayList();
        emails.add(new Email("name1@email.com"));
        emails.add(new Email("name1@email.com", new Type(Label.EMAIL, "PRO", user)));

        List<Messaging> messagings = new ArrayList();
        messagings.add(new Messaging("latortu", null));
        messagings.add(new Messaging("toto", new Type(Label.MESSAGING, "SKYPE", user)));

        Contact c = new Contact("Luqman", user);
        c.setLastName("YOUSFI");
        c.addWebsites(websites);
        c.addEmails(emails);
        c.addMessagings(messagings);
        c.addAddresses(addresses);

        contact = contactService.save(c);

        Logger.getLogger(Main.class.getName()).log(Level.INFO, contact.toString());

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Contact addesse");
        List<Address> addressList = contact.getAddresses();
        for (Address address : addressList) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, address.toString());
        }

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Contact emails");
        List<Email> emailList = contact.getEmails();
        for (Email email : emailList) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, email.toString());

        }

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Contact websites");
        List<Website> websiteList = contact.getWebsites();
        for (Website website : websiteList) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, website.toString());

        }

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Contact messagings");
        List<Messaging> messagingList = contact.getMessagings();
        for (Messaging messaging : messagingList) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, messaging.toString());
        }
    }

    public static void updateContact() {

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Update contact ::: ");

        List<Phone> phones = new ArrayList();
        phones.add(new Phone("0102030405"));
        phones.add(new Phone("0601020304", new Type(Label.PHONE, "PROFESIONAL", user)));

        contact.setCivility(new Civility("Mr", user));
        contact.setJob("Developer");
        contact.addPhones(phones);

        contact = contactService.update(contact);

        Logger.getLogger(Main.class.getName()).log(Level.INFO, contact.toString());

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Contact phones");
        List<Phone> phoneList = contact.getPhones();
        for (Phone phone : phoneList) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, phone.toString());
        }

    }

    public static void addCircleToContact() {
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Create Circle");
        Circle circle = circleService.save(new Circle("FAMILLY", user));
        Logger.getLogger(Main.class.getName()).log(Level.INFO, circle.toString());

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Add circle to contact");
        contact.addCircle(circle);
        contact = contactService.update(contact);

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Contact circles");
        List<Circle> circles = contact.getCircles();
        for (Circle mCircle : circles) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, mCircle.toString());
        }

    }

    public static void getAllContacts() {
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Get all created contacts");
        List<Contact> contacts = contactService.findAll();
        for (Contact mContact : contacts) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, mContact.toString());
        }
    }

}
