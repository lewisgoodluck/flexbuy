package com.example.flexbuy.user.service.admin;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AdminReportDto {
    private Long totalUsers;
    private Long totalVendors;
    private Long totalProducts;
    private Long totalOrders;
    private Long paidOrders;
    private Long pendingOrders;
    private BigDecimal totalRevenue;
}
