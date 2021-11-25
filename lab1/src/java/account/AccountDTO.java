package account;

import booking.BookingDTO;
import resource.ResourceDTO;
import java.util.List;

/**
 *
 * @author pc
 */
public class AccountDTO {

    String userid, phone, fullname, address, createdDate, role, status;
    String password;
    List<BookingDTO> history;

    public AccountDTO(String userid, String phone, String fullname, String address, String createdDate, String role, String status) {
        this.userid = userid;
        this.phone = phone;
        this.fullname = fullname;
        this.address = address;
        this.createdDate = createdDate;
        this.role = role;
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BookingDTO> getHistory() {
        return history;
    }

    public void setHistory(List<BookingDTO> history) {
        this.history = history;
    }

}
