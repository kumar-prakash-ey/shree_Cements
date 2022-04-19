package com.scl.occ.controllers;

import com.scl.facades.customer.SclCustomerFacade;
import com.scl.facades.prosdealer.data.ProsDealerData;
import com.scl.facades.prosdealer.data.ProsDealerListData;
import com.scl.occ.dto.prosdealer.ProsDealerListWsDTO;
import com.scl.occ.dto.prosdealer.ProsDealerWsDTO;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdAndUserIdParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Controller class for Scl users
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/users/{userId}")
@ApiVersion("v2")
@Api(tags = "Scl Users Management")
public class SclUsersController extends SclBaseController{

    @Resource
    private SclCustomerFacade sclCustomerFacade;


    //@Secured({ SclSecuredAccessConstants.ROLE_CUSTOMERGROUP, SclSecuredAccessConstants.ROLE_TRUSTED_CLIENT,SclSecuredAccessConstants.ROLE_CUSTOMERGROUP,SclSecuredAccessConstants.ROLE_CLIENT })
    @RequestMapping(method = RequestMethod.GET,value = "/prosdealerslist")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiOperation(nickname = "getProspectiveDealersList", value = "Returns Prospective dealers List ", notes = "Returns List of prospective dealer attached with the user.")
    @ApiBaseSiteIdAndUserIdParam
    public ProsDealerListWsDTO getProspectiveDealers(){

        ProsDealerListData prosDealers = sclCustomerFacade.getProspectiveDealersForCurrentuser();
        return getDataMapper().map(prosDealers,ProsDealerListWsDTO.class, BASIC_FIELD_SET);

    }
    @RequestMapping(method = RequestMethod.GET,value = "/prospdealerdetails")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiOperation(nickname = "getProspectiveDealerDetails", value = "Returns Prospective dealer details", notes = "Returns details of  prospective dealer with the dealer code.")
    @ApiBaseSiteIdAndUserIdParam
    public ProsDealerWsDTO getProspectiveDealerDetail(
            @ApiParam(value = "Pros Dealer Code identifier") @RequestParam(required = true) final String prosDealerCode){

        ProsDealerData prosDealerDetails = sclCustomerFacade.getProsDealerDetailsByCode(prosDealerCode);
        return getDataMapper().map(prosDealerDetails,ProsDealerWsDTO.class, DEFAULT_FIELD_SET);

    }


}
