package cn.xpbootcamp.refactor;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {

    private String name;
    private Vector<Rental> rentals = new Vector<>();

    Customer(String name) {
        this.name = name;
    }

    void addRental(Rental rental) {
        rentals.addElement(rental);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        double totalRentCharge = 0d;
        int totalFrequentRenterPoints = 0;
        Enumeration<Rental> rentals = this.rentals.elements();
        StringBuilder receiptInfo = new StringBuilder("Rental Record for " + getName() + "：\n");
        while (rentals.hasMoreElements()) {
            Rental rental = rentals.nextElement();

            double rentCharge = getSingleMovieRentCharge(rental);
            totalRentCharge += rentCharge;

            totalFrequentRenterPoints += getFrequentRenterPoints(rental);

            receiptInfo.append("\t")
                       .append(rental.getMovie().getTitle())
                       .append("\t")
                       .append(rentCharge)
                       .append("\n");
        }

        receiptInfo.append("Amount owed is ")
                   .append(totalRentCharge)
                   .append("\n")
                   .append("You earned ")
                   .append(totalFrequentRenterPoints)
                   .append(" frequent renter points");

        return receiptInfo.toString();
    }

    private int getFrequentRenterPoints(Rental rental) {
        int frequentRenterPoints = 1;
        if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && rental.getDaysRented() > 1) {
            frequentRenterPoints += 1;
        }
        return frequentRenterPoints;
    }

    private double getSingleMovieRentCharge(Rental rental) {
        double rentCharge = 0d;
        switch (rental.getMovie().getPriceCode()) {
            case Movie.HISTORY:
                rentCharge += 2;
                if (rental.getDaysRented() > 2)
                    rentCharge += (rental.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                rentCharge += rental.getDaysRented() * 3;
                break;
            case Movie.CAMPUS:
                rentCharge += 1.5;
                if (rental.getDaysRented() > 3)
                    rentCharge += (rental.getDaysRented() - 3) * 1.5;
                break;
        }
        return rentCharge;
    }

}
