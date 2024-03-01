package com.BillyCodes.inventoryservice.Controller;


import com.BillyCodes.inventoryservice.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkSkuCode(@PathVariable("sku-code") String skuCode)
    {
        return inventoryService.checkSkuCode(skuCode);
    }
}
