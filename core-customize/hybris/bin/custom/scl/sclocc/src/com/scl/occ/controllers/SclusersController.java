package com.scl.occ.controllers;

import com.scl.occ.security.SclSecuredAccessConstants;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import de.hybris.platform.commercewebservicescommons.dto.product.ReviewWsDTO;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdAndUserIdParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller class for Scl users
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/users/{userId}")
@ApiVersion("v2")
@Api(tags = "Scl Users Management")
public class SclusersController extends SclBaseController{

    @Secured({ SclSecuredAccessConstants.ROLE_CUSTOMERGROUP, SclSecuredAccessConstants.ROLE_TRUSTED_CLIENT,SclSecuredAccessConstants.ROLE_CUSTOMERGROUP,SclSecuredAccessConstants.ROLE_CLIENT })
    @RequestMapping(method = RequestMethod.POST,value = "/prospectivedealerslist")
    //@ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(nickname = "getProspectiveDealersList", value = "Returns Prospective dealers List ", notes = "Returns List of prospective dealer attached with the user.")
    @ApiBaseSiteIdAndUserIdParam
    public void getProspectiveDeales(){


    }
}
