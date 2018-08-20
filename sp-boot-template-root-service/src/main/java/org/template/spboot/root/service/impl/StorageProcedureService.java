/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.template.spboot.root.interfaces.StorageProcedureInterface;

/**
 *
 * @author dnikiforov
 */
@Component
@Transactional
public class StorageProcedureService implements StorageProcedureInterface {

	@PersistenceContext(name = "globalPU")
	private EntityManager em;
	
	@Override
	public int calc(int x, int y) {
		final StoredProcedureQuery query = em.createStoredProcedureQuery("calc_prc");
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT);
		query.setParameter(1, 2);
		query.setParameter(2, 4);
		query.execute();
		final Integer res = (Integer)query.getOutputParameterValue(3);
		return res;
	}
	
}
