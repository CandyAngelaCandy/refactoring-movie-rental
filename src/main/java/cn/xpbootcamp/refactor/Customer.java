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
        double totalAmount = 0d;
        int frequentRenterPoints = 0;
        Enumeration<Rental> rentals = this.rentals.elements();
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "：\n");
        while (rentals.hasMoreElements()) {
            Rental rental = rentals.nextElement();
            //show figures for this rental
            //determine amounts for each line
            double rentCharge = getRentCharge(rental);
            //add frequent renter points
            frequentRenterPoints++;
            if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && rental.getDaysRented() > 1)
                frequentRenterPoints++;

            //show figures for this rental
            result.append("\t")
                  .append(rental.getMovie().getTitle())
                  .append("\t")
                  .append(rentCharge).append("\n");
            totalAmount += rentCharge;
        }
        //add footer lines
        result.append("Amount owed is ").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
        return result.toString();
    }

    private double getRentCharge(Rental rental) {
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
