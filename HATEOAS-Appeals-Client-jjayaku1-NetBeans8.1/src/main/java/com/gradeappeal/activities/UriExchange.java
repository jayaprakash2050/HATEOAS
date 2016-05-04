/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.activities;

import com.gradeappeal.representations.AppealsUri;


/**
 *
 * @author jayaprakashjayakumar
 */
public class UriExchange {

    public static AppealsUri paymentForOrder(AppealsUri orderUri) {
        checkForValidOrderUri(orderUri);
        return new AppealsUri(orderUri.getBaseUri() + "/payment/" + orderUri.getId().toString());
    }

    public static AppealsUri orderForPayment(AppealsUri paymentUri) {
        checkForValidPaymentUri(paymentUri);
        return new AppealsUri(paymentUri.getBaseUri() + "/order/" + paymentUri.getId().toString());
    }

    public static AppealsUri receiptForPayment(AppealsUri paymentUri) {
        checkForValidPaymentUri(paymentUri);
        return new AppealsUri(paymentUri.getBaseUri() + "/receipt/" + paymentUri.getId().toString());
    }

    public static AppealsUri orderForReceipt(AppealsUri receiptUri) {
        checkForValidReceiptUri(receiptUri);
        return new AppealsUri(receiptUri.getBaseUri() + "/order/" + receiptUri.getId().toString());
    }

    private static void checkForValidOrderUri(AppealsUri orderUri) {
        if (!orderUri.toString().contains("/order/")) {
            throw new RuntimeException("Invalid Order URI");
        }
    }

    private static void checkForValidPaymentUri(AppealsUri payment) {
        if (!payment.toString().contains("/payment/")) {
            throw new RuntimeException("Invalid Payment URI");
        }
    }

    private static void checkForValidReceiptUri(AppealsUri receipt) {
        if (!receipt.toString().contains("/receipt/")) {
            throw new RuntimeException("Invalid Receipt URI");
        }
    }

}
