package bank.utils;

public class FullName {
    private String name;
    private String surname;
    private String middleName = null;

    public FullName(String name, String surname, String middleName) {
        this.setName(name);
        this.setSurname(surname);
        this.setMiddleName(middleName);
    }

    public FullName(String name, String surname) {
        this.setName(name);
        this.setSurname(surname);
    }

    @Override
    public String toString(){
        String str = getName();
        if (getMiddleName() != null)
            str += " " + getMiddleName();
        str += " " + getSurname();
        return str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
