package com.courier.sgacourierapp.common;

public class CourierEnums {

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public enum UserRole {
        ADMIN,
        CUSTOMER,
        FINANCIAL,
        PACKAGE_HANDLER
    }

    public enum Status {
        ACTIVE,
        INACTIVE,
        PENDING,
        DEACTIVATED
    }

    public enum TrackingStatus {
        STARTED,
        IN_PROGRESS
    }

    public enum CourierType {
        PERISHABLE,
        NON_PERISHABLE,
    }

    public enum CountryCode {
        KENYA("KE"),
        TANZANIA("TZ"),
        UGANDA("UG");

        private final String countryCode;

        CountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryCode() {
            return countryCode;
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
}
