package com.kirussell.meetupsept2017;


import com.kirussell.meetupsept2017.models.BookingService;
import com.kirussell.meetupsept2017.models.Deposit;
import com.kirussell.meetupsept2017.models.Notes;

import java.util.HashMap;

/**
 * 1) constructors
 * 2) static constants
 * 2) nullability
 * 3) std lib extension func s
 * 4) type inference
 * 5) if/when as expression
 * 6) forEach\
 * 7) lazy initialization
 * 8) Card nullability
 */
public class HotelRoom1 {

    private static final BookingService DEFAULT_SERVICE = BookingService.DIRECT;
    private static final Notes DEFAULT_NOTES = new Notes(Notes.BedType.QUIN, "");

    private final String id;
    private final Deposit deposit;
    private final BookingService service;
    private final Notes notes;

    private final HashMap<String, Integer> towels = new HashMap<>(3);
    {
        towels.put("HANDS", 2);
        towels.put("BODY", 2);
        towels.put("BEACH", 2);
    }

    HotelRoom1(String id) {
        this(id, null, DEFAULT_SERVICE, DEFAULT_NOTES);
    }

    HotelRoom1(String id, Deposit deposit) {
        this(id, deposit, DEFAULT_SERVICE, DEFAULT_NOTES);
    }

    HotelRoom1(String id, Deposit deposit, BookingService service, Notes notes) {
        this.id = id;
        this.deposit = deposit;
        this.service = service;
        this.notes = notes;
    }

    public TotalResult checkOut(double spentOnBar, double spentInRestaurants,
                                boolean areThereHappyHours) {
        TotalResult result = new TotalResult();

        result.totalAmount = calculateTotalAmount(spentOnBar, spentInRestaurants, areThereHappyHours);

        result.depositAvailable = deposit != null && deposit.getCard().checkAmount();

        result.canPayFromDeposit = result.depositAvailable && result.totalAmount <= deposit.getAmount();

        return result;
    }

    private double calculateTotalAmount(double spentOnBar, double spentInRestaurants, boolean areThereHappyHours) {
        return (areThereHappyHours ? spentOnBar / 2.0 : spentOnBar) + spentInRestaurants;
    }

    public boolean sendConfirmationToBookingService(BookingServicesManager manager) {
        if (service == BookingService.DIRECT) {
            return false;
        } else {
            return manager.sendConfirmation(service, id);
        }
    }

    private int totalCountOfTowels = 0;

    public boolean verifyTowels(int quantity) {
        if (totalCountOfTowels == 0) {
            int totalCount = 0;
            for (Integer i : towels.values()) {
                totalCount += i;
            }
            totalCountOfTowels = totalCount;
        }

        return totalCountOfTowels == quantity;
    }

    public String getPreferedOptions() {
        return "Bed type: " + notes.getBedType() + "\n Comments:" + notes.getComments();
    }

    public class TotalResult {
        double totalAmount;
        boolean depositAvailable;
        boolean canPayFromDeposit;
    }
}
