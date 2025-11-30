package org.delcom.app.controllers;

// import org.delcom.app.CashFlowController; // INI DIHAPUS KARENA SATU PACKAGE
import org.delcom.app.entities.CashFlowTest;
import org.delcom.app.repositories.CashFlowRepository; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashFlowControllerTest {

    @Mock
    private CashFlowRepository cashFlowRepository;

    @Mock
    private Model model;

    @InjectMocks
    private CashFlowController cashFlowController;

    private List<CashFlowTest> dummyData;

    @BeforeEach
    void setUp() {
        // Persiapan data dummy untuk test logika hitung saldo
        dummyData = new ArrayList<>();
        
        // Data 1: Pemasukan 100.000 (Pastikan method di Entity sesuai, misal setAmount/setNominal)
        CashFlowTest t1 = new CashFlowTest();
        t1.setId(1L);
        t1.setType("Pemasukan");
        t1.setAmount(100000.0); // Sesuaikan dengan Entity Anda (setNominal atau setAmount)
        t1.setTanggal(LocalDateTime.now());
        
        // Data 2: Pengeluaran 50.000
        CashFlowTest t2 = new CashFlowTest();
        t2.setId(2L);
        t2.setType("Pengeluaran");
        t2.setAmount(50000.0); // Sesuaikan dengan Entity Anda
        t2.setTanggal(LocalDateTime.now());

        dummyData.add(t1);
        dummyData.add(t2);
    }

    @Test
    void testListCashFlow() {
        // Skenario: Repository mengembalikan list data dummy
        when(cashFlowRepository.findAll()).thenReturn(dummyData);

        // Eksekusi method controller
        String viewName = cashFlowController.listCashFlow(model);

        // Verifikasi:
        // 1. Pastikan view yang dikembalikan benar
        assertEquals("cashflow-view", viewName);
        
        // 2. Pastikan repository dipanggil
        verify(cashFlowRepository, times(1)).findAll();
        
        // 3. Pastikan perhitungan matematika dijalankan dan dikirim ke Model
        // Total Masuk (100k) - Total Keluar (50k) = 50k
        verify(model).addAttribute("listCashFlow", dummyData);
        verify(model).addAttribute(eq("totalMasuk"), any(Double.class));
        verify(model).addAttribute(eq("totalKeluar"), any(Double.class));
        verify(model).addAttribute(eq("sisaSaldo"), any(Double.class));
    }

    @Test
    void testAddCashFlowForm() {
        String viewName = cashFlowController.addCashFlowForm(model);
        
        assertEquals("cashflow-add", viewName);
        verify(model).addAttribute(eq("cashFlow"), any(CashFlowTest.class));
    }

    @Test
    void testSaveCashflow_WithDate() {
        CashFlowTest newData = new CashFlowTest();
        newData.setTanggal(LocalDateTime.now()); // Tanggal sudah ada

        String viewName = cashFlowController.saveCashflow(newData);

        assertEquals("redirect:/cashflow", viewName);
        verify(cashFlowRepository, times(1)).save(newData);
    }

    @Test
    void testSaveCashflow_WithoutDate() {
        CashFlowTest newData = new CashFlowTest();
        newData.setTanggal(null); // Tanggal kosong, harus diisi otomatis oleh controller

        String viewName = cashFlowController.saveCashflow(newData);

        assertEquals("redirect:/cashflow", viewName);
        verify(cashFlowRepository, times(1)).save(newData);
    
        assertNotNull(newData.getTanggal());
    }

    @Test
    void testDeleteCashFlow() {
        Long idToDelete = 1L;
        
        String viewName = cashFlowController.deleteCashFlow(idToDelete);

        assertEquals("redirect:/cashflow", viewName);
        verify(cashFlowRepository, times(1)).deleteById(idToDelete);
    }
}
