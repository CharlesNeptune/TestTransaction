package com.springboot.app.carlos.repository;

import com.springboot.app.carlos.models.entity.TransactionEntity;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class ReferenceGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        if (obj instanceof TransactionEntity) {
            TransactionEntity transactionEntity = (TransactionEntity) obj;
            Serializable reference = transactionEntity.getReference();
            if (reference != null) {
                return reference;
            }
        }
        return super.generate(session, obj);
    }
}

