package com.kirussell.meetupsept2017


import com.kirussell.meetupsept2017.models.BookingService
import com.kirussell.meetupsept2017.models.Deposit
import com.kirussell.meetupsept2017.models.Notes

/**
 * 1) constructors
 * 2) static constants
 * 2) nullability
 * 3) std lib extension func s
 * 4) type inference
 * 5) if/when as expression
 * 6) forEach\
 * 7) Card nullability
 * 8) Card nullability
 */

private val DEFAULT_SERVICE = BookingService.DIRECT
private val DEFAULT_NOTES = Notes(Notes.BedType.QUIN, "")

class HotelRoom2 @JvmOverloads internal constructor(
        private val id: String,
        private val deposit: Deposit? = null,
        private val service: BookingService = DEFAULT_SERVICE,
        private val notes: Notes = DEFAULT_NOTES
) {

    private val towels = mapOf(Pair("HANDS", 2), Pair("BODY", 2), Pair("BEACH", 2))

    fun checkOut(spentOnBar: Double, spentInRestaurants: Double,
                 areThereHappyHours: Boolean)
            = TotalResult().apply {
        totalAmount = calculateTotalAmount(spentOnBar, spentInRestaurants, areThereHappyHours)
        depositAvailable = deposit != null && deposit.card?.checkAmount() ?: false
        canPayFromDeposit = depositAvailable && totalAmount <= (deposit?.amount ?: Double.MAX_VALUE)
    }

    private fun calculateTotalAmount(spentOnBar: Double, spentInRestaurants: Double, areThereHappyHours: Boolean)
            = (if (areThereHappyHours) spentOnBar / 2.0 else spentOnBar) + spentInRestaurants

    fun sendConfirmationToBookingService(manager: BookingServicesManager) = when (service) {
        BookingService.DIRECT -> false
        BookingService.EXPEDIA,
        BookingService.BOOKING,
        BookingService.AGODA -> manager.sendConfirmation(service, id)
    }

    private val totalCountOfTowels by lazy { towels.values.sum() }

    fun verifyTowels(quantity: Int) = totalCountOfTowels == quantity

    val preferedOptions
        get() = "Bed type: ${notes.bedType} \n Comments: ${notes.comments}"

    inner class TotalResult {
        internal var totalAmount = 0.0
        internal var depositAvailable = false
        internal var canPayFromDeposit = false
    }
}
