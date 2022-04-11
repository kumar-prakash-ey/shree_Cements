package com.scl.core.customer.services.impl;

import com.scl.core.constants.SclCoreConstants;
import com.scl.core.customer.services.SclCustomerService;
import com.scl.core.customer.services.SclDealerDocumentsService;
import com.scl.core.model.DealerModel;
import de.hybris.platform.catalog.model.CatalogUnawareMediaModel;
import de.hybris.platform.core.model.media.MediaFolderModel;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

public class DefaultSclDealerDocumentsService implements SclDealerDocumentsService {

    private MediaService mediaService;

    private FlexibleSearchService flexibleSearchService;

    private ModelService modelService;

    private SclCustomerService sclCustomerService;

    private static final Logger LOG = Logger.getLogger(DefaultSclDealerDocumentsService.class);

    @Override
    public void uploadDealerDocument(String dealerCode, String documentType, MultipartFile file) {

        validateParameterNotNull(file, "file must not be null");
        DealerModel dealer = getSclCustomerService().getDealerForCode(dealerCode);

        switch (documentType){
            case SclCoreConstants.DOCUMENT_PAN_CARD:
                CatalogUnawareMediaModel panCard = createMediaFromFile(dealerCode,SclCoreConstants.DOCUMENT_PAN_CARD,file);
                LOG.info("Pan card file URL: "+panCard.getURL());
                //TODO: Set file to dealer model
                break;

            case SclCoreConstants.DOCUMENT_TYPE_BANK_STATEMENT:
                CatalogUnawareMediaModel bankStatement = createMediaFromFile(dealerCode,SclCoreConstants.DOCUMENT_TYPE_BANK_STATEMENT,file);
                LOG.info("Bank statement file URL: "+bankStatement.getURL());
                //TODO: Set file to dealer model
                break;

            case SclCoreConstants.DOCUMENT_TYPE_BLANK_CHEQUE:
                CatalogUnawareMediaModel blankCheque = createMediaFromFile(dealerCode,SclCoreConstants.DOCUMENT_TYPE_BLANK_CHEQUE,file);
                LOG.info("Blank cheque file URL: "+blankCheque.getURL());
                //TODO: Set file to dealer model
                break;

            case SclCoreConstants.DOCUMENT_TYPE_DD_DETAILS:
                CatalogUnawareMediaModel ddDetails = createMediaFromFile(dealerCode,SclCoreConstants.DOCUMENT_TYPE_DD_DETAILS,file);
                LOG.info("DD details file URL: "+ddDetails.getURL());
                //TODO: Set file to dealer model
                break;

            case SclCoreConstants.DOCUMENT_TYPE_GODOWN_DETAILS:
                CatalogUnawareMediaModel godownDetails = createMediaFromFile(dealerCode,SclCoreConstants.DOCUMENT_TYPE_GODOWN_DETAILS,file);
                LOG.info("Godown file URL: "+godownDetails.getURL());
                //TODO: Set file to dealer model
                break;

            case SclCoreConstants.DOCUMENT_TYPE_GST_DETAILS:
                CatalogUnawareMediaModel gstDetails = createMediaFromFile(dealerCode,SclCoreConstants.DOCUMENT_TYPE_GST_DETAILS,file);
                LOG.info("GST details file URL: "+gstDetails.getURL());
                //TODO: Set file to dealer model
                break;

            case SclCoreConstants.DOCUMENT_TYPE_LETTER_HEAD_COPY:
                CatalogUnawareMediaModel letterHead = createMediaFromFile(dealerCode,SclCoreConstants.DOCUMENT_TYPE_LETTER_HEAD_COPY,file);
                LOG.info("Letter head file URL: "+letterHead.getURL());
                //TODO: Set file to dealer model
                break;
        }

        getModelService().save(dealer);



    }

