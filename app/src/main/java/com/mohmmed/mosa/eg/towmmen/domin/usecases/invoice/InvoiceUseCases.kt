package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

data class InvoiceUseCases(
    val upsertInvoice: UpsertInvoice,
    val deleteInvoice: DeleteInvoice,
    val getAllInvoice: GetAllInvoice,
    val getInvoice: GetInvoice,
    val upsertInvoiceItems: UpsertInvoiceItems,
    val getInvoiceItems: GetInvoiceItems,
    val deleteInvoiceItem: DeleteInvoiceItem,
    val getAllInvoicesWithItems: GetAllInvoicesWithItems,
    val insertFullInvoice: InsertFullInvoice,
    val getMinInvoice: GetMinInvoice,
    val getMaxInvoice: GetMaxInvoice,
    val getInvoiceProfitByMonth: GetInvoiceProfitByMonth,
    val getInvoiceAvg: GetInvoiceAvg,
    val getInvoiceCountByMonth: GetInvoiceCountByMonth,
    val getAllInvoiceProfit: GetAllInvoiceProfit,
    val getInvoiceByCustomer: GetInvoiceByCustomer
)