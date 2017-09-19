package com.kirussell.meetupsept2017

import com.kirussell.meetupsept2017.models.BookingService
import com.kirussell.meetupsept2017.models.Card
import com.kirussell.meetupsept2017.models.Deposit
import com.kirussell.meetupsept2017.models.Notes
import org.junit.Test

import org.junit.Assert.*

class HotelRoomTests {
    val id = "testId"

    @Test
    fun checkOutNoDeposit() {
        val room = HotelRoom1(id)
        val checkOutResult = room.checkOut(0.0, 0.0, false)
        assertFalse(checkOutResult.canPayFromDeposit)
        assertFalse(checkOutResult.depositAvailable)
        assertEquals(checkOutResult.totalAmount, 0.0, 0.0)
    }

    @Test
    fun checkOutWithDepositEnoughMoney() {
        val room = HotelRoom1(id, Deposit(Card(), 100.0))
        val checkOutResult = room.checkOut(50.0, 50.0, false)
        assertTrue(checkOutResult.canPayFromDeposit)
        assertTrue(checkOutResult.depositAvailable)
        assertEquals(checkOutResult.totalAmount, 100.0, 0.1)
    }

    @Test
    fun checkOutWithDepositNotEnoughMoney() {
        val room = HotelRoom1(id, Deposit(Card(), 75.0))
        val checkOutResult = room.checkOut(50.0, 50.0, false)
        assertFalse(checkOutResult.canPayFromDeposit)
        assertTrue(checkOutResult.depositAvailable)
        assertEquals(checkOutResult.totalAmount, 100.0, 0.1)
    }

    @Test
    fun checkOutWithDepositAndHappyHours() {
        val room = HotelRoom1(id, Deposit(Card(), 75.0))
        val checkOutResult = room.checkOut(50.0, 50.0, true)
        assertTrue(checkOutResult.canPayFromDeposit)
        assertTrue(checkOutResult.depositAvailable)
        assertEquals(checkOutResult.totalAmount, 75.0, 0.1)
    }

    @Test
    fun verifyTowels() {
        val room = HotelRoom1(id, Deposit(Card(), 100.0))
        assertTrue(room.verifyTowels(6))
        assertFalse(room.verifyTowels(7))
    }

    @Test
    fun getPreferredOptions() {
        val room = HotelRoom1(id, Deposit(Card(), 100.0),
                BookingService.DIRECT, Notes(Notes.BedType.KING, "TestComment"))
        assertTrue(room.preferedOptions.contains("KING"))
        assertTrue(room.preferedOptions.contains("TestComment"))

    }

    @Test
    fun sendConfirmation() {
        val room = HotelRoom1(id, Deposit(Card(), 100.0),
                BookingService.AGODA, Notes(Notes.BedType.KING, "TestComment"))
        assertTrue(room.sendConfirmationToBookingService(BookingServicesManager()))
    }
}
