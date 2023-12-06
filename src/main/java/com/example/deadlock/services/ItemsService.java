package com.example.deadlock.services;

import com.example.deadlock.entities.Item;
import com.example.deadlock.repository.ItemsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemsService {

    private ItemsRepository itemsRepository;

    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Transactional
    public List<Item> sendForUpdate() {
        List<Item> items = itemsRepository.findNotProcessed();
        items.forEach(item -> {
            item.setStatus("SENT");
            itemsRepository.save(item);
        });
        return items;
    }

    @Transactional
    public void processResponse(List<Item> items) {
        items.forEach(item -> {
            item.setStatus("NOT_PROCESSED");
            itemsRepository.save(item);
        });
    }

}
