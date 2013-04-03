package mypackage;

import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.pim.Contact;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import net.rim.blackberry.api.pdap.BlackBerryContact;
import net.rim.blackberry.api.pdap.BlackBerryContactList;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MyScreen extends MainScreen
{
    /**
     * Creates a new MyScreen object
     */
    public MyScreen()
    {        
        // Set the displayed title of the screen      
    	Vector v = getContacts();
        Enumeration iterator = v.elements();
        while (iterator.hasMoreElements()) {
            String[] contact = (String[]) iterator.nextElement();
            for (int i = 0; i < contact.length; i++)
                add(new LabelField(contact[i]));
        }
    }
    private Vector getContacts() {
        Vector result = new Vector();
        try {
            BlackBerryContactList contactList = (BlackBerryContactList) PIM
                    .getInstance().openPIMList(PIM.CONTACT_LIST, PIM.READ_ONLY);
            Enumeration enumx = contactList.items();
            while (enumx.hasMoreElements()) 
            {
                BlackBerryContact c = (BlackBerryContact) enumx.nextElement();
                String[] contact = new String[2];
                if (contactList.isSupportedField(BlackBerryContact.NAME))
                {
                    String[] name = c.getStringArray(BlackBerryContact.NAME, 0);
                    String firstName = name[Contact.NAME_GIVEN];
                    String lastName = name[Contact.NAME_FAMILY];
                    if(lastName==null)
                    {
                    	contact[0] = firstName;
                    }
                    else
                    {
                    contact[0] = firstName + " " + lastName;
                    }
                }
                if (contactList.isSupportedField(BlackBerryContact.EMAIL))
                {
                    StringBuffer emails = new StringBuffer();
                    int emailCount = c.countValues(BlackBerryContact.EMAIL);
                    for (int i = 0; i < emailCount; i++) 
                    {
                        String email = c.getString(BlackBerryContact.EMAIL, i);
                        if (email != null) 
                        {
                            emails.append(email.trim());
                            emails.append("; ");
                        }
                        else
                        {
                        	emails.append("Email Address Null");
                        }
                    }
                    contact[1] = emails.toString();
                }
                result.addElement(contact);
            }
        } catch (PIMException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
