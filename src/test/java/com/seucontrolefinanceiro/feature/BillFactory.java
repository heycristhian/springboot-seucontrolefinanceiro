package com.seucontrolefinanceiro.feature;

import com.seucontrolefinanceiro.domain.model.Bill;
import com.seucontrolefinanceiro.domain.model.BillType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BillFactory {

    public static Bill getBill() {
        return Bill.builder()
                .billDescription("Academia")
                .amount(BigDecimal.valueOf(100.90))
                .everyMonth(true)
                .payDAy(LocalDate.now())
                .billType(BillType.PAYMENT)
                .paymentCategory(PaymentCategoryFactory.getCategoryWithId())
                .paid(false)
                .parent("123")
                .userId("432")
                .portion(24)
                .paidIn(LocalDate.now().plusDays(10))
                .build();
    }

    public static List<Bill> getBills() {
        var bill1 = Bill.builder()
                .billDescription("Academia")
                .amount(BigDecimal.valueOf(100.90))
                .everyMonth(true)
                .payDAy(LocalDate.now())
                .billType(BillType.PAYMENT)
                .paymentCategory(PaymentCategoryFactory.getCategoryWithId())
                .paid(false)
                .parent("123")
                .userId("432")
                .portion(24)
                .paidIn(LocalDate.now().plusDays(10))
                .build();

        var bill2 = Bill.builder()
                .billDescription("Mercado")
                .amount(BigDecimal.valueOf(1000.90))
                .everyMonth(true)
                .payDAy(LocalDate.now())
                .billType(BillType.PAYMENT)
                .paymentCategory(PaymentCategoryFactory.getCategoryWithId())
                .paid(false)
                .parent("987")
                .userId("432")
                .portion(24)
                .paidIn(LocalDate.now().plusDays(10))
                .build();

        return List.of(bill1, bill2);
    }
}