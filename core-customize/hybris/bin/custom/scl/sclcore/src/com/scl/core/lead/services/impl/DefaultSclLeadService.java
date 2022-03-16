package com.scl.core.lead.services.impl;


import com.scl.core.lead.dao.SclLeadDao;
import com.scl.core.lead.services.SclLeadService;
import com.scl.core.model.LeadModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

public class DefaultSclLeadService implements SclLeadService {

    @Resource
    private ModelService modelService;


    @Resource
    private SclLeadDao sclLeadDao;

    private static final Logger LOG = Logger.getLogger(DefaultSclLeadService.class);


    @Override
    public Boolean saveLeadEntry(final B2BCustomerModel b2bCustomer , final LeadModel leadModel){

        //final String leadId = String.valueOf(leadCodeGenerator.generate());
        final String leadId = UUID.randomUUID().toString();
        leadModel.setLeadId(leadId);
        leadModel.setOwner(b2bCustomer);
        leadModel.setCreatedBy(b2bCustomer);
        try{
            modelService.save(leadModel);
        }
        catch (ModelSavingException mse){
            LOG.error("Error occured while saving Lead "+leadModel.getFirstName()+"\n");
            LOG.error("Exception is: "+mse.getMessage());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateLeadEntry(final B2BCustomerModel b2BCustomer,  final LeadModel leadModel){

        leadModel.setModifiedBy(b2BCustomer);

        try{
            modelService.save(leadModel);
        }
        catch (ModelSavingException mse){
            LOG.error("Error occured while updating Lead "+leadModel.getLeadId()+"\n");
            LOG.error("Exception is: "+mse.getMessage());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public LeadModel findLeadByLeadId(final String leadID){
        return sclLeadDao.getLeadByLeadID(leadID);
    }

    @Override
    public List<LeadModel> getAllLeads(){
        return sclLeadDao.getAllLeadsData();
    }

}
