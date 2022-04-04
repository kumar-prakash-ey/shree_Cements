package com.scl.core.lead.dao;

import com.scl.core.model.LeadModel;
import de.hybris.platform.servicelayer.internal.dao.Dao;

import java.util.List;

public interface SclLeadDao extends Dao {

    /**
     * Dao method to fetch lead by lead ID
     * @param leadID
     * @return
     */
    LeadModel getLeadByLeadID(final String leadID);

    /**
     * gets all leads
     * @return
     */
    List<LeadModel> getAllLeadsData();
}
