package com.ntnt.dut.crawlerapi.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private Object result;
    private String message;
}
