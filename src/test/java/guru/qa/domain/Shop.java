package guru.qa.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Shop {

    private String name;
    private Integer departmentQuantity;
    private String[] locations;
    private Boolean isActive;
    private EntityData entityData;

    public String getName() {
        return name;
    }

    public Integer getDepartmentQuantity() {
        return departmentQuantity;
    }

    public String[] getLocations() {
        return locations;
    }

    @JsonProperty("isActive")
    public Boolean isActive() {
        return isActive;
    }

    public EntityData getEntityData() {
        return entityData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartmentQuantity(Integer departmentQuantity) {
        this.departmentQuantity = departmentQuantity;
    }

    public void setLocations(String[] locations) {
        this.locations = locations;
    }

    @JsonProperty("isActive")
    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setEntityData(EntityData entityData) {
        this.entityData = entityData;
    }

    public static class EntityData {

        private String phone;
        private Long inn;

        public String getPhone() {
            return phone;
        }

        public Long getInn() {
            return inn;
        }


        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setInn(Long inn) {
            this.inn = inn;
        }
    }
}
