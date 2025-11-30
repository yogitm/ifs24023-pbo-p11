package org.delcom.app.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CashFlowCoverageTest {

    // TEST UNTUK FILE 1: CashFlow.java
    @Test
    void testCashFlowEntity() {
        // 1. Test Constructor Parameter
        CashFlow cfParam = new CashFlow("Makan", 20000.0, "Expense");
        assertEquals("Makan", cfParam.getDescription());
        assertEquals(20000.0, cfParam.getAmount());
        assertEquals("Expense", cfParam.getType());
        assertNotNull(cfParam.getTanggal()); 

        // 2. Test Setter & Getter Manual
        CashFlow cf = new CashFlow();
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();

        cf.setId(id);
        cf.setDescription("Gaji");
        cf.setAmount(5000000.0);
        cf.setType("Income");
        cf.setTanggal(now);

        assertEquals(id, cf.getId());
        assertEquals("Gaji", cf.getDescription());
        assertEquals(5000000.0, cf.getAmount());
        assertEquals("Income", cf.getType());
        assertEquals(now, cf.getTanggal());
    }

    //  TEST UNTUK FILE 2: CashFlowTest.java 
    @Test
    void testCashFlowTestEntity() {
        // Test Setter & Getter untuk file entity 'CashFlowTest'
        CashFlowTest cft = new CashFlowTest();
        
        Long id = 2L;
        String desc = "Bayar Air";
        Double amt = 50000.0;
        String type = "Expense";
        LocalDateTime now = LocalDateTime.now();

        cft.setId(id);
        cft.setDescription(desc);
        cft.setAmount(amt);
        cft.setType(type);
        cft.setTanggal(now);

        assertEquals(id, cft.getId());
        assertEquals(desc, cft.getDescription());
        assertEquals(amt, cft.getAmount());
        assertEquals(type, cft.getType());
        assertEquals(now, cft.getTanggal());
    }
}