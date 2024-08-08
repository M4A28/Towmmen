package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.InvoiceDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.DeleteInvoice
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.DeleteInvoiceItem
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetAllInvoice
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetAllInvoiceProfit
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetAllInvoicesWithItems
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetInvoice
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetInvoiceAvg
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetInvoiceByCustomer
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetInvoiceCountByMonth
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetInvoiceItems
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetInvoiceProfitByMonth
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetInvoiceProfitForCurrentDay
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetInvoiceProfitForCurrentMonth
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetInvoicesWithItemsByCustomerId
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetMaxInvoice
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetMinInvoice
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetTopSelling
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetTopSellingCurrentDay
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.GetTopSellingCurrentMonth
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.InsertFullInvoice
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.InvoiceUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.UpsertInvoice
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.UpsertInvoiceItems
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InvoiceModule {

    @Provides
    @Singleton
    fun providesInvoiceDao(tommenDatabase: TowmmenDatabase): InvoiceDao{
        return tommenDatabase.invoiceDao
    }
    @Provides
    @Singleton
    fun providesInvoiceRepository(invoiceDao: InvoiceDao): InvoiceRepositoryImp{
        return InvoiceRepositoryImp(invoiceDao)
    }

    @Provides
    @Singleton
    fun providesInvoiceUseCases(invoiceRepo: InvoiceRepositoryImp): InvoiceUseCases{
        return InvoiceUseCases(
            upsertInvoice = UpsertInvoice(invoiceRepo),
            deleteInvoice = DeleteInvoice(invoiceRepo),
            getAllInvoice = GetAllInvoice(invoiceRepo),
            getInvoice = GetInvoice(invoiceRepo),
            upsertInvoiceItems = UpsertInvoiceItems(invoiceRepo),
            getInvoiceItems = GetInvoiceItems(invoiceRepo),
            deleteInvoiceItem = DeleteInvoiceItem(invoiceRepo),
            getAllInvoicesWithItems = GetAllInvoicesWithItems(invoiceRepo),
            insertFullInvoice = InsertFullInvoice(invoiceRepo),
            getMinInvoice = GetMinInvoice(invoiceRepo),
            getMaxInvoice = GetMaxInvoice(invoiceRepo),
            getInvoiceProfitByMonth = GetInvoiceProfitByMonth(invoiceRepo),
            getInvoiceAvg = GetInvoiceAvg(invoiceRepo),
            getInvoiceCountByMonth = GetInvoiceCountByMonth(invoiceRepo),
            getAllInvoiceProfit = GetAllInvoiceProfit(invoiceRepo),
            getInvoiceByCustomer = GetInvoiceByCustomer(invoiceRepo),
            getInvoicesWithItemsByCustomerId = GetInvoicesWithItemsByCustomerId(invoiceRepo),
            getTopSelling = GetTopSelling(invoiceRepo),
            getTopSellingCurrentDay = GetTopSellingCurrentDay(invoiceRepo),
            getTopSellingCurrentMonth = GetTopSellingCurrentMonth(invoiceRepo),
            getInvoiceProfitForCurrentMonth = GetInvoiceProfitForCurrentMonth(invoiceRepo),
            getInvoiceProfitForCurrentDay = GetInvoiceProfitForCurrentDay(invoiceRepo),
        )
    }
}