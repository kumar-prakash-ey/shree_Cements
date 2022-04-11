package com.scl.occ.controllers;


import com.scl.facades.customer.SclCustomerFacade;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Controller class Dealer Management
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/dealer/{dealerCode}")
@ApiVersion("v2")
@Api(tags = "Scl Dealer Management")
public class DealersController extends SclBaseController{

    /*@Resource(name = "dealerDocumentWsDTOValidator")
    protected Validator dealerDocumentWsDTOValidator;*/

    @Resource
    private SclCustomerFacade sclCustomerFacade;

    /**
     * upload documents of dealer with dealer code
     * @param
     *
     * @return
     */
    //@Secured({ SclSecuredAccessConstants.ROLE_CUSTOMERGROUP, SclSecuredAccessConstants.ROLE_TRUSTED_CLIENT,SclSecuredAccessConstants.ROLE_CUSTOMERGROUP,SclSecuredAccessConstants.ROLE_CLIENT })
    @RequestMapping(value = "/upload/{documentType}", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(nickname = "uploadDealerDocument", value = "Uploads the document of the dealer", notes = "Uploads the document of the dealer")
    @ApiBaseSiteIdParam
    public void uploadDocument(
            @ApiParam(value = "dealer code .", required = true) @PathVariable final String dealerCode,
            @ApiParam(value = "document type .", required = true) @PathVariable final String documentType,
            @ApiParam(value = "Object contains doc image file",required = true) @RequestParam(value = "file") final MultipartFile file) {

        sclCustomerFacade.uploadDealerDocument(dealerCode,documentType,file);
    }



}
