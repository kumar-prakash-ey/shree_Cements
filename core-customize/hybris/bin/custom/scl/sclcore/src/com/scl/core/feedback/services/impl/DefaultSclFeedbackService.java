package com.scl.core.feedback.services.impl;

import com.scl.core.feedback.dao.SclFeedbackDao;
import com.scl.core.feedback.services.SclFeedbackService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

public class DefaultSclFeedbackService implements SclFeedbackService {

    private ModelService modelService;
    private SclFeedbackDao sclFeedbackDao;

    /**
     * Creating  feeback with provided fields
     * @param rating
     * @param headline
     * @param comment
     * @param user
     * @param product
     * @param order
     * @return
     */
    @Override
    public CustomerReviewModel createFeedback(Double rating, String headline, String comment, UserModel user, ProductModel product ,  OrderModel order){

        CustomerReviewModel review = (CustomerReviewModel)this.getModelService().create(CustomerReviewModel.class);
        review.setUser(user);
        review.setProduct(product);
        if(null!= order){
            review.setOrder(order);
        }
        review.setRating(rating);
        review.setHeadline(headline);
        review.setComment(comment);
        this.getModelService().save(review);
        return review;
    }

    /**
     * getting customer feedback with Order code(Order code is optional, if not present all feedback
     * of that customer will be fetched)
     * @param customer
     * @param order
     * @return
     */
    @Override
    public List<CustomerReviewModel> getFeedbackForCustomerAndOrder(final CustomerModel customer, final OrderModel order){
        return getSclFeedbackDao().findFeedbacksForCustomerAndOrder(customer,order);
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public SclFeedbackDao getSclFeedbackDao() {
        return sclFeedbackDao;
    }

    public void setSclFeedbackDao(SclFeedbackDao sclFeedbackDao) {
        this.sclFeedbackDao = sclFeedbackDao;
    }

}
