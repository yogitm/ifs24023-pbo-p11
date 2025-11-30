package org.delcom.app.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class CashFlowEntityTest {
  
    @Test
    void testSetterAndGetters() {
      // Buat object baru (ini untuk tes Constructor kosong)
      CashFlowTest cashFlow = new CashFlowTest();

      // Data dummy untuk pengetesan
      Long id = 10L;
      String description = "Bayar Listrik";
      Double amount = 250000.0;
      String type = "Expense";
      LocalDateTime now = LocalDateTime.now();

      // Masukkan data menggunakan SETTER
      cashFlow.setId(id);
      cashFlow.setDescription(description);
      cashFlow.setAmount(amount);
      cashFlow.setType(type);
      cashFlow.setTanggal(now);

      // Jalankan semua GETTER dan pastikan datanya benar (Assert)
      assertEquals(id,cashFlow.getId());
      assertEquals(description,cashFlow.getDescription());
      assertEquals(amount,cashFlow.getAmount());
      assertEquals(type, cashFlow.getType());
      assertEquals(now, cashFlow.getTanggal());

      // Pastikan object tidak null
      assertNotNull(cashFlow);
    }
}