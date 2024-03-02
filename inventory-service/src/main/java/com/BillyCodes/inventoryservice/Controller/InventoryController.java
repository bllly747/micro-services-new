package com.BillyCodes.inventoryservice.Controller;


import com.BillyCodes.inventoryservice.Dtos.InventoryResponse;
import com.BillyCodes.inventoryservice.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> checkSkuCode(@RequestParam List<String> skuCode)
    {
        return inventoryService.checkSkuCode(skuCode);
    }
}
