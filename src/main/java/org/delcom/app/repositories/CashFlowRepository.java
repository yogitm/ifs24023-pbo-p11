package org.delcom.app.repositories;

import org.delcom.app.entities.CashFlowTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlowTest, Long> {


  
}
