package com.example.flexbuy.user.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.model.Vendor;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;


    // controller to view all users and be able to delete them
    @GetMapping("/viewUsers")
    public ResponseEntity<?> viewUsers(){
        List<User> users = adminService.readAllUsers();
        List<AdminResponses.users> usersToJson = users.stream().map(this::mapToUser).toList();
        CustomResponse<List<AdminResponses.users>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "All users Fetched successfully",
            usersToJson
        );

        return ResponseEntity.ok(body);
    }

    @GetMapping("/viewVendors")
    public ResponseEntity<?> viewVendors(){
        List<Vendor> vendors = adminService.readAllVendors();
        List<AdminResponses.vendors> vendorToJson = vendors.stream().map(this::mapToVendor).toList();
        CustomResponse<List<AdminResponses.vendors>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "Vendors fetched successfully",
            vendorToJson
        );

        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(String email){
        adminService.deleteUser(email);
        CustomResponse<Object> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "user deleted successfully",
            null
        );

        return ResponseEntity.ok(body);
    }

    @GetMapping("/reports")
    public ResponseEntity<?> viewReports(){
        AdminReportDto report = adminService.generateReport();
        Map<String,Object> generatedReport = new HashMap<>();
        generatedReport.put("totalUsers", report.getTotalUsers());
        generatedReport.put("totalVendors",report.getTotalVendors());
        generatedReport.put("totalProducts",report.getTotalProducts());
        generatedReport.put("totalOrders",report.getTotalOrders());
        generatedReport.put("paidOrders",report.getPaidOrders());
        generatedReport.put("pendingOrders",report.getPendingOrders());
        generatedReport.put("totalRevenue",report.getTotalRevenue());

        CustomResponse<Map<String,Object>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "reports generated",
            generatedReport
        );

        return ResponseEntity.ok(body);
    }

    private AdminResponses.vendors mapToVendor(Vendor vendor){
        return new AdminResponses.vendors(
            vendor.getId(),
            vendor.getBusinessName(), 
            vendor.getBankAccountName(), 
            vendor.getMobileVendor(), 
            vendor.getMobileNumber(), 
            vendor.getBusinessTin()
        );
    }

    private AdminResponses.users mapToUser(User user){
        List<String> roles = user.getAuthorities().stream().map(auth->auth.getAuthority()).collect(Collectors.toList());
        return new AdminResponses.users(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPhone(),
            roles
        );
    }


}


