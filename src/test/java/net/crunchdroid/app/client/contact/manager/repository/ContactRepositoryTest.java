/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crunchdroid.app.client.contact.manager.repository;

import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import static junit.framework.Assert.*;
import net.crunchdroid.module.ejb.contat.manager.entity.Contact;
import net.crunchdroid.module.ejb.contat.manager.entity.Role;
import net.crunchdroid.module.ejb.contat.manager.entity.User;
import net.crunchdroid.module.ejb.contat.manager.entity.Website;
import net.crunchdroid.module.ejb.contat.manager.entity.WebsiteType;
import net.crunchdroid.module.ejb.contat.manager.repository.RemoteContactRepository;
import net.crunchdroid.module.ejb.contat.manager.repository.RemoteRoleRepository;
import net.crunchdroid.module.ejb.contat.manager.repository.RemoteUserRepository;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cdi316
 */
public class ContactRepositoryTest {

    private static RemoteContactRepository contactRepository;
    private static RemoteRoleRepository roleRepository;
    private static RemoteUserRepository userRepository;

    private static User user;

    @BeforeClass
    public static void init() throws NamingException {

        InitialContext context = new InitialContext();

        contactRepository = (RemoteContactRepository) context.lookup("contactRepository");
        userRepository = (RemoteUserRepository) context.lookup("userRepository");
        roleRepository = (RemoteRoleRepository) context.lookup("roleRepository");

//        contactRepository.deleteAll();
//        userRepository.deleteAll();
//        roleRepository.deleteAll();
        User u = new User();
        u.setFirstName("Riad");
        u.setLastName("YOUSFI");
        u.setUsername("riad.yousfi@gmail.com");
        u.setRole(new Role("USER"));
        user = userRepository.save(u);
    }

    @Test
//    @Ignore
    public void CrudTest() {

        /*Test save(entity)*/
        System.out.println("Test save(entity)");
        System.out.println(user);
        Contact contact = contactRepository.save(new Contact(user, "Adel"));
        System.out.println(contact);
//        assertEquals(contact.getId(), Long.valueOf(1l));

        /*Test update(entity)*/
        System.out.println("Test update(entity)");
        contact.setLastName("YOUSFI");
        contactRepository.update(contact);
        System.out.println(contact);

        /*Test findOne(id)*/
        System.out.println("Test findOne(id)");
        Contact c = contactRepository.findOne(contact.getId());
        System.out.println(c);
//        assertEquals(c.getLastName(), "YOUSFI");

        /*Test findOne(id)*/
        System.out.println("Test add website");
        List<Website> websites = new ArrayList<>();
        websites.add(new Website(contact, new WebsiteType("Blog"), "www.blog.com"));
        contact.setWebsites(websites);
        contactRepository.update(contact);

        /*Test delete(id)*/
//        System.out.println("Test delete(id)");
//        contactRepository.delete(contact.getId());
//        Long count = contactRepository.count();
//        System.out.println(String.format("count %s", count));
//        assertEquals(count, Long.valueOf(0l));
    }

}
