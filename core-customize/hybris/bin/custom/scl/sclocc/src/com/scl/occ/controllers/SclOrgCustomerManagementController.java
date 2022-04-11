package com.scl.occ.controllers;

import com.scl.facades.customer.SclCustomerFacade;
import com.scl.occ.security.SclSecuredAccessConstants;
import de.hybris.platform.b2bcommercefacades.company.B2BUserFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BCostCenterData;
import de.hybris.platform.b2bwebservicescommons.dto.company.B2BCostCenterWsDTO;
import de.hybris.platform.b2bwebservicescommons.dto.company.OrgCustomerCreationWsDTO;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import de.hybris.platform.commercewebservicescommons.dto.user.UserWsDTO;
import de.hybris.platform.webservicescommons.errors.exceptions.AlreadyExistsException;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdAndUserIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

@Controller
@RequestMapping(value = "/{baseSiteId}/users/{userId}/sclOrgCustomers")
@ApiVersion("v2")
@Api(tags = "Scl Organizational Unit Customer Management")
public class SclOrgCustomerManagementController extends SclBaseController{

    private static final String OBJECT_NAME_CUSTOMER = "customer";

    private static final String USER_ALREADY_EXISTS_ERROR_KEY = "User already exists";

    private static final Logger LOG = LoggerFactory.getLogger(SclOrgCustomerManagementController.class);

    @Resource(name = "wsUserFacade")
    private UserFacade wsUserFacade;

    @Resource(name = "orgCustomerCreationWsDTOValidator")
    protected Validator orgCustomerCreationWsDTOValidator;

    @Resource(name = "b2bUserFacade")
    private B2BUserFacade b2bUserFacade;

    @Resource
    private SclCustomerFacade sclCustomerFacade;

    @Secured({ SclSecuredAccessConstants.ROLE_B2BADMINGROUP, SclSecuredAccessConstants.ROLE_TRUSTED_CLIENT })
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(nickname = "createOrgCustomer", value = " Registers a org customer", notes = "Creates a new organizational customer.", consumes = MediaType.APPLICATION_JSON)
    @ApiBaseSiteIdAndUserIdParam
    public UserWsDTO createOrgCustomer(
            @ApiParam(value = "Data object that contains information necessary for user creation", required = true) @RequestBody final OrgCustomerCreationWsDTO orgCustomerCreation,
            @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
    {
        if (isUserExisting(orgCustomerCreation.getEmail()))
        {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS_ERROR_KEY);
        }

        validate(orgCustomerCreation, OBJECT_NAME_CUSTOMER, orgCustomerCreationWsDTOValidator);

        final CustomerData orgCustomerData = getDataMapper().map(orgCustomerCreation, CustomerData.class);
        orgCustomerData.setDisplayUid(orgCustomerCreation.getEmail());

        b2bUserFacade.updateCustomer(orgCustomerData);

        final CustomerData updatedCustomerData = b2bUserFacade.getCustomerForUid(orgCustomerCreation.getEmail());

        return getDataMapper().map(updatedCustomerData, UserWsDTO.class, fields);
    }
    /*@Secured({ SecuredAccessConstants.ROLE_B2BADMINGROUP, SecuredAccessConstants.ROLE_TRUSTED_CLIENT })
    @RequestMapping(value = "/costcenters/{costCenterCode}", method = RequestMethod.PATCH)
    @ApiOperation(nickname = "updateCostCenter", value = "Update a cost center.", notes = "Updates a cost center. Only attributes provided in the request body will be changed.", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @ApiBaseSiteIdParam
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public B2BCostCenterWsDTO updateCostCenter(
            @ApiParam(value = "Cost center identifier.", required = true) @PathVariable final String costCenterCode,
            @ApiParam(value = "Cost center object.", required = true) @RequestBody final B2BCostCenterWsDTO costCenter,
            @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
    {
        b2BCostCentersHelper.enableDisableCostCenter(costCenterCode, costCenter);

        final B2BCostCenterData costCenterData = b2BCostCentersHelper.getCostCenterDataForCode(costCenterCode);
        getDataMapper().map(costCenter, costCenterData, false);
        costCenterData.setOriginalCode(costCenterCode);

        final B2BCostCenterWsDTO toBeValidatedCostCenter = getDataMapper().map(costCenterData, B2BCostCenterWsDTO.class);
        validate(toBeValidatedCostCenter, OBJECT_NAME_COST_CENTER, b2BCostCenterWsDTOValidator);

        costCenterFacade.updateCostCenter(costCenterData);
        return b2BCostCentersHelper.searchCostCenter(costCenterData.getCode(), fields);
    }*/
    @Secured({ SclSecuredAccessConstants.ROLE_B2BADMINGROUP, SclSecuredAccessConstants.ROLE_TRUSTED_CLIENT })
    @RequestMapping(value = "/updateLastSixMonthsAverageOrderValue", method = RequestMethod.POST)
    @ApiOperation(nickname = "updateAvarageOrderValue", value = "Update Last Six Months Average Order Value", notes = "Update Last Six Months Average Order Value.")
    @ApiBaseSiteIdAndUserIdParam
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserWsDTO updateAveargeOrderValue(
            @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields){

        sclCustomerFacade.calculateLastSixMonthsAverageOrderValue();
        return getDataMapper().map(new UserWsDTO(), UserWsDTO.class, fields);
    }

    protected boolean isUserExisting(String orgUnitUserId)
    {
        return orgUnitUserId != null && wsUserFacade.isUserExisting(orgUnitUserId);
    }

}
