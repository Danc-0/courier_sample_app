package com.courier.sgacourierapp.models;

import com.courier.sgacourierapp.common.CourierEnums.CustomerTypes;
import com.courier.sgacourierapp.common.CourierEnums.PaymentTerms;
import com.courier.sgacourierapp.common.CourierEnums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerDTO {
    private CustomerTypes customerType;
    private PaymentTerms paymentTerms;
    private Status status;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
