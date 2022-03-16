package com.scl.facades.lead.impl;

import com.scl.core.lead.services.SclLeadService;
import com.scl.core.model.LeadModel;
import com.scl.facades.lead.data.LeadData;
import com.scl.facades.lead.SclLeadFacade;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;

import java.util.UUID;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

public class DefaultSclLeadFacade implements SclLeadFacade {

    @Resource
    private ModelService modelService;

    @Resource
    private SclLeadService sclLeadService;

    @Resource
    private Converter<LeadData, LeadModel> sclLeadReverseConverter;

    @Resource
    private Converter<LeadModel , LeadData> sclLeadConverter;

    @Override
    public String updateLead(LeadData leadData) {
        validateParameterNotNullStandardMessage("leadData", leadData);
        LeadModel leadModel;
        String leadId = UUID.randomUUID().toString();
        if(StringUtils.isBlank(leadData.getLeadId())){
            leadModel = modelService.create(LeadModel.class);
            leadModel.setLeadId(leadId);
        }
        else {
            leadModel = sclLeadService.findLeadByLeadId(leadData.getLeadId());
            leadId = leadData.getLeadId();
        }
        modelService.save(sclLeadReverseConverter.convert(leadData,leadModel));
        return leadId;
    }

    @Override
    public LeadData getLeadForLeadId(String leadId) {

         LeadData leadData = new LeadData();
         final LeadModel leadModel = sclLeadService.findLeadByLeadId(leadId);
         return sclLeadConverter.convert(leadModel,leadData);
    }

}
