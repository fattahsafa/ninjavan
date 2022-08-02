package com.toptal.lib.beans;

public class Product {
  private String name;
  private String[] sizes;
  private String[] colors;
  private String availability;

  public Product(String name, String[] sizes, String[] colors, String availability) {
    this.name = name;
    this.sizes = sizes;
    this.colors = colors;
    this.availability = availability;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getSizes() {
    return sizes;
  }

  public void setSizes(String[] sizes) {
    this.sizes = sizes;
  }

  public String[] getColors() {
    return colors;
  }

  public void setColors(String[] colors) {
    this.colors = colors;
  }

  public String getAvailability() {
    return availability;
  }

  public void setAvailability(String availability) {
    this.availability = availability;
  }
}
