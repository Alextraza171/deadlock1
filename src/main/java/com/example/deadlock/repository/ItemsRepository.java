package com.example.deadlock.repository;

import com.example.deadlock.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.status = 'NOR_PROCESSED'")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Item> findNotProcessed();

}
