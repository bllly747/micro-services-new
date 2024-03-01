package com.BillyCodes.inventoryservice.Service;

import com.BillyCodes.inventoryservice.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public boolean checkSkuCode(String skuCode)
    {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}

