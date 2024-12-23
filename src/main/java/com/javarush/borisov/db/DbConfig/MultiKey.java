package com.javarush.borisov.db.DbConfig;

import lombok.Getter;

@Getter
public record MultiKey(int year, int month) implements Comparable<MultiKey> {

    @Override
    public String toString() {
        return "MultiKey{" +
               "year='" + year + '\'' +
               ", month='" + month + '\'' +
               '}';
    }

    @Override
    public int compareTo(MultiKey o) {
        int yearCompare = Integer.compare(this.year, o.year);
        if (yearCompare != 0) {
            return yearCompare;
        }
        return Integer.compare(this.month, o.month);
    }
}

