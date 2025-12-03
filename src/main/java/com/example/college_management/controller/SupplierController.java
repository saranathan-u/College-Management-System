package com.example.college_management.controller;

import com.example.college_management.entity.Supplier;
import com.example.college_management.entity.SupplierItem;
import com.example.college_management.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Create a supplier
    @PostMapping("/create")
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.createSupplier(supplier));
    }

    // Add item to a supplier
    @PostMapping("/{supplierId}/items")
    public ResponseEntity<SupplierItem> addItem(
            @PathVariable Long supplierId,
            @RequestBody SupplierItem item
    ) {
        return ResponseEntity.ok(supplierService.addItemToSupplier(supplierId, item));
    }

    // Get all suppliers
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    // (Optional) Get all items across all suppliers
    @GetMapping("/items")
    public ResponseEntity<List<SupplierItem>> getAllItems() {
        return ResponseEntity.ok(supplierService.getAllItems());
    }
}
