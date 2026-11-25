package com.campuslands.proyecto_spring_boot_coworking.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "membership_plans")
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer includedHoursPerMonth;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal overageDiscount;

    @Column(nullable = false)
    private Integer maxActiveBookings;

    @JsonIgnore
    @OneToMany(mappedBy = "membershipPlan", fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    public MembershipPlan() {
    }

    public MembershipPlan(String name, Integer includedHoursPerMonth, BigDecimal overageDiscount, Integer maxActiveBookings) {
        this.name = name;
        this.includedHoursPerMonth = includedHoursPerMonth;
        this.overageDiscount = overageDiscount;
        this.maxActiveBookings = maxActiveBookings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIncludedHoursPerMonth() {
        return includedHoursPerMonth;
    }

    public void setIncludedHoursPerMonth(Integer includedHoursPerMonth) {
        this.includedHoursPerMonth = includedHoursPerMonth;
    }

    public BigDecimal getOverageDiscount() {
        return overageDiscount;
    }

    public void setOverageDiscount(BigDecimal overageDiscount) {
        this.overageDiscount = overageDiscount;
    }

    public Integer getMaxActiveBookings() {
        return maxActiveBookings;
    }

    public void setMaxActiveBookings(Integer maxActiveBookings) {
        this.maxActiveBookings = maxActiveBookings;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
