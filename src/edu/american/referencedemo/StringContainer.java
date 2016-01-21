package edu.american.referencedemo;

/**
 * A container class for the purpose of treating Strings as mutable objects
 *
 * @author knappa
 * @version 1.0
 */
class StringContainer {

    private String containedString;

    /**
     * Create a new StringContainer containing stringToContain
     *
     * @param stringToContain the string to contain
     */
    public StringContainer(String stringToContain) {
        this.containedString = stringToContain;
    }

    /**
     * Convert the contained string to upper case
     */
    public void toUpperCase() {

        containedString = containedString.toUpperCase();

    }

    /**
     * Access the contained String
     *
     * @return the contained string
     */
    public String getString() {
        return containedString;
    }

}
