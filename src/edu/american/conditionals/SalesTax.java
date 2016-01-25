package edu.american.conditionals;

import java.util.Scanner;

/**
 * @author knappa
 * @version 1.0
 */
public class SalesTax {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Which state?: ");

        String stateName = scanner.next();

        stateName = stateName.toUpperCase();

        State state = State.valueOf(stateName);

        double salesTax;

        switch (state) {
            case ALABAMA:
                salesTax = 4.000;
                break;
            case ALASKA:
                System.out.println("Brr!");
            case OREGON:
            case DELAWARE:
            case MONTANA:
            case NEW_HAMPSHIRE:
                System.out.println("No Sales Tax");
                salesTax = 0.000;
                break;
            case ARIZONA:
                salesTax = 5.600;
                break;
            case ARKANSAS:
                salesTax = 6.500;
                break;
            case CALIFORNIA:
                salesTax = 7.500;
                break;
            case COLORADO:
                salesTax = 2.900;
                break;
            case CONNECTICUT:
                salesTax = 6.350;
                break;
            case DC:
                salesTax = 5.750;
                break;
            case FLORIDA:
                salesTax = 6.000;
                break;
            case GEORGIA:
                salesTax = 4.000;
                break;
            case HAWAII:
                salesTax = 4.000;
                break;
            case IDAHO:
                salesTax = 6.000;
                break;
            case ILLINOIS:
                salesTax = 6.250;
                break;
            case INDIANA:
                salesTax = 7.000;
                break;
            case IOWA:
                salesTax = 6.000;
                break;
            case KANSAS:
                salesTax = 6.500;
                break;
            case KENTUCKY:
                salesTax = 6.000;
                break;
            case LOUISIANA:
                salesTax = 4.000;
                break;
            case MAINE:
                salesTax = 5.500;
                break;
            case MARYLAND:
                salesTax = 6.000;
                break;
            case MASSACHUSETTS:
                salesTax = 6.250;
                break;
            case MICHIGAN:
                salesTax = 6.000;
                break;
            case MINNESOTA:
                salesTax = 6.875;
                break;
            case MISSISSIPPI:
                salesTax = 7.000;
                break;
            case MISSOURI:
                salesTax = 4.225;
                break;
            case NEBRASKA:
                salesTax = 5.50;
                break;
            case NEVADA:
                salesTax = 6.85;
                break;
            case NEW_JERSEY:
                salesTax = 7.000;
                break;
            case NEW_MEXICO:
                salesTax = 5.125;
                break;
            case NEW_YORK:
                salesTax = 4.00;
                break;
            case NORTH_CAROLINA:
                salesTax = 4.750;
                break;
            case NORTH_DAKOTA:
                salesTax = 5.000;
                break;
            case OHIO:
                salesTax = 5.750;
                break;
            case OKLAHOMA:
                salesTax = 4.500;
                break;
            case PENNSYLVANIA:
                salesTax = 6.000;
                break;
            case RHODE_ISLAND:
                salesTax = 7.000;
                break;
            case SOUTH_CAROLINA:
                salesTax = 6.000;
                break;
            case SOUTH_DAKOTA:
                salesTax = 4.000;
                break;
            case TENNESSEE:
                salesTax = 7.000;
                break;
            case TEXAS:
                salesTax = 6.250;
                break;
            case UTAH:
                salesTax = 4.700;
                break;
            case VERMONT:
                salesTax = 6.000;
                break;
            case VIRGINIA:
                salesTax = 4.300;
                break;
            case WASHINGTON:
                salesTax = 6.500;
                break;
            case WEST_VIRGINIA:
                salesTax = 6.000;
                break;
            case WISCONSIN:
                salesTax = 5.000;
                break;
            case WYOMING:
                salesTax = 4.000;
                break;
            default:
                System.err.println("Missing a State!");
                salesTax = 0;
        }

        System.out.println("The sales tax in "+state+" is "+salesTax);

    }

    public enum State {
        ALABAMA,
        ALASKA,
        ARIZONA,
        ARKANSAS,
        CALIFORNIA,
        COLORADO,
        CONNECTICUT,
        DELAWARE,
        FLORIDA,
        GEORGIA,
        HAWAII,
        IDAHO,
        ILLINOIS,
        INDIANA,
        IOWA,
        KANSAS,
        KENTUCKY,
        LOUISIANA,
        MAINE,
        MARYLAND,
        MASSACHUSETTS,
        MICHIGAN,
        MINNESOTA,
        MISSISSIPPI,
        MISSOURI,
        MONTANA, NEBRASKA,
        NEVADA,
        NEW_HAMPSHIRE,
        NEW_JERSEY,
        NEW_MEXICO,
        NEW_YORK,
        NORTH_CAROLINA,
        NORTH_DAKOTA,
        OHIO,
        OKLAHOMA,
        OREGON,
        PENNSYLVANIA, RHODE_ISLAND,
        SOUTH_CAROLINA,
        SOUTH_DAKOTA,
        TENNESSEE,
        TEXAS,
        UTAH,
        VERMONT,
        VIRGINIA,
        WASHINGTON,
        WEST_VIRGINIA,
        WISCONSIN,
        DC, WYOMING
    }

}
