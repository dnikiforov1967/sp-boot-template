/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.jms;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.template.spboot.root.data.model.AppUser;
import org.template.spboot.root.data.resource.AppUserResource;
import org.template.spboot.root.jms.api.JmsApi;
import org.template.spboot.root.jms.model.JmsResult;
import org.template.spboot.root.service.UserApiServiceInterface;

/**
 *
 * @author dima
 */
@Component("jmsConsumer")
public class JmsConsumer implements JmsApi {

	@Autowired
	@Qualifier("jpaUserApiService")
	private UserApiServiceInterface userApi;
	
    @Override
    public JmsResult send(AppUserResource resource) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @JmsListener(destination = "testQueue", containerFactory = "custimizedFactory")
    //@Transactional
    public JmsResult receive(AppUserResource resource) {
		try {
			final AppUser user = AppUserResource.CONVERT2USER(resource);
			final AppUser created = userApi.create(user);
			AppUserResource result = new AppUserResource(created);
			return new JmsResult(result,null);
		} catch(Exception ex) {
			return new JmsResult(null, ex);
		}	
    }
    
}
