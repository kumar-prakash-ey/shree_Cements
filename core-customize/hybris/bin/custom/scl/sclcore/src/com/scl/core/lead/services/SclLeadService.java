package com.scl.core.lead.services;

import com.scl.core.model.LeadModel;
import com.scl.facades.lead.data.LeadData;
import de.hybris.platform.b2b.model.B2BCustomerModel;

import java.util.List;

public interface SclLeadService {

    Boolean saveLeadEntry(final B2BCustomerModel b2bCustomer , final LeadModel leadModel);

    Boolean updateLeadEntry(final B2BCustomerModel b2BCustomer,  final LeadModel leadModel);

    LeadModel findLeadByLeadId(final String leadID);

    List<LeadModel> getAllLeads();
}