    /*@Override
    public void uploadDealerDocument(final DealerDocumentUploadData documentUploadData){
        validateParameterNotNull(documentUploadData, "Document data must not be null");
        validateParameterNotNull(documentUploadData.getDealerCode(), "dealer code must not be null");
        final String dealerCode = documentUploadData.getDealerCode();
        DealerModel dealer = getSclCustomerService().getDealerForCode(dealerCode);
        if(StringUtils.isNotBlank(documentUploadData.getBankStatement())){
            //decode image from base64 encoding

            // save bank statement media to dealer

           final CatalogUnawareMediaModel bankStatement =  createMediaFromFile(documentUploadData.getBankStatement(),dealerCode,SclCoreConstants.BANK_STATEMENT_NAME_PREFIX);

           //TODO://REMOVE AND REFACTOR BELOW CODE
           LOG.info("Image is: "+bankStatement.getURL());
           //TODO://logic to set document to dealer model
        }
        if(null!=documentUploadData.getBlankCheque()){
            // save blank cheque media to dealer
            final CatalogUnawareMediaModel blankCheque =   createMediaFromFile(documentUploadData.getBankStatement(),dealerCode,SclCoreConstants.BLANK_CHEQUE_NAME_PREFIX);
            //TODO://REMOVE AND REFACTOR BELOW CODE
            LOG.info("Image is: "+blankCheque.getURL());
            //TODO://logic to set document to dealer model
        }
        if(null!=documentUploadData.getDdDetails()){
            // save DD details media to dealer
            final CatalogUnawareMediaModel ddDetails =   createMediaFromFile(documentUploadData.getDdDetails(),dealerCode,SclCoreConstants.DD_DETAILS_NAME_PREFIX);
            //TODO://REMOVE AND REFACTOR BELOW CODE
            LOG.info("Image is: "+ddDetails.getURL());
            //TODO://logic to set document to dealer model
        }
        if(null!=documentUploadData.getGodownDetails()){
            // save godown media media to dealer
            final CatalogUnawareMediaModel godownDetails =   createMediaFromFile(documentUploadData.getGodownDetails(),dealerCode,SclCoreConstants.GODOWN_DETAILS_NAME_PREFIX);
            //TODO://REMOVE AND REFACTOR BELOW CODE
            LOG.info("Image is: "+godownDetails.getURL());
            //TODO://logic to set document to dealer model
        }
        if(null!=documentUploadData.getLetterHeadCopy()){
            // save letter head copy  media to dealer
            final CatalogUnawareMediaModel letterHeadCopy =   createMediaFromFile(documentUploadData.getLetterHeadCopy(),dealerCode,SclCoreConstants.LETTER_HEAD_COPY_NAME_PREFIX);
            //TODO://REMOVE AND REFACTOR BELOW CODE
            LOG.info("Image is: "+letterHeadCopy.getURL());
            //TODO://logic to set document to dealer model
        }
        if(null!=documentUploadData.getGstDetails()){
            // save GST details media to dealer
            final CatalogUnawareMediaModel gstDetails =  createMediaFromFile(documentUploadData.getGstDetails(),dealerCode,SclCoreConstants.GST_DETAILS_NAME_PREFIX);
            //TODO://REMOVE AND REFACTOR BELOW CODE
            LOG.info("Image is: "+gstDetails.getURL());
            //TODO://logic to set document to dealer model
        }
        if(null!=documentUploadData.getPanCard()){
            // save pan card media to dealer
            final CatalogUnawareMediaModel panCard =  createMediaFromFile(documentUploadData.getPanCard(),dealerCode,SclCoreConstants.PAN_CARD_NAME_PREFIX);
            //TODO://REMOVE AND REFACTOR BELOW CODE
            LOG.info("Image is: "+panCard.getURL());
            //TODO://logic to set document to dealer model
        }



    }
    private CatalogUnawareMediaModel createMediaFromFile(final MultipartFile file , final String dealerCode, final String fileNamePrefix)  {

        final String mediaCode = fileNamePrefix.concat(dealerCode);

        final MediaFolderModel imageMediaFolder = mediaService.getFolder(SclCoreConstants.IMAGE_MEDIA_FOLDER_NAME);
        CatalogUnawareMediaModel documentMedia = null;

        try{
            documentMedia = (CatalogUnawareMediaModel) getMediaService().getMedia(mediaCode);
        }
        catch (AmbiguousIdentifierException ex){
            LOG.error("More than one media found with code : "+mediaCode);
            LOG.error("Removing duplicate media : "+mediaCode);
            CatalogUnawareMediaModel duplicateMedia = new CatalogUnawareMediaModel();
            duplicateMedia.setCode(mediaCode);
            List<CatalogUnawareMediaModel> duplicateMedias = flexibleSearchService.getModelsByExample(duplicateMedia);
            getModelService().removeAll(duplicateMedias);
        }
        catch (UnknownIdentifierException uie){
            LOG.error("No Media found with code : "+mediaCode);
        }
        finally {
            if(null == documentMedia){
                documentMedia = getModelService().create(CatalogUnawareMediaModel.class);
                documentMedia.setCode(mediaCode);
            }
        }

        documentMedia.setAltText(fileNamePrefix);
        documentMedia.setFolder(imageMediaFolder);
        documentMedia.setMime(file.getContentType());
        getModelService().save(documentMedia);
        try{
            getMediaService().setStreamForMedia(documentMedia,file.getInputStream());
        }
        catch (IOException ioe){
            LOG.error("IO Exception occured while creating: "+fileNamePrefix+ " ,for dealer: "+dealerCode);
        }

        return (CatalogUnawareMediaModel) getMediaService().getMedia(mediaCode);

    }

    public MediaService getMediaService() {
        return mediaService;
    }

    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
*/

