package com.scl.occ.controllers;

import com.scl.dto.lead.LeadWsDTO;
import com.scl.facades.lead.data.LeadData;
import com.scl.facades.lead.SclLeadFacade;
import com.scl.occ.security.SclSecuredAccessConstants;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdAndUserIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

@Controller
@RequestMapping(value = "/{baseSiteId}/users/{userId}/sclLeads")
@ApiVersion("v2")
@Api(tags = "Scl Lead Management")
public class SclLeadsController extends SclBaseController{

    @Resource
    private SclLeadFacade sclLeadFacade;

    /**
     * Method to create lead
     * @param leadCreation
     * @param fields
     * @return
     */
    @Secured({ SclSecuredAccessConstants.ROLE_B2BADMINGROUP, SclSecuredAccessConstants.ROLE_TRUSTED_CLIENT,SclSecuredAccessConstants.ROLE_CUSTOMERGROUP })
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(nickname = "createLead", value = " Create a Lead", notes = "Creates a new lead Entry.", consumes = MediaType.APPLICATION_JSON)
    @ApiBaseSiteIdAndUserIdParam
    public LeadWsDTO createLead(
            @ApiParam(value = "Data object that contains information necessary for Lead creation", required = true) @RequestBody final LeadWsDTO leadCreation,
            @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
    {


        final LeadData leadData = getDataMapper().map(leadCreation, LeadData.class);

        String leadId = sclLeadFacade.updateLead(leadData);
        LeadData  updatedLeadData = sclLeadFacade.getLeadForLeadId(leadId);
        return getDataMapper().map(updatedLeadData, LeadWsDTO.class, fields);
    }
}
