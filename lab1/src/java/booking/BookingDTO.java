package booking;

/**
 *
 * @author pc
 */
public class BookingDTO {

    String bookingID;

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }
    String username, resourceName, resourceID, date, status, adminApprove;
    int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BookingDTO(String username, String resourceName, String resourceID, String date, String status, String adminApprove) {
        this.username = username;
        this.resourceName = resourceName;
        this.resourceID = resourceID;
        this.date = date;
        this.status = status;
        this.adminApprove = adminApprove;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminApprove() {
        return adminApprove;
    }

    public void setAdminApprove(String adminApprove) {
        this.adminApprove = adminApprove;
    }

}
