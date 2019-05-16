package edu.ncu.zww.imserver.service.tools;

import edu.ncu.zww.imserver.bean.Contact;
import edu.ncu.zww.imserver.bean.Invitation;

// 邀请函切换工具
public class InvitationExchange {

    public static Invitation exchangeInvitation(final Invitation invitation) {
        Invitation newInvitation = new Invitation();

        newInvitation.setUuid(invitation.getUuid());
        newInvitation.setCreateDate(invitation.getCreateDate());
        newInvitation.setStatus(invitation.getStatus());
        newInvitation.setInfo(invitation.getInfo());
        newInvitation.setType(invitation.getType());

//        Contact contact = invitation.getFromUser();
//        newInvitation.setAccount(contact.getAccount());
//        newInvitation.setImg(contact.getImg());
//        newInvitation.setName(contact.getName());
//
//        Contact newContact = new Contact();
//        newContact.setAccount(invitation.getAccount());
//        newContact.setImg(invitation.getImg());
//        newContact.setName(invitation.getName());
//        newInvitation.setFromUser(newContact);

        return newInvitation;
    }
}
