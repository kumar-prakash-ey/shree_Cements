package com.scl.facades.lead;

import com.scl.facades.lead.data.LeadData;

public interface SclLeadFacade {

    String updateLead(final LeadData leadData);

    LeadData getLeadForLeadId(final String emailId);
}
