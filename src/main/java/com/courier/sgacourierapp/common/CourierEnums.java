package com.courier.sgacourierapp.common;

import lombok.Getter;

public class CourierEnums {

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public enum UserRoles {
        ADMIN,
        DISPATCHER,
        MANAGER,
        BILLING_STAFF,
        SUPER_USER
    }

    public enum Status {
        ACTIVE,
        INACTIVE,
        PENDING,
        SUSPENDED;
    }

    public enum OrderStatus {
        ACTIVE,
        COMPLETED,
        PENDING,
        CANCELLED
    }

    public enum TrackingStatus {
        STARTED,
        IN_PROGRESS
    }

    public enum CourierType {
        PERISHABLE,
        NON_PERISHABLE,
    }

    @Getter
    public enum CountryCode {
        KENYA("KE"),
        TANZANIA("TZ"),
        UGANDA("UG");

        private final String countryCode;

        CountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

    }

    public enum Verification {
        VERIFIED,
        NOT_VERIFIED
    }

    public enum Activation {
        ACTIVATED,
        NOT_ACTIVATED
    }

    @Getter
    public enum CustomerTypes {
        INDIVIDUAL(0),
        BUSINESS(1);

        private final int customerType;

        CustomerTypes(int customerType) {
            this.customerType = customerType;
        }

    }

    @Getter
    public enum PaymentTerms {
        PREPAID(0),
        POSTPAID(1),
        INVOICE(2);

        private final int paymentTerm;

        PaymentTerms(int paymentTerm) {
            this.paymentTerm = paymentTerm;
        }
    }

    public enum PaymentMethods {
        BANK_TRANSFER,
        MOBILE_MONEY,
        CASH
    }

    public enum OrderTypes {
        EXPRESS,
        DEDICATED
    }

    public enum DispatchStatus {
        ACTIVE,
        ASSIGNED,
        INACTIVE,
    }

    public enum BillingPaymentStatuses {
        PAID,
        PENDING,
        FAILED,
    }

    public enum BillingStatuses {
        BILLED,
        UNBILLED,
    }

}
