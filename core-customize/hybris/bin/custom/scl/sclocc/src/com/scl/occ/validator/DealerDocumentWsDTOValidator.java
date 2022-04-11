/*
package com.scl.occ.validator;

import com.scl.dto.dealer.DealerDocumentUploadWsDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

*/
/**
 * Validator class to validate dealer document upload
 *//*

@Component(value = "dealerDocumentWsDTOValidator")
public class DealerDocumentWsDTOValidator implements Validator {

    private static final String PNG_MIME_TYPE    =  "image/png";
    private static final String JPEG_MIME_TYPE   =  "image/jpeg";
    private static final String JPG_MIME_TYPE    =  "image/jpg";
    private static final long   FIVE_MB_IN_BYTES =   5242880;

    @Override
    public boolean supports(Class<?> aClass) {
        return DealerDocumentUploadWsDTO.class.equals(aClass);
    }

    */
/**
     * To validate dealer document upload
     * @param object
     * @param errors
     *//*

    @Override
    public void validate(Object object, Errors errors) {
        final DealerDocumentUploadWsDTO dealerDocuments = (DealerDocumentUploadWsDTO) object;
        if(StringUtils.isBlank(dealerDocuments.getDealerCode())){
            errors.rejectValue("dealerCode", "error.dealercode.empty");
        }
        //Validate Pan Card
        validateImageDocument(dealerDocuments.getPanCard(),errors);

        //Validate Bank Statement
        validateImageDocument(dealerDocuments.getBankStatement(),errors);

        //Validate DDDetail
        validateImageDocument(dealerDocuments.getDdDetails(),errors);

        //Validate Blank Cheque
        validateImageDocument(dealerDocuments.getBlankCheque(),errors);

        //Validate Godown details
        validateImageDocument(dealerDocuments.getGodownDetails(),errors);

        //Validate Gst Details
        validateImageDocument(dealerDocuments.getGstDetails(),errors);

        //Validate Letter Head Copy
        validateImageDocument(dealerDocuments.getLetterHeadCopy(),errors);

    }

    */
/**
     * Method to Validate the image document
     * @param file
     * @param errors
     *//*

    private void validateImageDocument(MultipartFile file , Errors errors){

        if(!(PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) ||  JPEG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) || JPG_MIME_TYPE.equalsIgnoreCase(file.getContentType()))){
            errors.rejectValue("imageFile", "image.upload.invalid.file.type");
        }

        else if(file.getSize() > FIVE_MB_IN_BYTES){
            errors.rejectValue("imageFile", "image.upload.exceeded.file.size");
        }
    }
}
*/
