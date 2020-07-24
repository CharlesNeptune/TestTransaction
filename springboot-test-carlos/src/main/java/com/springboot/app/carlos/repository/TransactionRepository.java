package com.springboot.app.carlos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.app.carlos.models.entity.TransactionEntity;



@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

}
