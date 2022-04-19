package com.scl.core.prosdealer.service.impl;

import com.scl.core.constants.SclCoreConstants;
import com.scl.core.model.ProspectiveDealerModel;
import com.scl.core.prosdealer.dao.ProsDealerDao;
import com.scl.core.prosdealer.service.ProsDealerService;
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

public class DefaultProsDealerService implements ProsDealerService {

    private ProsDealerDao prosDealerDao;

    private ModelService modelService;

    private MediaService mediaService ;

    private FlexibleSearchService flexibleSearchService;

    private static final Logger LOG = Logger.getLogger(DefaultProsDealerService.class);

    @Override
    public ProspectiveDealerModel getProsDealerByCode(final String prosDealerCode){
        return getProsDealerDao().findProsDealerByCode(prosDealerCode);
    }

    @Override
    public void uploadDealerDocument(ProspectiveDealerModel prosDealer, String documentType, MultipartFile file) {

        final String prosDealerCode = prosDealer.getDealerCode();
        validateParameterNotNull(file, "file must not be null");

        switch (documentType){
            case SclCoreConstants.DOCUMENT_PAN_CARD:
                CatalogUnawareMediaModel panCard = createMediaFromFile(prosDealerCode,SclCoreConstants.DOCUMENT_PAN_CARD,file);
                prosDealer.setPanCardDoc(panCard);
                break;

            case SclCoreConstants.DOCUMENT_TYPE_BANK_STATEMENT:
                CatalogUnawareMediaModel bankStatement = createMediaFromFile(prosDealerCode,SclCoreConstants.DOCUMENT_TYPE_BANK_STATEMENT,file);
                prosDealer.setBankStatementDoc(bankStatement);
                break;

            case SclCoreConstants.DOCUMENT_TYPE_BLANK_CHEQUE:
                CatalogUnawareMediaModel blankCheque = createMediaFromFile(prosDealerCode,SclCoreConstants.DOCUMENT_TYPE_BLANK_CHEQUE,file);
                prosDealer.setBlankChequeDoc(blankCheque);
                break;

            case SclCoreConstants.DOCUMENT_TYPE_DD_DETAILS:
                CatalogUnawareMediaModel ddDetails = createMediaFromFile(prosDealerCode,SclCoreConstants.DOCUMENT_TYPE_DD_DETAILS,file);
                prosDealer.setDdDetailsDoc(ddDetails);
                break;

            case SclCoreConstants.DOCUMENT_TYPE_GODOWN_DETAILS:
                CatalogUnawareMediaModel godownDetails = createMediaFromFile(prosDealerCode,SclCoreConstants.DOCUMENT_TYPE_GODOWN_DETAILS,file);
                prosDealer.setGodownSpaceDoc(godownDetails);
                break;

            case SclCoreConstants.DOCUMENT_TYPE_GST_DETAILS:
                CatalogUnawareMediaModel gstDetails = createMediaFromFile(prosDealerCode,SclCoreConstants.DOCUMENT_TYPE_GST_DETAILS,file);
                prosDealer.setGstDetailsDoc(gstDetails);
                break;

            case SclCoreConstants.DOCUMENT_TYPE_LETTER_HEAD_COPY:
                CatalogUnawareMediaModel letterHead = createMediaFromFile(prosDealerCode,SclCoreConstants.DOCUMENT_TYPE_LETTER_HEAD_COPY,file);
                prosDealer.setLetterHeadCopyDoc(letterHead);
                break;
        }

        getModelService().save(prosDealer);

    }
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

    public ProsDealerDao getProsDealerDao() {
        return prosDealerDao;
    }

    public void setProsDealerDao(ProsDealerDao prosDealerDao) {
        this.prosDealerDao = prosDealerDao;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
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
}
