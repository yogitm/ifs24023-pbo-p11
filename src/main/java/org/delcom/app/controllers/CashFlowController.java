package org.delcom.app.controllers;

import org.delcom.app.entities.CashFlowTest;
import org.delcom.app.repositories.CashFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CashFlowController {

    @Autowired
    private CashFlowRepository cashFlowRepository; 

    // 1. Menampilkan halaman List & Ringkasan Saldo
    
    @GetMapping("/cashflow")
    public String listCashFlow(Model model) {
        // A. Ambil semua data dari database
        List<CashFlowTest> listData = cashFlowRepository.findAll();

        // B. Hitung Total Pemasukan
        double totalMasuk = listData.stream()
                .filter(item -> "Pemasukan".equalsIgnoreCase(item.getType())) 
                .mapToDouble(CashFlowTest::getAmount) 
                .sum(); 

        // C. Hitung Total Pengeluaran
        double totalKeluar = listData.stream()
                .filter(item -> "Pengeluaran".equalsIgnoreCase(item.getType())) 
                .mapToDouble(CashFlowTest::getAmount)
                .sum();

        // D. Hitung Sisa Saldo (Masuk - Keluar)
        double sisaSaldo = totalMasuk - totalKeluar;

        // E. Kirim semua data ke HTML (View)
        model.addAttribute("listCashFlow", listData);
        model.addAttribute("totalMasuk", totalMasuk);
        model.addAttribute("totalKeluar", totalKeluar);
        model.addAttribute("sisaSaldo", sisaSaldo);

        return "cashflow-view";
    }

    // 2. Menampilkan Form Tambah
    @GetMapping("/cashflow/add")
    public String addCashFlowForm(Model model) {
        model.addAttribute("cashFlow", new CashFlowTest());
        return "cashflow-add"; 
    }

    // 3. Proses Simpan Data
    @PostMapping("/cashflow/save")
    public String saveCashflow(@ModelAttribute CashFlowTest cashFlow) {
        // Set waktu otomatis saat ini
        if (cashFlow.getTanggal() == null) {
            cashFlow.setTanggal(LocalDateTime.now());
        }
        cashFlowRepository.save(cashFlow);
        return "redirect:/cashflow";
    }

    // 4. Hapus Data
    @GetMapping("/cashflow/delete/{id}")
    public String deleteCashFlow(@PathVariable Long id) {
        cashFlowRepository.deleteById(id);
        return "redirect:/cashflow";
    }

    // // 5. Tampilkan Form Edit (Ambil data berdasarkan ID)
    // @GetMapping("/cashflow/edit/{id}")
    // public String editCashFlowForm(@PathVariable Long id, Model model) {
    //     // Cari data di database berdasarkan ID
    //     CashFlowTest data = cashFlowRepository.findById(id).orElse(null);
        
    //     // Jika data tidak ketemu, balik ke halaman utama
    //     if (data == null) {
    //         return "redirect:/cashflow";
    //     }
        
    //     // Kirim data yang ketemu ke form agar terisi otomatis
    //     model.addAttribute("cashFlow", data);
        
    //     // Gunakan file HTML yang sama dengan Tambah (Reuse)
    //     return "cashflow-add"; 
    // }

    //  // 6. Tampilkan Halaman Detail
    // @GetMapping("/cashflow/detail/{id}")
    // public String detailCashFlow(@PathVariable Long id, Model model) {
    //     // Ambil data berdasarkan ID
    //     CashFlowTest data = cashFlowRepository.findById(id).orElse(null);
        
    //     // Jika data tidak ada, kembalikan ke list utama
    //     if (data == null) {
    //         return "redirect:/cashflow";
    //     }
        
    //     // Kirim data ke view
    //     model.addAttribute("cashFlow", data);
        
    //     // Buka file template baru 
    //     return "cashflow-detail"; 
    // }
}