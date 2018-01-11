package net.oasismgt.tin_service_generation.local.dao;

import java.util.Collection;

import net.oasismgt.tin_service_generation.local.model.MailingList;;

public interface MailingListDao {
	public Long add(MailingList m);
    public MailingList get(Long id);   
    public Collection<MailingList> getAll();
    public MailingList update(MailingList m);
    public void delete(MailingList m);
}
