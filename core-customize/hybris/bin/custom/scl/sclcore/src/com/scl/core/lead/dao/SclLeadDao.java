package com.scl.core.lead.dao;

import com.scl.core.model.LeadModel;

import java.util.List;

public interface SclLeadDao {

    LeadModel getLeadByLeadID(final String leadID);

    List<LeadModel> getAllLeadsData();
}