    private CatalogUnawareMediaModel createMediaFromFile(final String dealerCode, final String documentType,final MultipartFile file )  {

        final String mediaCode = documentType.concat(SclCoreConstants.UNDERSCORE_CHARACTER).concat(dealerCode);

        final MediaFolderModel imageMediaFolder = mediaService.getFolder(SclCoreConstants.IMAGE_MEDIA_FOLDER_NAME);
        CatalogUnawareMediaModel documentMedia = null;

        try{
            documentMedia = (CatalogUnawareMediaModel) getMediaService().getMedia(mediaCode);
        }
        catch (AmbiguousIdentifierException ex){
            LOG.error("More than one media found with code : "+mediaCode);
            LOG.error("Removing duplicate media : "+mediaCode);
            CatalogUnawareMediaModel duplicateMedia = new CatalogUnawareMediaModel();
            duplicateMedia.setCode(mediaCode);
            List<CatalogUnawareMediaModel> duplicateMedias = getFlexibleSearchService().getModelsByExample(duplicateMedia);
            getModelService().removeAll(duplicateMedias);
        }
        catch (UnknownIdentifierException uie){
            LOG.error("No Media found with code : "+mediaCode);
        }
        finally {
            if(null == documentMedia){
                documentMedia = getModelService().create(CatalogUnawareMediaModel.class);
                documentMedia.setCode(mediaCode);
            }
        }

        documentMedia.setAltText(documentType);
        documentMedia.setFolder(imageMediaFolder);
        documentMedia.setMime(file.getContentType());
        getModelService().save(documentMedia);
        try{
            getMediaService().setStreamForMedia(documentMedia,file.getInputStream());
        }
        catch (IOException ioe){
            LOG.error("IO Exception occured while creating: "+documentType+ " ,for dealer: "+dealerCode);
        }

        return (CatalogUnawareMediaModel) getMediaService().getMedia(mediaCode);

    }
    public SclCustomerService getSclCustomerService() {
        return sclCustomerService;
    }

    public void setSclCustomerService(SclCustomerService sclCustomerService) {
        this.sclCustomerService = sclCustomerService;
    }
    public MediaService getMediaService() {
        return mediaService;
    }

    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }
    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
