package Lab_5_Starter;

// Import necessary packages for exceptions
public class CellPhone {
   // Fields
   private String model;
   private String manufacturer;
   private double retailPrice;

   // Constructor with validation
   public CellPhone(String m, String man, double price) throws InvalidModelException, InvalidManufacturerException, InvalidRetailPriceException {
      setModel(m);
      setManufacturer(man);
      setRetailPrice(price);
   }

   // Default constructor
   public CellPhone() {
      this.model = "";
      this.manufacturer = "";
      this.retailPrice = 0.0;
   }

   // Mutators (setters) with exception handling
   public void setModel(String m) throws InvalidModelException {
      if (m == null || m.trim().isEmpty()) {
         throw new InvalidModelException("Model cannot be empty!");
      }
      this.model = m;
   }

   public void setManufacturer(String man) throws InvalidManufacturerException {
      if (man == null || man.trim().isEmpty()) {
         throw new InvalidManufacturerException("Manufacturer cannot be empty!");
      }
      this.manufacturer = man;
   }

   public void setRetailPrice(double price) throws InvalidRetailPriceException {
      if (price < 0 || price > 1500) {
         throw new InvalidRetailPriceException("Price must be between 0 and 1500!");
      }
      this.retailPrice = price;
   }

   // Accessors (getters)
   public String getModel() {
      return model;
   }

   public String getManufacturer() {
      return manufacturer;
   }

   public double getRetailPrice() {
      return retailPrice;
   }

   // toString override
   @Override
   public String toString() {
      return String.format("Model: %-20s Manufacturer: %-20s Retail Price: %10.2f%n",
              model, manufacturer, retailPrice);
   }
}
